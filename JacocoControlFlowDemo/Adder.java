package JacocoControlFlowDemo;

public class Adder {
    public Integer addition(Integer a, Integer b) {
        if (a == null || b == null) {
            return null;
        }
        return a + b;
    }
}