package contact;

import java.util.Objects;

/**
 * Contact domain object with field constraints per requirements:
 * - contactId: required, unique, non-null, max 10 chars, NOT updatable
 * - firstName: required, non-null, max 10 chars
 * - lastName:  required, non-null, max 10 chars
 * - phone:     required, non-null, exactly 10 digits
 * - address:   required, non-null, max 30 chars
 */
public final class Contact {
    private final String contactId;     // not updatable
    private String firstName;
    private String lastName;
    private String phone;
    private String address;

    public Contact(String contactId, String firstName, String lastName, String phone, String address) {
        this.contactId = validateId(contactId);
        this.firstName = validateName("firstName", firstName);
        this.lastName  = validateName("lastName", lastName);
        this.phone     = validatePhone(phone);
        this.address   = validateAddress(address);
    }

    // --- Getters ---
    public String getContactId() { return contactId; }
    public String getFirstName() { return firstName; }
    public String getLastName()  { return lastName; }
    public String getPhone()     { return phone; }
    public String getAddress()   { return address; }

    // --- Updatable fields (with validation) ---
    public void setFirstName(String firstName) {
        this.firstName = validateName("firstName", firstName);
    }

    public void setLastName(String lastName) {
        this.lastName = validateName("lastName", lastName);
    }

    public void setPhone(String phone) {
        this.phone = validatePhone(phone);
    }

    public void setAddress(String address) {
        this.address = validateAddress(address);
    }

    // --- Validation helpers ---
    private static String validateId(String id) {
        Objects.requireNonNull(id, "contactId cannot be null");
        if (id.length() > 10) {
            throw new IllegalArgumentException("contactId max length is 10");
        }
        return id;
    }

    private static String validateName(String field, String value) {
        Objects.requireNonNull(value, field + " cannot be null");
        if (value.length() > 10) {
            throw new IllegalArgumentException(field + " max length is 10");
        }
        return value;
    }

    private static String validatePhone(String phone) {
        Objects.requireNonNull(phone, "phone cannot be null");
        if (!phone.matches("\\d{10}")) {
            throw new IllegalArgumentException("phone must be exactly 10 digits");
        }
        return phone;
    }

    private static String validateAddress(String address) {
        Objects.requireNonNull(address, "address cannot be null");
        if (address.length() > 30) {
            throw new IllegalArgumentException("address max length is 30");
        }
        return address;
    }
}
