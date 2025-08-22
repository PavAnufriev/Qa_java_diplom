package praktikum;

import org.junit.Before;
import org.junit.Test;
import org.assertj.core.api.SoftAssertions;
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
    public void testInitialStateBunIsNull() {
        assertNull("Изначально булочка должна быть null", burger.bun);
    }

    @Test
    public void testInitialStateIngredientsNotNull() {
        assertNotNull("Список ингредиентов должен быть инициализирован", burger.ingredients);
    }

    @Test
    public void testInitialStateIngredientsEmpty() {
        assertTrue("Список ингредиентов должен быть пустым", burger.ingredients.isEmpty());
    }

    @Test
    public void testRemoveIngredientFromMiddleSize() {
        burger.addIngredient(mockIngredient1);
        burger.addIngredient(mockIngredient2);
        burger.addIngredient(mockIngredient3);
        
        burger.removeIngredient(1); // Удаляем средний ингредиент
        
        assertEquals("Должно остаться 2 ингредиента", 2, burger.ingredients.size());
    }

    @Test
    public void testRemoveIngredientFromMiddleFirstStays() {
        burger.addIngredient(mockIngredient1);
        burger.addIngredient(mockIngredient2);
        burger.addIngredient(mockIngredient3);
        
        burger.removeIngredient(1); // Удаляем средний ингредиент
        
        assertEquals("Первый ингредиент должен остаться на месте", mockIngredient1, burger.ingredients.get(0));
    }

    @Test
    public void testRemoveIngredientFromMiddleThirdMoves() {
        burger.addIngredient(mockIngredient1);
        burger.addIngredient(mockIngredient2);
        burger.addIngredient(mockIngredient3);
        
        burger.removeIngredient(1); // Удаляем средний ингредиент
        
        assertEquals("Третий ингредиент должен сдвинуться", mockIngredient3, burger.ingredients.get(1));
    }

    @Test
    public void testRemoveIngredientFromMiddleSecondRemoved() {
        burger.addIngredient(mockIngredient1);
        burger.addIngredient(mockIngredient2);
        burger.addIngredient(mockIngredient3);
        
        burger.removeIngredient(1); // Удаляем средний ингредиент
        
        assertFalse("Второй ингредиент должен быть удален", burger.ingredients.contains(mockIngredient2));
    }

    @Test
    public void testRemoveLastIngredientSize() {
        burger.addIngredient(mockIngredient1);
        burger.addIngredient(mockIngredient2);
        
        burger.removeIngredient(1); // Удаляем последний ингредиент
        
        assertEquals("Должен остаться 1 ингредиент", 1, burger.ingredients.size());
    }

    @Test
    public void testRemoveLastIngredientFirstStays() {
        burger.addIngredient(mockIngredient1);
        burger.addIngredient(mockIngredient2);
        
        burger.removeIngredient(1); // Удаляем последний ингредиент
        
        assertEquals("Первый ингредиент должен остаться", mockIngredient1, burger.ingredients.get(0));
    }

    @Test
    public void testMoveIngredientToBeginningThirdFirst() {
        burger.addIngredient(mockIngredient1);
        burger.addIngredient(mockIngredient2);
        burger.addIngredient(mockIngredient3);
        
        // Перемещаем последний ингредиент в начало
        burger.moveIngredient(2, 0);
        
        assertEquals("Третий ингредиент должен быть первым", mockIngredient3, burger.ingredients.get(0));
    }

    @Test
    public void testMoveIngredientToBeginningFirstSecond() {
        burger.addIngredient(mockIngredient1);
        burger.addIngredient(mockIngredient2);
        burger.addIngredient(mockIngredient3);
        
        // Перемещаем последний ингредиент в начало
        burger.moveIngredient(2, 0);
        
        assertEquals("Первый ингредиент должен быть вторым", mockIngredient1, burger.ingredients.get(1));
    }

    @Test
    public void testMoveIngredientToBeginningSecondThird() {
        burger.addIngredient(mockIngredient1);
        burger.addIngredient(mockIngredient2);
        burger.addIngredient(mockIngredient3);
        
        // Перемещаем последний ингредиент в начало
        burger.moveIngredient(2, 0);
        
        assertEquals("Второй ингредиент должен быть третьим", mockIngredient2, burger.ingredients.get(2));
    }

    @Test
    public void testMoveIngredientToEndSecondFirst() {
        burger.addIngredient(mockIngredient1);
        burger.addIngredient(mockIngredient2);
        burger.addIngredient(mockIngredient3);
        
        // Перемещаем первый ингредиент в конец (позиция 2, так как у нас 3 элемента)
        burger.moveIngredient(0, 2);
        
        assertEquals("Второй ингредиент должен быть первым", mockIngredient2, burger.ingredients.get(0));
    }

    @Test
    public void testMoveIngredientToEndThirdSecond() {
        burger.addIngredient(mockIngredient1);
        burger.addIngredient(mockIngredient2);
        burger.addIngredient(mockIngredient3);
        
        // Перемещаем первый ингредиент в конец (позиция 2, так как у нас 3 элемента)
        burger.moveIngredient(0, 2);
        
        assertEquals("Третий ингредиент должен быть вторым", mockIngredient3, burger.ingredients.get(1));
    }

    @Test
    public void testMoveIngredientToEndFirstThird() {
        burger.addIngredient(mockIngredient1);
        burger.addIngredient(mockIngredient2);
        burger.addIngredient(mockIngredient3);
        
        // Перемещаем первый ингредиент в конец (позиция 2, так как у нас 3 элемента)
        burger.moveIngredient(0, 2);
        
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

    SoftAssertions softly = new SoftAssertions();
    softly.assertThat(receipt).as("старт с булочки").startsWith("(==== Test Bun ====)");
    softly.assertThat(receipt).as("наличие цены").contains("Price: 21.5");
    softly.assertThat(receipt).as("начинка строка").contains("= filling Lettuce =");
    softly.assertThat(receipt).as("соус строка").contains("= sauce Mayo =");

    int topBunIndex = receipt.indexOf("(==== Test Bun ====)");
    int fillingIndex = receipt.indexOf("= filling Lettuce =");
    int sauceIndex = receipt.indexOf("= sauce Mayo =");
    int bottomBunIndex = receipt.lastIndexOf("(==== Test Bun ====)");
    int priceIndex = receipt.indexOf("Price:");

    softly.assertThat(fillingIndex).as("порядок: начинка после верха").isGreaterThan(topBunIndex);
    softly.assertThat(sauceIndex).as("порядок: соус после начинки").isGreaterThan(fillingIndex);
    softly.assertThat(bottomBunIndex).as("порядок: низ после соуса").isGreaterThan(sauceIndex);
    softly.assertThat(priceIndex).as("порядок: цена после низа").isGreaterThan(bottomBunIndex);
    softly.assertAll();
    }

    @Test
    public void testGetReceiptFormatting() {
        when(mockBun.getName()).thenReturn("Special Bun");
        when(mockBun.getPrice()).thenReturn(7.75f);
        
        burger.setBuns(mockBun);
        
    String receipt = burger.getReceipt();

    SoftAssertions softly = new SoftAssertions();
    softly.assertThat(receipt).as("переносы строк").contains("\n");
    softly.assertThat(receipt).as("формат цены").matches("(?s).*Price: \\d+\\.\\d+.*");
    softly.assertAll();
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
