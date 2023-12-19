import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimpleCalculator extends JFrame {

    private JTextField textField;
    private String currentInput;
    private double result;
    private char operator;

    public SimpleCalculator() {
        setTitle("Simple Calculator");
        setSize(300, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        currentInput = "";
        result = 0;
        operator = ' ';

        textField = new JTextField();
        textField.setEditable(false);
        textField.setFont(new Font("Arial", Font.PLAIN, 24));
        add(textField, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(5, 4, 5, 5)); // Increased gap between buttons

        String[] buttonLabels = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ".", "=", "+",
                "C" // Added Reset button
        };

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.addActionListener(new ButtonClickListener());
            button.setFont(new Font("Arial", Font.PLAIN, 18)); // Adjusted font size

            // Set colors for operators and digits
            if (label.matches("[=]")) {
                button.setBackground(Color.orange);
            }
            else if (label.matches("[/*\\-+C]"))
            {
                button.setBackground(Color.yellow);
            }
            else {
                button.setBackground(Color.lightGray);
            }

            buttonPanel.add(button);
        }

        add(buttonPanel, BorderLayout.CENTER);
    }

    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            String buttonText = ((JButton) event.getSource()).getText();

            switch (buttonText) {
                case "=":
                    calculateResult();
                    break;
                case "/":
                case "*":
                case "-":
                case "+":
                    setOperator(buttonText.charAt(0));
                    break;
                case "C":
                    resetCalculator();
                    break;
                default:
                    appendToInput(buttonText);
                    break;
            }
        }
    }

    private void appendToInput(String input) {
        currentInput += input;
        textField.setText(currentInput);
    }

    private void setOperator(char op) {
        operator = op;
        result = Double.parseDouble(currentInput);
        currentInput = "";
    }

    private void calculateResult() {
        if (!currentInput.isEmpty()) {
            double operand = Double.parseDouble(currentInput);
            try {
                switch (operator) {
                    case '/':
                        if (operand == 0) {
                            throw new ArithmeticException("Cannot divide by zero");
                        }
                        result /= operand;
                        break;
                    case '*':
                        result *= operand;
                        break;
                    case '-':
                        result -= operand;
                        break;
                    case '+':
                        result += operand;
                        break;
                }
                currentInput = String.valueOf(result);
                textField.setText(currentInput);
            } catch (ArithmeticException e) {
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                resetCalculator();
            }
        }
    }

    private void resetCalculator() {
        currentInput = "";
        result = 0;
        operator = ' ';
        textField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SimpleCalculator calculator = new SimpleCalculator();
            calculator.setVisible(true);
        });
    }
}
