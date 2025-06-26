package move;

import java.util.Scanner;
import utils.TypeUtils;

/**
 * The type Move input helper.
 */
@SuppressWarnings("squid:S106")
public class MoveInputHelper {

    private final Scanner scanner;

    /**
     * Instantiates a new Move input helper.
     *
     * @param scanner the scanner
     */
    public MoveInputHelper(final Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Input move name string.
     *
     * @param manager the manager
     *
     * @return the string
     */
    public String inputMoveName(MoveManager manager) {
        String name;

        while (true) {
            System.out.print("Enter Move Name: ");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("Name cannot be empty.");
            } else if (input.length() > 20) {
                System.out.println("Name is too long. Maximum length is 20 characters.");
            } else if (!input.matches("[A-Za-z0-9.\\-\\s'()]+")) {
                System.out.println(
                    "Invalid characters in name. Only letters, numbers, dashes, apostrophes, periods, parentheses, and spaces are allowed.");
            } else {
                // Normalize capitalization (first letter uppercase)
                name = input.substring(0, 1).toUpperCase() + input.substring(1);

                if (manager.isMoveNameTaken(name)) {
                    System.out.println(
                        "This move name already exists. Please enter a different one.");
                } else {
                    break;
                }
            }
        }
        return name;
    }

    /**
     * Input move description string.
     *
     * @return the string
     */
    public String inputMoveDescription() {
        String description;

        while (true) {
            System.out.print("Enter move description/effect: ");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("Description cannot be empty.");
            } else if (input.length() > 120) {
                System.out.println("Description is too long. Keep it under 120 characters.");
            } else if (!input.matches("[A-Za-z0-9.,'\\-()/\\%\\s]+")) {
                System.out.println(
                    "Invalid characters in description. Only letters, numbers, spaces, and . , - ' ( ) / % are allowed.");
            } else if (!input.endsWith(".")) {
                System.out.println("Description should end with a period.");
            } else {
                description = input.substring(0, 1).toUpperCase() + input.substring(1);
                break;
            }
        }
        return description;
    }

    /**
     * Input move classification move . classification.
     *
     * @return the move . classification
     */
    public Move.Classification inputMoveClassification() {
        while (true) {
            System.out.print("Enter move classification (HM or TM): ");
            String input = scanner.nextLine().trim().toUpperCase();

            try {
                return Move.Classification.valueOf(input);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid classification. Please enter either 'HM' or 'TM'.");
            }
        }
    }

    /**
     * Input move typing string.
     *
     * @param typeName the type name
     *
     * @return the string
     */
    public String inputMoveTyping(String typeName) {
        String validType = "";
        boolean isValid = false;

        while (!isValid) {
            System.out.print("Enter " + typeName + " Type: ");
            String type = scanner.nextLine().trim().toLowerCase();

            if (TypeUtils.isValidType(type)) {
                validType = TypeUtils.capitalize(type);
                isValid = true;
            } else {
                System.out.println("Invalid type. Please enter a valid Pokémon type.");
            }
        }
        return validType;
    }

    /**
     * Input secondary move type string.
     *
     * @param typeName the type name
     *
     * @return the string
     */
    public String inputSecondaryMoveType(String typeName) {
        String result;
        System.out.print("Does this move have a Secondary Type? (0 = No, 1 = Yes): ");
        String input = scanner.nextLine().trim();

        if (input.equals("1")) {
            result = inputMoveTyping(typeName);
        } else if (input.equals("0")) {
            result = ""; // No secondary type
        } else {
            System.out.println("Invalid input. Assuming no secondary type.");
            result = "";
        }
        return result;
    }
}