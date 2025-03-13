package seedu.address.logic.parser;

import com.sun.prism.Presentable;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_CRITERIA_FORMAT;

public class SortCommandParser implements Parser<SortCommand> {

    @Override
    public SortCommand parse(String args) throws ParseException {
        try {
            String input = args.trim();
            return new SortCommand(new Prefix(input));

        } catch (CommandException e) {
            throw new ParseException(e.getMessage());
        }

        /*
        if (args.equals(" n/")) {
            return new SortCommand(new Prefix("n/"));
        } else if (args.equals(" e/")) {
            return new SortCommand(new Prefix("e/"));
        } else if (args.equals(" id/")) {
            return new SortCommand(new Prefix("id/"));
        } else if (args.equals(" jp/")) {
            return new SortCommand(new Prefix("jp/"));
        } else if (args.equals(" s/")) {
            return new SortCommand(new Prefix("s/"));
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_CRITERIA_FORMAT, "sorting", SortCommand.MESSAGE_USAGE));
        }
         */
    }

}
