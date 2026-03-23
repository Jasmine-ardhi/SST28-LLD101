class InkRefill implements RefillStrategy {
    private String color;

    public InkRefill(String color) {
        this.color = color;
    }

    public void write() {
        System.out.println("Writing with Ink Pen, Color: " + color);
    }
}