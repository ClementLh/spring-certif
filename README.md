# Java / Spring / Backend — parcours d'expertise

> **Objectif : devenir un développeur backend Java / Spring capable de comprendre les mécanismes, concevoir des solutions robustes, diagnostiquer des problèmes de production et expliquer ses décisions techniques — puis utiliser la certification comme validation, pas comme objectif d'apprentissage unique.**

Ce dépôt est mon **campus personnel** : cours, exercices, labs, expériences JVM/Spring, notes, décisions d'architecture, projet fil rouge et préparation à la certification.

---

## 1. Objectifs

À terme, je veux être capable de :

- maîtriser Java moderne et la JVM ;
- écrire du Java lisible, idiomatique et robuste ;
- comprendre mémoire, GC, JIT, bytecode et diagnostic JVM ;
- maîtriser la concurrence et le Java Memory Model ;
- comprendre le fonctionnement interne de Spring Core ;
- comprendre Spring Boot et son auto-configuration plutôt que simplement utiliser ses annotations ;
- maîtriser SQL, PostgreSQL, JPA, Hibernate et les transactions ;
- concevoir des API HTTP/REST cohérentes et évolutives ;
- mettre en place une stratégie de tests efficace ;
- sécuriser une application Spring ;
- concevoir une architecture modulaire, hexagonale et pragmatique ;
- raisonner sur DDD, événements et systèmes distribués ;
- gérer messaging, retry, timeouts, idempotence et résilience ;
- développer des batchs fiables et restartables ;
- dockeriser, automatiser et déployer une application ;
- instrumenter et diagnostiquer une application en production ;
- défendre une décision technique en expliquant ses compromis ;
- préparer une certification Spring ;
- éventuellement préparer **Oracle Certified Professional Java SE 21 Developer**.

---

## 2. Philosophie du parcours

### 2.1 Comprendre avant d'empiler des annotations

Une compétence n'est pas considérée comme acquise parce que je sais écrire :

```java
@Transactional
```

Je dois pouvoir expliquer ce qui se passe derrière : proxy, interception, transaction manager, connexion, commit/rollback, auto-invocation et limites du mécanisme.

### 2.2 Apprendre par la pratique

Pour une notion importante :

```text
notion
  ↓
explication
  ↓
quiz
  ↓
exercice isolé
  ↓
lab / expérience
  ↓
application au projet
  ↓
review
  ↓
validation
```

### 2.3 Faire des expériences plutôt que croire la théorie

Lorsqu'une question concerne performance, JVM, concurrence, SQL ou Spring internals, je dois chercher à produire une expérience reproductible : benchmark, test concurrent, log SQL, JFR, thread dump, heap dump, EXPLAIN, etc.

### 2.4 Apprendre à choisir, pas seulement à utiliser

Pour chaque technologie ou pattern important, je dois savoir répondre :

- quel problème cela résout ;
- quels sont les coûts ;
- quelles sont les alternatives ;
- quand cela devient du surengineering ;
- quelles propriétés non fonctionnelles cela améliore ou dégrade.

### 2.5 Le projet fil rouge évolue avec mes compétences

Je ne construis pas toute l'application au mois 1.

Je construis une version simple, puis je la refactore et l'enrichis lorsque de nouveaux sujets apparaissent.

---

# 3. Organisation du dépôt

```text
spring-certif/
│
├── README.md
├── PROGRESS.md
├── JOURNAL.md
├── pom.xml
│
├── .github/
│   └── workflows/
│       └── ci.yml
│
├── docs/
│   ├── architecture/
│   │   └── adr/
│   └── project/
│       └── DOMAIN.md
│
├── exercises/
│   └── 01-java-objects-equality/
│
├── labs/
│   └── 01-java-objects-equality/
│
└── projects/
    └── fit-performance/
```

### Rôle de chaque partie

