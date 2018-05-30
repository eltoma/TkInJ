package think.generic.tuple;

public class TupleTwo<A,B> {
    public final A first;
    public final B second;

    public TupleTwo(A first, B second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public String toString() {
        return "(" + first + " " + second + ")";
    }
}

