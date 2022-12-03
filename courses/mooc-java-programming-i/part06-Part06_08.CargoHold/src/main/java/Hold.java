import java.util.ArrayList;

public class Hold {
    private int maxWeight;
    private ArrayList<Suitcase> suitcases;

    public Hold(int maxWeight) {
        this.maxWeight = maxWeight;
        suitcases = new ArrayList<>();
    }

    public void addSuitcase(Suitcase suitcase) {
        if (suitcase.totalWeight() + this.totalWeight() <= maxWeight) {
            suitcases.add(suitcase);
        }
    }

    @Override
    public String toString() {
        return suitcases.size() + " suitcases (" + this.totalWeight() + "kg)";
    }

    public int totalWeight() {
        int total = 0;
        for (Suitcase s : suitcases) {
            total += s.totalWeight();
        }
        return total;
    }

    public void printItems() {
        for (Suitcase s : suitcases) {
            s.printItems();
        }
    }
}
