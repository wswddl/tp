package seedu.address.logic.commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.logic.Messages.MESSAGE_INVALID_CRITERIA_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

public class SortCommandTest {
    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_sortByName_success() {
        Prefix prefix = new Prefix("n/");
        try {
            SortCommand sortCommand = new SortCommand(prefix);
            String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, sortCommand.getCriteria());

            Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
            expectedModel.sortPersons(prefix);

            assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
        } catch (CommandException e) {
            fail();
        }
    }

    @Test
    public void execute_sortByEmailAddress_success() {
        Prefix prefix = new Prefix("e/");
        try {
            SortCommand sortCommand = new SortCommand(prefix);
            String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, sortCommand.getCriteria());

            Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
            expectedModel.sortPersons(prefix);

            assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
        } catch (CommandException e) {
            fail();
        }

    }

    @Test
    public void execute_sortByUnknownCriteria_doNothing() {
        Prefix prefix = new Prefix("z/");
        try {
            SortCommand sortCommand = new SortCommand(prefix);
            fail();
        } catch (CommandException e) {
            String expectedExceptionMessage = String.format(
                    MESSAGE_INVALID_CRITERIA_FORMAT, "sorting", SortCommand.MESSAGE_USAGE);
            assertEquals(expectedExceptionMessage, e.getMessage());
        }
    }

}
