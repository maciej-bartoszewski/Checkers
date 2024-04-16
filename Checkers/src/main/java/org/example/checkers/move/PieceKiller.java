package org.example.checkers.move;

import org.example.checkers.game.Game;
import org.example.checkers.piece.Piece;

public interface PieceKiller {
    boolean killPiece(Game game, Piece piece, int colDest, int rowDest);
}
