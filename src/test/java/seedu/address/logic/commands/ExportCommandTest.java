package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.applicant.Applicant;
import seedu.address.testutil.TypicalPersons;

public class ExportCommandTest {

    private final Model model = new ModelManager(TypicalPersons.getTypicalAddressBook(), new UserPrefs());
    
    @Test
    public void execute_validExport_fileCreatedWithCorrectContent() throws Exception {
        File tempFile = File.createTempFile("test_export", ".csv");
        tempFile.deleteOnExit(); // clean up

        ExportCommand command = new ExportCommand(tempFile.getAbsolutePath());
        command.execute(model);

        List<String> lines = Files.readAllLines(tempFile.toPath());
        assertTrue(lines.size() > 0, "CSV should contain at least one line (header)");

        assertEquals("Name,Email,Phone,Job Position,Status,Tags", lines.get(0));

        boolean containsAlice = lines.stream().anyMatch(line -> line.contains("Alice Pauline"));
        assertTrue(containsAlice, "CSV should contain Alice Pauline");
    }


    @Test
    public void equals() {
        ExportCommand c1 = new ExportCommand("file1.csv");
        ExportCommand c2 = new ExportCommand("file1.csv");
        ExportCommand c3 = new ExportCommand("other.csv");

        assertEquals(c1, c1);         // same object
        assertEquals(c1, c2);         // same file name
        assertTrue(!c1.equals(c3));   // different file name
        assertTrue(!c1.equals(null)); // null
        assertTrue(!c1.equals(42));   // different type
    }
}
