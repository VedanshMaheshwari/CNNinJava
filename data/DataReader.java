package data;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DataReader {

    private final int rows = 28;
    private final int cols = 28;

    public List<Image> readData(String path) {
        List<Image> images = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File(path))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] lineItems = line.split(",");

                double[][] data = new double[rows][cols];
                int label = Integer.parseInt(lineItems[0]);

                int i = 1;

                for (int row = 0; row < rows; row++) {
                    for (int col = 0; col < cols; col++) {
                        data[row][col] = Double.parseDouble(lineItems[i]);
                        i++;
                    }
                }

                images.add(new Image(data, label));
            }
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("File not found " + path);
        }

        return images;
    }
}
