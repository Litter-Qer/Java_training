package pojo;

public class Square extends Rectangle {
    final private int side;

    public Square(int side) {
        super();
        this.side = side;
    }

    public int getSide() {
        return side;
    }

    @Override
    public void print() {
        super.print();
        for (int i = 0; i < this.side; i++) {
            for (int j = 0; j < this.side; j++) {
                System.out.print(getLabel());
            }
            System.out.println();
        }
        System.out.println(); //图形之间空行
    }
}
