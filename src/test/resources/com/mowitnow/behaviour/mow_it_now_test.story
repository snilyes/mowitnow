Narrative:
I want to be able to mow a lawn

Scenario: The mower can be programmed to traverse the entire surface of the lawn
Given a 1 by 1 lawn
And a mower at position 0 0 facing NORTH
When the mower executes instructions ADADA
Then the lawn should be mowed

Scenario: If the position after movement is out of the lawn, a mower does not move, retains its orientation and processes the next command
Given a 3 by 3 lawn
And a mower at position 3 3 facing NORTH
When the mower executes instructions AGA
Then the mower should be at position 2 3 facing WEST

Scenario: A mower is given instructions to execute
Given a 5 by 5 lawn
And a mower at position 1 2 facing NORTH
When the mower executes instructions GAGAGAGAA
Then the mower should be at position 1 3 facing NORTH

Scenario: A mower is given instructions to execute 2
Given a 5 by 5 lawn
And a mower at position 3 3 facing EAST
When the mower executes instructions AADAADADDA
Then the mower should be at position 5 1 facing EAST 

Scenario: A mower can not be placed outside the lawn
Given a 5 by 5 lawn
And a mower at position 6 3 facing EAST
Then an error message should appear which says: mower 1 should not be placed outside the lawn