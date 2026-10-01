# Exercice 02 — Streams / programmation fonctionnelle

## Objectif

Construire un premier pipeline Stream lisible en combinant :

- `filter`
- `map`
- `distinct`
- `sorted`
- `toList`

## Problème

À partir d'une liste de `TrainingSet`, retourner la liste des noms d'exercices uniques réalisés à **au moins 80 kg**, triés par ordre alphabétique.

Exemple conceptuel :

```text
List<TrainingSet>
    ↓
Stream<TrainingSet>
    ↓
filter(loadKg >= 80)
    ↓
map(TrainingSet → String)
    ↓
distinct()
    ↓
sorted()
    ↓
toList()
    ↓
List<String>
```

## Contraintes

- Le résultat doit conserver uniquement les exercices à partir de 80 kg inclus.
- Deux `TrainingSet` ayant le même contenu mais des UUID différents restent deux entités ; le `distinct()` doit donc intervenir après la transformation vers le nom de l'exercice.
- Le résultat doit être trié par ordre alphabétique.
- Utiliser la Stream API.
- Ne pas modifier la liste fournie en entrée.
- Ne pas utiliser de boucle `for` ou `while` pour effectuer le traitement demandé.

## Travail demandé

Implémenter :

```java
public List<String> findUniqueExercisesAtLeast80Kg(List<TrainingSet> trainingSets)
```

dans `TrainingSetQueries`.

Avant de coder, être capable d'expliquer le type à chaque étape du pipeline.

## Validation

Les tests fournis décrivent le comportement attendu. Ils sont volontairement désactivés au démarrage de l'exercice : active-les lorsque ton implémentation est prête.

Après implémentation :

1. tests verts ;
2. revue de code ;
3. explication orale du pipeline ;
4. exercice complémentaire sur `reduce`.
