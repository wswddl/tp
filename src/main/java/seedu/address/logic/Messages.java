package seedu.address.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.applicant.Applicant;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_NO_RESULT = "No applicants found.";
    public static final String MESSAGE_UNKNOWN_FLAG = "Unknown flag: %1$s";
    public static final String MESSAGE_INVALID_CHARACTER_FILENAME_FORMAT =
                "Invalid filename. Only letters, digits, '-', '_', '.', and spaces are allowed.";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT =
                "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_EMPTY_FILENAME_FORMAT =
                "Filename must not be empty or start with a dot.";
    public static final String MESSAGE_INVALID_LONG_FILENAME_FORMAT =
                "Filename is too long. Keep it under 255 characters.";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX =
                "The applicant index provided is invalid";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW =
                "%1$d persons listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";
    public static final String MESSAGE_INVALID_CRITERIA_FORMAT =
                "Invalid criteria for %1$s \n%2$s";

    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
                Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Formats the {@code applicant} for display to the user.
     */
    public static String format(Applicant applicant) {
        final StringBuilder builder = new StringBuilder();
        builder.append(applicant.getName())
                .append("; Phone: ")
                .append(applicant.getPhone())
                .append("; Email: ")
                .append(applicant.getEmail())
                .append("; Address: ")
                .append(applicant.getAddress())
                .append("; Tags: ");
        applicant.getTags().forEach(builder::append);
        return builder.toString();
    }

}
