package com.company;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Database {
    private List<Column> columns = new ArrayList<>();
    private Column decisionCol;

    public List<Column> getColumns() {
        return columns;
    }

    public Column getDecisionColumn() {
        return decisionCol;
    }

    public void setColumnsList(List<Column> columns) {
        this.columns = columns;
    }

    public void setDecisionCol(Column decisionCol) {
        this.decisionCol = decisionCol;
    }

    public Database generateWith(String nameCol, String param) {
        Database newDatabase = new Database();
        List<Column> newCols = new ArrayList<>();
        Column newDecCol = new Column("decision", newDatabase);

        for (Column col : columns) {
            if (!col.getName().equals(nameCol)) {
                newCols.add(new Column(col.getName(), newDatabase));
            }
        }

        int index = 0;
        for (int i = columns.size() - 1; i >= 0; i--) {
            if (columns.get(i).getName().equals(nameCol)) {
                index = i;
                break;
            }
        }

        for (int a = columns.get(index).getParametres().size() - 1; a >= 0; a--) {//цикл по параметрам
            if (columns.get(index).getParametres().get(a).equals(param)) {
                newDecCol.addParametres(decisionCol.getParametres().get(a));
                for (int b = newCols.size() - 1; b >= 0; b--) {//цикл проходящий по всем в новосозданном списке
                    for (int c = columns.size() - 1; c >= 0; c--) {//цикл для столпцов в старом списке
                        if (newCols.get(b).getName().equals(columns.get(c).getName())) {
                            newCols.get(b).addParametres(columns.get(c).getParametres().get(a));
                            break;
                        }
                    }
                }
            }
        }
        newDatabase.setColumnsList(newCols);
        newDatabase.setDecisionCol(newDecCol);
        for (Column col : newCols) {
            col.setI(col.calc_i());
        }
        return newDatabase;
    }

    public boolean fill(String doc) {
        try (FileReader reader = new FileReader(doc)) {
            //повторять, пока не достигнем decision, после него остановиться
            Scanner scan = new Scanner(reader);
            while (scan.hasNextLine()) {
                String name = scan.next();

                Column tempCol = new Column(name, this);
                columns.add(tempCol);

                while (scan.hasNext()) {
                    String tempParam = scan.next();
                    if (tempParam.equals("end")) break;
                    else tempCol.addParametres(tempParam);
                }
                if (columns.get(0).getSize() != tempCol.getSize()) {
                    System.out.println("Not enough parametrs");
                    return false;
                }
            }
            decisionCol = columns.get(columns.size() - 1);
            columns.remove(columns.size() - 1);

            for (Column col : columns) {
                col.setI(col.calc_i());
            }

            scan.close();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
