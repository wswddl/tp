package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_DUPLICATE_FIELDS;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_FLAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_DATE_FORMAT;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteCommand;
import seedu.address.model.applicant.AfterDatePredicate;
import seedu.address.model.applicant.BeforeDatePredicate;
import seedu.address.model.applicant.EmailMatchesKeywordPredicate;
import seedu.address.model.applicant.JobPositionMatchesPredicate;
import seedu.address.model.applicant.NameMatchesKeywordPredicate;
import seedu.address.model.applicant.PhoneMatchesKeywordPredicate;
import seedu.address.model.applicant.StatusMatchesPredicate;

public class DeleteCommandParserTest {

    private final DeleteCommandParser parser = new DeleteCommandParser();

    // ===== Valid Input Tests =====

    /**
     * EP: Valid ID input
     */
    @Test
    public void parse_validIdArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "delete id/1", new DeleteCommand(INDEX_FIRST_PERSON, false));
    }

    /**
     * EP: Valid name input
     */
    @Test
    public void parse_validNameArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "delete n/John Doe",
                new DeleteCommand(List.of(new NameMatchesKeywordPredicate("John Doe")), false));
    }

    /**
     * EP: Valid phone input
     */
    @Test
    public void parse_validPhoneArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "delete p/98765432",
                new DeleteCommand(List.of(new PhoneMatchesKeywordPredicate("98765432")), false));
    }

    /**
     * EP: Valid email input
     */
    @Test
    public void parse_validEmailArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "delete e/john.doe@example.com",
                new DeleteCommand(List.of(new EmailMatchesKeywordPredicate("john.doe@example.com")), false));
    }

    /**
     * EP: Valid status input
     */
    @Test
    public void parse_validStatusArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "delete s/Applied",
                new DeleteCommand(List.of(new StatusMatchesPredicate("Applied")), false));
    }

    /**
     * EP: Valid job position input
     */
    @Test
    public void parse_validJobPositionArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "delete j/Software Engineer",
                new DeleteCommand(List.of(new JobPositionMatchesPredicate("Software Engineer")), false));
    }

    /**
     * EP: Valid before date input
     */
    @Test
    public void parse_validBeforeDateArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "delete bfr/2023-01-01",
                new DeleteCommand(List.of(
                        new BeforeDatePredicate(
                                LocalDateTime.parse("2023-01-01T00:00:00"))), false));
    }

    /**
     * EP: Valid after date input
     */
    @Test
    public void parse_validAfterDateArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "delete aft/2023-01-01",
                new DeleteCommand(List.of(
                        new AfterDatePredicate(
                                LocalDateTime.parse("2023-01-01T00:00:00"))), false));
    }

    /**
     * EP: Valid force flag
     */
    @Test
    public void parse_validForceFlag_returnsDeleteCommand() {
        assertParseSuccess(parser, "delete id/1 --force",
                new DeleteCommand(INDEX_FIRST_PERSON, true));
    }

    // ===== Invalid Input Tests =====

    /**
     * EP: Invalid ID format
     */
    @Test
    public void parse_invalidIdFormat_throwsParseException() {
        assertParseFailure(parser, "delete id/not_a_number",
                MESSAGE_INVALID_INDEX);
    }

    /**
     * EP: Missing prefix
     */
    @Test
    public void parse_missingPrefix_throwsParseException() {
        assertParseFailure(parser, "delete John Doe",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }

    /**
     * EP: Both ID and other predicates
     */
    @Test
    public void parse_idWithOtherPredicates_throwsParseException() {
        assertParseFailure(parser, "delete id/1 n/John Doe",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }

    /**
     * EP: No arguments
     */
    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertParseFailure(parser, "delete ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }

    /**
     * EP: Invalid date format
     */
    @Test
    public void parse_invalidDateFormat_throwsParseException() {
        assertParseFailure(parser, "delete bfr/not_a_date",
                MESSAGE_INVALID_DATE_FORMAT);
    }

    /**
     * EP: Invalid flag
     */
    @Test
    public void parse_invalidFlag_throwsParseException() {
        assertParseFailure(parser, "delete id/1 --invalid",
                String.format(MESSAGE_UNKNOWN_FLAG, "--invalid"));
    }

    /**
     * EP: Duplicate prefixes
     */
    @Test
    public void parse_duplicatePrefixes_throwsParseException() {
        assertParseFailure(parser, "delete n/John n/Doe",
                MESSAGE_DUPLICATE_FIELDS + "n/");
    }
}