| Répertoire | Rôle |
|---|---|
| `exercises/` | énoncés et travaux pratiques à réaliser sans solution immédiate |
| `labs/` | expériences techniques reproductibles |
| `projects/` | application fil rouge |
| `docs/` | notes de cours, explications, diagrammes et architecture |
| `docs/architecture/adr/` | décisions d'architecture argumentées |
| `PROGRESS.md` | tableau de bord de maîtrise |
| `JOURNAL.md` | erreurs, découvertes et retours d'expérience |

---

# 4. Échelle de maîtrise

Chaque compétence est évaluée sur 6 niveaux :

| Niveau | Signification |
|---:|---|
| 0 | jamais étudié |
| 1 | je reconnais le concept |
| 2 | je sais l'utiliser seul |
| 3 | je sais l'expliquer clairement |
| 4 | je sais diagnostiquer / comparer des solutions |
| 5 | je sais concevoir une solution et expliciter ses compromis |

Une notion structurante n'est **pas considérée comme acquise au niveau 2**.

Pour les fondamentaux Java/Spring, la cible minimale est le niveau 3.

Pour concurrence, performance, architecture et production, la cible est 4 ou 5.

---

# 5. Règles de travail avec le professeur / mentor

Le rôle de l'accompagnant est de :

- expliquer les concepts ;
- construire des exercices progressifs ;
- poser des questions ;
- faire des contre-exemples ;
- corriger du code ;
- challenger les choix ;
- simuler des entretiens techniques ;
- faire passer des mini-examens ;
- suivre la progression ;
- pousser vers les sources officielles ;
- éviter de donner immédiatement la solution lorsque le raisonnement est l'objectif.

Commandes utiles pendant le parcours :

```text
Cours <sujet>
Exercice <numéro>
Lab <sujet>
Quiz <sujet>
Oral <sujet>
Review <chemin>
Debug <problème>
Examen <phase>
Projet <feature>
```

Lorsqu'un exercice est en cours, la préférence est :

```text
question
→ indice
→ contre-exemple
→ nouvel indice
→ solution seulement si nécessaire
```

---

# 6. Preuve de maîtrise

Une case de roadmap doit avoir une preuve.

| Compétence | Théorie | Exercice | Projet | Validation |
|---|---|---|---|---|
| `HashMap` | fonctionnement interne | LRU/cache | fonctionnalité de cache | quiz + oral |
| égalité | contrat `equals/hashCode` | objet métier | value object | tests |
| transactions | propagation/isolation | lab transactionnel | opération métier | scénarios |
| N+1 | SQL généré | lab JPA | endpoint | analyse SQL |
| concurrence | JMM | race condition | écriture concurrente | test concurrent |
| AOP | proxies | proxy lab | audit | explication |
| architecture | principes | design exercise | feature complète | ADR + review |

---

# 7. Stack de départ

## Socle

- Java 21 ;
- Maven ;
- Spring Boot 4.1.1 ;
- Spring MVC ;
- Bean Validation ;
- Spring Boot Actuator ;
- JUnit 5 ;
- AssertJ ;
- Git / GitHub Actions.

Spring Boot 4.1.1 est la version stable affichée actuellement dans la documentation officielle Spring Boot. La documentation précise également que les applications MVC modernes utilisent généralement `spring-boot-starter-webmvc`. Sources :

- https://docs.spring.io/spring-boot/
- https://docs.spring.io/spring-boot/reference/web/index.html

## Outils qui seront introduits progressivement

- PostgreSQL ;
- Flyway ou Liquibase ;
- Spring Data JPA / Hibernate ;
- Testcontainers ;
- WireMock ;
- REST Assured ;
- Spring Security ;
- Kafka ou RabbitMQ ;
- Resilience4j ;
- Spring Batch ;
- Docker / Compose ;
- Kubernetes ;
- Micrometer ;
- Prometheus ;
- Grafana ;
- OpenTelemetry ;
- JFR / JMC ;
- JMH ;
- Spotless ;
- Checkstyle ;
- SpotBugs ;
- JaCoCo ;
- analyse de dépendances / vulnérabilités.

