package pokemon;

public class DisplayHelper {

    public static void printSection(String title, String[][] rows, int width) {
        printCenteredLine(centerText(title, width));

        for (String[] row : rows) {
            String leftCol = String.format("%-15s - %-15s", centerText(row[0], 15).trim(), centerText(row[1], 15).trim());
            String rightCol = row[2].isEmpty() ? "" : String.format("%-15s - %-15s", centerText(row[2], 15).trim(), centerText(row[3], 15).trim());

            int spacing = width / 2 - leftCol.length();
            spacing = spacing > 0 ? spacing : 2;

            System.out.print(leftCol);
            for (int i = 0; i < spacing; i++) System.out.print(" ");
            System.out.println(rightCol);
        }
        printCenteredLine(repeat("-", width));
    }

    public static String centerText(String text, int width) {
        if (text == null) text = "";
        int padding = (width - text.length()) / 2;
        String pad = " ".repeat(Math.max(0, padding));
        return pad + text + pad + (text.length() % 2 == 0 ? "" : " ");
    }

    public static void printCenteredLine(String line) {
        System.out.println(line);
    }

    public static String repeat(String s, int count) {
        return s.repeat(count);
    }


}
