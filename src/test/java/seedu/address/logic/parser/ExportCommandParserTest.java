package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ExportCommand;

/**
 * Contains unit tests for {@link ExportCommandParser}.
 */
public class ExportCommandParserTest {

    private final ExportCommandParser parser = new ExportCommandParser();

    /**
     * Tests that a valid CSV filename is parsed successfully.
     */
    @Test
    public void parse_validFileName_success() {
        assertParseSuccess(parser, "applicants.csv", new ExportCommand("applicants.csv"));
    }

    /**
     * Tests that surrounding whitespace is trimmed and file name is still valid.
     */
    @Test
    public void parse_validFileNameWithSpaces_success() {
        assertParseSuccess(parser, "  my_export.csv  ", new ExportCommand("my_export.csv"));
    }

    /**
     * Tests that missing file name throws a parse exception.
     */
    @Test
    public void parse_missingFileName_throwsParseException() {
        assertParseFailure(parser, "", "Filename must not be empty or start with a dot.");
        assertParseFailure(parser, "   ", "Filename must not be empty or start with a dot.");
    }

    /**
     * Tests that invalid characters in the file name are rejected.
     */
    @Test
    public void parse_invalidCharacters_throwsParseException() {
        assertParseFailure(parser, "bad/filename.csv",
                "Invalid filename. Only letters, digits, '-', '_', '.', and spaces are allowed.");
        assertParseFailure(parser, "my:file.csv",
                "Invalid filename. Only letters, digits, '-', '_', '.', and spaces are allowed.");
        assertParseFailure(parser, "<bad>.csv",
                "Invalid filename. Only letters, digits, '-', '_', '.', and spaces are allowed.");
    }

    /**
     * Tests that file names starting with a dot are rejected.
     */
    @Test
    public void parse_leadingDot_throwsParseException() {
        assertParseFailure(parser, ".hiddenfile", "Filename must not be empty or start with a dot.");
        assertParseFailure(parser, ".csv", "Filename must not be empty or start with a dot.");
    }

    /**
     * Tests that long file names are rejected.
     */
    @Test
    public void parse_fileNameTooLong_throwsParseException() {
        String longName = "a".repeat(260) + ".csv"; // exceeds common filesystem limits
        assertParseFailure(parser, longName, "Filename is too long. Keep it under 255 characters.");
    }
}
