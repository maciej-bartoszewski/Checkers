package org.example.checkers.piece;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.example.checkers.game.Game;
import org.example.checkers.move.Move;

import java.io.Serializable;

public abstract class Piece extends StackPane implements Serializable {
    protected transient Game game;
    protected int col;
    protected int row;
    protected transient double mouseX, mouseY;
    protected transient double oldX, oldY;
    protected boolean canKill = false;
    protected PieceType pieceType;
    protected PieceColor pieceColor;

    protected void createPiece() {
        Circle circle = new Circle(40);
        Circle secondCircle = new Circle(30);
        if (PieceColor.BLACK == this.pieceColor) {
            circle.setFill(Color.rgb(50, 50, 50));
            secondCircle.setFill(Color.rgb(30, 30, 30));
        }
        if (PieceColor.WHITE == this.pieceColor) {
            circle.setFill(Color.rgb(236, 236, 236));
            secondCircle.setFill(Color.rgb(249, 249, 249));
        }
        getChildren().addAll(circle, secondCircle);
    }

    protected void MouseControl(Move move) {
        setOnMousePressed(e -> {
            mouseX = e.getSceneX();
            mouseY = e.getSceneY();
            oldX = getLayoutX();
            oldY = getLayoutY();
        });

        setOnMouseDragged(e -> {
            if (game.getGamemode().isOnline() || game.getGamemode().isOnePlayer()) {
                if (game.getTurn() == pieceColor && game.getPlayerColor() == pieceColor)
                    relocate(e.getSceneX() - mouseX + oldX, e.getSceneY() - mouseY + oldY);
            } else {
                if (game.getTurn() == pieceColor)
                    relocate(e.getSceneX() - mouseX + oldX, e.getSceneY() - mouseY + oldY);
            }
        });

        setOnMouseReleased(e -> {
            double newX = oldX + e.getSceneX() - mouseX;
            double newY = oldY + e.getSceneY() - mouseY;

            int newRow = (int) (newY / Game.TILE_SIZE);
            int newCol = (int) (newX / Game.TILE_SIZE);

            if (move.isMoveValid(game, this, newCol, newRow)) {
                if (game.getGamemode().isOnline() || game.getGamemode().isOnePlayer()) {
                    if (game.getTurn() == pieceColor && game.getPlayerColor() == pieceColor) {
                        handleMove(move, newRow, newCol);
                        if (game.getGamemode().isOnePlayer()) game.notifyTurnChange();
                    }
                } else {
                    if (game.getTurn() == pieceColor) {
                        handleMove(move, newRow, newCol);
                    }
                }
            } else {
                relocate(oldX, oldY);
            }
        });
    }

    public void handleMove(Move move, int newRow, int newCol) {
        relocate(newCol * Game.TILE_SIZE + 5 + ((double) Game.BORDER_SIZE / 2),
                newRow * Game.TILE_SIZE + 5 + ((double) Game.BORDER_SIZE / 2));
        if (canKill(game)) kill(game, newCol, newRow);
        move.movePiece(game, this, newCol, newRow);
        game.changeTurn(this);
    }

    public void updateOnBoard() {
        relocate(col * Game.TILE_SIZE + 5 + ((double) Game.BORDER_SIZE / 2),
                row * Game.TILE_SIZE + 5 + ((double) Game.BORDER_SIZE / 2));
    }

    public boolean equal(Piece piece) {
        return (this.col == piece.col && this.row == piece.row && this.pieceColor == piece.pieceColor);
    }

    public PieceColor getColor() {
        return pieceColor;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public boolean getCanKill() {
        return canKill;
    }

    public void setCanKill(boolean canKill) {
        this.canKill = canKill;
    }

    public abstract boolean canKill(Game game);

    public abstract void kill(Game game, int colDest, int rowDest);
}
