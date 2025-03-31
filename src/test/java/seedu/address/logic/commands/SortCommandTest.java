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
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDED_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_JOB_POSITION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

public class SortCommandTest {
    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_sortByName_success() {
        Prefix prefix = PREFIX_NAME;
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
        Prefix prefix = PREFIX_EMAIL;
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
    public void execute_sortByJobPosition_success() {
        Prefix prefix = PREFIX_JOB_POSITION;
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
    public void execute_sortByStatus_success() {
        Prefix prefix = PREFIX_STATUS;
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
    public void execute_sortByAddedTime_success() {
        Prefix prefix = PREFIX_ADDED_TIME;
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
    public void execute_sortByUnknownCriteria_throwCommandException() {
        // Undefined prefix
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
