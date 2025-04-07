package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.applicant.Applicant;
import seedu.address.model.applicant.Status;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code UpdateCommand}.
 */
public class UpdateCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final Status newStatus = new Status("Interview Scheduled");

    // ===== Index-based Update Tests =====

    /**
     * EP: Valid index within bounds, force update
     * BV: First index in unfiltered list
     */
    @Test
    public void execute_validIndexUnfilteredList_forceUpdateSuccess() {
        Applicant applicantToUpdate = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        UpdateCommand updateCommand = new UpdateCommand(INDEX_FIRST_PERSON, newStatus, true);

        String expectedMessage = String.format(UpdateCommand.MESSAGE_UPDATE_STATUS_SUCCESS,
                Messages.format(applicantToUpdate));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setStatus(applicantToUpdate, newStatus);

        assertCommandSuccess(updateCommand, model, expectedMessage, expectedModel);
    }

    /**
     * EP: Valid index within bounds, needs confirmation
     * BV: Last index in unfiltered list
     */
    @Test
    public void execute_validIndexUnfilteredList_needsConfirmation() {
        Index lastIndex = Index.fromOneBased(model.getFilteredPersonList().size());
        UpdateCommand updateCommand = new UpdateCommand(lastIndex, newStatus, false);

        String expectedMessage = String.format(UpdateCommand.MESSAGE_ID_UPDATE_CONFIRMATION,
                lastIndex.getOneBased());

        assertCommandSuccess(updateCommand, model, expectedMessage, null);
    }

    /**
     * EP: Invalid index (out of bounds)
     * BV: size + 1
     */
    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        UpdateCommand updateCommand = new UpdateCommand(outOfBoundIndex, newStatus, false);

        assertCommandFailure(updateCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * EP: Valid index in filtered list, force update
     */
    @Test
    public void execute_validIndexFilteredList_forceUpdateSuccess() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Applicant applicantToUpdate = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        UpdateCommand updateCommand = new UpdateCommand(INDEX_FIRST_PERSON, newStatus, true);

        String expectedMessage = String.format(UpdateCommand.MESSAGE_UPDATE_STATUS_SUCCESS,
                Messages.format(applicantToUpdate));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setStatus(applicantToUpdate, newStatus);
        showNoPerson(expectedModel);

        assertCommandSuccess(updateCommand, model, expectedMessage, expectedModel);
    }

    // ===== Predicate-based Update Tests =====

    // Note: Add tests for predicate-based updates once implemented

    // ===== Unit Tests =====

    @Test
    public void equals() {
        UpdateCommand updateFirstCommand = new UpdateCommand(INDEX_FIRST_PERSON, newStatus, false);
        UpdateCommand updateSecondCommand = new UpdateCommand(INDEX_SECOND_PERSON, newStatus, false);

        // same object -> returns true
        assertTrue(updateFirstCommand.equals(updateFirstCommand));

        // same values -> returns true
        UpdateCommand updateFirstCommandCopy = new UpdateCommand(INDEX_FIRST_PERSON, newStatus, false);
        assertTrue(updateFirstCommand.equals(updateFirstCommandCopy));

        // different types -> returns false
        assertFalse(updateFirstCommand.equals(1));

        // null -> returns false
        assertFalse(updateFirstCommand.equals(null));

        // different applicant -> returns false
        assertFalse(updateFirstCommand.equals(updateSecondCommand));

        // different force flag -> returns false
        UpdateCommand updateFirstCommandForce = new UpdateCommand(INDEX_FIRST_PERSON, newStatus, true);
        assertFalse(updateFirstCommand.equals(updateFirstCommandForce));

        // different status -> returns false
        Status differentStatus = new Status("Rejected");
        UpdateCommand updateFirstCommandDifferentStatus = new UpdateCommand(INDEX_FIRST_PERSON, differentStatus, false);
        assertFalse(updateFirstCommand.equals(updateFirstCommandDifferentStatus));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        Status status = new Status("Interview Scheduled");
        UpdateCommand updateCommand = new UpdateCommand(targetIndex, status, false);
        String expected = UpdateCommand.class.getCanonicalName()
                + "{targetIndex=" + targetIndex
                + ", status=" + status
                + ", isForceOperation=false}";
        assertEquals(expected, updateCommand.toString());
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);
        assertTrue(model.getFilteredPersonList().isEmpty());
    }
}
