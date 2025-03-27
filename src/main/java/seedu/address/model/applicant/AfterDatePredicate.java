package seedu.address.model.applicant;

import java.time.LocalDateTime;

/**
 * Represents a predicate that tests whether an {@code Applicant}'s application date
 * is after the specified date.
 */
public class AfterDatePredicate extends IdentifierPredicate {
    private final LocalDateTime afterDate;

    /**
     * Constructs a {@code AfterDatePredicate} with the given date.
     *
     * @param afterDate The cutoff date. Applicants with an application date after this will be matched.
     */
    public AfterDatePredicate(LocalDateTime afterDate) {
        super(afterDate.toString());
        this.afterDate = afterDate;
    }

    @Override
    public boolean test(Applicant applicant) {
        return applicant.getAddedTime().isAfter(afterDate);
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof AfterDatePredicate && afterDate.equals(((AfterDatePredicate) other).afterDate);
    }
}
