package seedu.address.model.applicant;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.*;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.applicant.exceptions.DuplicatePersonException;
import seedu.address.model.applicant.exceptions.PersonNotFoundException;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 * A applicant is considered unique by comparing using {@code Applicant#isSamePerson(Applicant)}. As such, adding and updating of
 * persons uses Applicant#isSamePerson(Applicant) for equality so as to ensure that the applicant being added or updated is
 * unique in terms of identity in the UniquePersonList. However, the removal of a applicant uses Applicant#equals(Object) so
 * as to ensure that the applicant with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Applicant#isSamePerson(Applicant)
 */
public class UniquePersonList implements Iterable<Applicant> {

    private final ObservableList<Applicant> internalList = FXCollections.observableArrayList();
    private final ObservableList<Applicant> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent applicant as the given argument.
     */
    public boolean contains(Applicant toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSamePerson);
    }

    /**
     * Adds a applicant to the list.
     * The applicant must not already exist in the list.
     */
    public void add(Applicant toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePersonException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the applicant {@code target} in the list with {@code editedApplicant}.
     * {@code target} must exist in the list.
     * The applicant identity of {@code editedApplicant} must not be the same as another existing applicant in the list.
     */
    public void setPerson(Applicant target, Applicant editedApplicant) {
        requireAllNonNull(target, editedApplicant);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new PersonNotFoundException();
        }

        if (!target.isSamePerson(editedApplicant) && contains(editedApplicant)) {
            throw new DuplicatePersonException();
        }

        internalList.set(index, editedApplicant);
    }

    /**
     * Removes the equivalent applicant from the list.
     * The applicant must exist in the list.
     */
    public void remove(Applicant toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new PersonNotFoundException();
        }
    }

    public void setPersons(UniquePersonList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code applicants}.
     * {@code applicants} must not contain duplicate applicants.
     */
    public void setPersons(List<Applicant> applicants) {
        requireAllNonNull(applicants);
        if (!personsAreUnique(applicants)) {
            throw new DuplicatePersonException();
        }

        internalList.setAll(applicants);
    }

    /**
     * Sorts the list of persons based on the given prefix.
     * The sorting works as follows:
     * - If sorting by name, email, job position, or status, the list is sorted in
     *   lexicographic order with case sensitivity (A, a, B, b, ..., Z, z).
     * - If sorting by added time, the list is sorted in chronological order,
     *   with the earliest added applicant appearing first.
     * - If the prefix is not recognized, the list remains unchanged.
     *
     * @param prefix The prefix that determines how the list should be sorted.
     */
    public void sortPersons(Prefix prefix) {
        // sort String based on lexicographic order with case sensitivity (A, a, B, b, ..., Z, z)
        Comparator<String> caseSensitiveLexicographicComparator = (s1, s2) -> {
            for (int i = 0; i < Math.min(s1.length(), s2.length()); i++) {
                char c1 = s1.charAt(i);
                char c2 = s2.charAt(i);

                if (c1 != c2) {
                    if (Character.toLowerCase(c1) == Character.toLowerCase(c2)) {
                        return Character.compare(c1, c2);
                    }
                    return Character.compare(Character.toLowerCase(c1), Character.toLowerCase(c2));
                }

            }
            return Integer.compare(s1.length(), s2.length());
        };

        if (prefix.equals(PREFIX_NAME)) {
            // sort by name
            internalList.sort((p1, p2) -> caseSensitiveLexicographicComparator
                    .compare(p1.getName().fullName, p2.getName().fullName));

        } else if (prefix.equals(PREFIX_EMAIL)) {
            // sort by email address
            internalList.sort((p1, p2) -> caseSensitiveLexicographicComparator
                    .compare(p1.getEmail().value, p2.getEmail().value));

        } else if (prefix.equals(PREFIX_ADDED_TIME)) {
            // sort by added time, first added appear at the top
            internalList.sort((p1, p2) -> p1.getAddedTime().compareTo(p2.getAddedTime()));

        } else if (prefix.equals(PREFIX_JOB_POSITION)) {
            // sort by job position
            internalList.sort((p1, p2) -> caseSensitiveLexicographicComparator
                    .compare(p1.getJobPosition().jobPosition, p2.getJobPosition().jobPosition));

        } else if (prefix.equals(PREFIX_STATUS)) {
            // sort by status
            internalList.sort((p1, p2) -> caseSensitiveLexicographicComparator
                    .compare(p1.getStatus().value, p2.getStatus().value));

        } // ignore non-sorting prefix
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Applicant> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Applicant> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UniquePersonList)) {
            return false;
        }

        UniquePersonList otherUniquePersonList = (UniquePersonList) other;
        return internalList.equals(otherUniquePersonList.internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public String toString() {
        return internalList.toString();
    }

    /**
     * Returns true if {@code applicants} contains only unique applicants.
     */
    private boolean personsAreUnique(List<Applicant> applicants) {
        for (int i = 0; i < applicants.size() - 1; i++) {
            for (int j = i + 1; j < applicants.size(); j++) {
                if (applicants.get(i).isSamePerson(applicants.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
