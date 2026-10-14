# J4 — JVM et mémoire : objets, références et rétention

## Objectif de cette première séance

Comprendre pourquoi un objet reste en mémoire, ce que signifie « être atteignable » (*reachable*), et pourquoi une fuite mémoire peut exister même si le garbage collector fonctionne correctement.

Cette première séance n'essaie pas encore d'expliquer tous les algorithmes de GC ni de mesurer précisément la mémoire du processus. On commence par un modèle mental et une expérience contrôlée.

## 1. Le modèle mental à connaître

### Stack (pile d'exécution)

Chaque thread Java possède sa pile d'exécution. Les appels de méthodes créent des frames qui contiennent notamment l'état local de chaque appel : variables locales et références nécessaires à l'exécution.

Une récursion profonde peut épuiser cette pile et provoquer un `StackOverflowError`.

### Heap (tas)

Les objets et les tableaux sont normalement représentés dans le heap, partagé par les threads. Une variable locale peut contenir une **référence** vers un objet du heap : la variable et l'objet référencé ne sont pas la même chose.

C'est un modèle pédagogique : les optimisations de la JVM (par exemple l'escape analysis et l'élimination d'allocations par le JIT) peuvent faire qu'une allocation théorique n'existe pas sous la forme attendue en mémoire physique.

### Metaspace

Le metaspace contient des métadonnées de classes chargées. Il ne faut pas le confondre avec le heap des objets applicatifs.

### Atteignabilité et GC

Le garbage collector peut récupérer un objet lorsqu'il n'est plus atteignable depuis les racines GC (par exemple les références de threads, les champs statiques et certaines références JNI). Il n'a aucune obligation de collecter un objet dès que celui-ci devient inutilisé.

Point fondamental : **un objet encore référencé reste atteignable**, même si l'application n'en a plus réellement besoin. Une collection qui grossit sans limite peut donc retenir des objets inutilement. C'est une cause classique de fuite mémoire.

Retirer une référence rend éventuellement l'objet récupérable ; cela ne garantit ni que le GC s'exécute immédiatement, ni que la mémoire soit rendue immédiatement au système d'exploitation.

## 2. Exercice à implémenter

Classe : `labs/03-jvm-memory/src/main/java/com/clementlh/labs/jvm/PayloadRetentionRegistry.java`

Cette classe simule un composant qui conserve des tableaux d'octets. Elle est volontairement simple afin d'observer les références fortes et la rétention d'objets, sans dépendre de mesures de mémoire bruitées.

Les méthodes ont les contrats suivants :

- `retainPayload(int sizeInBytes)` : conserve un nouveau `byte[]` de la taille demandée ; une taille nulle ou négative doit provoquer `IllegalArgumentException`.
- `releaseAll()` : retire toutes les références détenues par le registre.
- `retainedPayloadCount()` : renvoie le nombre de tableaux encore référencés par le registre.
- `retainedBytes()` : renvoie la somme de la longueur des tableaux conservés.

**Attention :** `retainedBytes()` mesure uniquement la somme des tailles des tableaux du scénario, pas l'empreinte réelle du heap. Cela n'inclut ni les en-têtes des objets, ni la capacité interne de la liste, ni les autres objets du processus.

## 3. Travail demandé

1. Implémente les quatre méthodes en respectant leur contrat.
2. Ne change pas les signatures publiques ou le nom du package.
3. Ne masque pas une entrée invalide : refuse les tailles inférieures ou égales à zéro.
4. Vérifie qu'après `releaseAll()`, le registre ne référence plus les tableaux précédemment retenus.
5. Active les tests en retirant `@Disabled` dans `PayloadRetentionRegistryTest`.
6. Lance les tests et inspecte le résultat.

Commande à exécuter depuis la racine du dépôt :

```bash
mvn -pl labs/03-jvm-memory -am test
```

## 4. Questions à pouvoir expliquer après l'exercice

- Si une méthode se termine, ses variables locales restent-elles des racines actives de la même manière ?
- Pourquoi un tableau de 5 Mo peut-il rester en mémoire alors qu'aucun code métier ne s'en sert encore ?
- Pourquoi `releaseAll()` n'est-il pas une preuve que le GC a déjà récupéré les tableaux ?
- Pourquoi un test qui attend que `System.gc()` libère immédiatement une quantité précise de mémoire est-il fragile ?
- Quelle différence fais-tu entre mémoire allouée, mémoire encore atteignable, mémoire récupérable par le GC et mémoire rendue au système ?

## Contraintes

- Ne fais pas dépendre les tests du moment où le GC se déclenche.
- N'utilise pas `System.gc()` comme mécanisme fonctionnel ou comme assertion de test.
- Ne cherche pas à provoquer un `OutOfMemoryError` dans cette première séance.
- Ne déduis pas une fuite mémoire d'une seule valeur de mémoire utilisée : il faut observer l'évolution, l'atteignabilité et les références retenues.

## Étape suivante

Après review, poursuis avec [l'étape 2 — outils de diagnostic](DIAGNOSTIC-TOOLS.md). Elle utilise `RetentionDemo` pour explorer le processus avec `jcmd`, les journaux GC et JFR. Cette expérience simple ne remplace pas un heap dump : elle pose le modèle mental nécessaire pour interpréter ce qu'un outil de diagnostic montre.
