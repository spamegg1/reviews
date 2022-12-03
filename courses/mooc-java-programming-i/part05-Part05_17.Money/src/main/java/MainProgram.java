
public class MainProgram {

    public static void main(String[] args) {
        // test your class here
        Money a = new Money(10,0);
        Money b = new Money(5,0);

        Money c = a.plus(b);

        System.out.println(a);  // 10.00e
        System.out.println(b);  // 5.00e
        System.out.println(c);  // 15.00e

        a = a.plus(c);          // NB: a new Money object is created, and
        // is placed "at the end of the strand connected to a"
        // the old 10 euros at the end of the strand disappears
        // and the Java garbage collector takes care of it

        System.out.println(a);  // 25.00e
        System.out.println(b);  // 5.00e
        System.out.println(c);  // 15.00e


        Money d = new Money(10, 0);
        Money e = new Money(3, 0);
        Money f = new Money(5, 0);

        System.out.println(d.lessThan(e));  // false
        System.out.println(e.lessThan(f));  // true
    }
}
