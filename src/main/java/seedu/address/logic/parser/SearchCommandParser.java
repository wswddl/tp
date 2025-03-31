package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.ParserUtil.*;

import java.util.ArrayList;
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
     * @throws ParseException if the user input does not conform to the expected
     * format.
     */
    @Override
    public SearchCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_ORDER);
        System.out.println(argMultimap);
        if (!argMultimap.areAnyPrefixesPresent(PREFIX_ORDER)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
        }

        List<IdentifierPredicate> predicates = extractPredicates(argMultimap);

        if (predicates.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
        }

        return new SearchCommand(predicates);
    }

    /**
     * Extracts predicates from the given {@code ArgumentMultimap}.
     *
     * @param argMultimap The parsed argument multimap.
     * @return A list of identifier predicates for filtering applicants.
     */
    private List<IdentifierPredicate> extractPredicates(ArgumentMultimap argMultimap) {
        List<IdentifierPredicate> predicates = new ArrayList<>();

        for (Prefix prefix : PREFIX_ORDER) {
            if (argMultimap.getValue(prefix).isPresent()) {
                String value = argMultimap.getValue(prefix).get();
                predicates.add(predicateMapping.get(prefix).apply(value));
            }
        }

        return predicates;
    }

}
