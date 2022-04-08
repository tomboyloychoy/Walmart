import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class SeatAllocation {
    private static int seatsPerRow = 20;
    private static int noOfRows = 10;
    public static void main(String[] args) throws IOException, RequestException {
        System.out.println("Enter input file path: ");
        Scanner input = new Scanner(System.in);
        String path = input.next();
        File file = new File(path);
        Scanner sc = new Scanner(file);

        // create available seat through theater map
        TheaterSeat theaterSeat = new TheaterSeat(createTheaterMap());
        while (sc.hasNextLine()){
            theaterSeat.parseInputFile(sc.nextLine());
        }

        sc.close();
        input.close();
        // create output file
        System.out.println("Output file is: " + theaterSeat.createFile());
    }


    // Create theater map, label the seat
    private static HashMap<Character, ArrayList<Integer>> createTheaterMap() {
        HashMap<Character, ArrayList<Integer>> theaterMap = new HashMap<>();
        char temp = 'A';
        for (int i = 0; i < noOfRows; i++){
            theaterMap.put(temp, createRow());
            temp++;
        }
        return theaterMap;
    }

    private static ArrayList<Integer> createRow() {
        ArrayList<Integer> seats = new ArrayList<>();
        for (int i = 0; i < seatsPerRow; i++) {
            seats.add(i+1);
        }
        return seats;
    }
}
