package seedu.address.testutil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.applicant.Applicant;

import static seedu.address.logic.commands.CommandTestUtil.*;

/**
 * A utility class containing a list of {@code Applicant} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Applicant ALICE = new PersonBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("94351253")
            .withTags("friends")
            .withJobPosition("Frontend Engineer")
            .withStatus("Resume Screening")
            .withAddedTime(LocalDateTime.of(2025, 3, 13, 00, 00, 00))
            .build();
    public static final Applicant BENSON = new PersonBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withPhone("98765432")
            .withTags("owesMoney", "friends")
            .withJobPosition("Senior Frontend Engineer")
            .withStatus("Technical Interview Round 1")
            .withAddedTime(LocalDateTime.of(2025, 3, 13, 1, 0, 0))
            .build();
    public static final Applicant CARL = new PersonBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withAddress("wall street")
            .withAddedTime(LocalDateTime.of(2025, 3, 13, 2, 0, 0))
            .build();
    public static final Applicant DANIEL = new PersonBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withAddress("10th street").withTags("friends")
            .withJobPosition("Systems Developer")
            .withAddedTime(LocalDateTime.of(2025, 3, 13, 3, 0, 0))
            .build();
    public static final Applicant ELLE = new PersonBuilder().withName("Elle Meyer").withPhone("9482224")
            .withEmail("werner@example.com").withAddress("michegan ave")
            .withStatus("Offer Given")
            .withAddedTime(LocalDateTime.of(2025, 3, 13, 4, 0, 0))
            .build();
    public static final Applicant FIONA = new PersonBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withEmail("lydia@example.com").withAddress("little tokyo")
            .withJobPosition("UI UX")
            .withStatus("Signed")
            .withAddedTime(LocalDateTime.of(2025, 3, 13, 5, 0, 0))
            .build();
    public static final Applicant GEORGE = new PersonBuilder().withName("George Best").withPhone("9482442")
            .withEmail("anna@example.com").withAddress("4th street")
            .withAddedTime(LocalDateTime.of(2025, 3, 13, 6, 0, 0))
            .build();


    // Manually added
    public static final Applicant HOON = new PersonBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmail("stefan@example.com").withAddress("little india")
            .withAddedTime(LocalDateTime.of(2025, 3, 13, 7, 0, 0))
            .build();
    public static final Applicant IDA = new PersonBuilder().withName("Ida Mueller").withPhone("8482131")
            .withEmail("hans@example.com").withAddress("chicago ave")
            .withAddedTime(LocalDateTime.of(2025, 3, 13, 8, 0, 0))
            .build();

    // Manually added - Applicant's details found in {@code CommandTestUtil}
    public static final Applicant AMY = new PersonBuilder()
            .withName(VALID_NAME_AMY)
            .withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY)
            .withJobPosition(VALID_JOB_POSITION_AMY)
            .withStatus(VALID_STATUS_AMY)
            .withAddress(VALID_ADDRESS_AMY)
            .withTags(VALID_TAG_FRIEND)
            .build();
    public static final Applicant BOB = new PersonBuilder()
            .withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB)
            .withJobPosition(VALID_JOB_POSITION_BOB)
            .withStatus(VALID_STATUS_BOB)
            .withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Applicant applicant : getTypicalPersons()) {
            ab.addPerson(applicant);
        }
        return ab;
    }

    public static List<Applicant> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
