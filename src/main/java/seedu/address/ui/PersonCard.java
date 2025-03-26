package seedu.address.ui;

import java.io.File;
import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import seedu.address.model.applicant.Applicant;

/**
 * An UI component that displays information of a {@code Applicant}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Applicant applicant;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label jobPosition;
    @FXML
    private Label status;
    @FXML
    private Label addedTime;
    @FXML
    private FlowPane tags;
    @FXML
    private ImageView profileImage;

    /**
     * Creates a {@code PersonCode} with the given {@code Applicant} and index to display.
     */
    public PersonCard(Applicant applicant, int displayedIndex) {
        super(FXML);
        this.applicant = applicant;
        id.setText(displayedIndex + ". ");
        name.setText(applicant.getName().fullName);
        phone.setText(applicant.getPhone().value);
        address.setText(applicant.getAddress().value);
        email.setText(applicant.getEmail().value);
        jobPosition.setText(applicant.getJobPosition().jobPosition);
        status.setText(applicant.getStatus().value);
        addedTime.setText(applicant.getFormattedAddedTime());

        applicant.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));

        // Make the ImageView Circular
        Image image = new Image("/images/profile_photos/default_profile_photo.png");
        double radius = Math.min(profileImage.getFitWidth(), profileImage.getFitHeight()) / 2;
        Circle clip = new Circle(radius, radius, radius);
        profileImage.setClip(clip);
        profileImage.setImage(image);
/*
        profileImage.setFitWidth(profilePicButton.getPrefWidth());  // Set width to button's width
        profileImage.setFitHeight(profilePicButton.getPrefHeight()); // Set height to button's height

        // Remove text from the button
        profilePicButton.setText("");

        // Set ImageView in Button (cover the entire button)
        profilePicButton.setGraphic(profileImage);
        profilePicButton.setPadding(new Insets(0)); // No padding around the image */
    }

    @FXML
    private void handleImageClick() {
        FileChooser fileChooser = new FileChooser();

        // Set title of file chooser window
        fileChooser.setTitle("Choose Profile Picture");

        // Restrict to image file types
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));

        // Open file chooser
        File selectedFile = fileChooser.showOpenDialog(new Stage());

        if (selectedFile != null) {

            Image image = new Image(selectedFile.toURI().toString());
            profileImage.setImage(image);

        }
    }

}
