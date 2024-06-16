import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.List;

public class NoteManager extends Component {
    private JFileChooser fileChooser;
    private static final String FILENAME = "notes.txt";

    public void saveNotes(List<String> notes) {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(this); // This opens the browse feature on windows.
        if (result == JFileChooser.APPROVE_OPTION) { // If a user clicks Open or OK button in browse window ...
            File selectedFile = fileChooser.getSelectedFile();
            try (PrintWriter writer = new PrintWriter(selectedFile)) {
                for(String note : notes) {
                    writer.print(note);
                }
                JOptionPane.showMessageDialog(this, "Note saved successfully!");
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error occurred while saving the note!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public StringBuilder openNote() {
        StringBuilder content = new StringBuilder();
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try(BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {

                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }
                return content;
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }
        return null;
    }
}