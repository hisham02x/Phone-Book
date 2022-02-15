import java.io.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.JFileChooser;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class PhoneBook {

    public static void main(String[] args) {

        JFrame frame = new JFrame();
        frame.setTitle("Phone Book");
        JTable table = new JTable();

        Object[] columns = {
                "Name",
                "Last Name",
                "Phone Number"
        };

        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(columns);
        table.setModel(model);

        TableRowSorter < TableModel > tr = new TableRowSorter < TableModel > (model);
        table.setRowSorter(tr);


        JTextField textName = new JTextField("");
        JTextField textLastName = new JTextField("");
        JTextField textPhNumber = new JTextField("");
        JTextField textSearch = new JTextField("");


        JButton buttonLoad = new JButton("Load");
        JButton buttonSave = new JButton("Save");
        JButton buttonADD = new JButton("Add");
        JButton buttonDelete = new JButton("Delete");


        JLabel searchLabel = new JLabel("Search:");
        JLabel nameLabel = new JLabel("Name:");
        JLabel lastNameLabel = new JLabel("Last Name:");
        JLabel PhNumberLabel = new JLabel("Phone Number:");


        textName.setBounds(150, 220, 100, 25);
        textLastName.setBounds(150, 265, 100, 25);
        textPhNumber.setBounds(150, 310, 100, 25);
        textSearch.setBounds(150, 355, 100, 25);


        buttonADD.setBounds(280, 220, 100, 25);
        buttonDelete.setBounds(435, 220, 100, 25);
        buttonSave.setBounds(590, 220, 100, 25);
        buttonLoad.setBounds(745, 220, 100, 25);


        nameLabel.setBounds(20, 220, 100, 25);
        lastNameLabel.setBounds(20, 265, 100, 25);
        PhNumberLabel.setBounds(20, 310, 100, 25);
        searchLabel.setBounds(20, 355, 100, 25);


        JScrollPane pane = new JScrollPane(table);
        pane.setBounds(0, 0, 880, 200);


        frame.setLayout(null);

        frame.add(pane);

        frame.add(nameLabel);
        frame.add(lastNameLabel);
        frame.add(PhNumberLabel);

        frame.add(textName);
        frame.add(textLastName);
        frame.add(textPhNumber);
        frame.add(textSearch);

        frame.add(buttonLoad);
        frame.add(buttonSave);
        frame.add(buttonADD);
        frame.add(searchLabel);
        frame.add(buttonDelete);


        Object[] row = new Object[3];

        buttonADD.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                row[0] = textName.getText();
                row[1] = textLastName.getText();
                row[2] = textPhNumber.getText();

                model.addRow(row);
            }
        });

        buttonDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int i = table.getSelectedRow();

                if (i >= 0) {
                    model.removeRow(i);
                }

                else {
                    System.out.println("Delete error");
                }
            }
        });

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int i = table.getSelectedRow();
                textName.setText(model.getValueAt(i, 0).toString());
                textLastName.setText(model.getValueAt(i, 1).toString());
                textPhNumber.setText(model.getValueAt(i, 2).toString());
            }
        });

        textSearch.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {
                String query = textSearch.getText().toLowerCase();
                tr.setRowFilter(RowFilter.regexFilter(query));

            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {
                String query = textSearch.getText().toLowerCase();
                tr.setRowFilter(RowFilter.regexFilter(query));
            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {
                String query = textSearch.getText().toLowerCase();
                tr.setRowFilter(RowFilter.regexFilter(query));

            }
        });

        buttonSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    JFileChooser jF = new JFileChooser(new File("C://"));
                    jF.setDialogTitle("Save a file");
                    int result = jF.showSaveDialog(null);

                    if (result == JFileChooser.APPROVE_OPTION)
                        System.out.println("File: " + jF.getSelectedFile());

                    FileOutputStream out;
                    PrintStream pS;
                    out = new FileOutputStream(jF.getSelectedFile());
                    pS = new PrintStream(out);

                    for (int i = 0; i < table.getRowCount(); i++) {
                        for (int j = 0; j < table.getColumnCount(); j++) {
                            pS.print(table.getValueAt(i, j).toString() + " "); //separate by space
                        }
                        pS.println(" ");
                    }

                    pS.close();
                    JOptionPane.showMessageDialog(null, " Saved!");

                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        });

        buttonLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser fc = new JFileChooser(new File("C://"));
                int returnVal = fc.showOpenDialog(null);

                if (returnVal == JFileChooser.APPROVE_OPTION)
                    System.out.println("File: " + fc.getSelectedFile());

                try {
                    FileReader fr = new FileReader(fc.getSelectedFile());
                    BufferedReader in = new BufferedReader(fr);

                    Object[] inLine = in .lines().toArray();

                    for (int i = 0; i < inLine.length; i++) {
                        String[] row1 = inLine[i].toString().split(" ");
                        model.addRow(row1);
                    } in .close();
                }

                catch (Exception e) {
                    System.out.println(e);
                }
            }
        });

        frame.setSize(892, 450);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}