import com.sonarsource.cinema.model.FoodItem;
import com.sonarsource.cinema.service.FoodService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

 class FilterTest {
     private FoodService service;
    @BeforeEach
    void setUp(){
        List<FoodItem> foodItems = List.of(new FoodItem(1, "Sushi", new BigDecimal("3.99")),
                new FoodItem(1, "Burrito", new BigDecimal("2.99")),
                new FoodItem(1, "Taco", new BigDecimal("9.99")));

        service = new FoodService(foodItems);

    }

    @Test
    void testEqualOperator(){
        List<FoodItem> actualFoodItems = service.filterPrice("=", "3.99");

        Assertions.assertEquals(1,actualFoodItems.size());
        Assertions.assertEquals("Sushi",actualFoodItems.getFirst().foodItem());
    }

    @Test
    void testFilterNameWhichIsPresent(){
        List<FoodItem> actualFoodItems = service.filterName("Sushi");

        Assertions.assertEquals(1,actualFoodItems.size());
        Assertions.assertEquals("Sushi",actualFoodItems.getFirst().foodItem());
    }

    @Test
    void testFilterNameWithEmptyArg(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> service.filterName(""));
    }

    @Test
    void testFilterNameWhichIsAbsent(){
        List<FoodItem> actualFoodItems = service.filterName("snehal");

        Assertions.assertEquals(0,actualFoodItems.size());    }
}
