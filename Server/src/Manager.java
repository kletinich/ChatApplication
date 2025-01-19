

/*
 * Manages the server side
 */

import java.util.ArrayList;

public class Manager {
    private Server _server;
    private UserList _users;

    public Manager(){
        this._server = new Server();
        this._users = new UserList();
    }

    public void start(){
        this._server.startServer();

        this._users.copyUsers(FileHandler.readUsersFromFile());
    }

    public void closeProgram(){
        this._server.closeServer();

        FileHandler.writeUsersToFile(this._users.getUsersWithPassword());
    }
}
