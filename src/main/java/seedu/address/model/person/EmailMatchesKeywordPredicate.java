package seedu.address.model.person;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Email} matches the keyword given.
 */
public class EmailMatchesKeywordPredicate extends IdentifierPredicate {

    public EmailMatchesKeywordPredicate(String keyword) {
        super(keyword);
    }

    @Override
    public boolean test(Person person) {
        return keyword.equalsIgnoreCase(person.getEmail().value);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EmailMatchesKeywordPredicate)) {
            return false;
        }

        EmailMatchesKeywordPredicate otherEmailMatchesKeywordPredicate = (EmailMatchesKeywordPredicate) other;
        return keyword.equals(otherEmailMatchesKeywordPredicate.keyword);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("identifierType", "email")
                .add("keyword", keyword).toString();
    }
}
