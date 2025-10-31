import java.util.Scanner;

public class CalculatorConsole {
    private double result = 0;
    private Scanner scanner;
    
    public CalculatorConsole() {
        scanner = new Scanner(System.in);
    }
    
    public void run() {
        System.out.println("========================================");
        System.out.println("         Java Console Calculator");
        System.out.println("========================================");
        System.out.println("Operations: +, -, *, /, ^");
        System.out.println("Enter 'exit' to quit");
        System.out.println("========================================\n");
        
        boolean running = true;
        while (running) {
            try {
                System.out.print("Current result: " + result + "\n");
                System.out.print("Enter operation (+, -, *, /, ^): ");
                String operation = scanner.nextLine().trim();
                
                if (operation.equalsIgnoreCase("exit")) {
                    running = false;
                    System.out.println("\nThank you for using Console Calculator!");
                    continue;
                }
                
                if (!isValidOperation(operation)) {
                    System.out.println("Invalid operation. Please use +, -, *, /, or ^");
                    continue;
                }
                
                System.out.print("Enter number: ");
                double number = Double.parseDouble(scanner.nextLine().trim());
                
                result = calculate(result, number, operation);
                System.out.println("Result: " + result + "\n");
                
            } catch (NumberFormatException e) {
                System.out.println("Invalid number format. Please enter a valid number.\n");
            } catch (ArithmeticException e) {
                System.out.println("Error: " + e.getMessage() + "\n");
                result = 0;
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage() + "\n");
            }
        }
        
        scanner.close();
    }
    
    private boolean isValidOperation(String operation) {
        return operation.equals("+") || operation.equals("-") || 
               operation.equals("*") || operation.equals("/") || 
               operation.equals("^");
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
            case "^":
                return Math.pow(operand1, operand2);
            default:
                throw new IllegalArgumentException("Unknown operation: " + operation);
        }
    }
    
    public static void main(String[] args) {
        CalculatorConsole calculator = new CalculatorConsole();
        calculator.run();
    }
}
