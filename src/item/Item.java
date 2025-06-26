package item;

import utils.DisplayHelper;
import java.util.List;

/**
 * The type Item.
 */
@SuppressWarnings("squid:S106")
public class Item {

    private final int buyingPrice;
    private final String name;
    private final String category;
    private final String description;
    private final String effect;
    private final int sellingPrice;
    private int stock;

    /**
     * Instantiates a new Item.
     *
     * @param name         the name
     * @param category     the category
     * @param description  the description
     * @param effect       the effect
     * @param buyingPrice  the buying price
     * @param sellingPrice the selling price
     * @param stock        the stock
     */
    public Item(String name, String category, String description, String effect,
                int buyingPrice, int sellingPrice, int stock) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.effect = effect;
        this.buyingPrice = buyingPrice;
        this.sellingPrice = sellingPrice;
        this.stock = stock;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets category.
     *
     * @return the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets effect.
     *
     * @return the effect
     */
    public String getEffect() {
        return effect;
    }

    /**
     * Gets buying price.
     *
     * @return the buying price
     */
    public int getBuyingPrice() {
        return buyingPrice;
    }

    /**
     * Gets selling price.
     *
     * @return the selling price
     */
    public int getSellingPrice() {
        return sellingPrice;
    }

    /**
     * Gets stock.
     *
     * @return the stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * Sets stock.
     *
     * @param stock the stock
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Display.
     */
    public void display() {
        String formatRow = "%-25s %-20s %-35s\n";
        List<String> lines = DisplayHelper.wrapText(effect, 35);

        // Print first line with all columns
        System.out.printf(formatRow, name, category, lines.getFirst());

        // Print remaining wrapped lines of the effect
        for (int i = 1; i < lines.size(); i++) {
            System.out.printf(formatRow, "", "", lines.get(i));
        }
    }
}
