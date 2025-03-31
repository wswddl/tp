package seedu.address.model.util;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.applicant.Address;
import seedu.address.model.applicant.Applicant;
import seedu.address.model.applicant.Email;
import seedu.address.model.applicant.JobPosition;
import seedu.address.model.applicant.Name;
import seedu.address.model.applicant.Phone;
import seedu.address.model.applicant.Rating;
import seedu.address.model.applicant.Status;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Applicant[] getSamplePersons() {
        return new Applicant[] {
            new Applicant(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new JobPosition("Front end SWE"), new Status("Online Assessment"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                LocalDateTime.of(2025, 1, 5, 10, 15, 30),
                getTagSet("Recommended"),
                new Rating("-1")),
            new Applicant(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new JobPosition("Backend Senior Engineer"), new Status("Round 1"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                LocalDateTime.of(2025, 2, 20, 18, 45, 0),
                getTagSet("friendly", "SQLExpert"),
                new Rating("5")),
            new Applicant(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new JobPosition("Full stack SWE"), new Status("Resume Screening"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                LocalDateTime.of(2025, 3, 1, 8, 0, 0),
                getTagSet("exGoogle"),
                new Rating("4")),
            new Applicant(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new JobPosition("Systems Engineer"), new Status("Final interview"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                LocalDateTime.of(2025, 3, 10, 23, 59, 59),
                getTagSet("Funny"),
                new Rating("-1")),
            new Applicant(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new JobPosition("UIUX"), new Status("Accepted"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                LocalDateTime.of(2025, 3, 12, 14, 30, 15),
                getTagSet("knowsFigma"),
                new Rating("3")),
            new Applicant(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new JobPosition("Tech Lead"), new Status("Rejected"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                LocalDateTime.of(2025, 3, 13, 15, 45, 11),
                getTagSet("exNetflix"),
                new Rating("-1"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Applicant sampleApplicant : getSamplePersons()) {
            sampleAb.addPerson(sampleApplicant);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
