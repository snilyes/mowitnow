Narrative:
I want the xebia test to pass

Scenario: Xebia example
Given a 5 by 5 lawn
And a mower at position 1 2 facing NORTH with instructions GAGAGAGAA
And a mower at position 3 3 facing EAST with instructions AADAADADDA
When the mowers execute their instructions
Then the 1st mower should be at position 1 3 facing NORTH
And the 2nd mower should be at position 5 1 facing EAST