import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import com.classes.*;

import java.util.Vector;

/*
 * Handles socket connections from users.
 * Each connected user is handled by ClientHandler class
 */
public class Server {
    public static final String DEFAULT_IP_ADDRESS = "127.0.0.1";
    public static final int DEFAULT_PORT = 8080;
    public static final int DEFAULT_MAX_NUM_OF_CONNECTIONS = 10;

    private String _ipAddress;
    private int _port;

    private ServerSocket _server;
    private Socket _socket;

    private Vector<ClientHandler> _clients;
    private int _maxNumOfConnections;

    private UserList _users;

    public Server(){
        this(DEFAULT_IP_ADDRESS, DEFAULT_PORT, DEFAULT_MAX_NUM_OF_CONNECTIONS);
    }

    public Server(String ipAddress, int port, int maxNumOfConnections){
        this._ipAddress = ipAddress;
        this._port = port;
        this._maxNumOfConnections = maxNumOfConnections;

        this._users = null;

        this._clients = new Vector<>(this._maxNumOfConnections);
    }

    /*
     * Start the server:
     * Bind to ip address
     * listen to clients
     * client connects - pass the handle to the ClientHandler
     */
    public void startServer(){
        try{
            InetAddress bindAddress = InetAddress.getByName(this._ipAddress);

            this._server = new ServerSocket(this._port, this._maxNumOfConnections, bindAddress);
            System.out.println("Server started at IP: " + this._ipAddress + " on port: " + this._port);

            while(true){
                this._socket = this._server.accept();
                System.out.println("Accepted connection with:" + this._socket.getInetAddress().getHostAddress());
                
                ClientHandler newClient = new ClientHandler(this, this._socket, this._users.getUsersWithoutPassword());
                this._clients.add(newClient);
        
                newClient.start();
            }

        }catch(IOException e){
            System.err.println("Error creating server");
        }
    }

    /*
     * Iterating over the connected clients
     * Close their connection
     * Remove them from the list of connected clients
     */
    public void closeClients(){
        for(ClientHandler client: this._clients){
            client.closeConnection();
        }

        this._clients.removeAllElements();
    }

    /*
     * Close all the connected clients
     * Close the server
     */
    public void closeServer(){
        this.closeClients();
        try {
            this._server.close();
        } catch (IOException e) {
            System.err.println("Failed  to close server");
        }
    }

    public void setUsersList(UserList users){
        this._users = users;
    }
}
