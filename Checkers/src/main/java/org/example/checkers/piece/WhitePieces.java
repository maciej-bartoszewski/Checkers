package org.example.checkers.piece;

import org.example.checkers.game.Game;
import org.example.checkers.move.KillScanner;
import org.example.checkers.move.MoveValidator;
import org.example.checkers.move.PieceKiller;
import org.example.checkers.move.PieceMover;

import java.util.ArrayList;

public class WhitePieces {
    private final Game game;
    private final MoveValidator normalMoveValidator;
    private final PieceMover normalMover;
    private final PieceKiller normalPieceKiller;
    private final KillScanner normalKillScanner;
    private final ArrayList<Piece> whitePieces = new ArrayList<>();

    public WhitePieces(Game game, MoveValidator normalMoveValidator, PieceMover normalMover, PieceKiller normalPieceKiller, KillScanner normalKillScanner) {
        this.game = game;
        this.normalKillScanner = normalKillScanner;
        this.normalMover = normalMover;
        this.normalPieceKiller = normalPieceKiller;
        this.normalMoveValidator = normalMoveValidator;
    }

    public ArrayList<Piece> setUpWhitePieces() {
        whitePieces.clear();

        whitePieces.add(new NormalPiece(game, 0, 5, PieceType.NORMAL, PieceColor.WHITE, normalMoveValidator, normalMover, normalPieceKiller, normalKillScanner));
        whitePieces.add(new NormalPiece(game, 2, 5, PieceType.NORMAL, PieceColor.WHITE, normalMoveValidator, normalMover, normalPieceKiller, normalKillScanner));
        whitePieces.add(new NormalPiece(game, 4, 5, PieceType.NORMAL, PieceColor.WHITE, normalMoveValidator, normalMover, normalPieceKiller, normalKillScanner));
        whitePieces.add(new NormalPiece(game, 6, 5, PieceType.NORMAL, PieceColor.WHITE, normalMoveValidator, normalMover, normalPieceKiller, normalKillScanner));

        whitePieces.add(new NormalPiece(game, 1, 6, PieceType.NORMAL, PieceColor.WHITE, normalMoveValidator, normalMover, normalPieceKiller, normalKillScanner));
        whitePieces.add(new NormalPiece(game, 3, 6, PieceType.NORMAL, PieceColor.WHITE, normalMoveValidator, normalMover, normalPieceKiller, normalKillScanner));
        whitePieces.add(new NormalPiece(game, 5, 6, PieceType.NORMAL, PieceColor.WHITE, normalMoveValidator, normalMover, normalPieceKiller, normalKillScanner));
        whitePieces.add(new NormalPiece(game, 7, 6, PieceType.NORMAL, PieceColor.WHITE, normalMoveValidator, normalMover, normalPieceKiller, normalKillScanner));

        whitePieces.add(new NormalPiece(game, 0, 7, PieceType.NORMAL, PieceColor.WHITE, normalMoveValidator, normalMover, normalPieceKiller, normalKillScanner));
        whitePieces.add(new NormalPiece(game, 2, 7, PieceType.NORMAL, PieceColor.WHITE, normalMoveValidator, normalMover, normalPieceKiller, normalKillScanner));
        whitePieces.add(new NormalPiece(game, 4, 7, PieceType.NORMAL, PieceColor.WHITE, normalMoveValidator, normalMover, normalPieceKiller, normalKillScanner));
        whitePieces.add(new NormalPiece(game, 6, 7, PieceType.NORMAL, PieceColor.WHITE, normalMoveValidator, normalMover, normalPieceKiller, normalKillScanner));

        return whitePieces;
    }
}
