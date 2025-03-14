package seedu.address.logic.parser;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

public class SortCommandParser implements Parser<SortCommand> {

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
