# J4 — Étape 2 : lire le processus avec les outils du JDK

## Objectif

Utiliser un processus Java qui retient volontairement des tableaux, puis observer ce processus depuis un second terminal. L'objectif est d'apprendre à formuler des hypothèses et à recueillir des indices, pas de conclure à une fuite à partir d'une seule mesure.

Références officielles :
- [Diagnostic Tools du JDK 21](https://docs.oracle.com/en/java/javase/21/troubleshoot/diagnostic-tools.html)
- [Dépannage des fuites mémoire — JDK 21](https://docs.oracle.com/en/java/javase/21/troubleshoot/troubleshooting-memory-leaks.html)
- [Options de lancement Java et journalisation unifiée](https://docs.oracle.com/en/java/javase/21/docs/specs/man/java.html)

## 1. Préparer le lancement

Depuis la racine du dépôt, compile le module :

\`\`\`powershell
mvn -pl labs/03-jvm-memory -am compile
\`\`\`

Lance ensuite la démo dans un premier terminal PowerShell :

\`\`\`powershell
java -Xms64m -Xmx256m "-Xlog:gc*:file=labs/03-jvm-memory/target/gc.log:time,uptime,level,tags" -cp labs/03-jvm-memory/target/classes com.clementlh.labs.jvm.RetentionDemo
\`\`\`

La démo démarre avec 8 tableaux de 1 MiB (soit 8 MiB de tableaux, hors surcoûts). Elle affiche le PID et attend une commande :
- Entrée ou \`add\` : ajoute 8 MiB de tableaux ;
- \`release\` : vide le registre ;
- \`quit\` : termine le processus.

Laisse la démo ouverte pendant les étapes suivantes. N'essaie pas de remplir le heap jusqu'à l'OutOfMemoryError ; quelques ajouts suffisent.

Si \`java\` n'est pas trouvé ou si \`jcmd\` n'est pas reconnu, vérifie \`java -version\`, \`where.exe java\` et \`where.exe jcmd\`. Les utilitaires se trouvent dans le dossier \`bin\` du JDK. Vérifie que \`java\` et \`jcmd\` proviennent d'un JDK cohérent.

## 2. Lister et inspecter le processus

Dans un second terminal PowerShell :

\`\`\`powershell
jcmd -l
\`\`\`

Repère le PID affiché par \`RetentionDemo\`, puis remplace \`<PID>\` dans les commandes suivantes :

\`\`\`powershell
jcmd <PID> VM.version
jcmd <PID> VM.flags
jcmd <PID> GC.heap_info
\`\`\`

Questions à noter :
- Quelle version de JVM utilise le processus ?
- Quelle valeur de heap maximal est indiquée dans les flags ?
- Quelle différence vois-tu entre le heap utilisé et la capacité / le maximum du heap ?

Les commandes disponibles dépendent de la JVM ; \`jcmd <PID> help\` permet de vérifier celles disponibles sur le processus ciblé. \`jcmd\` doit être lancé sur la même machine et avec des droits compatibles avec ceux du processus cible.

## 3. Examiner l'histogramme des classes

Toujours dans le deuxième terminal :

\`\`\`powershell
jcmd <PID> GC.class_histogram
\`\`\`

Repère les lignes de type \`[B\` : elles correspondent aux tableaux de \`byte\` dans l'histogramme HotSpot. La taille totale estimée des tableaux devrait augmenter après plusieurs commandes \`add\`.

**Prudence :** un histogramme peut être coûteux. Utilise-le sur ce processus de laboratoire, pas comme une commande anodine sur un service sensible. N'interprète pas un seul histogramme comme une preuve suffisante de fuite mémoire.

Retourne dans le premier terminal, tape \`add\` plusieurs fois et compare les résultats. Puis tape \`release\` : le registre indique alors qu'il ne détient plus aucun tableau. Cela ne prouve pas à lui seul que le GC a déjà collecté les tableaux ou rendu la mémoire au système.

## 4. Comprendre les journaux GC

Le lancement utilise :

\`\`\`text
-Xlog:gc*:file=labs/03-jvm-memory/target/gc.log:time,uptime,level,tags
\`\`\`

Le fichier \`labs/03-jvm-memory/target/gc.log\` recueille les événements GC sélectionnés par le tag \`gc\`. Il peut ne contenir que peu d'événements si la charge est trop faible : ce n'est pas une erreur. La collecte dépend de l'état du heap et des choix de la JVM, pas du nombre exact de fois où tu tapes \`add\`.

À retenir :
- le GC peut récupérer des objets inatteignables ;
- il n'est pas obligé de s'exécuter immédiatement lorsque des objets deviennent inatteignables ;
- une baisse ou une hausse ponctuelle de mémoire ne suffit pas à diagnostiquer une fuite ;
- si plusieurs GC importants n'arrivent pas à faire baisser le live set, cela peut indiquer une rétention anormale à investiguer.

## 5. Enregistrer une courte session JFR

Pendant que la démo tourne, commence une capture d'une minute depuis le deuxième terminal :

\`\`\`powershell
jcmd <PID> JFR.start name=J4Memory settings=profile duration=60s filename=labs/03-jvm-memory/target/j4-memory.jfr
\`\`\`

Pendant la capture, tape \`add\` quelques fois dans le premier terminal, puis \`release\`. Reste à une taille largement inférieure aux 256 MiB de heap maximal.

Une fois la capture terminée :

\`\`\`powershell
jfr summary labs/03-jvm-memory/target/j4-memory.jfr
\`\`\`

Si JDK Mission Control (JMC) est installé, ouvre le fichier \`.jfr\` et regarde les vues Memory et Garbage Collections. Le modèle \`profile\` collecte davantage d'informations que \`default\` et peut ajouter du surcoût ; garde cette capture courte et réservée au laboratoire.

JFR enregistre des événements et des échantillons de l'exécution. Ce n'est pas la même chose qu'un heap dump : pour déterminer pourquoi un objet est encore retenu, il faut notamment examiner ses chemins de référence.

## 6. Compte-rendu à faire avant la suite

Note dans ton journal :
1. le PID du processus ;
2. les informations pertinentes de \`GC.heap_info\` ;
3. ce qui a changé dans l'histogramme après deux ou trois commandes \`add\` ;
4. ce qui change après \`release\` et ce qui n'est **pas** garanti ;
5. deux observations tirées du journal GC ou du résumé JFR ;
6. pourquoi ce laboratoire montre une rétention volontaire et ne constitue pas encore un diagnostic complet d'une fuite mémoire.

Ne commite aucun heap dump ou fichier JFR dans le dépôt : ils peuvent être volumineux et contenir des données sensibles dans des contextes réels.
