import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Main runner for Script Gender Analysis program.
 * User must send a valid .txt file for
 * script creation, character generation, and
 * gender balance data.
 */
public class ScriptSurfer {
    // main method. Driver for the whole program
    public static void main(String[] args) {
        displayIntro();
        runMainMenuOptions();
    }

    /**
     * Displays text introducing the program.
     */
    private static void displayIntro(){
        System.out.println("\nWelcome to Script Runner, a program designed to give you useful " +
                "data on your script.\nFunctionality includes calculating character word counts, " +
                "gender balance statistics, and more.");
    }

    /**
     * Asks user to input name of .txt file (excluding extension).
     * Name of file is returned.
     * @param keyboard Scanner connected to keyboard
     * @return fileName
     */
    private static String promptFileName(Scanner keyboard){
        System.out.print("Enter script file name (no extension): ");
        String fileName = "";
        if(keyboard.hasNext()){
            fileName = keyboard.nextLine();
            fileName += ".txt";
        }
        return fileName;
    }

    /* Create a Scanner and return connected to a File with the given name.
     * pre: fileName != null
     * post: Return a Scanner connected to the file or null
     * if the File does not exist in the current directory.
     */
    private static Scanner getFileScannerForNames(String fileName) {
        Scanner sc = null;
        try {
            sc = new Scanner(new File(fileName));
        } catch (FileNotFoundException e) {
            System.out.println("\n***** ERROR IN READING FILE ***** ");
            System.out.println("Can't find this file "
                    + fileName + " in the current directory.");
            System.out.println("Error: " + e);
            String currentDir = System.getProperty("user.dir");
            System.out.println("Be sure " + fileName + " is in this directory: ");
            System.out.println(currentDir);
            System.out.println("\nReturning null from method.");
            sc = null;
        }
        return sc;
    }

    /**
     * Asks user to enter a menu choice. If the menu choice is
     * valid that number is returned. Else a message is displayed
     * and the user is asked to enter a choice again.
     * @param keyboard Scanner connected to user keyboard
     * @return user menu choice
     */
    private static int getChoice(Scanner keyboard) {
        // Note, no way to check if keyboard actually connected to System.in
        // so we simply assume it is.
        if (keyboard == null) {
            throw new IllegalArgumentException("The parameter keyboard cannot be null");
        }
        int choice = getInt(keyboard, "Enter choice: ");
        keyboard.nextLine();
        // Add one due to zero based indexing of enums, but 1 based indexing of menu.
        final int MAX_CHOICE = ScriptMenuChoices.QUIT.ordinal() + 1;
        while (choice < 1 || choice > MAX_CHOICE) {
            System.out.println();
            System.out.println(choice + " is not a valid choice");
            choice = getInt(keyboard, "Enter choice: ");
            keyboard.nextLine();
        }
        return choice;
    }

    /* Ensure an int is entered from the keyboard.
     * pre: s != null and is connected to System.in
     * post: return the int typed in by the user.
     */
    private static int getInt(Scanner s, String prompt) {
        // Note, no way to check if keyboard actually connected to System.in
        // so we simply assume it is.
        if (s == null) {
            throw new IllegalArgumentException("The parameter s cannot be null");
        }
        System.out.print(prompt);
        while (!s.hasNextInt()) {
            s.next();
            System.out.println("That was not an int.");
            System.out.print(prompt);
        }
        return s.nextInt();
    }

    /**
     * Calls the method associated with user's start-up menu choice.
     */
    private static void runMainMenuOptions() {
        Scanner keyboard = new Scanner(System.in);
        MainMenuChoices[] menuChoices = MainMenuChoices.values();
        MainMenuChoices menuChoice;
        do {
            showMainMenu();
            int userChoice = getChoice(keyboard) - 1;
            menuChoice = menuChoices[userChoice];
            if (menuChoice == MainMenuChoices.EXPLORE_DATABASE) {
                runDatabaseOptions(keyboard);
            } else if (menuChoice == MainMenuChoices.INPUT_SCRIPT) {
                Scanner fileScanner = getFileScannerForNames(promptFileName(keyboard));
                Script script = new Script(fileScanner);
                fileScanner.close();
                runScriptOptions(script, keyboard);
            }
        } while (menuChoice != MainMenuChoices.QUIT);
        keyboard.close();
    }

