import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReorderTest {
    int num = 0;
    boolean ready = false;

    public void actor1(Result r) {
        if (ready) {
            r.r1 = num + num;
        } else {
            r.r1 = 1;
        }
    }

    public void actor2() {
        num = 2;
        ready = true;
    }
    class Result {
        private int r1;
    }
}

