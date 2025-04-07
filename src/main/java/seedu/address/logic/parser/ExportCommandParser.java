package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_CHARACTER_FILENAME_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_EMPTY_FILENAME_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_EXPORT_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_LONG_FILENAME_FORMAT;

import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ExportCommand object.
 */
public class ExportCommandParser implements Parser<ExportCommand> {

    @Override
    public ExportCommand parse(String args) throws ParseException {
        String trimmed = args.trim();

        if (trimmed.isEmpty() || trimmed.startsWith(".")) {
            throw new ParseException(MESSAGE_INVALID_EMPTY_FILENAME_FORMAT);
        }

        // Basic invalid characters check
        if (!trimmed.matches("^[\\w\\-. ]+$")) {
            throw new ParseException(MESSAGE_INVALID_CHARACTER_FILENAME_FORMAT);
        }

        // Check if file is a csv
        if (!trimmed.endsWith(".csv")) {
            throw new ParseException(MESSAGE_INVALID_EXPORT_FORMAT);
        }

        // Avoid file name too long
        if (trimmed.length() > 255) {
            throw new ParseException(MESSAGE_INVALID_LONG_FILENAME_FORMAT);
        }

        return new ExportCommand(trimmed);
    }

}