Le formatage Java sera arrêté explicitement dans le parcours après comparaison avec la configuration IntelliJ cible. L'objectif est d'éviter un conflit entre formatage IDE et formatage CI.

---

# 8. Projet fil rouge — FitPerformance

## 8.1 Vision

**FitPerformance** est une plateforme backend dédiée au suivi, à la planification et à l'analyse de l'entraînement en musculation / fitness.

Le projet permet de travailler sur un domaine que je connais et que je peux enrichir naturellement, tout en offrant suffisamment de problèmes techniques pour couvrir une grosse partie du backend moderne.

Le projet n'est pas une simple application CRUD. Il doit progressivement devenir un terrain d'expérimentation pour :

- modèle métier ;
- qualité de code ;
- SQL ;
- JPA ;
- transactions ;
- concurrence ;
- calculs ;
- sécurité ;
- événements ;
- batch ;
- résilience ;
- observabilité ;
- architecture ;
- production.

Détail du domaine : [`docs/project/DOMAIN.md`](docs/project/DOMAIN.md)

---

## 8.2 Fonctionnalités cibles

### Athlète

- [ ] profil ;
- [ ] objectifs ;
- [ ] préférences d'entraînement ;
- [ ] historique de progression.

### Catalogue d'exercices

- [ ] exercice ;
- [ ] groupe musculaire principal ;
- [ ] muscles secondaires ;
- [ ] matériel ;
- [ ] variantes ;
- [ ] consignes et contraintes.

### Planification

- [ ] programme ;
- [ ] séances planifiées ;
- [ ] exercices dans une séance ;
- [ ] ordre des exercices ;
- [ ] séries cibles ;
- [ ] répétitions cibles ;
- [ ] charge cible ;
- [ ] RPE / RIR cible.

### Séance réelle

- [ ] créer une séance ;
- [ ] démarrer une séance ;
- [ ] ajouter un exercice ;
- [ ] enregistrer une série ;
- [ ] renseigner répétitions ;
- [ ] renseigner charge ;
- [ ] renseigner RPE / RIR ;
- [ ] renseigner temps de repos ;
- [ ] terminer une séance ;
- [ ] corriger une série ;
- [ ] annuler une saisie erronée.

### Performance

- [ ] records personnels ;
- [ ] meilleure charge ;
- [ ] meilleure performance à répétitions données ;
- [ ] estimation 1RM ;
- [ ] volume ;
- [ ] tonnage ;
- [ ] progression par exercice ;
- [ ] progression par groupe musculaire.

### Analyse

- [ ] volume hebdomadaire ;
- [ ] fréquence d'entraînement ;
- [ ] évolution des charges ;
- [ ] synthèse hebdomadaire ;
- [ ] comparaison de périodes ;
- [ ] détection d'anomalies simples ;
- [ ] calculs asynchrones de statistiques.

### Administration / sécurité

- [ ] authentification ;
- [ ] rôle athlète ;
- [ ] rôle coach ;
- [ ] rôle administrateur ;
- [ ] permissions métier ;
- [ ] audit des actions sensibles.

### Évolutions avancées

- [ ] notifications ;
- [ ] import / export ;
- [ ] fournisseur externe ;
- [ ] événements métier ;
- [ ] messaging ;
- [ ] batch de synthèse ;
- [ ] reporting.

---

# 9. Architecture cible du projet

## Départ

```text
HTTP
 ↓
Controller
 ↓
Service
 ↓
Repository
 ↓
Database
```

Cette première version sert à apprendre et n'est pas considérée comme l'architecture finale.

## Étape intermédiaire

```text
                 REST API
                    ↓
             Application Layer
                    ↓
              Domain / Rules
                    ↓
        ┌───────────┼───────────┐
        ↓           ↓           ↓
    Database      Events     External API
```

## Cible

