class ClickPenStrategy implements PenOpenStrategy {

    public void open() {
        System.out.println("Click! Tip out");
    }

    public void close() {
        System.out.println("Click! Tip inside");
    }
}