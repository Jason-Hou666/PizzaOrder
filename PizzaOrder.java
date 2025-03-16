import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

enum PizzaSelection {
    PEPPERONI("Pepperoni", "Lots of pepperoni and extra cheese", 18),
    HAWAIIAN("Hawaiian", "Pineapple, ham, and extra cheese", 22),
    VEGGIE("Veggie", "Green pepper, onion, tomatoes, mushroom, and black olives", 25),
    BBQ_CHICKEN("BBQ Chicken", "Chicken in BBQ sauce, bacon, onion, green pepper, and cheddar cheese", 35),
    EXTRAVAGANZA("Extravaganza", "Pepperoni, ham, Italian sausage, beef, onions, green pepper, mushrooms, black olives, and extra cheese", 45);

    private final String pizzaName;
    private final String pizzaToppings;
    private final double price;

    PizzaSelection(String name, String toppings, double price) {
        this.pizzaName = name;
        this.pizzaToppings = toppings;
        this.price = price;
    }

    public String getPizzaName() { return pizzaName; }
    public String getPizzaToppings() { return pizzaToppings; }
    public double getPrice() { return price; }

    @Override
    public String toString() {
        return pizzaName + " Pizza with " + pizzaToppings + ", for €" + price;
    }
}

enum PizzaToppings {
    HAM("Ham", 2),
    PEPPERONI("Pepperoni", 2),
    BEEF("Beef", 2),
    CHICKEN("Chicken", 2),
    SAUSAGE("Sausage", 2),
    PINEAPPLE("Pineapple", 1),
    ONION("Onion", 0.5),
    TOMATOES("Tomatoes", 0.4),
    GREEN_PEPPER("Green Pepper", 0.5),
    BLACK_OLIVES("Black Olives", 0.5),
    SPINACH("Spinach", 0.5),
    CHEDDAR_CHEESE("Cheddar Cheese", 0.8),
    MOZZARELLA_CHEESE("Mozzarella Cheese", 0.8),
    FETA_CHEESE("Feta Cheese", 1),
    PARMESAN_CHEESE("Parmesan Cheese", 1);

    private final String topping;
    private final double toppingPrice;

    PizzaToppings(String topping, double price) {
        this.topping = topping;
        this.toppingPrice = price;
    }

    public String getTopping() { return topping; }
    public double getToppingPrice() { return toppingPrice; }

    @Override
    public String toString() {
        return topping + " (€" + toppingPrice + ")";
    }
}

enum PizzaSize {
    LARGE("Large", 10),
    MEDIUM("Medium", 5),
    SMALL("Small", 0);

    private final String pizzaSize;
    private final double addToPizzaPrice;

    PizzaSize(String size, double price) {
        this.pizzaSize = size;
        this.addToPizzaPrice = price;
    }

    public String getPizzaSize() { return pizzaSize; }
    public double getAddToPizzaPrice() { return addToPizzaPrice; }

    @Override
    public String toString() {
        return pizzaSize + " (€" + addToPizzaPrice + ")";
    }
}

enum SideDish {
    CALZONE("Calzone", 15),
    CHICKEN_PUFF("Chicken Puff", 20),
    MUFFIN("Muffin", 12),
    NOTHING("No side dish", 0);

    private final String sideDishName;
    private final double addToPizzaPrice;

    SideDish(String name, double price) {
        this.sideDishName = name;
        this.addToPizzaPrice = price;
    }

    public String getSideDishName() { return sideDishName; }
    public double getAddToPizzaPrice() { return addToPizzaPrice; }

    @Override
    public String toString() {
        return sideDishName + " (€" + addToPizzaPrice + ")";
    }
}

enum Drinks {
    COCA_COLA("Coca Cola", 8),
    COCOA_DRINK("Cocoa Drink", 10),
    NOTHING("No drinks", 0);

    private final String drinkName;
    private final double addToPizzaPrice;

    Drinks(String name, double price) {
        this.drinkName = name;
        this.addToPizzaPrice = price;
    }

    public String getDrinkName() { return drinkName; }
    public double getAddToPizzaPrice() { return addToPizzaPrice; }

    @Override
    public String toString() {
        return drinkName + " (€" + addToPizzaPrice + ")";
    }
}

public class PizzaOrder {
    private static final double PIZZA_BASE_PRICE = 10.0;
    private String[] pizzasOrdered = new String[10];
    private String[] pizzaSizesOrdered = new String[10];
    private String[] sideDishesOrdered = new String[20];
    private String[] drinksOrdered = new String[20];
    private int orderCount = 0;
    private double totalOrderPrice = 0.0;
    private Scanner scanner = new Scanner(System.in);

