package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
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
import seedu.address.model.applicant.IdentifierPredicate;
import seedu.address.model.applicant.NameMatchesKeywordPredicate;
import seedu.address.model.applicant.Rating;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code RateCommand}.
 */
public class RateCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_rateSuccess() {
        Applicant applicantToRate = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Rating rating = new Rating("3");
        RateCommand rateCommand = new RateCommand(INDEX_FIRST_PERSON, rating);
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setRating(applicantToRate, rating);
        Applicant updatedApplicant = expectedModel.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        String expectedMessage = String.format("Assigned a rating of 3 / 5 to: %s", Messages.format(updatedApplicant));

        assertCommandSuccess(rateCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        RateCommand rateCommand = new RateCommand(outOfBoundIndex, new Rating("3"));

        assertCommandFailure(rateCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_forceDeleteSuccess() {
        Applicant applicantToRate = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Rating rating = new Rating("-1");
        RateCommand rateCommand = new RateCommand(INDEX_FIRST_PERSON, rating);

        String expectedMessage = String.format("Assigned a rating of %s to: %s",
                rating.toString(), Messages.format(applicantToRate));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setRating(applicantToRate, rating);

        assertCommandSuccess(rateCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        RateCommand validRateCommand = new RateCommand(INDEX_FIRST_PERSON, new Rating("4"));

        // same object -> returns true
        assertTrue(validRateCommand.equals(validRateCommand));

        // same values -> returns true
        RateCommand validRateCommandCopy = new RateCommand(INDEX_FIRST_PERSON, new Rating("4"));
        assertTrue(validRateCommand.equals(validRateCommandCopy));

        // different types -> returns false
        assertFalse(validRateCommand.equals(1));

        // null -> returns false
        assertFalse(validRateCommand.equals(null));

        // different applicant -> returns false
        RateCommand differentIndexRateCommand = new RateCommand(INDEX_SECOND_PERSON, new Rating("4"));
        assertFalse(validRateCommand.equals(differentIndexRateCommand));

        // different rating -> returns false
        RateCommand differentRatingRateCommand = new RateCommand(INDEX_FIRST_PERSON, new Rating("-1"));
        assertFalse(validRateCommand.equals(differentRatingRateCommand));
    }

    @Test
    public void toStringMethod() {
        // rate by index
        Index targetIndex = Index.fromOneBased(1);
        RateCommand rateByIndexCommand = new RateCommand(targetIndex, new Rating("4"));
        String expected1 = RateCommand.class.getCanonicalName()
                + "{targetIndex=" + targetIndex + ", rating=4 / 5}";
        assertEquals(expected1, rateByIndexCommand.toString());

        // rate by predicate
        IdentifierPredicate predicate = new NameMatchesKeywordPredicate("amy");
        RateCommand rateByPredicateCommand = new RateCommand(predicate, new Rating("4"));
        String expected2 = RateCommand.class.getCanonicalName()
                + "{predicate=" + predicate + ", rating=4 / 5}";
        assertEquals(expected2, rateByPredicateCommand.toString());
    }

}
