public class Door {
    private boolean open = false;

    public void open() {
        open = true;
        System.out.println("Door opened");
    }

    public void close() {
        open = false;
        System.out.println("Door closed");
    }
}