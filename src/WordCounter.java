import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class WordCounter {
    private final JTextArea textArea;
    private final JLabel wordCountLabel;

    public WordCounter(JTextArea textArea, JLabel wordCountLabel) {
        this.textArea = textArea;
        this.wordCountLabel = wordCountLabel;
        addDocumentListener();
        updateWordCount();
    }

    public static int countWords(String text) {
        if (text == null || text.isEmpty()) {
            return 0;
        }
        String[] words = text.trim().split("\\s+");
        return words.length;
    }

    public static int countCharacters(String text) {
        if (text == null) {
            return 0;
        }
        return text.length();
    }

    private void updateWordCount() {
        String text = textArea.getText();
        int wordCount = countWords(text);
        int charCount = countCharacters(text);
        wordCountLabel.setText(" Words: " + wordCount + " Characters: " + charCount);
    }

    private void addDocumentListener() {
        textArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateWordCount();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateWordCount();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateWordCount();
            }
        });
    }
}
