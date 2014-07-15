MowItNow
=========
Une solution technique pour un entretien technique chez Xebia.

## Spécifications
<pre>
La société MowItNow a décidé de développer une tondeuse à gazon automatique, destinée aux surfaces rectangulaires.

La tondeuse peut être programmée pour parcourir l'intégralité de la surface.
La position de la tondeuse est représentée par une combinaison de coordonnées (x,y) et d'une lettre indiquant l'orientation selon la notation cardinale anglaise (N,E,W,S). La pelouse est divisée en grille pour simplifier la navigation. 

Par exemple, la position de la tondeuse peut être « 0, 0, N », ce qui signifie qu'elle se situe dans le coin inférieur gauche de la pelouse, et orientée vers le Nord.

Pour contrôler la tondeuse, on lui envoie une séquence simple de lettres. Les lettres possibles sont « D », « G » et « A ». « D » et « G » font pivoter la tondeuse de 90° à droite ou à gauche respectivement, sans la déplacer. « A » signifie que l'on avance la tondeuse d'une case dans la direction à laquelle elle fait face, et sans modifier son orientation.

Si la position après mouvement est en dehors de la pelouse, la tondeuse ne bouge pas, conserve son orientation et traite la commande suivante. 

On assume que la case directement au Nord de la position (x, y) a pour coordonnées (x, y+1).

Pour programmer la tondeuse, on lui fournit un fichier d'entrée construit comme suit :
•	La première ligne correspond aux coordonnées du coin supérieur droit de la pelouse, celles du coin inférieur gauche sont supposées être (0,0)
•	La suite du fichier permet de piloter toutes les tondeuses qui ont été déployées. Chaque tondeuse a deux lignes la concernant :
•	la première ligne donne la position initiale de la tondeuse, ainsi que son orientation. La position et l'orientation sont fournies sous la forme de 2 chiffres et une lettre, séparés par un espace
•	la seconde ligne est une série d'instructions ordonnant à la tondeuse d'explorer la pelouse. Les instructions sont une suite de caractères sans espaces.

Chaque tondeuse se déplace de façon séquentielle, ce qui signifie que la seconde tondeuse ne bouge que lorsque la première a exécuté intégralement sa série d'instructions.

Lorsqu'une tondeuse achève une série d'instruction, elle communique sa position et son orientation.

OBJECTIF
Concevoir et écrire un programme s'exécutant sur une JVM ≥ 1.7, un navigateur web ou un serveur node.js, et implémentant la spécification ci-dessus et passant le test ci-après

TEST
Le fichier suivant est fourni en entrée :
5 5
1 2 N
GAGAGAGAA
3 3 E
AADAADADDA

On attend le résultat suivant (position finale des tondeuses) :
1 3 N
5 1 E

NB: Les données en entrée peuvent être injectée sous une autre forme qu'un fichier (par exemple un test automatisé).
</pre>

## Conception
Ce projet se décompose selon les packages suivants:
- <strong>base</strong>: fournie les classes de base (exemple : Position, Cell, ...)
- <strong>mower</strong>: represente l'implémentation logique du besoin
- <strong>io</strong>: spécifie les entrées/sorties du système
- <strong>util</strong>: ensemble de classes utilitaires

## Démonstration
On propose une démonstration utilisant un serveur web embarqué (Spring boot) avec un protocoloe de communication bidirectionnelle (Websocket)

L'IHM exposé prend en entrée un fichier (en drop) ou une chaine de caractère et affiche en sortie tout les 3 ms le deplacemment des tondeuses sur une pelouse reprsentée par un canevas

## Tests
Pour les tests, on propose en plus des tests unitaires (junit), des tests fonctionnels (Jbehave), et des tests integration (selenium)

## Environnements technique
* Langage de programmation: Java, Javascript
* Gestionnaire de source: Git - repository: https://github.com/snilyes/mowitnow
* Gestionnaire de projet: Maven
* IDE: Eclipse Luna
* Serveur: Spring Boot (v 4)
* Protocole: Http, Websocket
* Tests: JUnit, Junitparams, Selenium, JBehave
* Web: Html 5, Bootstrap
* Integration & Déploiement Continue: Jenkins - Cloudbees - https://snilyes.ci.cloudbees.com/job/xebia-interview/
* Autres Dependances java: Spring boot, Spring messaging, Lambok, Guava, jackson, logback, ...
* Autres Dependances JS: jquery, stomp.js, sockjs.js, jcanva, filedrop.js

