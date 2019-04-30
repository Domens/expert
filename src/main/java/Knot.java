import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Knot {
    private Column column;
    private HashMap<String, KnotDecision> knotDecision = new HashMap<>();
    private List<String> uniqParametres;
    private HashMap<String, Knot> nextKnot = new HashMap<>();
    private String name;

    public Knot(Database database) {
        if (database.getColumns().size() == 0) {
            column = null;
        } else {
            column = findForKnot(database.getColumns());
            uniqParametres = column.getParametres().stream().distinct().collect(Collectors.toList());
            for (String str : uniqParametres) {
                knotDecision.put(str, knotTribunal(database, str));
            }
        }
    }

    private KnotDecision knotTribunal(Database database, String string) {
        if (this.column.countYes(column.getParametres(), database, string) == 0) {
            return KnotDecision.NO;
        } else if (this.column.countNo(column.getParametres(), database, string) == 0) {
            return KnotDecision.YES;
        } else if (column.getSize() == 1) {
            return KnotDecision.END;
        } else {
            return KnotDecision.NEXT;
        }
    }

    private Column findForKnot(List<Column> columns) {
        double maxI = columns.get(0).getI();
        Column tempCol = columns.get(0);
        for (Column col : columns) {
            if (col.getI() > maxI) {
                maxI = col.getI();
                tempCol = col;
            }
        }
        this.name = tempCol.getName();
        return tempCol;
    }

    public Column getColumn() {
        return column;
    }

    public HashMap<String, Knot> getNextKnot() {
        return nextKnot;
    }

    public void addKnot(String str, Knot knot) {
        nextKnot.put(str, knot);
    }

    public String getName() {
        return name;
    }

    public List<String> getUniqParametres() {
        return uniqParametres;
    }

    public HashMap<String, KnotDecision> getKnotDecision() {
        return knotDecision;
    }
}
