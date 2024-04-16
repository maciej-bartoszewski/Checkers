package org.example.checkers.move;

import org.example.checkers.piece.Piece;

public class MoveData {
    private final Piece piece;
    private final int newCol;
    private final int newRow;
    private final boolean kill;

    public MoveData(Piece piece, int newCol, int newRow, boolean kill) {
        this.piece = piece;
        this.newCol = newCol;
        this.newRow = newRow;
        this.kill = kill;
    }

    public Piece getPiece() {
        return piece;
    }

    public int getNewCol() {
        return newCol;
    }

    public int getNewRow() {
        return newRow;
    }

    public boolean isKill() {
        return kill;
    }
}