## Compilation & Déploiement
### Sur le Cloud
Ce projet est compilé et deployé sur le cloud "PaaS" dénommé Cloudbess utilisant Jenkins
, le Job jenkins lié est situé https://snilyes.ci.cloudbees.com/job/xebia-interview/

Dans le post build du job on configure un deploiement conditioné de la stabilité du build sur une JVM fournit par cloudbees le port http étant dynamique faudrait donc le récupérer depuis une variable JVM "app.port"

La demo est disponible sur http://mowitnow.snilyes.eu.cloudbees.net/
### Avec Maven
Spring boot fournit un plugin permettant de compiler et de déployer localement l'application sous le port 8080
On doit simpelment tappez cette commande <code>mvn spring-boot:run</code>
sur une console.

La demo sera disponible sur http://localhost:8080/
### En générant un jar executable
Pour générer le jar executable, il faut simplement tapper dans la console<code>mvn clean install</code>, le plugin maven-shade-plugin va packager le jar en générant un exécutable dans <code>target/xebia-interview</code>
Il reste qu'à lancer l'executable aveec la commande <code>java -jar xebia-interview.jar</code>.

La demo sera disponible sur http://localhost:8080/

## Doc & Qualité
le plugin maven-site-plugin et utilisé pour générer les rapports du build sous forme d'un site exposants: 
- Le rapport des tests
- La java doc
- Le rapport du checkstyle
- Les infos du projets
- Le rapport de couverture de code
- Le rapport de l'analyse PMD
- Le report de l'analyze findbug
- Un rapport sur les versions des dependances et des plugins utilisés

le site est localisé https://snilyes.github.io/mowitnow/
## Execution & Traces
Pour une pelouse de largeur 5 sur une longueur 5, une tondeuse initialement situé sur la cellule (2, 2), orienté vers l'Ouest, l'exécution des instructions: DAAGAAGAGA placera la tondeuse à la cellule (1, 3), orienté vers l'Est.
Si dessous les traces d'exécutions:

<pre>
17:17:05.452 [main] DEBUG fr.xebia.mowitnow.mower.Mower - Demarrage Tondeuse (id=0, position=Position(x=2, y=2), orientation=WEST). ...
17:17:05.453 [main] DEBUG fr.xebia.mowitnow.unit.MowerTest - 

 4 |   |   |   |   |   |
-------------------------
 3 |   |   |   |   |   |
-------------------------
 2 |   |   | N |   |   |
-------------------------
 1 |   |   |   |   |   |
-------------------------
 0 |   |   |   |   |   |
-------------------------
   | 0 | 1 | 2 | 3 | 4 |


17:17:05.453 [main] DEBUG fr.xebia.mowitnow.mower.Mower - Execution instruction N° 1 (ROTATE_RIGHT).
17:17:05.453 [main] DEBUG fr.xebia.mowitnow.unit.MowerTest - 

 4 |   |   |   |   |   |
-------------------------
 3 |   |   | N |   |   |
-------------------------
 2 |   |   | - |   |   |
-------------------------
 1 |   |   |   |   |   |
-------------------------
 0 |   |   |   |   |   |
-------------------------
   | 0 | 1 | 2 | 3 | 4 |


17:17:05.453 [main] DEBUG fr.xebia.mowitnow.mower.Mower - Execution instruction N° 2 (FORWARD).
17:17:05.453 [main] DEBUG fr.xebia.mowitnow.unit.MowerTest - 

 4 |   |   | N |   |   |
-------------------------
 3 |   |   | - |   |   |
-------------------------
 2 |   |   | - |   |   |
-------------------------
 1 |   |   |   |   |   |
-------------------------
 0 |   |   |   |   |   |
-------------------------
   | 0 | 1 | 2 | 3 | 4 |


