package seedu.address.model.person;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Phone} matches the keyword given.
 */
public class PhoneMatchesKeywordPredicate extends IdentifierPredicate {

    public PhoneMatchesKeywordPredicate(String keyword) {
        super(keyword);
    }

    @Override
    public boolean test(Person person) {
        return keyword.equalsIgnoreCase(person.getPhone().value);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PhoneMatchesKeywordPredicate)) {
            return false;
        }

        PhoneMatchesKeywordPredicate otherPhoneMatchesKeywordPredicate = (PhoneMatchesKeywordPredicate) other;
        return keyword.equals(otherPhoneMatchesKeywordPredicate.keyword);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("identifierType", "phone")
                .add("keyword", keyword).toString();
    }
}
