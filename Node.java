public class Node {
    String productId;
    String name;
    String category;
    String price; // Store price as a string
    Node left, right, parent;
    boolean isRed;

    public Node(String productId, String name, String category, String price) {
        this.productId = productId;
        this.name = name;
        this.category = category;
        this.price = price;
        this.isRed = true; // New nodes are red initially
        this.left = this.right = this.parent = null;
    }
}
