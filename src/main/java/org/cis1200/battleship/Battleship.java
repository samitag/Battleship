package org.cis1200.battleship;

public class Battleship {

    private int[][] playerBoard;
    private int[][] computerBoard;
    private boolean player;
    private boolean setUpMode;
    private boolean battleMode;
    private boolean endMode;
    private int cpuShips;
    private int playerShips;

    private int cpuI;
    private int cpuJ;
    private int cpuV;

    private int playerI;
    private int playerJ;
    private int playerV;


    /**
     * Constructor sets up game state.
     */
    public Battleship() {
        reset();
    }

    /**
     * reset (re-)sets the game state to start a new game.
     */
    public void reset() {
        playerBoard = new int[10][10];
        computerBoard = new int[10][10];
        player = true;
        setUpMode = true;
        battleMode = false;
        endMode = false;
        playerShips = 15;
        cpuShips = 15;
    }

    /**
     * playTurn allows players to play a turn. Returns true if the move is
     * successful. The move is successful if the move hits water (0) or ship (2).
     * Returns false if a player has unsuccessful move. The move is unsuccessful if
     * the move hits an already attempted miss (1) or an already successful hit, rubble (3).
     * If the turn is successful and the game has not ended, the computerMode is turned on.
     * If the turn is unsuccessful or the game has ended, computerMode is off.
     *
     * @param c column to play in
     * @param r row to play in
     */
    public void playTurn(int c, int r) {
        cpuI = r;
        cpuJ = c;
        cpuV = computerBoard[r][c];

        if (computerBoard[r][c] == 0) {
            computerBoard[r][c] = 1;
        }

        if (computerBoard[r][c] == 2) {
            computerBoard[r][c] = 3;
            cpuShips--;
        }

        if (checkWinner() != 0) {
            battleMode = false;
            endMode = true;
        }
    }

    /*
     * cpuTurn completes one computer turn but updating the player board.
     * It generates a random index on the playerBoard and makes sure that the index has not
     * already been hit before. Then it sets a ship (2) to rubble (2) or water (0) to missed (2)
     * on the player board.
     * It will then check that there is no current winner and change the player status back to true.
     * Which means that it is the player turn.
     */
    public void cpuTurn() {
        if (battleMode && !player) {
            int currI = ((int) (Math.random() * 10));
            int currJ = ((int) (Math.random() * 10));

            while (playerBoard[currI][currJ] == 1 || playerBoard[currI][currJ] == 3) {
                currI = ((int) (Math.random() * 10));
                currJ = ((int) (Math.random() * 10));
            }

            playerI = currI;
            playerJ = currJ;
            playerV = playerBoard[currI][currJ];

            if (playerBoard[currI][currJ] == 2) {
                playerBoard[currI][currJ] = 3;
                playerShips--;
            } else if (playerBoard[currI][currJ] == 0) {
                playerBoard[currI][currJ] = 1;
            }

            if (checkWinner() != 0) {
                battleMode = false;
                endMode = true;
            }


        }
    }

    /**
     * checkWinner checks whether the game has reached a win condition.
     * checkWinner only looks for horizontal wins.
     *
     * @return 0 if nobody has won yet, 1 if player has won, and 2 if computer
     *         has won
     */
    public int checkWinner() {
        boolean playerWon = true;
        boolean computerWon = true;

        // check computerBoard
        for (int i = 0; i < computerBoard.length; i++) {
            for (int j = 0; j < computerBoard[i].length; j++) {
                if (computerBoard[i][j] == 2) {
                    playerWon = false;
                }
            }
        }

        // check playerBoard
        for (int i = 0; i < playerBoard.length; i++) {
            for (int j = 0; j < playerBoard[i].length; j++) {
                if (playerBoard[i][j] == 2) {
                    computerWon = false;
                }
            }
        }

        if (playerWon) {
            battleMode = false;
            endMode = true;
            return 1;
        } else if (computerWon) {
            battleMode = false;
            endMode = true;
            return 2;
        } else {
            battleMode = true;
            endMode = false;
            return 0;
        }
    }

    // Sets up a board with new ship configuration.
    public void setComputerBoard() {
        if (setUpMode) {
            //set 5 bit ship
            int jIndex5 = ((int) (Math.random() * 6));
            int jIndex5row = ((int) (Math.random() * 2));
            int count5 = 0;
            while (count5 < 5) {
                computerBoard [jIndex5row][jIndex5] = 2;
                jIndex5++;
                count5++;
            }

            //set 4 bit ship
            int jIndex4 = ((int) (Math.random() * 7));
            int jIndex4row = ((int) (Math.random() * 2)) + 2;
            int count4 = 0;
            while (count4 < 4) {
                computerBoard [jIndex4row][jIndex4] = 2;
                jIndex4++;
                count4++;
            }

            //set 3 bit ship
            int jIndex3 = ((int) (Math.random() * 8));
            int jIndex3row = ((int) (Math.random() * 2)) + 4;
            int count3 = 0;
            while (count3 < 3) {
                computerBoard [jIndex3row][jIndex3] = 2;
                jIndex3++;
                count3++;
            }

            //set 2 bit ship
            int jIndex2 = ((int) (Math.random() * 9));
            int jIndex2row = ((int) (Math.random() * 2)) + 6;
            int count2 = 0;
            while (count2 < 2) {
                computerBoard [jIndex2row][jIndex2] = 2;
                jIndex2++;
                count2++;
            }

            //set 1 bit ship
            int jIndex1 = ((int) (Math.random() * 10));
            int jIndex1row = ((int) (Math.random() * 2)) + 8;
            computerBoard [jIndex1row][jIndex1] = 2;

        }
    }

