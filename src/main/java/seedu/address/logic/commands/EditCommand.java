package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_JOB_POSITION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
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
 * Edits the details of an existing applicant in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the applicant identified "
            + "by the index number used in the displayed applicant list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_JOB_POSITION + "JOB_POSITION] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Applicant: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This applicant already exists in the address book.";

    private final Index index;
    private final EditPersonDescriptor editPersonDescriptor;

    /**
     * @param index of the applicant in the filtered applicant list to edit
     * @param editPersonDescriptor details to edit the applicant with
     */
    public EditCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Applicant> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Applicant applicantToEdit = lastShownList.get(index.getZeroBased());
        Applicant editedApplicant = createEditedPerson(applicantToEdit, editPersonDescriptor);

        if (!applicantToEdit.isSamePerson(editedApplicant) && model.hasPerson(editedApplicant)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.setPerson(applicantToEdit, editedApplicant);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedApplicant)));
    }

    /**
     * Creates and returns a {@code Applicant} with the details of
     * {@code applicantToEdit} edited with {@code editPersonDescriptor}.
     */
    private static Applicant createEditedPerson(Applicant applicantToEdit, EditPersonDescriptor editPersonDescriptor) {
        assert applicantToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElse(applicantToEdit.getName());
        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(applicantToEdit.getPhone());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(applicantToEdit.getEmail());
        JobPosition updatedJobPosition = editPersonDescriptor.getJobPosition().orElse(applicantToEdit.getJobPosition());
        Status updatedStatus = editPersonDescriptor.getStatus().orElse(applicantToEdit.getStatus());
        Address updatedAddress = editPersonDescriptor.getAddress().orElse(applicantToEdit.getAddress());
        Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(applicantToEdit.getTags());
        Rating updatedRating = editPersonDescriptor.getRating().orElse(applicantToEdit.getRating());
        // addedTime can't and won't be changed using EditCommand
        LocalDateTime originalAddedTime = applicantToEdit.getAddedTime();
        String profilePicturePath = applicantToEdit.getProfilePicturePath();

        return new Applicant(updatedName, updatedPhone, updatedEmail, updatedJobPosition,
                updatedStatus, updatedAddress, originalAddedTime, updatedTags, updatedRating, profilePicturePath);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        EditCommand otherEditCommand = (EditCommand) other;
        return index.equals(otherEditCommand.index)
                && editPersonDescriptor.equals(otherEditCommand.editPersonDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editPersonDescriptor", editPersonDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the applicant with. Each non-empty field value
     * will replace the corresponding field value of the applicant.
     */
    public static class EditPersonDescriptor {

        private Name name;
        private Phone phone;
        private Email email;
        private JobPosition jobPosition;
        private Status status;
        private Address address;
        private Set<Tag> tags;
        private Rating rating;

        public EditPersonDescriptor() {
        }

        /**
         * Copy constructor. A defensive copy of {@code tags} is used
         * internally.
         */
        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setJobPosition(toCopy.jobPosition);
            setStatus(toCopy.status);
            setAddress(toCopy.address);
            setTags(toCopy.tags);
            setRating(toCopy.rating);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, jobPosition, address, tags);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setJobPosition(JobPosition jobPosition) {
            this.jobPosition = jobPosition;
        }

        public Optional<JobPosition> getJobPosition() {
            return Optional.ofNullable(jobPosition);
        }

        public void setStatus(Status status) {
            this.status = status;
        }

        public Optional<Status> getStatus() {
            return Optional.ofNullable(status);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}. A defensive copy of
         * {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws
         * {@code UnsupportedOperationException} if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        public void setRating(Rating rating) {
            this.rating = rating;
        }

        public Optional<Rating> getRating() {
            return Optional.ofNullable(rating);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditPersonDescriptor)) {
                return false;
            }

            EditPersonDescriptor otherEditPersonDescriptor = (EditPersonDescriptor) other;
            return Objects.equals(name, otherEditPersonDescriptor.name)
                    && Objects.equals(phone, otherEditPersonDescriptor.phone)
                    && Objects.equals(email, otherEditPersonDescriptor.email)
                    && Objects.equals(jobPosition, otherEditPersonDescriptor.jobPosition)
                    && Objects.equals(status, otherEditPersonDescriptor.status)
                    && Objects.equals(address, otherEditPersonDescriptor.address)
                    && Objects.equals(tags, otherEditPersonDescriptor.tags)
                    && Objects.equals(rating, otherEditPersonDescriptor.rating);
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
}
