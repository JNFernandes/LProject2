package app.domain.model;

import java.io.Serializable;

/**
 * Class responsable for represents the citizen card nember and respective validations
 */
public class CitizenCard implements Serializable {

    private static final long serialVersionUID = 2511798882798689469L;

    private String number;

    /**
     * Constructs an instance of Citizen Card
     *
     * @param number citizen card number
     */
    public CitizenCard(String number)
    {
        if (!this.isValid(number))
        {
            throw new IllegalArgumentException("Invalid Citizen card number.");
        } else
        {
            this.number = number;
        }
    }

    /**
     * Constructs an instance of Citizen Card from another citizen card instance
     *
     * @param citizenCard a CitizenCard
     */
    public CitizenCard(CitizenCard citizenCard)
    {
        if (citizenCard == null)
        {
            throw new IllegalArgumentException("Citizen card object can not be null");
        }
        this.number = citizenCard.number;
    }

    public String getNumber()
    {
        return number;
    }

    private boolean isValid(String number) {

        if (number == null)
        {
            throw new IllegalArgumentException("Citizen card number cannot be null.");
        }
        if (number.length() != 8)
        {
            throw new IllegalArgumentException("Citizen card number size invalid.");
        }

        return true;
    }

//    /**
//     * Validates if the inputed Citizen Card is valid:
//     *
//     * @param number the citizen card number
//     *               <ul>
//     *               <li>checks if  <strong>citizen card number</strong> is null</li>
//     *               <li>checks if  <strong>citizen card number</strong> has the correct length</li>
//     *                <li>checks if  <strong>citizen card number</strong> has the correct pattern</li>
//     *               </ul>
//     * @return a boolean that indicates whether the citizen card number is of a valid pattern
//     */
    // From https://gist.github.com/gplgps/90c49fd4f57bd7344737148def46764f
//    private boolean isValid(String number)
//    {
//        int sum = 0;
//        boolean secondDigit = false;
//        if (number == null)
//        {
//            throw new IllegalArgumentException("Citizen card number cannot be null.");
//        }
//        if (number.length() != 12)
//        {
//            throw new IllegalArgumentException("Citizen card number size invalid.");
//        }
//        for (int i = number.length() - 1; i >= 0; --i)
//        {
//            int valor = getNumberFromChar(number.charAt(i));
//            if (secondDigit)
//            {
//                valor *= 2;
//                if (valor > 9)
//                    valor -= 9;
//            }
//            sum += valor;
//            secondDigit = !secondDigit;
//        }
//
//        return (sum % 10) == 0;
//    }
//
//    /**
//     * Returns the integer in accordance with the char typed
//     * @param letter the char typed
//     * @return the integer
//     */
//    private static int getNumberFromChar(char letter)
//    {
//        switch (letter)
//        {
//            case '0':
//                return 0;
//            case '1':
//                return 1;
//            case '2':
//                return 2;
//            case '3':
//                return 3;
//            case '4':
//                return 4;
//            case '5':
//                return 5;
//            case '6':
//                return 6;
//            case '7':
//                return 7;
//            case '8':
//                return 8;
//            case '9':
//                return 9;
//            case 'A':
//                return 10;
//            case 'B':
//                return 11;
//            case 'C':
//                return 12;
//            case 'D':
//                return 13;
//            case 'E':
//                return 14;
//            case 'F':
//                return 15;
//            case 'G':
//                return 16;
//            case 'H':
//                return 17;
//            case 'I':
//                return 18;
//            case 'J':
//                return 19;
//            case 'K':
//                return 20;
//            case 'L':
//                return 21;
//            case 'M':
//                return 22;
//            case 'N':
//                return 23;
//            case 'O':
//                return 24;
//            case 'P':
//                return 25;
//            case 'Q':
//                return 26;
//            case 'R':
//                return 27;
//            case 'S':
//                return 28;
//            case 'T':
//                return 29;
//            case 'U':
//                return 30;
//            case 'V':
//                return 31;
//            case 'W':
//                return 32;
//            case 'X':
//                return 33;
//            case 'Y':
//                return 34;
//            case 'Z':
//                return 35;
//        }
//        throw new IllegalArgumentException("Citizen card number invalid character");
//    }

}
