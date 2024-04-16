package org.example.checkers.move;

import org.example.checkers.game.Game;
import org.example.checkers.piece.Piece;

public class NormalKillScanner implements KillScanner {
    @Override
    public boolean isKillPossible(Game game, Piece piece) {
        int[][] opponentPlace = new int[][]{{1, 1}, {-1, 1}, {-1, -1}, {1, -1}};
        int[][] directions = new int[][]{{2, 2}, {-2, 2}, {-2, -2}, {2, -2}};

        for (int i = 0; i < directions.length; i++) {
            int[] direction = directions[i];
            int[] place = opponentPlace[i];

            if (piece.getCol() + direction[0] < Game.WIDTH && piece.getCol() + direction[0] >= 0 && piece.getRow() + direction[1] < Game.WIDTH && piece.getRow() + direction[1] >= 0) {
                if (!game.isPieceOnCords(piece.getCol() + direction[0], piece.getRow() + direction[1])) {
                    Piece pieceToKill = game.findPiece(piece.getCol() + place[0], piece.getRow() + place[1]);
                    if (pieceToKill != null && pieceToKill.getColor() != piece.getColor()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public Piece findPieceToKill(Game game, int col, int row, int colDest, int rowDest) {
        int rowDirection = (row > rowDest) ? -1 : 1;
        int colDirection = (col > colDest) ? -1 : 1;
        int killRow = row + rowDirection;
        int killCol = col + colDirection;

        return game.findPiece(killCol, killRow);
    }
}
