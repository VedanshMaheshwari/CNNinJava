package network;

import layers.ConvolutionLayer;
import layers.FullyConnectedLayer;
import layers.Layer;
import layers.MaxPoolLayer;

import java.util.ArrayList;
import java.util.List;

public class NetworkBuilder {

    private NeuralNetwork net;
    private int _inputRows;
    private int _inputCols;
    private double _scaleFactor;
    private double _learningRate;
    List<Layer> _layers;

    public NetworkBuilder(int _inputRows, int _inputCols, double _scaleFactor, double _learningRate) {
        this._inputRows = _inputRows;
        this._inputCols = _inputCols;
        this._scaleFactor = _scaleFactor;
        this._learningRate = _learningRate;
        _layers = new ArrayList<>();
    }

    public void addConvolutionLayer(int numFilters, int filterSize, int stepSize, long SEED){
        if(_layers.isEmpty()){
            _layers.add(new ConvolutionLayer(filterSize, stepSize, 1, _inputRows, _inputCols, SEED, numFilters, this._learningRate));
        } else {
            Layer prev = _layers.get(_layers.size()-1);
            _layers.add(new ConvolutionLayer(filterSize, stepSize, prev.getOutputLength(), prev.getOutputRows(), prev.getOutputCols(), SEED, numFilters, this._learningRate));
        }
    }

    public void addMaxPoolLayer(int windowSize, int stepSize){
        if(_layers.isEmpty()){
            _layers.add(new MaxPoolLayer(stepSize, windowSize, 1, _inputRows, _inputCols));
        } else {
            Layer prev = _layers.get(_layers.size()-1);
            _layers.add(new MaxPoolLayer(stepSize, windowSize, prev.getOutputLength(), prev.getOutputRows(), prev.getOutputCols()));
        }
    }

    public void addFullyConnectedLayer(int outLength, long SEED){
        if(_layers.isEmpty()) {
            _layers.add(new FullyConnectedLayer(_inputCols*_inputRows, outLength, SEED, this._learningRate));
        } else {
            Layer prev = _layers.get(_layers.size()-1);
            _layers.add(new FullyConnectedLayer(prev.getOutputElements(), outLength, SEED, this._learningRate));
        }

    }

    public NeuralNetwork build(){
        net = new NeuralNetwork(_layers, _scaleFactor);
        return net;
    }

}
