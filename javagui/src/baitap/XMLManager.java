package baitap;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Vector;

public class XMLManager extends JFrame {
    private DefaultTableModel tableModel;
    private JTable table;
    private JTextField txtTagName, txtTagValue, txtParentTagName;
    private Document document;
    private Node root;

    public XMLManager() {
        setTitle("XML Manager");
        setSize(1000, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Table to display XML elements
        tableModel = new DefaultTableModel(new String[]{"Tag Name", "Value", "Parent"}, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Input fields and buttons
        JPanel panel = new JPanel(new FlowLayout());
        txtTagName = new JTextField(10);
        txtTagValue = new JTextField(10);
        txtParentTagName = new JTextField(10);
        JButton btnAdd = new JButton("Add");
        JButton btnUpdate = new JButton("Update");
        JButton btnDelete = new JButton("Delete");
        JButton btnSave = new JButton("Save XML");
        JButton btnLoad = new JButton("Load XML");

        panel.add(new JLabel("Tag Name:"));
        panel.add(txtTagName);
        panel.add(new JLabel("Value:"));
        panel.add(txtTagValue);
        panel.add(new JLabel("Parent Tag (Optional):"));
        panel.add(txtParentTagName);
        panel.add(btnAdd);
        panel.add(btnUpdate);
        panel.add(btnDelete);
        panel.add(btnSave);
        panel.add(btnLoad);

        add(panel, BorderLayout.SOUTH);

        // Button actions
        btnAdd.addActionListener(this::addTag);
        btnUpdate.addActionListener(this::updateTag);
        btnDelete.addActionListener(this::deleteTag);
        btnSave.addActionListener(this::saveXML);
        btnLoad.addActionListener(this::loadXML);

        initXMLDocument();
    }

    private void initXMLDocument() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.newDocument();
            root = document.createElement("root");
            document.appendChild(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addTag(ActionEvent e) {
        String tagName = txtTagName.getText().trim();
        String tagValue = txtTagValue.getText().trim();
        String parentTagName = txtParentTagName.getText().trim();

        if (!tagName.isEmpty() && !tagValue.isEmpty()) {
            Node parent = root; // Default is root node

            // Find parent node if specified
            if (!parentTagName.isEmpty()) {
                NodeList nodeList = root.getChildNodes();
                for (int i = 0; i < nodeList.getLength(); i++) {
                    if (nodeList.item(i).getNodeName().equals(parentTagName)) {
                        parent = nodeList.item(i);
                        break;
                    }
                }
            }

            // Create new XML element
            Element element = document.createElement(tagName);
            element.setTextContent(tagValue);
            parent.appendChild(element);

            // Add to table
            tableModel.addRow(new Object[]{tagName, tagValue, parentTagName});
            txtTagName.setText("");
            txtTagValue.setText("");
            txtParentTagName.setText("");
        }
    }

    private void updateTag(ActionEvent e) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String tagName = txtTagName.getText().trim();
            String tagValue = txtTagValue.getText().trim();
            if (!tagName.isEmpty() && !tagValue.isEmpty()) {
                tableModel.setValueAt(tagName, selectedRow, 0);
                tableModel.setValueAt(tagValue, selectedRow, 1);

                // Update XML document
                Node node = root.getChildNodes().item(selectedRow);
                node.setNodeValue(tagValue);
            }
        }
    }

    private void deleteTag(ActionEvent e) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String tagName = tableModel.getValueAt(selectedRow, 0).toString();

            // Remove the element from XML document
            NodeList nodeList = root.getChildNodes();
            for (int i = 0; i < nodeList.getLength(); i++) {
                if (nodeList.item(i).getNodeName().equals(tagName)) {
                    root.removeChild(nodeList.item(i));
                    break;
                }
            }

            // Remove from table
            tableModel.removeRow(selectedRow);
        }
    }

    private void saveXML(ActionEvent e) {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(document);

            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                StreamResult result = new StreamResult(file);
                transformer.transform(source, result);
                JOptionPane.showMessageDialog(this, "XML saved successfully.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void loadXML(ActionEvent e) {
        try {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();

                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                document = builder.parse(file);

                tableModel.setRowCount(0);
                NodeList nodeList = document.getDocumentElement().getChildNodes();
                for (int i = 0; i < nodeList.getLength(); i++) {
                    Node node = nodeList.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        String tagName = node.getNodeName();
                        String tagValue = node.getTextContent();
                        String parentTagName = node.getParentNode().getNodeName();
                        tableModel.addRow(new Object[]{tagName, tagValue, parentTagName});
                    }
                }
                JOptionPane.showMessageDialog(this, "XML loaded successfully.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new XMLManager().setVisible(true));
    }
}
