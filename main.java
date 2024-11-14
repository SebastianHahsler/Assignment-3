import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int ernum = 0;
        int icnum = 0;

        // Lists to store entries with errors and incomplete entries
        List<String> errorEntries = new ArrayList<>();
        List<String> incompleteEntries = new ArrayList<>();

        //Initialize the Red-Black Tree
        RedBlackTree tree = new RedBlackTree();

        //Read and insert products
        String csvFile = "amazon-product-data.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            br.readLine(); // Skip the header line

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)"); // Handles commas inside quotes
                if (data.length < 4) {
                    incompleteEntries.add(line); // Adds to incomplete
                    icnum++;
                    continue;
                }

                String productId = data[0].trim().toLowerCase();
                String name = data[1].trim();
                String category = data[2].trim();
                String priceStr = data[3].replaceAll("[$,]", "").replaceAll("\\s+", " ").trim();

                double minPrice = 0.0, maxPrice = 0.0;

                try {
                    if (priceStr.contains("-")) { //handles price ranges
                        String[] rangePrices = priceStr.split("-");
                        minPrice = Double.parseDouble(rangePrices[0].trim());
                        maxPrice = Double.parseDouble(rangePrices[1].trim());
                    } else {
                        String[] priceParts = priceStr.split(" ");
                        if (priceParts.length == 1) {
                            minPrice = maxPrice = Double.parseDouble(priceParts[0]);
                        } else {
                            minPrice = Double.parseDouble(priceParts[0]);
                            maxPrice = Double.parseDouble(priceParts[1]);
                        }
                    }
                } catch (NumberFormatException e) {
                    errorEntries.add(line); // Adds errors
                    ernum++;
                    continue;
                }

                tree.insert(productId, name, category, minPrice, maxPrice);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // handles user input 
        Scanner scanner = new Scanner(System.in);

        // user error handling prompt
        while (true) {
            System.out.println("Number of entries with errors: " + ernum);
            System.out.println("Number of incomplete entries: " + icnum);
            System.out.print("Would you like to view entries with errors (type 'Errors'), incomplete entries (type 'Incomplete'), or neither (type 'None')? ");
            String choice = scanner.nextLine().trim().toLowerCase();

            if (choice.equals("errors")) {
                System.out.println("Entries with errors:");
                errorEntries.forEach(System.out::println);
            } else if (choice.equals("incomplete")) {
                System.out.println("Incomplete entries:");
                incompleteEntries.forEach(System.out::println);
            }
            else if (choice.equals("none")) {
                break;
            }
            else {System.out.println("Invalid input. Please enter 'Errors', 'Incomplete', or 'None'.");}
        }
        // user search/insertion prompt 
        while (true) {
            System.out.print("If you would like to search, enter 'Search'. If you would like to insert, enter 'Insert'. If you would like to exit, enter 'Exit': ");
            String action = scanner.nextLine().trim().toLowerCase();

            if (action.equals("search")) {
                // User Search
                System.out.print("Enter product ID to search: ");
                String searchId = scanner.nextLine().trim().toLowerCase();
                System.out.println("Searching for product with ID: " + searchId);
                tree.searchProduct(searchId);
            } else if (action.equals("insert")) {
                System.out.print("Enter product ID to insert: ");
                String productId = scanner.nextLine().trim().toLowerCase();

                // Dupe Check
                if (tree.search(productId) != tree.getTNULL()) {
                    System.out.println("Error: Product with ID " + productId + " already exists.");
                    continue;
                }

                System.out.print("Enter product name: ");
                String name = scanner.nextLine().trim();
                System.out.print("Enter product category: ");
                String category = scanner.nextLine().trim();
                System.out.print("Enter product price (or range as 'min-max'): ");
                String priceStr = scanner.nextLine().trim().replace("$", "");

                double minPrice, maxPrice;
                try {
                    if (priceStr.contains("-")) {
                        String[] prices = priceStr.split("-");
                        minPrice = Double.parseDouble(prices[0].trim());
                        maxPrice = Double.parseDouble(prices[1].trim());
                    } else {
                        minPrice = maxPrice = Double.parseDouble(priceStr);
                    }
                    tree.insert(productId, name, category, minPrice, maxPrice);
                    System.out.println("Product inserted successfully!");
                } catch (NumberFormatException e) {
                    System.out.println("Invalid price format. Skipping insertion.");
                }
            } else if (action.equals("exit")) {
                System.out.println("Exiting the program.");
                break;
            } else {
                System.out.println("Invalid input. Please enter 'Search', 'Insert', or 'Exit'.");
            }
        }
        scanner.close();
    }
}
