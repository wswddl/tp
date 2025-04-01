package seedu.address.ui;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Objects;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
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

    private MainWindow mainWindow;
    private Applicant applicant;
    private final Logger logger = LogsCenter.getLogger(PersonCard.class);

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
    private Label rating;
    @FXML
    private ImageView profileImageView;

    /**
     * Creates a {@code PersonCard} to display the given {@code Applicant}'s information.
     *
     * @param applicant The applicant whose information is to be displayed.
     * @param displayedIndex The index number to be shown beside the applicant's name.
     */
    private PersonCard(Applicant applicant, int displayedIndex) {
        super(FXML);

        id.setText(displayedIndex + ". ");
        name.setText(applicant.getName().fullName);
        phone.setText(applicant.getPhone().value);
        address.setText(applicant.getAddress().value);
        email.setText(applicant.getEmail().value);
        jobPosition.setText(applicant.getJobPosition().jobPosition);
        status.setText(applicant.getStatus().value);
        addedTime.setText(applicant.getFormattedAddedTime());
        rating.setText("Rating: " + applicant.getRating().toString());

        applicant.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    /**
     * Factory method to create a {@code PersonCard} with the given {@code Applicant} and index to display.
     */
    public static PersonCard createPersonCard(MainWindow mainWindow, Applicant applicant, int displayedIndex) {
        PersonCard personCard = new PersonCard(applicant, displayedIndex);

        personCard.mainWindow = mainWindow;
        personCard.applicant = applicant;

        personCard.setProfileImageView(applicant.getProfilePicturePath());

        return personCard;
    }

    /**
     * Configures the {@code profileImageView} by cropping the profile picture into a circular shape.
     * @param imagePath is the path where the profile picture is store at.
     */
    private void setProfileImageView(String imagePath) {
        Image image = fetchImage(imagePath);

        // crop incoming image to fit the imageView
        this.cropImage(image);

        // Make ImageView Circular
        this.setCircularImageView();

        profileImageView.setImage(image);

        // save changes in the applicant's profile pic path
        mainWindow.saveAddressBook();
    }

    /**
     * @return an {@code Image} by fetching from the provided image path.
     */
    private Image fetchImage(String imagePath) {
        File file = new File(imagePath);
        Image image;
        if (file.exists()) {
            image = new Image(file.toURI().toString());
        } else {
            // file doesn't exist if
            // the applicant profile picture got corrupted
            // OR
            // the applicant profile picture is the default one
            this.applicant.setProfilePicturePath(DEFAULT_PROFILE_PIC);
            InputStream profilePicStream = MainApp.class.getResourceAsStream(DEFAULT_PROFILE_PIC);
            image = new Image(Objects.requireNonNull(
                    profilePicStream,
                    "Default profile picture resource not found: " + DEFAULT_PROFILE_PIC));
        }
        return image;
    }

    /**
     * Crops the image into a square shape by using the smaller dimension as the side length.
     * The crop is centered within the original image.
     */
    private void cropImage(Image image) {
        // Get the dimensions of the image
        double width = image.getWidth();
        double height = image.getHeight();
        double squareSize = Math.min(width, height);

        // Calculate the top-left coordinates for the centered crop
        double cropX = (width - squareSize) / 2;
        double cropY = (height - squareSize) / 2;

        profileImageView.setViewport(new Rectangle2D(cropX, cropY, squareSize, squareSize));
    }

    /**
     * Sets a circular clipping mask on the profile image view,
     * so that the image is displayed as a circle.
     */
    private void setCircularImageView() {
        double radius = Math.min(profileImageView.getFitWidth(), profileImageView.getFitHeight()) / 2;
        Circle clip = new Circle(radius, radius, radius);
        profileImageView.setClip(clip);
    }

    /**
     * Handles the logic when the user clicks on the profile image view.
     * Opens a file chooser to select an image, saves the selected image
     * to the custom profile picture folder, updates the image display,
     * and updates the applicant's profile picture path.
     */
    @FXML
    private void handleImageClick() {
        File selectedFile = this.chooseProfilePicture();

        // check if user did NOT select a file
        if (selectedFile == null) {
            return;
        }

        // setup profile pic folder if hadn't yet
        File savedFileDir = new File(CUSTOM_PROFILE_PIC_FOLDER);
        if (!savedFileDir.exists()) {
            savedFileDir.mkdirs(); // Create the directory if it doesn't exist
        }

        Path sourcePath = selectedFile.toPath();
        Path savedFilePath = Paths.get(CUSTOM_PROFILE_PIC_FOLDER, selectedFile.getName());
        String savedFileName = selectedFile.getName();

        // Handle duplicate file name
        int copyIndex = 1;
        // If the file already exists, change the name by appending a number
        while (Files.exists(savedFilePath)) {
            String fileName = selectedFile.getName();
            String nameWithoutExtension = fileName.substring(0, fileName.lastIndexOf('.'));
            String extension = fileName.substring(fileName.lastIndexOf('.'));

            savedFileName = nameWithoutExtension + "_" + copyIndex++ + extension;
            savedFilePath = Paths.get(CUSTOM_PROFILE_PIC_FOLDER, savedFileName);
        }

        // Copy the selected picture to profile picture folder
        this.saveProfilePicture(sourcePath, savedFilePath);

        // Update the profile picture right away so it feels more responsive
        this.setProfileImageView(CUSTOM_PROFILE_PIC_FOLDER + savedFileName);

        // Update applicant info
        applicant.deleteProfilePic();
        applicant.setProfilePicturePath(CUSTOM_PROFILE_PIC_FOLDER + savedFileName);

        // Save changes in the applicant's profile pic path
        mainWindow.saveAddressBook();
    }


    /**
     * Opens a file chooser dialog for the user to select an image file.
     *
     * @return the selected image file, or {@code null} if no file was selected.
     */
    private File chooseProfilePicture() {
        FileChooser fileChooser = new FileChooser();

        // Set title of file chooser window
        fileChooser.setTitle("Choose Profile Picture");

        // Restrict to image file types
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"));

        // open file chooser and return the selected file
        return fileChooser.showOpenDialog(new Stage());
    }

    /**
     * Copies the selected profile picture file to the application's profile picture folder.
     *
     * @param sourcePath the original path of the selected image file.
     * @param savedFilePath the destination path to save the copied image.
     */
    private void saveProfilePicture(Path sourcePath, Path savedFilePath) {
        try {
            // Copy the file
            Files.copy(sourcePath, savedFilePath);
        } catch (IOException e) {
            logger.info("An error occurred while saving the applicant's profile picture to the folder");
        }
    }

}
