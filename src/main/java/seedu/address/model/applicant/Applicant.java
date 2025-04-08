package seedu.address.model.applicant;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.ui.UiManager.DEFAULT_PROFILE_PIC;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Represents an Applicant in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Applicant {
    public static final Applicant NULL_APPLICANT = new Applicant(
            new Name("name"), new Phone("12345678"), new Email("null@mail.com"),
            new JobPosition("job"), new Status("status"), new Address("address"),
            LocalDateTime.now(), new HashSet<>(), new Rating("-1"), "path");

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final JobPosition jobPosition;
    private final Status status;
    private final Address address;
    private final LocalDateTime addedTime;
    private final Set<Tag> tags = new HashSet<>();
    private final Rating rating;
    private String profilePicturePath;

    /**
     * Constructor of Applicant.
     * Every field must be present and not null, except {@code profilePicturePath}.
     */
    public Applicant(Name name, Phone phone, Email email, JobPosition jobPosition, Status status,
                     Address address, LocalDateTime addedTime, Set<Tag> tags,
                     Rating rating, String profilePicturePath) {
        requireAllNonNull(name, phone, email, jobPosition, status, address, addedTime, tags, rating);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.jobPosition = jobPosition;
        this.status = status;
        this.address = address;
        this.addedTime = addedTime;
        this.tags.addAll(tags);
        this.rating = rating;
        if (profilePicturePath == null) {
            this.profilePicturePath = DEFAULT_PROFILE_PIC;
        } else {
            this.profilePicturePath = profilePicturePath;
        }
    }

    /**
     * Constructor of Applicant with no profile picture and assigned rating
     */
    public Applicant(Name name, Phone phone, Email email, JobPosition jobPosition, Status status,
                     Address address, LocalDateTime addedTime, Set<Tag> tags) {
        this(name, phone, email, jobPosition, status, address, addedTime, tags,
                new Rating("-1"), null);
    }

    /**
     * Constructor of Applicant with assigned rating but no profile picture
     */
    public Applicant(Name name, Phone phone, Email email, JobPosition jobPosition, Status status,
                     Address address, LocalDateTime addedTime, Set<Tag> tags, Rating rating) {
        this(name, phone, email, jobPosition, status, address, addedTime, tags,
                rating, null);
    }

    /**
     * Constructor of Applicant with profile picture but no assigned rating
     */
    public Applicant(Name name, Phone phone, Email email, JobPosition jobPosition, Status status,
                     Address address, LocalDateTime addedTime, Set<Tag> tags, String profilePicturePath) {
        this(name, phone, email, jobPosition, status, address, addedTime, tags,
                new Rating("-1"), profilePicturePath);
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
    public String getProfilePicturePath() {
        return this.profilePicturePath;
    }
    public void setProfilePicturePath(String specifiedPath) {
        this.profilePicturePath = specifiedPath;
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

    public Rating getRating() {
        return rating;
    }

    /**
     * Returns true if both persons have the same email or phone number.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Applicant otherApplicant) {
        if (otherApplicant == this) {
            return true;
        }

        return otherApplicant != null
                && (otherApplicant.getEmail().equals(getEmail()) || otherApplicant.getPhone().equals(getPhone()));
    }

    /**
     * Deletes the image file in the profile pictures folder if it is not the default profile picture.
     */
    public void deleteProfilePic() {
        if (!profilePicturePath.equals(DEFAULT_PROFILE_PIC)) {
            Path photoPath = Paths.get(profilePicturePath);
            try {
                Files.delete(photoPath);
                // set it to default profile picture
                profilePicturePath = DEFAULT_PROFILE_PIC;
            } catch (IOException e) {
                System.err.println("Error deleting photo: " + e.getMessage());
            }
        }
        // else do nothing, don't delete the default profile pic
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
        // don't compare the added time
        return name.equals(otherApplicant.name)
                && phone.equals(otherApplicant.phone)
                && email.equals(otherApplicant.email)
                && jobPosition.equals(otherApplicant.jobPosition)
                && status.equals(otherApplicant.status)
                && address.equals(otherApplicant.address)
                && tags.equals(otherApplicant.tags)
                && rating.equals((otherApplicant.rating))
                && profilePicturePath.equals(otherApplicant.profilePicturePath);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, jobPosition, status, address, tags, rating);
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
                .add("rating", rating)
                .toString();
    }

}
