package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 * A person is considered unique by comparing using {@code Person#isSamePerson(Person)}. As such, adding and updating of
 * persons uses Person#isSamePerson(Person) for equality so as to ensure that the person being added or updated is
 * unique in terms of identity in the UniquePersonList. However, the removal of a person uses Person#equals(Object) so
 * as to ensure that the person with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Person#isSamePerson(Person)
 */
public class UniquePersonList implements Iterable<Person> {

    private final ObservableList<Person> internalList = FXCollections.observableArrayList();
    private final ObservableList<Person> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(Person toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSamePerson);
    }

    /**
     * Adds a person to the list.
     * The person must not already exist in the list.
     */
    public void add(Person toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePersonException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the list.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the list.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new PersonNotFoundException();
        }

        if (!target.isSamePerson(editedPerson) && contains(editedPerson)) {
            throw new DuplicatePersonException();
        }

        internalList.set(index, editedPerson);
    }

    /**
     * Removes the equivalent person from the list.
     * The person must exist in the list.
     */
    public void remove(Person toRemove) {
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
     * Replaces the contents of this list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        requireAllNonNull(persons);
        if (!personsAreUnique(persons)) {
            throw new DuplicatePersonException();
        }

        internalList.setAll(persons);
    }

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

        if (prefix.equals(new Prefix("n/"))) {
            // sort by name
            internalList.sort((p1, p2) -> caseSensitiveLexicographicComparator
                    .compare(p1.getName().fullName, p2.getName().fullName));

        } else if (prefix.equals(new Prefix("e/"))) {
            // sort by email address
            internalList.sort((p1, p2) -> caseSensitiveLexicographicComparator
                    .compare(p1.getEmail().value, p2.getEmail().value));

        } else if (prefix.equals(new Prefix("time/"))) {
            // sort by added time, first added appear at the top
            internalList.sort((p1, p2) -> p1.getAddedTime().compareTo(p2.getAddedTime()));

        } else if (prefix.equals(new Prefix("j/"))) {
            // sort by job position
            internalList.sort((p1, p2) -> caseSensitiveLexicographicComparator
                    .compare(p1.getJobPosition().jobPosition, p2.getJobPosition().jobPosition));

        } else if (prefix.equals(new Prefix("s/"))) {
            // sort by status
            internalList.sort((p1, p2) -> caseSensitiveLexicographicComparator
                    .compare(p1.getStatus().value, p2.getStatus().value));

        } // ignore non-sorting prefix
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Person> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Person> iterator() {
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
     * Returns true if {@code persons} contains only unique persons.
     */
    private boolean personsAreUnique(List<Person> persons) {
        for (int i = 0; i < persons.size() - 1; i++) {
            for (int j = i + 1; j < persons.size(); j++) {
                if (persons.get(i).isSamePerson(persons.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
