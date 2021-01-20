import java.util.*;

public class TravelingSalesman{

  public static int[][] readFile(String text){
      ArrayList<String> cities = new ArrayList<String>();
      File texts = new File(Input.txt);
      Scanner input = new Scanner(texts);
      Scanner nextLine = new Scanner(input.nextLine());
      int [][] distancesList ;
      for (int i = 0 ; nextLine.hasNext() ; i++){

      }
    }

      //create a 2d array of cities and assign cities
      ArrayList<String> allCities = new ArrayList<String>();
      int[][] distances = new int[allCities.size()][allCities.size()];
      Scanner city = new Scanner(); //add a scanner
      while (city.hasNextLine()){
        Scanner line = new Scanner(city.nextLine());
        while (line.hasNext()){
          String cityOne = line.next();
          String cityTwo = line.next();
          int distance = line.nextInt();
          distances[allCities.indexOf(city1)][allCities.indexOf(city2)] = distance;
        }
      }

}
