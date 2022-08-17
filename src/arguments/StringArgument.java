package arguments;

import input.Input;

/**
 * Argument for a string that should be non-empty - enables re-use for any such argument
 */
public class StringArgument extends Argument<String> {
    String emptyMessage;
    String missingMessage;

    public StringArgument(Input input, String argumentName, String emptyMessage, String missingMessage) {
        super(input, argumentName);
        this.emptyMessage = emptyMessage;
        this.missingMessage = missingMessage;
    }

    @Override
    public void validate() throws IllegalArgumentException {
        if (super.value != null) return;

        try {
            String text = input.getParameter(super.argumentName);
            super.value = text;

            if (text.trim().equals("")) {
                throw new EmptyArgumentException(emptyMessage);
            }
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(missingMessage);
        } catch (EmptyArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}
