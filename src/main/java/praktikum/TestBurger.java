package praktikum;

public class TestBurger {
    public static void main(String[] args) {
        Burger burger = new Burger();
        Bun bun = new Bun("Test Bun", 7.75f);
        burger.setBuns(bun);
        
        System.out.println("Receipt:");
        System.out.println(burger.getReceipt());
        System.out.println("Price: " + burger.getPrice());
    }
}
