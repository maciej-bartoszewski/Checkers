package org.example.checkers.move;

import org.example.checkers.game.Game;
import org.example.checkers.piece.Piece;

import java.util.ArrayList;

public class QueenMoveValidator extends PieceMoveValidator {
    @Override
    protected ArrayList<MoveData> getValidMoves(Game game, Piece piece) {
        return game.getQueenMove().getValidMoves(game, piece);
    }
}
