# Journal d'apprentissage

Ce fichier sert à mémoriser les erreurs, les surprises et les concepts qui ont réellement nécessité du raisonnement.

## Règle

À la fin d'une session importante, noter au minimum :

- ce que je pensais avant ;
- ce que j'ai découvert ;
- l'erreur commise ;
- pourquoi elle s'est produite ;
- comment je prouve que je ne la reproduirai plus.

## Entrées

J1 : equal and hash code
a == b fait une comparaison par référence, si 2 objets sont instanciés avec les mêmes valeurs
, ils ne sont pas égaux par défaut. Il faut redéfinir la méthode equals() pour comparer les valeurs des objets. De plus, si equals() est redéfini,
il faut également redéfinir hashCode() pour garantir que les objets égaux ont le même code de hachage.

Si a.equals(b) est vrai, alors a.hashCode() doit être égal à b.hashCode().
Cela est crucial pour le bon fonctionnement des collections basées sur le hachage, comme HashMap et HashSet.
### J1 — Identité, égalité et hashCode

2 objets différents peuvent avoir le même hashCode() (collision),
mais si a.equals(b) est vrai, alors a.hashCode() doit être égal à b.hashCode().

HashMap et HashSet utilisent le code de hachage pour déterminer où stocker les objets.
Tu peux alors avoir un objet qui a été inséré dans un bucket correspondant à l'ancien hash,
mais qu'une nouvelle recherche tente de trouver à partir du nouveau hash.
Immutabilité → principalement une garantie de cohérence et de sûreté, pas simplement une optimisation.

- J'avais inversé le fonctionnement de == et equals(). == compare les références entre deux objets ; equals() représente l'égalité logique définie par la classe.
- Si a.equals(b) est vrai, a.hashCode() et b.hashCode() doivent être égaux.
- L'inverse n'est pas obligatoire : deux objets différents peuvent avoir le même hashCode() à cause d'une collision.
- HashSet et HashMap utilisent le hachage pour accélérer la recherche, puis l'égalité pour distinguer les éléments concernés.
- Modifier l'état utilisé par equals() / hashCode() pendant qu'un objet est utilisé dans une hash collection peut rendre la recherche incohérente.
- Pour FitPerformance, TrainingSet est modélisé comme une entité : deux séries peuvent avoir exactement le même exercice, le même nombre de répétitions et la même charge tout en représentant deux occurrences différentes dans une séance.
- L'identité de TrainingSet est donc portée par un UUID stable. Les valeurs métier ne participent pas à equals() / hashCode().


### J2 — Collections, generics et choix de structure pour PerformanceStore

#### Ce que je pensais avant l'exercice

Pour stocker au maximum 100 TrainingSet tout en permettant une recherche rapide par UUID et en conservant l'ordre d'insertion, je pensais utiliser une structure de type Map plutôt qu'une List.

J'ai identifié LinkedHashMap comme un bon candidat car elle combine :
- une recherche par clé rapide ;
- la conservation de l'ordre d'insertion.

Je visais les complexités suivantes :
- add : O(1) moyen ;
- findById : O(1) moyen ;
- findAll : O(n).

#### Ce que j'ai découvert / consolidé

Une List conserve bien l'ordre d'insertion. Le problème de la List dans cet exercice n'est donc pas l'ordre, mais la recherche par identifiant : retrouver un TrainingSet à partir de son UUID nécessite potentiellement de parcourir toute la collection, donc O(n).

Une LinkedHashMap permet d'avoir simultanément :
- une association clé → valeur ;
- une recherche par clé rapide ;
- un ordre d'itération correspondant à l'ordre d'insertion.

Pour limiter la collection à 100 éléments, il faut identifier et supprimer le plus ancien élément lorsque la taille dépasse 100. Un point important est que la capacité initiale d'une Map n'est pas une limite fonctionnelle de taille.

La méthode findAll ne doit pas exposer directement la structure interne du store. Retourner directement la collection interne permettrait au code appelant de modifier l'état du store. Retourner une copie protège la structure interne contre ce type de modification.

#### Erreur commise

Dans mon implémentation, j'utilise :

    trainingSets.put(UUID.randomUUID(), trainingSet);

alors que la clé doit être l'identifiant du TrainingSet lui-même.

