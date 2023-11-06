
/* Name: UserRepository
 * Description: Manages the users in the database
 * Parameters: connection - the connection to the database
 * Relationships: Uses the User class
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class UserRepository {
    private String filename="users.txt";

    /* Name: saveUser
     * Description: Saves a user to the database
     * Parameters: user - the user to save
     * Returns: none
     */
    public boolean saveUser(User user)  {
        User rez = getUser(user.getUsername());
        if(rez==null){
            Database UserDatabase = new Database("users.txt");
            String row=user.getUid()+","+user.getUsername()+","+user.getHash()+","+user.getSalt();
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
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2 && parts[1].equals(username)) {
                    // Found the user, return the index
                    return new User(Integer.parseInt(parts[0]), parts[1],parts[3],parts[4]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // No user with that username exists
        return null;
    }
}


