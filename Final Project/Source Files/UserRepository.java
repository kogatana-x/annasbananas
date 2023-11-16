//-------------------------------------------------------
/* Name: Anna Andler
 * Class: SE 450
 * Date: 11/15/2023
 * Project: Final Project - eCommerce Site (Anna's Bananas)
 * File Name: UserRepository.java
 */
//-------------------------------------------------------



/* Name: UserRepository
 * Description: Manages the users in the database
 * Parameters: connection - the connection to the database
 * Relationships: has a Database 
 */
class UserRepository {
    private String filename; //The name of the file to store the users in
    private Database UserDatabase; //The database to store the users in

    /* Name: UserRepository
     * Description: The constructor for the UserRepository class
     */
    public UserRepository(){
        filename="users.txt";
        UserDatabase = new Database(filename);
    }
    /* Name: UserRepository
     * Description: The constructor for the UserRepository class. Used for JUnit testing
     */
    public UserRepository(String filename){
        this.filename=filename;
        UserDatabase = new Database(filename);
    }
    /* Name: saveUser
     * Description: Saves a user to the database
     * Parameters: user - the user to save
     * Returns: boolean - whether the user was saved successfully
     */
    public boolean saveUser(User user)  {

        int index = UserDatabase.isInDB(user.getUsername()); //Check if the user is already in the database
        if(index==-1){ //If the user is not in the database, add it
            String row=user.getUsername()+","+user.getHash()+","+user.getXSalt()+","+user.getFirstname()+","+user.getLastname(); //Create the row to add to the database
            UserDatabase.add(row); //Add the row to the database
            return true;
        }
        return false;
    }

    
    /* Name: getUser
     * Description: Retrieves a user from the database
     * Parameters: username - the username of the user to retrieve
     * Returns: the user, or null if no user with that username exists
     */
    public User getUser(String username) {
        String partString = UserDatabase.returnResultRow("0",username); //Get the user from the database
        String[] parts=partString.split(","); //Split the user into its parts
        if(parts==null){return null; } //If the user does not exist, return null
        if(parts.length>1){ //If the user exists, return it
            return new User(parts[0], parts[1], parts[2], parts[3], parts[4],""); //Return the user
        }
        return null;  
    }

}


