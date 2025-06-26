package item;

public class Item {

    private final int buyingPrice;
    private final String name;
    private final String category;
    private final String description;
    private final String effect;
    private final int sellingPrice;
    private int stock;

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

    // Getters
    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public String getEffect() {
        return effect;
    }

    public int getBuyingPrice() {
        return buyingPrice;
    }

    public int getSellingPrice() {
        return sellingPrice;
    }

    public int getStock() {
        return stock;
    }

    // Setters
    public void setStock(int stock) {
        this.stock = stock;
    }

}