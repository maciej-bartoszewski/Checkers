package org.example.checkers.move;

import org.example.checkers.game.Game;
import org.example.checkers.piece.Piece;

import java.util.ArrayList;

import static org.example.checkers.game.Game.HEIGHT;
import static org.example.checkers.game.Game.WIDTH;

public abstract class PieceMoveValidator implements MoveValidator {

    @Override
    public boolean isMoveValid(Game game, Piece piece, int colDest, int rowDest) {
        if (colDest >= WIDTH || colDest < 0 || rowDest < 0 || rowDest >= HEIGHT) return false;

        if (piece.getColor() != game.getTurn()) return false;
        if (game.getGamemode().isOnline() && piece.getColor() != game.getPlayerColor()) return false;

        if (piece.getColor().isWhite()) {
            if (!piece.getCanKill() && game.getCanWhiteKill()) return false;
        }
        if (piece.getColor().isBlack()) {
            if (!piece.getCanKill() && game.getCanBlackKill()) return false;
        }

        ArrayList<MoveData> validMoves = getValidMoves(game, piece);
        for (MoveData move : validMoves) {
            if (move.getNewCol() == colDest && move.getNewRow() == rowDest) {
                if (piece.getCanKill() && move.isKill()) return true;
                if (!piece.getCanKill()) return true;
            }
        }

        return false;
    }

    protected abstract ArrayList<MoveData> getValidMoves(Game game, Piece piece);
}