```text
                     ┌──────────────────┐
                     │   REST Adapter   │
                     └────────┬─────────┘
                              ↓
                     ┌──────────────────┐
                     │ Application      │
                     │ Use Cases        │
                     └────────┬─────────┘
                              ↓
                     ┌──────────────────┐
                     │ Domain           │
                     │ Entities         │
                     │ Value Objects    │
                     │ Rules            │
                     │ Domain Events    │
                     └────────┬─────────┘
                              ↓
                           Ports
                ┌─────────────┼──────────────┐
                ↓             ↓              ↓
          PostgreSQL       Kafka       External API
```

La cible est un **monolithe modulaire**. Les microservices ne sont pas une finalité.

---

# 10. Architecture : règles du projet

- pas d'abstraction sans problème concret ;
- pas de pattern simplement pour afficher un pattern ;
- pas de microservice par défaut ;
- pas de repository générique sans justification ;
- pas de couche inutile uniquement pour respecter un schéma ;
- privilégier package-by-feature lorsque la structure s'y prête ;
- documenter les décisions structurantes par ADR ;
- chaque refactoring important doit être justifié ;
- les erreurs et compromis doivent être documentés.

---

# 11. Journal et ADR

## Journal

[`JOURNAL.md`](JOURNAL.md)

Pour chaque erreur importante :

```text
Ce que je pensais
→ ce qui s'est passé
→ pourquoi
→ ce que je retiens
→ comment je prouve que j'ai compris
```

## ADR

[`docs/architecture/adr/`](docs/architecture/adr/)

Format :

```text
Context
Problem
Options considered
Decision
Consequences
```

Exemples futurs :

```text
ADR-001 domain model
ADR-002 PostgreSQL
ADR-003 modular boundaries
ADR-004 transaction strategy
ADR-005 optimistic locking
ADR-006 event publication
ADR-007 Kafka
ADR-008 observability
```

---

# 12. Roadmap détaillée

# Phase 0 — Initialisation

**Durée : environ 1 semaine**

Objectifs :

- [x] créer le dépôt ;
- [x] structurer le parcours ;
- [x] choisir le projet fil rouge ;
- [x] mettre en place Java 21 ;
- [x] mettre en place Maven ;
- [x] initialiser un multi-module Maven ;
- [x] initialiser Spring Boot ;
- [x] initialiser CI GitHub Actions ;
- [x] créer le premier lab ;
- [ ] aligner la stratégie de formatage IDE/CI ;
- [ ] confirmer l'environnement local ;
- [ ] premier build local ;
- [ ] premier push propre.

---

# Phase 1 — Java moderne

**Durée : 5 à 6 semaines**

## Fondamentaux

- [ ] types primitifs et wrappers ;
- [ ] passage des paramètres ;
- [ ] références ;
- [ ] portée et durée de vie des variables ;
- [ ] classes et objets ;
- [ ] interfaces ;
- [ ] classes abstraites ;
- [ ] héritage ;
- [ ] composition ;
- [ ] délégation ;
- [ ] polymorphisme ;
- [ ] surcharge ;
- [ ] redéfinition ;
- [ ] encapsulation ;
- [ ] visibilité ;
- [ ] immutabilité ;
- [ ] records ;
- [ ] enums ;
- [ ] classes scellées ;
- [ ] annotations ;
- [ ] pattern matching ;
- [ ] expressions `switch` ;
- [ ] text blocks ;
- [ ] utilisation raisonnée de `var`.

## Objets

- [ ] identité vs égalité ;
- [ ] `equals` ;
- [ ] `hashCode` ;
- [ ] `toString` ;
- [ ] contrats d'égalité ;
- [ ] copie défensive ;
- [ ] objets immuables ;
- [ ] value objects.

## Exceptions

- [ ] checked vs unchecked ;
- [ ] exceptions métier ;
- [ ] exceptions techniques ;
- [ ] propagation ;
- [ ] try-with-resources ;
- [ ] conservation de la cause ;
- [ ] niveau d'abstraction ;
- [ ] stratégie globale d'erreurs.

