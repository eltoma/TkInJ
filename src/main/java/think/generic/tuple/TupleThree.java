package think.generic.tuple;

public class TupleThree<A,B,C> extends TupleTwo<A,B> {
    public C third;

    public TupleThree(A first, B second, C third) {
        super(first, second);
        this.third = third;
    }

    @Override
    public String toString() {
        return "(" + first + " " + second + " " + third +")";
    }
}

