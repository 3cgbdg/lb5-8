package menu;

/**
 * Enumeration representing the available menu options.
 * Each option corresponds to a specific command.
 */
public enum MenuEnum {
    LOADVAN(1),
    SHOWCOFFEE(2),
    SORTCOFFEE(3),
    REMOVECOFFEEBYID(4),
    SEARCHBYQUALITY(5),
    CHECKREMAINBUDGET(6),
    CHECKREMAINVOLUME(7),
    COUNTTOTALPRICE(8),
    SAVETOFILE(10),
    GETFROMFILE(9),
    EXIT(11);
    /**
     * Numeric representation of the menu item (used for console input).
     */
    private final int number;

    /**
     * Constructs a MenuEnum constant.
     *
     * @param number the numeric value assigned to the menu option
     */
    MenuEnum(int number) {
        this.number = number;
    }

    /**
     * Returns the numeric identifier of the menu item.
     *
     * @return the number associated with the menu item
     */
    public int getNumber() {
        return number;
    }

    /**
     * Converts a numeric input to a MenuEnum constant.
     *
     * @param choice the number entered by the user
     * @return the matching MenuEnum constant, or {@code null} if not found
     */
    public static MenuEnum fromNumber(int choice) {
        for (MenuEnum item : MenuEnum.values()) {
            // if enum constant value with such num exists than returning it
            if (item.getNumber() == choice) {
                return item;
            }
        }
        return null;
    }


}
