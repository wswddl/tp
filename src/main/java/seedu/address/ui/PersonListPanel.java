package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.model.applicant.Applicant;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends UiPart<Region> {
    private static final String FXML = "PersonListPanel.fxml";
    private final MainWindow mainWindow;

    @FXML
    private ListView<Applicant> personListView;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public PersonListPanel(ObservableList<Applicant> applicantList, MainWindow mainWindow) {
        super(FXML);
        personListView.setItems(applicantList);
        personListView.setCellFactory(listView -> new PersonListViewCell());
        this.mainWindow = mainWindow;
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Applicant} using a {@code PersonCard}.
     */
    class PersonListViewCell extends ListCell<Applicant> {
        @Override
        protected void updateItem(Applicant applicant, boolean empty) {
            super.updateItem(applicant, empty);

            if (empty || applicant == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(PersonCard.createPersonCard(mainWindow, applicant, getIndex() + 1).getRoot());
            }
        }
    }

}
