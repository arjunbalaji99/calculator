package CalculatorExceptions;

public class CalculatorException extends Exception {

    private final String type;
    private String selfMessage = "";

    /**
     * General Exception object for Translator, Engine, and UI
     * @param type String representing type of error, see list below:
     *       <ul>
     *             <li>"syntax"</li>
     *             <li>"div0"</li>
     *             <li>"parentheses"</li>
     *             <li>"invalidOperation"</li>
     *       </ul>
     * @param message message to send to the user
     */
    public CalculatorException(String type, String message) {
        super(message);
        this.selfMessage = message;
        this.type = type;
    }

    /**
     * General Exception object for Translator, Engine, and UI
     * @param type String representing type of error, see list below:
     *       <ul>
     *             <li>"syntax"</li>
     *             <li>"div0"</li>
     *             <li>"parentheses"</li>
     *             <li>"invalidOperation"</li>
     *       </ul>
     */
    public CalculatorException(String type) {
        this.selfMessage = "";
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public String getSelfMessage() {
        return this.selfMessage;
    }

    public String getDisplayMessage() {
        String msg;

        switch (this.getType()) {

            case "syntax":
                msg = "Syntax Error";
                break;

            case "parentheses":
                msg = "Parentheses Error";
                break;

            case "div0":
                msg = "Division by Zero";
                break;

            case "invalidOperation":
                msg = "Invalid Operation Provided";
                break;

            default:
                msg = "Error";

        }

        if (!this.getSelfMessage().isEmpty()) {
            msg += ": " + this.getSelfMessage();
        }

        return msg;
    }
}