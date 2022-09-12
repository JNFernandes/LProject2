package app.domain.shared;

public enum DoseNumber
{
    PRIMEIRA("Primeira", 1),
    SEGUNDA("Segunda",2),
    TERCEIRA("Terceira",3),
    QUARTA("Quarta",4),
    QUINTA("Quinta",5);
    private int doseNumber;
    private String ordinalNumber;

    DoseNumber(String ordinalNumber, int doseNumber)
    {
        this.ordinalNumber = ordinalNumber;
        this.doseNumber = doseNumber;
    }

    public String getOrdinalNumber() {return ordinalNumber;}
    public int getDoseNumber() {return doseNumber;}

    public static int getDoseNumberIfExists(String givenOrdinalNumber)
    {
        for (DoseNumber d : DoseNumber.values())
        {
            if (d.getOrdinalNumber().equalsIgnoreCase(givenOrdinalNumber))
            {
                return d.getDoseNumber();
            }
        }
        return -1;
    }

}
