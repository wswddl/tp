package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_NO_RESULT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AFTER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BEFORE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_JOB_POSITION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;

import java.util.List;
import java.util.stream.Collectors;

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
            + "identified by the provided identifier(s).\n"
            + "Parameters: (Must include Status, and specify EITHER an ID or any combination of other identifiers) "
            + "[" + PREFIX_ID + "INDEX] "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_STATUS + "STATUS] "
            + "[" + PREFIX_JOB_POSITION + "JOB_POSITION] "
            + "[" + PREFIX_BEFORE + "YYYY-MM-DD] "
            + "[" + PREFIX_AFTER + "YYYY-MM-DD] "
            + "[--force]\n"
            + "Example: " + COMMAND_WORD + " n/Alex Yeoh s/Interview Scheduled";

    static final String MESSAGE_UPDATE_CONFIRMATION =
            "Are you sure you want to update the following applicant(s)?\n"
                    + "Type 'yes' to continue\n"
                    + "Type anything else to cancel the update";

    static final String MESSAGE_ID_UPDATE_CONFIRMATION =
            "Are you sure you want to update applicant %d in the list?\n"
                    + "Type 'yes' to continue\n"
                    + "Type anything else to cancel the update";

    private static final String MESSAGE_UPDATE_STATUS_SUCCESS = "Updated status of: %1$s";

    private final List<IdentifierPredicate> predicates;
    private final Index targetIndex;
    private final Status status;
    private boolean isForceUpdate;

    /**
     * @param predicates The predicate used to identify the {@code Applicant} to be updated.
     * @param status    The {@code Status} to which the {@code Applicant}'s status should be set to.
     * @param isForceUpdate The flag to specify whether it is force deletion or not.
     */
    public UpdateCommand(List<IdentifierPredicate> predicates, Status status, boolean isForceUpdate) {
        this.predicates = predicates;
        this.targetIndex = null;
        this.status = status;
        this.isForceUpdate = isForceUpdate;
    }

    /**
     * @param targetIndex The index of the {@code Applicant} to be updated in the filtered list.
     * @param status      The {@code Status} to which the {@code Applicant}'s status should be set to.
     * @param isForceUpdate The flag to specify whether it is force deletion or not.
     */
    public UpdateCommand(Index targetIndex, Status status, boolean isForceUpdate) {
        this.predicates = null;
        this.targetIndex = targetIndex;
        this.status = status;
        this.isForceUpdate = isForceUpdate;
    }

    /**
     * Executes update command with target identified by predicate or index,
     * depending on whether targetIndex was provided.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (targetIndex == null) {
            return updateByPredicate(model);
        } else {
            return updateByIndex(model);
        }
    }

    public CommandResult updateByPredicate(Model model) throws CommandException {
        requireNonNull(model);

        // Apply filtering predicates
        model.updateFilteredPersonList(applicant -> {
            assert predicates != null;
            return predicates.stream().allMatch(predicate -> predicate.test(applicant));
        });

        List<Applicant> filteredList = model.getFilteredPersonList();
        if (filteredList.isEmpty()) {
            throw new CommandException(MESSAGE_NO_RESULT);
        }

        if (!isForceUpdate) {
            return new CommandResult(MESSAGE_UPDATE_CONFIRMATION);
        }
        List<Applicant> applicantsToUpdate = List.copyOf(filteredList);
        for (Applicant applicant : applicantsToUpdate) {
            model.setStatus(applicant, status);
        }
        String updatedApplicants = applicantsToUpdate.stream()
                .map(Messages::format)
                .collect(Collectors.joining("\n\n"));

        return new CommandResult(String.format(MESSAGE_UPDATE_STATUS_SUCCESS, updatedApplicants));
    }

    public CommandResult updateByIndex(Model model) throws CommandException {
        List<Applicant> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        Applicant applicantToUpdate = lastShownList.get(targetIndex.getZeroBased());
        if (!isForceUpdate) {
            return new CommandResult(String.format(MESSAGE_ID_UPDATE_CONFIRMATION, targetIndex.getOneBased()));
        }
        Applicant updatedApplicant = model.setStatus(applicantToUpdate, status);
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

        assert predicates != null;
        return predicates.equals(otherUpdateCommand.predicates);
    }

    @Override
    public String toString() {
        if (targetIndex == null) {
            return new ToStringBuilder(this)
                    .add("predicate", predicates)
                    .add("status", status)
                    .toString();
        } else {
            return new ToStringBuilder(this)
                    .add("targetIndex", targetIndex)
                    .add("status", status)
                    .toString();
        }
    }

    public void setForceUpdate(boolean isForceDelete) {
        this.isForceUpdate = isForceDelete;
    }
}
