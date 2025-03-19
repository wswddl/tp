package seedu.address.testutil;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import seedu.address.model.applicant.*;
import seedu.address.model.applicant.Applicant;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Applicant objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";

    public static final String DEFAULT_JOB_POSITION = "Front-End SWE";

    public static final String DEFAULT_STATUS = "Resume Screening";
    public static final LocalDateTime DEFAULT_ADDED_TIME = LocalDateTime.of(2031, 1, 1, 00, 30, 00);

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private JobPosition jobPosition;
    private Status status;
    private LocalDateTime addedTime;
    private Set<Tag> tags;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        jobPosition = new JobPosition(DEFAULT_JOB_POSITION);
        status = new Status(DEFAULT_STATUS);
        addedTime = DEFAULT_ADDED_TIME;
        tags = new HashSet<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code applicantToCopy}.
     */
    public PersonBuilder(Applicant applicantToCopy) {
        name = applicantToCopy.getName();
        phone = applicantToCopy.getPhone();
        email = applicantToCopy.getEmail();
        address = applicantToCopy.getAddress();
        jobPosition = applicantToCopy.getJobPosition();
        status = applicantToCopy.getStatus();
        addedTime = applicantToCopy.getAddedTime();
        tags = new HashSet<>(applicantToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Applicant} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Applicant} that we are building.
     */
    public PersonBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Applicant} that we are building.
     */
    public PersonBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Applicant} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Applicant} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code JobPosition} of the {@code Applicant} that we are building.
     */
    public PersonBuilder withJobPosition(String jobPosition) {
        this.jobPosition = new JobPosition(jobPosition);
        return this;
    }

    /**
     * Sets the {@code Status} of the {@code Applicant} that we are building.
     */
    public PersonBuilder withStatus(String status) {
        this.status = new Status(status);
        return this;
    }

    /**
     * Sets the {@code AddedTime} of the {@code Applicant} that we are building.
     */
    public PersonBuilder withAddedTime(LocalDateTime addedTime) {
        this.addedTime = addedTime;
        return this;
    }

    public Applicant build() {
        return new Applicant(name, phone, email, jobPosition, status, address, addedTime, tags);
    }

}
