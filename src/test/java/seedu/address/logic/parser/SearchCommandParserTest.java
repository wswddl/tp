package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SearchCommand;
import seedu.address.model.applicant.EmailMatchesKeywordPredicate;
import seedu.address.model.applicant.JobPositionMatchesPredicate;
import seedu.address.model.applicant.NameMatchesKeywordPredicate;
import seedu.address.model.applicant.StatusMatchesPredicate;

/**
 * Contains unit tests for {@link SearchCommandParser}.
 */
public class SearchCommandParserTest {

    private final SearchCommandParser parser = new SearchCommandParser();

    /**
     * Tests that a completely empty input string throws a parse exception.
     */
    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
    }

    /**
     * Tests that an input string with only whitespace throws a parse exception.
     */
    @Test
    public void parse_onlyWhitespace_throwsParseException() {
        assertParseFailure(parser, " \t \n ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
    }

    /**
     * Tests parsing input with only a valid name field.
     */
    @Test
    public void parse_onlyName_success() {
        SearchCommand expected = new SearchCommand(
                List.of(new NameMatchesKeywordPredicate("Alice")));
        assertParseSuccess(parser, " n/Alice", expected);
    }

    /**
     * Tests parsing input with only a valid email field.
     */
    @Test
    public void parse_onlyEmail_success() {
        SearchCommand expected = new SearchCommand(
                List.of(new EmailMatchesKeywordPredicate("alice@example.com")));
        assertParseSuccess(parser, " e/alice@example.com", expected);
    }

    /**
     * Tests parsing input with only a valid job position field.
     */
    @Test
    public void parse_onlyJob_success() {
        SearchCommand expected = new SearchCommand(
                List.of(new JobPositionMatchesPredicate("Engineer")));
        assertParseSuccess(parser, " j/Engineer", expected);
    }

    /**
     * Tests parsing input with only a valid status field.
     */
    @Test
    public void parse_onlyStatus_success() {
        SearchCommand expected = new SearchCommand(
                List.of(new StatusMatchesPredicate("Pending")));
        assertParseSuccess(parser, " s/Pending", expected);
    }

    /**
     * Tests that a prefix without a value results in a parse exception.
     */
    @Test
    public void parse_missingValue_throwsParseException() {
        assertParseFailure(parser, "n/", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "e/", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
    }

    /**
     * Tests that an unknown prefix causes a parse failure.
     */
    @Test
    public void parse_invalidPrefix_throwsParseException() {
        assertParseFailure(parser, " x/unknown",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
    }

}
