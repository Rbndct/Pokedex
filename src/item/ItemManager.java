package item;

import static utils.DisplayHelper.centerText;
import static utils.DisplayHelper.printCenteredLine;
import static utils.DisplayHelper.repeat;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ItemManager {

    private final Scanner scanner;
    private final List<Item> itemList;

    public ItemManager(final Scanner scanner) {
        itemList = new ArrayList<>();
        populateInitialItems();
        this.scanner = scanner;
    }

    public void populateInitialItems() {
        // Vitamins (boost EVs)
        itemList.add(
            new Item("Calcium", "Vitamin", "A nutritious drink for Pokémon.",
                "+10 Special Attack EVs",
                10000, 5000, 10));
        itemList.add(
            new Item("Carbos", "Vitamin", "A nutritious drink for Pokémon.", "+10 Speed EVs", 10000,
                5000, 10));
        itemList.add(
            new Item("HP Up", "Vitamin", "A nutritious drink for Pokémon.", "+10 HP EVs", 10000,
                5000,
                10));
        itemList.add(
            new Item("Iron", "Vitamin", "A nutritious drink for Pokémon.", "+10 Defense EVs", 10000,
                5000, 10));
        itemList.add(
            new Item("Protein", "Vitamin", "A nutritious drink for Pokémon.", "+10 Attack EVs",
                10000,
                5000, 10));
        itemList.add(
            new Item("Zinc", "Vitamin", "A nutritious drink for Pokémon.",
                "+10 Special Defense EVs",
                10000, 5000, 10));

        // Feathers (slightly increase EVs)
        itemList.add(
            new Item("Health Feather", "Feather", "Slightly increases HP.", "+1 HP EV", 300, 150,
                10));
        itemList.add(
            new Item("Muscle Feather", "Feather", "Slightly increases Attack.", "+1 Attack EV", 300,
                150, 10));
        itemList.add(
            new Item("Resist Feather", "Feather", "Slightly increases Defense.", "+1 Defense EV",
                300,
                150, 10));
        itemList.add(
            new Item("Swift Feather", "Feather", "Slightly increases Speed.", "+1 Speed EV", 300,
                150,
                10));
        itemList.add(new Item("Genius Feather", "Feather", "Slightly increases Special Attack.",
            "+1 Special Attack EV", 300, 150, 10));
        itemList.add(new Item("Clever Feather", "Feather", "Slightly increases Special Defense.",
            "+1 Special Defense EV", 300, 150, 10));

        // Other
        itemList.add(new Item("Rare Candy", "Leveling Item", "A candy packed with energy.",
            "Increases level by 1", -1, 2400, 10));

        // Evolution Stones
        itemList.add(new Item("Fire Stone", "Evolution Stone", "Radiates heat.",
            "Evolves Vulpix, Growlithe, Eevee, etc.", 3000, 1500, 10));

        itemList.add(new Item("Water Stone", "Evolution Stone", "Blue, watery appearance.",
            "Evolves Poliwhirl, Shellder, Eevee, etc.", 3000, 1500, 10));

        itemList.add(new Item("Thunder Stone", "Evolution Stone", "Sparkles with electricity.",
            "Evolves Pikachu, Eelektrik, Eevee, etc.", 3000, 1500, 10));

        itemList.add(new Item("Leaf Stone", "Evolution Stone", "Leaf pattern.",
            "Evolves Gloom, Weepinbell, Exeggcute etc.", 3000, 1500, 10));

        itemList.add(new Item("Moon Stone", "Evolution Stone", "Glows faintly.",
            "Evolves Nidorina, Clefairy, Jigglypuff, etc.", -1, 1500, 10));

        itemList.add(new Item("Sun Stone", "Evolution Stone", "Glows like the sun.",
            "Evolves Gloom, Sunkern, Cottonee, etc.", 3000, 1500, 10));

        itemList.add(new Item("Shiny Stone", "Evolution Stone", "Sparkles brightly.",
            "Evolves Togetic, Roselia, Minccino, etc.", 3000, 1500, 10));

        itemList.add(new Item("Dusk Stone", "Evolution Stone", "Ominous appearance.",
            "Evolves Murkrow, Misdreavus, Doublade, etc.", 3000, 1500, 10));

        itemList.add(new Item("Dawn Stone", "Evolution Stone", "Sparkles like the morning sky.",
            "Evolves male Kirlia and female Snorunt.", 3000, 1500, 10));

        itemList.add(new Item("Ice Stone", "Evolution Stone", "Cold to the touch.",
            "Evolves Alolan Vulpix, etc.", 3000, 1500, 10));

    }

    public void viewAllItemsAvailable() {
        if (itemList.isEmpty()) {
            System.out.println("\nSystem: No items in the database.");
        } else {
            // Header
            System.out.println(centerText("Item Database", 80));
            String formatHeader = "%-20s %-20s %-45s\n";
            System.out.printf(formatHeader, "Item Name", "Category", "Effect");
            printCenteredLine(repeat("-", 80));

            // Row format
            String formatRow = "%-20s %-20s %-45s\n";
            for (Item item : itemList) {
                System.out.printf(formatRow,
                    item.getName(),
                    item.getCategory(),
                    item.getEffect());
            }
        }
    }

    public void handleItemSearch() {
        System.out.println("\n" + centerText("--- Search Pokémon Items ---", 35));
        System.out.printf("%-5s %-30s%n", "1.", "By Name or Effect");
        System.out.printf("%-5s %-30s%n", "2.", "By Category (e.g., Vitamin, Feather)");
        System.out.printf("%-5s", ""); // Align input
        System.out.print("Enter option: ");

        String option = scanner.nextLine().trim();

        switch (option) {
            case "1":
                System.out.print("Enter keyword (name or effect): ");
                String keyword = scanner.nextLine().trim();
                searchItemsByNameOrEffect(keyword);
                break;

            case "2":
                System.out.print(
                    "Enter category (Vitamin, Feather, Leveling Item, Evolution Stone): ");
                String category = scanner.nextLine().trim();
                searchByCategory(category);
                break;

            default:
                System.out.println("Invalid option.");
        }
    }

    public void searchItemsByNameOrEffect(String keyword) {
        List<Item> results = new ArrayList<>();
        String lowerKeyword = keyword.toLowerCase();

        for (Item item : itemList) {
            boolean nameMatch = item.getName().toLowerCase().contains(lowerKeyword);
            boolean effectMatch = item.getEffect().toLowerCase().contains(lowerKeyword);

            if (nameMatch || effectMatch) {
                results.add(item);
            }
        }
        showItemSearchResults(results, "Name/Effect contains: " + keyword);
    }

    public void searchByCategory(String category) {
        List<Item> results = new ArrayList<>();
        for (Item item : itemList) {
            if (item.getCategory().equalsIgnoreCase(category)) {
                results.add(item);
            }
        }
        showItemSearchResults(results, "Category: " + category);
    }

    private void showItemSearchResults(List<Item> results, String title) {
        if (results.isEmpty()) {
            System.out.println("No items found for " + title);
            return;
        }

        System.out.println(centerText("Search Results for " + title, 80));
        String formatHeader = "%-25s %-20s %-35s\n";
        System.out.printf(formatHeader, "Item Name", "Category", "Effect");
        printCenteredLine(repeat("-", 80));

        String formatRow = "%-25s %-20s %-35s\n";
        for (Item item : results) {
            System.out.printf(formatRow,
                item.getName(),
                item.getCategory(),
                item.getEffect());
        }
    }

}