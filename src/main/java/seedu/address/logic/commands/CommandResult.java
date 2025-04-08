package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /** Help information should be shown to the user. */
    private final boolean showHelp;

    /** The application should exit. */
    private final boolean exit;

    /**
     * Constructs a {@code CommandResult} with the specified feedback message,
     * help indicator, and exit flag.
     *
     * @param feedbackToUser The message to display to the user.
     * @param showHelp Whether help should be displayed.
     * @param exit Whether the application should exit.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
    }

    /**
     * Constructs a {@code CommandResult} with only a feedback message.
     * Help and exit flags are set to {@code false}.
     *
     * @param feedbackToUser The message to display to the user.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false);
    }

    /**
     * Returns the feedback message to be shown to the user.
     */
    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    /**
     * Returns true if help information should be shown to the user.
     */
    public boolean isShowHelp() {
        return showHelp;
    }

    /**
     * Returns true if the application should exit.
     */
    public boolean isExit() {
        return exit;
    }

    /**
     * Returns true if this result represents a confirmation request.
     * <p>
     * Currently used specifically for deletion confirmation.
     */
    public boolean isConfirmation() {
        return feedbackToUser.matches("(?s)^Are you sure you want to.*");
    }

    /**
     * Returns true if this {@code CommandResult} is equal to another object.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CommandResult)) {
            return false;
        }

        CommandResult otherCommandResult = (CommandResult) other;
        return feedbackToUser.equals(otherCommandResult.feedbackToUser)
                && showHelp == otherCommandResult.showHelp
                && exit == otherCommandResult.exit;
    }

    /**
     * Returns the hash code for this {@code CommandResult}.
     */
    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, exit);
    }

    /**
     * Returns a string representation of the {@code CommandResult} for debugging.
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("feedbackToUser", feedbackToUser)
                .add("showHelp", showHelp)
                .add("exit", exit)
                .toString();
    }

}
