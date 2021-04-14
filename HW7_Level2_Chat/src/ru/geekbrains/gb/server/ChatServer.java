package ru.geekbrains.gb.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class ChatServer {
    private final Set<ClientHandler> peopleForChat;

    public ChatServer() {
        peopleForChat = new HashSet<>();

        try {
            ServerSocket socket = new ServerSocket(8080);
            System.out.println("Server is running up...");
            while (true) {
                System.out.println("Waiting for a connection...");
                Socket clientSocket = socket.accept();
                new ClientHandler(clientSocket, this);
            }
        } catch (IOException e) {
            throw new ChatServerException("Something went wrong", e);
        }
    }

    public void sendPrivateMessage(String text, String receiver, String sender) {
        for (ClientHandler client : peopleForChat) {
            if(client.getName().equals(receiver)) {
                client.sendMessage(sender + " : " + text);
            }
        }
    }
    public void readyForChat(ClientHandler clientHandler) {
        peopleForChat.add(clientHandler);
    }
}
