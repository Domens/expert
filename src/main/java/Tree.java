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

    public void printTree(Knot knot, int step) {
        String tabs = "";

        for (int i = 0; i < step; i++)
            tabs += " ";

        //System.out.println(tabs + knot.getName() + " ?");
        knot.getKnotDecision().forEach((key, value) -> {
            String arrows = "";
            for (int i = 0; i < step; i++)
                arrows += " -> ";

            if (value == KnotDecision.NEXT) {
                System.out.println(arrows + "IF " + knot.getName() + " IS " + key + " THEN:");

                printTree(knot.getNextKnot().get(key), step + 1);
            } else {
                System.out.println(arrows + "IF " + knot.getName() + " IS " + key + " THEN " + value.name());
            }
        });
    }
}
