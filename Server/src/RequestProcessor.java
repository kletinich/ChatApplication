import java.util.TreeMap;

import com.classes.Codes;
import com.classes.RequestPack;


/*
 * Process requests from the user
 */
public abstract class RequestProcessor {

    private static Manager _manager = null;

    public static void initManager(Manager manager){
        _manager = manager;
    }

    /*
     * Proccess general requests from the client.
     * Receive a general request.
     * Send the request to the specific request processor function
     * Return the correct response code after processing the request
     */
    public static int processRequest(TreeMap<String, Object> data){

        boolean isValid = RequestPack.isValidRequest(data);

        if(isValid){

            //user request for login
            switch((int)data.get("request_code")){
                case Codes.LOGIN_REQUEST:
                    return processLoginRequest(data);

                default:
                    return -1; // to do: add code for not valid request code
            }
        }

        return -1; // to do: add code for not valid request
    }

    /*
     * Process A login request
     * Return 
     */
    private static int processLoginRequest(TreeMap<String, Object> data){
        String username = (String)data.get("username");
        String password = (String)data.get("password");

        boolean isRegistered = _manager.getUsers().isRegistered(username, password);

        if(isRegistered){
            return Codes.LOGIN_FAILED_RESPONSE;
        }

        return Codes.LOGIN_SUCCESS_RESPONSE;
    }
}
