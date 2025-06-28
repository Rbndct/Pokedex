package app;

import item.ItemManager;
import java.util.Scanner;
import move.MoveManager;
import pokemon.PokemonManager;
import ui.Menu;

/**
 * The type Main.
 */
public class Main {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PokemonManager pokemonManager = new PokemonManager(scanner);
        MoveManager moveManager = new MoveManager(scanner);
        ItemManager itemManager = new ItemManager(scanner);
        moveManager.loadDefaultMoves();

        Menu menu = new Menu(pokemonManager, moveManager, itemManager, scanner);
        menu.display();
    }
}