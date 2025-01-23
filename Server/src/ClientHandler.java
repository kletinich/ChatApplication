import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.TreeMap;

import com.classes.*;

/*
 * Handles one connection with one clientHa
 */
public class ClientHandler extends Thread{
    private Server _server;
    private Socket _socket;
    
    private ObjectInputStream _in;
    private ObjectOutput _out;

    private ArrayList<User> _users;

    public ClientHandler(Server server, Socket socket, ArrayList<User> users){
        this._server = server;
        this._socket = socket;

        this._users = users;
        
        try {
            this._out = new ObjectOutputStream(this._socket.getOutputStream());
            this._in = new ObjectInputStream(this._socket.getInputStream());
        } catch (IOException e) {
            System.err.println("Error creating input or output stream");
        }
    }

    @Override
    public void run(){
    
        // to do: later add while(true loop until closing connection)

        TreeMap<String, Object> data = receiveRequest();
        TreeMap<String, Object> responseData = RequestProcessor.processRequest(data);
        
        // to do: do something with the response code. Create TreeMap to process a response

        closeConnection();
    }

    // wait for requests from the client
    public TreeMap<String, Object> receiveRequest(){
        
        try {
            TreeMap<String,Object> data = (TreeMap<String, Object>) this._in.readObject();
            return data;

        } catch (ClassNotFoundException e) {
            System.err.println("Class not found");
        } catch (IOException e) {
            System.err.println("Error receiving data from client");
        }

        return null;
    }

    /*
     * send users list to the client
     */
    public void sendUsersListToClient(){
        try{
            this._out.writeObject(this._users);
        }catch(IOException e){
            System.err.println("Failed to send list of users to client");
        }
    
    }

    /*
     * Close the input stream
     * Close the connection
     */
    public void closeConnection(){
        try {
            this._in.close();
            this._socket.close();
            System.out.println("Closed connection");
        } catch (IOException e) {
            System.err.println(this._socket.getInetAddress().getHostAddress() + ":");
            System.err.println("Failed to close input stream or socket");
        }
    }
}