
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class SportStatistics {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("File:");
        String fileName = scan.nextLine();

        System.out.println("Team:");
        String team = scan.nextLine();

        try {
            Scanner fileScan = new Scanner(Paths.get(fileName));
            int games = 0;
            int wins = 0;
            int losses = 0;

            while (fileScan.hasNextLine()) {
                String[] match = fileScan.nextLine().split(",");
                String homeTeam = match[0];
                String visitingTeam = match[1];
                int homeScore = Integer.valueOf(match[2]);
                int visitingScore = Integer.valueOf(match[3]);

                if (homeTeam.equals(team)) {
                    games++;
                    if (homeScore > visitingScore) {
                        wins++;
                    } else {
                        losses++;
                    }
                } else if (visitingTeam.equals(team)) {
                    games++;
                    if (homeScore < visitingScore) {
                        wins++;
                    } else {
                        losses++;
                    }
                }
            }

            System.out.println("Games: " + games);
            System.out.println("Wins: " + wins);
            System.out.println("Losses: " + losses);

        } catch (Exception e) {
            System.out.println("Reading file " + fileName + "failed.");
        }
    }

}
