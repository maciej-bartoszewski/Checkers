package org.example.checkers.move;

import org.example.checkers.game.Game;
import org.example.checkers.piece.Piece;

public interface KillScanner  {
    boolean isKillPossible(Game game, Piece piece);

    Piece findPieceToKill(Game game, int col, int row, int colDest, int rowDest);
}
