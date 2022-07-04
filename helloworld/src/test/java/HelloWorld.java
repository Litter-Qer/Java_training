import org.junit.Test;

public class HelloWorld {
    @Test
    public void say(){
        // 这个测试应该先上传到test1 然后通过git merge在合并到master上
        System.out.println("Hello World!");
        System.out.println("This file should be committed to test1 then merge to master");
    }

}
