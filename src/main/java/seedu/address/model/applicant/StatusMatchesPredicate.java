package seedu.address.model.applicant;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents a predicate that tests whether an {@code Applicant}'s status
 * matches a specified keyword. The match is case-insensitive and requires an exact match.
 */
public class StatusMatchesPredicate extends IdentifierPredicate {

    /**
     * Constructs a {@code StatusMatchesPredicate} with the given keyword.
     *
     * @param keyword The status keyword to match.
     */
    public StatusMatchesPredicate(String keyword) {
        super(keyword);
    }

    /**
     * Evaluates this predicate on the given applicant.
     *
     * @param applicant The applicant whose status is to be tested.
     * @return {@code true} if the applicant's status matches the keyword (case-insensitive),
     *         otherwise {@code false}.
     */
    @Override
    public boolean test(Applicant applicant) {
        return keyword.equalsIgnoreCase(applicant.getStatus().value);
    }

    /**
     * Checks if this predicate is equal to another object.
     *
     * @param other The object to compare.
     * @return {@code true} if the other object is a {@code StatusMatchesPredicate} with the same keyword,
     *         otherwise {@code false}.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles null cases
        if (!(other instanceof StatusMatchesPredicate)) {
            return false;
        }

        StatusMatchesPredicate otherStatusMatchesPredicate = (StatusMatchesPredicate) other;
        return keyword.equals(otherStatusMatchesPredicate.keyword);
    }

    /**
     * Returns a string representation of this predicate.
     *
     * @return A string representation containing the status identifier and the keyword.
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("identifierType", "status")
                .add("keyword", keyword).toString();
    }
}
