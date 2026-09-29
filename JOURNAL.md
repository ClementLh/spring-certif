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
