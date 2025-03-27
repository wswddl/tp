package seedu.address.model.applicant;

import java.time.LocalDateTime;

/**
 * Represents a predicate that tests whether an {@code Applicant}'s application date
 * is after the specified date.
 */
public class AfterDatePredicate extends IdentifierPredicate {
    private final String afterDate;

    public AfterDatePredicate(String afterDate) {
        super(afterDate);
        this.afterDate = afterDate;
    }

    @Override
    public boolean test(Applicant applicant) {
        return applicant.getAddedTime().isAfter(LocalDateTime.parse(afterDate));
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof AfterDatePredicate && afterDate.equals(((AfterDatePredicate) other).afterDate);
    }
}
