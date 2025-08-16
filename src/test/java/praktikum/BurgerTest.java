package praktikum;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.assertj.core.api.SoftAssertions;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(Parameterized.class)
public class BurgerTest {

    @Mock
    private Bun mockBun;

    @Mock
    private Ingredient mockIngredient1;

    @Mock
    private Ingredient mockIngredient2;

    private Burger burger;

    // Параметры для параметризованных тестов
    private final String bunName;
    private final float bunPrice;
    private final float ingredient1Price;
    private final float ingredient2Price;
    private final float expectedTotalPrice;

    // Конструктор для параметризации
    public BurgerTest(String bunName, float bunPrice, float ingredient1Price, float ingredient2Price, float expectedTotalPrice) {
        this.bunName = bunName;
        this.bunPrice = bunPrice;
        this.ingredient1Price = ingredient1Price;
        this.ingredient2Price = ingredient2Price;
        this.expectedTotalPrice = expectedTotalPrice;
    }

    // Данные для параметризации
    @Parameterized.Parameters(name = "Тестовые данные: {0}, булочка={1}, ингредиент1={2}, ингредиент2={3}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"Sesame Bun", 10.0f, 5.0f, 3.0f, 28.0f}, // 10*2 + 5 + 3 = 28
                {"Whole Wheat Bun", 15.0f, 7.5f, 4.5f, 42.0f}, // 15*2 + 7.5 + 4.5 = 42
                {"Brioche Bun", 12.5f, 6.0f, 2.0f, 33.0f}, // 12.5*2 + 6 + 2 = 33
                {"Plain Bun", 8.0f, 4.0f, 1.5f, 21.5f} // 8*2 + 4 + 1.5 = 21.5
        });
    }

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        burger = new Burger();

        // Настройка моков
        when(mockBun.getName()).thenReturn(bunName);
        when(mockBun.getPrice()).thenReturn(bunPrice);
        when(mockIngredient1.getPrice()).thenReturn(ingredient1Price);
        when(mockIngredient2.getPrice()).thenReturn(ingredient2Price);
    }

    @Test
    public void testGetPrice() {
        burger.setBuns(mockBun);
        burger.addIngredient(mockIngredient1);
        burger.addIngredient(mockIngredient2);
        
        float actualPrice = burger.getPrice();
        
    assertEquals("Цена должна рассчитываться правильно", expectedTotalPrice, actualPrice, 0.01f);
    }

    @Test
    public void testGetReceipt() {
        // Настройка дополнительных моков для чека
        when(mockIngredient1.getName()).thenReturn("Cheese");
        when(mockIngredient1.getType()).thenReturn(IngredientType.FILLING);
        when(mockIngredient2.getName()).thenReturn("Ketchup");
        when(mockIngredient2.getType()).thenReturn(IngredientType.SAUCE);
        
        burger.setBuns(mockBun);
        burger.addIngredient(mockIngredient1);
        burger.addIngredient(mockIngredient2);
        
    String receipt = burger.getReceipt();

    SoftAssertions softly = new SoftAssertions();
    softly.assertThat(receipt).as("верхняя булочка").contains("(==== " + bunName + " ====)");
    softly.assertThat(receipt).as("нижняя булочка").contains("(==== " + bunName + " ====)");
    softly.assertThat(receipt).as("начинка").contains("= filling Cheese =");
    softly.assertThat(receipt).as("соус").contains("= sauce Ketchup =");
    softly.assertThat(receipt).as("цена").contains("Price: ");
    softly.assertAll();
    }
}
