package seedu.address.model.person;

import java.util.function.Predicate;

abstract public class IdentifierPredicate implements Predicate<Person> {

    protected final String keyword;

    abstract public boolean test(Person person);

    protected IdentifierPredicate(String keyword) {
        this.keyword = keyword;
    }

}
