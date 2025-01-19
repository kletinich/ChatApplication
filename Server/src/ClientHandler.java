import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

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
        sendUsersListToClient();
        closeConnection();
    }

    public void sendUsersListToClient(){
        try{
            System.out.println(_users.toString());
            //this._out.writeObject(this._users);
            this._in.readObject();
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