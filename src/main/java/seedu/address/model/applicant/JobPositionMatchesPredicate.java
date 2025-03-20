package seedu.address.model.applicant;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents a predicate that tests whether an {@code Applicant}'s job position
 * matches a specified keyword. The match is case-insensitive and requires an exact match.
 */
public class JobPositionMatchesPredicate extends IdentifierPredicate {

    /**
     * Constructs a {@code JobPositionMatchesPredicate} with the given keyword.
     *
     * @param keyword The job position keyword to match.
     */
    public JobPositionMatchesPredicate(String keyword) {
        super(keyword);
    }

    /**
     * Evaluates this predicate on the given applicant.
     *
     * @param applicant The applicant whose job position is to be tested.
     * @return {@code true} if the applicant's job position matches the keyword (case-insensitive),
     *         otherwise {@code false}.
     */
    @Override
    public boolean test(Applicant applicant) {
        return keyword.equalsIgnoreCase(applicant.getJobPosition().jobPosition);
    }

    /**
     * Checks if this predicate is equal to another object.
     *
     * @param other The object to compare.
     * @return {@code true} if the other object is a {@code JobPositionMatchesPredicate} with the same keyword,
     *         otherwise {@code false}.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles null cases
        if (!(other instanceof JobPositionMatchesPredicate)) {
            return false;
        }

        JobPositionMatchesPredicate otherJobPositionMatchesPredicate = (JobPositionMatchesPredicate) other;
        return keyword.equals(otherJobPositionMatchesPredicate.keyword);
    }

    /**
     * Returns a string representation of this predicate.
     *
     * @return A string representation containing the job position identifier and the keyword.
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("identifierType", "jobPosition")
                .add("keyword", keyword).toString();
    }
}
