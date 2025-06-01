package pokemon;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import static pokemon.DisplayHelper.*;


public class PokemonManager {
    private final PokemonInputHelper inputHelper;
    private final List<Pokemon> pokemonList;
    private final Scanner scanner;

    public PokemonManager(final Scanner scanner) {
        inputHelper = new PokemonInputHelper(scanner);
        pokemonList = new ArrayList<>();
        this.scanner = scanner;
    }

    public void addPokemon() {
        int pokedexNumber = inputHelper.inputPokedexNumber(this);
        String pokemonName = inputHelper.inputPokemonName(this);
        String primaryType = inputHelper.inputPokemonTyping("primary");

        String secondaryType;
        while (true) {
            secondaryType = inputHelper.inputSecondaryType("secondary");
            if (secondaryType.equalsIgnoreCase(primaryType)) {
                System.out.println("Secondary type must be different from primary type.");
            } else {
                break;
            }
        }



        int baseLevel = inputHelper.inputBaseLevel();
        int evolvesFromNumber = inputHelper.inputEvolvesFrom(this);
        int evolvesToNumber = inputHelper.inputEvolvesTo(this);


        int evolutionLevel = inputHelper.inputEvolutionLevel( baseLevel, this);

        int baseHp = inputHelper.inputBaseStat("HP");
        int baseAttack = inputHelper.inputBaseStat("Attack");
        int baseDefense = inputHelper.inputBaseStat("Defense");
        int baseSpeed = inputHelper.inputBaseStat("Speed");

        Pokemon newPokemon = new Pokemon(pokedexNumber,
                pokemonName,
                primaryType,
                secondaryType, baseLevel, evolvesFromNumber, evolvesToNumber,evolutionLevel, baseHp, baseAttack, baseDefense, baseSpeed, "");

        pokemonList.add(newPokemon);
        System.out.println("Pokémon added: " + newPokemon.getName() +
                " (Pokedex Number: " + newPokemon.getPokedexNumber() + ")");
    }

    public boolean isPokedexNumberUnique(int inputNumber) {
        boolean isTaken = false;

        for (Pokemon p : pokemonList) {
            if (p.getPokedexNumber() == inputNumber) {
                isTaken = true;
                break;
            }
        }
        return isTaken;
    }

