package org.example.checkers.move;

import org.example.checkers.game.Game;
import org.example.checkers.piece.Piece;

public interface PieceMover {
    void movePiece(Game game, Piece piece, int newCol, int newRow);
}
