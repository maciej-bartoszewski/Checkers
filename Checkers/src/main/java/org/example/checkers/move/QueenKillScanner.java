package org.example.checkers.move;

import org.example.checkers.game.Game;
import org.example.checkers.piece.Piece;

public class QueenKillScanner implements KillScanner {
    @Override
    public boolean isKillPossible(Game game, Piece piece) {
        int[][] directions = {{1, 1}, {1, -1}, {-1, -1}, {-1, 1}};

        for (int[] direction : directions) {
            int currentCol = piece.getCol();
            int currentRow = piece.getRow();

            while (currentCol >= 0 && currentCol < 8 && currentRow >= 0 && currentRow < 8) {
                currentCol += direction[0];
                currentRow += direction[1];

                Piece pieceSearch = game.findPiece(currentCol, currentRow);
                if (pieceSearch != null && pieceSearch.getColor() != piece.getColor()) {
                    if (currentCol + direction[0] >= 0 && currentCol + direction[0] < 8 && currentRow + direction[1] >= 0 && currentRow + direction[1] < 8) {
                        if (!game.isPieceOnCords(currentCol + direction[0], currentRow + direction[1])) {
                            return true;
                        } else break;
                    }
                }
            }
        }

        return false;
    }

    @Override
    public Piece findPieceToKill(Game game, int col, int row, int colDest, int rowDest) {
        int rowDirection = (row > rowDest) ? -1 : 1;
        int colDirection = (col > colDest) ? -1 : 1;

        int currentRow = row;
        int currentCol = col;

        while (currentRow != rowDest && currentCol != colDest) {
            Piece pieceToKill = game.findPiece(currentCol, currentRow);
            if (pieceToKill != null && pieceToKill.getCol() != col && pieceToKill.getRow() != row) {
                return pieceToKill;
            }

            currentCol += colDirection;
            currentRow += rowDirection;
        }

        return null;
    }
}
