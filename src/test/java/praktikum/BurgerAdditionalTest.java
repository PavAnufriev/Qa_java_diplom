package praktikum;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Дополнительные тесты для класса Burger без параметризации
 * для достижения 100% покрытия кода
 */
public class BurgerAdditionalTest {

    @Mock
    private Bun mockBun;

    @Mock
    private Ingredient mockIngredient1;

    @Mock
    private Ingredient mockIngredient2;

    @Mock
    private Ingredient mockIngredient3;

    private Burger burger;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        burger = new Burger();
    }

    @Test
    public void testInitialState() {
        assertNull("Изначально булочка должна быть null", burger.bun);
        assertNotNull("Список ингредиентов должен быть инициализирован", burger.ingredients);
        assertTrue("Список ингредиентов должен быть пустым", burger.ingredients.isEmpty());
    }

    @Test
    public void testRemoveIngredientFromMiddle() {
        burger.addIngredient(mockIngredient1);
        burger.addIngredient(mockIngredient2);
        burger.addIngredient(mockIngredient3);
        
        burger.removeIngredient(1); // Удаляем средний ингредиент
        
        assertEquals("Должно остаться 2 ингредиента", 2, burger.ingredients.size());
        assertEquals("Первый ингредиент должен остаться на месте", mockIngredient1, burger.ingredients.get(0));
        assertEquals("Третий ингредиент должен сдвинуться", mockIngredient3, burger.ingredients.get(1));
        assertFalse("Второй ингредиент должен быть удален", burger.ingredients.contains(mockIngredient2));
    }

    @Test
    public void testRemoveLastIngredient() {
        burger.addIngredient(mockIngredient1);
        burger.addIngredient(mockIngredient2);
        
        burger.removeIngredient(1); // Удаляем последний ингредиент
        
        assertEquals("Должен остаться 1 ингредиент", 1, burger.ingredients.size());
        assertEquals("Первый ингредиент должен остаться", mockIngredient1, burger.ingredients.get(0));
    }

    @Test
    public void testMoveIngredientToBeginning() {
        burger.addIngredient(mockIngredient1);
        burger.addIngredient(mockIngredient2);
        burger.addIngredient(mockIngredient3);
        
        // Перемещаем последний ингредиент в начало
        burger.moveIngredient(2, 0);
        
        assertEquals("Третий ингредиент должен быть первым", mockIngredient3, burger.ingredients.get(0));
        assertEquals("Первый ингредиент должен быть вторым", mockIngredient1, burger.ingredients.get(1));
        assertEquals("Второй ингредиент должен быть третьим", mockIngredient2, burger.ingredients.get(2));
    }

    @Test
    public void testMoveIngredientToEnd() {
        burger.addIngredient(mockIngredient1);
        burger.addIngredient(mockIngredient2);
        burger.addIngredient(mockIngredient3);
        
        // Перемещаем первый ингредиент в конец (позиция 2, так как у нас 3 элемента)
        burger.moveIngredient(0, 2);
        
        assertEquals("Второй ингредиент должен быть первым", mockIngredient2, burger.ingredients.get(0));
        assertEquals("Третий ингредиент должен быть вторым", mockIngredient3, burger.ingredients.get(1));
        assertEquals("Первый ингредиент должен быть третьим", mockIngredient1, burger.ingredients.get(2));
    }

    @Test
    public void testGetPriceWithManyIngredients() {
        when(mockBun.getPrice()).thenReturn(5.0f);
        when(mockIngredient1.getPrice()).thenReturn(2.0f);
        when(mockIngredient2.getPrice()).thenReturn(3.0f);
        when(mockIngredient3.getPrice()).thenReturn(1.5f);
        
        burger.setBuns(mockBun);
        burger.addIngredient(mockIngredient1);
        burger.addIngredient(mockIngredient2);
        burger.addIngredient(mockIngredient3);
        
        float expectedPrice = 5.0f * 2 + 2.0f + 3.0f + 1.5f; // 16.5
        float actualPrice = burger.getPrice();
        
        assertEquals("Цена должна учитывать все ингредиенты", expectedPrice, actualPrice, 0.01f);
    }

    @Test
    public void testGetReceiptWithDifferentIngredientTypes() {
        when(mockBun.getName()).thenReturn("Test Bun");
        when(mockBun.getPrice()).thenReturn(10.0f);
        
        when(mockIngredient1.getName()).thenReturn("Lettuce");
        when(mockIngredient1.getType()).thenReturn(IngredientType.FILLING);
        when(mockIngredient1.getPrice()).thenReturn(1.0f);
        
        when(mockIngredient2.getName()).thenReturn("Mayo");
        when(mockIngredient2.getType()).thenReturn(IngredientType.SAUCE);
        when(mockIngredient2.getPrice()).thenReturn(0.5f);
        
        burger.setBuns(mockBun);
        burger.addIngredient(mockIngredient1);
        burger.addIngredient(mockIngredient2);
        
        String receipt = burger.getReceipt();
        
        // Проверяем структуру чека
        assertTrue("Чек должен начинаться с булочки", receipt.startsWith("(==== Test Bun ====)"));
        assertTrue("Чек должен содержать цену", receipt.contains("Price: 21.5"));
        assertTrue("Чек должен содержать начинку", receipt.contains("= filling Lettuce ="));
        assertTrue("Чек должен содержать соус", receipt.contains("= sauce Mayo ="));
        
        // Проверяем правильный порядок строк
        int topBunIndex = receipt.indexOf("(==== Test Bun ====)");
        int fillingIndex = receipt.indexOf("= filling Lettuce =");
        int sauceIndex = receipt.indexOf("= sauce Mayo =");
        int bottomBunIndex = receipt.lastIndexOf("(==== Test Bun ====)");
        int priceIndex = receipt.indexOf("Price:");
        
        assertTrue("Начинка должна быть после верхней булочки", fillingIndex > topBunIndex);
        assertTrue("Соус должен быть после начинки", sauceIndex > fillingIndex);
        assertTrue("Нижняя булочка должна быть после соуса", bottomBunIndex > sauceIndex);
        assertTrue("Цена должна быть в конце", priceIndex > bottomBunIndex);
    }

    @Test
    public void testGetReceiptFormatting() {
        when(mockBun.getName()).thenReturn("Special Bun");
        when(mockBun.getPrice()).thenReturn(7.75f);
        
        burger.setBuns(mockBun);
        
        String receipt = burger.getReceipt();
        
        // Проверяем наличие переносов строк
        assertTrue("Чек должен содержать переносы строк", receipt.contains("\n"));
        
        // Проверяем формат цены (число с плавающей точкой) с учетом многострочного чека
        assertTrue("Цена должна быть в правильном формате", receipt.matches("(?s).*Price: \\d+\\.\\d+.*"));
    }

    @Test
    public void testIngredientTypeToString() {
        when(mockIngredient1.getType()).thenReturn(IngredientType.FILLING);
        when(mockIngredient1.getName()).thenReturn("Test Filling");
        when(mockBun.getName()).thenReturn("Test Bun");
        when(mockBun.getPrice()).thenReturn(5.0f);
        when(mockIngredient1.getPrice()).thenReturn(2.0f);
        
        burger.setBuns(mockBun);
        burger.addIngredient(mockIngredient1);
        
        String receipt = burger.getReceipt();
        
        // Проверяем, что тип ингредиента правильно преобразуется в нижний регистр
        assertTrue("Тип ингредиента должен быть в нижнем регистре", receipt.contains("= filling Test Filling ="));
        assertFalse("Тип ингредиента не должен быть в верхнем регистре", receipt.contains("= FILLING Test Filling ="));
    }
}
