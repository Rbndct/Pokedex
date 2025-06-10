package move;


import static utils.DisplayHelper.centerText;
import static utils.DisplayHelper.printCenteredLine;
import static utils.DisplayHelper.repeat;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import utils.DisplayHelper;

public class MoveManager {

    private final List<Move> moveList;
    private final MoveInputHelper inputHelper;
    private final Scanner scanner;

    public MoveManager(final Scanner scanner) {
        inputHelper = new MoveInputHelper(scanner);
        moveList = new ArrayList<>();
        this.scanner = scanner;
    }

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

    public void viewAllMovesAvailable() {
        if (moveList.isEmpty()) {
            System.out.println("\nSystem: No moves in the database.");
        } else {
            // Header
            System.out.println(centerText("Move Database", 110));
            String formatHeader = "%-20s %-10s %-15s %-55s\n";
            System.out.printf(formatHeader, "Move Name", "Class", "Type(s)", "Description");
            printCenteredLine(repeat("-", 110));

            // Row format
            String formatRow = "%-20s %-10s %-15s %-55s\n";
            for (Move m : moveList) {
                String types = m.getPrimaryType();
                if (m.getSecondaryType() != null && !m.getSecondaryType().isEmpty()) {
                    types += "/" + m.getSecondaryType();
                }

                List<String> descriptionLines = DisplayHelper.wrapText(m.getDescription(), 60);

                // Print first line with all columns
                System.out.printf(formatRow,
                    m.getName(),
                    m.getClassification(),
                    types,
                    descriptionLines.get(0));

                // Print remaining description lines (if any)
                for (int i = 1; i < descriptionLines.size(); i++) {
                    System.out.printf(formatRow, "", "", "", descriptionLines.get(i));
                }
            }
        }
    }


    public void handleMoveSearch() {
        System.out.println("\n" + centerText("--- Search Pokémon Moves ---", 35));
        System.out.printf("%-5s %-25s%n", "1.", "By Name or Effect");
        System.out.printf("%-5s %-25s%n", "2.", "By Type");
        System.out.printf("%-5s %-25s%n", "3.", "By Classification (HM/TM)");
        System.out.printf("%-5s", ""); // Align the input with options
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

    public void searchByClassification(Move.Classification classification) {
        List<Move> results = new ArrayList<>();
        for (Move m : moveList) {
            if (m.getClassification() == classification) {
                results.add(m);
            }
        }
        showSearchResults(results, "Classification: " + classification);
    }

    public void showSearchResults(List<Move> results, String title) {
        int width = 60;

        if (results.isEmpty()) {
            System.out.println("No moves found for " + title);
            return;
        }

        printCenteredLine(centerText("Results for " + title, width));
        String formatHeader = "%-20s %-10s %-20s\n";
        System.out.printf(formatHeader, "Move Name", "Class", "Type(s)");
        printCenteredLine(repeat("-", width));

        String formatRow = "%-20s %-10s %-20s\n";

        for (Move m : results) {
            String types = m.getPrimaryType();
            if (m.getSecondaryType() != null && !m.getSecondaryType().isEmpty()) {
                types += "/" + m.getSecondaryType();
            }

            System.out.printf(formatRow,
                m.getName(),
                m.getClassification(),
                types);
        }
    }

    public void loadDefaultMoves() {
        Move tackle = new Move("Tackle",
            "Tackle is one of the most common and basic moves a Pokémon learns. It deals damage with no additional effects.",
            Move.Classification.TM, "Normal", "");
        Move defend = new Move("Defend", "Raises user's defense stat temporarily.",
            Move.Classification.TM, "Normal", "");

        moveList.add(tackle);
        moveList.add(defend);
    }
}


