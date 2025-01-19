import java.util.ArrayList;


/*
 * A container for users that registered to the app
 */
public class UserList {
    private ArrayList<ThisUser> users;
    
    private int numOfUsers;

    public UserList(){
        this.users = new ArrayList<>();
        this.numOfUsers = 0;
    }

    /*
     * Get the list of users with password (for server usage)
     */
    public ArrayList<ThisUser> getUsersWithPassword(){
        return this.users;
    }

    /*
     * Get the list of users without password (for client usage)
     */
    public ArrayList<User> getUsersWithoutPassword(){
        ArrayList<User> usersWithNoPassword = new ArrayList<>();

        for(ThisUser user: this.users){
            usersWithNoPassword.add((User)user);
        }

        return usersWithNoPassword;
    }

    /*
     * Add a user to the list if no user with the same username exists
     * Return true if user successfully added, false otherwise.
     */
    public boolean addUser(ThisUser newUser){
        String username = newUser.getUsername();
        for(ThisUser user: this.users){
            if(user.getUsername().equals(username)){
                return false;
            }
        }

        this.users.add(newUser);
        numOfUsers++;
        return true;
    }

    /*
     * Remove a user by username if exists in the list. 
     * Return true if user successfully removed, false otherwise.
     */
    public boolean removeUserByUsername(String username){
        for(ThisUser user: this.users){
            if(user.getUsername().equals(username)){
                this.users.remove(user);
                this.numOfUsers--;
                
                return true;
            }
        }

        return false;
    }
}
