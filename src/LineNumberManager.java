import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class LineNumberManager {
    private final JTextArea textArea;
    private final JTextArea lineNumberArea;

    public LineNumberManager(JTextArea textArea, JTextArea lineNumberArea) {
        this.textArea = textArea;
        this.lineNumberArea = lineNumberArea;
        setupLineNumbers();
    }

    private void setupLineNumbers() {
        // Set properties for line number area
        lineNumberArea.setEditable(false);
        lineNumberArea.setBackground(Color.LIGHT_GRAY);
        lineNumberArea.setFont(textArea.getFont());
        lineNumberArea.setBorder(new EmptyBorder(0, 5, 0, 5));

        // Add a document listener to update line numbers when the text changes
        textArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateLineNumbers();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateLineNumbers();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateLineNumbers();
            }
        });

        // Synchronize scrollbars
        textArea.getCaret().addChangeListener(e -> updateLineNumbers());

        // Initial line numbers
        updateLineNumbers();
    }

    private void updateLineNumbers() {
        String text = textArea.getText();
        int lineCount = textArea.getLineCount();
        StringBuilder numbers = new StringBuilder();
        for (int i = 1; i <= lineCount; i++) {
            numbers.append(i).append("\n");
        }
        lineNumberArea.setText(numbers.toString());
    }
}
