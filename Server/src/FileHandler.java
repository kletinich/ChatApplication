import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import com.classes.*;

/*
 * A class for reading and writing data with files
 */
public class FileHandler {
    public static final String FILE_NAME = "users.info";

    public static void writeUsersToFile(ArrayList<ThisUser> users){
        try{
            FileWriter usersFile = new FileWriter(FILE_NAME, false);
            String info = "";

            for(ThisUser user : users){
                info = user.getFirstName() + " " +
                        user.getLastName() + " " +
                        user.getUsername() + " " +
                        user.getPassword();

                        usersFile.append(info);
                        usersFile.append("\n");
            } 

            usersFile.close();

            System.out.println("Users data written to file");

        } catch (IOException e) {
            System.err.println("Error writing to file");
        }
    }

    public static ArrayList<ThisUser> readUsersFromFile(){
        ArrayList<ThisUser> users = new ArrayList<>();
        
        try  {
            FileReader usersFile = new FileReader(FILE_NAME);
            BufferedReader usersReader = new BufferedReader(usersFile);
            String userInfo;

            while((userInfo = usersReader.readLine()) != null){
                String[] parsedInfo = userInfo.split(" ");
                String firstName = parsedInfo[0];
                String lastName = parsedInfo[1];
                String username = parsedInfo[2];
                String password = parsedInfo[3];
                
                users.add(new ThisUser(firstName, lastName, username, password));
            }

            usersReader.close();

        } catch (IOException e) {
            System.err.println("Error writing from file");
        }

        return users;
    }
}
