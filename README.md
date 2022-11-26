# Spelling-Bee-Project
This is Spelling Bee Project implemented using Java Swing and SQL.
The program allows the user to play a Spelling Bee Game similar to New York Time's Spelling Bee, with the user making as many words as possible using the given, randomly generted characters.
The user's results are uploaded to a SQL database along with their name, which are then sorted by the LeaderBoard and UserScoreboard Classes.
These scores can then be accessed by the LeaderBoard and UserScore classes to display high scores in a JTable, with LeaderBoard displaying high scores for all users, and UserScore displaying the inputted user's scores.
The Query and Test classes are used to access the SQL database, and have been modified to work with the database for this Project.
The HiveGame classes hold the Java Swing implementation for the game, including a HashSet to store English words from the words.txt file.
The program is initated through the MainMenu class.
