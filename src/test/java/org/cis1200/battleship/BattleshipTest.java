package org.cis1200.battleship;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * You can use this file (and others) to test your
 * implementation.
 */

public class BattleshipTest {

    /*----playTurn Tests----*/
    //Note: playTurn is only called by controller when input is within bounds and on un-hit spaces.
    @Test
    public void playTurnWater() {
        Battleship bb = new Battleship();
        bb.setComputerBoard();
        bb.set1bit(4, 4);

        boolean isWater = false;
        int indexI = 0;
        int indexJ = 0;

        while (!isWater) {
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    if (bb.getCpuCell(i, j) == 0) {
                        isWater = true;
                        indexI = i;
                        indexJ = j;
                    }
                }
            }
        } // make sure player is hitting water on the board


        bb.setPlayer(true);
        bb.playTurn(indexJ, indexI);
        assertEquals(1, bb.getCpuCell(indexJ, indexI)); // water turned to missed
        assertFalse(bb.getSetState()); // states not changing
        assertTrue(bb.getBattleState());
        assertFalse(bb.getEndState());
        assertEquals(0, bb.checkWinner()); //no winner
        assertEquals(15, bb.getCpuShips()); // ship counts don't change
        assertEquals(15, bb.getPlayerShips());
    }

    @Test
    public void playTurnShip() {
        Battleship bb = new Battleship();
        bb.setComputerBoard();
        bb.set1bit(4, 4);

        boolean isShip = false;
        int indexI = 0;
        int indexJ = 0;

        while (!isShip) {
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    if (bb.getCpuCell(i, j) == 2) {
                        isShip = true;
                        indexI = i;
                        indexJ = j;
                    }
                }
            }
        } // make sure to hit ship

        bb.setPlayer(true);
        bb.playTurn(indexI, indexJ);
        assertEquals(3, bb.getCpuCell(indexI, indexJ)); // ship turned to rubble
        assertFalse(bb.getSetState()); // states unchanged
        assertTrue(bb.getBattleState());
        assertFalse(bb.getEndState());
        assertEquals(14, bb.getCpuShips()); // cpu ships decremented but not player ships
        assertEquals(15, bb.getPlayerShips());
    }

    @Test
    public void playTurnToEnd() {
        Battleship bb = new Battleship();
        bb.setComputerBoard();
        bb.set1bit(4, 4);

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (bb.getCpuCell(i, j) == 2) {
                    bb.playTurn(i, j);
                    bb.cpuTurn();
                    bb.setPlayer(true);
                }
            }
        }
        assertFalse(bb.getSetState()); // state's changed to end
        assertFalse(bb.getBattleState());
        assertTrue(bb.getEndState());
        assertEquals(1, bb.checkWinner()); // player wins
        assertEquals(0, bb.getCpuShips()); // no cpu ships left
    }

    /*----CPU Turn Test---*/
    @Test
    public void cpuTurnHitAll() {
        Battleship bb = new Battleship();
        bb.setComputerBoard();
        bb.set5bit(0, 0);
        bb.set4bit(1, 1);
        bb.set3bit(2, 2);
        bb.set2bit(3, 3);
        bb.set1bit(4, 4);
        bb.setPlayer(false);

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                bb.cpuTurn();
            }
        }

        assertEquals(2, bb.checkWinner()); // cpu wins because player makes no turns
        assertEquals(0, bb.getPlayerShips()); //no player ships left
        assertFalse(bb.getSetState()); // states changed
        assertFalse(bb.getBattleState());
        assertTrue(bb.getEndState());
    }


    /*---SetComputerBoardTest---*/
    @Test
    public void setComputerBoard() {
        Battleship bb = new Battleship();
        bb.setComputerBoard();
        int shipCount = 0;
        int waterCount = 0;
        int missedCount = 0;
        int rubbleCount = 0;

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                int test = bb.getCpuCell(i, j);
                if (test == 0) {
                    waterCount++;
                } else if (test == 1) {
                    missedCount++;
                } else if (test == 2) {
                    shipCount++;
                } else if (test == 3) {
                    rubbleCount++;
                }
            }
        }

        assertEquals(15, shipCount); //making sure that there are 15 ship tiles
        assertEquals(85, waterCount); // exactly 85 water tiles
        assertEquals(0, rubbleCount); // no rubble or missed tiles during setup
        assertEquals(0, missedCount);
    }

    /* Helper functions for setting player board Tests. The logic for
     *  set5bit, set4bit, set3bit, set2bit, and set1bit are the same.
     */

    @Test
    public void set2bitTrue() {
        Battleship bb = new Battleship();
        bb.setComputerBoard();
        assertTrue(bb.set2bit(2,2));
        assertEquals(2, bb.getPlayerCell(2, 2)); //successful setup
        assertEquals(2, bb.getPlayerCell(2, 3));
        assertEquals(0, bb.getPlayerCell(2, 4));

    }

    @Test
    public void set2bitOnTop() {
        Battleship bb = new Battleship();
        bb.setComputerBoard();
        assertTrue(bb.set3bit(2,2));
        assertFalse(bb.set2bit(2,2)); // can't place directly on top
    }

    @Test
    public void set2bitHalfOverlap() {
        Battleship bb = new Battleship();
        bb.setComputerBoard();
        assertTrue(bb.set3bit(2,2));
        assertFalse(bb.set2bit(2,1)); // can't let bottom half of ship overlap other ship
    }

    @Test
    public void set4bitOutOfBounds() {
        Battleship bb = new Battleship();
        bb.setComputerBoard();
        assertFalse(bb.set4bit(2,8));  // can't let part of the ship be out of bounds
    }
}
