package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.applicant.IdentifierPredicate;
import seedu.address.model.applicant.NameMatchesKeywordPredicate;

public class SummaryCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        List<IdentifierPredicate> firstPredicate = Collections.singletonList(
                new NameMatchesKeywordPredicate("first"));
        List<IdentifierPredicate> secondPredicate = Collections.singletonList(
                new NameMatchesKeywordPredicate("second"));

        SummaryCommand findFirstCommand = new SummaryCommand(firstPredicate);
        SummaryCommand findSecondCommand = new SummaryCommand(secondPredicate);

        // same object -> returns true
        assertEquals(findFirstCommand, findFirstCommand);

        // same values -> returns true
        SummaryCommand findFirstCommandCopy = new SummaryCommand(firstPredicate);
        assertEquals(findFirstCommand, findFirstCommandCopy);

        // different types -> returns false
        assertNotEquals(1, findFirstCommand);

        // null -> returns false
        assertNotEquals(null, findFirstCommand);

        // different applicant -> returns false
        assertNotEquals(findFirstCommand, findSecondCommand);
    }

}
