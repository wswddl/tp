package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an Applicant's application status
 * Guarantees: immutable; is valid as declared in {@link #isValidStatus(String)}
 */
public class Status {
    public static final String MESSAGE_CONSTRAINTS =
            "Statuses should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String status;

    /**
     * Constructs a {@code Status}.
     *
     * @param status A valid status.
     */
    public Status(String status) {
        requireNonNull(status);
        checkArgument(isValidStatus(status), MESSAGE_CONSTRAINTS);
        this.status = status;
    }

    /**
     * Returns true if a given string is a valid status.
     */
    public static boolean isValidStatus(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return this.status;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Status otherStatus)) {
            return false;
        }

        return this.status.equals(otherStatus.status);
    }

    @Override
    public int hashCode() {
        return this.status.hashCode();
    }
}
