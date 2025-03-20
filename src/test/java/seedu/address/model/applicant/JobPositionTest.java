package seedu.address.model.applicant;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

public class JobPositionTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new JobPosition(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidJobPosition = "";
        assertThrows(IllegalArgumentException.class, () -> new JobPosition(invalidJobPosition));
    }

    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> JobPosition.isValidJobPosition(null));

        // invalid name
        assertFalse(JobPosition.isValidJobPosition("")); // empty string
        assertFalse(JobPosition.isValidJobPosition(" ")); // spaces only
        assertFalse(JobPosition.isValidJobPosition("^")); // only non-alphanumeric characters
        assertFalse(JobPosition.isValidJobPosition("frontend*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(JobPosition.isValidJobPosition("frontend engineer")); // alphabets only
        assertTrue(JobPosition.isValidJobPosition("12345")); // numbers only
        assertTrue(JobPosition.isValidJobPosition("l1 engineer")); // alphanumeric characters
        assertTrue(JobPosition.isValidJobPosition("Frontend Engineer")); // with capital letters
        assertTrue(JobPosition.isValidJobPosition("Second right hand man to the CEO")); // long names
    }

    @Test
    public void equals() {
        JobPosition jobPosition = new JobPosition("Valid Job Position");

        // same values -> returns true
        assertTrue(jobPosition.equals(new JobPosition("Valid Job Position")));

        // same object -> returns true
        assertTrue(jobPosition.equals(jobPosition));

        // null -> returns false
        assertFalse(jobPosition.equals(null));

        // different types -> returns false
        assertFalse(jobPosition.equals(5.0f));

        // different values -> returns false
        assertFalse(jobPosition.equals(new JobPosition("Other Valid Job Position")));
    }
}
