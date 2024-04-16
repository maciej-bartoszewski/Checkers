package org.example.checkers.move;

import org.example.checkers.game.Game;
import org.example.checkers.piece.Piece;

import java.util.ArrayList;

public abstract class Move {
    protected MoveValidator moveValidator;
    protected PieceMover pieceMover;
    protected PieceKiller pieceKiller;
    protected KillScanner killScanner;

    public Move(MoveValidator validator, PieceMover mover, PieceKiller killer, KillScanner scanner) {
        this.moveValidator = validator;
        this.pieceMover = mover;
        this.pieceKiller = killer;
        this.killScanner = scanner;
    }

    public abstract boolean isMoveValid(Game game, Piece piece, int colDest, int rowDest);

    public abstract boolean isKillPossible(Game game, Piece piece);

    public abstract void killPiece(Game game, Piece piece, int colDest, int rowDest);

    public abstract void movePiece(Game game, Piece piece, int newCol, int newRow);

    public abstract ArrayList<MoveData> getValidMoves(Game game, Piece piece);

}
