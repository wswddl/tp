package seedu.address.logic.commands;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.Model;


import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_CRITERIA_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;

public class SortCommand extends Command {
    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sort the applicants based on [CRITERIA].\n"
            + "Accepted [CRITERIA]:   "
            + PREFIX_NAME + ": Sort by name.   "
            + PREFIX_EMAIL + ": Sort by email address.   "
            + PREFIX_ADDED_TIME + ": Sort by added time.   "
            + PREFIX_JOB_POSITION + ": Sort by job position.   "
            + PREFIX_STATUS + ": Sort by application status.\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_NAME + "\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_EMAIL;

    public static final String MESSAGE_SUCCESS = "Applicant list has been sorted successfully based on %1$s";

    private final Prefix prefix;
    private final String criteria;



    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public SortCommand(Prefix prefix) throws CommandException {
        requireNonNull(prefix);
        this.prefix = prefix;
        switch (prefix.getPrefix()) {
        case "n/":
            this.criteria = "Name";
            break;

        case "e/":
            this.criteria = "Email Address";
            break;

        case "time/":
            this.criteria = "Added Time";
            break;

        case "j/":
            this.criteria = "Job Position";
            break;

        case "s/":
            this.criteria = "Application Status";
            break;

        default:
            throw new CommandException(String.format(
                    MESSAGE_INVALID_CRITERIA_FORMAT, "sorting", SortCommand.MESSAGE_USAGE));
        }

    }

    public String getCriteria() {
        return criteria;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        model.sortPersons(this.prefix);

        return new CommandResult(String.format(MESSAGE_SUCCESS, this.criteria));
    }

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
        return prefix.equals(otherSortCommand.prefix);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("prefix", prefix)
                .toString();
    }
}

