import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class TheaterSeat {
    private HashMap<Character, ArrayList<Integer>> theaterMap;
    private int seatPerRow = 20;
    private int noOfRow = 10;
    private int countAvailableSeat = seatPerRow * noOfRow;
    private Character[] bestRow = {'D', 'E', 'F', 'G'};
    LinkedHashMap<String, ArrayList<String>> bookedSeat = new LinkedHashMap<>();
    HashMap<String, Integer> requested = new HashMap<String, Integer>();
    public TheaterSeat(HashMap<Character, ArrayList<Integer>> createTheaterMap) {
        theaterMap = createTheaterMap;
    }

    public String parseInputFile(String s) throws RequestException {
        String split[] = s.split(" ");
        return validateRequest(split);
    }

    private String validateRequest(String split[]) throws RequestException {
        String request = "";
        int numSeat = 0; // number of the seat

        // IF REQUEST IS VALID
        if (split.length == 2) {
            if (bookedSeat.containsKey(request));
            request = split[0];
            numSeat = Integer.parseInt(split[1]);
            if (bookedSeat.containsKey(request)){
                throw new RequestException(request + ": Repeat request ID");
            }
            else {
                requested.put(request, numSeat);
                return chooseBestSeat(request, numSeat);
            }

        }

        // IF REQUEST IS INVALID
        if (split.length > 2 || split.length < 2) {
            throw new RequestException(request + ": Invalid request code");
        }
        if (numSeat <= 0) {
            throw new RequestException(request + ": Number of seats must be more than 0");
        }
        if (request == null || request.isEmpty() ) {
            throw new RequestException(request + ": Invalid Request ID");
        }

        return "";
    }

    private String chooseBestSeat(String request, int numSeat){
        String result = "";
        boolean seatFound = false;

        if (numSeat > countAvailableSeat || numSeat > seatPerRow ){
            result = request.concat("Request failed. Too many seats.");
            return result;
        }

        Character row = Character.MIN_VALUE;

        // check if ideal seat is available
        for (char i : bestRow){
            if (theaterMap.get(i).size() >= numSeat && seatFound == false){
                seatFound = true;
                row = i;
                selectSeat(numSeat, row, request);
            }
        }

        if (seatFound == false){
            for (char i : theaterMap.keySet()) {
                if (theaterMap.get(i).size() >= numSeat){
                    seatFound = true;
                    row = i;
                    selectSeat(numSeat, row, request);
                }
            }
        }
        // Update available seat in theater
        return new String(request.concat(bookedSeat.get(request).toString()));
    }

    public String selectSeat(int numSeat, Character row, String request){
        ArrayList<String> seat = new ArrayList<>();
        for (int k = 0; k < numSeat; k++){
            seat.add(row.toString() + theaterMap.get(row).get(k));
        }

        removeSeat(numSeat, row);
        // buffer seat
        int buffer = 3;
        if (theaterMap.get(row).size() > 3){
            while (buffer > 0){
                buffer -= 1;
                removeSeat(1, row);
            }
        }
        else {
            while(theaterMap.get(row).size() > 0){
                removeSeat(1, row);
            }
        }
        bookedSeat.put(request, seat);
        return new String(request.concat(bookedSeat.get(request).toString()));
    }

    public void removeSeat(int numSeat, Character row){
        for (int i = 0; i < numSeat; i++){
            countAvailableSeat -= 1;
            theaterMap.get(row).remove(0);
        }
    }

    // OUTPUT
    public String createFile() throws IOException {
        String result ="";
        for (Map.Entry<String, ArrayList<String>> request : bookedSeat.entrySet()){
            result += request.getKey() + " " + request.getValue() + System.lineSeparator();
            result = result.replace("[","").replace("]","" );
        }


        // Create output file
        Files.write(Paths.get("output.txt"), result.getBytes(), StandardOpenOption.CREATE);
        File f = new File("output.txt");
        String path = f.getCanonicalPath();
        return path;
    }

}





