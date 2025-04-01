package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.*;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.applicant.Applicant;
import seedu.address.model.applicant.IdentifierPredicate;
import seedu.address.model.Model;
import seedu.address.model.applicant.JobPosition;
import seedu.address.model.applicant.Status;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SummaryCommand extends Command {
    public static final String COMMAND_WORD = "summary";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Summarizes the applicants based on a given parameter "
            + "Parameters: [" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_JOB_POSITION + "JOB_POSITION] "
            + "[" + PREFIX_STATUS + "STATUS]\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_JOB_POSITION + "Senior SWE";

    public static final String MESSAGE_SUCCESS = "Summarized %1$d / %2$d Applicants \n%3$s";

    private final List<IdentifierPredicate> predicates;
    /**
     * Creates a SummaryCommand to summarise the applicants based on the given identifier
     *
     * @param predicates A list of predicates to filter the applicants for summary.
     */
    public SummaryCommand(List<IdentifierPredicate> predicates) {
        // Note: Predicate can be null i.e summarise all candidates
        this.predicates  = predicates;
    }

    /**
     * Executes the summary command, optionally filtering the applicant list,
     * and producing statistics grouped by job position and application status.
     *
     * @param model The application's model.
     * @return A {@code CommandResult} with formatted summary data.
     * @throws CommandException If the command cannot be executed.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        ObservableList<Applicant> applicants = model.getAddressBook().getPersonList();

        // Combine all predicates using logical AND (all conditions must be met)
        ObservableList<Applicant> filteredList =
                applicants.filtered(person -> predicates.stream().allMatch(p -> p.test(person)));

        // Count how many Applicants per JobPosition
        Map<JobPosition, Long> jobPositionCountMap = filteredList.stream()
                .collect(Collectors.groupingBy(Applicant::getJobPosition, Collectors.counting()));

        // Count how many Applicants per Status
        Map<Status, Long> statusCountMap = filteredList.stream()
                .collect(Collectors.groupingBy(Applicant::getStatus, Collectors.counting()));

        // Get strings:
        String jobPositionStats = jobPositionCountMap.entrySet()
                .stream()
                .map(entry -> entry.getKey().toString() + ": " + entry.getValue())
                .collect(Collectors.joining(", "));

        String statusStats = statusCountMap.entrySet()
                .stream()
                .map(entry -> entry.getKey().toString() + ": " + entry.getValue())
                .collect(Collectors.joining(", "));

        String statisticsString = "Job Positions -> \n["
                + jobPositionStats
                + "] \nStatuses -> \n["
                + statusStats + "]";

        return new CommandResult(String.format(
                String.format(MESSAGE_SUCCESS, filteredList.size(), applicants.size(), statisticsString)));
    }

}