TrainingSet possède déjà un UUID avec trainingSet.id(). En générant un nouvel UUID pour la clé, je crée deux identités différentes :
- l'identité du TrainingSet ;
- l'identité de l'entrée dans le PerformanceStore.

Cela casse le contrat de findById(UUID id) : une recherche avec trainingSet.id() ne retrouve pas l'élément, puisque cette valeur n'est jamais utilisée comme clé.

Le bon raisonnement est : si l'API expose findById(UUID id) et que l'UUID appartient au TrainingSet, la clé de la Map doit être cet UUID.

#### Autre point à corriger

L'API demandait des méthodes publiques. Mon implémentation utilise actuellement une visibilité package-private :

    void add(...)
    Optional<TrainingSet> findById(...)
    List<TrainingSet> findAll()
    int size()

Cela peut fonctionner dans certains tests du même package, mais ce n'est pas conforme au contrat d'API demandé.

#### Ce que les tests auraient dû prouver

Avant de considérer l'exercice terminé, je dois avoir des tests couvrant au minimum :
- ajout d'un TrainingSet et récupération avec son UUID ;
- UUID inconnu → Optional.empty() ;
- conservation de l'ordre d'insertion dans findAll ;
- taille maximale de 100 ;
- suppression du plus ancien au 101e ajout ;
- deux TrainingSet avec le même contenu mais des UUID différents sont conservés ;
- modification de la List retournée par findAll ne modifie pas le store ;
- size() correspond au nombre d'éléments réellement stockés.

Le test le plus important pour éviter l'erreur commise est le suivant dans l'idée :

    TrainingSet trainingSet = new TrainingSet(...);
    store.add(trainingSet);

    assertThat(store.findById(trainingSet.id()))
        .contains(trainingSet);

Si ce test avait été écrit avant ou en même temps que l'implémentation, l'erreur de clé aurait été détectée immédiatement.

#### Ce que je dois retenir

Le choix d'une structure de données ne doit pas seulement répondre à la question « quelle collection semble adaptée ? ». Il faut vérifier que la représentation choisie respecte exactement les invariants et le contrat de l'API.

Ici, LinkedHashMap est le bon choix architectural pour l'exercice, mais une bonne structure mal alimentée reste une implémentation incorrecte.

Je dois également distinguer :
- ordre d'insertion ;
- clé d'identification ;
- capacité technique ;
- limite métier ;
- encapsulation de l'état interne.

#### Niveau atteint

Collections : 2/5 → 3/5 en cours de consolidation.

Je sais désormais choisir une collection à partir de contraintes concrètes de recherche, d'ordre et d'unicité. Je dois encore progresser sur la vérification systématique des invariants de la structure choisie et sur la conception des tests qui démontrent réellement ces propriétés.


#### Validation finale

La première implémentation avait une bonne structure mais une mauvaise clé UUID et une suppression incorrecte via Iterator.remove(). J'ai corrigé ces deux points et ajouté une couverture de tests correspondant aux invariants du store.

La version finale utilise :
- LinkedHashMap<UUID, TrainingSet> ;
- trainingSet.id() comme clé ;
- suppression du premier élément lorsque la limite de 100 est dépassée ;
- une copie de la collection pour findAll() ;
- une API publique ;
- des tests sur le lookup, l'ordre, la limite, l'éviction, les doublons métier et l'encapsulation.

J2 est maintenant considéré comme validé. La leçon principale est de relier systématiquement le choix de structure, ses invariants, le contrat de l'API et les tests qui démontrent ces propriétés.


### J3 — Streams et programmation fonctionnelle

#### Pipeline Stream

Un Stream représente un pipeline de traitement plutôt qu'une collection de données modifiable. Les opérations intermédiaires construisent le pipeline et sont évaluées de manière paresseuse ; une opération terminale déclenche le traitement.

Concepts consolidés :
- `filter` conserve les éléments correspondant à un prédicat et conserve le type du Stream ;
- `map` transforme chaque élément et peut changer son type ;
- `mapToInt` / `mapToDouble` produisent des streams spécialisés pour les primitives ;
- `distinct` utilise l'égalité des éléments ;
- `sorted` ordonne les éléments mais reste une opération intermédiaire ;
- `toList` matérialise le résultat et termine le pipeline ;
- un Stream déjà consommé ne doit pas être réutilisé.

