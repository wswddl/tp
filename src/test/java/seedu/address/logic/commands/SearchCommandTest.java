package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.GABRIELLA;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.applicant.IdentifierPredicate;
import seedu.address.model.applicant.NameMatchesKeywordPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code SearchCommand}.
 */
public class SearchCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        List<IdentifierPredicate> firstPredicate = Collections.singletonList(
            new NameMatchesKeywordPredicate("first"));
        List<IdentifierPredicate> secondPredicate = Collections.singletonList(
            new NameMatchesKeywordPredicate("second"));

        SearchCommand findFirstCommand = new SearchCommand(firstPredicate);
        SearchCommand findSecondCommand = new SearchCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        SearchCommand findFirstCommandCopy = new SearchCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different applicant -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        List<IdentifierPredicate> predicate = preparePredicate("Gabriella");
        SearchCommand command = new SearchCommand(predicate);
        expectedModel.updateFilteredPersonList(person -> predicate.stream().allMatch(p -> p.test(person)));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(GABRIELLA), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        List<IdentifierPredicate> predicate = Arrays.asList(new NameMatchesKeywordPredicate("keyword"));
        SearchCommand searchCommand = new SearchCommand(predicate);
        String expected = SearchCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, searchCommand.toString());
    }

    /**
     * Parses {@code userInput} into a list of {@code IdentifierPredicate}.
     */
    private List<IdentifierPredicate> preparePredicate(String userInput) {
        if (userInput == null || userInput.trim().isEmpty()) {
            return Collections.emptyList();
        }
        return Arrays.stream(userInput.split("\\s+"))
            .map(NameMatchesKeywordPredicate::new)
            .map(p -> (IdentifierPredicate) p) // Ensure proper type conversion
            .toList();

    }
}
