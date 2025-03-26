package seedu.address.model.applicant;

import seedu.address.commons.util.ToStringBuilder;

/**
 * A predicate that evaluates whether a given {@link Applicant}'s {@code Phone}
 * exactly matches a specified keyword (case-insensitive).
 * <p>
 * This predicate is typically used by the {@code search} command to filter applicants by phone number.
 */
public class PhoneMatchesKeywordPredicate extends IdentifierPredicate {

    /**
     * Constructs a {@code PhoneMatchesKeywordPredicate} with the specified keyword.
     *
     * @param keyword The phone number keyword to compare against the applicant's phone value.
     */
    public PhoneMatchesKeywordPredicate(String keyword) {
        super(keyword);
    }

    /**
     * Tests whether the applicant's phone number matches the keyword.
     * Matching is case-insensitive and exact (not partial).
     *
     * @param applicant The applicant to be tested.
     * @return {@code true} if the applicant's phone number exactly matches the keyword (case-insensitive), {@code false} otherwise.
     */
    @Override
    public boolean test(Applicant applicant) {
        return keyword.equalsIgnoreCase(applicant.getPhone().value);
    }

    /**
     * Checks whether this predicate is equal to another object.
     * Two {@code PhoneMatchesKeywordPredicate} instances are equal if they have the same keyword.
     *
     * @param other The object to compare to.
     * @return {@code true} if the other object is a {@code PhoneMatchesKeywordPredicate} with the same keyword.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof PhoneMatchesKeywordPredicate)) {
            return false;
        }

        PhoneMatchesKeywordPredicate otherPhoneMatchesKeywordPredicate = (PhoneMatchesKeywordPredicate) other;
        return keyword.equals(otherPhoneMatchesKeywordPredicate.keyword);
    }

    /**
     * Returns a string representation of this predicate for debugging purposes.
     *
     * @return A string representation showing the identifier type and keyword.
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("identifierType", "phone")
                .add("keyword", keyword).toString();
    }
}
