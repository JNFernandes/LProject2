package app.ui.console.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Paulo Maio <pam@isep.ipp.pt>
 * This class of utils where updated for ISEP'S Integrative Project 2021-2022,
 * second semester.
 */
public class Utils
{

   static public String readLineFromConsole(String prompt)
   {
      try
      {
         System.out.println("\n" + prompt);

         InputStreamReader converter = new InputStreamReader(System.in);
         BufferedReader in = new BufferedReader(converter);

         return in.readLine();
      } catch (Exception e)
      {
         e.printStackTrace();
         return null;
      }
   }

   static public long readLongFromConsole(String prompt)
   {
      do
      {
         try
         {
            String input = readLineFromConsole(prompt);

            long value = Long.parseLong(input);

            return value;
         } catch (NumberFormatException ex)
         {
            System.out.println("Please insert a valid number!");
         }
      } while (true);
   }

   static public int readIntegerFromConsole(String prompt)
   {
      do
      {
         try
         {
            String input = readLineFromConsole(prompt);

            int value = Integer.parseInt(input);

            return value;
         } catch (NumberFormatException ex)
         {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
         }
      } while (true);
   }

   static public double readDoubleFromConsole(String prompt)
   {
      do
      {
         try
         {
            String input = readLineFromConsole(prompt);

            double value = Double.parseDouble(input);

            return value;
         } catch (NumberFormatException ex)
         {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
         }
      } while (true);
   }

   static public Date readDateFromConsole(String prompt)
   {
      do
      {
         try
         {
            String strDate = readLineFromConsole(prompt);

            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");

            Date date = df.parse(strDate);

            return date;
         } catch (ParseException ex)
         {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
         }
      } while (true);
   }

    static public LocalDateTime readDateTimeGreaterThanTodayFromConsole(String prompt)
    {
        do
        {
            try
            {
                String strDate = readLineFromConsole(prompt);

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                LocalDateTime dateTime = LocalDateTime.parse(strDate, formatter);

               validateIfDataIsAfterNow(dateTime);

               return dateTime;
            } catch (DateTimeParseException | IllegalArgumentException ex)
            {
               Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            }
        } while (true);
    }

   static public LocalDateTime readDateTimeFromConsole(String prompt)
   {
      do
      {
         try
         {
            String strDate = readLineFromConsole(prompt);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

            return LocalDateTime.parse(strDate, formatter);
         } catch (DateTimeParseException | IllegalArgumentException ex)
         {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
         }
      } while (true);
   }

   static public boolean confirm(String message)
   {
      String input;
      do
      {
         input = Utils.readLineFromConsole("\n" + message + "\n");
      } while (!input.equalsIgnoreCase("s") && !input.equalsIgnoreCase("n"));

      return input.equalsIgnoreCase("s");
   }

   static public Object showAndSelectOne(List list, String header)
   {
      showList(list, header);
      return selectsObject(list);
   }

   static public int showAndSelectIndex(List list, String header)
   {
      showList(list, header);
      return selectsIndex(list);
   }

   static public void showList(List list, String header)
   {
      System.out.println(header);

      int index = 0;
      for (Object o : list)
      {
         index++;

         System.out.println(index + ". " + o.toString());
      }
      System.out.println("");
      System.out.println("0 - Cancel");
   }

   static public void showListWithoutCancel(List list, String header)
   {
      System.out.println(header);

      int index = 0;
      for (Object o : list)
      {
         index++;

         System.out.println(index + ". " + o.toString());
      }
   }

   static public Object selectsObject(List list)
   {
      String input;
      Integer value;
      do
      {
         input = Utils.readLineFromConsole("Type your option: ");
         value = Integer.valueOf(input);
      } while (value < 0 || value > list.size());

      if (value == 0)
      {
         return null;
      } else
      {
         return list.get(value - 1);
      }
   }

   static public int selectsIndex(List list)
   {
      String input;
      Integer value;
      do
      {
         input = Utils.readLineFromConsole("Type your option: ");
         value = Integer.valueOf(input);
      } while (value < 0 || value > list.size());

      return value - 1;
   }

    static public Object showAndSelectOneFromEnum(List<Enum> list, String header)
    {
        Object object = null;
        int option = Integer.MAX_VALUE;

        do
        {
            try
            {
                showList(list, header);
                option = selectsIndex(list);
                if (option >= 0 && option < list.size())
                {
                    object = list.get(option);
                }
            } catch (NumberFormatException exception)
            {
                System.out.println("Please insert a valid option!");
            }
        } while (option == Integer.MAX_VALUE);

        return object;
    }

   static public int readPositiveIntegerFromConsole(String prompt)
   {
      do
      {
         try
         {
            String input = readLineFromConsole(prompt);
            int value = Integer.parseInt(input);
            while (value < 0)
            {
               System.out.println("The number has to be positive!");
               input = readLineFromConsole(prompt);
               value = Integer.parseInt(input);
            }

            return value;
         } catch (NumberFormatException ex)
         {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
         }
      } while (true);
   }

   static public boolean confirmEN(String message)
   {
      String input;
      do
      {
         input = Utils.readLineFromConsole(message);
      } while (!input.equalsIgnoreCase("y") && !input.equalsIgnoreCase("n"));

      return input.equalsIgnoreCase("y");
   }

   static public int readGreaterThan0IntegerFromConsole(String prompt)
   {
      do
      {
         try
         {
            String input = readLineFromConsole(prompt);
            int value = Integer.parseInt(input);
            while (value < 1)
            {
               System.out.println("The number has to be greater than one!");
               input = readLineFromConsole(prompt);
               value = Integer.parseInt(input);
            }

            return value;
         } catch (NumberFormatException ex)
         {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
         }
      } while (true);
   }

   static public double readGreaterThan0DoubleFromConsole(String prompt)
   {
      do
      {
         try
         {
            String input = readLineFromConsole(prompt);

            double value = Double.parseDouble(input);
            while (value <= 0)
            {
               System.out.println("The number has to be greater than 0!");
               input = readLineFromConsole(prompt);
               value = Integer.parseInt(input);
            }
            return value;
         } catch (NumberFormatException ex)
         {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
         }
      } while (true);
   }

   private static void validateIfDataIsAfterNow(LocalDateTime date)
   {
      if (date.isBefore(LocalDateTime.now()))
      {
         throw new IllegalArgumentException("Please insert a valid date. The date already passed.");
      }
   }


   static public LocalDateTime convertToLocalDateTimeViaInstant(Date dateToConvert)
   {
      return dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
   }

   static public LocalDate convertToDateViaInstant(LocalDateTime dateToConvert)
   {
      return dateToConvert.atZone(ZoneId.systemDefault()).toLocalDate();
   }

   static public Date readDateFromConsoleYearMonthDay(String prompt)
   {
      do
      {
         try
         {
            String strDate = readLineFromConsole(prompt);

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

            Date date = df.parse(strDate);

            return date;
         } catch (ParseException ex)
         {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
         }
      } while (true);
   }

   static public <T> List<T> removeDuplicatesFromList(List<T> t)
   {
      List<T> emptyList = new ArrayList<>();
      try
      {
         Set<T> setToClean = new HashSet<>(t);
         List<T> cleanedList = new ArrayList<>(setToClean);
         return cleanedList;
      } catch (Exception e)
      {
         e.printStackTrace();
      }
      return emptyList;

   }









}