17:17:05.453 [main] DEBUG fr.xebia.mowitnow.mower.Mower - Execution instruction N° 3 (FORWARD).
17:17:05.453 [main] DEBUG fr.xebia.mowitnow.unit.MowerTest - 

 4 |   |   | W |   |   |
-------------------------
 3 |   |   | - |   |   |
-------------------------
 2 |   |   | - |   |   |
-------------------------
 1 |   |   |   |   |   |
-------------------------
 0 |   |   |   |   |   |
-------------------------
   | 0 | 1 | 2 | 3 | 4 |


17:17:05.453 [main] DEBUG fr.xebia.mowitnow.mower.Mower - Execution instruction N° 4 (ROTATE_LEFT).
17:17:05.453 [main] DEBUG fr.xebia.mowitnow.unit.MowerTest - 

 4 |   | W | - |   |   |
-------------------------
 3 |   |   | - |   |   |
-------------------------
 2 |   |   | - |   |   |
-------------------------
 1 |   |   |   |   |   |
-------------------------
 0 |   |   |   |   |   |
-------------------------
   | 0 | 1 | 2 | 3 | 4 |


17:17:05.453 [main] DEBUG fr.xebia.mowitnow.mower.Mower - Execution instruction N° 5 (FORWARD).
17:17:05.454 [main] DEBUG fr.xebia.mowitnow.unit.MowerTest - 

 4 | W | - | - |   |   |
-------------------------
 3 |   |   | - |   |   |
-------------------------
 2 |   |   | - |   |   |
-------------------------
 1 |   |   |   |   |   |
-------------------------
 0 |   |   |   |   |   |
-------------------------
   | 0 | 1 | 2 | 3 | 4 |


17:17:05.454 [main] DEBUG fr.xebia.mowitnow.mower.Mower - Execution instruction N° 6 (FORWARD).
17:17:05.454 [main] DEBUG fr.xebia.mowitnow.unit.MowerTest - 

 4 | S | - | - |   |   |
-------------------------
 3 |   |   | - |   |   |
-------------------------
 2 |   |   | - |   |   |
-------------------------
 1 |   |   |   |   |   |
-------------------------
 0 |   |   |   |   |   |
-------------------------
   | 0 | 1 | 2 | 3 | 4 |


17:17:05.454 [main] DEBUG fr.xebia.mowitnow.mower.Mower - Execution instruction N° 7 (ROTATE_LEFT).
17:17:05.454 [main] DEBUG fr.xebia.mowitnow.unit.MowerTest - 

 4 | - | - | - |   |   |
-------------------------
 3 | S |   | - |   |   |
-------------------------
 2 |   |   | - |   |   |
-------------------------
 1 |   |   |   |   |   |
-------------------------
 0 |   |   |   |   |   |
-------------------------
   | 0 | 1 | 2 | 3 | 4 |


17:17:05.454 [main] DEBUG fr.xebia.mowitnow.mower.Mower - Execution instruction N° 8 (FORWARD).
17:17:05.455 [main] DEBUG fr.xebia.mowitnow.unit.MowerTest - 

 4 | - | - | - |   |   |
-------------------------
 3 | E |   | - |   |   |
-------------------------
 2 |   |   | - |   |   |
-------------------------
 1 |   |   |   |   |   |
-------------------------
 0 |   |   |   |   |   |
-------------------------
   | 0 | 1 | 2 | 3 | 4 |


17:17:05.455 [main] DEBUG fr.xebia.mowitnow.mower.Mower - Execution instruction N° 9 (ROTATE_LEFT).
17:17:05.455 [main] DEBUG fr.xebia.mowitnow.unit.MowerTest - 

 4 | - | - | - |   |   |
-------------------------
 3 | - | E | - |   |   |
-------------------------
 2 |   |   | - |   |   |
-------------------------
 1 |   |   |   |   |   |
-------------------------
 0 |   |   |   |   |   |
-------------------------
   | 0 | 1 | 2 | 3 | 4 |


17:17:05.455 [main] DEBUG fr.xebia.mowitnow.mower.Mower - Execution instruction N° 10 (FORWARD).
17:17:05.455 [main] DEBUG fr.xebia.mowitnow.mower.Mower - Arrêt Tondeuse (id=0, position=Position(x=1, y=3)
</pre>

