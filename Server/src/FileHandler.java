import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import com.classes.*;



/****************************************************************
 *                                                              *
 *             Read and write data to files                     *
 *        Handles the save and load of registered users         *
 *              TODO: later will handle chat history            *
 *                                                              *
 ***************************************************************/



public class FileHandler {
    public static final String FILE_NAME = "users.info";

    // Save the list of registered clients to users.info file
    public static void writeUsersToFile(ArrayList<ThisUser> users){
        try{
            FileWriter usersFile = new FileWriter(FILE_NAME, false);
            String info = "";

            // info string for each user
            for(ThisUser user : users){
                info = user.getFirstName() + " " +
                        user.getLastName() + " " +
                        user.getUsername() + " " +
                        user.getPassword();

                // append the user data to the file
                usersFile.append(info);
                usersFile.append("\n");
            } 

            usersFile.close();
            System.out.println("Users data written to file");

        } catch (IOException e) {
            System.err.println("Error writing to file");
        }
    }

    // save a new user to the users.info file
    public static void addUserToFile(ThisUser newUser){
        try{
            FileWriter usersFile = new FileWriter(FILE_NAME, true);
            String info = newUser.getFirstName() + " " +
            newUser.getLastName() + " " +
            newUser.getUsername() + " " +
            newUser.getPassword();

            usersFile.append(info);

            usersFile.close();
            System.out.println("Users data written to file");

        } catch (IOException e) {
            System.err.println("Error writing to file");
        }
    }

    // Load the list of registerd clients from users.info file
    public static ArrayList<ThisUser> readUsersFromFile(){
        ArrayList<ThisUser> users = new ArrayList<>();
        
        try  {
            FileReader usersFile = new FileReader(FILE_NAME);
            BufferedReader usersReader = new BufferedReader(usersFile);
            String userInfo;

            // read all lines. Each line is one user
            while((userInfo = usersReader.readLine()) != null){
                String[] parsedInfo = userInfo.split(" ");
                String firstName = parsedInfo[0];
                String lastName = parsedInfo[1];
                String username = parsedInfo[2];
                String password = parsedInfo[3];
                
                // add the data of the user to the list of users
                users.add(new ThisUser(username, password, firstName, lastName));
            }

            usersReader.close();

        } catch (IOException e) {
            System.err.println("Error writing from file");
        }

        return users;
    }
}