## Collections

- [ ] `List` ;
- [ ] `Set` ;
- [ ] `Map` ;
- [ ] `Queue` ;
- [ ] `Deque` ;
- [ ] `ArrayList` ;
- [ ] `LinkedList` ;
- [ ] `HashMap` ;
- [ ] `LinkedHashMap` ;
- [ ] `TreeMap` ;
- [ ] `HashSet` ;
- [ ] `LinkedHashSet` ;
- [ ] `TreeSet` ;
- [ ] collections immuables ;
- [ ] complexité temporelle ;
- [ ] complexité mémoire ;
- [ ] collections concurrentes.

## Génériques

- [ ] generic classes ;
- [ ] generic methods ;
- [ ] wildcards ;
- [ ] covariance ;
- [ ] contravariance ;
- [ ] PECS ;
- [ ] type erasure ;
- [ ] limites des génériques ;
- [ ] API génériques lisibles.

## Fonctionnel

- [ ] interfaces fonctionnelles ;
- [ ] lambdas ;
- [ ] method references ;
- [ ] Stream API ;
- [ ] `map` ;
- [ ] `flatMap` ;
- [ ] `filter` ;
- [ ] `reduce` ;
- [ ] collectors ;
- [ ] `groupingBy` ;
- [ ] `partitioningBy` ;
- [ ] évaluation paresseuse ;
- [ ] short-circuit ;
- [ ] effets de bord ;
- [ ] `parallelStream` ;
- [ ] `Optional`.

## Exercices ciblés

- [ ] immutable value object ;
- [ ] LRU cache ;
- [ ] index en mémoire ;
- [ ] moteur de classement ;
- [ ] regroupement de données d'entraînement ;
- [ ] parser ;
- [ ] validation métier ;
- [ ] stratégie de tri ;
- [ ] mini moteur de règles.

---

# Phase 2 — JVM, mémoire et performance

**Durée : 3 à 4 semaines**

## JVM

- [ ] source → bytecode ;
- [ ] class loading ;
- [ ] classloaders ;
- [ ] stack ;
- [ ] heap ;
- [ ] metaspace ;
- [ ] allocation ;
- [ ] JIT ;
- [ ] inlining ;
- [ ] warm-up ;
- [ ] escape analysis ;
- [ ] safepoints ;
- [ ] références fortes ;
- [ ] références faibles ;
- [ ] références fantômes.

## Garbage collector

- [ ] generations ;
- [ ] young / old ;
- [ ] G1 ;
- [ ] ZGC ;
- [ ] stop-the-world ;
- [ ] allocation pressure ;
- [ ] fuite mémoire ;
- [ ] OOM ;
- [ ] StackOverflowError ;
- [ ] logs GC.

## Diagnostic

- [ ] thread dump ;
- [ ] heap dump ;
- [ ] JFR ;
- [ ] Java Mission Control ;
- [ ] VisualVM ;
- [ ] `jstack` ;
- [ ] `jcmd` ;
- [ ] `jmap` ;
- [ ] CPU profiling ;
- [ ] memory profiling ;
- [ ] JMH ;
- [ ] latence ;
- [ ] débit ;
- [ ] saturation.

## Labs

- [ ] allocation benchmark ;
- [ ] comparaison d'objets ;
- [ ] OOM contrôlé ;
- [ ] fuite mémoire artificielle ;
- [ ] étude GC ;
- [ ] benchmark JMH ;
- [ ] JFR d'une application Spring ;
- [ ] analyse d'un thread dump.

---

# Phase 3 — Concurrence

**Durée : 3 à 4 semaines**

