# Exercice final J3 — Requêtes sur plusieurs séances

## Contexte

Une séance est représentée ici par une `List<TrainingSet>`. L'entrée complète est donc :

```java
List<List<TrainingSet>> sessions;
```

Les listes externes et internes sont ordonnées. Pour les questions qui demandent le « premier » élément, l'ordre d'itération des deux niveaux fait partie du contrat.

Un `TrainingSet` est une entité identifiée par son UUID. Deux séries ayant le même exercice, le même nombre de répétitions et la même charge peuvent rester deux objets métier distincts.

## Classe à implémenter

Fichier :

`labs/02-java-streams-functional/src/main/java/com/clementlh/labs/streams/SessionTrainingSetQueries.java`

La classe et les signatures des huit méthodes sont déjà créées. Remplace chaque `UnsupportedOperationException` par une implémentation Stream correcte et lisible.

## Besoins métier

### 1. Noms d'exercices uniques à au moins 100 kg

Signature :

```java
List<String> findUniqueExercisesAtLeast100Kg(List<List<TrainingSet>> sessions)
```

Aplatir les séances, garder les séries à `loadKg >= 100.0`, récupérer les noms, éliminer les doublons et trier alphabétiquement.

Opérations attendues à considérer : `flatMap`, `filter`, `map`, `distinct`, `sorted`, `toList`.

### 2. Charge totale

Signature :

```java
double sumLoads(List<List<TrainingSet>> sessions)
```

Additionner la charge de toutes les séries de toutes les séances. Utiliser `mapToDouble(TrainingSet::loadKg)` et `sum()`, pas `reduce`.

### 3. Série la plus lourde

Signature :

```java
Optional<TrainingSet> findHeaviestSet(List<List<TrainingSet>> sessions)
```

Retourner l'objet `TrainingSet` complet qui possède la charge maximale. Si l'entrée ne contient aucune série, retourner `Optional.empty()`.

Choisir une opération qui conserve le `TrainingSet`, et non uniquement sa valeur numérique.

### 4. Nombre de séries par exercice

Signature :

```java
Map<String, Long> countSetsByExercise(List<List<TrainingSet>> sessions)
```

Résultat attendu : nom d'exercice → nombre de séries. Utiliser `groupingBy` avec un downstream collector de comptage.

### 5. Charge moyenne par exercice

Signature :

```java
Map<String, Double> averageLoadByExercise(List<List<TrainingSet>> sessions)
```

Résultat attendu : nom d'exercice → charge moyenne. Utiliser `groupingBy` avec `averagingDouble`.

### 6. Partition légère / lourde

Signature :

```java
Map<Boolean, List<TrainingSet>> partitionByLoadThreshold(List<List<TrainingSet>> sessions)
```

- clé `true` : séries à au moins 100 kg ;
- clé `false` : séries sous 100 kg.

Utiliser `partitioningBy`.

### 7. Charges utilisées par exercice

Signature :

```java
Map<String, List<Double>> loadsByExercise(List<List<TrainingSet>> sessions)
```

Résultat attendu : nom d'exercice → liste des charges de cet exercice. Utiliser `groupingBy` et `mapping` avec un collector final de liste. La liste des charges doit conserver l'ordre de rencontre des séries.

### 8. Première série lourde rencontrée

Signature :

```java
Optional<TrainingSet> findFirstHeavySetInFirstSessionContainingOne(
        List<List<TrainingSet>> sessions
)
```

Retourner la première série à au moins 100 kg de la première séance, dans l'ordre d'entrée, qui contient une série répondant au seuil. Les séances précédentes sans série lourde sont ignorées. Ne pas chercher la série la plus lourde : c'est la première série qui convient qui est attendue.

Si aucune série ne répond au seuil, retourner `Optional.empty()`.

## Règles

- Aucune boucle `for` ou `while` pour implémenter les requêtes.
- Ne pas modifier les listes d'entrée.
- Ne pas utiliser de collection externe mutable alimentée par `forEach`.
- Ne pas ajouter de tri si le résultat métier n'en a pas besoin.
- Éviter les `reduce` lorsque `sum`, `max`, `average` ou un Collector spécialisé exprime directement l'intention.
- Le traitement doit accepter une liste externe vide et des séances internes vides.
- On suppose que les listes et les éléments qu'elles contiennent ne sont pas `null`.
- Ne pas écrire un seul pipeline géant : chaque besoin correspond à une méthode indépendante.

## Tests

Le fichier `SessionTrainingSetQueriesTest.java` contient des tests de contrat désactivés au démarrage.

Après implémentation :

1. retire l'annotation `@Disabled` de la classe de test ;
2. exécute `mvn -pl labs/02-java-streams-functional -am test` ;
3. corrige toute régression ;
4. complète les tests si tu repères des cas limites manquants ;
5. committe ton implémentation ;
6. demande une review.

Les tests couvrent notamment le seuil inclusif de 100 kg, les doublons de noms, l'agrégation sur plusieurs séances, l'ordre de rencontre, les entrées vides et la différence entre « maximum » et « premier élément correspondant ».

## Checklist d'auto-review

Avant de considérer l'exercice terminé, vérifie pour chaque méthode :

- Quel est le type d'entrée ?
- Quel est le type après chaque opération ?
- Quelles opérations sont intermédiaires et laquelle est terminale ?
- Est-ce que l'ordre des opérations est nécessaire ?
- Le résultat attendu est-il un nombre, une collection, une Map ou un Optional ?
- Est-ce que l'entrée peut être vide ?
- Est-ce que chaque Collector exprime bien le besoin métier ?
- Le code a-t-il des effets de bord ?
