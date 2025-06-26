package pokemon;

import static utils.DisplayHelper.centerText;
import static utils.DisplayHelper.printCenteredLine;
import static utils.DisplayHelper.repeat;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import pokemon.Pokemon.PokemonEvolutionInfo;

@SuppressWarnings("squid:S106")
public class PokemonManager {

    private final PokemonInputHelper inputHelper;
    private final List<Pokemon> pokemonList;
    private final Scanner scanner;

    public PokemonManager(final Scanner scanner) {
        inputHelper = new PokemonInputHelper(scanner);
        pokemonList = new ArrayList<>();
        this.scanner = scanner;
        // Load test data
        loadTest();
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

        // 1) Gather evolution info
        int evolvesFrom = inputHelper.inputEvolvesFrom(pokedexNumber, this);
        int evolvesTo = inputHelper.inputEvolvesTo(pokedexNumber, this);

        PokemonEvolutionInfo evoInfo;
        if (evolvesTo == PokemonConstants.NO_EVOLUTION) {
            evoInfo = Pokemon.PokemonEvolutionInfo.NONE;
        } else {
            int evolutionLevel = inputHelper.inputEvolutionLevel();
            evoInfo = new Pokemon.PokemonEvolutionInfo(evolvesFrom, evolvesTo, evolutionLevel);
        }

        // 2) Gather stats
        Pokemon.PokemonStats stats = new Pokemon.PokemonStats(
            inputHelper.inputBaseStat("HP"),
            inputHelper.inputBaseStat("Attack"),
            inputHelper.inputBaseStat("Defense"),
            inputHelper.inputBaseStat("Special Attack"),
            inputHelper.inputBaseStat("Special Defense"),
            inputHelper.inputBaseStat("Speed")
        );

        // 3) Create and add the Pokémon
        Pokemon newPokemon = new Pokemon(
            pokedexNumber,
            pokemonName,
            primaryType,
            secondaryType,
            stats,
            evoInfo,
            "" // heldItem
        );

        pokemonList.add(newPokemon);
        System.out.printf("Pokémon added: %s (Pokedex #%03d)%n",
            newPokemon.getName(),
            newPokemon.getPokedexNumber());
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
        boolean taken = false;
        for (Pokemon p : pokemonList) {
            if (p.getName().equalsIgnoreCase(name)) {
                taken = true;
                break;
            }
        }
        return taken;
    }

