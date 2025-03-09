package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.IdentifierPredicate;
import seedu.address.model.person.Person;

/**
 * Deletes a person identified using the specified contact identifier from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by the contact identifier provided.\n"
            + "Parameters: "
            + "n/NAME | p/PHONE | e/EMAIL | id/ID [--force]\n"
            + "Example: " + COMMAND_WORD + " n/John Doe";

    static final String MESSAGE_CONFIRMATION_REQUIRED_MULTIPLE_PERSONS =
            "Are you sure you want to delete all the persons?";
    static final String MESSAGE_CONFIRMATION_REQUIRED_SINGLE_PERSON = "Are you sure you want to delete this person?";
    static final String MESSAGE_NO_MATCHING_PERSON = "No matching person found.";

    static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";
    static final String MESSAGE_DELETION_CANCELED = "Deletion canceled.";

    private final IdentifierPredicate predicate;
    private final boolean isForceDelete;
    private final Index targetIndex;

    /**
     * @param predicate     The predicate used to filter the person(s) to be deleted.
     * @param isForceDelete A flag indicating whether the deletion should proceed without confirmation.
     */
    public DeleteCommand(IdentifierPredicate predicate, boolean isForceDelete) {
        this.predicate = predicate;
        this.isForceDelete = isForceDelete;
        this.targetIndex = null;
    }

    /**
     * @param targetIndex   The index used to filter the person to be deleted.
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

    /**
     * Deletes a person by index.
     */
    private CommandResult deleteByIndex(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToDelete = lastShownList.get(targetIndex.getZeroBased());

        if (!isForceDelete) {
            // Show confirmation dialog
            boolean isConfirmed = showConfirmationDialog("Confirm Deletion",
                    MESSAGE_CONFIRMATION_REQUIRED_SINGLE_PERSON);

            if (isConfirmed) {
                model.deletePerson(personToDelete);
                return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(personToDelete)));
            } else {
                return new CommandResult(MESSAGE_DELETION_CANCELED);
            }
        }
        model.deletePerson(personToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(personToDelete)));
    }

    /**
     * Deletes a person by predicate.
     */
    private CommandResult deleteByPredicate(Model model) throws CommandException {
        model.updateFilteredPersonList(predicate);
        List<Person> filteredList = model.getFilteredPersonList();

        if (filteredList.isEmpty()) {
            throw new CommandException(MESSAGE_NO_MATCHING_PERSON);
        }

        if (!isForceDelete && filteredList.size() > 1) {
            // Show confirmation dialog for multiple persons
            boolean isConfirmed = showConfirmationDialog("Confirm Deletion",
                    MESSAGE_CONFIRMATION_REQUIRED_MULTIPLE_PERSONS);

            if (isConfirmed) {
                for (Person person : filteredList) {
                    model.deletePerson(person);
                }
                String deletedNames = filteredList.stream()
                        .map(Messages::format)
                        .collect(Collectors.joining(", "));
                return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, deletedNames));
            } else {
                return new CommandResult(MESSAGE_DELETION_CANCELED);
            }
        }

        if (!isForceDelete && filteredList.size() == 1) {
            // Show confirmation dialog for a single person
            Person personToDelete = filteredList.get(0);
            boolean isConfirmed = showConfirmationDialog("Confirm Deletion",
                    MESSAGE_CONFIRMATION_REQUIRED_SINGLE_PERSON);

            if (isConfirmed) {
                model.deletePerson(personToDelete);
                return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(personToDelete)));
            } else {
                return new CommandResult(MESSAGE_DELETION_CANCELED);
            }
        }

        // Force delete without confirmation
        for (Person person : filteredList) {
            model.deletePerson(person);
        }

        String deletedNames = filteredList.stream()
                .map(Messages::format)
                .collect(Collectors.joining(", "));

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

    /**
     * Shows a confirmation dialog and returns the user's response.
     *
     * @param title   The title of the dialog.
     * @param message The message to display.
     * @return True if the user confirms, false if the user cancels.
     */
    private boolean showConfirmationDialog(String title, String message) {
        // Ensure the dialog is shown on the JavaFX Application Thread
        if (Platform.isFxApplicationThread()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);

            Optional<ButtonType> result = alert.showAndWait();
            return result.isPresent() && result.get() == ButtonType.OK;
        } else {
            // If not on the JavaFX Application Thread, use Platform.runLater
            final boolean[] isConfirmed = {false};
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle(title);
                alert.setHeaderText(null);
                alert.setContentText(message);

                Optional<ButtonType> result = alert.showAndWait();
                isConfirmed[0] = result.isPresent() && result.get() == ButtonType.OK;
            });
            return isConfirmed[0];
        }
    }
}
