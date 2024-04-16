package org.example.checkers.move;

import org.example.checkers.game.Game;
import org.example.checkers.piece.Piece;

public class QueenPieceKiller implements PieceKiller {
    @Override
    public boolean killPiece(Game game, Piece piece, int colDest, int rowDest) {
        Piece pieceToKill = game.getQueenKillScanner().findPieceToKill(game, piece.getCol(), piece.getRow(), colDest, rowDest);
        if (pieceToKill != null) {
            Piece checkingPiece = game.getQueenKillScanner().findPieceToKill(game, colDest, rowDest, piece.getCol(), piece.getRow());
            if (pieceToKill.equal(checkingPiece)) {
                game.removePiece(pieceToKill);
                game.setWasKill(true);
                return true;
            }
        }

        game.setWasKill(false);
        return false;
    }
}