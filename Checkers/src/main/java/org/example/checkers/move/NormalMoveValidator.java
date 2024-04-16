package org.example.checkers.move;

import org.example.checkers.game.Game;
import org.example.checkers.piece.Piece;

import java.util.ArrayList;

public class NormalMoveValidator extends PieceMoveValidator {
    @Override
    protected ArrayList<MoveData> getValidMoves(Game game, Piece piece) {
        return game.getNormalMove().getValidMoves(game, piece);
    }
}
