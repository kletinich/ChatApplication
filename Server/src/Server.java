import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/************************************************************************
 *                                                                      *
 *              Handles socket connections from users.                  *
 *      Each connected user is handled by ClientHandler class           *
 *                                                                      *
 ***********************************************************************/


public class Server {
    public static final String DEFAULT_IP_ADDRESS = "127.0.0.1";  
    public static final int DEFAULT_PORT = 8080;
    public static final int DEFAULT_MAX_NUM_OF_CONNECTIONS = 10;

    private String _ipAddress;                  // IP address of the server
    private int _port;                          // Port of the server

    private ServerSocket _server;               // The server socket

    private Vector<ClientHandler> _clients;     // Connected clients
    private ExecutorService _threadPool;        // Thread for each connected client
    private int _maxNumOfConnections;           // Maximum ammount of connected clients

    private UserList _users;                    // List of registered users

    public Server(){
        this(DEFAULT_IP_ADDRESS, DEFAULT_PORT, DEFAULT_MAX_NUM_OF_CONNECTIONS);
    }

    public Server(String ipAddress, int port, int maxNumOfConnections){
        this._ipAddress = ipAddress;
        this._port = port;
        this._maxNumOfConnections = maxNumOfConnections;

        this._users = null;

        this._clients = new Vector<>(this._maxNumOfConnections);
        this._threadPool = Executors.newFixedThreadPool(this._maxNumOfConnections);
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
            Socket socket;
            ClientHandler newClient;

            // Start the server and listen for connections
            this._server = new ServerSocket(this._port, this._maxNumOfConnections, bindAddress);
            System.out.println("Server listening at IP: " + this._ipAddress + " on port: " + this._port);

            while(true){

                // Accept connection with some client
                socket = this._server.accept();
                System.out.println("Accepted connection with:" + socket.getInetAddress().getHostAddress());
                
                // Handle the clients request in ClientHandler class
                newClient = new ClientHandler(this, socket, this._users.getUsersWithoutPassword());
                this._clients.add(newClient);
                this._threadPool.execute(newClient);
                //newClient.start();
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

        this._threadPool.shutdown();
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
