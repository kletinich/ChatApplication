import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler extends Thread{
    private Server _server;
    private Socket _socket;

    private DataInputStream _in;
    private DataOutput _out;

    public ClientHandler(Server server, Socket socket){
        this._server = server;
        this._socket = socket;

        try {
            this._in = new DataInputStream(socket.getInputStream());
            this._out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            System.err.println("Error creating input or output stream");
        }
    }

    @Override
    public void run(){

    }

    /*
     * Close the input stream
     * Close the connection
     */
    public void closeConnection(){
        try {
            this._in.close();
            this._socket.close();
        } catch (IOException e) {
            System.err.println(this._socket.getInetAddress().getHostAddress() + ":");
            System.err.println("Failed to close input stream or socket");
        }
    }
}