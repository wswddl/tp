package seedu.address.model.person;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Name} matches the keyword given.
 */
public class NameMatchesKeywordPredicate extends IdentifierPredicate {

    public NameMatchesKeywordPredicate(String keyword) {
        super(keyword);
    }

    @Override
    public boolean test(Person person) {
        return keyword.equalsIgnoreCase(person.getName().fullName);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NameMatchesKeywordPredicate)) {
            return false;
        }

        NameMatchesKeywordPredicate otherNameMatchesKeywordPredicate = (NameMatchesKeywordPredicate) other;
        return keyword.equals(otherNameMatchesKeywordPredicate.keyword);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("identifierType", "name")
                .add("keyword", keyword).toString();
    }
}
