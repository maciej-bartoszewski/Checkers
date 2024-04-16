package org.example.checkers.piece;

import java.io.Serializable;

public enum PieceType implements Serializable {
    NORMAL, QUEEN;

    public boolean isNormal() {
        return this == NORMAL;
    }

    public boolean isQueen() {
        return this == QUEEN;
    }
}
