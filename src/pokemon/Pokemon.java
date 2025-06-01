package pokemon;

import java.util.ArrayList;
import java.util.List;

import static pokemon.DisplayHelper.*;

public class Pokemon {

    private final int pokedexNumber;
    private String name;
    private String primaryType;
    private String secondaryType; // optional
    private int baseLevel;

    private int evolvesFromNumber;
    private int evolvesToNumber;
    private int evolutionLevel;

    private int baseHp;
    private int baseAttack;
    private int baseDefense;
    private int baseSpeed;

    private ArrayList<String> moveSet = new ArrayList<>();
    private String heldItem;

    public Pokemon(int pokedexNumber, String name, String primaryType, String secondaryType,
                   int baseLevel, int evolvesFromNumber, int evolvesToNumber, int evolutionLevel,
                   int baseHp, int baseAttack, int baseDefense, int baseSpeed, String heldItem) {
        this.pokedexNumber = pokedexNumber;
        this.name = name;
        this.primaryType = primaryType;
        this.secondaryType = secondaryType;
        this.baseLevel = baseLevel;
        this.evolvesFromNumber = evolvesFromNumber;
        this.evolvesToNumber = evolvesToNumber;
        this.evolutionLevel = evolutionLevel;
        this.baseHp = baseHp;
        this.baseAttack = baseAttack;
        this.baseDefense = baseDefense;
        this.baseSpeed = baseSpeed;
        this.heldItem = heldItem;

        this.moveSet = new ArrayList<>();
        // Default moves:
        moveSet.add("Tackle");
        moveSet.add("Defend");
    }

    public int getPokedexNumber() {
        return pokedexNumber;
    }
    public String getName() {
        return name;
    }
    public String getPrimaryType() {
        return primaryType;
    }
    public String getSecondaryType() {
        return secondaryType;
    }
    public int getBaseLevel() {
        return baseLevel;
    }
    public int getEvolvesFromNumber() {
        return evolvesFromNumber;
    }
    public int getEvolvesToNumber() {
        return evolvesToNumber;
    }
    public int getEvolutionLevel() {
        return evolutionLevel;
    }
    public int getBaseHp() {
        return baseHp;
    }
    public int getBaseAttack() {
        return baseAttack;
    }
    public int getBaseDefense() {
        return baseDefense;
    }
    public int getBaseSpeed() {
        return baseSpeed;
    }
    public List<String> getMoveSet() {
        return new ArrayList<>(moveSet);
    }

    public String getHeldItem() {
        return heldItem;
    }

    public void setHeldItem(String heldItem) {
        this.heldItem = heldItem;
    }


    public void displayDetails() {
        final int WIDTH = 80;

        printHeader(WIDTH);
        printGeneralInfo(WIDTH);
        printEvolutionInfo(WIDTH);
        printBaseStats(WIDTH);
        printMoveSet(WIDTH);
    }

    private void printHeader(int width) {
        printCenteredLine(repeat("-", width));
        printCenteredLine(centerText(this.name.toUpperCase(), width));
        printCenteredLine(repeat("-", width));
    }

    private void printGeneralInfo(int width) {
        String[][] generalInfo = {
                {"Pokedex No", String.valueOf(pokedexNumber), "Name", name},
                {"Primary Type", primaryType, "Secondary Type",
                        (secondaryType == null || secondaryType.isEmpty()) ? "None" : secondaryType},
                {"Base Level", String.valueOf(baseLevel), "Held Item",
                        (heldItem == null || heldItem.isEmpty()) ? "None" : heldItem}
        };
        printSection("GENERAL INFO", generalInfo, width);
    }

    private void printEvolutionInfo(int width) {
        String[][] evolutionInfo = {
                {"Evolves From", String.valueOf(evolvesFromNumber), "Evolves To", String.valueOf(evolvesToNumber)},
                {"Evolution Level", String.valueOf(evolutionLevel), "", ""}
        };
        printSection("EVOLUTION INFO", evolutionInfo, width);
    }

    private void printBaseStats(int width) {
        String[][] baseStats = {
                {"HP", String.valueOf(baseHp), "Attack", String.valueOf(baseAttack)},
                {"Defense", String.valueOf(baseDefense), "Speed", String.valueOf(baseSpeed)}
        };
        printSection("BASE STATS", baseStats, width);
    }

    private void printMoveSet(int width) {
        String[][] moveSetFormatted = {
                {"Move 1", getMoveOrNone(0), "Move 2", getMoveOrNone(1)},
                {"Move 3", getMoveOrNone(2), "Move 4", getMoveOrNone(3)}
        };
        printSection("MOVE SET", moveSetFormatted, width);
    }

    private String getMoveOrNone(int index) {
        String result = "None";
        if (index >= 0 && index < moveSet.size()) {
            String move = moveSet.get(index);
            if (move != null && !move.isEmpty()) {
                result = move;
            }
        }
        return result;
    }
}
