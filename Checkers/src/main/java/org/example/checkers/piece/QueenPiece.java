package org.example.checkers.piece;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.checkers.game.Game;
import org.example.checkers.move.*;

import java.util.Objects;

public class QueenPiece extends Piece {
    private final transient QueenMove queenMove;

    public QueenPiece(Game game, int col, int row, PieceType pieceType, PieceColor pieceColor, MoveValidator validator, PieceMover mover, PieceKiller killer, KillScanner scanner) {
        this.game = game;
        this.col = col;
        this.row = row;
        this.pieceType = pieceType;
        this.pieceColor = pieceColor;
        queenMove = new QueenMove(validator, mover, killer, scanner);

        relocate(col * Game.TILE_SIZE + 5 + ((double) Game.BORDER_SIZE / 2),
                row * Game.TILE_SIZE + 5 + ((double) Game.BORDER_SIZE / 2));

        createPiece();

        MouseControl(queenMove);
    }

    protected void createPiece() {
        super.createPiece();

        Image image;
        if (pieceColor.isWhite())
            image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/org/example/checkers/whiteCrown.png")));
        else
            image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/org/example/checkers/blackCrown.png")));
        ImageView crown = new ImageView(image);
        crown.setFitHeight(80);
        crown.setFitWidth(80);

        getChildren().addAll(crown);
    }

    @Override
    public boolean canKill(Game game) {
        return queenMove.isKillPossible(game, this);
    }

    @Override
    public void kill(Game game, int colDest, int rowDest) {
        queenMove.killPiece(game, this, colDest, rowDest);
    }
}