Point important : l'ordre des opérations peut changer le résultat. Dans FitPerformance, `distinct()` doit intervenir après `map(TrainingSet::exercise)` lorsqu'on veut supprimer les doublons de noms d'exercices, car deux `TrainingSet` de même contenu peuvent avoir des UUID différents et rester différents selon `equals()`.

#### reduce

`reduce` permet de combiner plusieurs éléments en un résultat unique.

Pour `reduce(identity, accumulator)` :
- identity = valeur neutre ;
- accumulator = combinaison du résultat courant avec le prochain élément.

Pour `reduce(identity, accumulator, combiner)` avec changement de type :
- T = type des éléments du Stream ;
- U = type du résultat accumulé ;
- identity = valeur neutre de type U ;
- accumulator = (U, T) -> U ;
- combiner = (U, U) -> U.

Exemple étudié :
`Stream<TrainingSet>` vers `Double` avec une somme de charges.

Une identity doit être neutre pour l'opération : 0 pour l'addition, 1 pour la multiplication.

`max()`, `average()`, `sum()` et autres opérations spécialisées doivent être préférées à un `reduce` artificiel lorsque l'API fournit directement l'opération recherchée.

`mapToDouble(...).max()` retourne un `OptionalDouble` et conserve uniquement la valeur numérique. `max(Comparator)` retourne un `Optional<TrainingSet>` et conserve l'objet complet.

#### Associativité et parallelStream

L'associativité signifie que le regroupement des opérations ne change pas le résultat :
`(a + b) + c = a + (b + c)`.

La commutativité signifie que l'ordre ne change pas le résultat :
`a + b = b + a`.

Ces propriétés ne doivent pas être confondues. L'addition possède les deux ; la concaténation est associative mais pas commutative.

Une réduction avec une opération non associative, comme la soustraction, peut produire un résultat différent entre Stream séquentiel et parallèle. Le test expérimental a été rendu déterministe avec une séquence fixe de 1 à 100.

#### Collectors

`collect` sert à construire un résultat ou une structure à partir du Stream.

`groupingBy(classifier)` produit par exemple :
`Map<String, List<TrainingSet>>`.

Le classifier produit la clé du groupe. Ce n'est pas nécessairement une condition.

Un downstream collector permet d'agréger chaque groupe :
- `counting()` → `Map<String, Long>` ;
- `averagingDouble(...)` → `Map<String, Double>`.

`partitioningBy(predicate)` est adapté à une partition en deux groupes :
`Map<Boolean, List<TrainingSet>>`.

`mapping(..., downstream)` permet de transformer les éléments à l'intérieur de chaque groupe avant d'appliquer le collector final.

`toMap(keyMapper, valueMapper, mergeFunction)` est une alternative à `groupingBy` lorsque le résultat final est directement une valeur par clé. Avec plusieurs éléments pour une même clé, la fonction de fusion doit définir la règle métier. Exemple étudié :
`Double::max` pour obtenir la charge maximale par exercice.

#### Erreurs et corrections

- J'ai d'abord confondu le résultat de `mapToDouble(...).max()` avec `Double` ; le type exact est `OptionalDouble`.
- J'ai d'abord confondu `max(Comparator)` avec `TrainingSet` ; le type exact est `Optional<TrainingSet>`.
- J'ai confondu `distinct` avec `filter` ; `distinct` traite l'égalité, tandis que `filter` applique un prédicat.
- J'ai attribué à l'absence de variable réassignée la conservation des objets dans `reduce` ; la conservation dépend du type et de la valeur retournés par l'accumulateur, pas du nom de la variable.
- J'ai utilisé le hasard dans un test expérimental ; il a été remplacé par des données déterministes.
- J'ai confondu associativité et commutativité ; l'une concerne le regroupement, l'autre l'ordre.


#### flatMap et pipelines imbriqués

Quand une collection contient des collections, `stream()` sur la collection externe produit un `Stream<List<TrainingSet>>`. `flatMap(List::stream)` transforme chaque liste en stream puis aplatit les sous-streams en un seul `Stream<TrainingSet>`.

`map` transforme un élément en un autre élément (`T -> R`). `flatMap` est utile lorsqu'une transformation produit une séquence ou un stream d'éléments, puis qu'il faut aplatir ces séquences.

Pour le modèle final, `List<List<TrainingSet>> sessions` est utilisé sans créer une classe métier Session : chaque liste interne représente une séance et son ordre fait partie du contrat pour les requêtes qui recherchent le premier élément.

