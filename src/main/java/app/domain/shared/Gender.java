package app.domain.shared;

import java.util.Locale;

/**
 * Represents the list of genders
 */
public enum Gender {

    Male("Male"),
    Female("Female"),
    Other("Other"),
    NA("N/A"),
    EMPTY("");


    private String description;

    Gender(String description)
    {
        this.description = description;
    }

    public String getDescription(String description) {
        return description;
    }

    @Override
    public String toString()
    {
        return description.toLowerCase(Locale.ROOT);
    }
}
