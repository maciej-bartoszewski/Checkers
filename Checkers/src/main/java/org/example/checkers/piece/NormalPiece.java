package org.example.checkers.piece;

import org.example.checkers.game.Game;
import org.example.checkers.move.*;

public class NormalPiece extends Piece {
    private final transient NormalMove normalMove;

    public NormalPiece(Game game, int col, int row, PieceType pieceType, PieceColor pieceColor, MoveValidator validator, PieceMover mover, PieceKiller killer, KillScanner scaner) {
        this.game = game;
        this.col = col;
        this.row = row;
        this.pieceType = pieceType;
        this.pieceColor = pieceColor;
        normalMove = new NormalMove(validator, mover, killer, scaner);

        relocate(col * Game.TILE_SIZE + 5 + ((double) Game.BORDER_SIZE / 2),
                row * Game.TILE_SIZE + 5 + ((double) Game.BORDER_SIZE / 2));

        createPiece();

        MouseControl(normalMove);
    }

    @Override
    public boolean canKill(Game game) {
        return normalMove.isKillPossible(game, this);
    }

    @Override
    public void kill(Game game, int colDest, int rowDest) {
        normalMove.killPiece(game, this, colDest, rowDest);
    }
}