    public boolean isPokemonNameTaken(String name) {
        for (Pokemon p : pokemonList) {
            if (p.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    public boolean doesPokedexNumberExists(int pokedexNumber) {
        boolean exists = false;

        if (pokedexNumber == 0) {
            exists = true;  // 0 means no evolution, valid by default
        } else {
            for (Pokemon p : pokemonList) {
                if (p.getPokedexNumber() == pokedexNumber) {
                    exists = true;
                    break;
                }
            }
        }
        return exists;
    }

    // Checks if evolution level is valid based on baseLevel
    public boolean isValidEvolutionLevel(int baseLevel, int evolutionLevel) {
        boolean isValid = false;

        if (evolutionLevel == 0) {
            // 0 means no evolution level required, so valid
            isValid = true;
        } else if (evolutionLevel > baseLevel && evolutionLevel <= 100) {
            // Evolution level must be greater than baseLevel and <= 100 (max level)
            isValid = true;
        }

        return isValid;
    }

    public void viewAllPokemonAvailable() {
        if (pokemonList.isEmpty()) {
            System.out.println("\nSystem: No Pokémon in the database.");
            return;
        }

        System.out.println(centerText("Pokémon Database", 80));

        // Header
        String formatHeader = "%-12s %-12s %-15s %-7s %-5s %-8s %-8s %-6s\n";
        System.out.printf(formatHeader, "Pokedex #", "Name", "Type/s", "Total", "HP", "Attack", "Defense", "Speed");
        printCenteredLine(repeat("-", 80));

        // Row format
        String formatRow = "%-12s %-12s %-15s %-7d %-5d %-8d %-8d %-6d\n";
        for (Pokemon p : pokemonList) {
            int total = p.getBaseHp() + p.getBaseAttack() + p.getBaseDefense() + p.getBaseSpeed();
            String types = p.getPrimaryType();
            if (p.getSecondaryType() != null && !p.getSecondaryType().isEmpty()) {
                types += "/" + p.getSecondaryType();
            }

            System.out.printf(formatRow,
                    String.format("%03d", p.getPokedexNumber()),
                    p.getName(),
                    types,
                    total,
                    p.getBaseHp(),
                    p.getBaseAttack(),
                    p.getBaseDefense(),
                    p.getBaseSpeed());
        }
    }

    public void handleSearch() {
        System.out.println("\n" + centerText("--- Search Pokémon ---", 35));
        System.out.printf("%-5s %-25s%n", "1.", "By Name");
        System.out.printf("%-5s %-25s%n", "2.", "By Type");
        System.out.printf("%-5s %-25s%n", "3.", "By Pokedex ID");
        System.out.printf("%-5s", ""); // Align the input with options
        System.out.print("Enter option: ");

        String option = scanner.nextLine().trim();

        switch (option) {
            case "1":
                System.out.print("Enter name: ");
                String name = scanner.nextLine().trim();
                searchByName(name);
                break;
            case "2":
                System.out.print("Enter type: ");
                String type = scanner.nextLine().trim();
                searchByType(type);
                break;
            case "3":
                System.out.print("Enter Pokedex ID: ");
                String idStr = scanner.nextLine().trim();
                try {
                    int id = Integer.parseInt(idStr);
                    searchByPokedexId(id);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid number.");
                }
                break;
            default:
                System.out.println("Invalid option.");
        }
    }

    public void searchByName(String name) {
        List<Pokemon> results = new ArrayList<>();
        for (Pokemon p : pokemonList) {
            if (p.getName().toLowerCase().contains(name.toLowerCase())) {
                results.add(p);
            }
        }
        showSearchResults(results, "Name: " + name);
    }

    public void searchByType(String type) {
        List<Pokemon> results = new ArrayList<>();
        for (Pokemon p : pokemonList) {
            boolean matchesPrimary = p.getPrimaryType().equalsIgnoreCase(type);
            boolean matchesSecondary = p.getSecondaryType() != null && p.getSecondaryType().equalsIgnoreCase(type);
            if (matchesPrimary || matchesSecondary) {
                results.add(p);
            }
        }
        showSearchResults(results, "Type: " + type);
    }

    public void searchByPokedexId(int id) {
        List<Pokemon> results = new ArrayList<>();
        for (Pokemon p : pokemonList) {
            if (p.getPokedexNumber() == id) {
                results.add(p);
                break; // IDs are unique
            }
        }
        showSearchResults(results, "Pokedex ID: " + String.format("%03d", id));
    }



    public void showSearchResults(List<Pokemon> results, String title) {
        int width = 76;
        if (results.isEmpty()) {
            System.out.println("No Pokémon found for " + title);
            return;
        }

        printCenteredLine(centerText("Results for " + title, width));
        String formatHeader = "%-12s %-12s %-15s %-7s %-5s %-8s %-8s %-6s\n";
        System.out.printf(formatHeader, "Pokedex #", "Name", "Type/s", "Total", "HP", "Attack", "Defense", "Speed");
        printCenteredLine(repeat("-", width));

        String formatRow = "%-12s %-12s %-15s %-7d %-5d %-8d %-8d %-6d\n";

        for (Pokemon p : results) {
            int total = p.getBaseHp() + p.getBaseAttack() + p.getBaseDefense() + p.getBaseSpeed();
            String types = p.getPrimaryType();
            if (p.getSecondaryType() != null && !p.getSecondaryType().isEmpty()) {
                types += "/" + p.getSecondaryType();
            }

            System.out.printf(formatRow,
                    String.format("%03d", p.getPokedexNumber()),
                    p.getName(),
                    types,
                    total,
                    p.getBaseHp(),
                    p.getBaseAttack(),
                    p.getBaseDefense(),
                    p.getBaseSpeed());
        }
    }





}
