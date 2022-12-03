
public class Greatest {

    public static int greatest(int number1, int number2, int number3) {
        //write some code here
        if (number1 < number2) {
            if (number2 < number3) {
                return number3;
            } else {
                return number2;
            }
        } else {
            if (number1 < number3) {
                return number3;
            } else {
                return number1;
            }
        }
    }

    public static void main(String[] args) {
        int result = greatest(2, 7, 3);
        System.out.println("Greatest: " + result);
    }
}
