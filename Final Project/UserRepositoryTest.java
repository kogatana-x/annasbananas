import org.junit.Test;
import static org.junit.Assert.*;

public class UserRepositoryTest {
    
    @Test
    public void testSaveUser() {
        UserRepository userRepository = new UserRepository();
        User user = new User("testuser", "password", "salt", "John", "Doe","Session");
        boolean result = userRepository.saveUser(user);
        assertTrue(result);
    }
    
    @Test
    public void testGetUser() {
        UserRepository userRepository = new UserRepository();
        User user = new User("testuser", "password", "salt", "John", "Doe","Session");
        userRepository.saveUser(user);
        User result = userRepository.getUser("testuser");
        assertEquals(user, result);
    }
    
    @Test
    public void testGetNonexistentUser() {
        UserRepository userRepository = new UserRepository();
        User result = userRepository.getUser("nonexistentuser");
        assertNull(result);
    }
}