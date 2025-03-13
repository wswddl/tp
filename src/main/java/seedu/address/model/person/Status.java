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

// for next increment
//    public static final Status UNDER_REVIEW = new Status("Under review");
//    public static final Status SHORTLISTED = new Status("Shortlisted");
//    public static final Status INTERVIEW_SCHEDULED = new Status("Interview scheduled");
//    public static final Status OFFERED = new Status("Offer extended");
//    public static final Status ACCEPTED = new Status("Offer accepted");
//    public static final Status DECLINED = new Status("Offer declined");

    public final String value;

    /**
     * Constructs a {@code Status}.
     *
     * @param value A valid status String.
     */
    public Status(String value) {
        requireNonNull(value);
        checkArgument(isValidStatus(value), MESSAGE_CONSTRAINTS);
        this.value = value;
    }

    /**
     * Returns true if a given string is a valid status.
     */
    public static boolean isValidStatus(String test) {
        return test.matches(VALIDATION_REGEX);
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

        return this.value.equals(otherStatus.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + value + ']';
    }

}
