package ui;

import pokemon.PokemonManager;
import java.util.Scanner;
import static pokemon.DisplayHelper.*;


public class Menu {
    private final PokemonManager manager;
    private final Scanner scanner;

    public Menu(PokemonManager manager, Scanner scanner) {
        this.manager = manager;
        this.scanner = scanner;
    }

    public void display() {
        int choice = -1;
        while (choice != 4) {

            System.out.println("\n" + centerText("Pokémon Database Menu", 30));
            System.out.printf("%-5s %-25s%n", "1.", "Add Pokémon");
            System.out.printf("%-5s %-25s%n", "2.", "View All Pokémon");
            System.out.printf("%-5s %-25s%n", "3.", "Search Pokémon");
            System.out.printf("%-5s %-25s%n", "4.", "Exit");

            System.out.printf("%-6sEnter choice: ", " ");


            String input = scanner.nextLine().trim();
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    manager.addPokemon();
                    break;
                case 2:
                    manager.viewAllPokemonAvailable();
                    break;
                case 3:
                    manager.handleSearch();
                    break;
                case 4:
                    System.out.printf("%n%-6sSystem: Exiting...", " ");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
