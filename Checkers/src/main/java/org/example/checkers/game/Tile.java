package org.example.checkers.game;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tile extends StackPane {
    public Tile(boolean light, int x, int y) {
        setWidth(Game.TILE_SIZE);
        setHeight(Game.TILE_SIZE);

        Rectangle rectangleWithBorder = new Rectangle(90, 90);
        rectangleWithBorder.setFill(Color.BLACK);
        Rectangle rectangle = new Rectangle(88, 88);

        relocate(x * Game.TILE_SIZE + ((double) Game.BORDER_SIZE / 2),
                y * Game.TILE_SIZE + ((double) Game.BORDER_SIZE / 2));
        rectangle.setFill(light ? Color.WHEAT : Color.rgb(102, 71, 64));

        getChildren().addAll(rectangleWithBorder, rectangle);
    }
}
