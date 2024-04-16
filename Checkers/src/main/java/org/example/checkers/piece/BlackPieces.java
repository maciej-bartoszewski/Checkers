package org.example.checkers.piece;

import org.example.checkers.game.Game;
import org.example.checkers.move.*;

import java.util.ArrayList;

public class BlackPieces {
    private final Game game;
    private final MoveValidator normalMoveValidator;
    private final PieceMover normalMover;
    private final PieceKiller normalPieceKiller;
    private final KillScanner normalKillScanner;
    private final ArrayList<Piece> blackPieces = new ArrayList<>();

    public BlackPieces(Game game, MoveValidator normalMoveValidator, PieceMover normalMover, PieceKiller normalPieceKiller, KillScanner normalKillScanner) {
        this.game = game;
        this.normalKillScanner = normalKillScanner;
        this.normalMover = normalMover;
        this.normalPieceKiller = normalPieceKiller;
        this.normalMoveValidator = normalMoveValidator;
    }

    public ArrayList<Piece> createUpBlackPieces() {
        blackPieces.clear();

        blackPieces.add(new NormalPiece(game, 1, 0, PieceType.NORMAL, PieceColor.BLACK, normalMoveValidator, normalMover, normalPieceKiller, normalKillScanner));
        blackPieces.add(new NormalPiece(game, 3, 0, PieceType.NORMAL, PieceColor.BLACK, normalMoveValidator, normalMover, normalPieceKiller, normalKillScanner));
        blackPieces.add(new NormalPiece(game, 5, 0, PieceType.NORMAL, PieceColor.BLACK, normalMoveValidator, normalMover, normalPieceKiller, normalKillScanner));
        blackPieces.add(new NormalPiece(game, 7, 0, PieceType.NORMAL, PieceColor.BLACK, normalMoveValidator, normalMover, normalPieceKiller, normalKillScanner));

        blackPieces.add(new NormalPiece(game, 0, 1, PieceType.NORMAL, PieceColor.BLACK, normalMoveValidator, normalMover, normalPieceKiller, normalKillScanner));
        blackPieces.add(new NormalPiece(game, 2, 1, PieceType.NORMAL, PieceColor.BLACK, normalMoveValidator, normalMover, normalPieceKiller, normalKillScanner));
        blackPieces.add(new NormalPiece(game, 4, 1, PieceType.NORMAL, PieceColor.BLACK, normalMoveValidator, normalMover, normalPieceKiller, normalKillScanner));
        blackPieces.add(new NormalPiece(game, 6, 1, PieceType.NORMAL, PieceColor.BLACK, normalMoveValidator, normalMover, normalPieceKiller, normalKillScanner));


        blackPieces.add(new NormalPiece(game, 1, 2, PieceType.NORMAL, PieceColor.BLACK, normalMoveValidator, normalMover, normalPieceKiller, normalKillScanner));
        blackPieces.add(new NormalPiece(game, 3, 2, PieceType.NORMAL, PieceColor.BLACK, normalMoveValidator, normalMover, normalPieceKiller, normalKillScanner));
        blackPieces.add(new NormalPiece(game, 5, 2, PieceType.NORMAL, PieceColor.BLACK, normalMoveValidator, normalMover, normalPieceKiller, normalKillScanner));
        blackPieces.add(new NormalPiece(game, 7, 2, PieceType.NORMAL, PieceColor.BLACK, normalMoveValidator, normalMover, normalPieceKiller, normalKillScanner));

        return blackPieces;
    }
}
