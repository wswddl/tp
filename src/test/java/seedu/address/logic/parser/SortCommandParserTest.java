package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.logic.Messages.MESSAGE_INVALID_CRITERIA_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDED_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_JOB_POSITION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
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
            SortCommand expectedSortCommand = new SortCommand(PREFIX_NAME);
            assertParseSuccess(parser, input, expectedSortCommand);
        } catch (CommandException pe) {
            fail();
        }
    }

    @Test
    public void parse_validEmailAddressArgs_returnsSortCommand() {
        try {
            String input = " e/";
            SortCommand expectedSortCommand = new SortCommand(PREFIX_EMAIL);
            assertParseSuccess(parser, input, expectedSortCommand);
        } catch (CommandException pe) {
            fail();
        }
    }

    @Test
    public void parse_validJobPositionArgs_returnsSortCommand() {
        try {
            String input = " j/";
            SortCommand expectedSortCommand = new SortCommand(PREFIX_JOB_POSITION);
            assertParseSuccess(parser, input, expectedSortCommand);
        } catch (CommandException pe) {
            fail();
        }
    }

    @Test
    public void parse_validStatusArgs_returnsSortCommand() {
        try {
            String input = " s/";
            SortCommand expectedSortCommand = new SortCommand(PREFIX_STATUS);
            assertParseSuccess(parser, input, expectedSortCommand);
        } catch (CommandException pe) {
            fail();
        }
    }

    @Test
    public void parse_validAddedTimeArgs_returnsSortCommand() {
        try {
            String input = " time/";
            SortCommand expectedSortCommand = new SortCommand(PREFIX_ADDED_TIME);
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
}
