# J5.2 — Compteur partagé et race condition

## Objectif

Démontrer une perte de mise à jour dans un compteur partagé, puis comparer trois stratégies :

1. aucune protection (**UnsafeTrainingCounter**) ;
2. exclusion mutuelle avec **synchronized** (**SynchronizedTrainingCounter**) ;
3. incrément atomique avec **AtomicInteger** (**AtomicTrainingCounter**).

Ce lab est volontairement indépendant de Spring : on isole d'abord les garanties du langage Java et du Java Memory Model (JMM), avant de les réutiliser dans FitPerformance.

## Contrat

L'interface **TrainingCounter** expose :

- **increment()** : incrémente le compteur d'une unité ;
- **getCount()** : renvoie la valeur courante.

Pour les versions thread-safe, plusieurs appels concurrents à **increment()** ne doivent pas perdre d'incrément. Pour la version non protégée, une perte est possible, mais elle n'est pas garantie à chaque exécution.

## Travail demandé

1. Implémente les trois classes sans modifier l'interface publique.
2. Ouvre **TrainingCounterConcurrencyTest** et examine le harnais fourni.
3. Retire les annotations **@Disabled** au fur et à mesure que les implémentations sont prêtes.
4. Lance d'abord les tests des compteurs thread-safe.
5. Lance ensuite le test de diagnostic non protégé plusieurs fois et note les résultats observés.
6. Explique les cinq questions ci-dessous dans ton compte-rendu ou à l'oral.

### Contraintes de test

- 10 workers exécutent chacun 100 000 incréments, soit 1 000 000 au total.
- Le **CountDownLatch** rapproche le départ des workers ; il ne protège pas le compteur.
- Les **Future.get(...)** doivent être examinés afin d'attendre les tâches et de propager les erreurs.
- Aucun test ne doit affirmer que la version non protégée donnera obligatoirement un résultat inférieur à 1 000 000.
- N'utilise pas **Thread.sleep()** pour essayer de rendre la race condition déterministe.
- Le test doit arrêter proprement l'**ExecutorService**, y compris en cas d'erreur.

## Questions de compréhension

1. Pourquoi utiliser **CountDownLatch** ? Corrige-t-il la race condition ?
2. Pourquoi **Future.get()** est-il préférable à une attente arbitraire avec **Thread.sleep()** ?
3. Pourquoi faut-il examiner les **Future** plutôt que simplement appeler **shutdown()** ?
4. Quand choisir **AtomicInteger** plutôt que **synchronized** dans un service Spring ? Quand un verrou est-il préférable ?
5. Pourquoi **volatile int count** ne suffit-il pas à rendre **count++** atomique ?

## Commandes

Depuis la racine du dépôt :

    mvn -pl labs/04-concurrency -am test

Les tests sont désactivés initialement afin que le squelette compile sans fournir la solution. Active-les au fur et à mesure de ton implémentation. Un build vert avec les tests encore désactivés ne constitue pas une validation de l'exercice.

## Critères de validation

- [ ] les trois implémentations respectent le contrat ;
- [ ] les deux versions thread-safe passent le test à 1 000 000 incréments ;
- [ ] la version non protégée est testée comme une expérience, sans assertion flaky ;
- [ ] les exceptions de tâches ne sont pas silencieusement ignorées ;
- [ ] le cycle de vie de l'exécuteur est correctement géré ;
- [ ] les cinq questions sont expliquées avec les notions d'atomicité, visibilité, ordre et happens-before ;
- [ ] l'apprenant sait justifier les compromis entre verrou et atomique.

Ne considère pas J5 comme validé tant que le code, les tests exécutés et l'explication ne sont pas revus.


---

## Étape suivante : J5.3 — invariant métier et capacité

Après avoir terminé J5.2, passe à [J5.3 — Capacité d'une séance](J5.3-CAPACITY-INVARIANT.md). L'objectif est de garantir qu'un ensemble d'opérations concurrentes ne dépasse jamais la capacité maximale, et de comprendre pourquoi une opération métier composée nécessite une section critique.
