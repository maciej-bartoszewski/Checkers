package org.example.checkers.piece;

import java.io.Serializable;

public enum PieceColor implements Serializable {
    BLACK, WHITE;

    public boolean isWhite() {
        return this == WHITE;
    }

    public boolean isBlack() {
        return this == BLACK;
    }

    public PieceColor getColor() {
        return this;
    }
}