    /*** Helper functions to set player's ship. Takes in index and creates a horizontal ship
    of a certain length. **/

    // set 5 bit
    public boolean set5bit(int x, int y) {
        if (playerBoard[y][x] == 0 && y < 6 && playerBoard[y + 4][x] == 0) {
            int bit = 0;
            while (bit < 5) {
                playerBoard[y][x] = 2;
                y++;
                bit++;
            }
            return true;
        }
        return false;
    }

    // set 4 bit
    public boolean set4bit(int x, int y) {
        if (playerBoard[y][x] == 0 && y < 7 && playerBoard[y + 3][x] == 0) {
            int bit = 0;
            while (bit < 4) {
                playerBoard[y][x] = 2;
                y++;
                bit++;
            }
            return true;
        }
        return false;
    }

    // set 3 bit
    public boolean set3bit(int x, int y) {
        if (playerBoard[y][x] == 0 && y < 8 && playerBoard[y + 2][x] == 0) {
            int bit = 0;
            while (bit < 3) {
                playerBoard[y][x] = 2;
                y++;
                bit++;
            }
            return true;
        }
        return false;
    }

    // set 2 bit
    public boolean set2bit(int x, int y) {
        if (playerBoard[y][x] == 0 && y < 9 && playerBoard[y + 1][x] == 0) {
            int bit = 0;
            while (bit < 2) {
                playerBoard[y][x] = 2;
                y++;
                bit++;
            }
            return true;
        }
        return false;
    }

    // set 1 bit
    public boolean set1bit(int x, int y) {
        if (playerBoard[y][x] == 0 && y < 7) {
            playerBoard[y][x] = 2;
            setUpMode = false;
            battleMode = true;
            return true;
        }
        return false;
    }



    /* ------START OF GETTERS AND SETTERS----- */
    /**
     *getPlayerCell is a getter for the contents of the cell specified by the method
     * arguments.
     *
     * @param c column to retrieve
     * @param r row to retrieve
     * @return an integer denoting the contents of the corresponding cell on the
     *         game board. 0 = water, 1 = missed, 2 = ship, 3 = rubble
     */
    public int getPlayerCell(int c, int r) {
        return playerBoard[r][c];
    }

    /**
     *getCpuCell is a getter for the contents of the cell specified by the method
     * arguments.
     *
     * @param c column to retrieve
     * @param r row to retrieve
     * @return an integer denoting the contents of the corresponding cell on the
     *         game board. 0 = water, 1 = missed, 2 = ship, 3 = rubble
     */
    public int getCpuCell(int c, int r) {
        return computerBoard[r][c];
    }


    //getter for if it is the player's turn
    public boolean getPlayer() {
        return player;
    }

    //setter to set if it is the player's turn
    public void setPlayer(boolean state) {
        player = state;
    }

    // getter for if the battleState is on
    public boolean getBattleState() {
        return battleMode;
    }

    // getter for if the endState is on
    public boolean getEndState() {
        return endMode;
    }

    //// getter for if the setState is on
    public boolean getSetState() {
        return setUpMode;
    }

    //getter for the number of intact player tiles remaining
    public int getPlayerShips() {
        return playerShips;
    }

    //getter for the number of intact computer tiles remaining
    public int getCpuShips() {
        return cpuShips;
    }

    //setter for the number of intact player tiles remaining
    public void setPlayerShips(int i) {
        playerShips = i;
    }

    //setter for the number of intact computer tiles remaining
    public void setCpuShips(int i) {
        cpuShips = i;
    }

    public void setPlayerCell(int i, int j, int v) {
        playerBoard[i][j] = v;
    }

    public void setCpuCell(int i, int j, int v) {
        computerBoard[i][j] = v;
    }

    //setter for set-up mode
    public void setSetState(boolean condition) {
        setUpMode = condition;
    }

    //setter for battle mode
    public void setBattleState(boolean condition) {
        battleMode = condition;
    }

    //setter for end mode
    public void setEndState(boolean condition) {
        endMode = condition;
    }

    public int getCpuI() {
        return cpuI;
    }

    public int getCpuJ() {
        return cpuJ;
    }

    public int getCpuV() {
        return cpuV;
    }

    public int getPlayerI() {
        return playerI;
    }

    public int getPlayerJ() {
        return playerJ;
    }

    public int getPlayerV() {
        return playerV;
    }

    /* ------END OF GETTERS AND SETTERS----- */

}
