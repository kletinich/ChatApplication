
/************************************************************
 *                                                          *
 *                   Manages the server side                *
 *              Contains the server, list of users.         *
 *                                                          *
 ***********************************************************/

 
public class Manager {
    private Server _server;         // The server
    private UserList _users;        // list of users

    public Manager(){
        this._server = new Server();
        this._users = new UserList();

        // Send the manager to the requestProccessor to have access to the server and user list
        RequestProcessor.initManager(this); 
    }

    /*
     * Start the manager:
     * Load the users from the save file.
     * Start the server.
     */
    public void start(){
        this._users.copyUsers(FileHandler.readUsersFromFile());
        this._server.setUsersList(this._users);
        this._server.startServer();
    }

    /*
     * Close the manager:
     * Save the users to the save file.
     * Close the server.
     */
    public void closeProgram(){
        FileHandler.writeUsersToFile(this._users.getUsersWithPassword());
        this._server.closeServer();
    }

    public Server getServer(){
        return this._server;
    }

    public UserList getUsers(){
        return this._users;
    }
}
