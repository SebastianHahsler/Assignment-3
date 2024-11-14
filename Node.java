public class Node {
    String productId;
    String name;
    String category;
    double minPrice;
    double maxPrice;
    Node left, right, parent;
    boolean isRed;

    public Node(String productId, String name, String category, double minPrice, double maxPrice) {
        this.productId = productId;
        this.name = name;
        this.category = category;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.isRed = true; // New nodes are always red
        this.left = this.right = this.parent = null;
    }
}
