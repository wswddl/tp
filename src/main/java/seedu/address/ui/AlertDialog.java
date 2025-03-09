package seedu.address.ui;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.StageStyle;

/**
 * A custom alert dialog that extends {@link UiPart}.
 */
public class AlertDialog extends UiPart<Alert> {

    private static final String FXML = "AlertDialog.fxml";

    private final Alert alert;

    /**
     * Creates a new AlertDialog with the specified alert type.
     *
     * @param alertType The type of alert (e.g., CONFIRMATION, INFORMATION, ERROR).
     */
    public AlertDialog(Alert.AlertType alertType) {
        super(FXML);
        this.alert = new Alert(alertType);
        this.alert.initModality(Modality.APPLICATION_MODAL);
        this.alert.initStyle(StageStyle.UTILITY);
    }

    /**
     * Sets the title of the alert dialog.
     *
     * @param title The title of the dialog.
     */
    public void setTitle(String title) {
        alert.setTitle(title);
    }

    /**
     * Sets the header text of the alert dialog.
     *
     * @param headerText The header text of the dialog.
     */
    public void setHeaderText(String headerText) {
        alert.setHeaderText(headerText);
    }

    /**
     * Sets the content text of the alert dialog.
     *
     * @param contentText The content text of the dialog.
     */
    public void setContentText(String contentText) {
        alert.setContentText(contentText);
    }

    /**
     * Shows the alert dialog and waits for user input.
     *
     * @return An {@link Optional} containing the user's response.
     */
    public Optional<ButtonType> showAndWait() {
        return alert.showAndWait();
    }

    /**
     * Shows a confirmation dialog and returns the user's response.
     *
     * @param title   The title of the dialog.
     * @param message The message to display.
     * @return True if the user confirms, false if the user cancels.
     */
    public static boolean showConfirmationDialog(String title, String message) {
        AlertDialog dialog = new AlertDialog(Alert.AlertType.CONFIRMATION);
        dialog.setTitle(title);
        dialog.setHeaderText(null);
        dialog.setContentText(message);

        Optional<ButtonType> result = dialog.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    /**
     * Shows an information dialog.
     *
     * @param title   The title of the dialog.
     * @param message The message to display.
     */
    public static void showInformationDialog(String title, String message) {
        AlertDialog dialog = new AlertDialog(Alert.AlertType.INFORMATION);
        dialog.setTitle(title);
        dialog.setHeaderText(null);
        dialog.setContentText(message);
        dialog.showAndWait();
    }

    /**
     * Shows an error dialog.
     *
     * @param title   The title of the dialog.
     * @param message The message to display.
     */
    public static void showErrorDialog(String title, String message) {
        AlertDialog dialog = new AlertDialog(Alert.AlertType.ERROR);
        dialog.setTitle(title);
        dialog.setHeaderText(null);
        dialog.setContentText(message);
        dialog.showAndWait();
    }

    @Override
    public Alert getRoot() {
        return alert;
    }
}
