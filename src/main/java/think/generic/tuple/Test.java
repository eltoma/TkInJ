package think.generic.tuple;

public class Test {
    static TupleTwo<String, Integer> f() {
        return new TupleTwo<String, Integer>("hi", 14);
    }

    static TupleThree<String, Integer, Character> g() {
        return new TupleThree<String, Integer, Character>("hi", 14, 'a');
    }


    public static void main(String[] args) {
        System.out.println(f());
        System.out.println(g());
    }
}
