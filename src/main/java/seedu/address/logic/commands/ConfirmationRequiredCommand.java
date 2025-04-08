package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.applicant.Applicant;
import seedu.address.model.applicant.IdentifierPredicate;

/**
 * An abstract base class for commands that require user confirmation before execution.
 * <p>
 * Provides common functionality for commands that need to:
 * <ul>
 *   <li>Filter applicants using predicates or index</li>
 *   <li>Handle confirmation prompts</li>
 *   <li>Support force operation flags</li>
 *   <li>Process applicants in bulk or individually</li>
 * </ul>
 *
 * Concrete subclasses must implement the specific processing logic and messages.
 */
public abstract class ConfirmationRequiredCommand extends Command {

    /** The predicates used to filter applicants, or null if using index */
    protected final List<IdentifierPredicate> predicates;

    /** The index of the target applicant, or null if using predicates */
    protected final Index targetIndex;

    /** Flag indicating whether to skip confirmation prompts */
    protected boolean isForceOperation;

    /**
     * Constructs a ConfirmationRequiredCommand that operates on applicants matching the given predicates.
     *
     * @param predicates The list of predicates to filter applicants, must not be null
     * @param isForceOperation Whether to skip confirmation prompts
     * @throws NullPointerException if predicates is null
     */
    protected ConfirmationRequiredCommand(List<IdentifierPredicate> predicates, boolean isForceOperation) {
        requireNonNull(predicates);
        this.predicates = predicates;
        this.targetIndex = null;
        this.isForceOperation = isForceOperation;
    }

    /**
     * Constructs a ConfirmationRequiredCommand that operates on the applicant at the given index.
     *
     * @param targetIndex The index of the applicant to process, must not be null
     * @param isForceOperation Whether to skip confirmation prompts
     * @throws NullPointerException if targetIndex is null
     */
    protected ConfirmationRequiredCommand(Index targetIndex, boolean isForceOperation) {
        requireNonNull(targetIndex);
        this.predicates = null;
        this.targetIndex = targetIndex;
        this.isForceOperation = isForceOperation;
    }

    /**
     * Executes the command by delegating to either predicate-based or index-based execution.
     *
     * @param model The model to execute the command on, must not be null
     * @return The command result containing either confirmation or success message
     * @throws CommandException If an error occurs during execution
     * @throws NullPointerException if model is null
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        assert (predicates != null && targetIndex == null) || (predicates == null && targetIndex != null)
                : "Command must use either predicates or index, but not both";

        return targetIndex == null ? executeWithPredicates(model) : executeWithIndex(model);
    }

    /**
     * Executes the command using the stored predicates to filter applicants.
     *
     * @param model The model to execute on, must not be null
     * @return The command result containing either confirmation or success message
     * @throws CommandException If no applicants match the predicates
     * @throws NullPointerException if model is null
     */
    protected CommandResult executeWithPredicates(Model model) throws CommandException {
        requireNonNull(model);
        assert predicates != null : "Predicates must not be null in predicate-based execution";

        model.updateFilteredPersonList(applicant ->
                predicates.stream().allMatch(predicate -> predicate.test(applicant)));

        List<Applicant> filteredList = model.getFilteredPersonList();
        if (filteredList.isEmpty()) {
            throw new CommandException(getNoResultMessage());
        }

        if (!isForceOperation) {
            return new CommandResult(getConfirmationMessage());
        }

        List<Applicant> applicantsToProcess = List.copyOf(filteredList);
        processApplicants(model, applicantsToProcess);

        List<Applicant> updatedApplicants = model.getFilteredPersonList();

        String processedApplicants = updatedApplicants.stream()
                .map(Messages::format)
                .collect(Collectors.joining("\n"));

        return new CommandResult(String.format(getSuccessMessage(), processedApplicants));
    }

    /**
     * Executes the command using the stored index to identify the applicant.
     *
     * @param model The model to execute on, must not be null
     * @return The command result containing either confirmation or success message
     * @throws CommandException If the index is invalid
     * @throws NullPointerException if model is null
     */
    protected CommandResult executeWithIndex(Model model) throws CommandException {
        requireNonNull(model);
        assert targetIndex != null : "Target index must not be null in index-based execution";

        List<Applicant> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Applicant applicantToProcess = lastShownList.get(targetIndex.getZeroBased());

        if (!isForceOperation) {
            return new CommandResult(String.format(getIndexConfirmationMessage(), targetIndex.getOneBased()));
        }

        processApplicant(model, applicantToProcess);
        Applicant updatedApplicant = lastShownList.get(targetIndex.getZeroBased());

        return new CommandResult(String.format(getSuccessMessage(), Messages.format(updatedApplicant)));
    }

    // Abstract methods to be implemented by subclasses

    /**
     * Gets the message to display when no applicants match the criteria.
     *
     * @return The no result message
     */
    protected abstract String getNoResultMessage();

    /**
     * Gets the confirmation message for predicate-based operations.
     *
     * @return The confirmation message
     */
    protected abstract String getConfirmationMessage();

    /**
     * Gets the confirmation message for index-based operations.
     *
     * @return The confirmation message with %d placeholder for index
     */
    protected abstract String getIndexConfirmationMessage();

    /**
     * Gets the success message to display after successful execution.
     *
     * @return The success message with %s placeholder for applicant details
     */
    protected abstract String getSuccessMessage();

    /**
     * Processes multiple applicants (predicate-based operations).
     *
     * @param model The model to process against, must not be null
     * @param applicants The applicants to process, must not be null
     * @throws CommandException If processing fails
     * @throws NullPointerException if model or applicants is null
     */
    protected abstract void processApplicants(Model model, List<Applicant> applicants) throws CommandException;

    /**
     * Processes a single applicant (index-based operations).
     *
     * @param model The model to process against, must not be null
     * @param applicant The applicant to process, must not be null
     * @throws CommandException If processing fails
     * @throws NullPointerException if model or applicant is null
     */
    protected abstract void processApplicant(Model model, Applicant applicant) throws CommandException;

    /**
     * Sets the force operation flag.
     *
     * @param isForceOperation Whether to skip confirmation prompts
     */
    public void setForceOperation(boolean isForceOperation) {
        this.isForceOperation = isForceOperation;
    }

    /**
     * Checks equality based on predicates, target index, and force operation flag.
     *
     * @param other The object to compare with
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ConfirmationRequiredCommand otherCommand)) {
            return false;
        }

        return Objects.equals(predicates, otherCommand.predicates)
                && Objects.equals(targetIndex, otherCommand.targetIndex)
                && isForceOperation == otherCommand.isForceOperation;
    }

    /**
     * Returns a string representation including predicates, target index, and force operation status.
     *
     * @return The string representation
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicates", predicates)
                .add("targetIndex", targetIndex)
                .add("isForceOperation", isForceOperation)
                .toString();
    }
}
