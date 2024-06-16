import javax.swing.*;
import java.awt.*;

public class DarkModeLogic {
    private JTextArea textArea;
    private JTextArea lineNumberArea;

    public DarkModeLogic(JTextArea textA, JTextArea lineNumberA) {
        this.lineNumberArea = lineNumberA;
        this.textArea = textA;
    }

    public void enableDarkMode() {
        getTextArea().setBackground(Color.darkGray);
        getTextArea().setForeground(Color.white);
        getLineNumberArea().setBackground(Color.GRAY);
        getLineNumberArea().setForeground(Color.white);
    }

    public void disableDarkMode() {
        getTextArea().setBackground(Color.white);
        getTextArea().setForeground(Color.black);
        getLineNumberArea().setBackground(Color.LIGHT_GRAY);
        getLineNumberArea().setForeground(Color.black);
    }

    public JTextArea getLineNumberArea() {
        return lineNumberArea;
    }

    public void setLineNumberArea(JTextArea lineNumberArea) {
        this.lineNumberArea = lineNumberArea;
    }
    public JTextArea getTextArea() {
        return textArea;
    }

    public void setTextArea(JTextArea textArea) {
        this.textArea = textArea;
    }
}
