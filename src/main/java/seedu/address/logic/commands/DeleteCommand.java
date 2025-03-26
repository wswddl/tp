package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
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

/**
 * Deletes an applicant identified using the specified contact identifier from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the applicant identified by the contact identifier provided.\n"
            + "Parameters: "
            + PREFIX_NAME + "NAME | "
            + PREFIX_PHONE + "PHONE | "
            + PREFIX_EMAIL + "EMAIL | "
            + PREFIX_ID + "ID | "
            + PREFIX_STATUS + "STATUS | "
            + PREFIX_JOB_POSITION + "JOB_POSITION | "
            + "[--force]\n"
            + "Example: " + COMMAND_WORD + " n/John Doe --force";

    static final String MESSAGE_CONFIRMATION_REQUIRED_MULTIPLE_PERSONS =
            "Are you sure you want to delete all the applicants listed below?\n" + "Type 'yes' to continue\n"
                    + "Type anything else to cancel the deletion";
    static final String MESSAGE_CONFIRMATION_REQUIRED_SINGLE_PERSON =
            "Are you sure you want to delete this applicant?\n"
            + "Type 'yes' to continue\n"
            + "Type anything else to cancel the deletion";
    static final String MESSAGE_NO_MATCHING_PERSON = "No matching applicant found.";
    static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Applicant: %1$s";

    private final IdentifierPredicate predicate;
    private boolean isForceDelete;
    private final Index targetIndex;

    /**
     * @param predicate     The predicate used to filter the applicant(s) to be deleted.
     * @param isForceDelete A flag indicating whether the deletion should proceed without confirmation.
     */
    public DeleteCommand(IdentifierPredicate predicate, boolean isForceDelete) {
        this.predicate = predicate;
        this.isForceDelete = isForceDelete;
        this.targetIndex = null;
    }

    /**
     * @param targetIndex   The index used to filter the applicant to be deleted.
     * @param isForceDelete A flag indicating whether the deletion should proceed without confirmation.
     */
    public DeleteCommand(Index targetIndex, boolean isForceDelete) {
        this.targetIndex = targetIndex;
        this.isForceDelete = isForceDelete;
        this.predicate = null;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (targetIndex != null) {
            // Handle index-based deletion
            return deleteByIndex(model);
        } else {
            // Handle predicate-based deletion
            return deleteByPredicate(model);
        }
    }

    public void setForceDelete(boolean isForceDelete) {
        this.isForceDelete = isForceDelete;
    }

    /**
     * Deletes a applicant by index.
     */
    private CommandResult deleteByIndex(Model model) throws CommandException {
        List<Applicant> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Applicant applicantToDelete = lastShownList.get(targetIndex.getZeroBased());
        if (!isForceDelete) {
            return new CommandResult(String.format(MESSAGE_CONFIRMATION_REQUIRED_SINGLE_PERSON));
        }

        // Force delete without confirmation
        model.deletePerson(applicantToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(applicantToDelete)));
    }

    /**
     * Deletes a applicant by predicate.
     */
    private CommandResult deleteByPredicate(Model model) throws CommandException {
        model.updateFilteredPersonList(predicate);
        List<Applicant> filteredList = model.getFilteredPersonList();

        if (filteredList.isEmpty()) {
            throw new CommandException(MESSAGE_NO_MATCHING_PERSON);
        }

        if (!isForceDelete) {
            if (filteredList.size() > 1) {
                return new CommandResult(String.format(MESSAGE_CONFIRMATION_REQUIRED_MULTIPLE_PERSONS));
            } else {
                return new CommandResult(String.format(MESSAGE_CONFIRMATION_REQUIRED_SINGLE_PERSON));
            }
        }

        // Force delete without confirmation
        List<Applicant> personsToDelete = List.copyOf(filteredList);

        for (Applicant applicant : personsToDelete) {
            model.deletePerson(applicant);
        }

        String deletedNames = personsToDelete.stream()
                .map(person -> Messages.format(person) + "\n")
                .collect(Collectors.joining());

        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, deletedNames));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DeleteCommand)) {
            return false;
        }

        DeleteCommand otherDeleteCommand = (DeleteCommand) other;
        if (targetIndex != null) {
            return targetIndex.equals(otherDeleteCommand.targetIndex)
                    && isForceDelete == otherDeleteCommand.isForceDelete;
        } else {
            return predicate.equals(otherDeleteCommand.predicate)
                    && isForceDelete == otherDeleteCommand.isForceDelete;
        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .add("targetIndex", targetIndex)
                .add("isForceDelete", isForceDelete)
                .toString();
    }
}
