# J6 — Spring Core : IoC, DI et beans

## Objectif

Comprendre comment Spring prend en charge la création des objets et la résolution de leurs dépendances, sans confondre compilation et démarrage du contexte.

## Contexte FitPerformance

Le service `TrainingService` dépend du contrat `TrainingRepository`.
Deux implémentations sont fournies comme candidates :
- `InMemoryTrainingRepository`
- `JpaTrainingRepository` (placeholder pédagogique : aucun accès JPA réel dans ce lab)

Le service ne doit pas instancier lui-même son repository.

## Exercices

### J6.1 — Injection par constructeur (sans Spring)
- [ ] Compléter le contrat `TrainingRepository` avec une opération minimale.
- [ ] Implémenter les deux repositories.
- [ ] Ajouter le constructeur de `TrainingService` qui reçoit le contrat.
- [ ] Ajouter une opération de service qui délègue au repository.
- [ ] Instancier manuellement les objets dans une classe de démonstration ou un test.
- [ ] Expliquer ce qui est couplé et ce qui ne l'est pas.

### J6.2 — Configuration explicite avec `@Configuration` et `@Bean`
- [ ] Annoter `AppConfig` avec la configuration Spring appropriée.
- [ ] Déclarer un bean pour chaque repository.
- [ ] Déclarer le bean `TrainingService`.
- [ ] Faire résoudre sa dépendance par Spring.
- [ ] Créer le contexte Spring et récupérer le service.
- [ ] Vérifier que le service et le repository sont bien des objets gérés par le contexte.

### J6.3 — Plusieurs candidats : résolution du bean
- [ ] Observer le comportement lorsque deux beans implémentent `TrainingRepository`.
- [ ] Résoudre l'ambiguïté avec `@Primary` ou `@Qualifier`.
- [ ] Justifier le choix et indiquer où il est exprimé.
- [ ] Changer l'implémentation sélectionnée sans modifier `TrainingService`.

### J6.4 — Découverte automatique
- [ ] Ajouter une configuration de component scan.
- [ ] Annoter les classes avec les stéréotypes appropriés.
- [ ] Comparer cette approche à la configuration explicite par méthodes `@Bean`.
- [ ] Identifier les cas où une configuration explicite reste préférable.

### J6.5 — Cycle de vie et portée
- [ ] Observer la création et la fermeture du contexte.
- [ ] Ajouter un point d'observation du cycle de vie.
- [ ] Vérifier le comportement par défaut d'un bean singleton.
- [ ] Expliquer pourquoi singleton Spring ne signifie pas classe statique ni thread-safe.

## Questions de compréhension

1. À quel moment Spring crée-t-il les beans : compilation ou démarrage du contexte ?
2. Qui est responsable de construire `TrainingRepository` ?
3. Quelle différence entre `@Configuration` et `@Bean` ?
4. Pourquoi le constructeur de `TrainingService` dépend-il de l'interface ?
5. Comment Spring résout-il deux beans candidats du même type ?
6. Quand utiliser `@Primary` plutôt que `@Qualifier` ?
7. Quelle différence entre configuration explicite et component scan ?
8. Que signifie singleton dans le contexte Spring ?

## Commandes

Depuis la racine :

```bash
mvn -pl labs/05-spring-core -am test
```

Le squelette ne contient pas de solution et les tests doivent être ajoutés/activés par l'apprenant. Un build vert sans assertions pertinentes ne valide pas J6.

## Critères de validation

- [ ] le service reçoit sa dépendance via constructeur ;
- [ ] le service ne fait pas de `new` sur le repository ;
- [ ] le contexte Spring démarre et fournit le service ;
- [ ] le bean candidat est choisi sans ambiguïté ;
- [ ] le changement d'implémentation ne modifie pas le service ;
- [ ] les tests vérifient le wiring et la délégation ;
- [ ] les huit questions sont expliquées ;
- [ ] le journal de bord est complété après l'implémentation.

**Règle de travail :** implémentation par l'apprenant. Ce dossier et les classes du lab sont volontairement des squelettes.