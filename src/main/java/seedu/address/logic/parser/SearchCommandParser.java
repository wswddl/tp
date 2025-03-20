package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_JOB_POSITION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import seedu.address.logic.commands.SearchCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.applicant.EmailMatchesKeywordPredicate;
import seedu.address.model.applicant.IdentifierPredicate;
import seedu.address.model.applicant.JobPositionMatchesPredicate;
import seedu.address.model.applicant.NameMatchesKeywordPredicate;
import seedu.address.model.applicant.StatusMatchesPredicate;

/**
 * Parses input arguments and creates a new {@code SearchCommand} object.
 */
public class SearchCommandParser implements Parser<SearchCommand> {

    /** Mapping of prefixes to their respective predicate constructors. */
    private static final Map<Prefix, Function<String, IdentifierPredicate>> predicateMapping = Map.of(
            PREFIX_NAME, NameMatchesKeywordPredicate::new,
            PREFIX_EMAIL, EmailMatchesKeywordPredicate::new,
            PREFIX_JOB_POSITION, JobPositionMatchesPredicate::new,
            PREFIX_STATUS, StatusMatchesPredicate::new
    );

    /**
     * Parses the given {@code String} of arguments in the context of the {@code SearchCommand}
     * and returns a {@code SearchCommand} object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format.
     */
    @Override
    public SearchCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
            args, predicateMapping.keySet().toArray(new Prefix[0]));

        if (!argMultimap.areAnyPrefixesPresent(predicateMapping.keySet().toArray(new Prefix[0]))) {
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

        predicateMapping.forEach((prefix, predicateConstructor) ->
                argMultimap.getValue(prefix).ifPresent(value -> predicates.add(predicateConstructor.apply(value)))
        );

        return predicates;
    }
}
