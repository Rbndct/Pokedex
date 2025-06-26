package move;

import static utils.DisplayHelper.centerText;
import static utils.DisplayHelper.printCenteredLine;
import static utils.DisplayHelper.repeat;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * The type Move manager.
 */
@SuppressWarnings("squid:S106")
public class MoveManager {

    private static final String MENU_FORMAT = "%-5s %-25s%n";
    private final List<Move> moveList;
    private final MoveInputHelper inputHelper;
    private final Scanner scanner;


    /**
     * Instantiates a new Move manager.
     *
     * @param scanner the scanner
     */
    public MoveManager(final Scanner scanner) {
        inputHelper = new MoveInputHelper(scanner);
        moveList = new ArrayList<>();
        this.scanner = scanner;
    }

    /**
     * Add move.
     */
    public void addMove() {

        String name = inputHelper.inputMoveName(this);
        String description = inputHelper.inputMoveDescription();
        Move.Classification classification = inputHelper.inputMoveClassification();
        String primaryType = inputHelper.inputMoveTyping("primary");
        String secondaryType;

        while (true) {
            secondaryType = inputHelper.inputSecondaryMoveType("secondary");
            if (secondaryType.equalsIgnoreCase(primaryType)) {
                System.out.println("Secondary type must be different from primary type.");
            } else {
                break;
            }
        }

        Move newMove = new Move(name, description, classification, primaryType, secondaryType);

        moveList.add(newMove);

        System.out.println("Move added: " + newMove.getName() +
            " (" + newMove.getClassification() + ")");
    }

    /**
     * Is move name taken boolean.
     *
     * @param name the name
     *
     * @return the boolean
     */
    public boolean isMoveNameTaken(String name) {
        boolean taken = false;
        for (Move m : moveList) {
            if (m.getName().equalsIgnoreCase(name)) {
                taken = true;
                break;
            }
        }
        return taken;
    }

    /**
     * View all moves available.
     */
    public void viewAllMovesAvailable() {
        if (moveList.isEmpty()) {
            System.out.println("\nSystem: No moves in the database.");
        } else {
            System.out.println(centerText("Move Database", 110));
            String formatHeader = "%-20s %-10s %-15s %-55s\n";
            System.out.printf(formatHeader, "Move Name", "Class", "Type(s)", "Description");
            printCenteredLine(repeat("-", 110));

            for (Move m : moveList) {
                m.display();
            }
        }
    }


    /**
     * Handle move search.
     */
    public void handleMoveSearch() {
        System.out.println("\n" + centerText("--- Search Pokémon Moves ---", 35));
        System.out.printf(MENU_FORMAT, "1.", "By Name or Effect");
        System.out.printf(MENU_FORMAT, "2.", "By Type");
        System.out.printf(MENU_FORMAT, "3.", "By Classification (HM/TM)");
        System.out.printf("%-5s", "");
        System.out.print("Enter option: ");

        String option = scanner.nextLine().trim();

        switch (option) {
            case "1":
                System.out.print("Enter keyword (name or effect): ");
                String keyword = scanner.nextLine().trim();
                searchByNameOrEffect(keyword);
                break;
            case "2":
                System.out.print("Enter type (e.g., Fire, Water): ");
                String type = scanner.nextLine().trim();
                searchByType(type);
                break;
            case "3":
                System.out.print("Enter classification (HM or TM): ");
                String classInput = scanner.nextLine().trim().toUpperCase();
                try {
                    Move.Classification classification = Move.Classification.valueOf(classInput);
                    searchByClassification(classification);
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid classification. Please enter HM or TM.");
                }
                break;
            default:
                System.out.println("Invalid option.");
        }
    }

    /**
     * Search by name or effect.
     *
     * @param keyword the keyword
     */
    public void searchByNameOrEffect(String keyword) {
        List<Move> results = new ArrayList<>();
        for (Move m : moveList) {
            boolean nameMatch = m.getName().toLowerCase().contains(keyword.toLowerCase());
            boolean descriptionMatch = m.getDescription().toLowerCase()
                .contains(keyword.toLowerCase());

            if (nameMatch || descriptionMatch) {
                results.add(m);
            }
        }
        showSearchResults(results, "Keyword: " + keyword);
    }

    /**
     * Search by type.
     *
     * @param type the type
     */
    public void searchByType(String type) {
        List<Move> results = new ArrayList<>();
        for (Move m : moveList) {
            boolean matchesPrimary = m.getPrimaryType().equalsIgnoreCase(type);
            boolean matchesSecondary =
                m.getSecondaryType() != null && m.getSecondaryType().equalsIgnoreCase(type);
            if (matchesPrimary || matchesSecondary) {
                results.add(m);
            }
        }
        showSearchResults(results, "Type: " + type);
    }

    /**
     * Search by classification.
     *
     * @param classification the classification
     */
    public void searchByClassification(Move.Classification classification) {
        List<Move> results = new ArrayList<>();
        for (Move m : moveList) {
            if (m.getClassification() == classification) {
                results.add(m);
            }
        }
        showSearchResults(results, "Classification: " + classification);
    }

    /**
     * Show search results.
     *
     * @param results the results
     * @param title   the title
     */
    public void showSearchResults(List<Move> results, String title) {
        int width = 100;

        if (results.isEmpty()) {
            System.out.println("No moves found for " + title);
            return;
        }

        printCenteredLine(centerText("Results for " + title, width));
        String formatHeader = "%-20s %-10s %-15s %-55s\n";
        System.out.printf(formatHeader, "Move Name", "Class", "Type(s)", "Description");
        printCenteredLine(repeat("-", width));

        for (Move m : results) {
            m.display();
        }
    }


    /**
     * Load default moves.
     */
    public void loadDefaultMoves() {
        moveList.add(new Move("Tackle",
                "Tackle is one of the most common and basic moves a Pokémon learns. It deals damage with no additional effects.",
                Move.Classification.TM, "Normal", ""));
        moveList.add(new Move("Defend",
                "Raises user's defense stat temporarily.",
                Move.Classification.TM, "Normal", ""));
        moveList.add(new Move("Cut",
                "A basic HM move that can be used to cut down small trees.",
                Move.Classification.HM, "Normal", ""));
        moveList.add(new Move("Surf",
                "Ride a huge wave to strike all targets. Can cross water.",
                Move.Classification.HM, "Water", ""));
        moveList.add(new Move("Fly",
                "Soar up and strike on the next turn. Also travels between towns.",
                Move.Classification.HM, "Flying", ""));
        moveList.add(new Move("Flamethrower",
                "A powerful blast of fire.",
                Move.Classification.TM, "Fire", ""));
        moveList.add(new Move("Ice Beam",
                "Blasts a freezing beam that may freeze the target.",
                Move.Classification.TM, "Ice", ""));
        moveList.add(new Move("Thunderbolt",
                "A strong electric blast crashes down on the target.",
                Move.Classification.TM, "Electric", ""));
    }

}