package task2604.pojo;

public class Shape {
    private char align;

    private char label;

    public Shape() {
    }

    public char getAlign() {
        return align;
    }

    public void setAlign(char align) {
        this.align = align;
    }

    public char getLabel() {
        return label;
    }

    public void setLabel(char label) {
        this.label = label;
    }

    public void print() {
        //内部调用最好不要使用 getter setter，直接操作即可，减少栈帧
        //其实这里执行多了之后， JIT 优化也会内联，将 getAlign() 改成 this.align
        if(this.align == 'l'){
            printLeft();
        }
        else if (this.align == 'r')
        {
            printRight();
        }
    }
    public void printLeft(){}
    public void printRight(){}

}