#### Short-circuit et évaluation paresseuse

Les opérations intermédiaires comme `filter`, `map` et `flatMap` construisent un pipeline et ne déclenchent généralement pas le parcours seules.

Opérations de short-circuit importantes :
- `anyMatch(predicate)` : au moins un élément convient ;
- `allMatch(predicate)` : tous les éléments conviennent ;
- `noneMatch(predicate)` : aucun élément ne convient ;
- `findFirst()` : premier élément selon l'ordre de rencontre ;
- `findAny()` : un élément correspondant, sans garantie qu'il soit le premier.

Elles retournent un résultat dès qu'elles peuvent conclure. `count()` n'est pas un short-circuit général ; avec un filtre, il faut normalement déterminer combien d'éléments passent ce filtre.

Ne pas confondre le type du résultat :
- match → `boolean` ;
- count → `long` ;
- findFirst/findAny → `Optional<T>`.

#### Effets de bord

Éviter de remplir une collection externe depuis un pipeline, en particulier avec `parallelStream().forEach(...)`. Une `ArrayList` partagée n'est pas conçue pour des mutations concurrentes. Préférer l'expression directe du résultat avec `map(...).toList()`, `collect(...)` ou un Collector approprié.

Les opérations de Stream doivent éviter les modifications de la source pendant le traitement et les effets de bord dans les fonctions passées au pipeline. `forEach` reste adapté lorsqu'un effet externe est le but réel, par exemple une journalisation.

#### Optional

`Optional<T>` représente une valeur qui peut être absente, souvent à la sortie de `findFirst`, `findAny` ou `max(Comparator)`.

Types appris :
- `mapToDouble(...).max()` → `OptionalDouble` ;
- `max(Comparator)` sur `Stream<TrainingSet>` → `Optional<TrainingSet>` ;
- `Optional<TrainingSet>.map(TrainingSet::exercise)` → `Optional<String>` ;
- `orElseThrow()` retourne la valeur ou lance une exception si elle est absente ;
- `orElse(null)` réintroduit `null` et annule une partie de l'intérêt d'Optional ;
- `ifPresent` exécute une action si la valeur est présente, mais n'est pas une façon générale de retourner la valeur.

Point à réviser : différence entre `orElse(defaultValue)` et `orElseGet(supplier)`, notamment le moment où l'expression de repli est évaluée.

#### Choisir une opération en fonction du résultat métier

Avant de construire un pipeline, déterminer le type de résultat voulu :
- nombre : `count`, `sum`, `average`, `min`, `max` sur un stream primitif ;
- objet entier : `max(Comparator)`, `findFirst`, `findAny` ;
- liste filtrée / transformée : `filter`, `map`, éventuellement `distinct` / `sorted`, puis `toList` ;
- map de groupes : `collect(groupingBy(...))` ;
- deux groupes booléens : `partitioningBy(...)` ;
- map d'une valeur par clé avec doublons : `toMap(..., mergeFunction)`.

Préférer une opération spécialisée et explicite à un `reduce` lorsque l'API offre déjà `sum`, `average`, `max` ou un Collector adapté.

#### Exercice final J3 — contrat de travail

L'exercice final est initialisé dans `exercises/02-java-streams-functional/FINAL-EXERCISE.md`, avec la classe `SessionTrainingSetQueries` et les tests de contrat `SessionTrainingSetQueriesTest`.

Les huit besoins portent sur :
1. noms d'exercices uniques avec charge >= 100 kg, triés ;
2. somme des charges de toutes les séances ;
3. TrainingSet le plus lourd ;
4. nombre de séries par exercice ;
5. charge moyenne par exercice ;
6. partition à 100 kg ;
7. liste des charges par exercice ;
8. première série >= 100 kg dans la première séance qui en possède une.

Les tests sont désactivés tant que l'implémentation n'est pas terminée. Une fois les méthodes implémentées, retirer `@Disabled`, exécuter `mvn -pl labs/02-java-streams-functional -am test`, puis demander une review.

Ce jalon n'est pas encore validé : les exercices précédents ont permis d'utiliser seul plusieurs opérations, mais il faut encore réaliser le capstone, vérifier les tests et être capable d'expliquer les compromis et les types de chaque méthode.
