import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class RecipeReader {
    private ArrayList<Recipe> recipeList;

    public RecipeReader() {
        this.recipeList = new ArrayList<>();
    }

    public void readFile(String fileName) {
        try (Scanner scanner = new Scanner(Paths.get(fileName))) {
            while (scanner.hasNextLine()) {
                String name = scanner.nextLine();
                int cookingTime = Integer.valueOf(scanner.nextLine());
                ArrayList<String> ingredients = new ArrayList<>();

                String line = scanner.nextLine();
                while (scanner.hasNextLine() && !line.equals("")) {
                    ingredients.add(line);
                    line = scanner.nextLine();
                }
                ingredients.add(line);

                Recipe recipe = new Recipe(name, cookingTime, ingredients);
                recipeList.add(recipe);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public ArrayList<Recipe> getRecipes() {
        return recipeList;
    }

    public String listRecipes() {
        String result = "Recipes:\n";
        for (Recipe recipe: recipeList) {
            result += recipe.toString() + "\n";
        }
        return result;
    }

    public String listCommands() {
        String result = "Commands:\n";
        result += "list - list the recipes\n";
        result += "stop - stops the program\n";
        result += "find name - searches recipes by name";
        result += "find cooking time - searches recipes by cooking time";
        result += "find ingredient - searches recipes by ingredient";
        return result;
    }

}
