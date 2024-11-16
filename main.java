import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void readFileAndParseData(RedBlackTree tree, String csvFile) {

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            br.readLine(); // Skip header line

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");

                // Assign default values for incomplete data
                String productId = (data.length > 0 && !data[0].trim().isEmpty()) ? data[0].trim().toLowerCase() : "N/A";
                String name = (data.length > 1 && !data[1].trim().isEmpty()) ? data[1].trim() : "N/A";
                String category = (data.length > 2 && !data[2].trim().isEmpty()) ? data[2].trim() : "N/A";
                String priceStr = (data.length > 3 && !data[3].trim().isEmpty()) ? data[3].trim().replaceAll("\\s+", " ") : "N/A";

                // Insert into the tree, even with default values
                tree.insert(productId, name, category, priceStr);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Parsing complete.");
    }

    public static void insertProduct(RedBlackTree tree, Scanner scanner) {
        System.out.print("Enter product ID: ");
        String productId = scanner.nextLine().trim().toLowerCase();
        if (productId.isEmpty()) productId = "N/A";

        System.out.print("Enter product name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) name = "N/A";

        System.out.print("Enter product category: ");
        String category = scanner.nextLine().trim();
        if (category.isEmpty()) category = "N/A";

        System.out.print("Enter product price: ");
        String price = scanner.nextLine().trim().replaceAll("\\s+", " ");
        if (price.isEmpty()) price = "N/A";

        if (tree.search(productId) != tree.getTNULL()) {
            System.out.println("Error: Product with ID " + productId + " already exists.");
        } else {
            tree.insert(productId, name, category, price);
            System.out.println("Product inserted successfully!");
        }
    }

    public static void main(String[] args) {
        RedBlackTree tree = new RedBlackTree();
        String csvFile = "amazon-product-data.csv";

        // Step 1: Read and parse data
        readFileAndParseData(tree, csvFile);

        Scanner scanner = new Scanner(System.in);

        // Step 2: Loop for user operations
        while (true) {
            System.out.print("Enter 'Search', 'Insert', or 'Exit': ");
            String action = scanner.nextLine().trim().toLowerCase();

            if (action.equals("search")) {
                System.out.print("Enter product ID to search: ");
                String searchId = scanner.nextLine().trim().toLowerCase();
                tree.searchProduct(searchId);

            } else if (action.equals("insert")) {
                insertProduct(tree, scanner);

            } else if (action.equals("exit")) {
                System.out.println("Exiting program.");
                break;

            } else {
                System.out.println("Invalid input. Please enter 'Search', 'Insert', or 'Exit'.");
            }
        }

        scanner.close();
    }
}
