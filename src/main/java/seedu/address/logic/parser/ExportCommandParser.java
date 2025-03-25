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
    
        if (trimmed.isEmpty() || trimmed.startsWith(".")) {
            throw new ParseException("Filename must not be empty or start with a dot.");
        }
    
        // Basic invalid characters check
        if (!trimmed.matches("^[\\w\\-. ]+$")) {
            throw new ParseException("Invalid filename. Only letters, digits, '-', '_', '.', and spaces are allowed.");
        }
    
        // Append .csv if not already there
        if (!trimmed.endsWith(".csv")) {
            trimmed += ".csv";
        }
        
        // Avoid file name too long
        if (trimmed.length() > 255) {
            throw new ParseException("Filename is too long. Keep it under 255 characters.");
        }
        
        return new ExportCommand(trimmed);
    }
    
}
