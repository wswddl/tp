package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_JOB_POSITION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.ParserUtil.COMMON_PREFIXES;
import static seedu.address.logic.parser.ParserUtil.COMMON_PREFIXES_WITH_ID;
import static seedu.address.logic.parser.ParserUtil.extractPredicates;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.applicant.EmailMatchesKeywordPredicate;
import seedu.address.model.applicant.IdentifierPredicate;
import seedu.address.model.applicant.JobPositionMatchesPredicate;
import seedu.address.model.applicant.NameMatchesKeywordPredicate;
import seedu.address.model.applicant.PhoneMatchesKeywordPredicate;
import seedu.address.model.applicant.StatusMatchesPredicate;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    /** Mapping of prefixes to their respective predicate constructors. */
    private static final Map<Prefix, Function<String, IdentifierPredicate>> predicateMapping = Map.of(
            PREFIX_NAME, NameMatchesKeywordPredicate::new,
            PREFIX_PHONE, PhoneMatchesKeywordPredicate::new,
            PREFIX_EMAIL, EmailMatchesKeywordPredicate::new,
            PREFIX_JOB_POSITION, JobPositionMatchesPredicate::new,
            PREFIX_STATUS, StatusMatchesPredicate::new
    );

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


        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, COMMON_PREFIXES_WITH_ID);

        argMultimap.verifyNoDuplicatePrefixesFor(COMMON_PREFIXES_WITH_ID);

        int numOfPrefixesPresentOtherThanId = numOfPrefixesPresent(argMultimap, COMMON_PREFIXES);
        // Allow ONLY id or any combination of other identifiers
        if ((numOfPrefixesPresentOtherThanId > 0 && argMultimap.getValue(PREFIX_ID).isPresent())
            || (numOfPrefixesPresentOtherThanId == 0 && argMultimap.getValue(PREFIX_ID).isEmpty())) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }

        // If Index is provided, perform delete solely on the index
        if (argMultimap.getValue(PREFIX_ID).isPresent()) {
            Index index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_ID).get());
            return new DeleteCommand(null, index, isForceDelete); // Use index-based constructor
        }

        List<IdentifierPredicate> predicates = extractPredicates(argMultimap);
        assert predicates.size() == numOfPrefixesPresentOtherThanId;
        return new DeleteCommand(predicates, null, isForceDelete);
    }

    /**
     * Counts the number of prefixes that have values in the given {@code ArgumentMultimap}.
     * i.e. number of prefixes provided in the argument.
     */
    private static int numOfPrefixesPresent(ArgumentMultimap argMultimap, Prefix... prefixes) {
        return (int) Stream.of(prefixes).filter(prefix -> argMultimap.getValue(prefix).isPresent()).count();
    }
}
