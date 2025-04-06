package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AFTER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BEFORE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_JOB_POSITION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.ParserUtil.COMMON_PREFIXES_WITH_ID;
import static seedu.address.logic.parser.ParserUtil.checkFlag;
import static seedu.address.logic.parser.ParserUtil.extractPredicates;
import static seedu.address.logic.parser.ParserUtil.numOfPrefixesPresent;

import java.util.List;

import javafx.util.Pair;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.UpdateCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.applicant.IdentifierPredicate;
import seedu.address.model.applicant.Status;
import seedu.address.model.applicant.StatusMatchesPredicate;

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

        boolean isForceUpdate;
        Pair<String, Boolean> checkFlagResult = checkFlag(args);
        args = checkFlagResult.getKey();
        isForceUpdate = checkFlagResult.getValue();

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, COMMON_PREFIXES_WITH_ID);

        argMultimap.verifyNoDuplicatePrefixesFor(COMMON_PREFIXES_WITH_ID);

        Status status;
        // Check if status is provided and valid
        if (argMultimap.getValue(PREFIX_STATUS).isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE));
        } else {
            status = ParserUtil.parseStatus(argMultimap.getValue(PREFIX_STATUS).get());
        }

        assert argMultimap.getValue(PREFIX_STATUS).isPresent();

        boolean hasId = argMultimap.getValue(PREFIX_ID).isPresent();
        int numOfContactIdentifiersExceptIdAndStatus = numOfPrefixesPresent(argMultimap, PREFIX_NAME,
                PREFIX_PHONE, PREFIX_EMAIL, PREFIX_JOB_POSITION, PREFIX_BEFORE, PREFIX_AFTER);
        // Validate input format
        if ((hasId && numOfContactIdentifiersExceptIdAndStatus > 0)
                || (!hasId && numOfContactIdentifiersExceptIdAndStatus == 0)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE));
        }


        // If Index is provided, perform delete solely on the index
        if (argMultimap.getValue(PREFIX_ID).isPresent()) {
            Index index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_ID).get());
            return new UpdateCommand(index, status, isForceUpdate); // Use index-based constructor
        }

        List<IdentifierPredicate> predicates = extractPredicates(argMultimap);
        predicates.remove(new StatusMatchesPredicate(status.toString()));
        return new UpdateCommand(predicates, status, isForceUpdate);
    }
}
