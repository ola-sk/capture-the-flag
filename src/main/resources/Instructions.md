# Assignment Details
Assignment: Capture The Flag
Language: Java
Time: 3 hours

# Task Description
Your task is to implement a simple Capture The Flag game simulation, which also utilizes the rules of Rock Paper Scissors. There are 3 teams: Rocks, Papers and Scissors. Each team has a certain number of players that are scattered across the map (which is a rectangular grid). There are also several flags on the map.

The simulation happens in cycles. During one cycle, each player makes a move depending on its team’s custom logic (ordered by their positions, starting from top left corner, going right, then down). Players can move, fight each other (but not the same team) and capture flags. When a player walks on a flag, he captures it - it gets removed from the map and the player is granted 10 points. Once all the flags have been captured, the game ends and the team with most points wins. The game can also end if all players are dead before capturing all flags.

The general movement pattern of all Players is the following:

If the nearest flag is directly above or below the Player, move up or down respectively
Otherwise move left or right in the direction of the nearest flag
Players can only move one field at a time
If the chosen position is occupied by a teammate, don’t move
However, every team has its own custom rules to this pattern:

Team Rock - represented by letter R.

If the chosen field is occupied by an enemy, fight him
Team Paper - represented by letter P.
If the chosen field is occupied by an enemy, then move in the opposite direction
If that position is also occupied by an enemy, fight him
If moving to the opposite direction would mean walking out of map’s boundaries, then don’t move
Team Scissors - represented by letter S.
If the chosen field is occupied by a Paper, fight him
If the chosen field is occupied by a Rock, then move in the opposite direction
If that position is also occupied by an enemy, fight him
If moving to the opposite direction would mean walking out of map’s boundaries, then don’t move
Flags are represented by the letter F. Empty positions are represented by ..

Fight happens whenever a player attempts to walk on a position occupied by another player from another team. The rules are the following:

Rock kills Scissors
Scissors kill Paper
Paper kills Rock
After one player kills another, he takes his position
Winning a fight grants 5 points
The simulation is deterministic (meaning: there is no randomness). The unit tests are going to check the game’s behavior with a set of predefined scenarios. If these tests don’t pass, it means there are some bugs or missing features in the game’s logic.
Key features that are going to be checked by unit test classes:

ActorFactoryTest - actor creation, actor creation based on given character
PlayerTest - simulating fights, getting move directions based on nearest flag position
GameUtilTest - utils methods oriented around actors, directions, vectors
GameTest - player’s behavior, movement, fight, game simulation conditions
GameMapTest - map creation, map-to-string generating, getting actors by positions, getting positions of actors
ScoreboardTest - getting ranked players, best players, for given state of the game
Hints:
For string creation tasks (like GameMap.toString()) it is recommended to use StringBuilder class or simple string concatenation. Also, when appending a new line, useSystem.lineSeparator(), using just \n or \r might cause the unit tests to fail
For splitting a string into lines, it is recommended to useString[] lines = charMatrix.split("\r\n|\r|\n");
All methods that are checked by unit tests and require implementation by the students, are marked with throw new RuntimeException("Method not implemented!"); code line
The game’s logic uses the standard coordinate system - (x, y). However, due to the fact how text-game simulation works and how the Console prints lines, not columns, the actorsMatrix 2D array inside GameMap class inverts the coordinates order - [y, x]. Also, the Y axis is inverted - the lower the number, the upper the position. Watch out for that.
Positions and vectors - are based on a Vector class in util package
