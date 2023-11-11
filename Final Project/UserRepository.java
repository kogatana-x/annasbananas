
/* Name: UserRepository
 * Description: Manages the users in the database
 * Parameters: connection - the connection to the database
 * Relationships: Uses the User class
 */


class UserRepository {
    private String filename="users.txt";
    Database UserDatabase = new Database(filename);

    /* Name: saveUser
     * Description: Saves a user to the database
     * Parameters: user - the user to save
     * Returns: none
     */
    public boolean saveUser(User user)  {

        int index = UserDatabase.isInDB(user.getUsername());
        if(index==-1){
            String row=user.getUsername()+","+user.getHash()+","+user.getXSalt()+","+user.getFirstname()+","+user.getLastname()+","+user.getCart();
            UserDatabase.add(row);
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
        String[] parts = UserDatabase.returnResult(username);
        if(parts.length>1){
            return new User(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5]);
        }
        return null;  
    }

}


