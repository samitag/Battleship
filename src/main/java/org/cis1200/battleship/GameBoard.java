package org.cis1200.battleship;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.LinkedList;


public class GameBoard extends JPanel {

    private Battleship bb; // model for the game
    private JLabel status; // current status text

    private JPanel gameBoard = new JPanel();

    MyButton[][] butGrid = new MyButton[10][10];

    private int shipCount;

    Timer delay;
    Timer delay1;
    Timer delay2;

    private final LinkedList<Move> moves = new LinkedList<>();

    // Game constants
    public static final int BOARD_WIDTH = 1000;
    public static final int BOARD_HEIGHT = 1000;

    class MyButton extends JButton {
        final int i;
        final int j;
        public MyButton(int i, int j) {
            super();
            this.i = i;
            this.j = j;
        }
    }

    /**
     * Initializes the game board.
     */
    public GameBoard(JLabel statusInit) {
        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        status = statusInit; // initializes the status JLabel
        gameBoard.setLayout(new GridLayout(10, 10));
        this.add(gameBoard);
        bb = new Battleship();

        delay = new Timer(1200,e -> {
            updateStatus();
            redrawBoard();
            delay1.start();
        });

        delay.setRepeats(false);

        delay1 = new Timer(1200,e -> {
            bb.cpuTurn();
            moves.add(new Move(bb.getCpuI(), bb.getCpuJ(), bb.getCpuV(), bb.getPlayerI(),
                    bb.getPlayerJ(), bb.getPlayerV()));
            redrawBoard();
            updateStatus();
            repaint();
            delay2.start();
        });
        delay1.setRepeats(false);


        delay2 = new Timer(1200, e -> {
            bb.setPlayer(true);
            updateStatus();
            redrawBoard();
            repaint();
        });
        delay2.setRepeats(false);

    }

    public void redraw(int value, int i, int j, boolean show2s) {
        if (show2s) {
            if (value == 0) {
                butGrid[i][j].setBackground(Color.BLUE);
            } else if (value == 2) {
                butGrid[i][j].setBackground(Color.BLACK);
            }
        } else {
            if (value == 0 || value == 2) {
                butGrid[i][j].setBackground(Color.BLUE);
            }
        }

        if (value == 1) {
            butGrid[i][j].setBackground(Color.LIGHT_GRAY);
        } else if (value == 3) {
            butGrid[i][j].setBackground(Color.RED);
        }
    }

