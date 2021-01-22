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
	public static int distanceForPermutation(ArrayList<Integer> permutation, int[][] distances) {
		int totalDistance = 0;
		// If we really wanted to, we could check to make sure that permutation only conatins one of each number
		for (int i = 1; i < permutation.size(); i++) {
			totalDistance += distances[permutation.get(i-1)][permutation.get(i)];
		}
		return totalDistance;
	}

	// The java compiler complains about this function but still compiles with it
	// It has something to do with the types, but I don't know enough about Java to completely fix it...?
	public static ArrayList[] generatePermutations(int max) {
		// Once again, could probably use error checks

		// Making a list of array lists, where each arraylist contains one permutation
		ArrayList<Integer>[] permutations = new ArrayList[max];
		for (int i = 0; i < max; i++) {
			permutations[i] = new ArrayList<Integer>();
			permutations[i].add(i);
		}

		for (int i = 1; i < max; i++) { //starting with 1 and not 0 because we already did the first round
			// This algorithm makes the length of permutations mimic n!, in the sense that it starts as n,
			// then becomes n * (n-1) (which is its length * (max - i))
			// then becomes (n * (n-1)) * (n-2)
			// and so on
			ArrayList<Integer>[] newPermutations = new ArrayList[permutations.length * (max - i)];
			int permIndex = 0;
			// For each permutation...
			for (int ii = 0; ii < permutations.length; ii++) {
				// ...It iterates over all of the values that haven't been added yet...
				for (int iii = 0; iii < max; iii++) {
					if (permutations[ii].contains(iii)) continue; // Skip what has been added
					// ...And creates new permutations for each non-added value!
					ArrayList<Integer> newPermutation = (ArrayList<Integer>) permutations[ii].clone();
					newPermutation.add(iii);
					newPermutations[permIndex] = newPermutation;
					permIndex++;
				}
			}
			permutations = newPermutations;
		}
		return permutations;
	}
	public static void main(String[] args) throws FileNotFoundException { // throws is temporary here
		int[][] distances = readFile("Input.txt");
//		System.out.println(Arrays.deepToString(distances)); // A todo could be to implement more robust checks

		ArrayList<Integer>[] permutations = generatePermutations(distances.length);
//		System.out.println("permutation: " + Arrays.deepToString(permutations));

		int smallestDistance = Integer.MAX_VALUE;
		for (int i = 0 ; i < permutations.length ; i++ ){
			int temp = distanceForPermutation(permutations[i], distances);
			if (temp < smallestDistance){
				smallestDistance = temp;
			}

		}

		System.out.println(smallestDistance);
	}
}
