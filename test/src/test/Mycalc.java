package test;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import java.awt.GridLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class Mycalc extends JFrame {
    private String exp = "";

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textField;
    private JMenuBar menuBar;
    private AboutDialog aboutDialog;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Mycalc frame = new Mycalc();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Mycalc() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);

        menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu mnNewMenu = new JMenu("File");
        menuBar.add(mnNewMenu);

        JMenuItem mntmNewMenuItem = new JMenuItem("Exit");
        mntmNewMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        mnNewMenu.add(mntmNewMenuItem);

        JMenu mnNewMenu_1 = new JMenu("Help");
        menuBar.add(mnNewMenu_1);

        JMenuItem mntmNewMenuItem_1 = new JMenuItem("About");
        mntmNewMenuItem_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                getAboutDialog().setVisible(true);
            }
        });
        mnNewMenu_1.add(mntmNewMenuItem_1);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        JPanel panel = new JPanel();
        contentPane.add(panel, BorderLayout.NORTH);
        panel.setLayout(new BorderLayout(0, 0));

        textField = new JTextField();
        textField.setText("0");
        textField.setHorizontalAlignment(SwingConstants.RIGHT);
        textField.setEditable(false);
        panel.add(textField, BorderLayout.NORTH);
        textField.setColumns(10);

        JPanel panel_1 = new JPanel();
        contentPane.add(panel_1, BorderLayout.SOUTH);
        panel_1.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

        JLabel lblNewLabel = new JLabel("Status");
        panel_1.add(lblNewLabel);

        JPanel panel_2 = new JPanel();
        contentPane.add(panel_2, BorderLayout.CENTER);
        panel_2.setLayout(new GridLayout(6, 4, 10, 5));  // Update the grid layout to 6 rows and 4 columns

        // Add buttons in the specified order
        String[] buttons = {"(", ")", "%", "AC",
                            "7", "8", "9", "+",
                            "4", "5", "6", "-",
                            "1", "2", "3", "*",
                            "0", ".", "=", "/",
                            "C"};

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String cmd = e.getActionCommand();
                    if (cmd.equals("=")) {
                        try {
                            String postfix = Infix2Postfix.convert(exp);
                            double value = Calc.eval(postfix);
                            textField.setText(String.valueOf(value));
                            exp = "";
                        } catch (Exception ex) {
                            textField.setText("Error");
                            exp = "";
                        }
                    } else if (cmd.equals("AC")) {
                        exp = "";
                        textField.setText("0");
                    } else if (cmd.equals("C")) {
                        if (exp.length() > 0) {
                            exp = exp.substring(0, exp.length() - 1);
                            textField.setText(exp.isEmpty() ? "0" : exp);
                        }
                    } else {
                        exp = exp + cmd;
                        textField.setText(exp);
                    }
                }
            });
            panel_2.add(button);
        }
    }

    public JTextField TextField() {
        return textField;
    }

    public AboutDialog getAboutDialog() {
        if (aboutDialog == null) {
            aboutDialog = new AboutDialog();
        }
        return aboutDialog;
    }
}
