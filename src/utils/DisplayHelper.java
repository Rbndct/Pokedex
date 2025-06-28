package utils;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Display helper.
 */
public class DisplayHelper {

    private DisplayHelper() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Print section.
     *
     * @param title the title
     * @param rows  the rows
     * @param width the width
     */
    public static void printSection(String title, String[][] rows, int width) {
        printCenteredLine(centerText(title, width));

        for (String[] row : rows) {
            String leftCol = String.format("%-15s - %-15s", centerText(row[0], 15).trim(),
                centerText(row[1], 15).trim());
            String rightCol = row[2].isEmpty() ? "" :
                String.format("%-15s - %-15s", centerText(row[2], 15).trim(),
                    centerText(row[3], 15).trim());

            int spacing = width / 2 - leftCol.length();
            spacing = spacing > 0 ? spacing : 2;

            System.out.print(leftCol);
            for (int i = 0; i < spacing; i++) {
                System.out.print(" ");
            }
            System.out.println(rightCol);
        }
        printCenteredLine(repeat("-", width));
    }

    /**
     * Center text string.
     *
     * @param text  the text
     * @param width the width
     *
     * @return the string
     */
    public static String centerText(String text, int width) {
        if (text == null) {
            text = "";
        }
        int padding = (width - text.length()) / 2;
        String pad = " ".repeat(Math.max(0, padding));
        return pad + text + pad + (text.length() % 2 == 0 ? "" : " ");
    }

    /**
     * Print centered line.
     *
     * @param line the line
     */
    public static void printCenteredLine(String line) {
        System.out.println(line);
    }

    /**
     * Repeat string.
     *
     * @param s     the s
     * @param count the count
     *
     * @return the string
     */
    public static String repeat(String s, int count) {
        return s.repeat(count);
    }

    /**
     * Format price string.
     *
     * @param price the price
     *
     * @return the string
     */
    public static String formatPrice(int price) {
        String formatted;
        if (price <= 0) {
            formatted = "N/A";
        } else {
            formatted = "₽" + String.format("%,d", price);
        }
        return formatted;
    }

    /**
     * Wrap text list.
     *
     * @param text          the text
     * @param maxLineLength the max line length
     *
     * @return the list
     */
    public static List<String> wrapText(String text, int maxLineLength) {
        List<String> lines = new ArrayList<>();
        int index = 0;

        while (index < text.length()) {
            // Determine the maximum endpoint
            int endIndex = Math.min(index + maxLineLength, text.length());

            // If we’re at the end, add the remaining text
            if (endIndex == text.length()) {
                lines.add(text.substring(index));
                break;
            }

            // If the character at endIndex is a space, we can break here
            if (text.charAt(endIndex) == ' ') {
                lines.add(text.substring(index, endIndex));
                index = endIndex + 1; // skip the space
            } else {
                // Backtrack to the last space before endIndex
                int lastSpace = text.lastIndexOf(' ', endIndex);
                if (lastSpace > index) {
                    lines.add(text.substring(index, lastSpace));
                    index = lastSpace + 1; // skip the space
                } else {
                    // No space found; force break mid-word (rare case)
                    lines.add(text.substring(index, endIndex));
                    index = endIndex;
                }
            }
        }

        return lines;
    }
}