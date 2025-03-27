package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AFTER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BEFORE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_JOB_POSITION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.applicant.BeforeDatePredicate;
import seedu.address.model.applicant.Email;
import seedu.address.model.applicant.EmailMatchesKeywordPredicate;
import seedu.address.model.applicant.IdentifierPredicate;
import seedu.address.model.applicant.JobPosition;
import seedu.address.model.applicant.JobPositionMatchesPredicate;
import seedu.address.model.applicant.Name;
import seedu.address.model.applicant.NameMatchesKeywordPredicate;
import seedu.address.model.applicant.Phone;
import seedu.address.model.applicant.PhoneMatchesKeywordPredicate;
import seedu.address.model.applicant.Status;
import seedu.address.model.applicant.StatusMatchesPredicate;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        requireNonNull(args);

        // Check for --force flag first
        boolean isForceDelete = args.contains("--force");
        args = args.replace("--force", ""); // Remove --force from args

        // Other flags that start with "--" are invalid
        if (args.trim().matches(".*\\s--\\w+.*")) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                PREFIX_ID, PREFIX_STATUS, PREFIX_JOB_POSITION, PREFIX_BEFORE, PREFIX_AFTER);

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ID,
                PREFIX_STATUS, PREFIX_JOB_POSITION, PREFIX_BEFORE, PREFIX_AFTER);

        int numOfPrefixesPresentOtherThanId = numOfPrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                PREFIX_STATUS, PREFIX_JOB_POSITION, PREFIX_BEFORE, PREFIX_AFTER);
        // Allow ONLY id or any combination of other identifiers
        if ((numOfPrefixesPresentOtherThanId > 0 && argMultimap.getValue(PREFIX_ID).isPresent())
            || (numOfPrefixesPresentOtherThanId == 0 && argMultimap.getValue(PREFIX_ID).isEmpty())) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }

        IdentifierPredicate predicate;
        List<IdentifierPredicate> predicates = new ArrayList<>();

        // If Index is provided, perform delete solely on the index
        if (argMultimap.getValue(PREFIX_ID).isPresent()) {
            Index index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_ID).get());
            return new DeleteCommand(null, index, isForceDelete); // Use index-based constructor
        }
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            String nameString = argMultimap.getValue(PREFIX_NAME).get();
            Name validName = ParserUtil.parseName(nameString);
            predicate = new NameMatchesKeywordPredicate(validName.fullName);
            predicates.add(predicate);
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            String phoneString = argMultimap.getValue(PREFIX_PHONE).get();
            Phone validPhone = ParserUtil.parsePhone(phoneString);
            predicate = new PhoneMatchesKeywordPredicate(validPhone.value);
            predicates.add(predicate);
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            String emailString = argMultimap.getValue(PREFIX_EMAIL).get();
            Email validEmail = ParserUtil.parseEmail(emailString);
            predicate = new EmailMatchesKeywordPredicate(validEmail.value);
            predicates.add(predicate);
        }
        if (argMultimap.getValue(PREFIX_STATUS).isPresent()) {
            String statusString = argMultimap.getValue(PREFIX_STATUS).get();
            Status validStatus = ParserUtil.parseStatus(statusString);
            predicate = new StatusMatchesPredicate(validStatus.value);
            predicates.add(predicate);
        }
        if (argMultimap.getValue(PREFIX_JOB_POSITION).isPresent()) {
            String jobPositionString = argMultimap.getValue(PREFIX_JOB_POSITION).get();
            JobPosition validJobPosition = ParserUtil.parseJobPosition(jobPositionString);
            predicate = new JobPositionMatchesPredicate(validJobPosition.jobPosition);
            predicates.add(predicate);
        }
        if (argMultimap.getValue(PREFIX_BEFORE).isPresent()) {
            System.out.println("hello");
            String beforeDateString = argMultimap.getValue(PREFIX_BEFORE).get();
            LocalDateTime validBeforeDate = ParserUtil.parseBeforeDate(beforeDateString);
            predicate = new BeforeDatePredicate(validBeforeDate.toString());
            predicates.add(predicate);
        }
        if (argMultimap.getValue(PREFIX_AFTER).isPresent()) {
            String afterDateString = argMultimap.getValue(PREFIX_AFTER).get();
            LocalDateTime validAfterDate = ParserUtil.parseAfterDate(afterDateString);
            predicate = new BeforeDatePredicate(validAfterDate.toString());
            predicates.add(predicate);
        }
        return new DeleteCommand(predicates, null, isForceDelete);
    }

    /**
     * Counts the number of prefixes that have values in the given {@code ArgumentMultimap}.
     * i.e. number of prefixes provided in the argument.
     */
    private static int numOfPrefixesPresent(ArgumentMultimap argMultimap, Prefix... prefixes) {
        return (int) Stream.of(prefixes).filter(prefix -> argMultimap.getValue(prefix).isPresent()).count();
    }
}
