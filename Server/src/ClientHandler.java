import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.TreeMap;

import com.classes.*;



/******************************************************************
 *                                                                *
 *                Handles requests from one client                *
 *     Receives requests, processes them and send responses       *
 *                                                                *
 *****************************************************************/


public class ClientHandler extends Thread{
    private Server _server;             // Server class
    private Socket _socket;             // this socket
    
    private ObjectInputStream _in;      // reading objects from the client
    private ObjectOutput _out;          // writing objects to the client

    private ArrayList<User> _users;     // list of users without their password

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

        // receive a request from the client and process it
        TreeMap<String, Object> data = receiveRequest();
        TreeMap<String, Object> responseData = RequestProcessor.processRequest(data);
        
        // send the response of the request back to the client and close
        sendResponse(responseData);
        closeConnection();
    }

    // wait for requests from the client
    public TreeMap<String, Object> receiveRequest(){
        
        try {
            @SuppressWarnings("unchecked")
            TreeMap<String,Object> data = (TreeMap<String, Object>) this._in.readObject();
            return data;

        } catch (ClassNotFoundException e) {
            System.err.println("Class not found");
        } catch (IOException e) {
            System.err.println("Error receiving data from client");
        }

        return null;
    }

    // send response to client
    public void sendResponse(TreeMap<String, Object> responseData){
        try {
            this._out.writeObject(responseData);
        } catch (IOException e) {
            System.err.println("Error sending data to client");
        }
    }

    
    //send users list to the client
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