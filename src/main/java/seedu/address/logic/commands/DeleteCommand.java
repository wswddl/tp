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
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.applicant.Applicant;
import seedu.address.model.applicant.IdentifierPredicate;

/**
 * Deletes applicants identified by index or by matching a set of criteria.
 * <p>
 * Supports both individual deletion by index and bulk deletion using multiple
 * filtering criteria. Includes confirmation prompts unless --force flag is used.
 */
public class DeleteCommand extends ConfirmationRequiredCommand {

    public static final String COMMAND_WORD = "delete";

    /** Message explaining the command usage and parameters */
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the applicant(s) identified by the provided identifier(s).\n"
            + "Parameters: (Use ONLY id or any combination of other identifiers) "
            + "[" + PREFIX_ID + "INDEX] "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_STATUS + "STATUS] "
            + "[" + PREFIX_JOB_POSITION + "JOB_POSITION] "
            + "[" + PREFIX_BEFORE + "YYYY-MM-DD] "
            + "[" + PREFIX_AFTER + "YYYY-MM-DD] "
            + "[--force]\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432" + " --force";

    /** Confirmation message for bulk deletion */
    static final String MESSAGE_DELETE_CONFIRMATION =
            "Are you sure you want to delete the following applicant(s)?\n"
                    + "Type 'yes' to continue\n"
                    + "Type anything else to cancel the deletion";

    /** Success message after deletion */
    static final String MESSAGE_DELETE_SUCCESS = "Deleted Applicant(s): %1$s";

    /** Confirmation message for index-based deletion */
    static final String MESSAGE_ID_DELETE_CONFIRMATION =
            "Are you sure you want to delete applicant %d in the list?\n"
                    + "Type 'yes' to continue\n"
                    + "Type anything else to cancel the deletion";

    /**
     * Creates a DeleteCommand that deletes applicants matching the given predicates.
     *
     * @param predicates The criteria to filter applicants, must not be null
     * @param isForceOperation Whether to skip confirmation
     * @throws NullPointerException if predicates is null
     */
    public DeleteCommand(List<IdentifierPredicate> predicates, boolean isForceOperation) {
        super(predicates, isForceOperation);
    }

    /**
     * Creates a DeleteCommand that deletes the applicant at the given index.
     *
     * @param targetIndex The index of the applicant to delete, must not be null
     * @param isForceOperation Whether to skip confirmation
     * @throws NullPointerException if targetIndex is null
     */
    public DeleteCommand(Index targetIndex, boolean isForceOperation) {
        super(targetIndex, isForceOperation);
    }

    @Override
    protected String getNoResultMessage() {
        return MESSAGE_NO_RESULT;
    }

    @Override
    protected String getConfirmationMessage() {
        return MESSAGE_DELETE_CONFIRMATION;
    }

    @Override
    protected String getIndexConfirmationMessage() {
        return MESSAGE_ID_DELETE_CONFIRMATION;
    }

    @Override
    protected String getSuccessMessage() {
        return MESSAGE_DELETE_SUCCESS;
    }

    @Override
    protected void processApplicants(Model model, List<Applicant> applicants) throws CommandException {
        requireNonNull(model);
        requireNonNull(applicants);

        for (Applicant applicant : applicants) {
            model.deletePerson(applicant);
        }
    }

    @Override
    protected void processApplicant(Model model, Applicant applicant) throws CommandException {
        requireNonNull(model);
        requireNonNull(applicant);

        model.deletePerson(applicant);
    }

    /**
     * Sets the force deletion flag.
     *
     * @param isForceDelete Whether to skip confirmation
     */
    public void setForceDelete(boolean isForceDelete) {
        setForceOperation(isForceDelete);
    }
}
