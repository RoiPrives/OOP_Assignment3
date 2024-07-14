package View;

import Utils.Callbacks.InputCallback;
import Utils.Callbacks.MessageCallback;

public abstract class View {
    public abstract void display(String message);
    public abstract String getInput();
    public MessageCallback getMessageCallback() {
        return this::display;
    }
    public InputCallback getInputCallback() { return this::getInput; }
}
