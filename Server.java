import java.net.ServerSocket;
import java.net.Socket;

public class Server {


    public static void main(String[] args) {

        try {
            ServerSocket ss = new ServerSocket(11111);
            while(true) {
                Socket client_socket = ss.accept();
                System.out.println("accepted");
                ClientHandler ch = new ClientHandler(client_socket);
                ch.start();
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
