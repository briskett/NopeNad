import javax.swing.*;
import java.awt.*;

public class FontManager extends JFrame {
    private final JTextArea textArea;
    private final JTextArea lineNumberArea;

    public FontManager(JTextArea textA, JTextArea lineNumberA) {
        this.textArea = textA;
        this.lineNumberArea = lineNumberA;
    }

    public void setFontSize(int size) {
        Font font = getTextArea().getFont();
        getTextArea().setFont(new Font(font.getName(), font.getStyle(), size));
        getLineNumberArea().setFont(new Font(font.getName(), font.getStyle(), size));
    }

    public JTextArea getTextArea() {
        return textArea;
    }

    public JTextArea getLineNumberArea() {
        return lineNumberArea;
    }

    public void requestFontSize() {
        while (true) {
            String input = JOptionPane.showInputDialog(this, "Enter Custom Font Size:");

            if (input == null) {
                // User clicked "Cancel", exit the method
                break;
            }

            try {
                int res = Integer.parseInt(input);

                if (res <= 0) {
                    JOptionPane.showMessageDialog(this, "Please enter a valid integer value greater than 0.");
                } else {
                    setFontSize(res);
                    dispose();
                    break; // Exit the loop after successfully setting the font size
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Please enter a valid integer value.");
            }
        }
    }
}
