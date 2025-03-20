package seedu.address.logic.parser;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SortCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {

    /**
     * Parse the given {@code String} and create a SortCommand object.
     * The checking of valid sorting criteria is done by SortCommand constructor
     * @param args is the sorting criteria from the user input
     * @throws ParseException if the user input is not a valid sorting criteria
     */
    @Override
    public SortCommand parse(String args) throws ParseException {
        try {
            String input = args.trim();
            return new SortCommand(new Prefix(input));

        } catch (CommandException e) {
            throw new ParseException(e.getMessage());
        }

    }

}
