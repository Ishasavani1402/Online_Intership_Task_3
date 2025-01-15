import java.io.*;
import java.net.*;
import java.util.Scanner;
public class ChatClient {

    public static void main(String[] args) throws IOException {
        String host = "localhost";
        int port = 12345;

        try (Socket client_socket = new Socket(host, port); 
        PrintWriter pr = new PrintWriter(client_socket.getOutputStream(), true);
        BufferedReader br = new BufferedReader(new InputStreamReader(client_socket.getInputStream()));// Create a BufferedReader object to read the data from the input
        Scanner sc = new Scanner(System.in)) {
            System.out.println("Connected to server");  

            //thread for reading from server
            Thread t = new Thread(()->{
                try{
                    String line ;
                    while ((line = br.readLine())!=null) {
                        System.out.println("server says : "+line);
                        
                    }
                }catch(IOException e){
                    System.out.println("error reading from server" + e.getMessage());
                }
            });
            t.start();

            //loop for sending msg to server
            while (true) {
                System.out.println("msg :");
                String msg = sc.nextLine();
                pr.println(msg);
                if(msg.equalsIgnoreCase("exit")){
                    System.out.println("exit chat");
                    break;
                } 
            }
        }
    }
}
