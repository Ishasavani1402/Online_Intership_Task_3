import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatServer {
    public static void main(String[] args) throws IOException {
        int port = 12345;
        try (ServerSocket server_socket = new ServerSocket(port)) {
            System.out.println(" Server started. Listening for incoming connections...");

            try (Socket client_socket = server_socket.accept();
                    PrintWriter pr = new PrintWriter(client_socket.getOutputStream(), true); 
                    BufferedReader br = new BufferedReader(new InputStreamReader(client_socket.getInputStream()));
                    Scanner sc = new Scanner(System.in)) {// Create a server socket on port 12345

                    System.out.println("Client Connected..");

                    Thread t = new Thread(() -> {
                        try{
                            String message ;
                            while ((message = br.readLine())!=null) {
                                System.out.println("client msg :" + message);   
                            }
                        }catch(IOException e){
                            System.out.println(" Error in reading from client");
                        }
                    });

                    t.start();

                    //loop to get the message from the user(client)
                while (true) {
                    System.out.println("client says");
                    String server_msg = sc.nextLine();
                    pr.println(server_msg);
                    if (server_msg.equalsIgnoreCase("exit")) {
                        System.out.println("chat exit..");
                        break;
                    }
                }
            }
        }
    }
}
