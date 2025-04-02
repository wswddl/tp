package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.ParserUtil.PREFIX_ORDER;
import static seedu.address.logic.parser.ParserUtil.extractPredicates;

import java.util.List;

import seedu.address.logic.commands.SearchCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.applicant.IdentifierPredicate;

/**
 * Parses input arguments and creates a new {@code SearchCommand} object.
 */
public class SearchCommandParser implements Parser<SearchCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the
     * {@code SearchCommand} and returns a {@code SearchCommand} object for
     * execution.
     *
     * @throws ParseException if the user input does not conform to the expected format.
     */
    @Override
    public SearchCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_ORDER);
        if (!argMultimap.areAnyPrefixesPresent(PREFIX_ORDER)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
        }
        //yuqian todo: check what is this verifyNoDuplicatePrefixesFor output
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_ORDER);

        List<IdentifierPredicate> predicates = extractPredicates(argMultimap);
        if (predicates.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
        }
        assert !predicates.isEmpty();

        return new SearchCommand(predicates);
    }
}
