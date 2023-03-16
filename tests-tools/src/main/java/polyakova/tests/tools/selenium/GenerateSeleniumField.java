package polyakova.tests.tools.selenium;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Generate selenium code for the field on the page
 *
 * @author Iuliia Poliakova
 */
public class GenerateSeleniumField extends JFrame implements DocumentListener, ItemListener, ActionListener {
    private final JComboBox findByTypeComboBox;
    private final JTextField findByValueInput;
    private final JComboBox fieldTypeComboBox;
    private final JButton copyButton;
    private final JTextArea templateTextArea;

    public GenerateSeleniumField() throws HeadlessException {
        super("Generate selenium field");
        JPanel top = new JPanel();
        findByTypeComboBox = new JComboBox(new String[]{"id", "xpath"});
        top.add(findByTypeComboBox);
        findByTypeComboBox.addItemListener(this);
        findByValueInput = new JTextField(30);
        findByValueInput.getDocument().addDocumentListener(this);
        top.add(findByValueInput);
        fieldTypeComboBox = new JComboBox(SeleniumFieldType.values());
        fieldTypeComboBox.addItemListener(this);
        top.add(fieldTypeComboBox);
        copyButton = new JButton("Copy");
        copyButton.addActionListener(this);
        top.add(copyButton);
        add(top, BorderLayout.NORTH);
        templateTextArea = new JTextArea();
        add(templateTextArea);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        setVisible(true);
    }

    @Override
    public void insertUpdate(DocumentEvent documentEvent) {
        changedUpdate(documentEvent);
    }

    @Override
    public void removeUpdate(DocumentEvent documentEvent) {
        changedUpdate(documentEvent);
    }

    @Override
    public void changedUpdate(DocumentEvent documentEvent) {
        templateTextArea.setText(getTemplate((String) findByTypeComboBox.getSelectedItem(), findByValueInput.getText(), (SeleniumFieldType) fieldTypeComboBox.getSelectedItem()));
    }

    @Override
    public void itemStateChanged(ItemEvent itemEvent) {
        templateTextArea.setText(getTemplate((String) findByTypeComboBox.getSelectedItem(), findByValueInput.getText(), (SeleniumFieldType) fieldTypeComboBox.getSelectedItem()));
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(templateTextArea.getText()), null);
    }

    public static String getTemplate(String findByType, String findByValue, SeleniumFieldType fieldType) {
        if (fieldType != null) {
            return fieldType.fillTemplate(findByType, findByValue);
        } else {
            return "Error fieldType";
        }
    }

    public static void main(String[] args) {
        new GenerateSeleniumField();
    }
}
