/**
 * PrintLayout is a public class with private attributes which are assigned by the constructor.
 * It Displays the Borders and the Header-Footer columns in the required format.
 */
public class PrintLayout {
    private int widthOfDisplayColumn, total;
    private String totalRow, countRow, totalStringValue;

    /**
     * PrintLayout() is the constructor which declares the following values which are in turn passed on to printHeaderOrFooter and printBorder to print the required.
     *
     * @param totalRow             is the String Value of the total column element.
     * @param widthOfDisplayColumn is the int value of the maximum width of the display column.
     * @param countRow             is the String value of the count column element.
     * @param totalStringValue     is the String value of the total int value.
     * @param total                is the int value of the sum of all the counts of the words given.
     */
    public PrintLayout(String totalRow, int widthOfDisplayColumn, String countRow, String totalStringValue, int total) {
        this.widthOfDisplayColumn = widthOfDisplayColumn;
        this.total = total;
        this.countRow = countRow;
        this.totalRow = totalRow;
        this.totalStringValue = totalStringValue;
    }

    /**
     * printHeaderOrFooter takes in the following parameters and prints the Footer row of the output table.
     *
     * @param headerOrFooter signifies the value which needs to be printed.
     *                       It prints header if it's 1 and footer if it's 2.
     */
    protected void printHeaderOrFooter(int headerOrFooter) {
        if (headerOrFooter == 1) {
            System.out.print("| WORD  ");
            for (int i = 0; i < widthOfDisplayColumn - totalRow.length(); i++) {
                System.out.print(" ");
            }
            System.out.print(countRow);
            if (countRow.length() < totalStringValue.length()) {
                for (int i = 0; i < totalStringValue.length() - countRow.length(); i++) {
                    System.out.print(" ");
                }
            }
            System.out.print(" |");
            System.out.println();
        } else if (headerOrFooter == 2) {
            System.out.print(totalRow);
            if (widthOfDisplayColumn < totalRow.length()) {
                System.out.print(" ");
            } else {
                for (int i = 0; i <= widthOfDisplayColumn - totalRow.length(); i++) {
                    System.out.print(" ");
                }
            }
            System.out.print("| ");
            if (totalStringValue.length() < countRow.length()) {
                for (int i = 0; i < countRow.length() - totalStringValue.length(); i++) {
                    System.out.print(" ");
                }
            } else {
                for (int i = 0; i < totalStringValue.length() - totalStringValue.length(); i++) {
                    System.out.print(" ");
                }
            }
            System.out.print(total + " |");
            System.out.println();
        }
    }

    /**
     * printBorder is a static method which takes in the following parameters and prints the Border of a table as per required.
     *
     * @param widthOfDisplayColumn is the int value of the width of the display column.
     * @param totalRow             is the String of the total row element.
     * @param totalStringValue     is the String value of the total number of all counts.
     * @param countRow             is the String value of the count row element.
     */
    protected void printBorder() {
        System.out.print("|");
        if (widthOfDisplayColumn < totalRow.length()) {
            for (int i = 0; i < totalRow.length() - 1; i++) {
                System.out.print("-");
            }
            System.out.print("-");
        } else {
            for (int i = 0; i < widthOfDisplayColumn; i++) {
                System.out.print("-");
            }
        }
        System.out.print("|");
        if (totalStringValue.length() < countRow.length()) {
            for (int i = 0; i < countRow.length(); i++) {
                System.out.print("-");
            }
        } else {
            for (int i = 0; i < totalStringValue.length(); i++) {
                System.out.print("-");
            }
        }
        System.out.print("|");
        System.out.println();
    }
}
