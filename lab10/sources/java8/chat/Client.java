package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.KeyPair;
import java.security.PublicKey;
import java.util.Base64;

public class Client {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private final KeyPair keypair = Cryptography.generateRSAKkeyPair();;
    private PublicKey receiverPublicKey;

    public Client() throws Exception {
    }

    public BufferedReader getIn() {
        return in;
    }

    public void startConnection(String ip, int port) throws IOException {
        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out.println(Base64.getEncoder().encodeToString(keypair.getPublic().getEncoded()));
        receiverPublicKey = Cryptography.getPublicKey(in.readLine());
    }

    public void sendMessage(String msg) throws Exception {
        String encryptedMsg = Cryptography.encryptBase64(msg, receiverPublicKey);
        out.println(encryptedMsg);
    }

    public void stopConnection() throws IOException {
        clientSocket.close();
    }

    public KeyPair getKeypair() {
        return keypair;
    }
}
