class NeuralNetwork{
    InputNode[] input;
    HiddenNode[] hidden;
    OutputNode[] output;
    NeuralNetwork(int aa,int bb, int cc){
        input = new InputNode[aa];
        hidden = new HiddenNode[bb];
        output = new OutputNode[cc];

        for(int i = 0; i < input.length;i++){
            InputNode temp = new InputNode();
            temp.outputNodes = new Edge[hidden.length];
            input[i] = temp;
        }
        for(int i = 0; i < hidden.length;i++){
            HiddenNode temp = new HiddenNode();
            temp.inputNodes = new Edge[input.length];
            temp.outputNodes = new Edge[output.length];
            hidden[i] = temp;
        }
        for(int i = 0; i < output.length;i++){
            OutputNode temp = new OutputNode();
            temp.inputNodes = new Edge[hidden.length];
            output[i] = temp;
        }

        for (int a = 0;a<input.length;a++) {
            for (int b = 0; b < hidden.length;b++) {
                Edge e = new Edge();
                e.input = input[a];
                e.output = hidden[b];
                input[a].outputNodes[b]=e;
                hidden[b].inputNodes[a]=e;
                e.weight = Math.random()*11;
            }            
        }
        for(int a = 0; a < hidden.length;a++){
            for(int b = 0; b < output.length;b++){
                Edge e = new Edge();
                e.input = hidden[a];
                e.output = output[b];
                hidden[a].outputNodes[b] = e;
                output[b].inputNodes[a] = e;
                e.weight = Math.random()*3;
            }
        }
    }
    double[] look(double[] pInput){
        for(int i = 0; i < input.length;i++){
            input[i].output = pInput[i];
        }
        for(int i = 0; i < hidden.length;i++){
            hidden[i].berechne();
        }
        for(int i = 0; i < output.length;i++){
            output[i].berechne();
        }
        double[] aOutput =  new double[output.length];
        int counter = 0;
        for(OutputNode z : output){
            aOutput[counter] = z.output;
            counter++;
        }
        return aOutput;
    }
    class Edge{
        Node input;
        Node output;
        double weight;
        Edge(){}        
    }
    abstract class Node{
        double output;
        Edge[] inputNodes;
        Edge[] outputNodes;
        abstract void berechne();
    }
    class InputNode extends Node{
        Edge[] outputNodes;
        double input;
        void berechne(){output = input;}
        InputNode(){}
    }
    class HiddenNode extends Node{
        Edge[] inputNodes;
        Edge[] outputNodes;
        double sumInput;
        Function activationFunction = new Function();
        double output;
        HiddenNode(){}
        void berechne(){berechneInput();berechneOutput();}
        void berechneInput(){
            double temp = 0;
            for(Edge i:inputNodes){
                temp += i.input.output * i.weight;
            }
            sumInput = temp;
        }
        public void berechneOutput(){
            output = activationFunction.berechne(sumInput);
        }
    }
    class OutputNode extends Node{
        Edge[] inputNodes;
        double sumInput;
        Function activationFunction = new Function();
        double output;
        OutputNode(){}
        void berechneInput(){
            double temp = 0;
            for(Edge i:inputNodes){
                temp += i.input.output * i.weight;
            }
            sumInput = temp;
        }
        public void berechneOutput(){
            output = activationFunction.berechne(sumInput);
        }
        void berechne(){berechneInput();berechneOutput();}
    }
    class Function{
        Function(){}
        double berechne(double x){
            //return (1/(1+Math.pow(2.71828,-1*x)));
            return x;
        }
    }
}