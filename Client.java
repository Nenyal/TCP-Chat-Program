import java.net.Socket;
import java.io.*;
import java.util.Scanner;

public class Client extends Thread {

    public static void main(String[] args) {
        try {
            Socket s = new Socket("localhost", 11111);
            Client c = new Client(args[0], s);
            c.start();

            Scanner sc = new Scanner(System.in);
            String input;
            while (true) {
                input = sc.nextLine();
                c.sendMessageToServer(input);
            }

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    String name = "";
    BufferedReader br;
    PrintWriter w;
    public Client(String n, Socket server_socket) throws IOException {
        name = n;

        InputStreamReader in = new InputStreamReader(server_socket.getInputStream());
        br = new BufferedReader(in);

        OutputStreamWriter osw = new OutputStreamWriter(server_socket.getOutputStream());
        w = new PrintWriter(osw);

        w.println(name);
        w.flush();
    }

    public void run() {
        try {
            String msg = "";
            while (msg != null) {
                msg = br.readLine();
                System.out.println(">> " + msg);
            }

        } catch (Exception ex) {
            System.out.println("Exception in Client:run: " + ex);
        }
    }

    public void sendMessageToServer(String s) {
        w.println(name + ":" + s);
        w.flush();
    }
}