package ui;

import static utils.DisplayHelper.centerText;

import item.ItemManager;
import java.util.Scanner;
import move.MoveManager;
import pokemon.PokemonManager;

public class Menu {

    private final PokemonManager pokemonManager;
    private final MoveManager moveManager;
    private final ItemManager itemManager;
    private final Scanner scanner;

    public Menu(PokemonManager pokemonManager, MoveManager moveManager, ItemManager itemManager,
        Scanner scanner) {
        this.pokemonManager = pokemonManager;
        this.moveManager = moveManager;
        this.itemManager = itemManager;
        this.scanner = scanner;
    }

    public void display() {
        int choice = -1;
        while (choice != 9) {
            printMenu();

            System.out.print("Enter choice: ");
            String input = scanner.nextLine().trim();

            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    pokemonManager.addPokemon();
                    break;
                case 2:
                    pokemonManager.viewAllPokemonAvailable();
                    break;
                case 3:
                    pokemonManager.handleSearch();
                    break;
                case 4:
                    moveManager.addMove();
                    break;
                case 5:
                    moveManager.viewAllMovesAvailable();
                    break;
                case 6:
                    moveManager.handleMoveSearch();
                    break;
                case 7:
                    itemManager.viewAllItemsAvailable();
                    break;
                case 8:
                    itemManager.handleItemSearch();
                    break;
                case 9:
                    System.out.println("\nThank you for using the Pokémon Database. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void printMenu() {
        System.out.println("\n" + centerText("Pokémon Database Menu", 30));
        System.out.printf("%-5s %-25s%n", "1.", "Add Pokémon");
        System.out.printf("%-5s %-25s%n", "2.", "View All Pokémon");
        System.out.printf("%-5s %-25s%n", "3.", "Search Pokémon");

        System.out.printf("%-5s %-25s%n", "4.", "Add Move");
        System.out.printf("%-5s %-25s%n", "5.", "View all Moves");
        System.out.printf("%-5s %-25s%n", "6.", "Search Moves");

        System.out.printf("%-5s %-25s%n", "7.", "View all items");
        System.out.printf("%-5s %-25s%n", "8.", "Search Items");

        System.out.printf("%-5s %-25s%n", "9.", "Exit");
    }
}
