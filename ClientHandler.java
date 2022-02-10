import java.net.Socket;
import java.io.*;
import java.util.Vector;

public class ClientHandler extends Thread {

    static Vector<ClientHandler> my_clients = new Vector();

    String name = "";
    BufferedReader br;
    PrintWriter w;
    public ClientHandler(Socket client) throws IOException {

        InputStreamReader in = new InputStreamReader(client.getInputStream());
        br = new BufferedReader(in);
        OutputStreamWriter osw = new OutputStreamWriter(client.getOutputStream());
        w = new PrintWriter(osw);
        my_clients.add(this);
    }

    public void run() {
        try {
            String msg = "";
            while (msg != null) {
                msg = br.readLine();
                if (name.equals("")) name = msg;
                else broadcastMessage(msg);
            }

        } catch (Exception ex) {
            System.out.println("Exception in ClientHandler:run: " + ex);
        }
    }

    public void sendMessage(String msg) {
        w.println(msg);
        w.flush();
    }

    public void broadcastMessage(String msg) {
        for (ClientHandler ch:my_clients) {
            if (!msg.startsWith(ch.name)) ch.sendMessage(msg);
        }
    }
}
