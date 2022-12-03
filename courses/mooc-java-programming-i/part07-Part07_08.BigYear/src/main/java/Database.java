import java.util.ArrayList;

public class Database {
    private ArrayList<Bird> birds;

    public Database() {
        birds = new ArrayList<>();
    }

    public void add(Bird bird) {
        birds.add(bird);
    }

    public void observeBird(String name) {
        for (Bird bird : birds) {
            if (bird.getName().equals(name)) {
                bird.observe();
                return;
            }
        }
        System.out.println("Not a bird!");
    }

    public String all() {
        String result = "";
        for (Bird bird : birds) {
            result += bird.toString() + "\n";
        }
        return result;
    }

    public void one(String name) {
        for (Bird bird : birds) {
            if (bird.getName().equals(name)) {
                System.out.println(bird.toString());
                return;
            }
        }
        System.out.println("Not a bird!");
    }
}
