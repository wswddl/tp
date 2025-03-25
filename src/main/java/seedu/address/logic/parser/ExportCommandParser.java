package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ExportCommand object.
 */
public class ExportCommandParser implements Parser<ExportCommand> {

    @Override
    public ExportCommand parse(String args) throws ParseException {
        String trimmed = args.trim();
        if (trimmed.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));
        }

        if (!trimmed.matches("^[\\w\\-. ]+\\.csv$")) {
            throw new ParseException("Invalid filename. Only letters, digits, '-', '_' and '.' are allowed.");
        }
        
        return new ExportCommand(trimmed);
    }
}
