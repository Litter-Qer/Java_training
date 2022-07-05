import pojo.Shape;

/**
 * 此项目是task_2604的延申
 * 此项目主要的功能是打印不同的图形
 * 每个图形的对齐方式，大小和样式都可选择，比如左、右；*、~等。
 * 本项目中的一些思考，全部写在了reflection.txt中
 * @author Jon
 * @version 0.1
*/

public class LoopPrint {
    /**
     * 决定打印的图形类型和对齐方式，将参数传到print里面
     * @param shape 一个图形对象
     */
    public static void printShape(Shape shape){
        shape.print();
    }
}