    /**
     * Calls the method associated with database menu choice.
     */
    private static void runDatabaseOptions(Scanner keyboard) {
        DatabaseChoices[] menuChoices = DatabaseChoices.values();
        DatabaseChoices menuChoice;
        do {
            showDatabaseMenu();
            int userChoice = getChoice(keyboard) - 1;
            menuChoice = menuChoices[userChoice];
            if (menuChoice == DatabaseChoices.DISNEY) {
                runSpecDatabaseOptions(keyboard);
            }
        } while (menuChoice != DatabaseChoices.QUIT);
    }

    private static void printDisneyOptions(){
        System.out.println("\n101 Dalmations | Aladdin | Alice in Wonderland\n" +
                "Aristocats | Atlantis | Bambi | Beauty and the Beast \n" +
                "Black Cauldron | Bolt | Cinderella | Emperor's New Groove\n" +
                "Encanto | Fantasia | Fantasia 2000 | Fox and the Hound\n" +
                "Frozen | Frozen II | Hercules | Hunchback of Notre Dame\n" +
                "Jungle Book | Lady and the Tramp | Raya and the Last Dragon\n" +
                "Lilo and Stitch | Lion King | Little Mermaid | Moana\n" +
                "Mulan | Peter Pan | Pinocchio | Pocahontas | Rescuers\n" +
                "Robin Hood | Sleeping Beauty | Snow White | Tangled\n" +
                "Tarzan | Zootopia\n");
    }

    /**
     * Calls the method associated with user's menu choice.
     */
    private static void runSpecDatabaseOptions(Scanner keyboard) {
        printDisneyOptions();
        Scanner fileScanner = getFileScannerForNames(promptFileName(keyboard));
        Script script = new Script(fileScanner);
        fileScanner.close();
        runScriptOptions(script, keyboard);
    }

    /**
     * Calls the method associated with user's menu choice.
     * @param script the script in question
     */
    private static void runScriptOptions(Script script, Scanner keyboard) {
        //Scanner keyboard = new Scanner(System.in);
        ScriptMenuChoices[] menuChoices = ScriptMenuChoices.values();
        ScriptMenuChoices menuChoice;
        do {
            showScriptMenu();
            int userChoice = getChoice(keyboard) - 1;
            menuChoice = menuChoices[userChoice];
            if (menuChoice == ScriptMenuChoices.DISPLAY_CHARACTERS) {
                displayChar(script);
            } else if (menuChoice == ScriptMenuChoices.DISPLAY_MUTED) {
                displayMuted(script);
            } else if (menuChoice == ScriptMenuChoices.ASSIGN_GENDER) {
                assignGen(script, keyboard);
            } else if (menuChoice == ScriptMenuChoices.ASSIGN_ALL_GENDERS) {
                assignAllGen(script, keyboard);
            } else if (menuChoice == ScriptMenuChoices.DISPLAY_GENDERS) {
                displayGen(script);
            } else if (menuChoice == ScriptMenuChoices.MUTE) {
                mute(script, keyboard);
            } else if (menuChoice == ScriptMenuChoices.UNMUTE) {
                unmute(script, keyboard);
            }
        } while (menuChoice != ScriptMenuChoices.QUIT);
        //keyboard.close();
    }

    /**
     * Prints string of non-muted characters names with their
     * gender, wordCounts, speechCounts, and averageWords per speech.
     * @param script the Script in question
     */
    private static void displayChar(Script script){
        System.out.print(script.toString());
    }

    /**
     * Prints string of muted characters names with their
     * gender, wordCounts, speechCounts, and averageWords per speech.
     * @param script the Script in question
     */
    private static void displayMuted(Script script){
        System.out.print(script.displayMuted());
    }

