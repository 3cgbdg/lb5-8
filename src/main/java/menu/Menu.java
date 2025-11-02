package menu;

import commands.Command;
import commands.ExitCommand;
import commands.GetFromFileCommand;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Represents the main console menu of the CoffeeVan application.
 * Handles user input and executes commands based on menu selection.
 */
public class Menu {
    private int idxOfLoad = 0;
    /**
     * Map of available commands, keyed by their menu enumeration.
     */
    private final Map<MenuEnum, Command> commands = new HashMap<>();

    /**
     * Adds a command to the menu.
     *
     * @param key     the menu item enumeration
     * @param command the command to associate with the menu item
     */
    public void addCommand(MenuEnum key, Command command) {
        commands.put(key, command);
    }

    /**
     * Starts the main menu loop.
     * Continuously displays options and executes selected commands.
     */
    public void run() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome back!");
        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Load van");
            System.out.println("2. Show coffee");
            System.out.println("3. Sort coffee");
            System.out.println("4. Delete coffee by id");
            System.out.println("5. Find coffee by quality");
            System.out.println("6. Check remaining budget");
            System.out.println("7. Check remaining volume");
            System.out.println("8. Count total price");
            System.out.println("9. Get data from file");
            System.out.println("10. Load data to file");
            System.out.println("11. Exit");
            System.out.print("Your choice: ");
            int choice = scanner.nextInt();
            Command command = commands.get(MenuEnum.fromNumber(choice));
            if (command != null) {
                if (command instanceof GetFromFileCommand) {
                    if (idxOfLoad >= 1) {
                        System.out.println("You`ve already loaded data from the file!");
                        continue;
                    }
                    command.execute();
                    idxOfLoad++;
                }else{
                    command.execute();
                }

                // if command is to exit the program - breaking the infinite loop
                if (command instanceof ExitCommand) {
                    break;
                }


            } else {
                System.out.println("Wrong choice! Try again.");
            }
        }

    }
}
