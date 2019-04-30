public class Main {

    public static void main(String[] args) {
        Database database = new Database();
        database.fill("C:\\Files\\java\\expert\\out\\production\\expert\\com\\company\\knowledgeBase.txt");
        Tree tree = new Tree(database);
    }
}
