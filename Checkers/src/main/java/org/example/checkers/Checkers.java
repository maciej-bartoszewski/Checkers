package org.example.checkers;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.example.checkers.computer.Computer;
import org.example.checkers.game.Game;
import org.example.checkers.game.Gamemode;
import org.example.checkers.online.Client;

import java.util.List;


public class Checkers extends Application {
    private final Game game = new Game();

    private Parent createContent() {
        Pane root = new Pane();
        root.setPrefSize(Game.WIDTH * Game.TILE_SIZE + Game.BORDER_SIZE,
                Game.HEIGHT * Game.TILE_SIZE + Game.BORDER_SIZE);
        root.getChildren().addAll(game.getTileGroup(), game.getPieceGroup());

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(root);
        borderPane.setStyle("-fx-background-color: rgb(73, 51, 46)");

        borderPane.setBottom(game.getTimeInfo().getInfo());

        if (game.getGamemode() == Gamemode.ONLINE) {
            borderPane.setTop(game.getTurnInfoOnline().getInfo());
        } else {
            borderPane.setTop(game.getTurnInfoTwoPlayers().getInfo());
        }

        return borderPane;
    }

    @Override
    public void start(Stage stage) throws Exception {
        List<String> rawParams = getParameters().getRaw();
        if (!rawParams.isEmpty() && rawParams.getFirst().equals("online")){
            game.setGamemode(Gamemode.ONLINE);
            new Client(game);
        }
        else if (!rawParams.isEmpty() && rawParams.getFirst().equals("single")){
            game.setGamemode(Gamemode.ONE_PLAYER);
            new Computer(game);
        }
        else {
            game.setGamemode(Gamemode.TWO_PLAYERS);
        }

        Scene scene = new Scene(createContent());
        stage.setTitle("Checkers - Maciej Bartoszewski");
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);

        stage.setOnCloseRequest(e -> game.getTimeInfo().stopCounting());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
