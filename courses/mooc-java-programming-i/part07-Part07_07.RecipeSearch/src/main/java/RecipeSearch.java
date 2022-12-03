import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class RecipeSearch {
    public static String byName(RecipeReader reader, String recipeName) {
        String result = "Recipes:\n";
        for (Recipe recipe : reader.getRecipes()) {
            if (recipe.getName().contains(recipeName)) {
                result += recipe.toString() + "\n";
            }
        }
        return result;
    }

    public static String byCookingTime(RecipeReader reader, int time) {
        String result = "Recipes:\n";
        for (Recipe recipe : reader.getRecipes()) {
            if (recipe.getTime() <= time) {
                result += recipe.toString() + "\n";
            }
        }
        return result;
    }

    public static String byIngredient(RecipeReader reader, String ingredient) {
        String result = "Recipes:\n";
        for (Recipe recipe : reader.getRecipes()) {
            if (recipe.getIngredients().contains(ingredient)) {
                result += recipe.toString() + "\n";
            }
        }
        return result;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("File to read: ");
        String fileName = scanner.nextLine();

        RecipeReader reader = new RecipeReader();
        reader.readFile(fileName);

        // System.out.println(reader.listRecipes());

        while (true) {
            System.out.println(reader.listCommands());
            System.out.println("Enter command: ");
            String command = scanner.nextLine();

            if (command.equals("stop")) {
                break;
            }

            if (command.equals("list")) {
                System.out.println(reader.listRecipes());
            }

            if (command.equals("find name")) {
                System.out.println("Searched word: ");
                String name = scanner.nextLine();
                System.out.println(byName(reader, name));
            }

            if (command.equals("find cooking time")) {
                System.out.println("Max cooking time: ");
                int time = Integer.valueOf(scanner.nextLine());
                System.out.println(byCookingTime(reader, time));
            }

            if (command.equals("find ingredient")) {
                System.out.println("Ingredient: ");
                String ingredient = scanner.nextLine();
                System.out.println(byIngredient(reader, ingredient));
            }
        }
    }
}
