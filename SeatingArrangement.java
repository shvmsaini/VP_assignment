import java.io.*;

public class SeatingArrangement {
    private int[][] seatingArray; // R x C
    private int[][][] seatingAssigned;
    private int noOfPassengers;
    private int seatingCapacity;
    private int maxDimensionsRow;
    private int cnt = 0; // Seat Number

    /**
     * Tries to validate input and assign it to class member.
     *
     * @param raw_input Input provided.
     * @return If not valid false, otherwise true
     */
    private boolean validateAndAssign(String[] raw_input) {
        if (raw_input.length % 2 != 0) {
            System.out.println("Input must be a 2D-Array of [x,y] pairs.");
            return false;
        }
        seatingArray = new int[raw_input.length / 2][2];
        for (int i = 0, j = 0; j < seatingArray.length; i += 2, ++j) {
            try {
                seatingArray[j][0] = Integer.parseInt(raw_input[i + 1]); // column first
                seatingArray[j][1] = Integer.parseInt(raw_input[i]); // row
                if (seatingArray[j][0] <= 0 || seatingArray[j][1] <= 0) {
                    System.out.println("row size or col size of must be an positive Integer");
                    seatingArray = null;
                    return false;
                }
            } catch (NumberFormatException e) {
                System.out.println("row and col size must consist be integers only");
                seatingArray = null;
                return false;
            }
        }
        return true;
    }

    /**
     * Takes input in form of string.
     *
     * @throws NumberFormatException If string contains not a number
     * @throws IOException           Due to Resource usage
     */
    public void takeInput() throws NumberFormatException, IOException {

        try (BufferedReader reader = new BufferedReader(new FileReader(
                "input.txt"))) { // If it doesn't work try inserting absolute path of the file.
            System.out.println("Please enter input in form of 2d array as [[x1,y1] , [x2,y2], ...] " +
                    "where xi, yi is the number of columns, rows in ith array respectively");
            String[] str = reader.readLine().replace('[', ' ').replace(']', ' ').replace(',', ' ').trim().replaceAll(" +", " ").split(" ");
            if (!validateAndAssign(str)) return;
            System.out.println("Please enter number of passengers:");
            try {
                noOfPassengers = Integer.parseInt(reader.readLine());
                if (noOfPassengers < 0) {
                    System.out.println("Input must be a positive integer");
                    seatingArray = null;
                    return;
                }
            } catch (NumberFormatException E) {
                seatingArray = null;
                System.out.println("Input must be an integer");
                return;
            }
            getCapacityAndDimensions();
            buildOutputArray();
            cnt = 0; // reset cnt
        }
    }

    /**
     * Calculates number of columns and maximum seating capacity.
     */
    private void getCapacityAndDimensions() {
        for (int[] i : seatingArray) {
            seatingCapacity += i[0] * i[1];
            maxDimensionsRow = Math.max(maxDimensionsRow, i[0]);
        }
    }

    private void fillMiddleSeats() {
        for (int row = 0; row < maxDimensionsRow; row++) { // row wise
            for (int i = 0; i < seatingAssigned.length; i++) { // number of array
                final int currSeatingRowLen = seatingAssigned[i].length;
                final int currSeatingColLen = seatingAssigned[i][0].length;
                if (row >= currSeatingRowLen) continue;
                for (int j = 1; j < currSeatingColLen - 1; ++j) {
                    if (cnt == noOfPassengers) return;
                    seatingAssigned[i][row][j] = ++cnt;
                }
            }
        }
    }

    private void fillWindowSeats() {
        final int firstRowLen = seatingArray[0][0];
        final int LastRowLen = seatingArray[seatingArray.length - 1][0];
        final int LastColLen = seatingArray[seatingArray.length - 1][1];
        for (int row = 0; row < maxDimensionsRow; row++) {
            if (cnt == noOfPassengers) return;
            if (row < firstRowLen && seatingArray[0][1] > 1) {
                seatingAssigned[0][row][0] = ++cnt;
            }
            if (cnt == noOfPassengers) return;
            if (row < LastRowLen && seatingArray[seatingArray.length - 1][1] > 1) {
                seatingAssigned[seatingAssigned.length - 1][row][LastColLen - 1] = ++cnt;
            }
        }
    }

    private void fillAisleSeats() {
        if(seatingArray.length == 1 && seatingArray[0][0] == 1) return;
        for (int row = 0; row < maxDimensionsRow; row++) { // row wise
            for (int i = 0; i < seatingAssigned.length; i++) { // number of array
                final int currRowLen = seatingArray[i][0];
                final int currColLen = seatingArray[i][1];
                if (row >= currRowLen) continue;
                if (cnt == noOfPassengers) return;
                if (i == 0) { // first seating
                    seatingAssigned[i][row][currColLen - 1] = ++cnt; // only aisle
                } else if (i == seatingAssigned.length - 1) { // last seating
                    seatingAssigned[i][row][0] = ++cnt; // only aisle
                } else { // not corner
                    seatingAssigned[i][row][0] = ++cnt;
                    if (currColLen != 1) seatingAssigned[i][row][currColLen - 1] = ++cnt;
                }
            }
        }
    }

    /**
     * Assigns seats to passengers in given order
     */
    public void fillSeats() {
        if (seatingArray == null) {
            System.out.println("Please enter valid input first.");
            return;
        }
        if (noOfPassengers > seatingCapacity) {
            System.out.printf("Only %d passengers will be able to sit!\n", seatingCapacity);
            noOfPassengers = seatingCapacity;
        }
        fillAisleSeats();
        fillWindowSeats();
        fillMiddleSeats();
    }

    private void buildOutputArray() {
        seatingAssigned = new int[seatingArray.length][][];
        for (int i = 0; i < seatingArray.length; ++i) {
            seatingAssigned[i] = new int[seatingArray[i][0]][seatingArray[i][1]];
        }
    }

    /**
     * Prints seating Arrangement in a table format.
     */
    public void printSeats() {
        if (seatingArray == null) {
            System.out.println("Please enter valid input first.");
            return;
        }
        try (PrintWriter writer = new PrintWriter(
                "output.txt")) { // If it doesn't work try inserting absolute path of the file.
            for (int row = 0; row < maxDimensionsRow; ++row) { // row wise
                for (int[][] group : seatingAssigned) { // number of array
                    for (int j = 0; j < group[0].length; ++j) {
                        if (row < group.length) writer.printf(" %2d ", group[row][j]);
                        else writer.print(" -- ");
                    }
                    writer.print(" | ");
                }
                writer.println();
            }
            writer.println();
            writer.flush();
        } catch (FileNotFoundException e) {
            System.out.println("Make sure you have write permissions in this project directory.");
            throw new RuntimeException(e);
        }
    }
}

class helper {
    public static void main(String[] args) throws NumberFormatException, IOException {
        SeatingArrangement seatingArrangement = new SeatingArrangement();
        seatingArrangement.takeInput();
        seatingArrangement.fillSeats();
        seatingArrangement.printSeats();
    }
}
