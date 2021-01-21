import java.util.*;
import java.io.*;

public class TravelingSalesman{
	public static int[][] readFile(String filename) throws FileNotFoundException {
		// Note: I did some reading and found an interesting Scanner method called "skip"
		// We could potentially use this method to skip "=" and "to"
		// However, I decided not to include it for simplicity

		ArrayList<String> cities = new ArrayList<String>();
		// Doing one pass over the text to get all of the city names
		// This could potentially be made faster by using nested array lists (ArrayList<ArrayList<Integer>>) and adding cities as they are encountered
		File f = new File(filename);
		Scanner input = new Scanner(f);
		while (input.hasNextLine()) {
			Scanner line = new Scanner(input.nextLine());
			if (!line.hasNext()) break; //encountered an empty line
			// Could be made robust by including error checking for improper lines
			// Or by just looping through all of the words in the line and throwing out "to," "=," and numbers
			// But this was the easiest way for now
			String city = line.next();
			if (!cities.contains(city)) cities.add(city);
			line.next(); //discard "to"
			city = line.next();
			if (!cities.contains(city)) cities.add(city);
		}

		// Second pass to actually collect data
		int[][] distances = new int[cities.size()][cities.size()];
		f = new File(filename); // This might not be necessary if Scanner doesn't consume the file. Added it just to be safe.
		input = new Scanner(f); // From the top
		while (input.hasNextLine()) {
			Scanner line = new Scanner(input.nextLine());
			while (line.hasNext()) { // Copied from older code but not sure of its necessity
				String cityOne = line.next();
				line.next(); //discard to
				String cityTwo = line.next();
				line.next(); //discard =, since nextInt doesn't skip to the next int but just takes the next token and throws java.util.InputMismatchException otherwise
				int distance = line.nextInt();
				distances[cities.indexOf(cityOne)][cities.indexOf(cityTwo)] = distance;
				distances[cities.indexOf(cityTwo)][cities.indexOf(cityOne)] = distance;
			}
		}
		return distances;
	}
	public static void main(String[] args) throws FileNotFoundException { // throws is temporary here
		// So far, main just tests the readFile function
		int[][] distances = readFile("Input.txt");
		System.out.println(Arrays.deepToString(distances)); // A todo could be to implement more robust checks
	}
}
