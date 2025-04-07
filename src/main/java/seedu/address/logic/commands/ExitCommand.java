package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = "exit";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting Address Book as requested ...";

    /**
     * Executes the exit command.
     * <p>
     * This does not modify the model but signals the application to terminate.
     *
     * @param model The model in which the command operates (not used in this command).
     * @return A {@code CommandResult} that signals the UI to exit the application.
     */
    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, true);
    }

}