    /**
     * Prompts user to enter character name to be muted.
     * Mutes Character with input name. Muted characters will no longer
     * be displayed in character display or used in calculations
     * of gender balance.
     * @param script the Script in question
     * @param key Scanner connected to the keyboard
     */
    private static void mute(Script script, Scanner key){
        System.out.print("Name: ");
        String name = "";
        if(key.hasNext()) {
            name = key.nextLine();
            script.mute(name);
        }
        System.out.println(name + " successfully muted.");
    }

    /**
     * Prompts user to enter character name to be unmuted.
     * Un-mutes Character with input name. Non-muted characters will
     * be displayed in character display and used in calculations
     * of gender balance.
     * @param script the Script in question
     * @param key Scanner connected to the keyboard
     */
    private static void unmute(Script script, Scanner key){
        System.out.print("Name: ");
        String name = "";
        if(key.hasNext()) {
            name = key.nextLine();
            script.unmute(name);
        }
        System.out.println(name + " successfully muted.");
    }

     /**
     * Prints totalWordCounts, averageSpeechCount,
     * and averageWords per speech for all non-muted character genders.
     * @param script the Script in question
     */
    private static void displayGen(Script script){
        System.out.print(script.displayGenders());
    }

    /**
     * Prompts user to assign genders for each
     * individual character in the Script.
     * @param script the Script in question
     * @param key Scanner connected to the keyboard
     */
    private static void assignAllGen(Script script, Scanner key){
        ArrayList<String> names = script.getNames();
        for(String n : names){
            System.out.print("Gender of " + n + ": ");
            if(key.hasNext()){
                String gen = key.nextLine();
                script.assignGender(n,gen);
            }
        }
    }

    /**
     * Prompts user to assign gender for one
     * character in the Script.
     * @param script the Script in question
     * @param key Scanner connected to the keyboard
     */
    private static void assignGen(Script script, Scanner key){
        System.out.print("Name: ");
        if(key.hasNext()){
            String name = key.nextLine();
            System.out.print("Gender: ");
            if(key.hasNext()){
                String gen = key.nextLine();
                script.assignGender(name,gen);
            }
        }
    }

    /**
     * Shows the user menu.
     */
    private static void showScriptMenu() {
        System.out.println();
        System.out.println("Script Options:");
        System.out.println("Enter 1 | display character details.");
        System.out.println("Enter 2 | display muted characters.");
        System.out.println("Enter 3 | assign a gender.");
        System.out.println("Enter 4 | assign all genders.");
        System.out.println("Enter 5 | display gender balance.");
        System.out.println("Enter 6 | mute a character.");
        System.out.println("Enter 7 | un-mute a character.");
        System.out.println("Enter 8 | quit to menu.");
        System.out.println();
    }

    /**
     * Shows the start-up menu.
     */
    private static void showMainMenu() {
        System.out.println();
        System.out.println("Main Menu Options:");
        System.out.println("Enter 1 | explore script databases.");
        System.out.println("Enter 2 | input custom script.");
        System.out.println("Enter 3 | quit.");
        System.out.println();
    }

    /**
     * Shows the database menu.
     */
    private static void showDatabaseMenu() {
        System.out.println();
        System.out.println("Database Options:");
        System.out.println("Enter 1 | DISNEY.");
        System.out.println("Enter 2 | quit to menu.");
        System.out.println();
    }

    /**
     * An enumerated type to hold the menu choices
     * for evaluating user Script.
     */
    private static enum ScriptMenuChoices {
        DISPLAY_CHARACTERS, DISPLAY_MUTED, ASSIGN_GENDER, ASSIGN_ALL_GENDERS, DISPLAY_GENDERS, MUTE, UNMUTE, QUIT;
    }

    /**
     * An enumerated type to hold the start-up menu choices
     */
    private static enum MainMenuChoices {
        EXPLORE_DATABASE, INPUT_SCRIPT, QUIT;
    }

    /**
     * An enumerated type to hold database choices
     */
    private static enum DatabaseChoices {
        DISNEY, QUIT;
    }

}
