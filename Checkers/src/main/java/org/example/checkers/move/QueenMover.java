package org.example.checkers.move;

import org.example.checkers.game.Game;
import org.example.checkers.piece.Piece;

public class QueenMover implements PieceMover {
    @Override
    public void movePiece(Game game, Piece piece, int newCol, int newRow) {
        piece.setCol(newCol);
        piece.setRow(newRow);
    }
}
