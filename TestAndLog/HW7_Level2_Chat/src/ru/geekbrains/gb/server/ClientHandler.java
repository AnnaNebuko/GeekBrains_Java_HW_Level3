package ru.geekbrains.gb.server;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Optional;

public class ClientHandler {
    private final Socket socket;
    private final DataInputStream in;
    private final DataOutputStream out;
    private final ChatServer chatServer;
    private String name;

    private static final String NEW_LINE = System.lineSeparator();

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
        sendMessage("Hi! What is your login? (Right: l1, l2, l3)");
        String login = in.readUTF();

        sendMessage("What is your password? (Right: P1, P2, P3)");
        String password = in.readUTF();

        Optional<AuthenticationService> mayBeUser = chatServer.getAuthenticationService().authorization (login, password);

        while (mayBeUser.isEmpty()) {
            sendMessage("No login or password found. Try again!");

            sendMessage("login: ");
            login = in.readUTF();
            sendMessage("password:");
            password = in.readUTF();

            mayBeUser = chatServer.getAuthenticationService().authorization (login, password);
        }
        AuthenticationService userInfo = mayBeUser.get();
        name = userInfo.getLogin();
        chatServer.readyForChat(this);

        //====Homework about IO========
        PrintLast100Lines();
        //====Homework about IO========

        sendMessage("You can chat. Write like this: /w nick (Right: l1, l2, l3) message:");
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

                    Optional<AuthenticationService> mayBeReceiver = chatServer.getAuthenticationService().findReceiver (receiver);
                    if (mayBeReceiver.isPresent()) {
                        chatServer.sendPrivateMessage(text, receiver, name);

                        //====Homework about IO=========
                        appendToFile(name + " : " + text + NEW_LINE);
                        //====Homework about IO=========
                    }
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

    private void appendToFile(String message) throws IOException {
        Path path = Paths.get("History.txt");
        Files.writeString(path, message,
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND);
    }
    private void PrintLast100Lines(){
        int readLines = 0;
        File file = new File("History.txt");
        StringBuilder builder = new StringBuilder();
        RandomAccessFile randomAccessFile = null;
        try {
            randomAccessFile = new RandomAccessFile(file, "r");
            long fileLength = file.length() - 1;

            randomAccessFile.seek(fileLength);
            for(long pointer = fileLength; pointer >= 0; pointer--){
                randomAccessFile.seek(pointer);
                char c;

                c = (char)randomAccessFile.read();

                if(c == '\n'){
                    readLines++;
                    if(readLines == 100)
                        break;
                }
                builder.append(c);
            }

            builder.reverse();
            sendMessage(builder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally{
            if(randomAccessFile != null){
                try {
                    randomAccessFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
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