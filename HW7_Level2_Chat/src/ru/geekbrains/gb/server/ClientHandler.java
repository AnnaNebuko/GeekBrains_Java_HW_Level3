package ru.geekbrains.gb.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class ClientHandler {
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private ChatServer chatServer;
    private String name;
    Connection con = DBConnection.ConnectionBD();

    public ClientHandler(Socket socket, ChatServer chatServer) {
        this.socket = socket;
        this.chatServer = chatServer;

        try {
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            throw new ChatServerException("Something went wrong during client establishing.", e);
        }

        new Thread(() -> {
            try {
                chatWithPeople();
            } catch (IOException e) {
                throw new ChatServerException("chatWithPeople() has a problem", e);
            }
        })
                .start();
    }
    private void chatWithPeople() throws IOException {
        sendMessage("Hi! What is your login? (Right: L1, L2, L3)");
        String login = in.readUTF();

        sendMessage("What is your password? (Right: P1, P2, P3)");
        String password = in.readUTF();

        Optional<Users> mayBeUser = authorization (login, password);

        while (mayBeUser.isEmpty()) {
            sendMessage("No login or password found. Try again!");

            sendMessage("login: ");
            login = in.readUTF();
            sendMessage("password:");
            password = in.readUTF();

            mayBeUser = authorization (login, password);
        }
        Users userInfo = mayBeUser.get();
        name = userInfo.getLogin();
        chatServer.readyForChat(this);

        sendMessage("Great! You can chat. Please write like this: /w nick message:");
        sendMessage("Type 'exit' to stop conversation.");
        while (true) {
            try {
                /**
                 * Message pattern
                 * /w nick message
                 */
                String message = in.readUTF();

                if (message.startsWith("/w")) {
                    String[] privateMessage = message.split("\\s");
                    String receiver = privateMessage[1];
                    String text = privateMessage[2];

                    Optional<Users> mayBeReceiver = findReceiver (receiver);
                    if (mayBeReceiver.isPresent())
                        chatServer.sendPrivateMessage(text, receiver, name);
                    else{
                        sendMessage("Your receiver is not found.");
                    }
                }
                else if (message.equals("exit")) {
                    sendMessage("Conversation stopped.");
                    return;
                } else {
                    sendMessage("Incorrect message pattern. " +
                            "Please write like this: /w nick message");
                }
            } catch (IOException e) {
                throw new ChatServerException("Something went wrong during client authentication.", e);
            }
        }
    }

    private Optional<Users> findReceiver(String receiver) {
        try {
            String sql = "select * from students where login like ? ; ";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, receiver);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(
                        new Users(
                                resultSet.getString("login"),
                                resultSet.getString("password")
                        )
                );
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private Optional<Users> authorization(String login, String password) {

        try {
            String sql = "select * from students where login like ? AND " +
                    "password like ?; ";
            PreparedStatement statement = con.prepareStatement(sql);

            statement.setString(1, login);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(
                        new Users(
                                resultSet.getString("login"),
                                resultSet.getString("password")
                        )
                );
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public void sendMessage(String message) {
        try {
            out.writeUTF(message);
        } catch (IOException e) {
            throw new ChatServerException("Something went wrong during sending the message.", e);
        }
    }

    public String getName() {
        return name;
    }
}