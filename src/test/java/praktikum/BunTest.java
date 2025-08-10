package praktikum;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class BunTest {

    private final String name;
    private final float price;

    public BunTest(String name, float price) {
        this.name = name;
        this.price = price;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"Sesame Bun", 10.5f},
                {"Whole Wheat Bun", 12.0f},
                {"Brioche Bun", 15.25f},
                {"Plain Bun", 8.75f}
        });
    }

    @Test
    public void testBunCreation() {
        Bun bun = new Bun(name, price);
        
        assertEquals("Имя булочки должно соответствовать переданному", name, bun.getName());
        assertEquals("Цена булочки должна соответствовать переданной", price, bun.getPrice(), 0.01f);
        assertEquals("Поле name должно быть установлено", name, bun.name);
        assertEquals("Поле price должно быть установлено", price, bun.price, 0.01f);
    }

    @Test
    public void testBunGetters() {
        Bun bun = new Bun(name, price);
        
        String actualName = bun.getName();
        float actualPrice = bun.getPrice();
        
        assertNotNull("getName() не должен возвращать null", actualName);
        assertEquals("getName() должен возвращать правильное имя", name, actualName);
        assertEquals("getPrice() должен возвращать правильную цену", price, actualPrice, 0.01f);
    }
}
