package seedu.address.logic.commands;

import static seedu.address.logic.Messages.MESSAGE_NO_RESULT;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.applicant.Applicant;
import seedu.address.model.applicant.IdentifierPredicate;
import seedu.address.model.applicant.Rating;

/**
 * Assigns a {@code Rating} to a {@code Applicant} identified using the specified contact identifier
 */
public class RateCommand extends Command {
    public static final String COMMAND_WORD = "rate";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Assigns a rating of 1 to 5 to the applicant "
            + "identified by the specified contact identifier (name, email, or phone).\n"
            + "Parameters: IDENTIFIER_TYPE/KEYWORD r/RATING\n"
            + "Example: " + COMMAND_WORD + " n/Alex Yeoh r/4";

    public static final String MESSAGE_MULTIPLE_MATCHES = "%1$d persons matched keyword. Please be more specific!";

    public static final String MESSAGE_ASSIGN_RATING_SUCCESS = "Assigned a rating of %1$s to: %2$s";

    private final IdentifierPredicate predicate;
    private final Index targetIndex;
    private final Rating rating;

    /**
     * @param predicate     The predicate used to identify the {@code Applicant} to be rated.
     * @param rating        The {@code Rating} to be assigned to the {@code Applicant}.
     */
    public RateCommand(IdentifierPredicate predicate, Rating rating) {
        this.predicate = predicate;
        this.targetIndex = null;
        this.rating = rating;
    }

    /**
     * @param targetIndex   The index of the {@code Applicant} to be updated in the filtered list.
     * @param rating        The {@code Rating} to be assigned to the {@code Applicant}.
     */
    public RateCommand(Index targetIndex, Rating rating) {
        this.predicate = null;
        this.targetIndex = targetIndex;
        this.rating = rating;
    }

    /**
     * Executes rate command with target identified by predicate or index,
     * depending on whether targetIndex was provided.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (targetIndex == null) {
            return rateByPredicate(model);
        } else {
            return rateByIndex(model);
        }
    }

    /**
     * Rates a single applicant based on an identifier predicate.
     * If zero or multiple matches are found, shows an appropriate message.
     *
     * @param model The model containing the list of applicants.
     * @return A {@code CommandResult} describing the result.
     */
    public CommandResult rateByPredicate(Model model) {
        model.updateFilteredPersonList(predicate);
        int numberOfMatches = model.getFilteredPersonListSize();

        if (numberOfMatches == 0) {
            return new CommandResult(MESSAGE_NO_RESULT);
        } else if (numberOfMatches > 1) {
            return new CommandResult(String.format(MESSAGE_MULTIPLE_MATCHES, numberOfMatches));
        }

        Applicant target = model.getFilteredPersonList().get(0);
        Applicant updatedApplicant = model.setRating(target, rating);

        return new CommandResult(String.format(MESSAGE_ASSIGN_RATING_SUCCESS,
                rating.toString(), Messages.format(updatedApplicant)));
    }

    /**
     * Rates an applicant by their index in the current filtered list.
     *
     * @param model The model containing the list of applicants.
     * @return A {@code CommandResult} describing the result.
     * @throws CommandException If the index is out of bounds.
     */
    public CommandResult rateByIndex(Model model) throws CommandException {
        List<Applicant> lastShownList = model.getFilteredPersonList();

        assert targetIndex != null;
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Applicant target = lastShownList.get(targetIndex.getZeroBased());
        Applicant updatedApplicant = model.setRating(target, rating);
        return new CommandResult(String.format(MESSAGE_ASSIGN_RATING_SUCCESS,
                rating.toString(), Messages.format(updatedApplicant)));
    }

    /**
     * Checks whether this command is equal to another command.
     *
     * @param other The other object to compare with.
     * @return True if both commands are equal based on their target and rating.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RateCommand otherRateCommand)) {
            return false;
        }

        // check if both RateCommand instances are of the same identifier type
        if (this.targetIndex == null && otherRateCommand.targetIndex != null) {
            return false;
        }
        if (this.targetIndex != null && otherRateCommand.targetIndex == null) {
            return false;
        }

        if (targetIndex == null) {
            assert predicate != null;
            return predicate.equals(otherRateCommand.predicate) && rating.equals(otherRateCommand.rating);
        } else {
            return targetIndex.equals(otherRateCommand.targetIndex) && rating.equals(otherRateCommand.rating);
        }
    }

    /**
     * Returns a string representation of the command for debugging.
     */
    @Override
    public String toString() {
        if (targetIndex == null) {
            return new ToStringBuilder(this)
                    .add("predicate", predicate)
                    .add("rating", rating)
                    .toString();
        } else {
            return new ToStringBuilder(this)
                    .add("targetIndex", targetIndex)
                    .add("rating", rating)
                    .toString();
        }
    }
}