- [ ] Java Memory Model ;
- [ ] atomicité ;
- [ ] visibilité ;
- [ ] ordre ;
- [ ] happens-before ;
- [ ] thread safety ;
- [ ] immutabilité ;
- [ ] `synchronized` ;
- [ ] `volatile` ;
- [ ] locks ;
- [ ] atomics ;
- [ ] concurrent collections ;
- [ ] executors ;
- [ ] thread pools ;
- [ ] `CompletableFuture` ;
- [ ] virtual threads ;
- [ ] race conditions ;
- [ ] deadlocks ;
- [ ] starvation ;
- [ ] parallélisme ;
- [ ] asynchronisme ;
- [ ] optimistic locking ;
- [ ] pessimistic locking.

## Labs

- [ ] compteur non thread-safe ;
- [ ] race condition ;
- [ ] correction avec lock ;
- [ ] correction avec atomic ;
- [ ] producer/consumer ;
- [ ] deadlock volontaire ;
- [ ] diagnostic de deadlock ;
- [ ] comparaison platform threads / virtual threads ;
- [ ] contrôle de concurrence sur FitPerformance.

---

# Phase 4 — Spring Core

**Durée : environ 4 semaines**

## IoC / DI

- [ ] IoC ;
- [ ] DI ;
- [ ] `ApplicationContext` ;
- [ ] cycle de vie ;
- [ ] scopes ;
- [ ] constructor injection ;
- [ ] résolution de dépendances ;
- [ ] component scanning ;
- [ ] `@Component` ;
- [ ] `@Service` ;
- [ ] `@Repository` ;
- [ ] `@Controller` ;
- [ ] `@Bean` ;
- [ ] `@Configuration` ;
- [ ] `@Qualifier` ;
- [ ] `@Primary` ;
- [ ] `@Profile` ;
- [ ] événements ;
- [ ] configuration conditionnelle ;
- [ ] dépendances circulaires.

## AOP / proxies

- [ ] JDK proxy ;
- [ ] CGLIB ;
- [ ] interception ;
- [ ] pointcuts ;
- [ ] advice ;
- [ ] `@Around` ;
- [ ] auto-invocation ;
- [ ] méthodes privées ;
- [ ] ordre des aspects ;
- [ ] fonctionnement de `@Transactional` ;
- [ ] fonctionnement de `@Async` ;
- [ ] fonctionnement de `@Cacheable`.

## Labs

- [ ] mini `ApplicationContext` conceptuel ;
- [ ] bean lifecycle lab ;
- [ ] dependency resolution lab ;
- [ ] JDK proxy lab ;
- [ ] CGLIB proxy lab ;
- [ ] AOP timing aspect ;
- [ ] auto-invocation démontrée par test.

---

# Phase 5 — Spring Boot et HTTP

**Durée : 3 semaines**

- [ ] starters ;
- [ ] dependency management ;
- [ ] auto-configuration ;
- [ ] conditions ;
- [ ] `application.yml` ;
- [ ] configuration properties ;
- [ ] validation de configuration ;
- [ ] profiles ;
- [ ] environment variables ;
- [ ] property precedence ;
- [ ] Actuator ;
- [ ] logs ;
- [ ] graceful shutdown ;
- [ ] health checks ;
- [ ] serveur HTTP ;
- [ ] packaging ;
- [ ] lancement ;
- [ ] auto-configuration custom ;
- [ ] création d'un starter interne ;
- [ ] tests d'auto-configuration.

## HTTP / REST

- [ ] méthodes HTTP ;
- [ ] méthodes sûres ;
- [ ] idempotence ;
- [ ] status codes ;
- [ ] headers ;
- [ ] content negotiation ;
- [ ] cache HTTP ;
- [ ] ETag ;
- [ ] CORS ;
- [ ] cookies / sessions ;
- [ ] timeouts ;
- [ ] TLS ;
- [ ] HTTP/1.1 ;
- [ ] HTTP/2 ;
- [ ] ressources ;
- [ ] URI ;
- [ ] DTO ;
- [ ] validation ;
- [ ] erreurs globales ;
- [ ] Problem Details ;
- [ ] pagination ;
- [ ] filtres ;
- [ ] tri ;
- [ ] versioning ;
- [ ] compatibilité ascendante ;
- [ ] idempotency key ;
- [ ] OpenAPI ;
- [ ] dépréciation ;
- [ ] contract tests.

