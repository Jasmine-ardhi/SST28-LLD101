class GelRefill implements RefillStrategy {
    private String color;

    public GelRefill(String color) {
        this.color = color;
    }

    public void write() {
        System.out.println("Writing with Gel Pen, Color: " + color);
    }
}