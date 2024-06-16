import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class UI extends JFrame implements ActionListener {
    private final JTextArea textArea = new JTextArea();
    private final JTextArea lineNumberArea = new JTextArea();
    private final NoteManager manager = new NoteManager();
    private final DarkModeLogic darkModeManager;
    private final JMenuBar menuBar;
    private final JMenu fileMenu;
    private final JMenuItem newMenuItem;
    private final JMenuItem saveMenuItem;
    private final JMenuItem openMenuItem;
    private final JMenuItem menuDark;
    private final FontManager fManager;
    private final JMenuItem fontSize12;
    private final JMenuItem fontSize16;
    private final JMenuItem fontSize20;
    private final JMenuItem fontSize24;
    private final JMenuItem customFontSize;
    private boolean darkModeEnabled = false;
    private final LineNumberManager lineNumberManager;
    private final JMenu editMenu;
    private final JMenuItem undoMenuItem;
    private final JMenuItem redoMenuItem;
    private UndoManagerWrapper undoManagerWrapper;
    private SearchFunctionality searchFunctionality;
    private KeyBindManager keyBindManager;
    private HelpFunctionality helpFunctionality;
    public UI() {
        setTitle("Nopenad, just a worse notepad.");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fManager = new FontManager(textArea, lineNumberArea);
        darkModeManager = new DarkModeLogic(textArea, lineNumberArea);
        lineNumberManager = new LineNumberManager(textArea, lineNumberArea);

        // Create Menu Bar
        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        newMenuItem = new JMenuItem("New Note");
        saveMenuItem = new JMenuItem("Save Note");
        openMenuItem = new JMenuItem("Open Note");

        JMenu viewMenu = new JMenu("View");
        menuDark = new JMenuItem("Toggle Dark Mode");

        // Create Edit Menu
        editMenu = new JMenu("Edit");
        undoMenuItem = new JMenuItem("Undo");
        redoMenuItem = new JMenuItem("Redo");

        // Create Font Size submenu
        JMenu fontSizeMenu = new JMenu("Font Size");
        fontSize12 = new JMenuItem("12");
        fontSize16 = new JMenuItem("16");
        fontSize20 = new JMenuItem("20");
        fontSize24 = new JMenuItem("24");
        customFontSize = new JMenuItem("...");

        // Add font size options to submenu
        fontSizeMenu.add(fontSize12);
        fontSizeMenu.add(fontSize16);
        fontSizeMenu.add(fontSize20);
        fontSizeMenu.add(fontSize24);
        fontSizeMenu.add(customFontSize);

        // Add options to appropriate menus
        fileMenu.add(newMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.add(openMenuItem);
        viewMenu.add(menuDark);
        viewMenu.add(fontSizeMenu);
        editMenu.add(undoMenuItem);
        editMenu.add(redoMenuItem);
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(viewMenu);
        setJMenuBar(menuBar);

        // Initialize SearchFunctionality and add search menu item
        searchFunctionality = new SearchFunctionality(textArea);
        searchFunctionality.addSearchMenuItem(editMenu);

        // Initialize HelpFunctionality and add help menu item
        helpFunctionality = new HelpFunctionality(this);
        helpFunctionality.addHelpMenuItem(menuBar);

        // Main Content Area
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        // Initialize word count label
        JLabel wordCountLabel = new JLabel("Words: 0 Characters: 0");
        getContentPane().add(wordCountLabel, BorderLayout.SOUTH);

        // Initialize WordCounter
        new WordCounter(textArea, wordCountLabel);

        // Initialize UndoManagerWrapper
        undoManagerWrapper = new UndoManagerWrapper(textArea, undoMenuItem, redoMenuItem);


        // Initialize KeyBindManager
        keyBindManager = new KeyBindManager(textArea);
        keyBindManager.addUndoRedoKeyBindings();
        keyBindManager.addSearchKeyBindings();

        // Wrap text area and line number area in scroll panes
        JScrollPane textScrollPane = new JScrollPane(textArea);
        JScrollPane lineNumberScrollPane = new JScrollPane(lineNumberArea);
        lineNumberScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

        // Add scroll panes to this panel
        getContentPane().add(textScrollPane, BorderLayout.CENTER);
        getContentPane().add(lineNumberScrollPane, BorderLayout.WEST);

        textArea.setFont(new Font("Consolas", Font.PLAIN, 16)); // Set font to Consolas, plain style, size 16
        textArea.setText("Welcome to Nopenad, it's just a worse notepad.");
        setLocationRelativeTo(null);
        setVisible(true);

        // Add Action Listeners and Action Performed to the menu options
        saveMenuItem.addActionListener(this);
        openMenuItem.addActionListener(this);
        newMenuItem.addActionListener(this);
        menuDark.addActionListener(this);
        fontSize12.addActionListener(this);
        fontSize16.addActionListener(this);
        fontSize20.addActionListener(this);
        fontSize24.addActionListener(this);
        customFontSize.addActionListener(this);

        // Show help dialog on startup
        if (helpFunctionality.shouldShowHelpOnStartup()) {
            helpFunctionality.showHelpDialog();
        }
    }

    public void save(JTextArea notes) {
        String note = notes.getText(); // Retrieve text from JTextArea
        List<String> notesList = new ArrayList<>();
        notesList.add(note);
        manager.saveNotes(notesList);
    }

    public void createNewNote(JTextArea area) {
        area.setText("");
    }

    private void setDarkMode() {
        darkModeManager.enableDarkMode();
        SwingUtilities.updateComponentTreeUI(this);
        darkModeEnabled = true;
        System.out.println("Dark mode enabled!");
    }

    private void setDefaultMode() {
        darkModeManager.disableDarkMode();
        SwingUtilities.updateComponentTreeUI(UI.this);
        darkModeEnabled = false;
        System.out.println("Reverted to default!");
    }

    private void toggleDarkMode() {
        if (darkModeEnabled) {
            setDefaultMode();
        } else {
            setDarkMode();
        }
        repaint();
        revalidate();
    }

    private void setFontSize(int size) {
        fManager.setFontSize(size);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == saveMenuItem) {
            save(textArea);
        }
        if (e.getSource() == openMenuItem) {
            StringBuilder content = manager.openNote();
            if (content != null) {
                textArea.setText(content.toString());
            }
        }
        if (e.getSource() == newMenuItem) {
            createNewNote(textArea);
        }
        if (e.getSource() == menuDark) {
            toggleDarkMode();
        }
        if (e.getSource() == fontSize12) {
            setFontSize(12);
        }
        if (e.getSource() == fontSize16) {
            setFontSize(16);
        }
        if (e.getSource() == fontSize20) {
            setFontSize(20);
        }
        if (e.getSource() == fontSize24) {
            setFontSize(24);
        }
        if (e.getSource() == customFontSize) {
            fManager.requestFontSize();
        }
    }
}
