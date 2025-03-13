package seedu.address.logic.parser;

import org.junit.jupiter.api.Test;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.exceptions.CommandException;

import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.logic.Messages.MESSAGE_INVALID_CRITERIA_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

public class SortCommandParserTest {
    private SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_validNameArgs_returnsSortCommand() {
        try {
            String input = " n/";
            SortCommand expectedSortCommand = new SortCommand(new Prefix("n/"));
            assertParseSuccess(parser, input, expectedSortCommand);
        } catch (CommandException pe) {
            fail();
        }
    }

    @Test
    public void parse_validEmailAddressArgs_returnsSortCommand() {
        try {
            String input = " e/";
            SortCommand expectedSortCommand = new SortCommand(new Prefix("e/"));
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
}
