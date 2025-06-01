package app;

import pokemon.PokemonManager;
import ui.Menu;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PokemonManager manager = new PokemonManager(scanner); // assuming your manager accepts a Scanner
        Menu menu = new Menu(manager, scanner);
        menu.display();
    }
}
