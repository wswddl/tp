package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;

import java.util.stream.Stream;

import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.UpdateCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.applicant.Email;
import seedu.address.model.applicant.EmailMatchesKeywordPredicate;
import seedu.address.model.applicant.IdentifierPredicate;
import seedu.address.model.applicant.Name;
import seedu.address.model.applicant.NameMatchesKeywordPredicate;
import seedu.address.model.applicant.Phone;
import seedu.address.model.applicant.PhoneMatchesKeywordPredicate;
import seedu.address.model.applicant.Status;

/**
 * Parses input arguments and creates a new UpdateCommand object
 */
public class UpdateCommandParser implements Parser<UpdateCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the UpdateCommand
     * and returns an UpdateCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public UpdateCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_STATUS);

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_STATUS);

        // Check that only one identifier prefix is provided
        if (numOfPrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL) != 1) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE));
        }

        Status status;
        // Check if status is provided and valid
        if (argMultimap.getValue(PREFIX_STATUS).isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE));
        } else {
            status = ParserUtil.parseStatus(argMultimap.getValue(PREFIX_STATUS).get());
        }

        IdentifierPredicate predicate;

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            String nameString = argMultimap.getValue(PREFIX_NAME).get();
            Name validName = ParserUtil.parseName(nameString);
            predicate = new NameMatchesKeywordPredicate(validName.fullName);
            return new UpdateCommand(predicate, status);
        } else if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            String phoneString = argMultimap.getValue(PREFIX_PHONE).get();
            Phone validPhone = ParserUtil.parsePhone(phoneString);
            predicate = new PhoneMatchesKeywordPredicate(validPhone.value);
            return new UpdateCommand(predicate, status);
        } else if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            String emailString = argMultimap.getValue(PREFIX_EMAIL).get();
            Email validEmail = ParserUtil.parseEmail(emailString);
            predicate = new EmailMatchesKeywordPredicate(validEmail.value);
            return new UpdateCommand(predicate, status);
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Counts the number of prefixes that have values in the given {@code ArgumentMultimap}.
     * i.e. number of prefixes provided in the argument.
     */
    private static int numOfPrefixesPresent(ArgumentMultimap argMultimap, Prefix... prefixes) {
        return (int) Stream.of(prefixes).filter(prefix -> argMultimap.getValue(prefix).isPresent()).count();
    }

}
