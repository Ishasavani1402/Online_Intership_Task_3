import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


class Clienthandler implements Runnable{
    private Socket client_socket;

    public Clienthandler(Socket client_socket){
        this.client_socket = client_socket;
    }
    @Override
    public void run() {
        try(BufferedReader br = new BufferedReader(new InputStreamReader(client_socket.getInputStream()));
        PrintStream pr = new PrintStream(client_socket.getOutputStream(),true)){

            String clientmsg;
            while ((clientmsg=br.readLine())!=null) {
                System.out.println("Client says :"+ clientmsg);
                pr.println("Server received :"+clientmsg);

                if(clientmsg.equalsIgnoreCase("exit")){
                    System.out.println("Client Disconnect!!!");
                    break;
                }
            }

        }catch(IOException e){
            System.out.println("Error Handling Client..."+e.getMessage());
        }
        
    }
}

public class ChatServer {
    public static void main(String[] args) throws IOException {
        int port = 12345;
        ExecutorService executor = Executors.newFixedThreadPool(10);// thread pool for multiple cloent size 10
        try (ServerSocket server_socket = new ServerSocket(port)) {
            System.out.println(" Server started. Listening for incoming connections...");

            while (true) {
                Socket client_socket = server_socket.accept();
            
                System.out.println("Client Connected.."+ client_socket.getInetAddress());
                executor.execute(new Clienthandler(client_socket));
            }
        }
    }
}