    public void takeOrder() {
        boolean ordering = true;
        while (ordering && orderCount < 10) {
            displayMenu();
            int choice = scanner.nextInt();
            
            if (choice >= 1 && choice <= 5) {
                processPredefinedPizza(choice - 1);
            } else if (choice == 6) {
                processCustomPizza();
            }

            processSize();
            processSideDish();
            processDrinks();

            System.out.println("Do you want to order more? (yes/no)");
            ordering = scanner.next().toLowerCase().equals("yes");
            orderCount++;
        }
    }

    private void displayMenu() {
        System.out.println("Welcome to Slice-o-Heaven Pizzeria. Here's what we serve:");
        int i = 1;
        for (PizzaSelection pizza : PizzaSelection.values()) {
            System.out.println(i++ + ". " + pizza.toString());
        }
        System.out.println(i + ". Custom Pizza with a maximum of 10 toppings that you choose");
        System.out.print("Please enter your choice (1 - " + i + "): ");
    }

    private void processPredefinedPizza(int index) {
        PizzaSelection pizza = PizzaSelection.values()[index];
        pizzasOrdered[orderCount] = pizza.toString();
        totalOrderPrice += pizza.getPrice();
    }

    private void processCustomPizza() {
        System.out.println("\nAvailable toppings:");
        PizzaToppings[] toppings = PizzaToppings.values();
        for (int i = 0; i < toppings.length; i++) {
            System.out.println((i + 1) + ". " + toppings[i]);
        }

        List<PizzaToppings> selectedToppings = new ArrayList<>();
        double customPrice = PIZZA_BASE_PRICE;

        System.out.println("\nEnter up to 10 topping numbers (enter 0 to finish):");
        while (selectedToppings.size() < 10) {
            int choice = scanner.nextInt();
            if (choice == 0) break;
            if (choice >= 1 && choice <= toppings.length) {
                PizzaToppings selected = toppings[choice - 1];
                selectedToppings.add(selected);
                customPrice += selected.getToppingPrice();
            }
        }

        StringBuilder customPizzaDesc = new StringBuilder("Custom Pizza with ");
        for (int i = 0; i < selectedToppings.size(); i++) {
            customPizzaDesc.append(selectedToppings.get(i).getTopping());
            if (i < selectedToppings.size() - 1) {
                customPizzaDesc.append(", ");
            }
        }
        customPizzaDesc.append(", for €").append(String.format("%.2f", customPrice));
        
        pizzasOrdered[orderCount] = customPizzaDesc.toString();
        totalOrderPrice += customPrice;
    }

    private void processSize() {
        System.out.println("\nChoose your pizza size:");
        PizzaSize[] sizes = PizzaSize.values();
        for (int i = 0; i < sizes.length; i++) {
            System.out.println((i + 1) + ". " + sizes[i]);
        }
        
        int choice = scanner.nextInt();
        if (choice >= 1 && choice <= sizes.length) {
            PizzaSize selectedSize = sizes[choice - 1];
            pizzaSizesOrdered[orderCount] = selectedSize.toString();
            totalOrderPrice += selectedSize.getAddToPizzaPrice();
        }
    }

    private void processSideDish() {
        System.out.println("\nChoose your side dish:");
        SideDish[] sides = SideDish.values();
        for (int i = 0; i < sides.length; i++) {
            System.out.println((i + 1) + ". " + sides[i]);
        }
        
        int choice = scanner.nextInt();
        if (choice >= 1 && choice <= sides.length) {
            SideDish selectedSide = sides[choice - 1];
            sideDishesOrdered[orderCount] = selectedSide.toString();
            totalOrderPrice += selectedSide.getAddToPizzaPrice();
        }
    }

    private void processDrinks() {
        System.out.println("\nChoose your drink:");
        Drinks[] drinks = Drinks.values();
        for (int i = 0; i < drinks.length; i++) {
            System.out.println((i + 1) + ". " + drinks[i]);
        }
        
        int choice = scanner.nextInt();
        if (choice >= 1 && choice <= drinks.length) {
            Drinks selectedDrink = drinks[choice - 1];
            drinksOrdered[orderCount] = selectedDrink.toString();
            totalOrderPrice += selectedDrink.getAddToPizzaPrice();
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Thank you for dining with Slice-o-Heaven Pizzeria. Your order details are as follows:\n");
        
        for (int i = 0; i < orderCount; i++) {
            sb.append(i + 1).append(". ").append(pizzasOrdered[i]).append("\n");
            sb.append(pizzaSizesOrdered[i]).append("\n");
            sb.append(sideDishesOrdered[i]).append("\n");
            sb.append(drinksOrdered[i]).append("\n\n");
        }
        
        sb.append("ORDER TOTAL: €").append(String.format("%.2f", totalOrderPrice));
        return sb.toString();
    }
}