    public boolean doesPokedexNumberExists(int pokedexNumber) {
        boolean exists = false;

        if (pokedexNumber == 0) {
            exists = true; // 0 means no evolution, valid by default
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

    public void viewAllPokemonAvailable() {
        if (pokemonList.isEmpty()) {
            System.out.println("\nSystem: No Pokémon in the database.");
        } else {
            // Header
            System.out.println(centerText("Pokémon Database", 110));
            String formatHeader = "%-12s %-12s %-15s %-7s %-5s %-8s %-8s %-14s %-14s %-6s\n";
            System.out.printf(formatHeader,
                "Pokedex #", "Name", "Type/s", "Total", "HP", "Attack",
                "Defense", "Sp. Attack", "Sp. Defense", "Speed");
            printCenteredLine(repeat("-", 110));

            // Row format: Poke#, Name, Type(s), Total, HP,  Atk, Def, SpA, SpD, Spe
            String formatRow = "%-12s %-12s %-15s %-7d %-5d %-8d %-8d %-14d %-14d %-6d\n";

            for (Pokemon p : pokemonList) {
                Pokemon.PokemonStats s = p.getPokemonStats();

                int total = s.getHp() +
                    s.getAttack() +
                    s.getDefense() +
                    s.getSpAttack() +
                    s.getSpDefense() +
                    s.getSpeed();

                String types = p.getPrimaryType();
                if (p.getSecondaryType() != null && !p.getSecondaryType().isEmpty()) {
                    types += "/" + p.getSecondaryType();
                }

                System.out.printf(formatRow,
                    String.format("%03d", p.getPokedexNumber()),
                    p.getName(),
                    types,
                    total,
                    s.getHp(),
                    s.getAttack(),
                    s.getDefense(),
                    s.getSpAttack(),
                    s.getSpDefense(),
                    s.getSpeed()
                );
            }
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
            boolean matchesSecondary =
                p.getSecondaryType() != null && p.getSecondaryType().equalsIgnoreCase(type);
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
                break;
            }
        }
        showSearchResults(results, "Pokedex ID: " + String.format("%03d", id));
    }

    public void showSearchResults(List<Pokemon> results, String title) {
        int width = 110;
        if (results.isEmpty()) {
            System.out.println("No Pokémon found for " + title);
            return;
        }

        printCenteredLine(centerText("Results for " + title, width));
        String formatHeader = "%-12s %-12s %-15s %-7s %-5s %-8s %-8s %-14s %-14s %-6s\n";
        System.out.printf(formatHeader,
            "Pokedex #", "Name", "Type/s", "Total", "HP", "Attack",
            "Defense", "Sp. Attack", "Sp. Defense", "Speed");
        printCenteredLine(repeat("-", width));

        String formatRow = "%-12s %-12s %-15s %-7d %-5d %-8d %-8d %-14d %-14d %-6d\n";
        for (Pokemon p : results) {
            Pokemon.PokemonStats s = p.getPokemonStats(); // pull the nested stats object

            int total = s.getHp() +
                s.getAttack() +
                s.getDefense() +
                s.getSpAttack() +
                s.getSpDefense() +
                s.getSpeed();

            String types = p.getPrimaryType();
            if (p.getSecondaryType() != null && !p.getSecondaryType().isEmpty()) {
                types += "/" + p.getSecondaryType();
            }

            System.out.printf(formatRow,
                String.format("%03d", p.getPokedexNumber()),
                p.getName(),
                types,
                total,
                s.getHp(),
                s.getAttack(),
                s.getDefense(),
                s.getSpAttack(),
                s.getSpDefense(),
                s.getSpeed()
            );
        }
    }

    public void loadTest() {
        System.out.println("Loading test Pokémon data...");

        // 5 Starters
        pokemonList.add(new Pokemon(
            1, "Bulbasaur", "Grass", "Poison",
            new Pokemon.PokemonStats(45, 49, 49, 65, 65, 45),
            new Pokemon.PokemonEvolutionInfo(0, 2, 16),
            ""
        ));

        pokemonList.add(new Pokemon(
            4, "Charmander", "Fire", "",
            new Pokemon.PokemonStats(39, 52, 43, 60, 50, 65),
            new Pokemon.PokemonEvolutionInfo(0, 5, 16),
            ""
        ));

        pokemonList.add(new Pokemon(
            7, "Squirtle", "Water", "",
            new Pokemon.PokemonStats(44, 48, 65, 50, 64, 43),
            new Pokemon.PokemonEvolutionInfo(0, 8, 16),
            ""
        ));

        pokemonList.add(new Pokemon(
            152, "Chikorita", "Grass", "",
            new Pokemon.PokemonStats(45, 49, 65, 49, 65, 45),
            new Pokemon.PokemonEvolutionInfo(0, 153, 16),
            ""
        ));

        pokemonList.add(new Pokemon(
            255, "Torchic", "Fire", "",
            new Pokemon.PokemonStats(45, 60, 40, 70, 50, 45),
            new Pokemon.PokemonEvolutionInfo(0, 256, 16),
            ""
        ));

        // 5 Legendaries
        pokemonList.add(new Pokemon(
            150, "Mewtwo", "Psychic", "",
            new Pokemon.PokemonStats(106, 110, 90, 154, 90, 130),
            Pokemon.PokemonEvolutionInfo.NONE,
            ""
        ));

        pokemonList.add(new Pokemon(
            145, "Zapdos", "Electric", "Flying",
            new Pokemon.PokemonStats(90, 90, 85, 125, 90, 100),
            Pokemon.PokemonEvolutionInfo.NONE,
            ""
        ));

        pokemonList.add(new Pokemon(
            144, "Articuno", "Ice", "Flying",
            new Pokemon.PokemonStats(90, 85, 100, 95, 125, 85),
            Pokemon.PokemonEvolutionInfo.NONE,
            ""
        ));

        pokemonList.add(new Pokemon(
            249, "Lugia", "Psychic", "Flying",
            new Pokemon.PokemonStats(106, 90, 130, 90, 154, 110),
            Pokemon.PokemonEvolutionInfo.NONE,
            ""
        ));

        pokemonList.add(new Pokemon(
            384, "Rayquaza", "Dragon", "Flying",
            new Pokemon.PokemonStats(105, 150, 90, 150, 90, 95),
            Pokemon.PokemonEvolutionInfo.NONE,
            ""
        ));

        System.out.println("Test Pokémon data loaded successfully.");
    }

}