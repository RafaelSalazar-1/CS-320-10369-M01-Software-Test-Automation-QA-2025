package contact;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ContactServiceTest {

    private ContactService service;

    @BeforeEach
    void setup() {
        service = new ContactService();
    }

    @Test
    void addContactWithUniqueId() {
        Contact c = service.addContact("A1", "Raf", "S", "4805551234", "Addr");
        assertEquals(1, service.getAll().size());
        assertEquals("A1", c.getContactId());
    }

    @Test
    void addingDuplicateIdThrows() {
        service.addContact("A1", "Raf", "S", "4805551234", "Addr");
        assertThrows(IllegalArgumentException.class,
                () -> service.addContact("A1", "Ray", "Sal", "6025559999", "Addr2"));
    }

    @Test
    void deleteByIdRemovesContact() {
        service.addContact("A1", "Raf", "S", "4805551234", "Addr");
        service.deleteContact("A1");
        assertTrue(service.getAll().isEmpty());
    }

    @Test
    void deleteMissingIdThrows() {
        assertThrows(IllegalArgumentException.class, () -> service.deleteContact("NOPE"));
    }

    @Test
    void updateFieldsById() {
        service.addContact("A1", "Raf", "S", "4805551234", "Addr");
        service.updateFirstName("A1", "Ray");
        service.updateLastName("A1", "Sal");
        service.updatePhone("A1", "6025559999");
        service.updateAddress("A1", "New Address");

        Contact c = service.getAll().get("A1");
        assertEquals("Ray", c.getFirstName());
        assertEquals("Sal", c.getLastName());
        assertEquals("6025559999", c.getPhone());
        assertEquals("New Address", c.getAddress());
    }

    @Test
    void updateMissingContactThrows() {
        assertThrows(IllegalArgumentException.class, () -> service.updateFirstName("X", "Y"));
        assertThrows(IllegalArgumentException.class, () -> service.updateLastName("X", "Y"));
        assertThrows(IllegalArgumentException.class, () -> service.updatePhone("X", "1234567890"));
        assertThrows(IllegalArgumentException.class, () -> service.updateAddress("X", "Z"));
    }

    @Test
    void updatesEnforceValidationRules() {
        service.addContact("A1", "Raf", "S", "4805551234", "Addr");
        assertThrows(IllegalArgumentException.class, () -> service.updateFirstName("A1", "01234567890"));
        assertThrows(IllegalArgumentException.class, () -> service.updateLastName("A1", "01234567890"));
        assertThrows(IllegalArgumentException.class, () -> service.updatePhone("A1", "12345"));
        assertThrows(IllegalArgumentException.class, () -> service.updateAddress("A1", "x".repeat(31)));
    }
}
