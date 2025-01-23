import java.util.ArrayList;
import com.classes.*;

/*
 * A container for users that registered to the app
 */
public class UserList {
    private ArrayList<ThisUser> _users;
    
    private int _numOfUsers;

    public UserList(){
        this._users = new ArrayList<>();
        this._numOfUsers = 0;
    }

    /*
     * Get the list of users with password (for server usage)
     */
    public ArrayList<ThisUser> getUsersWithPassword(){
        return this._users;
    }

    /*
     * Get the list of users without password (for client usage)
     */
    public ArrayList<User> getUsersWithoutPassword(){
        ArrayList<User> usersWithNoPassword = new ArrayList<>();
    
        for(ThisUser user: this._users){
            usersWithNoPassword.add(new User(user.getFirstName(), user.getLastName(), user.getUsername()));
        }

        return usersWithNoPassword;
    }

    /*
     * copy all the users data to this class
     */
    public void copyUsers(ArrayList<ThisUser> users){
        this._users = users;
    }

    /*
     * Add a user to the list if no user with the same username exists
     * Return true if user successfully added, false otherwise.
     */
    public boolean addUser(ThisUser newUser){
        String username = newUser.getUsername();
        for(ThisUser user: this._users){
            if(user.getUsername().equals(username)){
                return false;
            }
        }

        this._users.add(newUser);
        this._numOfUsers++;
        return true;
    }

    /*
     * Remove a user by username if exists in the list. 
     * Return true if user successfully removed, false otherwise.
     */
    public boolean removeUserByUsername(String username){
        for(ThisUser user: this._users){
            if(user.getUsername().equals(username)){
                this._users.remove(user);
                this._numOfUsers--;
                
                return true;
            }
        }

        return false;
    }

    /*
     * Check if a given username and passowrd belong to a registered user
     */
    public boolean isRegistered(String username, String password){
        for(ThisUser user: this._users){
            System.out.println("This: " + user.getUsername() + " other: " + username);
            System.out.println("This: " + user.getPassword() + " other: " + password);
            if(user.getUsername().equals(username)){
                if(user.getPassword().equals(password)){
                    return true;
                }

                return false;
            }
        }

        return false;
    }
}
