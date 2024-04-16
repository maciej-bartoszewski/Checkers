package org.example.checkers.move;

import org.example.checkers.game.Game;
import org.example.checkers.piece.Piece;

public class NormalPieceKiller implements PieceKiller {
    @Override
    public boolean killPiece(Game game, Piece piece, int colDest, int rowDest) {
        int rowDirection = (piece.getRow() > rowDest) ? -1 : 1;
        int colDirection = (piece.getCol() > colDest) ? -1 : 1;
        int finishRow = piece.getRow() + (rowDirection * 2);
        int finishCol = piece.getCol() + (colDirection * 2);

        if (game.getNormalKillScanner().isKillPossible(game, piece)) {
            Piece pieceToKill = game.getNormalKillScanner().findPieceToKill(game, piece.getCol(), piece.getRow(), colDest, rowDest);
            if (pieceToKill != null && finishCol == colDest && finishRow == rowDest) {
                game.removePiece(pieceToKill);
                game.setWasKill(true);
                return true;
            }
        }

        game.setWasKill(false);
        return false;
    }
}
