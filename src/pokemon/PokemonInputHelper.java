package pokemon;

import java.util.Scanner;
import utils.TypeUtils;

/**
 * The type Pokemon input helper.
 */
@SuppressWarnings("squid:S106")
public class PokemonInputHelper {

    private static final String INVALID_INTEGER_INPUT_MSG = "Invalid input. Please enter a valid integer.";
    private final Scanner scanner;


    /**
     * Instantiates a new Pokemon input helper.
     *
     * @param scanner the scanner
     */
    public PokemonInputHelper(final Scanner scanner) {
        this.scanner = scanner;
    }


    /**
     * Input pokedex number int.
     *
     * @param manager the manager
     *
     * @return the int
     */
    public int inputPokedexNumber(PokemonManager manager) {
        int number;

        while (true) {
            System.out.printf("Enter Pokedex Number (%d to %d): ",
                PokemonConstants.MIN_POKEDEX, PokemonConstants.MAX_POKEDEX);
            String input = scanner.nextLine().trim();
            try {
                int temp = Integer.parseInt(input);
                if (temp < PokemonConstants.MIN_LEVEL || temp > PokemonConstants.MAX_POKEDEX) {
                    System.out.printf("Must be in range %d–%d.%n",
                        PokemonConstants.MIN_LEVEL, PokemonConstants.MAX_POKEDEX);
                } else if (manager.isPokedexNumberUnique(temp)) {
                    System.out.println("Pokedex number already exists.");
                } else {
                    number = temp;
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid integer.");
            }
        }
        return number;
    }

    /**
     * Input pokemon name string.
     *
     * @param manager the manager
     *
     * @return the string
     */
    public String inputPokemonName(PokemonManager manager) {
        String name;

        while (true) {
            System.out.print("Enter Pokémon Name: ");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("Name cannot be empty.");
            } else if (!input.matches("[A-Za-z.\\-\\s'()]+")) {
                System.out.println(
                    "Invalid characters in name. Only letters, dashes, apostrophes, periods, parentheses, and spaces are allowed.");
            } else {
                name = input.substring(0, 1).toUpperCase() + input.substring(1);

                if (manager.isPokemonNameTaken(name)) {
                    System.out.println(
                        "This Pokémon name already exists. If it's a regional variant, add the region in parentheses (e.g., 'Meowth (Galar)').");
                } else {
                    break;
                }
            }
        }

        return name;
    }

    /**
     * Input pokemon typing string.
     *
     * @param typeName the type name
     *
     * @return the string
     */
    public String inputPokemonTyping(String typeName) {
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
     * Input secondary type string.
     *
     * @param typeName the type name
     *
     * @return the string
     */
    public String inputSecondaryType(String typeName) {
        String result;
        System.out.print("Does this Pokémon have a Secondary Type? (0 = No, 1 = Yes): ");
        String input = scanner.nextLine().trim();

        if (input.equals("1")) {
            result = inputPokemonTyping(typeName);
        } else if (input.equals("0")) {
            result = ""; // No secondary type
        } else {
            System.out.println("Invalid input. Assuming no secondary type.");
            result = "";
        }
        return result;
    }

    /**
     * Input evolves from int.
     *
     * @param currentNumber the current number
     * @param manager       the manager
     *
     * @return the int
     */
    public int inputEvolvesFrom(int currentNumber, PokemonManager manager) {
        int number = -1;
        boolean valid = false;

        do {
            System.out.print("Enter Pokédex Number this Pokémon evolves from (0 if none): ");
            String input = scanner.nextLine().trim();

            try {
                number = Integer.parseInt(input);

                if (number == PokemonConstants.NO_EVOLUTION) {
                    valid = true;
                } else if (number != currentNumber && manager.doesPokedexNumberExists(number)) {
                    valid = true;
                } else {
                    System.out.println(
                        "Invalid number. Enter 0 (no evolution) or an existing Pokédex number ≠ " +
                            currentNumber + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println(INVALID_INTEGER_INPUT_MSG);
            }

        } while (!valid);

        return number;
    }

    /**
     * Input evolves to int.
     *
     * @param currentNumber the current number
     * @param manager       the manager
     *
     * @return the int
     */
    public int inputEvolvesTo(int currentNumber, PokemonManager manager) {
        int number = -1;

        boolean valid = false;
        do {
            System.out.print("Enter Pokédex Number this Pokémon evolves to (0 if none): ");
            String input = scanner.nextLine().trim();

            try {
                number = Integer.parseInt(input);

                if (number == PokemonConstants.NO_EVOLUTION) {
                    valid = true;
                } else if (number != currentNumber && manager.doesPokedexNumberExists(number)) {
                    valid = true;
                } else {
                    System.out.println(
                        "Invalid number. Enter 0 (no further evolution) or an existing Pokédex number ≠ "
                            + currentNumber + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println(INVALID_INTEGER_INPUT_MSG);
            }
        } while (!valid);

        return number;
    }


    /**
     * Input evolution level int.
     *
     * @return the int
     */
    public int inputEvolutionLevel() {
        int evolutionLevel;

        while (true) {
            // Explain why minimum is 2
            System.out.print("Enter Evolution Level (2 to " + PokemonConstants.MAX_LEVEL +
                " — Pokémon start at level 1 and can’t evolve immediately): ");
            String input = scanner.nextLine().trim();
            try {
                evolutionLevel = Integer.parseInt(input);

                if (evolutionLevel >= 2 && evolutionLevel <= PokemonConstants.MAX_LEVEL) {
                    break;
                } else {
                    System.out.println("Evolution level must be between 2 and " +
                        PokemonConstants.MAX_LEVEL +
                        " (since a Pokémon can’t evolve at its base level of 1).");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter an integer between 2 and " +
                    PokemonConstants.MAX_LEVEL + ".");
            }
        }

        return evolutionLevel;
    }

    /**
     * Input base stat int.
     *
     * @param statName the stat name
     *
     * @return the int
     */
    public int inputBaseStat(String statName) {
        int stat;
        while (true) {
            System.out.print(
                "Enter base " + statName + " (1–" + PokemonConstants.MAX_BASE_STAT + "): ");
            String input = scanner.nextLine().trim();
            try {
                stat = Integer.parseInt(input);
                if (stat > 0 && stat <= PokemonConstants.MAX_BASE_STAT) {
                    break;
                } else {
                    System.out.println(
                        statName + " must be between 1 and " + PokemonConstants.MAX_BASE_STAT +
                            ".");
                }
            } catch (NumberFormatException e) {
                System.out.println(INVALID_INTEGER_INPUT_MSG);
            }
        }
        return stat;
    }
}