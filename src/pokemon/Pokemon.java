package pokemon;

import java.util.ArrayList;
import java.util.List;

public class Pokemon {

    private final int pokedexNumber;

    private final PokemonStats pokemonStats;
    private final PokemonEvolutionInfo pokemonEvolutionInfo;
    private final String name;
    private final String primaryType;
    private final String secondaryType; // optional
    private final int baseLevel;
    private ArrayList<String> moveSet = new ArrayList<>();
    private String heldItem;

    public Pokemon(int pokedexNumber, String name, String primaryType, String secondaryType,
        PokemonStats pokemonStats,
        PokemonEvolutionInfo pokemonEvolutionInfo,
        String heldItem) {
        this.pokedexNumber = pokedexNumber;
        this.name = name;
        this.primaryType = primaryType;
        this.secondaryType = secondaryType;
        this.baseLevel = PokemonConstants.DEFAULT_BASE_LEVEL;
        this.pokemonStats = pokemonStats;
        this.pokemonEvolutionInfo = pokemonEvolutionInfo;
        this.heldItem = heldItem;

        this.moveSet = new ArrayList<>();
        // Default moves:
        addDefaultMoves();

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

    public PokemonStats getPokemonStats() {
        return pokemonStats;
    }

    public PokemonEvolutionInfo getPokemonEvolutionInfo() {
        return pokemonEvolutionInfo;
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

    //    public void displayDetails() {
    //
    //        printHeader();
    //        printGeneralInfo();
    //        printEvolutionInfo();
    //        printBaseStats();
    //        printMoveSet();
    //    }
    //
    //    private void printHeader() {
    //        printCenteredLine(repeat("-", 80));
    //        printCenteredLine(centerText(this.name.toUpperCase(), 80));
    //        printCenteredLine(repeat("-", 80));
    //    }
    //
    //    private void printGeneralInfo() {
    //        String[][] generalInfo = {
    //            {"Pokedex No", String.valueOf(pokedexNumber), "Name", name},
    //            {"Primary Type", primaryType, "Secondary Type",
    //                (secondaryType == null || secondaryType.isEmpty()) ? "None" : secondaryType},
    //            {"Base Level", String.valueOf(baseLevel), "Held Item",
    //                (heldItem == null || heldItem.isEmpty()) ? "None" : heldItem}
    //        };
    //        printSection("GENERAL INFO", generalInfo, 80);
    //    }
    //
    //    private void printEvolutionInfo() {
    //        String[][] evolutionInfo = {
    //            {"Evolves From", String.valueOf(evolvesFromNumber), "Evolves To",
    //                String.valueOf(evolvesToNumber)},
    //            {"Evolution Level", String.valueOf(evolutionLevel), "", ""}
    //        };
    //        printSection("EVOLUTION INFO", evolutionInfo, 80);
    //    }
    //
    //    private void printBaseStats() {
    //        String[][] baseStats = {
    //            {"HP", String.valueOf(baseHp), "Attack", String.valueOf(baseAttack)},
    //            {"Defense", String.valueOf(baseDefense), "Speed", String.valueOf(baseSpeed)}
    //        };
    //        printSection("BASE STATS", baseStats, 80);
    //    }
    //
    //    private void printMoveSet() {
    //        String[][] moveSetFormatted = {
    //            {"Move 1", getMoveOrNone(0), "Move 2", getMoveOrNone(1)},
    //            {"Move 3", getMoveOrNone(2), "Move 4", getMoveOrNone(3)}
    //        };
    //        printSection("MOVE SET", moveSetFormatted, 80);
    //    }
    //
    //    private String getMoveOrNone(int index) {
    //        String result = "None";
    //        if (index >= 0 && index < moveSet.size()) {
    //            String move = moveSet.get(index);
    //            if (move != null && !move.isEmpty()) {
    //                result = move;
    //            }
    //        }
    //        return result;
    //    }

    private void addDefaultMoves() {
        moveSet.add("Tackle");
        moveSet.add("Defend");
    }

    public static class PokemonStats {

        private final int hp;
        private final int attack;
        private final int defense;
        private final int spAttack;
        private final int spDefense;
        private final int speed;

        public PokemonStats(int hp, int attack, int defense, int spAttack, int spDefense,
            int speed) {
            this.hp = hp;
            this.attack = attack;
            this.defense = defense;
            this.spAttack = spAttack;
            this.spDefense = spDefense;
            this.speed = speed;
        }

        public int getHp() {
            return hp;
        }

        public int getAttack() {
            return attack;
        }

        public int getDefense() {
            return defense;
        }

        public int getSpAttack() {
            return spAttack;
        }

        public int getSpDefense() {
            return spDefense;
        }

        public int getSpeed() {
            return speed;
        }
    }

    public static class PokemonEvolutionInfo {

        public static final PokemonEvolutionInfo NONE = new PokemonEvolutionInfo(
            PokemonConstants.NO_EVOLUTION,
            PokemonConstants.NO_EVOLUTION,
            PokemonConstants.NO_EVOLUTION
        );

        private final int evolvesFromNumber;
        private final int evolvesToNumber;
        private final int evolutionLevel;

        public PokemonEvolutionInfo(int evolvesFromNumber, int evolvesToNumber,
            int evolutionLevel) {
            this.evolvesFromNumber = evolvesFromNumber;
            this.evolvesToNumber = evolvesToNumber;
            this.evolutionLevel = evolutionLevel;
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
    }
}