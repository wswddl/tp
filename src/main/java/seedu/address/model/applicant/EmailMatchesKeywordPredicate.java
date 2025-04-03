package seedu.address.model.applicant;

import seedu.address.commons.util.ToStringBuilder;

/**
 * A predicate that evaluates whether a given {@link Applicant}'s {@code Email}
 * exactly matches a specified keyword (case-insensitive).
 * <p>
 * This is used in the {@code search} command to filter applicants by their email.
 */
public class EmailMatchesKeywordPredicate extends IdentifierPredicate {

    /**
     * Constructs a new {@code EmailMatchesKeywordPredicate} with the specified keyword.
     *
     * @param keyword The email keyword to compare against the applicant's email.
     */
    public EmailMatchesKeywordPredicate(String keyword) {
        super(keyword);
    }

    /**
     * Tests whether the applicant's email matches the keyword.
     * Matching is case-insensitive and exact (not partial).
     *
     * @param applicant The applicant to be tested.
     * @return {@code true} if the applicant's email exactly matches the keyword (case-insensitive), {@code false} otherwise.
     */
    @Override
    public boolean test(Applicant applicant) {
        return keyword.equalsIgnoreCase(applicant.getEmail().value);
    }

    /**
     * Checks whether this predicate is equal to another object.
     * Two {@code EmailMatchesKeywordPredicate} objects are equal if they have the same keyword.
     *
     * @param other The object to compare to.
     * @return {@code true} if the other object is an {@code EmailMatchesKeywordPredicate} with the same keyword.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof EmailMatchesKeywordPredicate otherEmailMatchesKeywordPredicate)) {
            return false;
        }

        return keyword.equals(otherEmailMatchesKeywordPredicate.keyword);
    }

    /**
     * Returns a string representation of this predicate for debugging purposes.
     *
     * @return A string representation showing the identifier type and keyword.
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("identifierType", "email")
                .add("keyword", keyword).toString();
    }

    /**
     * Returns the hash code of this predicate, based on its keyword.
     *
     * @return The hash code for this predicate.
     */
    @Override
    public int hashCode() {
        return keyword.hashCode();
    }
}
