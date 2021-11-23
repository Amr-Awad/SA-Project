import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Location {




    public static void main (String[] args) throws IOException {
        String x = null;
        File myObj = new File("D://Locations.txt");
        Scanner myReader = new Scanner(myObj);
        while (myReader.hasNextLine()) {

            String data = myReader.nextLine();

            String[] array = data.split(" ");
            String firstElement = array[0];

            int comp = firstElement.compareToIgnoreCase("Cairo");
            if (comp == 0) {
                x = array[1];
            }


        }
        myReader.close();
        System.out.println("Hello World");
        System.out.println(x);
    }


}

