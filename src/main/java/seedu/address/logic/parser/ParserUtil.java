package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_FLAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AFTER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BEFORE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_JOB_POSITION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

import javafx.util.Pair;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.applicant.Address;
import seedu.address.model.applicant.AfterDatePredicate;
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
import seedu.address.model.applicant.Rating;
import seedu.address.model.applicant.Status;
import seedu.address.model.applicant.StatusMatchesPredicate;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    public static final int MAX_INPUT_LENGTH = 50;
    public static final String MESSAGE_INVALID_DATE_FORMAT = "Invalid date format. Please use YYYY-MM-DD.";

    /**
     * The order in which prefixes are parsed. This determines the order of
     * predicates in the final command.
     */
    public static final Prefix[] COMMON_PREFIXES = {
        PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_JOB_POSITION, PREFIX_STATUS, PREFIX_BEFORE, PREFIX_AFTER
    };

    public static final Prefix[] COMMON_PREFIXES_WITH_ID =
            Stream.concat(Arrays.stream(COMMON_PREFIXES), Stream.of(PREFIX_ID))
                    .toArray(Prefix[]::new);
    /**
     * Mapping of prefixes to their respective predicate constructors.
     */
    public static final Map<Prefix, Function<String, IdentifierPredicate>> PREFIX_MAPPING = Map.of(
            PREFIX_NAME, NameMatchesKeywordPredicate::new,
            PREFIX_PHONE, PhoneMatchesKeywordPredicate::new,
            PREFIX_EMAIL, EmailMatchesKeywordPredicate::new,
            PREFIX_JOB_POSITION, JobPositionMatchesPredicate::new,
            PREFIX_STATUS, StatusMatchesPredicate::new
    );

    private static void checkLength(String fieldName, String value) throws ParseException {
        if (value.length() > MAX_INPUT_LENGTH) {
            throw new ParseException(fieldName + " cannot exceed " + MAX_INPUT_LENGTH + " characters.");
        }
    }

    /**
     * Extracts predicates from the given {@code ArgumentMultimap}.
     *
     * @param argMultimap The parsed argument multimap.
     * @return A list of identifier predicates for filtering applicants.
     */
    public static List<IdentifierPredicate> extractPredicates(ArgumentMultimap argMultimap) throws ParseException {
        List<IdentifierPredicate> predicates = new ArrayList<>();

        // First validate all inputs before creating any predicates
        for (Prefix prefix : PREFIX_MAPPING.keySet()) {
            if (argMultimap.getValue(prefix).isPresent()) {
                String value = argMultimap.getValue(prefix).get();
                if (prefix.equals(PREFIX_NAME)) {
                    ParserUtil.parseName(value);
                } else if (prefix.equals(PREFIX_PHONE)) {
                    ParserUtil.parsePhone(value);
                } else if (prefix.equals(PREFIX_EMAIL)) {
                    ParserUtil.parseEmail(value);
                } else if (prefix.equals(PREFIX_JOB_POSITION)) {
                    ParserUtil.parseJobPosition(value);
                } else if (prefix.equals(PREFIX_STATUS)) {
                    ParserUtil.parseStatus(value);
                }
            }
        }

        // If all validations passed, create the predicates
        PREFIX_MAPPING.forEach((prefix, predicateConstructor) ->
                argMultimap.getValue(prefix).ifPresent(value -> predicates.add(predicateConstructor.apply(value)))
        );
        extractPredicateFromDates(argMultimap, predicates);
        return predicates;
    }

    /**
     * Extracts date-related predicates from the argument multimap.
     *
     * @param argMultimap The parsed argument multimap.
     * @param predicates  The list of predicates to which extracted predicates will be added.
     * @throws ParseException if date parsing fails.
     */
    static void extractPredicateFromDates(ArgumentMultimap argMultimap, List<IdentifierPredicate> predicates)
            throws ParseException {
        IdentifierPredicate predicate;
        if (argMultimap.getValue(PREFIX_BEFORE).isPresent()) {
            String beforeDateString = argMultimap.getValue(PREFIX_BEFORE).get();
            LocalDateTime validBeforeDate = ParserUtil.parseBeforeDate(beforeDateString);
            predicate = new BeforeDatePredicate(validBeforeDate);
            predicates.add(predicate);
        }
        if (argMultimap.getValue(PREFIX_AFTER).isPresent()) {
            String afterDateString = argMultimap.getValue(PREFIX_AFTER).get();
            LocalDateTime validAfterDate = ParserUtil.parseAfterDate(afterDateString);
            predicate = new AfterDatePredicate(validAfterDate);
            predicates.add(predicate);
        }
    }

    /**
     * Checks for the presence of a flag in the argument string.
     *
     * @param args The input argument string.
     * @return A pair containing the cleaned argument string and a boolean indicating
     *      whether the --force flag was present.
     * @throws ParseException if an unknown flag is encountered.
     */
    public static Pair<String, Boolean> checkFlag(String args) throws ParseException {
        // Check for --force flag
        boolean isForceOperation = args.contains("--force");
        args = args.replace("--force", ""); // Remove --force from args

        // Flags other than --force that start with "--" are invalid
        if (args.matches(".*\\s--\\w+.*")) {
            String unknownFlag = args.trim().replaceAll(".*(--\\w+).*", "$1");
            throw new ParseException(String.format(MESSAGE_UNKNOWN_FLAG, unknownFlag));
        }

        return new Pair<>(args, isForceOperation);
    }

    /**
     * Counts the number of prefixes that have values in the given {@code ArgumentMultimap}.
     * i.e. number of prefixes provided in the argument.
     */
    public static int numOfPrefixesPresent(ArgumentMultimap argMultimap, Prefix... prefixes) {
        return (int) Stream.of(prefixes).filter(prefix -> argMultimap.getValue(prefix).isPresent()).count();
    }

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        checkLength("Name", trimmedName);
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        checkLength("Phone", trimmedPhone);
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        checkLength("Address", trimmedAddress);
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        checkLength("Email", trimmedEmail);
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String jobPosition} into a {@code JobPosition}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code jobPosition} is invalid.
     */
    public static JobPosition parseJobPosition(String jobPosition) throws ParseException {
        requireNonNull(jobPosition);
        String trimmedJobPosition = jobPosition.trim();
        if (!JobPosition.isValidJobPosition(trimmedJobPosition)) {
            throw new ParseException(JobPosition.MESSAGE_CONSTRAINTS);
        }
        checkLength("Job Position", trimmedJobPosition);
        return new JobPosition(trimmedJobPosition);
    }

    /**
     * Parses a {@code String status} into a {@code Status}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code status} is invalid.
     */
    public static Status parseStatus(String status) throws ParseException {
        requireNonNull(status);
        String trimmedStatus = status.trim();
        if (!Status.isValidStatus(trimmedStatus)) {
            throw new ParseException(Status.MESSAGE_CONSTRAINTS);
        }
        checkLength("Status", trimmedStatus);
        return new Status(trimmedStatus);
    }

    /**
     * Parses a {@code String beforeDate} into a {@code LocalDate}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code beforeDate} is invalid.
     */
    public static LocalDateTime parseBeforeDate(String beforeDate) throws ParseException {
        return parseAfterDate(beforeDate);
    }

    /**
     * Parses a {@code String afterDate} into a {@code LocalDate}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code afterDate} is invalid.
     */
    public static LocalDateTime parseAfterDate(String afterDate) throws ParseException {
        requireNonNull(afterDate);
        String trimmedDate = afterDate.trim();
        checkLength("Date", trimmedDate);
        try {
            return LocalDate.parse(trimmedDate, DateTimeFormatter.ISO_LOCAL_DATE).atStartOfDay();
        } catch (DateTimeParseException e) {
            throw new ParseException(MESSAGE_INVALID_DATE_FORMAT);
        }
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        checkLength("Tag", trimmedTag);
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses a {@code String rating} into a {@code Rating}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code rating} is invalid.
     */
    public static Rating parseRating(String rating) throws ParseException {
        requireNonNull(rating);
        String trimmedRating = rating.trim();
        if (!Rating.isValidRating(trimmedRating)) {
            throw new ParseException(Rating.MESSAGE_CONSTRAINTS);
        }
        checkLength("Rating", trimmedRating);
        return new Rating(trimmedRating);
    }
}
