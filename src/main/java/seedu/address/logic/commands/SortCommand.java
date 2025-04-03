package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_CRITERIA_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.Model;

/**
 * Sort the applicant list in the address book.
 */
public class SortCommand extends Command {
    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sort the applicants based on one specified criterion and an optional sorting order.\n"
            + "Criteria: "
            + "[" + PREFIX_NAME + "]: Sort by name. "
            + "[" + PREFIX_EMAIL + "]: Sort by email address. "
            + "[" + PREFIX_ADDED_TIME + "]: Sort by added time. "
            + "[" + PREFIX_JOB_POSITION + "]: Sort by job position. "
            + "[" + PREFIX_STATUS + "]: Sort by application status. \n"
            + "Order (ascending order by default): "
            + "[" + PREFIX_ASCENDING + "] OR [" + PREFIX_DESCENDING + "]: Sort in ascending OR descending order.\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_NAME + "\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_EMAIL + " " + PREFIX_ASCENDING + "\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_ADDED_TIME + " " + PREFIX_DESCENDING;

    public static final String MESSAGE_SUCCESS = "Applicant list has been sorted successfully based on %1$s in %2$s";
    private final Prefix prefix;
    private final String criteria;
    private final boolean isAscendingOrder;
    private final String order;



    /**
     * Creates an SortCommand to add the specified {@code Prefix}
     */
    public SortCommand(Prefix prefix, boolean isAscendingOrder) throws CommandException {
        requireNonNull(prefix);
        requireNonNull(isAscendingOrder);

        this.isAscendingOrder = isAscendingOrder;
        if (isAscendingOrder) {
            this.order = "ascending order";
        } else {
            this.order = "descending order";
        }

        this.prefix = prefix;
        if (prefix.equals(PREFIX_NAME)) {
            this.criteria = "Name";
        } else if (prefix.equals(PREFIX_EMAIL)) {
            this.criteria = "Email Address";
        } else if (prefix.equals(PREFIX_ADDED_TIME)) {
            this.criteria = "Added Time";
        } else if (prefix.equals(PREFIX_JOB_POSITION)) {
            this.criteria = "Job Position";
        } else if (prefix.equals(PREFIX_STATUS)) {
            this.criteria = "Application Status";
        } else {
            throw new CommandException(String.format(
                    MESSAGE_INVALID_CRITERIA_FORMAT, "sorting", SortCommand.MESSAGE_USAGE));
        }

    }

    /**
     * Returns a string describing the chosen sorting criteria.
     */
    public String getCriteria() {
        return criteria;
    }

    /**
     * Executes the sort command and updates the model's filtered list order.
     *
     * @param model The application's model.
     * @return A {@code CommandResult} indicating successful sorting.
     */
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        model.sortPersons(this.prefix, isAscendingOrder);

        return new CommandResult(String.format(MESSAGE_SUCCESS, this.criteria, this.order));
    }

    /**
     * Returns true if both SortCommands sort by the same prefix.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SortCommand)) {
            return false;
        }

        SortCommand otherSortCommand = (SortCommand) other;
        return prefix.equals(otherSortCommand.prefix) && this.isAscendingOrder == otherSortCommand.isAscendingOrder;
    }

    /**
     * Returns a string representation of this command for debugging.
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("prefix", prefix)
                .add("sortingOrder", isAscendingOrder)
                .toString();
    }
}