---

# Phase 6 — SQL et PostgreSQL

**Durée : 4 semaines**

## Modélisation

- [ ] tables ;
- [ ] relations ;
- [ ] contraintes ;
- [ ] primary keys ;
- [ ] foreign keys ;
- [ ] natural vs surrogate keys ;
- [ ] normalisation ;
- [ ] unique constraints ;
- [ ] referential integrity ;
- [ ] hiérarchies.

## SQL

- [ ] joins ;
- [ ] subqueries ;
- [ ] aggregations ;
- [ ] CTE ;
- [ ] recursive CTE ;
- [ ] window functions ;
- [ ] détection de doublons ;
- [ ] pagination stable ;
- [ ] tri ;
- [ ] filtrage ;
- [ ] traitements de masse.

## Performance

- [ ] index simples ;
- [ ] index composites ;
- [ ] sélectivité ;
- [ ] cardinalité ;
- [ ] plans d'exécution ;
- [ ] EXPLAIN / ANALYZE ;
- [ ] requêtes lentes ;
- [ ] verrous ;
- [ ] MVCC ;
- [ ] deadlocks ;
- [ ] isolation ;
- [ ] partitionnement.

## Schéma

- [ ] Flyway ou Liquibase ;
- [ ] migrations versionnées ;
- [ ] migrations compatibles production ;
- [ ] stratégie de correction ;
- [ ] tests de migration.

## Labs

- [ ] créer le modèle FitPerformance ;
- [ ] 20 requêtes SQL ;
- [ ] recherche de N+1 côté SQL ;
- [ ] EXPLAIN d'une requête lente ;
- [ ] index avant/après ;
- [ ] deadlock PostgreSQL ;
- [ ] isolation levels.

---

# Phase 7 — JPA / Hibernate / Spring Data

**Durée : 4 semaines**

## Persistence context

- [ ] transient ;
- [ ] managed ;
- [ ] detached ;
- [ ] removed ;
- [ ] persistence context ;
- [ ] dirty checking ;
- [ ] flush automatique ;
- [ ] flush explicite ;
- [ ] first-level cache ;
- [ ] second-level cache ;
- [ ] `save` ;
- [ ] `flush` ;
- [ ] `saveAndFlush`.

## Mapping

- [ ] `@OneToOne` ;
- [ ] `@OneToMany` ;
- [ ] `@ManyToOne` ;
- [ ] `@ManyToMany` ;
- [ ] owning side ;
- [ ] `mappedBy` ;
- [ ] cascade ;
- [ ] orphan removal ;
- [ ] lazy ;
- [ ] eager ;
- [ ] relations bidirectionnelles ;
- [ ] equality des entités.

## Requêtes

- [ ] JPQL ;
- [ ] Criteria API ;
- [ ] native SQL ;
- [ ] projections ;
- [ ] fetch join ;
- [ ] EntityGraph ;
- [ ] N+1 ;
- [ ] pagination ;
- [ ] batch ;
- [ ] optimistic locking ;
- [ ] pessimistic locking.

## Labs

- [ ] état d'une entity observé pendant une transaction ;
- [ ] dirty checking ;
- [ ] N+1 reproduit ;
- [ ] N+1 corrigé ;
- [ ] comparaison fetch join / projection ;
- [ ] `save` / `flush` / `saveAndFlush` ;
- [ ] optimistic locking sur une séance ;
- [ ] concurrence sur une performance.

---

# Phase 8 — Transactions

**Durée : 2 à 3 semaines**

- [ ] frontières transactionnelles ;
- [ ] `@Transactional` ;
- [ ] commit ;
- [ ] rollback ;
- [ ] checked exception ;
- [ ] unchecked exception ;
- [ ] `REQUIRED` ;
- [ ] `REQUIRES_NEW` ;
- [ ] autres propagations ;