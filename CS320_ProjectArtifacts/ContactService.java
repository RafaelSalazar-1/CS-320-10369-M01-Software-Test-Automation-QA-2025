package contact;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * In-memory Contact service:
 * - add contact with unique ID
 * - delete contact by ID
 * - update fields (firstName, lastName, phone, address) by ID
 */
public class ContactService {
    private final Map<String, Contact> store = new HashMap<>();

    /** Adds a new contact (throws if ID already exists). */
    public Contact addContact(Contact contact) {
        Objects.requireNonNull(contact, "contact cannot be null");
        String id = contact.getContactId();
        if (store.containsKey(id)) {
            throw new IllegalArgumentException("Contact ID already exists: " + id);
        }
        store.put(id, contact);
        return contact;
    }

    /** Convenience overload to build and add a contact in one call. */
    public Contact addContact(String id, String first, String last, String phone, String address) {
        return addContact(new Contact(id, first, last, phone, address));
    }

    /** Deletes a contact by ID (throws if missing). */
    public void deleteContact(String id) {
        Objects.requireNonNull(id, "id cannot be null");
        if (!store.containsKey(id)) {
            throw new IllegalArgumentException("No contact with ID: " + id);
        }
        store.remove(id);
    }

    /** Update firstName by ID. */
    public void updateFirstName(String id, String newFirst) {
        Contact c = getExisting(id);
        c.setFirstName(newFirst);
    }

    /** Update lastName by ID. */
    public void updateLastName(String id, String newLast) {
        Contact c = getExisting(id);
        c.setLastName(newLast);
    }

    /** Update phone by ID. */
    public void updatePhone(String id, String newPhone) {
        Contact c = getExisting(id);
        c.setPhone(newPhone);
    }

    /** Update address by ID. */
    public void updateAddress(String id, String newAddress) {
        Contact c = getExisting(id);
        c.setAddress(newAddress);
    }

    /** Read-only view (handy for assertions). */
    public Map<String, Contact> getAll() {
        return Collections.unmodifiableMap(store);
    }

    private Contact getExisting(String id) {
        Objects.requireNonNull(id, "id cannot be null");
        Contact c = store.get(id);
        if (c == null) {
            throw new IllegalArgumentException("No contact with ID: " + id);
        }
        return c;
    }
}

