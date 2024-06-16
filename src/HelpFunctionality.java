import javax.swing.*;
import java.awt.*;
import java.util.prefs.Preferences;

public class HelpFunctionality {
    private final JFrame parentFrame;
    private final Preferences prefs;
    private static final String SHOW_HELP_ON_STARTUP = "showHelpOnStartup";

    public HelpFunctionality(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        prefs = Preferences.userRoot().node(this.getClass().getName());
    }

    public void addHelpMenuItem(JMenuBar menuBar) {
        JMenu helpMenu = new JMenu("Help");
        JMenuItem helpMenuItem = new JMenuItem("Show Help");
        helpMenuItem.addActionListener(e -> showHelpDialog());
        helpMenu.add(helpMenuItem);
        menuBar.add(helpMenu);
    }

    public void showHelpDialog() {
        String helpContent = "<html><body>"
                + "<h1>Nopenad - Just a Worse Notepad</h1>"
                + "<ul>"
                + "<li><b>New Note:</b> Create a new note.</li>"
                + "<li><b>Save Note:</b> Save to... (Must include file extension!)</li>"
                + "<li><b>Open Note:</b> Open an existing note.</li>"
                + "<li><b>Toggle Dark Mode:</b> Switch between dark and light modes.</li>"
                + "<li><b>Font Size:</b> Adjust the font size (12, 16, 20, 24, or custom).</li>"
                + "<li><b>Undo:</b> Undo the last action (Ctrl+Z).</li>"
                + "<li><b>Redo:</b> Redo the last undone action (Ctrl+Y).</li>"
                + "<li><b>Search:</b> Search for text within the note (Case Sensitive) (Ctrl+F).</li>"
                + "<li><b>Author : William Ghanayem.</li>"
                + "</ul>"
                + "</body></html>";

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        JLabel helpLabel = new JLabel(helpContent);
        panel.add(helpLabel, BorderLayout.CENTER);

        JCheckBox showOnStartupCheckBox = new JCheckBox("Show this dialog on startup");
        showOnStartupCheckBox.setSelected(prefs.getBoolean(SHOW_HELP_ON_STARTUP, true));
        showOnStartupCheckBox.addActionListener(e -> prefs.putBoolean(SHOW_HELP_ON_STARTUP, showOnStartupCheckBox.isSelected()));
        panel.add(showOnStartupCheckBox, BorderLayout.SOUTH);

        JOptionPane.showMessageDialog(parentFrame, panel, "Help", JOptionPane.INFORMATION_MESSAGE);
    }

    public boolean shouldShowHelpOnStartup() {
        return prefs.getBoolean(SHOW_HELP_ON_STARTUP, true);
    }
}
