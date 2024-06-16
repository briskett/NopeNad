import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SearchFunctionality {
    private static JTextArea textArea;

    public SearchFunctionality(JTextArea textArea) {
        this.textArea = textArea;
    }

    public static void search(String keyword) {
        String content = textArea.getText();
        int index = content.indexOf(keyword);
        if (index != -1) {
            textArea.setCaretPosition(index);
            textArea.select(index, index + keyword.length());
            textArea.grabFocus();
        } else {
            JOptionPane.showMessageDialog(null, "Keyword not found");
        }
    }

    public void addSearchMenuItem(JMenu editMenu) {
        JMenuItem searchMenuItem = new JMenuItem("Search");
        searchMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String keyword = JOptionPane.showInputDialog("Enter keyword to search:");
                if (keyword != null && !keyword.isEmpty()) {
                    search(keyword);
                }
            }
        });
        editMenu.add(searchMenuItem);
    }

    public static void promptSearch() {
        String keyword = JOptionPane.showInputDialog("Enter keyword to search:");
        if (keyword != null && !keyword.isEmpty()) {
            search(keyword);
        }
    }

    public void addKeyBindings() {
        textArea.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("control F"), "search");
        textArea.getActionMap().put("search", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                promptSearch();
            }
        });
    }
}
