package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.applicant.Applicant;
import seedu.address.model.applicant.UniqueApplicantList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniqueApplicantList persons;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniqueApplicantList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the applicant list with {@code applicants}.
     * {@code applicants} must not contain duplicate applicants.
     */
    public void setPersons(List<Applicant> applicants) {
        this.persons.setPersons(applicants);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
    }

    //// applicant-level operations

    /**
     * Returns true if a applicant with the same identity as {@code applicant} exists in the address book.
     */
    public boolean hasPerson(Applicant applicant) {
        requireNonNull(applicant);
        return persons.contains(applicant);
    }

    /**
     * Adds a applicant to the address book.
     * The applicant must not already exist in the address book.
     */
    public void addPerson(Applicant p) {
        persons.add(p);
    }

    /**
     * Replaces the given applicant {@code target} in the list with {@code editedApplicant}.
     * {@code target} must exist in the address book.
     * The applicant identity of {@code editedApplicant} must not be the same as
     * another existing applicant in the address book.
     */
    public void setPerson(Applicant target, Applicant editedApplicant) {
        requireNonNull(editedApplicant);

        persons.setPerson(target, editedApplicant);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Applicant key) {
        persons.remove(key);
    }

    /**
     * Sort {@code internalList} in {@code AddressBook} based on the prefix.
     * @param prefix is the sorting criteria.
     */
    public void sortPersons(Prefix prefix) {
        requireNonNull(prefix);

        persons.sortPersons(prefix);
    }

    //// util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("applicants", persons)
                .toString();
    }

    @Override
    public ObservableList<Applicant> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddressBook)) {
            return false;
        }

        AddressBook otherAddressBook = (AddressBook) other;
        return persons.equals(otherAddressBook.persons);
    }

    @Override
    public int hashCode() {
        return persons.hashCode();
    }
}
