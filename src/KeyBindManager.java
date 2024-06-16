import javax.swing.*;
import java.awt.event.ActionEvent;

public class KeyBindManager {
    private final JTextArea textArea;

    public KeyBindManager(JTextArea textA) {
        this.textArea = textA;
    }
    public void addUndoRedoKeyBindings() {
        // Bind Ctrl+Z to undo
        textArea.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("control Z"), "undo");
        textArea.getActionMap().put("undo", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UndoManagerWrapper.undo();
            }
        });

        // Bind Ctrl+Y to redo
        textArea.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("control Y"), "redo");
        textArea.getActionMap().put("redo", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UndoManagerWrapper.redo();
            }
        });
    }

    public void addSearchKeyBindings() {
        textArea.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("control F"), "search");
        textArea.getActionMap().put("search", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SearchFunctionality.promptSearch();
            }
        });
    }
}
