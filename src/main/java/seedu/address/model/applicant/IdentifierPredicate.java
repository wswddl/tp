package seedu.address.model.applicant;

import java.util.function.Predicate;

/**
 * Structure for tests that check if a {@code Applicant}'s details matches the keyword given.
 */
public abstract class IdentifierPredicate implements Predicate<Applicant> {

    protected final String keyword;

    protected IdentifierPredicate(String keyword) {
        this.keyword = keyword;
    }

    public abstract boolean test(Applicant applicant);

}
