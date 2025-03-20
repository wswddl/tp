package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.applicant.Email;
import seedu.address.model.applicant.EmailMatchesKeywordPredicate;
import seedu.address.model.applicant.IdentifierPredicate;
import seedu.address.model.applicant.Name;
import seedu.address.model.applicant.NameMatchesKeywordPredicate;
import seedu.address.model.applicant.Phone;
import seedu.address.model.applicant.PhoneMatchesKeywordPredicate;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        requireNonNull(args);

        // Check for --force flag first
        boolean isForceDelete = args.contains("--force");
        args = args.replace("--force", ""); // Remove --force from args

        // Other flags that start with "--" are invalid
        if (args.trim().matches(".*\\s--\\w+.*")) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }


        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ID);

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ID);

        // Exact one contact identifier needs to be provided
        List<Boolean> presentFlags = List.of(
                argMultimap.getValue(PREFIX_NAME).isPresent(),
                argMultimap.getValue(PREFIX_PHONE).isPresent(),
                argMultimap.getValue(PREFIX_EMAIL).isPresent(),
                argMultimap.getValue(PREFIX_ID).isPresent()
        );

        long count = presentFlags.stream().filter(b -> b).count();
        if (count != 1) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }

        IdentifierPredicate predicate = null;

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            String nameString = argMultimap.getValue(PREFIX_NAME).get();
            Name validName = ParserUtil.parseName(nameString);
            predicate = new NameMatchesKeywordPredicate(validName.fullName);
            return new DeleteCommand(predicate, isForceDelete);
        } else if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            String phoneString = argMultimap.getValue(PREFIX_PHONE).get();
            Phone validPhone = ParserUtil.parsePhone(phoneString);
            predicate = new PhoneMatchesKeywordPredicate(validPhone.value);
            return new DeleteCommand(predicate, isForceDelete);
        } else if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            String emailString = argMultimap.getValue(PREFIX_EMAIL).get();
            Email validEmail = ParserUtil.parseEmail(emailString);
            predicate = new EmailMatchesKeywordPredicate(validEmail.value);
            return new DeleteCommand(predicate, isForceDelete);
        } else if (argMultimap.getValue(PREFIX_ID).isPresent()) {
            Index index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_ID).get());
            return new DeleteCommand(index, isForceDelete); // Use index-based constructor
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }
    }

}
