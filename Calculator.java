import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Calculator extends JFrame implements ActionListener, KeyListener {

    private JTextField display;
    private double firstNumber = 0;
    private String operator = "";
    private boolean startNewNumber = true;

    public Calculator() {

        setTitle("Dark Theme Calculator");
        setSize(350, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Display
        display = new JTextField("0");
        display.setFont(new Font("Arial", Font.BOLD, 30));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false);
        display.setBackground(new Color(35, 35, 35));
        display.setForeground(Color.WHITE);
        display.setCaretColor(Color.WHITE);
        display.addKeyListener(this);

        add(display, BorderLayout.NORTH);

        // Buttons
        JPanel panel = new JPanel(new GridLayout(5, 4, 5, 5));
        panel.setBackground(new Color(30, 30, 30));

        String[] buttons = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ".", "=", "+",
                "C", "", "", ""
        };

        for (String text : buttons) {

            if (text.equals("")) {
                panel.add(new JLabel());
                continue;
            }

            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.BOLD, 20));
            button.addActionListener(this);

            button.setBackground(new Color(60, 60, 60));
            button.setForeground(Color.WHITE);
            button.setFocusPainted(false);

            panel.add(button);
        }

        add(panel);

        addKeyListener(this);
        setFocusable(true);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        processInput(e.getActionCommand());
    }

    private void processInput(String input) {

        if (input.matches("[0-9]")) {

            if (startNewNumber || display.getText().equals("0")) {
                display.setText(input);
                startNewNumber = false;
            } else {
                display.setText(display.getText() + input);
            }

        } else if (input.equals(".")) {

            if (!display.getText().contains(".")) {
                display.setText(display.getText() + ".");
            }

        } else if (input.matches("[+\\-*/]")) {

            firstNumber = Double.parseDouble(display.getText());
            operator = input;
            startNewNumber = true;

        } else if (input.equals("=")) {

            double secondNumber = Double.parseDouble(display.getText());
            double result = 0;

            switch (operator) {
                case "+":
                    result = firstNumber + secondNumber;
                    break;
                case "-":
                    result = firstNumber - secondNumber;
                    break;
                case "*":
                    result = firstNumber * secondNumber;
                    break;
                case "/":
                    if (secondNumber == 0) {
                        display.setText("Cannot divide by zero");
                        startNewNumber = true;
                        return;
                    }
                    result = firstNumber / secondNumber;
                    break;
            }

            if (result == (long) result)
                display.setText(String.valueOf((long) result));
            else
                display.setText(String.valueOf(result));

            startNewNumber = true;

        } else if (input.equals("C")) {

            display.setText("0");
            firstNumber = 0;
            operator = "";
            startNewNumber = true;
        }
    }

    // Keyboard Support

    @Override
    public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();

        if (Character.isDigit(c)) {
            processInput(String.valueOf(c));
        } else if (c == '+' || c == '-' || c == '*' || c == '/') {
            processInput(String.valueOf(c));
        } else if (c == '.') {
            processInput(".");
        } else if (c == '\n') {
            processInput("=");
        } else if (c == 'c' || c == 'C') {
            processInput("C");
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Calculator::new);
    }
}