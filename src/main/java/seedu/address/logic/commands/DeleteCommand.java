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
import java.util.Objects;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
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
            + "Example: " + COMMAND_WORD + " " + PREFIX_NAME + "John Doe"
            + PREFIX_PHONE + "98765432" + "--force";

    static final String MESSAGE_CONFIRMATION_REQUIRED =
            "Are you sure you want to delete the following applicant(s)?\n"
                    + "Type 'yes' to continue\n"
                    + "Type anything else to cancel the deletion";
    static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Applicant(s): %1$s";

    private final List<IdentifierPredicate> predicates;
    private boolean isForceDelete;
    private final Index targetIndex;

    /**
     * @param predicates     The predicates used to identify the {@code Applicant} to be deleted.
     * @param targetIndex    The index in the last shown list used to identify the {@code Applicant} to be deleted.
     * @param isForceDelete  The flag to specify whether it is force deletion or not.
     */
    public DeleteCommand(List<IdentifierPredicate> predicates, Index targetIndex, boolean isForceDelete) {
        this.predicates = predicates;
        this.targetIndex = targetIndex;
        this.isForceDelete = isForceDelete;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (targetIndex != null) {
            return deleteByIndex(model);
        } else {
            return deleteByPredicates(model);
        }
    }

    private CommandResult deleteByIndex(Model model) throws CommandException {
        List<Applicant> lastShownList = model.getFilteredPersonList();
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Applicant applicantToDelete = lastShownList.get(targetIndex.getZeroBased());
        if (!isForceDelete) {
            model.updateFilteredPersonList(a -> a.equals(applicantToDelete));
            return new CommandResult(MESSAGE_CONFIRMATION_REQUIRED);
        }

        model.deletePerson(applicantToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(applicantToDelete)));
    }

    private CommandResult deleteByPredicates(Model model) throws CommandException {
        requireNonNull(predicates);

        // Apply filtering predicates
        model.updateFilteredPersonList(applicant ->
                predicates.stream().allMatch(predicate -> predicate.test(applicant)));

        List<Applicant> filteredList = model.getFilteredPersonList();
        if (filteredList.isEmpty()) {
            throw new CommandException(MESSAGE_NO_RESULT);
        }

        if (!isForceDelete) {
            return new CommandResult(MESSAGE_CONFIRMATION_REQUIRED);
        }

        // Create a copy of the list to avoid concurrent modification
        List<Applicant> applicantsToDelete = List.copyOf(filteredList);
        for (Applicant applicant : applicantsToDelete) {
            model.deletePerson(applicant);
        }

        String deletedApplicants = applicantsToDelete.stream()
                .map(Messages::format)
                .collect(Collectors.joining("\n"));

        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, deletedApplicants));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DeleteCommand)) {
            return false;
        }

        DeleteCommand otherCommand = (DeleteCommand) other;
        return Objects.equals(predicates, otherCommand.predicates)
                && Objects.equals(targetIndex, otherCommand.targetIndex)
                && isForceDelete == otherCommand.isForceDelete;
    }

    public void setForceDelete(boolean isForceDelete) {
        this.isForceDelete = isForceDelete;
    }
}
