import java.util.TreeMap;

import com.classes.Codes;
import com.classes.RequestPack;
import com.classes.ResponsePack;
import com.classes.ThisUser;



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

                // user request for register
                case Codes.REGISTER_REQUEST:
                    return proccessRegisterRequest(data);

                // user request for login
                case Codes.LOGIN_REQUEST:
                    return proccessLoginRequest(data);

                // user request for getting list of users
                case Codes.GET_USERS_REQUEST:
                    return proccessGetUsersRequest();
                
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
     * Process A register request.
     * Return a response treeMap.
     * Assumption - data is valid
     */
    private static TreeMap<String, Object> proccessRegisterRequest(TreeMap<String, Object> validData){
        TreeMap<String, Object> responseData = new TreeMap<>();

        String username = (String)validData.get("username");

        // check if user with the same username is registered
        boolean isRegistered = _manager.getUsers().isRegistered(username);

        // user already exists
        if(isRegistered){
            responseData.put("response_code", Codes.REGISTER_FAILED_RESPONSE);
        }

        // user doesn't exists. Save his data
        else{
            responseData.put("response_code", Codes.REGISTER_SUCCESS_RESPONSE);
            
            String password = (String)validData.get("password");
            String firstName = (String)validData.get("first_name");
            String lastName = (String)validData.get("last_name");

            ThisUser newUser = new ThisUser(username, password, firstName, lastName);

            _manager.getUsers().addUser(newUser);
            FileHandler.addUserToFile(newUser);
        }

        return responseData;
    }


    /*
     * Process A login request.
     * Return a response treeMap.
     * Assumption - data is valid
     */
    private static TreeMap<String, Object> proccessLoginRequest(TreeMap<String, Object> validData){
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

    private static TreeMap<String, Object> proccessGetUsersRequest(){
        TreeMap<String, Object> responseData = new TreeMap<>();

        if(_manager == null){
            responseData.put("response_code", Codes.GET_USERS_FAIL_RESPONSE);
        }
    
        else{
            responseData.put("response_code", Codes.GET_USERS_SUCCESS_RESPONSE);
            responseData.put("users_list", _manager.getUsers().getUsersWithoutPassword());
        }

        return responseData;
    }

}

