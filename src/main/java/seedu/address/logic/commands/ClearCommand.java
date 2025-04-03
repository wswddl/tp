package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;

/**
 * Clears all data from the address book.
 * <p>
 * This command resets the address book to an empty state.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";

    /**
     * Executes the clear command by resetting the address book in the model.
     *
     * @param model The model which the command should operate on.
     * @return A {@code CommandResult} indicating that the address book has been cleared.
     */
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.deleteAllProfilePicture();
        model.setAddressBook(new AddressBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
