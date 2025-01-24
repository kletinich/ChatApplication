import java.util.TreeMap;

import com.classes.Codes;
import com.classes.RequestPack;



/****************************************************
 *                                                  *
 *          Process requests from the user          *
 *                                                  *
 ***************************************************/



public abstract class RequestProcessor {

    private static Manager _manager = null;

    public static void initManager(Manager manager){
        _manager = manager;
    }

    /*
     * Proccess general requests from the client.
     * Receive a general request.
     * Send the request to the specific request processor function
     * Return the correct response data treeMap after processing the request
     */
    public static TreeMap<String, Object> processRequest(TreeMap<String, Object> data){

        // check if request is valid by format
        boolean isValid = RequestPack.isValidRequest(data);
        TreeMap<String, Object> responseData = null;

        // valid request format
        if(isValid){

            switch((int)data.get("request_code")){

                // user request for login
                case Codes.LOGIN_REQUEST:
                    return processLoginRequest(data);

                // request is not part of the list of known requests
                default:
                    responseData = new TreeMap<>();
                    responseData.put("response_code", Codes.UNKNOWN_REQUEST);
                    return responseData;
            }
        }

        // not a valid request format
        responseData = new TreeMap<>();
        responseData.put("response_code", Codes.NOT_VALID_REQUEST);
        return responseData;
    }

    /*
     * Process A login request.
     * Return a response treeMap.
     * Assumption - data is valid
     */
    private static TreeMap<String, Object> processLoginRequest(TreeMap<String, Object> validData){
        TreeMap<String, Object> responseData = new TreeMap<>();

        String username = (String)validData.get("username");
        String password = (String)validData.get("password");

        // check if user is registered
        boolean isRegistered = _manager.getUsers().isRegistered(username, password);

        // user is not registered
        if(!isRegistered){
            responseData.put("response_code", Codes.LOGIN_FAILED_RESPONSE);
            return responseData;
        }

        // user is registered, return the full user data
        responseData.put("response_code", Codes.LOGIN_SUCCESS_RESPONSE);
        responseData.put("first_name", _manager.getUsers().getFirstNameByUsername(username));
        responseData.put("last_name", _manager.getUsers().getLastNameByUsername(username));
        
        return responseData;
    }
}
