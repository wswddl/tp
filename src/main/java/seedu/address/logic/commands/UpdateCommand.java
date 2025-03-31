package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.applicant.Applicant;
import seedu.address.model.applicant.IdentifierPredicate;
import seedu.address.model.applicant.Status;

/**
 * Updates the {@code Status} of a {@code Applicant} identified using the specified contact identifier
 */
public class UpdateCommand extends Command {
    public static final String COMMAND_WORD = "update";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sets the application status of the applicant "
            + "identified by the specified contact identifier (name, email, or phone).\n"
            + "Parameters: IDENTIFIER_TYPE/KEYWORD s/STATUS\n"
            + "Example: " + COMMAND_WORD + " n/Alex Yeoh s/Interview Scheduled";

    public static final String MESSAGE_NO_MATCHES = "No applicant matches provided keyword!";
    public static final String MESSAGE_MULTIPLE_MATCHES = "%1$d persons matched keyword. Please be more specific!";

    public static final String MESSAGE_UPDATE_STATUS_SUCCESS = "Updated status of: %1$s";

    private final IdentifierPredicate predicate;
    private final Index targetIndex;
    private final Status status;

    /**
     * @param predicate     The predicate used to identify the {@code Applicant} to be updated.
     * @param status        The {@code Status} to which the {@code Applicant}'s status should be set to.
     */
    public UpdateCommand(IdentifierPredicate predicate, Status status) {
        this.predicate = predicate;
        this.targetIndex = null;
        this.status = status;
    }

    /**
     * @param targetIndex   The index of the {@code Applicant} to be updated in the filtered list.
     * @param status        The {@code Status} to which the {@code Applicant}'s status should be set to.
     */
    public UpdateCommand(Index targetIndex, Status status) {
        this.predicate = null;
        this.targetIndex = targetIndex;
        this.status = status;
    }

    /**
     * Executes update command with target identified by predicate or index,
     * depending on whether targetIndex was provided.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (targetIndex == null) {
            return updateByPredicate(model);
        } else {
            return updateByIndex(model);
        }
    }

    public CommandResult updateByPredicate(Model model) {
        model.updateFilteredPersonList(predicate);
        int numberOfMatches = model.getFilteredPersonListSize();
        if (numberOfMatches == 0) {
            return new CommandResult(String.format(MESSAGE_NO_MATCHES));
        } else if (numberOfMatches > 1) {
            return new CommandResult(String.format(MESSAGE_MULTIPLE_MATCHES, numberOfMatches));
        }
        Applicant target = model.getFilteredPersonList().get(0);
        Applicant updatedApplicant = model.setStatus(target, status);
        return new CommandResult(String.format(MESSAGE_UPDATE_STATUS_SUCCESS, Messages.format(updatedApplicant)));
    }

    public CommandResult updateByIndex(Model model) throws CommandException {
        List<Applicant> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Applicant target = lastShownList.get(targetIndex.getZeroBased());
        Applicant updatedApplicant = model.setStatus(target, status);
        return new CommandResult(String.format(MESSAGE_UPDATE_STATUS_SUCCESS, Messages.format(updatedApplicant)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UpdateCommand otherUpdateCommand)) {
            return false;
        }

        return predicate.equals(otherUpdateCommand.predicate);
    }

    @Override
    public String toString() {
        if (targetIndex == null) {
            return new ToStringBuilder(this)
                    .add("predicate", predicate)
                    .add("status", status)
                    .toString();
        } else {
            return new ToStringBuilder(this)
                    .add("targetIndex", targetIndex)
                    .add("status", status)
                    .toString();
        }
    }
}
