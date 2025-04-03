package seedu.address.model.applicant;

import seedu.address.commons.util.ToStringBuilder;

/**
 * A predicate that evaluates whether a given {@link Applicant}'s {@code Name}
 * exactly matches a specified keyword (case-insensitive).
 * <p>
 * This predicate is used by the {@code search} command to filter applicants by their name.
 */
public class NameMatchesKeywordPredicate extends IdentifierPredicate {

    /**
     * Constructs a {@code NameMatchesKeywordPredicate} with the specified keyword.
     *
     * @param keyword The name keyword to compare against the applicant's name.
     */
    public NameMatchesKeywordPredicate(String keyword) {
        super(keyword);
    }

    /**
     * Tests whether the applicant's full name matches the keyword.
     * Matching is case-insensitive and exact (not partial).
     *
     * @param applicant The applicant to be tested.
     * @return {@code true} if the applicant's name exactly matches the keyword (case-insensitive),
     *         {@code false} otherwise.
     */
    @Override
    public boolean test(Applicant applicant) {
        return keyword.equalsIgnoreCase(applicant.getName().fullName);
    }

    /**
     * Checks whether this predicate is equal to another object.
     * Two {@code NameMatchesKeywordPredicate} instances are equal if they have the same keyword.
     *
     * @param other The object to compare to.
     * @return {@code true} if the other object is a {@code NameMatchesKeywordPredicate} with the same keyword.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof NameMatchesKeywordPredicate)) {
            return false;
        }

        NameMatchesKeywordPredicate otherNameMatchesKeywordPredicate = (NameMatchesKeywordPredicate) other;
        return keyword.equals(otherNameMatchesKeywordPredicate.keyword);
    }

    /**
     * Returns a string representation of this predicate for debugging purposes.
     *
     * @return A string representation showing the identifier type and keyword.
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("identifierType", "name")
                .add("keyword", keyword).toString();
    }
}
