package app.domain.shared;

public enum Role
{
    CENTER_COORDINATOR(0, "CENTER_COORDINATOR"),
    RECEPTIONIST(1, "RECEPTIONIST"),
    NURSE(2, "NURSE");

    private int id;
    private String description;

    Role(int id, String description)
    {
        this.id = id;
        this.description = description;
    }

    public String getDescription(){return description;}
    public int getId(){return id;}

}
