package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
import seedu.address.testutil.TypicalPersons;

/**
 * Contains integration tests for {@code ExportCommand}.
 */
public class ExportCommandTest {

    private final Model model = new ModelManager(TypicalPersons.getTypicalAddressBook(), new UserPrefs());

    /**
     * Tests that executing export writes a valid CSV file with header and known data.
     */
    @Test
    public void execute_validExport_fileCreatedWithCorrectContent() throws Exception {
        File tempFile = File.createTempFile("test_export", ".csv");
        tempFile.deleteOnExit();

        ExportCommand command = new ExportCommand(tempFile.getAbsolutePath());
        command.execute(model);

        List<String> lines = Files.readAllLines(tempFile.toPath());

        assertTrue(lines.size() > 1, "CSV should contain header and at least one applicant");
        assertEquals("Name,Email,Phone,Job Position,Status,Tags", lines.get(0));

        boolean containsAlice = lines.stream().anyMatch(line -> line.contains("Alice Pauline"));
        boolean containsGeorge = lines.stream().anyMatch(line -> line.contains("George Best"));

        assertTrue(containsAlice, "CSV should contain Alice Pauline");
        assertTrue(containsGeorge, "CSV should contain George Best");
    }

    /**
     * Tests that equals() behaves correctly.
     */
    @Test
    public void equals() {
        ExportCommand c1 = new ExportCommand("file1.csv");
        ExportCommand c2 = new ExportCommand("file1.csv");
        ExportCommand c3 = new ExportCommand("other.csv");

        assertEquals(c1, c1);         // same object
        assertEquals(c1, c2);         // same value
        assertTrue(!c1.equals(c3));   // different value
        assertTrue(!c1.equals(null)); // null check
        assertTrue(!c1.equals(42));   // different type
    }

    /**
     * Tests that an export to a non-writable location throws CommandException.
     */
    @Test
    public void execute_unwritableFile_throwsCommandException() {
        String invalidPath = "/this/does/not/exist/export.csv"; 
        ExportCommand command = new ExportCommand(invalidPath);
        assertThrows(CommandException.class, () -> command.execute(model));
    }

    /**
     * Tests that exporting when applicant list is empty still writes a valid CSV file with just the header.
     */
    @Test
    public void execute_emptyList_writesHeaderOnly() throws Exception {
        Model emptyModel = new ModelManager(); // no applicants

        File tempFile = File.createTempFile("empty_export", ".csv");
        tempFile.deleteOnExit();

        ExportCommand command = new ExportCommand(tempFile.getAbsolutePath());
        command.execute(emptyModel);

        List<String> lines = Files.readAllLines(tempFile.toPath());
        assertEquals(1, lines.size(), "CSV should contain only the header line");
        assertEquals("Name,Email,Phone,Job Position,Status,Tags", lines.get(0));
    }

    /**
     * Tests that a file with a non-CSV extension (e.g. .txt) is accepted and content is correctly written.
     */
    @Test
    public void execute_fileNameWithNonCsvExtension_success() throws Exception {
        File tempFile = File.createTempFile("data", ".txt");
        tempFile.deleteOnExit();

        ExportCommand command = new ExportCommand(tempFile.getAbsolutePath());
        command.execute(model);

        List<String> lines = Files.readAllLines(tempFile.toPath());
        assertTrue(lines.size() > 0, "Exported file should not be empty for .txt extension");
        assertTrue(lines.get(0).contains("Name"), "Header should be present in .txt file");
    }

    /**
     * Tests that a filename ending with .csv.csv is accepted and correctly exported.
     */
    @Test
    public void execute_fileNameWithDuplicateExtension_success() throws Exception {
        File tempFile = File.createTempFile("report", ".csv.csv");
        tempFile.deleteOnExit();

        ExportCommand command = new ExportCommand(tempFile.getAbsolutePath());
        command.execute(model);

        List<String> lines = Files.readAllLines(tempFile.toPath());
        assertTrue(lines.size() > 0, "Exported file should contain content for .csv.csv file");
        assertTrue(lines.get(0).contains("Name"), "Header should be present for .csv.csv file");
    }

    /**
     * Tests that a filename containing multiple dots (e.g. summary.final.txt) is accepted.
     */
    @Test
    public void execute_fileNameWithMultiDotName_success() throws Exception {
        File tempFile = File.createTempFile("summary.final", ".txt");
        tempFile.deleteOnExit();

        ExportCommand command = new ExportCommand(tempFile.getAbsolutePath());
        command.execute(model);

        List<String> lines = Files.readAllLines(tempFile.toPath());
        assertTrue(lines.size() > 0, "Exported file should contain content for multi-dot file");
        assertTrue(lines.get(0).contains("Name"), "Header should be present in multi-dot file");
    }

}
