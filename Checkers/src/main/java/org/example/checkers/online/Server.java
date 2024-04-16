package org.example.checkers.online;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private final ServerSocket serverSocket;

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(1111);
            Server server = new Server(serverSocket);
            server.startServer();
        } catch (IOException e) {
            System.out.println("Server didn't start");
        }
    }

    public void startServer() {
        System.out.println("Server started!");
        try {
            while (!serverSocket.isClosed()) {
                Socket clientSocket1 = serverSocket.accept();
                System.out.println("Player 1 connected.");

                Socket clientSocket2 = serverSocket.accept();
                System.out.println("Player 2 connected.");

                ClientHandler clientHandler = new ClientHandler(clientSocket1, clientSocket2);
                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        } catch (IOException ignored) {
        } finally {
            closeServerSocket();
        }
    }

    private void closeServerSocket() {
        try {
            if (serverSocket != null) serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}