import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
public class apiTest {
    // Base API URL
    private static final String BASE_URL = "https://dummyjson.com/products";

    @Test
    public void validateAPIAndRetrieveData() {
        // Send API Request
        RequestSpecification request = RestAssured.given();
        Response response = request.get(BASE_URL);

        // Validating the  API Response
        Assert.assertEquals(response.getStatusCode(), 200, "API did not return status 200");
        System.out.println("API Response Code: " + response.getStatusCode());
        System.out.println("API Response Time: " + response.getTime() + " ms");

        // Parse Response JSON
        JSONObject jsonResponse = new JSONObject(response.getBody().asString());
        JSONArray products = jsonResponse.getJSONArray("products");

        // Initialize Lists to Store Selected Products
        List<JSONObject> groceries = new ArrayList<>();
        List<JSONObject> beauty = new ArrayList<>();

        // Filter Products Based on Conditions
        for (int i = 0; i < products.length(); i++) {
            JSONObject product = products.getJSONObject(i);
            String category = product.getString("category");
            double price = product.getDouble("price");

            if (!category.isEmpty()) {
                if (category.equals("groceries") && price <= 5 && groceries.size() < 3) {
                    groceries.add(product);
                } else if (category.equals("beauty") && price >= 5 && price <= 14 && beauty.size() < 3) {
                    beauty.add(product);
                }
            }


            if (groceries.size() == 3 && beauty.size() == 3) {
                break;
            }
        }

        // Validate the Product Selection
        if (groceries.size() < 3 || beauty.size() < 3) {
            Assert.fail("Not enough products found that match the criteria");
        }

        // Performing Calculations
        double totalPrice = 0;
        double groceriesTotal = 0, beautyTotal = 0;

        System.out.println("\nRetrieved Products:");

        for (JSONObject product : groceries) {
            printProductDetails(product);
            totalPrice += product.getDouble("price");
            groceriesTotal += product.getDouble("price");
        }

        for (JSONObject product : beauty) {
            printProductDetails(product);
            totalPrice += product.getDouble("price");
            beautyTotal += product.getDouble("price");
        }

        double avgPrice = totalPrice / 6;
        double avgGroceries = groceriesTotal / 3;
        double avgBeauty = beautyTotal / 3;

        // Display the results
        System.out.println("\nSummary of Calculations:");
        System.out.println("Total Price of all products: " + totalPrice);
        System.out.println("Average Price of all products: " + avgPrice);
        System.out.println("Average Price of Groceries: " + avgGroceries);
        System.out.println("Average Price of Beauty Products: " + avgBeauty);
    }

    // Print Product Details
    private void printProductDetails(JSONObject product) {
        System.out.println("ID: " + product.getInt("id"));
        System.out.println("Title: " + product.getString("title"));
        System.out.println("Category: " + product.getString("category"));
        System.out.println("Price: $" + product.getDouble("price"));
        System.out.println("----------------------------");
    }
}

