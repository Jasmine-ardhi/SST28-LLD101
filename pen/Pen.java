class Pen {

    private RefillStrategy refillStrategy;
    private PenOpenStrategy openStrategy;
    private boolean isOpen;

    public Pen(RefillStrategy refillStrategy, PenOpenStrategy openStrategy) {
        this.refillStrategy = refillStrategy;
        this.openStrategy = openStrategy;
        this.isOpen = false;
    }

    public void start() {
        openStrategy.open();
        isOpen = true;
    }

    public void write() {
        if (!isOpen) {
            System.out.println("Pen is closed! Cannot write.");
            return;
        }
        refillStrategy.write();
    }

    public void close() {
        openStrategy.close();
        isOpen = false;
    }

  
    public void refill(RefillStrategy newRefill) {
        this.refillStrategy = newRefill;
        System.out.println("Refill changed!");
    }

    
    public void changePenType(PenOpenStrategy newStrategy) {
        this.openStrategy = newStrategy;
        System.out.println("Pen type changed!");
    }
}