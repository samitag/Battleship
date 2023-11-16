package org.cis1200.battleship;

public class Move {
    private int cpuI;
    private int cpuJ;
    private int cpuV;

    private int playerI;
    private int playerJ;
    private int playerV;

    public Move(int ci, int cj, int cv, int pi, int pj, int pv) {
        cpuI = ci;
        cpuJ = cj;
        cpuV = cv;
        playerI = pi;
        playerJ = pj;
        playerV = pv;
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
}
