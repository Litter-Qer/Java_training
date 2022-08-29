package task2612;

public class Pair<T> {
    private T first;
    private T second;

    public Pair() {
    }

    public Pair(T first) {
        this.first = first;
    }

    public Pair(T first, T second) {
        this(first);
        this.second = second;
    }

    public T getFirst() {
        return first;
    }

    public void setFirst(T first) {
        this.first = first;
    }

    public T getSecond() {
        return second;
    }

    public void setSecond(T second) {
        this.second = second;
    }
}
