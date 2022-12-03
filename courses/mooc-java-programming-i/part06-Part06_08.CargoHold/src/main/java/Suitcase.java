import java.util.ArrayList;

public class Suitcase {
    private int maxWeight;
    private ArrayList<Item> items;

    public Suitcase(int maxWeight) {
        this.maxWeight = maxWeight;
        items = new ArrayList<>();
    }

    public void addItem(Item item) {
        int w = item.getWeight();
        if (w + totalWeight() <= maxWeight) {
            items.add(item);
        }
    }

    @Override
    public String toString() {
        if (items.size() == 0) {
            return "no items (0 kg)";
        }
        if (items.size() == 1) {
            return "1 item (" + items.get(0).getWeight() + " kg)";
        }
        return items.size() + " items (" + totalWeight() + " kg)";
    }

    public void printItems() {
        for (Item i : items) {
            System.out.println(i);
        }
    }

    public int totalWeight() {
        int totalWeight = 0;
        for (Item i : items) {
            totalWeight += i.getWeight();
        }
        return totalWeight;
    }

    public Item heaviestItem() {
        if (items.size() == 0) {
            return null;
        }

        Item heaviestItem = items.get(0);
        for (Item i: items) {
            if (i.getWeight() > heaviestItem.getWeight()) {
                heaviestItem = i;
            }
        }
        return heaviestItem;
    }
}
