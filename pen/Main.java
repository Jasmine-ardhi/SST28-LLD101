public class Main {
    public static void main(String[] args) {

       
        Pen pen = new Pen(new InkRefill("Blue"), new CapPenStrategy());

        pen.start();
        pen.write();
        pen.close();

        System.out.println("---- Changing Refill ----");

        pen.refill(new GelRefill("Black"));

        pen.start();
        pen.write();
        pen.close();

        System.out.println("---- Changing Pen Type ----");

        pen.changePenType(new ClickPenStrategy());

        pen.start();
        pen.write();
        pen.close();
    }
}