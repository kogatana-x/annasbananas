import org.junit.Test;
import static org.junit.Assert.*;

public class DatabaseTest {

    @Test
    public void testIsInDB() {
        Database db = new Database("testdb.txt");
        db.add("3,John,Doe");
        db.add("4,Jane,Smith");
        assertNotEquals(-1, db.isInDB("3"));
        assertNotEquals(-1, db.isInDB("4"));
        assertEquals(-1, db.isInDB("55"));
    }

    @Test
    public void testReturnResultRow() {
        Database db = new Database("testdb.txt");
        db.add("5,John,Doe");
        db.add("6,Jane,Smith");
        assertEquals("5,John,Doe", db.returnResultRow("0", "5"));
        assertEquals("6,Jane,Smith", db.returnResultRow("0", "6"));
        assertEquals("error", db.returnResultRow("0", "3"));
    }

    @Test
    public void testReturnFieldValue() {
        Database db = new Database("testdb.txt");
        db.add("7,John,Doe");
        db.add("8,Jane,Smith");
        assertEquals("John", db.returnFieldValue("7,John,Doe", 1).trim());
        assertEquals("Smith", db.returnFieldValue("8,Jane,Smith", 2).trim());
        assertEquals("error", db.returnFieldValue("0", 1).trim());
    }

    @Test
    public void testUpdate() {
        Database db = new Database("testdb.txt");
        db.add("9,John,Doe");
        db.add("10,Jane,Smith");
        db.update("9", "1", "Jack");
        String expected="9,Jack,Doe";
        assertEquals(expected, db.returnResultRow("1","Jack"));
    }

    @Test
    public void testDelete() {
        Database db = new Database("testdb.txt");
        db.add("11,John,Doe");
        db.add("12,Jane,Smith");
        db.delete(db.isInDB("11"));
        assertEquals(-1, db.isInDB("11"));
        
    }
}