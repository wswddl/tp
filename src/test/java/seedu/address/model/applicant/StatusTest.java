package seedu.address.model.applicant;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class StatusTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Status(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidStatus = "";
        assertThrows(IllegalArgumentException.class, () -> new Status(invalidStatus));
    }

    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> Status.isValidStatus(null));

        // invalid name
        assertFalse(Status.isValidStatus("")); // empty string
        assertFalse(Status.isValidStatus(" ")); // spaces only
        assertFalse(Status.isValidStatus("^")); // only non-alphanumeric characters
        assertFalse(Status.isValidStatus("frontend*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(Status.isValidStatus("frontend engineer")); // alphabets only
        assertTrue(Status.isValidStatus("12345")); // numbers only
        assertTrue(Status.isValidStatus("l1 engineer")); // alphanumeric characters
        assertTrue(Status.isValidStatus("Frontend Engineer")); // with capital letters
        assertTrue(Status.isValidStatus("Second right hand man to the CEO")); // long names
    }

    @Test
    public void equals() {
        Status status = new Status("Valid Status");

        // same values -> returns true
        assertTrue(status.equals(new Status("Valid Status")));

        // same object -> returns true
        assertTrue(status.equals(status));

        // null -> returns false
        assertFalse(status.equals(null));

        // different types -> returns false
        assertFalse(status.equals(5.0f));

        // different values -> returns false
        assertFalse(status.equals(new Status("Other Valid Status")));
    }
}
