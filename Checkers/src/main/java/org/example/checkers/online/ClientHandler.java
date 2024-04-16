package org.example.checkers.online;

import org.example.checkers.piece.Piece;
import org.example.checkers.piece.PieceColor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable {
    private final Socket clientSocket1;
    private final ObjectOutputStream outputStream1;
    private final ObjectInputStream inputStream1;
    private final Socket clientSocket2;
    private final ObjectOutputStream outputStream2;
    private final ObjectInputStream inputStream2;

    public ClientHandler(Socket clientSocket1, Socket clientSocket2) throws IOException {
        this.clientSocket1 = clientSocket1;
        this.outputStream1 = new ObjectOutputStream(clientSocket1.getOutputStream());
        this.inputStream1 = new ObjectInputStream(clientSocket1.getInputStream());
        this.clientSocket2 = clientSocket2;
        this.outputStream2 = new ObjectOutputStream(clientSocket2.getOutputStream());
        this.inputStream2 = new ObjectInputStream(clientSocket2.getInputStream());
    }

    @Override
    public void run() {
        try {
            sendColorToClient(outputStream1, PieceColor.WHITE);
            sendColorToClient(outputStream2, PieceColor.BLACK);

            while (clientSocket2.isConnected() && clientSocket2.isConnected()) {
                inputStream1.readBoolean();
                ArrayList<Piece> piecesFromClient1 = receivePiecesFromClient(inputStream1);
                sendPiecesAndMoveStatusToClient(outputStream2, piecesFromClient1, true);

                inputStream2.readBoolean();
                ArrayList<Piece> piecesFromClient2 = receivePiecesFromClient(inputStream2);
                sendPiecesAndMoveStatusToClient(outputStream1, piecesFromClient2, true);
            }
        } catch (IOException | ClassNotFoundException ignored) {
        } finally {
            closeEverything();
        }
    }

    private void sendPiecesAndMoveStatusToClient(ObjectOutputStream outputStream, ArrayList<Piece> piecesToSend, boolean moveMade) throws IOException {
        outputStream.writeBoolean(moveMade);
        outputStream.writeObject(piecesToSend);
        outputStream.flush();
    }

    private void sendColorToClient(ObjectOutputStream outputStream, PieceColor pieceColor) throws IOException {
        outputStream.writeObject(pieceColor);
        outputStream.flush();
    }

    private ArrayList<Piece> receivePiecesFromClient(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
        return (ArrayList<Piece>) inputStream.readObject();
    }

    private void closeEverything() {
        try {
            if (inputStream1 != null) inputStream1.close();
            if (outputStream1 != null) outputStream1.close();
            if (clientSocket1 != null) clientSocket1.close();

            if (inputStream2 != null) inputStream2.close();
            if (outputStream2 != null) outputStream2.close();
            if (clientSocket2 != null) clientSocket2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
