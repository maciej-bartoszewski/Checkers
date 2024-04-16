package org.example.checkers.move;

import org.example.checkers.game.Game;
import org.example.checkers.piece.Piece;

import java.util.ArrayList;

public class QueenMove extends Move {
    public QueenMove(MoveValidator validator, PieceMover mover, PieceKiller killer, KillScanner scanner) {
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

        int[][] directions = {{1, 1}, {1, -1}, {-1, -1}, {-1, 1}};
        boolean isSkip = false;

        for (int[] direction : directions) {
            int currentCol = piece.getCol();
            int currentRow = piece.getRow();

            while (currentCol >= 0 && currentCol < 8 && currentRow >= 0 && currentRow < 8) {
                currentCol += direction[0];
                currentRow += direction[1];

                Piece pieceSearch = game.findPiece(currentCol, currentRow);
                if (pieceSearch != null) {
                    if (pieceSearch.getColor() != piece.getColor()) {
                        currentCol = currentCol + direction[0];
                        currentRow = currentRow + direction[1];
                        while (currentCol >= 0 && currentCol < 8 && currentRow >= 0 && currentRow < 8) {
                            if (!game.isPieceOnCords(currentCol, currentRow)) {
                                validMoves.add(new MoveData(piece, currentCol, currentRow, true));
                            } else {
                                isSkip = true;
                                break;
                            }
                            currentCol += direction[0];
                            currentRow += direction[1];
                        }
                        if (isSkip) {
                            isSkip = false;
                            break;
                        }
                    } else {
                        break;
                    }
                } else validMoves.add(new MoveData(piece, currentCol, currentRow, false));
            }
        }

        return validMoves;
    }
}
