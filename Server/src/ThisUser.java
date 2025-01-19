public class ThisUser extends User{
    private String _password;

    ThisUser(String username, String password){
        super(username);
        this._password = password;
    }

    ThisUser(String firstName, String lastName, String username, String password){
        super(firstName, lastName, username);
        this._password = password;
    }

    public String getPassword(){
        return this._password;
    }

    public void setUsername(String username){
        this._username = username;
    }

    public void setPassword(String password){
        this._password = password;
    }
}
