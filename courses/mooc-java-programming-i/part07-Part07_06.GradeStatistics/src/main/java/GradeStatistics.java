import java.util.ArrayList;

public class GradeStatistics {
    private ArrayList<Integer> pointTotals;

    public GradeStatistics() {
        this.pointTotals = new ArrayList<>();
    }

    public void addPointTotal(int pointTotal) {
        if (pointTotal < 0 || pointTotal > 100) {
            return;
        }
        this.pointTotals.add(pointTotal);
    }

    public void print() {
        double sum = 0;
        double passingSum = 0;
        int numberOfPassing = 0;

        for (Integer points : pointTotals) {
            sum += points;
            if (points >= 50) {
                passingSum += points;
                numberOfPassing++;
            }
        }

        System.out.println("Point average (all): " + sum / this.pointTotals.size());

        if (numberOfPassing > 0) {
            System.out.println("Point average (passing): " + passingSum / numberOfPassing);
        } else {
            System.out.println("Point average (passing): -");
        }

        System.out.println("Pass percentage: " + (100.0 * numberOfPassing / this.pointTotals.size()));
        System.out.println("Grade distribution:");

        for (int i = 5; i >= 0; i--) {
            System.out.println(i + ": " + printStars(i));
        }
    }

    private String printStars(int grade) {
        int howManyStars = 0;

        for (Integer points : pointTotals) {
            if (convertToGrade(points) == grade) {
                howManyStars++;
            }
        }
        return stars(howManyStars);
    }

    // the following methods are only for internal use of the class,
    // and as such have been defined as private,
    // i.e. not shown to other classes

    private String stars(int n) {
        String stars = "";

        for (int i = 0; i < n; i++) {
            stars += "*";
        }
        return stars;
    }

    private int convertToGrade(int pointAmount) {
        int grade = 5;

        if (pointAmount < 50) {
            grade = 0;
        } else if (pointAmount < 60) {
            grade = 1;
        } else if (pointAmount < 70) {
            grade = 2;
        } else if (pointAmount < 80) {
            grade = 3;
        } else if (pointAmount < 90) {
            grade = 4;
        }

        return grade;
    }
}