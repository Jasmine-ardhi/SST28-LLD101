public class WeightSensor {
    private int weight = 0;
    private final int maxWeight = 1000;

    public void setWeight(int w) {
        this.weight = w;
    }

    public boolean isOverWeight() {
        return weight > maxWeight;
    }
}