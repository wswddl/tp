package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_CRITERIA_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASCENDING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCENDING;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SortCommand object
 */
@SuppressWarnings("checkstyle:Regexp")
public class SortCommandParser implements Parser<SortCommand> {

    /**
     * Parse the given {@code String} and create a SortCommand object.
     * The checking of valid sorting criteria is done by SortCommand constructor
     * @param args is the sorting criteria from the user input
     * @throws ParseException if the user input is not a valid sorting criteria
     */
    @Override
    public SortCommand parse(String args) throws ParseException {
        requireNonNull(args);
        try {
            String input = args.trim();
            String[] parts = input.split("\\s+");

            if (parts.length > 2) {
                throw new ParseException(String.format(
                        MESSAGE_INVALID_CRITERIA_FORMAT, "sorting", SortCommand.MESSAGE_USAGE));
            }
            // if user didn't specify the order, sort in ascending order by default
            boolean isAscendingOrder = true;

            if (parts.length > 1) {
                Prefix sortOrder = new Prefix(parts[1]);
                if (sortOrder.equals(PREFIX_ASCENDING)) {
                    isAscendingOrder = true;
                } else if (sortOrder.equals(PREFIX_DESCENDING)) {
                    isAscendingOrder = false;
                } else {
                    throw new ParseException(String.format(
                            MESSAGE_INVALID_CRITERIA_FORMAT, "sorting", SortCommand.MESSAGE_USAGE));
                }
            }
            String criteria = parts[0];
            Prefix p = new Prefix(criteria);
            return new SortCommand(p, isAscendingOrder);

        } catch (CommandException e) {
            throw new ParseException(e.getMessage());
        }
    }
}



