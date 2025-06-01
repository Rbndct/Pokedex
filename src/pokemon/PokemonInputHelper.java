package pokemon;

import java.util.Scanner;

public class PokemonInputHelper {
    private final Scanner scanner;

    public PokemonInputHelper(final Scanner scanner) {
        this.scanner = scanner;
    }


    public int inputPokedexNumber(PokemonManager manager) {
        int number;

        while (true) {
            System.out.print("Enter Pokedex Number (1 to 1010): ");
            String input = scanner.nextLine();
            try {
                int temp = Integer.parseInt(input);
                if (temp < 1 || temp > 1010) {
                    System.out.println("Must be in range 1–1010.");
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


    public String inputPokemonName(PokemonManager manager) {
        String name;

        while (true) {
            System.out.print("Enter Pokémon Name: ");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("Name cannot be empty.");
            } else if (!input.matches("[A-Za-z.\\-\\s'()]+")) {
                System.out.println("Invalid characters in name. Only letters, dashes, apostrophes, periods, parentheses, and spaces are allowed.");
            } else {
                name = input.substring(0, 1).toUpperCase() + input.substring(1);

                if (manager.isPokemonNameTaken(name)) {
                    System.out.println("This Pokémon name already exists. If it's a regional variant, add the region in parentheses (e.g., 'Meowth (Galar)').");
                } else {
                    break;
                }
            }
        }

        return name;
    }




    public String inputPokemonTyping(String typeName) {
        String validType = "";
        boolean isValid = false;

        while (!isValid) {
            System.out.print("Enter " + typeName + " Type: ");
            String type = scanner.nextLine().trim().toLowerCase();

            if (isValidType(type)) {
                validType = capitalize(type);
                isValid = true;
            } else {
                System.out.println("Invalid type. Please enter a valid Pokémon type.");
            }
        }
        return validType;
    }

    public String inputSecondaryType(String typeName) {
        String result;
        System.out.print("Does this Pokémon have a Secondary Type? (0 = No, 1 = Yes): ");
        String input = scanner.nextLine().trim();

        if (input.equals("1")) {
            result = inputPokemonTyping(typeName);
        } else if (input.equals("0")) {
            result = "";  // No secondary type
        } else {
            System.out.println("Invalid input. Assuming no secondary type.");
            result = "";
        }
        return result;
    }


    private boolean isValidType(String type) {
        String[] validTypes = {
                "normal", "fire", "water", "electric", "grass", "ice",
                "fighting", "poison", "ground", "flying", "psychic", "bug",
                "rock", "ghost", "dragon", "dark", "steel", "fairy"
        };

        boolean found = false;
        for (String validType : validTypes) {
            if (validType.equals(type)) {
                found = true;
                break;
            }
        }
        return found;
    }

    private String capitalize(String s) {
        String result;
        if (s == null || s.isEmpty()) {
            result = s;
        } else {
            result = s.substring(0, 1).toUpperCase() + s.substring(1);
        }
        return result;
    }

    public int inputBaseLevel() {
        int level;

        while (true) {
            System.out.print("Enter Base Level (1 to 70): ");
            String input = scanner.nextLine().trim();
            try {
                int temp = Integer.parseInt(input);
                if (temp < 1 || temp > 70) {
                    System.out.println("Base Level must be in the range 1–70.");
                } else {
                    level = temp;
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
        return level;
    }


    public int inputEvolvesFrom(PokemonManager manager) {
        int number;

        while (true) {
            System.out.print("Enter Pokédex Number this Pokémon evolves from (0 if none): ");
            String input = scanner.nextLine().trim();

            try {
                number = Integer.parseInt(input);

                if (number == 0) {
                    break;
                } else if (number > 0 && manager.doesPokedexNumberExists(number)) {
                    break;
                } else {
                    System.out.println("Invalid Pokédex number. Must be 0 or an existing Pokémon.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
        return number;
    }

    public int inputEvolvesTo(PokemonManager manager) {
        int number;

        while (true) {
            System.out.print("Enter Pokédex Number this Pokémon evolves to (0 if none): ");
            String input = scanner.nextLine().trim();

            try {
                number = Integer.parseInt(input);

                if (number == 0) {
                    break;  // no evolution
                } else if (number > 0 && manager.doesPokedexNumberExists(number)) {
                    break;  // valid Pokémon number, it already exists
                } else {
                    System.out.println("Invalid Pokédex number. Must be 0 or an existing Pokémon.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }

        return number;
    }

    public int inputEvolutionLevel(int baseLevel, PokemonManager manager) {
        int evolutionLevel;
        while (true) {
            System.out.print("Enter Evolution Level (0 if none): ");
            String input = scanner.nextLine().trim();
            try {
                evolutionLevel = Integer.parseInt(input);
                if (manager.isValidEvolutionLevel(baseLevel, evolutionLevel)) {
                    break;
                } else {
                    System.out.println("Evolution level must be 0 or greater than base level and at most 100.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter an integer.");
            }
        }
        return evolutionLevel;
    }
    public int inputBaseStat(String statName) {
        int stat = -1;
        while (true) {
            System.out.print("Enter base " + statName + " (positive integer): ");
            String input = scanner.nextLine().trim();
            try {
                stat = Integer.parseInt(input);
                if (stat > 0) {
                    break;
                } else {
                    System.out.println(statName + " must be a positive integer.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
        return stat;
    }

}
