package seedu.address.model.applicant;

import java.time.LocalDateTime;

/**
 * Represents a predicate that tests whether an {@code Applicant}'s application date
 * is before the specified date.
 */
public class BeforeDatePredicate extends IdentifierPredicate {
    private final LocalDateTime beforeDate;

    public BeforeDatePredicate(LocalDateTime beforeDate) {
        super(beforeDate.toString());
        this.beforeDate = beforeDate;
    }

    @Override
    public boolean test(Applicant applicant) {
        return applicant.getAddedTime().isBefore(beforeDate);
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof BeforeDatePredicate
                && beforeDate.equals(((BeforeDatePredicate) other).beforeDate);
    }
}
