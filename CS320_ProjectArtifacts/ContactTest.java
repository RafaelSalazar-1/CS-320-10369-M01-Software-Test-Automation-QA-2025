package contact;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ContactTest {

    @Test
    void createsContactWhenAllFieldsValid() {
        Contact c = new Contact("ABC123", "Raf", "Salazar", "4805551234", "123 Desert Rd, Phoenix");
        assertEquals("ABC123", c.getContactId());
        assertEquals("Raf", c.getFirstName());
        assertEquals("Salazar", c.getLastName());
        assertEquals("4805551234", c.getPhone());
        assertEquals("123 Desert Rd, Phoenix", c.getAddress());
    }

    @Test
    void contactIdMax10AndNotNull() {
        assertThrows(NullPointerException.class,
                () -> new Contact(null, "Raf", "S", "4805551234", "Addr"));
        assertThrows(IllegalArgumentException.class,
                () -> new Contact("TOO-LONG-ID", "Raf", "S", "4805551234", "Addr"));
    }

    @Test
    void firstNameRequiredMax10() {
        assertThrows(NullPointerException.class,
                () -> new Contact("ID", null, "S", "4805551234", "Addr"));
        assertThrows(IllegalArgumentException.class,
                () -> new Contact("ID", "ABCDEFGHIJKLMNOP", "S", "4805551234", "Addr"));
    }

    @Test
    void lastNameRequiredMax10() {
        assertThrows(NullPointerException.class,
                () -> new Contact("ID", "Raf", null, "4805551234", "Addr"));
        assertThrows(IllegalArgumentException.class,
                () -> new Contact("ID", "Raf", "TOO-LONG-NAME", "4805551234", "Addr"));
    }

    @Test
    void phoneExactly10Digits() {
        assertThrows(NullPointerException.class,
                () -> new Contact("ID", "Raf", "S", null, "Addr"));
        assertThrows(IllegalArgumentException.class,
                () -> new Contact("ID", "Raf", "S", "123456789", "Addr"));     // 9 digits
        assertThrows(IllegalArgumentException.class,
                () -> new Contact("ID", "Raf", "S", "12345678901", "Addr"));   // 11 digits
        assertThrows(IllegalArgumentException.class,
                () -> new Contact("ID", "Raf", "S", "12345abcde", "Addr"));    // non-digits
    }

    @Test
    void addressRequiredMax30() {
        assertThrows(NullPointerException.class,
                () -> new Contact("ID", "Raf", "S", "4805551234", null));
        String longAddr = "1234567890123456789012345678901"; // 31 chars
        assertThrows(IllegalArgumentException.class,
                () -> new Contact("ID", "Raf", "S", "4805551234", longAddr));
    }

    @Test
    void idIsNotUpdatableButOtherFieldsAre() {
        Contact c = new Contact("LOCKEDID", "Raf", "S", "4805551234", "Addr");
        // No setter for contactId; we verify others can be changed
        c.setFirstName("Ray");
        c.setLastName("Sal");
        c.setPhone("6025559999");
        c.setAddress("New Address");
        assertEquals("LOCKEDID", c.getContactId());
        assertEquals("Ray", c.getFirstName());
        assertEquals("Sal", c.getLastName());
        assertEquals("6025559999", c.getPhone());
        assertEquals("New Address", c.getAddress());
    }

    @Test
    void settersEnforceConstraints() {
        Contact c = new Contact("ID", "Raf", "S", "4805551234", "Addr");
        assertThrows(IllegalArgumentException.class, () -> c.setFirstName("01234567890")); // 11 chars
        assertThrows(IllegalArgumentException.class, () -> c.setLastName("01234567890"));  // 11 chars
        assertThrows(IllegalArgumentException.class, () -> c.setPhone("111"));             // not 10 digits
        assertThrows(IllegalArgumentException.class, () -> c.setAddress("x".repeat(31)));  // 31 chars
    }
}
