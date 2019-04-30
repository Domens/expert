public class Tree {
    private Knot startKnot;

    public Tree(Database database) {
        startKnot = doTree(database);
    }

    private Knot doTree(Database database) {
        Knot knot = new Knot(database);
        for (String str : knot.getUniqParametres()) {
            if (knot.getKnotDecision().get(str) == KnotDecision.NEXT) {
                knot.addKnot(str, doTree(database.generateWith(knot.getName(), str)));
            }
        }
        return knot;
    }

    public Knot getStartKnot() {
        return startKnot;
    }

    public void printTree(Knot knot, String tab) {
        System.out.println(tab + knot.getName());
        knot.getKnotDecision().forEach((key, value) -> {
            System.out.println(tab + key + " " + value.name());

            if (value == KnotDecision.NEXT) {
                printTree(knot.getNextKnot().get(key), tab + " -> ");
            }
        });
    }
}
