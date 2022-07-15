package computer;

public class Kingston implements EMS{

    @Override
    public int getSize() {
        return 1024;
    }

    @Override
    public String getType() {
        return "DDR4";
    }
}
