package seedu.address.model.applicant;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an applicant's applying JobPosition.
 * Guarantees: immutable; is valid as declared in {@link #isValidJobPosition(String)}
 */
public class JobPosition {
    public static final String MESSAGE_CONSTRAINTS =
            "Job Positions should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the JobPosition must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String jobPosition;


    /**
     * Constructs a {@code JobPosition}.
     *
     * @param jobPosition A valid name.
     */
    public JobPosition(String jobPosition) {
        requireNonNull(jobPosition);
        checkArgument(isValidJobPosition(jobPosition), MESSAGE_CONSTRAINTS);
        this.jobPosition = jobPosition;
    }

    /**
     * Returns true if a given string is a valid Job Position.
     */
    public static boolean isValidJobPosition(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return this.jobPosition;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof JobPosition otherJobPosition)) {
            return false;
        }

        return this.jobPosition.equals(otherJobPosition.jobPosition);
    }

    @Override
    public int hashCode() {
        return this.jobPosition.hashCode();
    }
}
