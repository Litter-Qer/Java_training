package task2611;

public class OuterClass {

    public OuterClass() {
    }

    private class PrivateClass {
        private final String name = PrivateClass.class.getCanonicalName();
        public PrivateClass( ) {}
    }

    private static class PrivateStaticClass {
        private final String name = PrivateStaticClass.class.getCanonicalName();
        public PrivateStaticClass( ) {}
    }

    private final Runnable r = new Runnable() {
        @Override
        public void run() {
            System.out.println("\nThis is an inner class run method");
        }
    };

    @Override
    public String toString() {
        return "OuterClass{" +
                "r=" + r +
                '}';
    }
}
