package app.ui;

import app.controller.App;
import app.ui.console.MainMenuUI;

/**
 *
 * @author Paulo Maio <pam@isep.ipp.pt>
 *     admin@lei.sem2.pt
 *     123456
 */

//Teste
public class Main {

    public static void main(String[] args)
    {
        try
        {
            MainMenuUI menu = new MainMenuUI();
            menu.run();

            //https://stackoverflow.com/questions/5824049/running-a-method-when-closing-the-program
            Runtime.getRuntime().addShutdownHook(new Thread(() -> App.getInstance().getCompany().saveAllStores(), "Shutdown-thread"));
        }
        catch( Exception e )
        {
            e.printStackTrace();
        }
    }
}
