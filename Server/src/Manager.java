import com.classes.*;

/*
 * Manages the server side
 */
public class Manager {
    private Server _server;
    private UserList _users;

    public Manager(){
        this._server = new Server();
        this._users = new UserList();

        RequestProcessor.initManager(this);
    }

    public void start(){
        this._users.copyUsers(FileHandler.readUsersFromFile());
        this._server.setUsersList(this._users);
        this._server.startServer();
    }

    public void closeProgram(){
        this._server.closeServer();

        FileHandler.writeUsersToFile(this._users.getUsersWithPassword());
    }

    public Server getServer(){
        return this._server;
    }

    public UserList getUsers(){
        return this._users;
    }
}
