package org.example.checkers.online;

import javafx.application.Platform;
import org.example.checkers.game.Game;
import org.example.checkers.piece.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class Client {
    private final Socket clientSocket;
    private final ObjectOutputStream outputStream;
    private final ObjectInputStream inputStream;
    private final Game game;

    public Client(Game game) throws IOException {
        this.game = game;

        clientSocket = new Socket("localhost", 1111);
        outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
        inputStream = new ObjectInputStream(clientSocket.getInputStream());

        Thread thread = new Thread(this::startConversation);
        thread.setDaemon(true);
        thread.start();
    }

    public void startConversation() {
        try {
            setStartingSettings();
            while (clientSocket.isConnected()) {
                sendPieces();
                receiveAndUpdateBoard();
            }
        } catch (ClassNotFoundException | IOException | InterruptedException ignored) {
        } finally {
            closeEverything(clientSocket, outputStream, inputStream);
        }
    }

    private void receiveAndUpdateBoard() throws IOException, ClassNotFoundException {
        boolean moveMade = inputStream.readBoolean();
        if (moveMade) {
            ArrayList<Piece> receivedPieces = receivePiecesFromServer();
            Platform.runLater(() -> {
                game.clearBoard();
                for (Piece piece : receivedPieces) {
                    if (piece.getPieceType().isNormal()) {
                        Piece newPiece = new NormalPiece(game, piece.getCol(), piece.getRow(), PieceType.NORMAL, piece.getColor(),
                                game.getNormalMoveValidator(), game.getNormalMover(), game.getNormalPieceKiller(), game.getNormalKillScanner());
                        game.addPiece(newPiece);
                    }
                    if (piece.getPieceType().isQueen()) {
                        Piece newPiece = new QueenPiece(game, piece.getCol(), piece.getRow(), PieceType.QUEEN, piece.getColor(),
                                game.getQueenMoveValidator(), game.getQueenMover(), game.getQueenPieceKiller(), game.getQueenKillScanner());
                        game.addPiece(newPiece);
                    }
                }
                game.changeTurn(null);
            });
            if (game.getPlayerColor().isWhite()) game.setTurn(PieceColor.WHITE);
            else game.setTurn(PieceColor.BLACK);
            Platform.runLater(() -> game.getTurnInfoOnline().updateTurnLabel());
        }
    }

    private void sendPieces() throws InterruptedException, IOException {
        if (game.getTurn() == game.getPlayerColor()) {
            game.waitForTurnChange();
            ArrayList<Piece> piecesToSend = new ArrayList<>(game.getBoard());
            sendMoveFinishedToServer();
            sendPiecesToServer(piecesToSend);
            Platform.runLater(() -> game.getTurnInfoOnline().updateTurnLabel());
        }
    }

    private void setStartingSettings() throws IOException, ClassNotFoundException {
        game.setPlayerColor(receiveColorFromServer());
        Platform.runLater(() -> {
            game.getTimeInfo().restartCounting();
            game.getTurnInfoOnline().updateColorLabel();
            game.getTurnInfoOnline().updateTurnLabel();
        });
    }

    private void sendMoveFinishedToServer() throws IOException {
        outputStream.writeBoolean(true);
        outputStream.flush();
    }

    private PieceColor receiveColorFromServer() throws IOException, ClassNotFoundException {
        return (PieceColor) inputStream.readObject();
    }

    private ArrayList<Piece> receivePiecesFromServer() throws IOException, ClassNotFoundException {
        return (ArrayList<Piece>) inputStream.readObject();
    }

    private void sendPiecesToServer(ArrayList<Piece> piecesToSend) throws IOException {
        outputStream.writeObject(piecesToSend);
        outputStream.flush();
    }

    private void closeEverything(Socket clientSocket, ObjectOutputStream outputStream, ObjectInputStream inputStream) {
        try {
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
            if (clientSocket != null) {
                clientSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
