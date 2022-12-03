
public class Main {

    public static void main(String[] args) {
        // Try out your class here
        Stack s = new Stack();
        System.out.println(s.isEmpty());
        System.out.println(s.values());
        s.add("Value");
        System.out.println(s.isEmpty());
        System.out.println(s.values());

        Stack s2 = new Stack();
        System.out.println(s2.isEmpty());
        System.out.println(s2.values());
        s.add("Value");
        System.out.println(s2.isEmpty());
        System.out.println(s2.values());
        String taken = s2.take();
        System.out.println(s2.isEmpty());
        System.out.println(s2.values());
        System.out.println(taken);

        Stack s3 = new Stack();
        s3.add("1");
        s3.add("2");
        s3.add("3");
        s3.add("4");
        s3.add("5");

        while (!s3.isEmpty()) {
            System.out.println(s3.take());
        }
    }
}
