package org.example.checkers.move;

import org.example.checkers.game.Game;
import org.example.checkers.piece.Piece;

import java.util.ArrayList;

public class NormalMove extends Move {

    public NormalMove(MoveValidator validator, PieceMover mover, PieceKiller killer, KillScanner scanner) {
        super(validator, mover, killer, scanner);
    }

    @Override
    public boolean isMoveValid(Game game, Piece piece, int colDest, int rowDest) {
        return moveValidator.isMoveValid(game, piece, colDest, rowDest);
    }

    @Override
    public void movePiece(Game game, Piece piece, int newCol, int newRow) {
        pieceMover.movePiece(game, piece, newCol, newRow);
    }

    @Override
    public boolean isKillPossible(Game game, Piece piece) {
        return killScanner.isKillPossible(game, piece);
    }

    @Override
    public void killPiece(Game game, Piece piece, int colDest, int rowDest) {
        pieceKiller.killPiece(game, piece, colDest, rowDest);
    }

    @Override
    public ArrayList<MoveData> getValidMoves(Game game, Piece piece) {
        ArrayList<MoveData> validMoves = new ArrayList<>();

        int[][] directions;
        if (piece.getColor().isBlack()) {
            directions = new int[][]{{1, 1}, {-1, 1}};
        } else directions = new int[][]{{1, -1}, {-1, -1}};

        for (int[] direction : directions) {
            if (piece.getCol() + direction[0] < Game.WIDTH && piece.getCol() + direction[0] >= 0 && piece.getRow() + direction[1] < Game.WIDTH && piece.getRow() + direction[1] >= 0) {
                if (!game.isPieceOnCords(piece.getCol() + direction[0], piece.getRow() + direction[1]))
                    validMoves.add(new MoveData(piece, piece.getCol() + direction[0], piece.getRow() + direction[1], false));
            }
        }

        int[][] opponentPlace = new int[][]{{1, 1}, {-1, 1}, {-1, -1}, {1, -1}};
        directions = new int[][]{{2, 2}, {-2, 2}, {-2, -2}, {2, -2}};

        for (int i = 0; i < directions.length; i++) {
            int[] direction = directions[i];
            int[] place = opponentPlace[i];

            if (piece.getCol() + direction[0] < Game.WIDTH && piece.getCol() + direction[0] >= 0 && piece.getRow() + direction[1] < Game.WIDTH && piece.getRow() + direction[1] >= 0) {
                if (!game.isPieceOnCords(piece.getCol() + direction[0], piece.getRow() + direction[1])) {
                    Piece pieceToKill = game.findPiece(piece.getCol() + place[0], piece.getRow() + place[1]);
                    if (pieceToKill != null && pieceToKill.getColor() != piece.getColor()) {
                        validMoves.add(new MoveData(piece, piece.getCol() + direction[0], piece.getRow() + direction[1], true));
                    }
                }
            }
        }

        return validMoves;
    }
}
