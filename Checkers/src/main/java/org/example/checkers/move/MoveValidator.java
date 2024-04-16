package org.example.checkers.move;

import org.example.checkers.game.Game;
import org.example.checkers.piece.Piece;


public interface MoveValidator {
    boolean isMoveValid(Game game, Piece piece, int colDest, int rowDest);
}
