package seedu.address.model.person;

import java.util.function.Predicate;

/**
 * Structure for tests that check if a {@code Person}'s details matches the keyword given.
 */
public abstract class IdentifierPredicate implements Predicate<Person> {

    protected final String keyword;

    protected IdentifierPredicate(String keyword) {
        this.keyword = keyword;
    }

    public abstract boolean test(Person person);

}
