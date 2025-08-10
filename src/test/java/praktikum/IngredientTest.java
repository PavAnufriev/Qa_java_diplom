package praktikum;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class IngredientTest {

    private final IngredientType type;
    private final String name;
    private final float price;

    public IngredientTest(IngredientType type, String name, float price) {
        this.type = type;
        this.name = name;
        this.price = price;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {IngredientType.SAUCE, "Ketchup", 2.5f},
                {IngredientType.SAUCE, "Mayo", 3.0f},
                {IngredientType.FILLING, "Cheese", 5.5f},
                {IngredientType.FILLING, "Lettuce", 1.5f},
                {IngredientType.FILLING, "Tomato", 2.0f},
                {IngredientType.SAUCE, "BBQ Sauce", 4.0f}
        });
    }

    @Test
    public void testIngredientCreation() {
        Ingredient ingredient = new Ingredient(type, name, price);
        
        assertEquals("Тип ингредиента должен соответствовать переданному", type, ingredient.getType());
        assertEquals("Имя ингредиента должно соответствовать переданному", name, ingredient.getName());
        assertEquals("Цена ингредиента должна соответствовать переданной", price, ingredient.getPrice(), 0.01f);
        
        assertEquals("Поле type должно быть установлено", type, ingredient.type);
        assertEquals("Поле name должно быть установлено", name, ingredient.name);
        assertEquals("Поле price должно быть установлено", price, ingredient.price, 0.01f);
    }

    @Test
    public void testIngredientGetters() {
        Ingredient ingredient = new Ingredient(type, name, price);
        
        IngredientType actualType = ingredient.getType();
        String actualName = ingredient.getName();
        float actualPrice = ingredient.getPrice();
        
        assertNotNull("getType() не должен возвращать null", actualType);
        assertNotNull("getName() не должен возвращать null", actualName);
        
        assertEquals("getType() должен возвращать правильный тип", type, actualType);
        assertEquals("getName() должен возвращать правильное имя", name, actualName);
        assertEquals("getPrice() должен возвращать правильную цену", price, actualPrice, 0.01f);
    }
}
