import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CalculatorGUI extends JFrame {
    private JTextField displayField;
    private double firstOperand = 0;
    private String currentOperation = "";
    private boolean shouldResetDisplay = true;
    
    public CalculatorGUI() {
        setTitle("Java Calculator GUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350, 450);
        setLocationRelativeTo(null);
        setResizable(false);
        initializeComponents();
    }
    
    private void initializeComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Display field
        displayField = new JTextField();
        displayField.setFont(new Font("Arial", Font.PLAIN, 24));
        displayField.setEditable(false);
        displayField.setText("0");
        displayField.setHorizontalAlignment(JTextField.RIGHT);
        mainPanel.add(displayField, BorderLayout.NORTH);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new GridLayout(4, 4, 5, 5));
        
        String[][] buttons = {
            {"7", "8", "9", "/"},
            {"4", "5", "6", "*"},
            {"1", "2", "3", "-"},
            {"0", ".", "=", "+"}
        };
        
        for (String[] row : buttons) {
            for (String buttonText : row) {
                JButton button = new JButton(buttonText);
                button.setFont(new Font("Arial", Font.BOLD, 18));
                button.addActionListener(new ButtonClickListener(buttonText));
                buttonPanel.add(button);
            }
        }
        
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        
        // Clear button
        JButton clearButton = new JButton("C");
        clearButton.setFont(new Font("Arial", Font.BOLD, 18));
        clearButton.addActionListener(new ButtonClickListener("C"));
        
        JPanel bottomPanel = new JPanel(new GridLayout(1, 1, 5, 5));
        bottomPanel.add(clearButton);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private class ButtonClickListener implements ActionListener {
        private String buttonText;
        
        public ButtonClickListener(String text) {
            this.buttonText = text;
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            String currentDisplay = displayField.getText();
            
            if (buttonText.equals("C")) {
                displayField.setText("0");
                firstOperand = 0;
                currentOperation = "";
                shouldResetDisplay = true;
            }
            else if (buttonText.equals("=")) {
                if (!currentOperation.isEmpty()) {
                    try {
                        double secondOperand = Double.parseDouble(currentDisplay);
                        double result = calculate(firstOperand, secondOperand, currentOperation);
                        displayField.setText(String.valueOf(result));
                        firstOperand = result;
                        currentOperation = "";
                        shouldResetDisplay = true;
                    } catch (NumberFormatException ex) {
                        displayField.setText("Error");
                    }
                }
            }
            else if (isOperator(buttonText)) {
                try {
                    firstOperand = Double.parseDouble(currentDisplay);
                    currentOperation = buttonText;
                    shouldResetDisplay = true;
                } catch (NumberFormatException ex) {
                    displayField.setText("Error");
                }
            }
            else {
                if (shouldResetDisplay) {
                    displayField.setText(buttonText);
                    shouldResetDisplay = false;
                } else {
                    if (buttonText.equals(".") && currentDisplay.contains(".")) {
                        return;
                    }
                    displayField.setText(currentDisplay + buttonText);
                }
            }
        }
    }
    
    private boolean isOperator(String text) {
        return text.equals("+") || text.equals("-") || text.equals("*") || text.equals("/");
    }
    
    private double calculate(double operand1, double operand2, String operation) {
        switch (operation) {
            case "+":
                return operand1 + operand2;
            case "-":
                return operand1 - operand2;
            case "*":
                return operand1 * operand2;
            case "/":
                if (operand2 == 0) {
                    throw new ArithmeticException("Cannot divide by zero");
                }
                return operand1 / operand2;
            default:
                return 0;
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CalculatorGUI calculator = new CalculatorGUI();
            calculator.setVisible(true);
        });
    }
}
