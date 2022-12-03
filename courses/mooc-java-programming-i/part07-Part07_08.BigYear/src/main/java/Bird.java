public class Bird {
    private String name;
    private String latinName;
    private int observations;

    public Bird(String name, String latinName) {
        this.latinName = latinName;
        this.name = name;
        observations = 0;
    }

    public String getName() {
        return name;
    }

    public void observe() {
        observations++;
    }

    @Override
    public String toString() {
        return name + "(" + latinName + "): " + observations + " observations";
    }
}
