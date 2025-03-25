package seedu.address.logic.commands;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

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
    public static final String MESSAGE_FAILURE = "Failed to export applicant list.";

    private final String fileName;

    public ExportCommand(String fileName) {
        this.fileName = fileName;
    }
    
    public String getFileName() {
        return fileName;
    }
    

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Applicant> applicants = model.getFilteredPersonList();

        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write("Name,Email,Phone,Job Position,Status,Tags\n");

            for (Applicant a : applicants) {
                writer.write(String.format("\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"\n",
                        a.getName().fullName,
                        a.getEmail().value,
                        a.getPhone().value,
                        a.getJobPosition().jobPosition,
                        a.getStatus().value,
                        a.getTags().stream().map(tag -> tag.tagName).collect(Collectors.joining(";"))
                ));
            }

            return new CommandResult(String.format(MESSAGE_SUCCESS, fileName));
        } catch (IOException e) {
            throw new CommandException(MESSAGE_FAILURE);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof ExportCommand
                && fileName.equals(((ExportCommand) other).fileName));
    }
}
