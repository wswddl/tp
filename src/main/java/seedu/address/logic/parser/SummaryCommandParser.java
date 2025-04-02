package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.ParserUtil.PREFIX_ORDER;
import static seedu.address.logic.parser.ParserUtil.extractPredicates;

import java.util.List;

import seedu.address.logic.commands.SearchCommand;
import seedu.address.logic.commands.SummaryCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.applicant.IdentifierPredicate;

/**
 * Parses input arguments and creates a new {@code SummaryCommand} object.
 */
public class SummaryCommandParser implements Parser<SummaryCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the {@code SummaryCommand}
     * and returns a {@code SummaryCommand} object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format.
     */
    @Override
    public SummaryCommand parse(String args) throws ParseException {
        // Note: There can be no predicates i.e. Summarize all
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
                args, PREFIX_ORDER);
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_ORDER);

        List<IdentifierPredicate> predicates = extractPredicates(argMultimap);
        //yuqian todo: test this okay!
        if (argMultimap.areAnyPrefixesPresent(PREFIX_ORDER) && predicates.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
        }
        return new SummaryCommand(predicates);
    }
}
