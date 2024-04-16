package org.example.checkers.move;

import org.example.checkers.game.Game;
import org.example.checkers.piece.Piece;

public class NormalMover implements PieceMover {
    @Override
    public void movePiece(Game game, Piece piece, int newCol, int newRow) {
        piece.setCol(newCol);
        piece.setRow(newRow);

        if (piece.getColor().isBlack() && piece.getRow() == 7 && piece.getPieceType().isNormal()) {
            game.createQueen(piece);
            game.setWasKill(false);
        }
        if (piece.getColor().isWhite() && piece.getRow() == 0 && piece.getPieceType().isNormal()) {
            game.createQueen(piece);
            game.setWasKill(false);
        }
    }
}
