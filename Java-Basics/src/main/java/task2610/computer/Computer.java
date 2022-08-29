package task2610.computer;

import java.text.MessageFormat;

public class Computer {
    private CPU cpu;
    private EMS ems;
    private DISK disk;

    public Computer() {
    }

    public Computer(CPU cpu, EMS ems, DISK disk) {
        this.cpu = cpu;
        this.ems = ems;
        this.disk = disk;
    }

    public void showInfo(){
        StringBuilder str = new StringBuilder();

        str.append(MessageFormat.format("Your computer information:\n" +
                "CPU： {0}\nEMS： {1}\nDISK: {2}\n",
                cpu.getBrand(),
                ems.getSize(),
                disk.getCapacity()));
        System.out.println(str);
    }
}
