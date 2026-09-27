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

### J1 — Identité, égalité et hashCode

- J'avais inversé le fonctionnement de == et equals(). == compare les références entre deux objets ; equals() représente l'égalité logique définie par la classe.
- Si a.equals(b) est vrai, a.hashCode() et b.hashCode() doivent être égaux.
- L'inverse n'est pas obligatoire : deux objets différents peuvent avoir le même hashCode() à cause d'une collision.
- HashSet et HashMap utilisent le hachage pour accélérer la recherche, puis l'égalité pour distinguer les éléments concernés.
- Modifier l'état utilisé par equals() / hashCode() pendant qu'un objet est utilisé dans une hash collection peut rendre la recherche incohérente.
- Pour FitPerformance, TrainingSet est modélisé comme une entité : deux séries peuvent avoir exactement le même exercice, le même nombre de répétitions et la même charge tout en représentant deux occurrences différentes dans une séance.
- L'identité de TrainingSet est donc portée par un UUID stable. Les valeurs métier ne participent pas à equals() / hashCode().
