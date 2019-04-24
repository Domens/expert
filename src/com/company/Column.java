package com.company;

import java.util.ArrayList;
import java.util.List;

public class Column {
    private String name;
    private List<String> parametres = new ArrayList<>();
    private Database database;
    private double I;

    public Column(String name, Database database) {
        this.name = name;
        this.database = database;
    }


    public double getI() {
        return I;
    }

    public void addParametres(String string) {
        parametres.add(string);
    }

    public int getSize() {
        return parametres.size();
    }

    public String getName() {
        return name;
    }

    public List<String> getParametres() {
        return parametres;
    }

    public void setI(double I) {
        this.I = I;
    }

    public double calc_i() {
        List<String> temp = new ArrayList<>();
        double result = H(null);
        for (String i : parametres) {
            if (!temp.contains(i)) {
                temp.add(i);
                result -= H(i);
            }
        }
        return result;
    }

    private double H(String string) {
        int x = countYes(parametres, database, string);
        int y = countNo(parametres, database, string);
        if (x == 0 || y == 0) return 0;

        double a = x / (double) (x + y);
        double b = y / (double) (x + y);

        return (-a * (Math.log(a) / Math.log(2)) -
                b * (Math.log(b) / Math.log(2))) *
                (double) (x + y) / (double) (countNo(parametres, database, null) + countYes(parametres, database, null));
    }

    public int countYes(List<String> parametres, Database database, String parametr) {
        int result = 0;
        for (int i = parametres.size() - 1; i >= 0; i--) {
            if ((parametres.get(i).equals(parametr) || parametr == null) && database.getDecisionColumn().getParametres().get(i).equals("+"))
                result++;
        }
        return result;
    }

    public int countNo(List<String> parametres, Database database, String parametr) {
        int result = 0;
        for (int i = parametres.size() - 1; i >= 0; i--) {
            if ((parametres.get(i).equals(parametr) || parametr == null) && database.getDecisionColumn().getParametres().get(i).equals("-"))
                result++;
        }
        return result;
    }

}
