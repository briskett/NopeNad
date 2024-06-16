import javax.swing.*;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UndoManagerWrapper {
    private static UndoManager undoManager;
    private final JTextArea textArea;
    private static  JMenuItem undoMenuItem;
    private static JMenuItem redoMenuItem;

    public UndoManagerWrapper(JTextArea textArea, JMenuItem undoMenuItem, JMenuItem redoMenuItem) {
        this.textArea = textArea;
        this.undoManager = new UndoManager();
        this.undoMenuItem = undoMenuItem;
        this.redoMenuItem = redoMenuItem;

        // Add UndoableEditListener to textArea's Document
        textArea.getDocument().addUndoableEditListener(new UndoableEditListener() {
            @Override
            public void undoableEditHappened(UndoableEditEvent e) {
                undoManager.addEdit(e.getEdit());
                updateMenuItems();
            }
        });

        // Add ActionListeners to menu items
        undoMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                undo();
            }
        });

        redoMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                redo();
            }
        });

        updateMenuItems();  // Initialize menu item states
    }

    public static void undo() {
        if (undoManager.canUndo()) {
            try {
                undoManager.undo();
                updateMenuItems();
            } catch (CannotUndoException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void redo() {
        if (undoManager.canRedo()) {
            try {
                undoManager.redo();
                updateMenuItems();
            } catch (CannotRedoException ex) {
                ex.printStackTrace();
            }
        }
    }

    private static void updateMenuItems() {
        undoMenuItem.setEnabled(undoManager.canUndo());
        redoMenuItem.setEnabled(undoManager.canRedo());
    }

    public void addKeyBindings() {
        // Bind Ctrl+Z to undo
        textArea.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("control Z"), "undo");
        textArea.getActionMap().put("undo", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                undo();
            }
        });

        // Bind Ctrl+Y to redo
        textArea.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("control Y"), "redo");
        textArea.getActionMap().put("redo", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                redo();
            }
        });
    }
}
