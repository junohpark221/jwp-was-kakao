package model;

public class Password {
    private final String value;

    public Password(String value) {
        this.value = value;
    }

    public boolean isCorrectPassword(String value) {
        return this.value.equals(value);
    }

}
