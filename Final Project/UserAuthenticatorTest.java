import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class UserAuthenticatorTest {
    private UserRepository userRepository;
    private UserAuthenticator userAuthenticator;

    @Before
    public void setUp() {
        userRepository = new UserRepository("testusers.txt");
        userAuthenticator = new UserAuthenticator(userRepository);
    }

    @Test
    public void testRegister() {
        assertTrue(userAuthenticator.register("0testuser", "testpassword", "Test", "User"));
        assertNotNull(userRepository.getUser("0testuser"));
    }

    @Test
    public void testLogin() {
        userAuthenticator.register("1testuser", "testpassword", "Test", "User");
        assertTrue(userAuthenticator.login("1testuser", "testpassword"));
        assertFalse(userAuthenticator.login("1testuser", "wrongpassword"));
        assertFalse(userAuthenticator.login("nonexistentuser", "password"));
    }

    @Test
    public void testHashPassword() {
        byte[] salt = userAuthenticator.generateSalt();
        byte[] hash1 = userAuthenticator.hashPassword("testpassword", salt);
        byte[] hash2 = userAuthenticator.hashPassword("testpassword", salt);
        assertArrayEquals(hash1, hash2);
    }

    @Test
    public void testGenerateSalt() {
        byte[] salt1 = userAuthenticator.generateSalt();
        byte[] salt2 = userAuthenticator.generateSalt();
        assertNotEquals(salt1, salt2);
    }
}