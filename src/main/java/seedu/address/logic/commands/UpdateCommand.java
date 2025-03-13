package seedu.address.logic.commands;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.IdentifierPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.Status;

public class UpdateCommand extends Command {
    public static final String COMMAND_WORD = "update";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sets the application status of the person "
            + "identified by the specified contact identifier (name, email, or phone).\n"
            + "Parameters: IDENTIFIER_TYPE/KEYWORD [--custom] STATUS\n"
            + "Example: " + COMMAND_WORD + " n/Alex Yeoh";

    private static final String MESSAGE_UPDATE_STATUS_SUCCESS = "Updated status of: %1$s";

    public static final String MESSAGE_NO_MATCHES = "No person matches provided keyword!";
    public static final String MESSAGE_MULTIPLE_MATCHES = "%1$d persons matched keyword. Please be more specific!";

    private final IdentifierPredicate predicate;
    private final Status status;

    public UpdateCommand(IdentifierPredicate predicate, Status status) {
        this.predicate = predicate;
        this.status = status;
    }

    @Override
    public CommandResult execute(Model model) {
        model.updateFilteredPersonList(predicate);
        int numberOfMatches = model.getFilteredPersonListSize();
        if (numberOfMatches == 0) {
            return new CommandResult(String.format(MESSAGE_NO_MATCHES));
        } else if (numberOfMatches > 1 ) {
            return new CommandResult(String.format(MESSAGE_MULTIPLE_MATCHES, numberOfMatches));
        }
        Person target = model.getFilteredPersonList().get(0);
        Person updatedPerson = model.setStatus(target, status);
        return new CommandResult(String.format(MESSAGE_UPDATE_STATUS_SUCCESS, Messages.format(updatedPerson)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UpdateCommand)) {
            return false;
        }

        UpdateCommand otherUpdateCommand = (UpdateCommand) other;
        return predicate.equals(otherUpdateCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .add("status", status)
                .toString();
    }
}
