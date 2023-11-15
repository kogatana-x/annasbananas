import org.junit.Test;
import static org.junit.Assert.*;

public class DatabaseTest {

    @Test
    public void testAddAndGetAll() {
        Database db = new Database("testdb.txt");
        db.add("1,John,Doe");
        db.add("2,Jane,Smith");
        String[] expected = {"1,John,Doe", "2,Jane,Smith"};
        assertArrayEquals(expected, db.getAll());
    }

    @Test
    public void testIsInDB() {
        Database db = new Database("testdb.txt");
        db.add("1,John,Doe");
        db.add("2,Jane,Smith");
        assertEquals(0, db.isInDB("1"));
        assertEquals(1, db.isInDB("2"));
        assertEquals(-1, db.isInDB("3"));
    }

    @Test
    public void testReturnResultRow() {
        Database db = new Database("testdb.txt");
        db.add("1,John,Doe");
        db.add("2,Jane,Smith");
        assertEquals("1,John,Doe", db.returnResultRow("0", "1"));
        assertEquals("2,Jane,Smith", db.returnResultRow("0", "2"));
        assertEquals("error", db.returnResultRow("0", "3"));
    }

    @Test
    public void testReturnFieldValue() {
        Database db = new Database("testdb.txt");
        db.add("1,John,Doe");
        db.add("2,Jane,Smith");
        assertEquals("John", db.returnFieldValue("1,John,Doe", 1));
        assertEquals("Smith", db.returnFieldValue("2,Jane,Smith", 2));
        assertEquals("error", db.returnFieldValue("1,John,Doe", 3));
    }

    @Test
    public void testUpdate() {
        Database db = new Database("testdb.txt");
        db.add("1,John,Doe");
        db.add("2,Jane,Smith");
        db.update("1", "1", "Jack");
        String[] expected = {"2,Jane,Smith", "1,Jack,Doe"};
        assertArrayEquals(expected, db.getAll());
    }

    @Test
    public void testDelete() {
        Database db = new Database("testdb.txt");
        db.add("1,John,Doe");
        db.add("2,Jane,Smith");
        db.delete(0);
        String[] expected = {"2,Jane,Smith"};
        assertArrayEquals(expected, db.getAll());
    }
}