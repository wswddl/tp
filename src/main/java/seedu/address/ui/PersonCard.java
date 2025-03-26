package seedu.address.ui;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.LogicManager;
import seedu.address.model.applicant.Applicant;


import javax.imageio.ImageIO;

import static seedu.address.ui.UiManager.CUSTOM_PROFILE_PIC_FOLDER;
import static seedu.address.ui.UiManager.DEFAULT_PROFILE_PIC;

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

    private final MainWindow mainWindow;
    private final Logger logger = LogsCenter.getLogger(PersonCard.class);
    private final Applicant applicant;

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
    public PersonCard(MainWindow mainWindow, Applicant applicant, int displayedIndex) {
        super(FXML);
        this.mainWindow = mainWindow;
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

        File file = new File(applicant.getProfilePicturePath());
        Image image;
        if (file.exists()) {
            image = new Image(file.toURI().toString());
        } else {
            // applicant profile picture got corrupted
            // OR
            // applicant profile picture is the default one
            this.applicant.setProfilePicturePath(DEFAULT_PROFILE_PIC);
            image = new Image(MainApp.class.getResourceAsStream(DEFAULT_PROFILE_PIC));
        }

        // Make the ImageView Circular
        double radius = Math.min(profileImage.getFitWidth(), profileImage.getFitHeight()) / 2;
        Circle clip = new Circle(radius, radius, radius);
        profileImage.setClip(clip);
        profileImage.setImage(image);

        // save changes in the applicant's profile pic path
        mainWindow.saveAddressBook();

    }

    @FXML
    private void handleImageClick() {
        System.out.println(applicant.getName() + " handling  " + applicant.getProfilePicturePath());
        FileChooser fileChooser = new FileChooser();

        // Set title of file chooser window
        fileChooser.setTitle("Choose Profile Picture");

        // Restrict to image file types
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));

        // Open file chooser
        File selectedFile = fileChooser.showOpenDialog(new Stage());

        // if user didn't select any file
        if (selectedFile == null) {
            return;
        }


        File savedFileDir = new File(CUSTOM_PROFILE_PIC_FOLDER);
        if (!savedFileDir.exists()) {
            savedFileDir.mkdirs(); // Create the directory if it doesn't exist
        }

        Path sourcePath = selectedFile.toPath();
        //Path savedFilePath = Paths.get(savedFileDir.getAbsolutePath(), selectedFile.getName());
        Path savedFilePath = Paths.get(CUSTOM_PROFILE_PIC_FOLDER, selectedFile.getName());

        int copyIndex = 1;
        String savedFileName = selectedFile.getName();;
        // If the file already exists, change the name by appending a number
        while (Files.exists(savedFilePath)) {
            String fileName = selectedFile.getName();
            String nameWithoutExtension = fileName.substring(0, fileName.lastIndexOf('.'));
            String extension = fileName.substring(fileName.lastIndexOf('.'));

            savedFileName = nameWithoutExtension + "_" + copyIndex++ + extension;
            savedFilePath = Paths.get(CUSTOM_PROFILE_PIC_FOLDER, savedFileName);
        }

        // copy the selected picture to profile picture folder
        // delete the old profile picture if it isn't the default one
        try {
            // Copy the file
            Files.copy(sourcePath, savedFilePath);
        } catch (IOException e) {
            logger.info("An error occurred while saving the applicant's profile picture to the folder");
        }

        // update the profile picture
        Image image = new Image(selectedFile.toURI().toString());
        profileImage.setImage(image);

        applicant.deleteProfilePic();
        applicant.setProfilePicturePath(CUSTOM_PROFILE_PIC_FOLDER + savedFileName);

        // save changes in the applicant's profile pic path
        mainWindow.saveAddressBook();
    }

}
