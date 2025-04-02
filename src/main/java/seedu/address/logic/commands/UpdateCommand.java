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

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.applicant.Applicant;
import seedu.address.model.applicant.IdentifierPredicate;
import seedu.address.model.applicant.Status;

/**
 * Updates the status of applicants identified by index or by matching criteria.
 * <p>
 * Supports both individual updates by index and bulk updates using multiple
 * filtering criteria. Requires a status parameter and includes confirmation
 * prompts unless --force flag is used.
 */
public class UpdateCommand extends ConfirmationRequiredCommand {
    public static final String COMMAND_WORD = "update";

    /** Message explaining the command usage and parameters */
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

    /** Confirmation message for bulk updates */
    private static final String MESSAGE_UPDATE_CONFIRMATION =
            "Are you sure you want to update the following applicant(s)?\n"
                    + "Type 'yes' to continue\n"
                    + "Type anything else to cancel the update";

    /** Confirmation message for index-based updates */
    private static final String MESSAGE_ID_UPDATE_CONFIRMATION =
            "Are you sure you want to update applicant %d in the list?\n"
                    + "Type 'yes' to continue\n"
                    + "Type anything else to cancel the update";

    /** Success message after update */
    private static final String MESSAGE_UPDATE_STATUS_SUCCESS = "Updated status of: %1$s";

    /** The new status to set for matching applicants */
    private final Status status;

    /**
     * Creates an UpdateCommand that updates applicants matching the given predicates.
     *
     * @param predicates The criteria to filter applicants, must not be null
     * @param status The new status to set, must not be null
     * @param isForceOperation Whether to skip confirmation
     * @throws NullPointerException if predicates or status is null
     */
    public UpdateCommand(List<IdentifierPredicate> predicates, Status status, boolean isForceOperation) {
        super(predicates, isForceOperation);
        requireNonNull(status);
        this.status = status;
    }

    /**
     * Creates an UpdateCommand that updates the applicant at the given index.
     *
     * @param targetIndex The index of the applicant to update, must not be null
     * @param status The new status to set, must not be null
     * @param isForceOperation Whether to skip confirmation
     * @throws NullPointerException if targetIndex or status is null
     */
    public UpdateCommand(Index targetIndex, Status status, boolean isForceOperation) {
        super(targetIndex, isForceOperation);
        requireNonNull(status);
        this.status = status;
    }

    @Override
    protected String getNoResultMessage() {
        return MESSAGE_NO_RESULT;
    }

    @Override
    protected String getConfirmationMessage() {
        return MESSAGE_UPDATE_CONFIRMATION;
    }

    @Override
    protected String getIndexConfirmationMessage() {
        return MESSAGE_ID_UPDATE_CONFIRMATION;
    }

    @Override
    protected String getSuccessMessage() {
        return MESSAGE_UPDATE_STATUS_SUCCESS;
    }

    @Override
    protected void processApplicants(Model model, List<Applicant> applicants) throws CommandException {
        requireNonNull(model);
        requireNonNull(applicants);

        for (Applicant applicant : applicants) {
            model.setStatus(applicant, status);
        }
    }

    @Override
    protected void processApplicant(Model model, Applicant applicant) throws CommandException {
        requireNonNull(model);
        requireNonNull(applicant);

        model.setStatus(applicant, status);
    }

    /**
     * Sets the force update flag.
     *
     * @param isForceUpdate Whether to skip confirmation
     */
    public void setForceUpdate(boolean isForceUpdate) {
        setForceOperation(isForceUpdate);
    }

    @Override
    public boolean equals(Object other) {
        if (!super.equals(other)) {
            return false;
        }
        UpdateCommand otherCommand = (UpdateCommand) other;
        return status.equals(otherCommand.status);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicates", predicates)
                .add("targetIndex", targetIndex)
                .add("status", status)
                .add("isForceOperation", isForceOperation)
                .toString();
    }
}
