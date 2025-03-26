package seedu.address.model.applicant;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.ui.UiManager.DEFAULT_PROFILE_PIC;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Represents a Applicant in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Applicant {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;
    private String profilePhotoPath;

    // Data fields
    private final JobPosition jobPosition;
    private final Status status;
    private final Address address;
    private final LocalDateTime addedTime;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Applicant(Name name, Phone phone, Email email, JobPosition jobPosition, Status status,
                     Address address, LocalDateTime addedTime, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, jobPosition, status, address, addedTime, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.jobPosition = jobPosition;
        this.status = status;
        this.address = address;
        this.addedTime = addedTime;
        this.tags.addAll(tags);
        // default profile photo
        this.profilePhotoPath = DEFAULT_PROFILE_PIC;
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public JobPosition getJobPosition() {
        return jobPosition;
    }

    public Status getStatus() {
        return status;
    }

    public Address getAddress() {
        return address;
    }

    public LocalDateTime getAddedTime() {
        return this.addedTime;
    }
    public String getProfilePhotoPath() {
        return this.profilePhotoPath;
    }
    public void setProfilePhotoPath(String specifiedPath) {
        this.profilePhotoPath = specifiedPath;
    }

    /**
     * Formatted String of the added time for PersonCard in UI
     */
    public String getFormattedAddedTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mma d/M/yyyy");
        String formattedTime = this.addedTime.format(formatter)
                .replace("AM", "a.m.").replace("PM", "p.m.");
        return "Added at " + formattedTime;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Applicant otherApplicant) {
        if (otherApplicant == this) {
            return true;
        }

        return otherApplicant != null
                && otherApplicant.getName().equals(getName());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Applicant)) {
            return false;
        }

        Applicant otherApplicant = (Applicant) other;
        return name.equals(otherApplicant.name)
                && phone.equals(otherApplicant.phone)
                && email.equals(otherApplicant.email)
                && jobPosition.equals(otherApplicant.jobPosition)
                && status.equals(otherApplicant.status)
                && address.equals(otherApplicant.address)
                && tags.equals(otherApplicant.tags);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, jobPosition, status, address, tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("jobPosition", jobPosition)
                .add("status", status)
                .add("address", address)
                .add("tags", tags)
                .toString();
    }

}
