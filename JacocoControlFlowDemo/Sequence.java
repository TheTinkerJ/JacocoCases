package JacocoControlFlowDemo;

public class Sequence {

    public static int staticValue;

    static {
        int a = 1;
        int b = 2;
        staticValue = a + b;
    }

    public int fib10() {
        int n1 = 1;
        int n2 = 1;
        int n3 = n1 + n2;
        int n4 = n2 + n3;
        int n5 = n3 + n4;
        int n6 = n4 + n5;
        int n7 = n5 + n6;
        int n8 = n6 + n7;
        int n9 = n7 + n8;
        int n10 = n8 + n9;
        return n10;
    }

}
