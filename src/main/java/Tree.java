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
}
