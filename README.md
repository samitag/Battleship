===================
=: Core Concepts :=
===================

  1. 2D Arrays. I used them to implement a the player's board and the computer boards. They were
       appropriate to use because the game board required me to keep track of a location (index)
       and the value at that location (the status of the tile: water, ship, missed hit, or rubble).
       A 2D array allows one to access anywhere in the array with an index and returns the value
       stored there. 2D arrays also mimic the layout of the grid in Battleship because it has
       rows and columns.


  2. Collections and Maps. I used a LinkedList, which is a Collection. I used it to store instances
  of the Move class, which stored the location and values of all the previous moves. I used this
  to implement an undo feature. A LinkedList is appropriate because it can add to the end of the
  list, which is appropriate because moves are sequential. It also had the ability to remove from
  the end of the list efficiently because it has access to the end of the list without having
  to traverse through the whole list. I removed from the end of the list to remove previous moves
  and update the game board to reflect that.

  3. File I/O. I used file I/O to save the game state to a file or load in a previously saved
  game file. This was appropriate because I could write to the file the variables I needed that
  stored the state of the game. Then I could just read the file back and set the game's state
  equal to those variables line by line. It also allowed me to load in the same saved game over
  any new game or after I have closed and reopened the game.

  4. JUnit Testable Component: I made sure that the logic of my game occurred in the model
  independently from the GUI, which was Battleship.java. I used JUnit to test various outcomes
  from the functions in my model, which were setting up the boards, making moves for the player and
  computer, and dealing with the game states starting and ending. Using JUnit also allowed me to
  test various functions and then verify that the rest of the game state was changed or unchanged,
  depending on the situation.


=========================
=:   Implementation    :=
=========================

  GameBoard.java is the controller. It handles the mouseclick events and redraws the board according
  to what state the model is in (setup, battle, end). It also redraws the main grid where the
  game is being played and handles the functionality of the buttons and status.

  RunBattleship.java displays the main part of the GUI. It has an area that displays the grid,
  as well as a control bar and a status bar. The control bar has buttons that call functions
  handled by GameBoard.java, and the status bar is frequently updated to reflect the game state.

  BattleShip.java is the main model of the game and it handles all of the logic. It handles the
  game states, the computer turn, the player turn, the winners, and setting up the computer board
  and the player's ships.

  Move.java creates a move object, which stores 6 integers and has function to access them. The
  integers are the indexes and the values in the 2D array where previous moves have been made.
  This made it easy to store all of these values together in one object of the linked list and
  access them all at once when undo was hit.

  BattleshipTest.java contains JUnit tests for my functions in Battleship.java.


========================
=: External Resources :=
========================

    https://docs.oracle.com/javase/tutorial/index.html for learning the timers and the
    windows displaying the messages.

