package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.ParserUtil.COMMON_PREFIXES;
import static seedu.address.logic.parser.ParserUtil.COMMON_PREFIXES_WITH_ID;
import static seedu.address.logic.parser.ParserUtil.checkFlag;
import static seedu.address.logic.parser.ParserUtil.extractPredicates;
import static seedu.address.logic.parser.ParserUtil.numOfPrefixesPresent;

import java.util.List;

import javafx.util.Pair;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.applicant.IdentifierPredicate;

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

        boolean isForceDelete;
        Pair<String, Boolean> checkFlagResult = checkFlag(args);
        args = checkFlagResult.getKey();
        isForceDelete = checkFlagResult.getValue();

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, COMMON_PREFIXES_WITH_ID);

        argMultimap.verifyNoDuplicatePrefixesFor(COMMON_PREFIXES_WITH_ID);

        boolean hasId = argMultimap.getValue(PREFIX_ID).isPresent();
        int numOfPrefixesPresentExceptId = numOfPrefixesPresent(argMultimap, COMMON_PREFIXES);
        // Allow ONLY id or any combination of other identifiers
        if ((numOfPrefixesPresentExceptId > 0 && hasId)
            || (numOfPrefixesPresentExceptId == 0 && !hasId)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }

        // If Index is provided, perform delete solely on the index
        if (argMultimap.getValue(PREFIX_ID).isPresent()) {
            Index index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_ID).get());
            return new DeleteCommand(index, isForceDelete); // Use index-based constructor
        }

        List<IdentifierPredicate> predicates = extractPredicates(argMultimap);
        assert predicates.size() == numOfPrefixesPresentExceptId;
        return new DeleteCommand(predicates, isForceDelete);
    }
}
