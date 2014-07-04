Narrative:
Je veux programmer les tondeuses à partir d'un fichier

Scenario: Fichier valide
Given le fichier "classpath:data.txt"
When tondre la pelouse
Then la tondeuse 1 doit etre postionnee au (1, 2) et orientee vers NORD
And la tondeuse 2 doit etre postionnee au (4, 0) et orientee vers EST