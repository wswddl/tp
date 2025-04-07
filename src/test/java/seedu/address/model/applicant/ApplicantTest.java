package seedu.address.model.applicant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_JOB_POSITION_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RATING_ASSIGNED;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATUS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class ApplicantTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Applicant applicant = new PersonBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> applicant.getTags().remove(0));
    }

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(AMY.isSamePerson(AMY));

        // null -> returns false
        assertFalse(AMY.isSamePerson(null));

        // same phone and email, all other attributes different -> returns true
        Applicant editedAmy = new PersonBuilder(BOB).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
                .build();
        assertTrue(AMY.isSamePerson(editedAmy));

        // different phone, all other attributes same -> returns true
        editedAmy = new PersonBuilder(AMY).withPhone(VALID_PHONE_BOB).build();
        assertTrue(AMY.isSamePerson(editedAmy));

        // different phone and email, all other attributes same -> returns false
        editedAmy = new PersonBuilder(AMY).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(AMY.isSamePerson(editedAmy));

    }

    @Test
    public void equals() {
        // same values -> returns true
        Applicant aliceCopy = new PersonBuilder(AMY).build();
        assertTrue(AMY.equals(aliceCopy));

        // same object -> returns true
        assertTrue(AMY.equals(AMY));

        // null -> returns false
        assertFalse(AMY.equals(null));

        // different type -> returns false
        assertFalse(AMY.equals(5));

        // different applicant -> returns false
        assertFalse(AMY.equals(BOB));

        // different name -> returns false
        Applicant editedAmy = new PersonBuilder(AMY).withName(VALID_NAME_BOB).build();
        assertFalse(AMY.equals(editedAmy));

        // different phone -> returns false
        editedAmy = new PersonBuilder(AMY).withPhone(VALID_PHONE_BOB).build();
        assertFalse(AMY.equals(editedAmy));

        // different email -> returns false
        editedAmy = new PersonBuilder(AMY).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(AMY.equals(editedAmy));

        // different jobPosition -> returns false
        editedAmy = new PersonBuilder(AMY).withJobPosition(VALID_JOB_POSITION_BOB).build();
        assertFalse(AMY.equals(editedAmy));

        // different status -> returns false
        editedAmy = new PersonBuilder(AMY).withStatus(VALID_STATUS_BOB).build();
        assertFalse(AMY.equals(editedAmy));

        // different address -> returns false
        editedAmy = new PersonBuilder(AMY).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(AMY.equals(editedAmy));

        // different tags -> returns false
        editedAmy = new PersonBuilder(AMY).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(AMY.equals(editedAmy));

        // different rating -> returns false
        editedAmy = new PersonBuilder(AMY).withRating(VALID_RATING_ASSIGNED).build();
        assertFalse(AMY.equals(editedAmy));
    }

    @Test
    public void toStringMethod() {
        String expected = Applicant.class.getCanonicalName() + "{name=" + AMY.getName()
                + ", phone=" + AMY.getPhone() + ", email=" + AMY.getEmail()
                + ", jobPosition=" + AMY.getJobPosition() + ", status=" + AMY.getStatus()
                + ", address=" + AMY.getAddress() + ", tags=" + AMY.getTags()
                + ", rating=" + AMY.getRating() + "}";
        assertEquals(expected, AMY.toString());
    }
}
