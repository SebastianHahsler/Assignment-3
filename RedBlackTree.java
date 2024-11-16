public class RedBlackTree {
    private Node root;
    private final Node TNULL;

    public RedBlackTree() {
        TNULL = new Node("","","","");
        TNULL.isRed = false; // TNULL is allways back
        root = TNULL;
    }
    public Node getTNULL() {
        return TNULL;
    }
    public void insert(String productId, String name, String category, String price) {
        Node node = new Node(productId, name, category, price);
        node.left = TNULL;
        node.right = TNULL;
        node.parent = null;

        Node y = null;
        Node x = this.root;

        while (x != TNULL) {
            y = x;
            if (node.productId.compareTo(x.productId) < 0) {
                x = x.left;
            } else if (node.productId.compareTo(x.productId) > 0) {
                x = x.right;
            } else {
                System.out.println("Error: Product with ID " + productId + " already exists.");
                return;
            }
        }

        node.parent = y;
        if (y == null) {
            root = node; // Tree was empty, so node becomes the root
        } else if (node.productId.compareTo(y.productId) < 0) {
            y.left = node;
        } else {
            y.right = node;
        }

        node.isRed = true; // New nodes are always red initially
        balanceAfterInsert(node); // Rebalance the tree
    }


    private void balanceAfterInsert(Node node) {
        while (node != root && node.parent.isRed) {
            if (node.parent == node.parent.parent.right) {
                Node uncle = node.parent.parent.left;

                if (uncle.isRed) {
                    uncle.isRed = false;
                    node.parent.isRed = false;
                    node.parent.parent.isRed = true;
                    node = node.parent.parent;
                } else {
                    if (node == node.parent.left) {
                        node = node.parent;
                        rightRotate(node);
                    }
                    node.parent.isRed = false;
                    node.parent.parent.isRed = true;
                    leftRotate(node.parent.parent);
                }
            } else {
                Node uncle = node.parent.parent.right;

                if (uncle.isRed) {
                    uncle.isRed = false;
                    node.parent.isRed = false;
                    node.parent.parent.isRed = true;
                    node = node.parent.parent;
                } else {
                    if (node == node.parent.right) {
                        node = node.parent;
                        leftRotate(node);
                    }
                    node.parent.isRed = false;
                    node.parent.parent.isRed = true;
                    rightRotate(node.parent.parent);
                }
            }
        }
        root.isRed = false;  // root is always black
    }



    private void rightRotate(Node y){
        Node x = y.left;
        y.left = x.right;
        if(x.right != TNULL){
            x.right.parent = y;
        }
        x.parent = y.parent;
        if(y.parent == null){
            root = x;
        }else if(y == y.parent.right){
            y.parent.right = x;
            }else{
            y.parent.left = x;
        }
        x.right = y;
        y.parent = x;

        }
        private void leftRotate(Node x){
        Node y = x.right;
        x.right = y.left;
        if(y.left != TNULL){
            y.left.parent = x;
        }
        y.parent = x.parent;
        if(x.parent == null){
            root = y;
        } else if(x == x.parent.left){
            x.parent.left = y;
        }else{
            x.parent.right = y;
        }
        y.left = x;
        x.parent = y;
        }
        // Search by productId
        public Node search(String productId) {
            return searchHelper(this.root, productId);
        }

        // Helper
        private Node searchHelper(Node node, String productId) {
            if (node == TNULL || node == null) { // returns TNULL or null if not found
                return TNULL;
            }
            if (productId.equals(node.productId)) {
                return node; // found
            }

            if (productId.compareTo(node.productId) < 0) {
                return searchHelper(node.left, productId); // in the left subtree
            }
            return searchHelper(node.right, productId); // in the right subtree
        }


    // display product details if found
    public void searchProduct(String productId) {
        Node result = search(productId);
        if (result == TNULL) {
            System.out.println("Product with ID " + productId + " not found.");
        } else {
            System.out.println("Product ID: " + result.productId + ", Name: " + result.name +
                    ", Category: " + result.category + ", Price: " + result.price);
        }
    }
}
