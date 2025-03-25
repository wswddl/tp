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

    /**
     * The order in which prefixes are parsed. This determines the order of predicates in the final command.
     */
    private static final Prefix[] SEARCH_PREFIX_ORDER = {
        PREFIX_NAME, PREFIX_EMAIL, PREFIX_JOB_POSITION, PREFIX_STATUS
    };

    /** 
     * Mapping of prefixes to their respective predicate constructors.
    */
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
    
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, SEARCH_PREFIX_ORDER);
        System.out.println(argMultimap);
        if (!argMultimap.areAnyPrefixesPresent(SEARCH_PREFIX_ORDER)) {
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
    
        for (Prefix prefix : SEARCH_PREFIX_ORDER) {
            if (argMultimap.getValue(prefix).isPresent()) {
                String value = argMultimap.getValue(prefix).get();
                predicates.add(predicateMapping.get(prefix).apply(value));
            }
        }
    
        return predicates;
    }
    
}
