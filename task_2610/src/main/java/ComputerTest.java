import computer.*;

public class ComputerTest {
    public static void main(String[] args) {
        CPU amd = new AMD();
        CPU intel = new Intel();

        EMS kingston = new Kingston();
        EMS samsung = new Samsung();

        DISK wd = new WestDigit();

        Computer comp = new Computer(amd,kingston,wd);
        Computer comp2 = new Computer(intel, samsung, wd);

        comp.showInfo();
        comp2.showInfo();
    }
}
