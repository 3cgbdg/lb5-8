package coffeevan;

import commands.*;
import menu.Menu;
import menu.MenuEnum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import services.CoffeeStorageService;

import java.io.IOException;

/**
 * Entry point for the Coffee Van application.
 * Initializes the {@link CoffeeVan} and binds available commands to a menu.
 */
public class CoffeeVanApp {
    private static final Logger LOGGER = LogManager.getLogger(CoffeeVanApp.class);

    /**
     * Main method — initializes the CoffeeVan, sets up commands, and runs the interactive menu.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        try {
            CoffeeVan coffeeVan = new CoffeeVan(500, 1500);
            CoffeeStorageService storageService = new CoffeeStorageService("coffee_data.txt");
            Menu menu = new Menu();
            menu.addCommand(MenuEnum.LOADVAN, new LoadVanCommand(coffeeVan));
            menu.addCommand(MenuEnum.SHOWCOFFEE, new ShowCoffeeCommand(coffeeVan));
            menu.addCommand(MenuEnum.SORTCOFFEE, new SortCoffeeCommand(coffeeVan));
            menu.addCommand(MenuEnum.REMOVECOFFEEBYID, new RemoveByIdCommand(coffeeVan,storageService));
            menu.addCommand(MenuEnum.SEARCHBYQUALITY, new SearchByQuality(coffeeVan));
            menu.addCommand(MenuEnum.CHECKREMAINBUDGET, new CheckRemainBudgetCommand(coffeeVan));
            menu.addCommand(MenuEnum.CHECKREMAINVOLUME, new CheckRemainVolumeCommand(coffeeVan));
            menu.addCommand(MenuEnum.COUNTTOTALPRICE, new CountTotalPriceCommand(coffeeVan));
            menu.addCommand(MenuEnum.GETFROMFILE, new GetFromFileCommand(coffeeVan, storageService));
            menu.addCommand(MenuEnum.SAVETOFILE, new SaveToFileCommand(coffeeVan, storageService));
            menu.addCommand(MenuEnum.EXIT, new ExitCommand());
            menu.run();
        } catch (Exception e) {
            LOGGER.fatal("A critical unexpected error occurred. Application is shutting down.", e);
        }
    }
}
