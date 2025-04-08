package seedu.address.logic.parser;

import org.junit.jupiter.api.Test;
import seedu.address.logic.commands.SearchCommand;
import seedu.address.logic.commands.SummaryCommand;
import seedu.address.model.applicant.EmailMatchesKeywordPredicate;
import seedu.address.model.applicant.JobPositionMatchesPredicate;
import seedu.address.model.applicant.NameMatchesKeywordPredicate;
import seedu.address.model.applicant.StatusMatchesPredicate;

import java.util.List;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

public class SummaryCommandParserTest {
    private final SummaryCommandParser parser = new SummaryCommandParser();

    /**
     * Tests parsing input with only a valid name field.
     */
    @Test
    public void parse_onlyName_success() {
        SummaryCommand expected = new SummaryCommand(
                List.of(new NameMatchesKeywordPredicate("Alice")));
        assertParseSuccess(parser, " n/Alice", expected);
    }

    /**
     * Tests parsing input with only a valid email field.
     */
    @Test
    public void parse_onlyEmail_success() {
        SummaryCommand expected = new SummaryCommand(
                List.of(new EmailMatchesKeywordPredicate("alice@example.com")));
        assertParseSuccess(parser, " e/alice@example.com", expected);
    }

    /**
     * Tests parsing input with only a valid job position field.
     */
    @Test
    public void parse_onlyJob_success() {
        SummaryCommand expected = new SummaryCommand(
                List.of(new JobPositionMatchesPredicate("Engineer")));
        assertParseSuccess(parser, " j/Engineer", expected);
    }

    /**
     * Tests parsing input with only a valid status field.
     */
    @Test
    public void parse_onlyStatus_success() {
        SummaryCommand expected = new SummaryCommand(
                List.of(new StatusMatchesPredicate("Pending")));
        assertParseSuccess(parser, " s/Pending", expected);
    }

    /**
     * Tests that a prefix without a value results in a parse exception.
     */
    @Test
    public void parse_missingValue_throwsParseException() {
        assertParseFailure(parser, "n/", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SummaryCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "e/", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SummaryCommand.MESSAGE_USAGE));
    }

    /**
     * Tests that an unknown prefix causes a parse failure.
     */
    @Test
    public void parse_invalidPrefix_throwsParseException() {
        assertParseFailure(parser, " x/unknown",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SummaryCommand.MESSAGE_USAGE));
    }
}
