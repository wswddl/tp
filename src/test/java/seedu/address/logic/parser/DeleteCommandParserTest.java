package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteCommand;
import seedu.address.model.applicant.EmailMatchesKeywordPredicate;
import seedu.address.model.applicant.NameMatchesKeywordPredicate;
import seedu.address.model.applicant.PhoneMatchesKeywordPredicate;

public class DeleteCommandParserTest {

    private DeleteCommandParser parser = new DeleteCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        // Test when only the ID is provided
        assertParseSuccess(parser, "delete id/1", new DeleteCommand(INDEX_FIRST_PERSON, false));

        // Test when Name is provided
        String nameArg = "delete n/John Doe";
        assertParseSuccess(parser, nameArg, new DeleteCommand(new NameMatchesKeywordPredicate("John Doe"), false));

        // Test when Phone is provided
        String phoneArg = "delete p/98765432";
        assertParseSuccess(parser, phoneArg, new DeleteCommand(new PhoneMatchesKeywordPredicate("98765432"), false));

        // Test when Email is provided
        String emailArg = "delete e/john.doe@example.com";
        assertParseSuccess(parser, emailArg, new DeleteCommand(new EmailMatchesKeywordPredicate("john.doe@example.com"),
                false));

        // Test when force delete flag is present
        String forceDeleteArg = "delete id/1 --force";
        assertParseSuccess(parser, forceDeleteArg, new DeleteCommand(INDEX_FIRST_PERSON, true));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Test invalid args for non-existing format (e.g., missing prefix)
        assertParseFailure(parser, "delete a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteCommand.MESSAGE_USAGE));

        // Test when multiple identifiers are provided (both phone and email, for example)
        assertParseFailure(parser, "delete p/98765432 e/john.doe@example.com",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));

        // Test invalid flag usage
        assertParseFailure(parser, "delete id/1 --invalidFlag",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));

        // Test empty input
        assertParseFailure(parser, "delete ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }
}
