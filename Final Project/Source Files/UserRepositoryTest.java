import org.junit.Test;
import static org.junit.Assert.*;

public class UserRepositoryTest {
    
    @Test
    public void testSaveUser() {
        UserRepository userRepository = new UserRepository("testusers.txt");
        User user = new User("testuser1", "password", "salt", "John", "Doe","Session");
        boolean result = userRepository.saveUser(user);
        assertTrue(result);
    }
    
    @Test
    public void testGetUser() {
        UserRepository userRepository = new UserRepository("testusers.txt");
        User user = new User("testuser2", "password", "salt", "John", "Doe","Session");
        userRepository.saveUser(user);
        User result = userRepository.getUser("testuser2");
        assertEquals(user.getFirstname(), result.getFirstname());
    }
    
    @Test
    public void testGetNonExistentUser() {
        UserRepository userRepository = new UserRepository("testusers.txt");
        User result = userRepository.getUser("nonexistentuser");
        assertNull(result);
    }
}