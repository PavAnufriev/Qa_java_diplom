package praktikum;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(Parameterized.class)
public class BurgerTest {

    @Mock
    private Bun mockBun;

    @Mock
    private Ingredient mockIngredient1;

    @Mock
    private Ingredient mockIngredient2;

    @Mock
    private Ingredient mockIngredient3;

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
    @Parameterized.Parameters
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
    public void testSetBuns() {
        burger.setBuns(mockBun);
        assertEquals("Булочка должна быть установлена", mockBun, burger.bun);
    }

    @Test
    public void testAddIngredient() {
        burger.addIngredient(mockIngredient1);
        assertTrue("Ингредиент должен быть добавлен", burger.ingredients.contains(mockIngredient1));
        assertEquals("Должен быть 1 ингредиент", 1, burger.ingredients.size());
    }

    @Test
    public void testAddMultipleIngredients() {
        burger.addIngredient(mockIngredient1);
        burger.addIngredient(mockIngredient2);
        
        assertEquals("Должно быть 2 ингредиента", 2, burger.ingredients.size());
        assertTrue("Первый ингредиент должен быть в списке", burger.ingredients.contains(mockIngredient1));
        assertTrue("Второй ингредиент должен быть в списке", burger.ingredients.contains(mockIngredient2));
    }

    @Test
    public void testRemoveIngredient() {
        burger.addIngredient(mockIngredient1);
        burger.addIngredient(mockIngredient2);
        
        burger.removeIngredient(0);
        
        assertEquals("Должен остаться 1 ингредиент", 1, burger.ingredients.size());
        assertFalse("Первый ингредиент должен быть удален", burger.ingredients.contains(mockIngredient1));
        assertTrue("Второй ингредиент должен остаться", burger.ingredients.contains(mockIngredient2));
    }

    @Test
    public void testMoveIngredient() {
        burger.addIngredient(mockIngredient1);
        burger.addIngredient(mockIngredient2);
        burger.addIngredient(mockIngredient3);
        
        // Перемещаем ингредиент с позиции 0 на позицию 2
        burger.moveIngredient(0, 2);
        
        assertEquals("Количество ингредиентов не должно измениться", 3, burger.ingredients.size());
        assertEquals("Ингредиент должен быть на новой позиции", mockIngredient1, burger.ingredients.get(2));
        assertEquals("Второй ингредиент должен сдвинуться", mockIngredient2, burger.ingredients.get(0));
    }

    @Test
    public void testGetPrice() {
        burger.setBuns(mockBun);
        burger.addIngredient(mockIngredient1);
        burger.addIngredient(mockIngredient2);
        
        float actualPrice = burger.getPrice();
        
        assertEquals("Цена должна рассчитываться правильно", expectedTotalPrice, actualPrice, 0.01f);
        
        // Проверяем, что методы моков были вызваны
        verify(mockBun, times(1)).getPrice();
        verify(mockIngredient1, times(1)).getPrice();
        verify(mockIngredient2, times(1)).getPrice();
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
        
        // Проверяем содержимое чека
        assertTrue("Чек должен содержать название булочки вверху", receipt.contains("(==== " + bunName + " ====)"));
        assertTrue("Чек должен содержать название булочки внизу", receipt.contains("(==== " + bunName + " ====)"));
        assertTrue("Чек должен содержать первый ингредиент", receipt.contains("= filling Cheese ="));
        assertTrue("Чек должен содержать второй ингредиент", receipt.contains("= sauce Ketchup ="));
        assertTrue("Чек должен содержать цену", receipt.contains("Price: "));
        
        // Проверяем вызовы методов
        verify(mockBun, atLeast(2)).getName();
        verify(mockIngredient1, times(1)).getName();
        verify(mockIngredient1, times(1)).getType();
        verify(mockIngredient2, times(1)).getName();
        verify(mockIngredient2, times(1)).getType();
    }

    @Test
    public void testGetPriceWithoutIngredients() {
        burger.setBuns(mockBun);
        
        float actualPrice = burger.getPrice();
        float expectedPrice = bunPrice * 2; // Только цена булочек
        
        assertEquals("Цена без ингредиентов должна быть равна двойной цене булочки", 
                     expectedPrice, actualPrice, 0.01f);
    }

    @Test
    public void testGetReceiptWithoutIngredients() {
        burger.setBuns(mockBun);
        
        String receipt = burger.getReceipt();
        
        assertTrue("Чек должен содержать название булочки", receipt.contains(bunName));
        assertTrue("Чек должен содержать цену", receipt.contains("Price: "));
        assertFalse("Чек не должен содержать ингредиенты", receipt.contains("= filling") || receipt.contains("= sauce"));
    }
}
