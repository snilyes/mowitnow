Narrative:
Je veux controler le deplacement d'une tondeuse sur une pelouse 

Scenario: Deplacement de la tondeuse sur une pelouse
Given une pelouse de $largeur sur $longueur
And une tondeuse coordonnee au ($x1, $y1) et orientee vers $o1
When la tondeuse execute les instructions suivantes : $instructions
Then la tondeuse doit etre postionnee au ($x2, $y2) et orientee vers $o2

Examples:     
|largeur|longueur|x1|y1|o1|instructions|x2|y2|o2|
|3|3|1|2|NORD|DA|2|2|EST|
|3|3|1|2|NORD|GGGG|1|2|NORD|