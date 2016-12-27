package ua.spalah.bank;

/**
 * Created by Kostya on 23.12.2016.
 */
public enum Gender {
    MALE("Mr."),
    FEMALE("Mrs.");

    private final String salutation;

    Gender(String salutation) {
        this.salutation = salutation;
    }

    public String getSalutation() {
        return salutation;
    }
}