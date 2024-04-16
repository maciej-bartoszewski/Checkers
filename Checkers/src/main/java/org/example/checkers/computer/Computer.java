package org.example.checkers.computer;

import javafx.application.Platform;
import org.example.checkers.game.Game;
import org.example.checkers.move.MoveData;
import org.example.checkers.piece.NormalPiece;
import org.example.checkers.piece.Piece;
import org.example.checkers.piece.PieceColor;
import org.example.checkers.piece.QueenPiece;

import java.util.ArrayList;
import java.util.Random;

public class Computer {
    private final Game game;
    private final PieceColor computerPieceColor = PieceColor.BLACK;

    public Computer(Game game) {
        this.game = game;

        Thread thread = new Thread(this::runComputerAI);
        thread.setDaemon(true);
        thread.start();
    }

    public void runComputerAI() {
        while (game.isRunning()) {
            if (game.getTurn() != computerPieceColor) {
                try {
                    game.waitForTurnChange();
                } catch (InterruptedException e) {
                    game.setIsRunning(false);
                }
            }

            if (!game.isRunning()) break;

            if (game.getTurn() == computerPieceColor) {
                Platform.runLater(() -> makeMove(computerPieceColor));
            }
        }
    }

    public void makeMove(PieceColor color) {
        if (!game.isRunning()) return;

        ArrayList<MoveData> moves = getAllMoves(color);
        ArrayList<MoveData> killingMoves = new ArrayList<>();

        for (MoveData move : moves) {
            if (move.isKill()) killingMoves.add(move);
        }

        Random random = new Random();
        int randomIndex = random.nextInt(moves.size());
        MoveData move = moves.get(randomIndex);

        if (!killingMoves.isEmpty()) {
            randomIndex = random.nextInt(killingMoves.size());
            move = killingMoves.get(randomIndex);
        }

        Piece piece = move.getPiece();
        if (piece.getPieceType().isNormal()) {
            NormalPiece normalPiece = (NormalPiece) piece;
            if (game.getNormalMoveValidator().isMoveValid(game, normalPiece, move.getNewCol(), move.getNewRow())) {
                if (piece.canKill(game)) piece.kill(game, move.getNewCol(), move.getNewRow());
                game.getNormalMover().movePiece(game, normalPiece, move.getNewCol(), move.getNewRow());
                normalPiece.updateOnBoard();
            }
        }
        if (piece.getPieceType().isQueen()) {
            QueenPiece queenPiece = (QueenPiece) piece;
            if (game.getQueenMoveValidator().isMoveValid(game, queenPiece, move.getNewCol(), move.getNewRow())) {
                if (piece.canKill(game)) piece.kill(game, move.getNewCol(), move.getNewRow());
                game.getQueenMover().movePiece(game, queenPiece, move.getNewCol(), move.getNewRow());
                queenPiece.updateOnBoard();
            }
        }

        game.changeTurn(piece);
    }

    public ArrayList<MoveData> getAllMoves(PieceColor color) {
        ArrayList<MoveData> moves = new ArrayList<>();

        for (Piece piece : game.getBoard()) {
            if (piece.getColor() == color) {
                if (piece.getPieceType().isNormal()) moves.addAll(game.getNormalMove().getValidMoves(game, piece));
                if (piece.getPieceType().isQueen()) moves.addAll(game.getQueenMove().getValidMoves(game, piece));
            }
        }

        return moves;
    }
}
