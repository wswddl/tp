package seedu.address.logic.commands;

import java.util.List;
import static java.util.Objects.requireNonNull;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class SearchCommand extends Command {

    public static final String COMMAND_WORD = "search";

    private static final Set<String> CRITERIA = Set.of(
            CliSyntax.PREFIX_NAME.getPrefix(),
            CliSyntax.PREFIX_EMAIL.getPrefix(),
            CliSyntax.PREFIX_ID.getPrefix(),
            CliSyntax.PREFIX_JOB_POSITION.getPrefix(),
            CliSyntax.PREFIX_STATUS.getPrefix()
    );

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Searches for applicants based on [CRITERIA] and [VALUE].\n"
            + "Parameters:\n"
            + CliSyntax.PREFIX_NAME + ": Applicant's name    "
            + CliSyntax.PREFIX_EMAIL + ": Applicant's email address    "
            + CliSyntax.PREFIX_ID + ": System's assigned ID    "
            + CliSyntax.PREFIX_JOB_POSITION + ": Job position    "
            + CliSyntax.PREFIX_STATUS + ": Hiring stage\n"
            + "Example: " + COMMAND_WORD + " " + CliSyntax.PREFIX_NAME + " John Doe\n"
            + "Example: " + COMMAND_WORD + " " + CliSyntax.PREFIX_EMAIL + " john@example.com\n";

    public static final String MESSAGE_NO_RESULT = "Error: No applicants found.";
    public static final String MESSAGE_INVALID_CRITERIA = "Error: Invalid search CRITERIA";
    public static final String MESSAGE_MISSING_VALUE = "Error: Missing VALUE for search";

    private final NameContainsKeywordsPredicate predicate;

    public SearchCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    private void validateKeywords(List<String> keywords) throws CommandException {
        Set<String> invalidKeywords = IntStream.range(0, keywords.size())
                .filter(i -> i % 2 == 0)
                .mapToObj(keywords::get)
                .filter(keyword -> !CRITERIA.contains(keyword))
                .collect(Collectors.toSet());

        if (!invalidKeywords.isEmpty()) {
            throw new CommandException(MESSAGE_INVALID_CRITERIA);
        }

        Set<String> missingValues = IntStream.range(0, keywords.size())
                .filter(i -> i % 2 != 0)
                .mapToObj(keywords::get)
                .filter(value -> CRITERIA.contains(value) || value == null)
                .collect(Collectors.toSet());

        if (!missingValues.isEmpty()) {
            throw new CommandException(MESSAGE_MISSING_VALUE);
        }
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        validateKeywords(predicate.getKeywords());

        model.updateFilteredPersonList(predicate);

        int count = model.getFilteredPersonList().size();
        if (count == 0) {
            throw new CommandException(MESSAGE_NO_RESULT);
        }

        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, count)
        );
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof SearchCommand)) {
            return false;
        }

        SearchCommand otherSearchCommand = (SearchCommand) other;
        return predicate.equals(otherSearchCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
