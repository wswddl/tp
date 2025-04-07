package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.logic.Messages.MESSAGE_INVALID_CRITERIA_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.parser.CliSyntax.*;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class SortCommandTest {
    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }
    @Test
    public void execute_sortByNameInAscendingOder_success() {
        Prefix prefix = PREFIX_NAME;
        boolean isAscendingOrder = true;
        String order = "ascending order";
        try {
            SortCommand sortCommand = new SortCommand(prefix, isAscendingOrder);
            String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS,
                    sortCommand.getCriteria(), order);

            Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
            expectedModel.sortPersons(prefix, isAscendingOrder);

            assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
        } catch (CommandException e) {
            fail();
        }
    }
    @Test
    public void execute_sortByNameInDescendingOder_success() {
        Prefix prefix = PREFIX_NAME;
        boolean isAscendingOrder = false;
        String order = "descending order";
        try {
            SortCommand sortCommand = new SortCommand(prefix, isAscendingOrder);
            String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, sortCommand.getCriteria(), order);

            Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
            expectedModel.sortPersons(prefix, isAscendingOrder);

            assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
        } catch (CommandException e) {
            fail();
        }
    }
    @Test
    public void execute_sortByEmailAddressInAscendingOrder_success() {
        Prefix prefix = PREFIX_EMAIL;
        boolean isAscendingOrder = true;
        String order = "ascending order";
        try {
            SortCommand sortCommand = new SortCommand(prefix, isAscendingOrder);
            String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, sortCommand.getCriteria(), order);

            Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
            expectedModel.sortPersons(prefix, isAscendingOrder);

            assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
        } catch (CommandException e) {
            fail();
        }

    }
    @Test
    public void execute_sortByEmailAddressInDescendingOrder_success() {
        Prefix prefix = PREFIX_EMAIL;
        boolean isAscendingOrder = false;
        String order = "descending order";
        try {
            SortCommand sortCommand = new SortCommand(prefix, isAscendingOrder);
            String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, sortCommand.getCriteria(), order);

            Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
            expectedModel.sortPersons(prefix, isAscendingOrder);

            assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
        } catch (CommandException e) {
            fail();
        }

    }
    @Test
    public void execute_sortByJobPositionInAscendingOrder_success() {
        Prefix prefix = PREFIX_JOB_POSITION;
        boolean isAscendingOrder = true;
        String order = "ascending order";
        try {
            SortCommand sortCommand = new SortCommand(prefix, isAscendingOrder);
            String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, sortCommand.getCriteria(), order);

            Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
            expectedModel.sortPersons(prefix, isAscendingOrder);

            assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
        } catch (CommandException e) {
            fail();
        }

    }
    @Test
    public void execute_sortByJobPositionInDescendingOrder_success() {
        Prefix prefix = PREFIX_JOB_POSITION;
        boolean isAscendingOrder = false;
        String order = "descending order";
        try {
            SortCommand sortCommand = new SortCommand(prefix, isAscendingOrder);
            String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, sortCommand.getCriteria(), order);

            Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
            expectedModel.sortPersons(prefix, isAscendingOrder);

            assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
        } catch (CommandException e) {
            fail();
        }

    }

    @Test
    public void execute_sortByStatusInAscendingOrder_success() {
        Prefix prefix = PREFIX_STATUS;
        boolean isAscendingOrder = true;
        String order = "ascending order";
        try {
            SortCommand sortCommand = new SortCommand(prefix, isAscendingOrder);
            String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, sortCommand.getCriteria(), order);

            Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
            expectedModel.sortPersons(prefix, isAscendingOrder);

            assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
        } catch (CommandException e) {
            fail();
        }

    }
    @Test
    public void execute_sortByStatusInDescendingOrder_success() {
        Prefix prefix = PREFIX_STATUS;
        boolean isAscendingOrder = false;
        String order = "descending order";
        try {
            SortCommand sortCommand = new SortCommand(prefix, isAscendingOrder);
            String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, sortCommand.getCriteria(), order);

            Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
            expectedModel.sortPersons(prefix, isAscendingOrder);

            assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
        } catch (CommandException e) {
            fail();
        }

    }
    @Test
    public void execute_sortByAddedTimeInAscendingOrder_success() {
        Prefix prefix = PREFIX_ADDED_TIME;
        boolean isAscendingOrder = true;
        String order = "ascending order";
        try {
            SortCommand sortCommand = new SortCommand(prefix, isAscendingOrder);
            String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, sortCommand.getCriteria(), order);

            Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
            expectedModel.sortPersons(prefix, isAscendingOrder);

            assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
        } catch (CommandException e) {
            fail();
        }

    }
    @Test
    public void execute_sortByAddedTimeInDescendingOrder_success() {
        Prefix prefix = PREFIX_ADDED_TIME;
        boolean isAscendingOrder = false;
        String order = "descending order";
        try {
            SortCommand sortCommand = new SortCommand(prefix, isAscendingOrder);
            String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, sortCommand.getCriteria(), order);

            Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
            expectedModel.sortPersons(prefix, isAscendingOrder);

            assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
        } catch (CommandException e) {
            fail();
        }

    }
    @Test
    public void execute_sortByRatingInAscendingOrder_success() {
        Prefix prefix = PREFIX_RATING;
        boolean isAscendingOrder = true;
        String order = "ascending order";
        try {
            SortCommand sortCommand = new SortCommand(prefix, isAscendingOrder);
            String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, sortCommand.getCriteria(), order);

            Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
            expectedModel.sortPersons(prefix, isAscendingOrder);

            assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
        } catch (CommandException e) {
            fail();
        }

    }
    @Test
    public void execute_sortByRatingInDescendingOrder_success() {
        Prefix prefix = PREFIX_RATING;
        boolean isAscendingOrder = false;
        String order = "descending order";
        try {
            SortCommand sortCommand = new SortCommand(prefix, isAscendingOrder);
            String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, sortCommand.getCriteria(), order);

            Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
            expectedModel.sortPersons(prefix, isAscendingOrder);

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
            SortCommand sortCommand = new SortCommand(prefix, true);
            fail();
        } catch (CommandException e) {
            String expectedExceptionMessage = String.format(
                    MESSAGE_INVALID_CRITERIA_FORMAT, "sorting", SortCommand.MESSAGE_USAGE);
            assertEquals(expectedExceptionMessage, e.getMessage());
        }
    }
}
