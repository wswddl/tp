package seedu.address.model.applicant;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.time.LocalDateTime;
import java.util.*;

import org.junit.jupiter.api.Test;

import seedu.address.model.applicant.exceptions.DuplicatePersonException;
import seedu.address.model.applicant.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

public class UniqueApplicantListTest {

    private final UniquePersonList uniquePersonList = new UniquePersonList();

    @Test
    public void contains_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.contains(null));
    }

    @Test
    public void contains_personNotInList_returnsFalse() {
        assertFalse(uniquePersonList.contains(ALICE));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        uniquePersonList.add(ALICE);
        assertTrue(uniquePersonList.contains(ALICE));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        uniquePersonList.add(ALICE);
        Applicant editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniquePersonList.contains(editedAlice));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.add(null));
    }

    @Test
    public void add_duplicatePerson_throwsDuplicatePersonException() {
        uniquePersonList.add(ALICE);
        assertThrows(DuplicatePersonException.class, () -> uniquePersonList.add(ALICE));
    }

    @Test
    public void setPerson_nullTargetPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.setPerson(null, ALICE));
    }

    @Test
    public void setPerson_nullEditedPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.setPerson(ALICE, null));
    }

    @Test
    public void setPerson_targetPersonNotInList_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> uniquePersonList.setPerson(ALICE, ALICE));
    }

    @Test
    public void setPerson_editedPersonIsSamePerson_success() {
        uniquePersonList.add(ALICE);
        uniquePersonList.setPerson(ALICE, ALICE);
        UniquePersonList expectedUniquePersonList = new UniquePersonList();
        expectedUniquePersonList.add(ALICE);
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void setPerson_editedPersonHasSameIdentity_success() {
        uniquePersonList.add(ALICE);
        Applicant editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        uniquePersonList.setPerson(ALICE, editedAlice);
        UniquePersonList expectedUniquePersonList = new UniquePersonList();
        expectedUniquePersonList.add(editedAlice);
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void setPerson_editedPersonHasDifferentIdentity_success() {
        uniquePersonList.add(ALICE);
        uniquePersonList.setPerson(ALICE, BOB);
        UniquePersonList expectedUniquePersonList = new UniquePersonList();
        expectedUniquePersonList.add(BOB);
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void setPerson_editedPersonHasNonUniqueIdentity_throwsDuplicatePersonException() {
        uniquePersonList.add(ALICE);
        uniquePersonList.add(BOB);
        assertThrows(DuplicatePersonException.class, () -> uniquePersonList.setPerson(ALICE, BOB));
    }

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.remove(null));
    }

    @Test
    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> uniquePersonList.remove(ALICE));
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        uniquePersonList.add(ALICE);
        uniquePersonList.remove(ALICE);
        UniquePersonList expectedUniquePersonList = new UniquePersonList();
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void setPersons_nullUniquePersonList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.setPersons((UniquePersonList) null));
    }

    @Test
    public void setPersons_uniquePersonList_replacesOwnListWithProvidedUniquePersonList() {
        uniquePersonList.add(ALICE);
        UniquePersonList expectedUniquePersonList = new UniquePersonList();
        expectedUniquePersonList.add(BOB);
        uniquePersonList.setPersons(expectedUniquePersonList);
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void setPersons_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.setPersons((List<Applicant>) null));
    }

    @Test
    public void setPersons_list_replacesOwnListWithProvidedList() {
        uniquePersonList.add(ALICE);
        List<Applicant> applicantList = Collections.singletonList(BOB);
        uniquePersonList.setPersons(applicantList);
        UniquePersonList expectedUniquePersonList = new UniquePersonList();
        expectedUniquePersonList.add(BOB);
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void setPersons_listWithDuplicatePersons_throwsDuplicatePersonException() {
        List<Applicant> listWithDuplicateApplicants = Arrays.asList(ALICE, ALICE);
        assertThrows(DuplicatePersonException.class, () -> uniquePersonList.setPersons(listWithDuplicateApplicants));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniquePersonList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void toStringMethod() {
        assertEquals(uniquePersonList.asUnmodifiableObservableList().toString(), uniquePersonList.toString());
    }

    @Test
    public void sortPersons_basedOnName_caseSensitiveLexicographical_success() {
        Email email = new Email("123@gmail.com");
        Phone phone = new Phone("1234567890");
        JobPosition jp = new JobPosition("job");
        Status status = new Status("status");
        Address address = new Address("happy street");
        LocalDateTime addedTime = LocalDateTime.now();
        Set<Tag> tags = new HashSet<>();
        Applicant p1 = new Applicant(new Name("Aa"), phone, email, jp, status, address, addedTime, tags);
        Applicant p2 = new Applicant(new Name("aaa"), phone, email, jp, status, address, addedTime, tags);
        Applicant p3 = new Applicant(new Name("Bbb"), phone, email, jp, status, address, addedTime, tags);
        Applicant p4 = new Applicant(new Name("bbB"), phone, email, jp, status, address, addedTime, tags);
        uniquePersonList.add(p3);
        uniquePersonList.add(p2);
        uniquePersonList.add(p1);
        uniquePersonList.add(p4);

        UniquePersonList expectedList = new UniquePersonList();
        expectedList.add(p1); // Aa
        expectedList.add(p2); // aaa
        expectedList.add(p3); // Bbb
        expectedList.add(p4); // bbB

        uniquePersonList.sortPersons(PREFIX_NAME);
        assertEquals(expectedList, uniquePersonList);
    }

    @Test
    public void sortPersons_basedOnName_lexicographicalOnly_fail() {
        Email email = new Email("123@gmail.com");
        Phone phone = new Phone("1234567890");
        JobPosition jp = new JobPosition("job");
        Status status = new Status("status");
        Address address = new Address("happy street");
        LocalDateTime addedTime = LocalDateTime.now();
        Set<Tag> tags = new HashSet<>();
        Applicant p1 = new Applicant(new Name("Aa"), phone, email, jp, status, address, addedTime, tags);
        Applicant p2 = new Applicant(new Name("aaa"), phone, email, jp, status, address, addedTime, tags);
        Applicant p3 = new Applicant(new Name("Bbb"), phone, email, jp, status, address, addedTime, tags);
        Applicant p4 = new Applicant(new Name("bbB"), phone, email, jp, status, address, addedTime, tags);
        uniquePersonList.add(p3);
        uniquePersonList.add(p2);
        uniquePersonList.add(p1);
        uniquePersonList.add(p4);

        UniquePersonList wronglySortedList = new UniquePersonList();
        // Error: lexicographical only
        wronglySortedList.add(p1); // Aa
        wronglySortedList.add(p3); // Bbb
        wronglySortedList.add(p2); // aaa
        wronglySortedList.add(p4); // bbB

        uniquePersonList.sortPersons(PREFIX_NAME);
        assertNotEquals(wronglySortedList, uniquePersonList);
    }



}
