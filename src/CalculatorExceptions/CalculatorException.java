package CalculatorExceptions;

public class CalculatorException extends Exception {

    private String type;
    private String selfMessage;

    /**
     * General Exception object for Translator, Engine, and UI
     * @param type String representing type of error, see list below:
     *       <ul>
     *             <li>"syntax"</li>
     *             <li>"div0"</li>
     *             <li>"parentheses"</li>
     *             <li>"invalidOperation"</li>
     *       </ul>
     * @param message optional message to send to the user
     */
    public CalculatorException(String type, String message) {
        super(message);
        this.selfMessage = message;
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public String getSelfMessage() {
        return this.selfMessage;
    }
}