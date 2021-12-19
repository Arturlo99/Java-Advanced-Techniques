package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyPair;
import java.security.PublicKey;
import java.util.Base64;

public class Host {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private final KeyPair keypair = Cryptography.generateRSAKkeyPair();
    private PublicKey receiverPublicKey;

    private Thread getClientThread = new Thread(() -> {
        try {
            clientSocket = serverSocket.accept();
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out.println(Base64.getEncoder().encodeToString(keypair.getPublic().getEncoded()));
            receiverPublicKey = Cryptography.getPublicKey(in.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    });

    public Host() throws Exception {
    }

    public void start(int port) throws Exception {
        serverSocket = new ServerSocket(port);
        getClientThread.start();
    }

    public void stop() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
        serverSocket.close();
    }

    public void sendMessage(String msg) throws Exception {
        String encryptedMsg = Cryptography.encryptBase64(msg, receiverPublicKey);
        out.println(encryptedMsg);
    }

    public BufferedReader getIn() {
        return in;
    }

    public Thread getGetClientThread() {
        return getClientThread;
    }

    public KeyPair getKeypair() {
        return keypair;
    }

}
