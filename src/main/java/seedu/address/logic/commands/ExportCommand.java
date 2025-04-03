package seedu.address.logic.commands;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.applicant.Applicant;

/**
 * Exports the currently displayed applicant list to a CSV file.
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Exports the current applicant list to a CSV file.\n"
            + "Parameters: [FILE-NAME]\n"
            + "Example: " + COMMAND_WORD + " applicants_data.csv";

    public static final String MESSAGE_SUCCESS = "Exported applicant list to: %s";
    public static final String MESSAGE_CANCELLED = "Export cancelled.";
    public static final String MESSAGE_FAILURE = "Failed to export applicant list.";

    private final String fileName;
    /**
     * Constructs an {@code ExportCommand} with the given file name.
     *
     * @param fileName The name of the CSV file to write to.
     */
    public ExportCommand(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Returns the file name specified for the export.
     *
     * @return The file name string.
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Executes the export command by writing the currently filtered applicant list to a CSV file.
     *
     * @param model The application's model containing the applicant list.
     * @return A {@code CommandResult} indicating success or failure of the export.
     * @throws CommandException If an I/O error occurs during file writing.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Exported CSV");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        fileChooser.setInitialFileName(fileName);
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        File file = fileChooser.showSaveDialog(new Stage()); // open new dialog stage

        if (file == null) {
            return new CommandResult(MESSAGE_CANCELLED);
        }

        if (!file.getName().endsWith(".csv")) {
            file = new File(file.getAbsolutePath() + ".csv");
        }

        try (FileWriter writer = new FileWriter(file)) {
            writer.write("Name,Email,Phone,Address,Job Position,Status,Tags,Added Time,Rating\n");

            List<Applicant> applicants = model.getFilteredPersonList();
            for (Applicant a : applicants) {
                writer.write(String.format("\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"\n",
                        a.getName().fullName,
                        a.getEmail().value,
                        a.getPhone().value,
                        a.getAddress().value,
                        a.getJobPosition().jobPosition,
                        a.getStatus().value,
                        a.getTags().stream().map(tag -> tag.tagName).collect(Collectors.joining(";")),
                        a.getAddedTime().toString(),
                        a.getRating().toString()
                ));
            }

            return new CommandResult(String.format(MESSAGE_SUCCESS, file.getName()));
        } catch (IOException e) {
            throw new CommandException(MESSAGE_FAILURE, e);
        }
    }

    /**
     * Checks whether this command is equal to another.
     *
     * @param other The object to compare against.
     * @return True if both commands export to the same file name.
     */
    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof ExportCommand
                && fileName.equals(((ExportCommand) other).fileName));
    }
}
