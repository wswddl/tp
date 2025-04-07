package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.logic.Messages.MESSAGE_INVALID_CRITERIA_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDED_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_JOB_POSITION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RATING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.exceptions.CommandException;

public class SortCommandParserTest {
    private final SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_validNameArgs_returnsSortCommand() {
        try {
            String input = " n/";
            SortCommand expectedSortCommand = new SortCommand(PREFIX_NAME, true);
            assertParseSuccess(parser, input, expectedSortCommand);
        } catch (CommandException pe) {
            fail();
        }
    }
    @Test
    public void parse_validNameArgsAscendingOrder_returnsSortCommand() {
        try {
            String input = " n/ a/";
            SortCommand expectedSortCommand = new SortCommand(PREFIX_NAME, true);
            assertParseSuccess(parser, input, expectedSortCommand);
        } catch (CommandException pe) {
            fail();
        }
    }
    @Test
    public void parse_validNameArgsDescendingOrder_returnsSortCommand() {
        try {
            String input = " n/ d/";
            SortCommand expectedSortCommand = new SortCommand(PREFIX_NAME, true);
            assertParseSuccess(parser, input, expectedSortCommand);
        } catch (CommandException pe) {
            fail();
        }
    }
    @Test
    public void parse_validEmailAddressArgs_returnsSortCommand() {
        try {
            String input = " e/";
            SortCommand expectedSortCommand = new SortCommand(PREFIX_EMAIL, true);
            assertParseSuccess(parser, input, expectedSortCommand);
        } catch (CommandException pe) {
            fail();
        }
    }
    @Test
    public void parse_validEmailAddressArgsAscendingOrder_returnsSortCommand() {
        try {
            String input = " e/ a/";
            SortCommand expectedSortCommand = new SortCommand(PREFIX_EMAIL, true);
            assertParseSuccess(parser, input, expectedSortCommand);
        } catch (CommandException pe) {
            fail();
        }
    }
    @Test
    public void parse_validEmailAddressArgsDescendingOrder_returnsSortCommand() {
        try {
            String input = " e/ d/";
            SortCommand expectedSortCommand = new SortCommand(PREFIX_EMAIL, true);
            assertParseSuccess(parser, input, expectedSortCommand);
        } catch (CommandException pe) {
            fail();
        }
    }
    @Test
    public void parse_validJobPositionArgs_returnsSortCommand() {
        try {
            String input = " j/";
            SortCommand expectedSortCommand = new SortCommand(PREFIX_JOB_POSITION, true);
            assertParseSuccess(parser, input, expectedSortCommand);
        } catch (CommandException pe) {
            fail();
        }
    }
    @Test
    public void parse_validJobPositionArgsAscendingOrder_returnsSortCommand() {
        try {
            String input = " j/ a/";
            SortCommand expectedSortCommand = new SortCommand(PREFIX_JOB_POSITION, true);
            assertParseSuccess(parser, input, expectedSortCommand);
        } catch (CommandException pe) {
            fail();
        }
    }
    @Test
    public void parse_validJobPositionArgsDescendingOrder_returnsSortCommand() {
        try {
            String input = " j/ d/";
            SortCommand expectedSortCommand = new SortCommand(PREFIX_JOB_POSITION, true);
            assertParseSuccess(parser, input, expectedSortCommand);
        } catch (CommandException pe) {
            fail();
        }
    }
    @Test
    public void parse_validStatusArgs_returnsSortCommand() {
        try {
            String input = " s/";
            SortCommand expectedSortCommand = new SortCommand(PREFIX_STATUS, true);
            assertParseSuccess(parser, input, expectedSortCommand);
        } catch (CommandException pe) {
            fail();
        }
    }
    @Test
    public void parse_validStatusArgsAscendingOrder_returnsSortCommand() {
        try {
            String input = " s/ a/";
            SortCommand expectedSortCommand = new SortCommand(PREFIX_STATUS, true);
            assertParseSuccess(parser, input, expectedSortCommand);
        } catch (CommandException pe) {
            fail();
        }
    }
    @Test
    public void parse_validStatusArgsDescendingOrder_returnsSortCommand() {
        try {
            String input = " s/ d/";
            SortCommand expectedSortCommand = new SortCommand(PREFIX_STATUS, true);
            assertParseSuccess(parser, input, expectedSortCommand);
        } catch (CommandException pe) {
            fail();
        }
    }
    @Test
    public void parse_validAddedTimeArgs_returnsSortCommand() {
        try {
            String input = " time/";
            SortCommand expectedSortCommand = new SortCommand(PREFIX_ADDED_TIME, true);
            assertParseSuccess(parser, input, expectedSortCommand);
        } catch (CommandException pe) {
            fail();
        }
    }
    @Test
    public void parse_validAddedTimeArgsAscendingOrder_returnsSortCommand() {
        try {
            String input = " time/ a/";
            SortCommand expectedSortCommand = new SortCommand(PREFIX_ADDED_TIME, true);
            assertParseSuccess(parser, input, expectedSortCommand);
        } catch (CommandException pe) {
            fail();
        }
    }
    @Test
    public void parse_validAddedTimeArgsDescendingOrder_returnsSortCommand() {
        try {
            String input = " time/ d/";
            SortCommand expectedSortCommand = new SortCommand(PREFIX_ADDED_TIME, true);
            assertParseSuccess(parser, input, expectedSortCommand);
        } catch (CommandException pe) {
            fail();
        }
    }
    @Test
    public void parse_validRatingArgs_returnsSortCommand() {
        try {
            String input = " r/";
            SortCommand expectedSortCommand = new SortCommand(PREFIX_RATING, true);
            assertParseSuccess(parser, input, expectedSortCommand);
        } catch (CommandException pe) {
            fail();
        }
    }
    @Test
    public void parse_validRatingArgsAscendingOrder_returnsSortCommand() {
        try {
            String input = " r/ a/";
            SortCommand expectedSortCommand = new SortCommand(PREFIX_RATING, true);
            assertParseSuccess(parser, input, expectedSortCommand);
        } catch (CommandException pe) {
            fail();
        }
    }
    @Test
    public void parse_validRatingArgsDescendingOrder_returnsSortCommand() {
        try {
            String input = " r/ d/";
            SortCommand expectedSortCommand = new SortCommand(PREFIX_RATING, true);
            assertParseSuccess(parser, input, expectedSortCommand);
        } catch (CommandException pe) {
            fail();
        }
    }
    @Test
    public void parse_invalidArgs_throwsParseException() {
        String invalidInput = " z/";
        String expectedExceptionMessage = String.format(
                MESSAGE_INVALID_CRITERIA_FORMAT, "sorting", SortCommand.MESSAGE_USAGE);
        assertParseFailure(parser, invalidInput, expectedExceptionMessage);
    }
    @Test
    public void parse_multipleCriteriaArgs_throwsParseException() {
        String invalidInput = " n/ e/";
        String expectedExceptionMessage = String.format(
                MESSAGE_INVALID_CRITERIA_FORMAT, "sorting", SortCommand.MESSAGE_USAGE);
        assertParseFailure(parser, invalidInput, expectedExceptionMessage);
    }
    @Test
    public void parse_noSpaceBetweenCriteriaAndOrder_throwsParseException() {
        String invalidInput = " n/a/";
        String expectedExceptionMessage = String.format(
                MESSAGE_INVALID_CRITERIA_FORMAT, "sorting", SortCommand.MESSAGE_USAGE);
        assertParseFailure(parser, invalidInput, expectedExceptionMessage);
    }
}
