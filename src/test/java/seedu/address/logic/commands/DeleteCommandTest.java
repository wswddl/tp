package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_NO_RESULT;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.applicant.AfterDatePredicate;
import seedu.address.model.applicant.Applicant;
import seedu.address.model.applicant.BeforeDatePredicate;
import seedu.address.model.applicant.EmailMatchesKeywordPredicate;
import seedu.address.model.applicant.IdentifierPredicate;
import seedu.address.model.applicant.NameMatchesKeywordPredicate;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    // ===== Index-based Deletion Tests =====

    /**
     * EP: Valid index within bounds, force delete
     * BV: First index in unfiltered list
     */
    @Test
    public void execute_validIndexUnfilteredList_forceDeleteSuccess() {
        Applicant applicantToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON, true);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_SUCCESS,
                Messages.format(applicantToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateFilteredPersonList(p -> p.equals(applicantToDelete));

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    /**
     * EP: Valid index within bounds, needs confirmation
     * BV: Last index in unfiltered list
     */
    @Test
    public void execute_validIndexUnfilteredList_needsConfirmation() {
        Index lastIndex = Index.fromOneBased(model.getFilteredPersonList().size());
        DeleteCommand deleteCommand = new DeleteCommand(lastIndex, false);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_ID_DELETE_CONFIRMATION,
                lastIndex.getOneBased());
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    /**
     * EP: Invalid index (out of bounds)
     * BV: size + 1
     */
    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex, false);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * EP: Valid index in filtered list, force delete
     */
    @Test
    public void execute_validIndexFilteredList_forceDeleteSuccess() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Applicant applicantToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON, true);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_SUCCESS,
                Messages.format(applicantToDelete));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(applicantToDelete);
        showNoPerson(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    // ===== Predicate-based Deletion Tests =====

    /**
     * EP: Single predicate matching one applicant, force delete
     */
    @Test
    public void execute_singlePredicateUnfilteredList_forceDeleteSuccess() {
        Applicant applicantToDelete = model.getFilteredPersonList().get(0);
        IdentifierPredicate predicate = new NameMatchesKeywordPredicate(applicantToDelete.getName().fullName);
        DeleteCommand deleteCommand = new DeleteCommand(List.of(predicate), true);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_SUCCESS,
                Messages.format(applicantToDelete));

        Model expectedModel = new ModelManager(model.getAddressBook(), model.getUserPrefs());
        expectedModel.deletePerson(applicantToDelete);
        showNoPerson(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    /**
     * EP: Multiple predicates matching one applicant, force delete
     */
    @Test
    public void execute_multiplePredicatesUnfilteredList_forceDeleteSuccess() {
        Applicant applicantToDelete = model.getFilteredPersonList().get(0);
        IdentifierPredicate namePredicate = new NameMatchesKeywordPredicate(applicantToDelete.getName().fullName);
        IdentifierPredicate emailPredicate = new EmailMatchesKeywordPredicate(applicantToDelete.getEmail().value);
        DeleteCommand deleteCommand = new DeleteCommand(Arrays.asList(namePredicate, emailPredicate), true);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_SUCCESS,
                Messages.format(applicantToDelete));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(applicantToDelete);
        showNoPerson(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    /**
     * EP: Predicate matching no applicants
     */
    @Test
    public void execute_predicateNoMatches_throwsCommandException() {
        IdentifierPredicate predicate = new NameMatchesKeywordPredicate("Nonexistent Name");
        DeleteCommand deleteCommand = new DeleteCommand(List.of(predicate), false);
        model.updateFilteredPersonList(predicate);

        assertCommandFailure(deleteCommand, model, MESSAGE_NO_RESULT);
    }

    /**
     * EP: Date predicate (after), force delete
     */
    @Test
    public void execute_afterDatePredicate_forceDeleteSuccess() {
        LocalDateTime futureDate = LocalDateTime.now().plusDays(1);
        IdentifierPredicate predicate = new AfterDatePredicate(futureDate);
        DeleteCommand deleteCommand = new DeleteCommand(List.of(predicate), true);
        model.updateFilteredPersonList(predicate);
        // Expect no matches (since we're using future date)
        assertCommandFailure(deleteCommand, model, MESSAGE_NO_RESULT);
    }

    /**
     * EP: Date predicate (before), force delete
     */
    @Test
    public void execute_beforeDatePredicate_forceDeleteSuccess() {
        LocalDateTime pastDate = LocalDateTime.now().minusDays(1);
        IdentifierPredicate predicate = new BeforeDatePredicate(pastDate);
        DeleteCommand deleteCommand = new DeleteCommand(List.of(predicate), true);

        model.updateFilteredPersonList(predicate);
        List<Applicant> expectedToDelete = new ArrayList<>(model.getFilteredPersonList());

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_SUCCESS,
                expectedToDelete.stream()
                        .map(Messages::format)
                        .collect(Collectors.joining("\n\n")));

        Model expectedModel = new ModelManager(model.getAddressBook(), model.getUserPrefs());
        expectedToDelete.forEach(expectedModel::deletePerson);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    // ===== Unit Tests =====

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST_PERSON, false);
        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_SECOND_PERSON, false);

        // same object -> returns true
        assertEquals(deleteFirstCommand, deleteFirstCommand);

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST_PERSON, false);
        assertEquals(deleteFirstCommand, deleteFirstCommandCopy);

        // different types -> returns false
        assertNotEquals(1, deleteFirstCommand);

        // null -> returns false
        assertNotEquals(null, deleteFirstCommand);

        // different applicant -> returns false
        assertNotEquals(deleteFirstCommand, deleteSecondCommand);

        // different force flag -> returns false
        DeleteCommand deleteFirstCommandForce = new DeleteCommand(INDEX_FIRST_PERSON, true);
        assertNotEquals(deleteFirstCommand, deleteFirstCommandForce);
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        DeleteCommand deleteCommand = new DeleteCommand(targetIndex, false);
        String expected = DeleteCommand.class.getCanonicalName()
                + "{predicates=null, "
                + "targetIndex=" + targetIndex + ", isForceOperation=false}";
        assertEquals(expected, deleteCommand.toString());
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);
        assertTrue(model.getFilteredPersonList().isEmpty());
    }
}