    // draws the player Board during the computer's turn
    // draws the computer's board during the player's turn
    public void redrawBoard() {
        if (bb.getPlayer()) {
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    redraw(bb.getCpuCell(j, i), i, j, false);
                }
            }
        } else if (!bb.getPlayer()) {
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    redraw(bb.getPlayerCell(j, i), i, j, true);
                }
            }
        }
    }

    public void handleMouseClick(int r, int c) {
        if (bb.getBattleState() && bb.getPlayer() &&
                ((bb.getCpuCell(c, r) == 0) || (bb.getCpuCell(c, r) == 2))) {
            bb.playTurn(c, r);
            redrawBoard();
            updateStatus();
            bb.setPlayer(false);
            delay.start();
        }

        if (bb.getSetState()) {
            bb.setPlayer(false);
            if (shipCount == 5) {
                if (bb.set5bit(c, r)) {
                    shipCount--;
                }
                redrawBoard();
            } else if (shipCount == 4) {
                if (bb.set4bit(c, r)) {
                    shipCount--;
                }
                redrawBoard();
            } else if (shipCount == 3) {
                if (bb.set3bit(c, r)) {
                    shipCount--;
                }
                redrawBoard();
            } else if (shipCount == 2) {
                if (bb.set2bit(c, r)) {
                    shipCount--;
                }
                redrawBoard();
            } else if (shipCount == 1) {
                if (bb.set1bit(c, r)) {
                    shipCount--;
                    redrawBoard();
                    battleMessage();
                    bb.setPlayer(true);
                    updateStatus();
                    redrawBoard();
                }
            }
        }
    }



    /**
     * (Re-)sets the game to its initial state.
     */
    public void reset() {
        if (bb.getPlayer()) {
            butGrid = new MyButton[10][10];
            gameBoard.removeAll();
            moves.clear();

            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {

                    MyButton but = new MyButton(i, j);
                    but.setOpaque(true);
                    but.setBackground(new Color(100, 7, 100));
                    but.setPreferredSize(new Dimension(70, 70));
                    but.setBorderPainted(true);
                    but.setBorder(BorderFactory.createRaisedBevelBorder());
                    but.addActionListener(e -> {
                        handleMouseClick(but.i, but.j);
                    });
                    butGrid[i][j] = but;
                    gameBoard.add(but);
                }
            }
            gameBoard.setOpaque(true);
            gameBoard.validate();

            // Enable keyboard focus on the court area. When this component has the
            // keyboard focus, key events are handled by its key listener.
            setFocusable(true);

            bb = new Battleship(); // initializes model for the game
            shipCount = 5;

            bb.setComputerBoard();

            redrawBoard();
            updateStatus();
            setMessage();
            repaint();

            // Makes sure this component has keyboard/mouse focus
            requestFocusInWindow();

        }
    }


    // Adds a pop-up instruction window.
    public void info() {
        JOptionPane.showMessageDialog(gameBoard,
                "Welcome to Battleship! \n " +
                        "In this game, you will play against me, the computer. Click \n " +
                        "on the screen 5 times to place 5 different ships. Your ship will be \n " +
                        "vertical and be placed starting where you click. Then, we will battle \n" +
                        "it out! Click anywhere on the screen to make a hit against the \n " +
                        "computer. Then, you will watch me attempt to make a hit on your \n" +
                        "board! We will go back and forth until one of us wins. If you \n " +
                        "need a break, just click save. If you want to load in a previous \n " +
                        "game, just click on load. Finally, you can undo your previous move \n " +
                        "using the undo button! You really have no excuse for not beating the \n" +
                        "me with this feature...",
                "Instructions",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public void battleMessage() {
        JOptionPane.showMessageDialog(gameBoard, "Entering BattleMode...", "Alert",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public void setMessage() {
        JOptionPane.showMessageDialog(gameBoard, "Set Your Ships.", "Alert",
                JOptionPane.INFORMATION_MESSAGE);
    }


    /**
     * Updates the JLabel to reflect the current state of the game.
     */
    private void updateStatus() {
        if (bb.getSetState()) {
            status.setText("Click on a square to set your ships.");
        } else if (bb.getBattleState()) {
            if (bb.getPlayer()) {
                status.setText("Your Turn" + " \n Opponent Tiles Remaining: " + bb.getCpuShips());
            } else {
                status.setText("Computer's Turn" + " \n Your Tiles Remaining: "
                        + bb.getPlayerShips());
            }

        } else if (bb.getEndState()) {
            int winner = bb.checkWinner();
            if (winner == 1) {
                status.setText("You won! :D");
            } else if (winner == 2) {
                status.setText("You lost :(");
            }
        }

        repaint();
    }

    public void undo() {
        if (bb.getBattleState() && bb.getPlayer() && !moves.isEmpty()) {
            Move prevMove = moves.removeLast();
            bb.setCpuCell(prevMove.getCpuI(), prevMove.getCpuJ(), prevMove.getCpuV());
            bb.setPlayerCell(prevMove.getPlayerI(), prevMove.getPlayerJ(), prevMove.getPlayerV());

            if (prevMove.getPlayerV() == 2) {
                bb.setPlayerShips(bb.getPlayerShips() + 1);
            }

            if (prevMove.getCpuV() == 2) {
                bb.setCpuShips(bb.getCpuShips() + 1);
            }
        }
        redrawBoard();
        updateStatus();
        repaint();
    }

    public void save() {
        if (bb.getBattleState() && bb.getPlayer()) {
            File saved = new File("gameState");
            BufferedWriter bufferedWriter = null;

            // make a file writer
            try {
                bufferedWriter = new BufferedWriter(new FileWriter(saved, false));
            } catch (IOException e) {
                System.out.println("Error: could not save to file");
                return;
            }

            // write all game data to file
            try {
                bufferedWriter.write(bb.getPlayer() + "\n"); // if it is the player's turn
                bufferedWriter.write(bb.getCpuShips() + "\n"); // number of cpu tiles
                bufferedWriter.write(bb.getPlayerShips() + "\n"); //number of player tiles

                for (int i = 0; i < 10; i++) {
                    for (int j = 0; j < 10; j++) {
                        bufferedWriter.write(bb.getCpuCell(j, i) + "\n"); // cpu board values
                    }
                }

                for (int i = 0; i < 10; i++) {
                    for (int j = 0; j < 10; j++) {
                        bufferedWriter.write(bb.getPlayerCell(j, i) + "\n"); // player board
                    }
                }

                for (Move move : moves) {
                    bufferedWriter.write(move.getCpuI() + "\n");
                    bufferedWriter.write(move.getCpuJ() + "\n");
                    bufferedWriter.write(move.getCpuV() + "\n");
                    bufferedWriter.write(move.getPlayerI() + "\n");
                    bufferedWriter.write(move.getPlayerJ() + "\n");
                    bufferedWriter.write(move.getPlayerV() + "\n");
                }

                bufferedWriter.flush();
                bufferedWriter.close();

            } catch (IOException e) {
                System.out.println("Error: could not save to file");
                return;
            }
        }
    }

    public void load() {
        if ((bb.getBattleState() || bb.getSetState()) && bb.getPlayer()) {
            moves.clear();
            BufferedReader reader;
            try {
                reader = new BufferedReader(new FileReader("gameState"));
            } catch (IOException e) {
                System.out.println("Error opening file");
                return;
            }

            try {
                bb.setPlayer(Boolean.parseBoolean(reader.readLine()));
                bb.setCpuShips(Integer.parseInt(reader.readLine()));
                bb.setPlayerShips(Integer.parseInt(reader.readLine()));
                bb.setSetState(false);
                bb.setBattleState(true);
                bb.setEndState(false);

                for (int i = 0; i < 10; i++) {
                    for (int j = 0; j < 10; j++) {
                        bb.setCpuCell(i, j, Integer.parseInt(reader.readLine()));
                    }
                }

                for (int i = 0; i < 10; i++) {
                    for (int j = 0; j < 10; j++) {
                        bb.setPlayerCell(i, j, Integer.parseInt(reader.readLine()));
                    }
                }


                while (true) {
                    String cpuI = reader.readLine();
                    if (cpuI == null) {
                        break;
                    }
                    String cpuJ = reader.readLine();
                    String cpuV = reader.readLine();
                    String playerI = reader.readLine();
                    String playerJ = reader.readLine();
                    String playerV = reader.readLine();

                    moves.add(new Move(Integer.parseInt(cpuI), Integer.parseInt(cpuJ),
                            Integer.parseInt(cpuV), Integer.parseInt(playerI),
                            Integer.parseInt(playerJ), Integer.parseInt(playerV)));
                }

                updateStatus();
                redrawBoard();
                repaint();
            } catch (IOException e) {
                System.out.println("Error: could not read file");
                return;
            }

        }
    }


    /**
     * Returns the size of the game board.
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
    }
}
