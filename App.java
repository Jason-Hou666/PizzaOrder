public class App {
    public static void main(String[] args) {
        PizzaOrder order = new PizzaOrder();
        order.takeOrder();
        System.out.println(order.toString());
    }
}
