package org.example.checkers.game;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.example.checkers.piece.PieceColor;

public class TimeInfo {
    private final Game game;
    private final Label player1MoveTimeLabel = new Label("Player 1 Move Time: 0 sec");
    private final Label player2MoveTimeLabel = new Label("Player 2 Move Time: 0 sec");
    private boolean running = true;
    private long player1Timer = 0;
    private long player2Timer = 0;

    public TimeInfo(Game game) {
        this.game = game;
    }
    public BorderPane getInfo(){
        HBox player1Box = new HBox(player1MoveTimeLabel);
        player1Box.setAlignment(Pos.CENTER_LEFT);
        player1Box.setFillHeight(true);

        BorderPane.setAlignment(player1Box, Pos.BOTTOM_LEFT);
        BorderPane bottomPane = new BorderPane();
        bottomPane.setLeft(player1Box);

        player1MoveTimeLabel.setFont(new Font("Arial", 18));
        player1MoveTimeLabel.setTextFill(Color.WHITE);


        HBox player2Box = new HBox(player2MoveTimeLabel);
        player2Box.setAlignment(Pos.CENTER_RIGHT);
        player2Box.setFillHeight(true);

        BorderPane.setAlignment(player2Box, Pos.BOTTOM_RIGHT);
        bottomPane.setRight(player2Box);

        player2MoveTimeLabel.setFont(new Font("Arial", 18));
        player2MoveTimeLabel.setTextFill(Color.WHITE);


        return bottomPane;
    }

    public void startCounting(){
        Thread thread = new Thread(() -> {
            while (running) {
                try {
                    Thread.sleep(1000);

                    updatePlayer1MoveTimeLabel();
                    if(game.getTurn() == game.getPlayerColor()) player1Timer += 1;

                    if(game.getGamemode() != Gamemode.ONE_PLAYER) {
                        updatePlayer2MoveTimeLabel();
                        if(game.getTurn() != game.getPlayerColor()) player2Timer += 1;
                    }

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        thread.start();
    }

    public void restartCounting(){
        player1Timer = 0;
        player2Timer = 0;
    }
    public void stopCounting(){
        running = false;
    }

    public void updatePlayer1MoveTimeLabel() {
        Platform.runLater(() -> player1MoveTimeLabel.setText("Player 1 Move Time: " + player1Timer + " sec"));
    }

    public void updatePlayer2MoveTimeLabel() {
        Platform.runLater(() -> player2MoveTimeLabel.setText("Player 2 Move Time: " + player2Timer + " sec"));
    }
}
