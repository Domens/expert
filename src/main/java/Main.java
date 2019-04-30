public class Main {

    public static void main(String[] args) {
        Database database = new Database();
        database.fill("D:\\projects\\expert\\src\\main\\java\\knowledgeBase.txt");
        Tree tree = new Tree(database);

        tree.printTree(tree.getStartKnot());
    }
}

