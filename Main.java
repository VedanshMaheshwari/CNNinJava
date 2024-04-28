import data.DataReader;
import data.Image;
import network.NetworkBuilder;
import network.NeuralNetwork;

import java.util.List;

import static java.util.Collections.shuffle;
import java.io.*;
import java.util.Scanner;
public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        long SEED = 48;

        System.out.println("Starting data loading...");

        List<Image> imagesTest = new DataReader().readData("D:\\Projects\\JavaProject\\src\\data\\mnist_test.csv");
        List<Image> imagesTrain = new DataReader().readData("D:\\Projects\\JavaProject\\src\\data\\mnist_train.csv");

        System.out.println("Images Train size: " + imagesTrain.size());
        System.out.println("Images Test size: " + imagesTest.size());
//
        NetworkBuilder builder = new NetworkBuilder(28,28,256*100,0.1);
        builder.addConvolutionLayer(8, 3, 1,SEED);
        builder.addMaxPoolLayer(2, 2);
//        builder.addFullyConnectedLayer(64, SEED);
//        builder.addFullyConnectedLayer(128, SEED);
        builder.addFullyConnectedLayer(10, SEED);

        NeuralNetwork net = builder.build();

        float rate = net.test(imagesTest);
        System.out.println("Pre training accuracy: " + rate);

        int epochs = 10;
        net.train(imagesTrain,epochs);
        float acc = net.test(imagesTest);
        System.out.println("Accuracy:" + acc);
    }
}
