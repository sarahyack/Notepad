import javax.swing.*;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.OceanTheme;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.io.*;
public class Editor extends JFrame implements ActionListener{
    private JTextArea textArea;
    JFrame frame;
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenu editMenu;

    public Editor(){
        frame = new JFrame("Editor");
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
            MetalLookAndFeel.setCurrentTheme(new OceanTheme());
        } catch (Exception e) {
            e.printStackTrace();
        }
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setLayout(new BorderLayout());

        //textArea
        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.add(scrollPane,BorderLayout.CENTER);

        //menuBar
        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        JMenuItem fmItem1 = new JMenuItem("New");
        JMenuItem fmItem2 = new JMenuItem("Open");
        JMenuItem fmItem3 = new JMenuItem("Save");
        JMenuItem fmItem4 = new JMenuItem("Print");

        fmItem1.addActionListener(this);
        fmItem2.addActionListener(this);
        fmItem3.addActionListener(this);
        fmItem4.addActionListener(this);

        fileMenu.add(fmItem1);
        fileMenu.add(fmItem2);
        fileMenu.add(fmItem3);
        fileMenu.add(fmItem4);

        editMenu = new JMenu("Edit");
        JMenuItem emItem1 = new JMenuItem("Cut");
        JMenuItem emItem2 = new JMenuItem("Copy");
        JMenuItem emItem3 = new JMenuItem("Paste");

        emItem1.addActionListener(this);
        emItem2.addActionListener(this);
        emItem3.addActionListener(this);

        editMenu.add(emItem1);
        editMenu.add(emItem2);
        editMenu.add(emItem3);

        JMenuItem mc = new JMenuItem("Close");
        mc.addActionListener(this);
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(mc);

        frame.setJMenuBar(menuBar);
        frame.add(textArea);
        frame.setSize(500,500);
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e){
        String s = e.getActionCommand();
        if (s.equals("Cut")) {
            textArea.cut();
        } else if (s.equals("Copy")) {
            textArea.copy();
        } else if (s.equals("Paste")) {
            textArea.paste();
        } else if (s.equals("Save")) {
            //create an object of JFileChooser class
            JFileChooser j = new JFileChooser("f:");
            //invoke the showsSaveDialog function to show the save dialog
            int r = j.showSaveDialog(null);
            if (r == JFileChooser.APPROVE_OPTION) {
                //set the label to the path of the selected file
                File fi = new File(j.getSelectedFile().getAbsolutePath());
                try {
                    BufferedWriter bw = new BufferedWriter(new FileWriter(fi));
                    String data = textArea.getText();
                    bw.write(data);
                    bw.close();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(frame, "operation cancelled");
            }
        } else if (s.equals("Print")) {
            try {
                textArea.print();
            } catch (PrinterException ex) {
                JOptionPane.showMessageDialog(frame, ex.getMessage());
            }
        } else if (s.equals("Open")) {
            //create an object of JFileChooser class
            JFileChooser j = new JFileChooser("f:");
            //invoke the showsOpenDialog function to show the open dialog
            int r = j.showOpenDialog(null);
            if (r == JFileChooser.APPROVE_OPTION) {
                //set the label to the path of the selected file
                File fi = new File(j.getSelectedFile().getAbsolutePath());
                try {
                    BufferedReader br = new BufferedReader(new FileReader(fi));
                    textArea.read(br, null);
                    br.close();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(frame, "operation cancelled");
            }

        } else if (s.equals("New")) {
            textArea.setText("");
        } else if (s.equals("Close")) {
            frame.dispose();
        }
    }

    public static void main(String[] args) {
        Editor e = new Editor();
    }
}
