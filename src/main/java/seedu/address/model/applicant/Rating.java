package seedu.address.model.applicant;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an Applicant's rating, from 1 to 5
 * Guarantees: immutable; has integer in range 1 to 5 as value
 */
public class Rating {
    public static final String MESSAGE_CONSTRAINTS =
            "Ratings should be on a scale of 1 to 5, with 5 being the most promising";

    /*
     * Rating should be an integer in range 1 to 5.
     */
    public static final String VALIDATION_REGEX = "^[1-5]$";

    public final String value;

    /**
     * Constructs a {@code Rating}.
     *
     * @param value A valid rating.
     */
    public Rating(String value) {
        requireNonNull(value);
        checkArgument(isValidRating(value), MESSAGE_CONSTRAINTS);
        this.value = value;
    }

    /**
     * Returns true if a given string is a valid rating.
     */
    public static boolean isValidRating(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Rating otherRating)) {
            return false;
        }

        return this.value.equals(otherRating.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return value;
    }

}
