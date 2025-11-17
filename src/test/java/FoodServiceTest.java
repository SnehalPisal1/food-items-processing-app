import com.sonarsource.cinema.model.FoodItem;
import com.sonarsource.cinema.service.FoodService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

public class FoodServiceTest {

    private FoodService service = null;
    private List<FoodItem> foodItems =  null;
    @BeforeEach
    void setup(){

        foodItems = List.of(
                new FoodItem(1,"Sushi",new BigDecimal("3.99")),
                        new FoodItem(1,"Burrito",new BigDecimal("2.99")),
                                new FoodItem(1,"Taco",new BigDecimal("9.99"))
        );

        service = new FoodService(foodItems);
    }

    @Test
    void testSortByAscOrder(){
        List<FoodItem> actual=service.sortByPrice("asc");
        Assertions.assertEquals(foodItems.size() , actual.size());
        Assertions.assertEquals(new BigDecimal("2.99"), actual.get(0).price());

    }

    @Test
    void testSortByDescOrder(){

        List<FoodItem> actual=service.sortByPrice("desc");
        Assertions.assertEquals(foodItems.size() , actual.size());
        Assertions.assertEquals(new BigDecimal("9.99"), actual.get(0).price());

    }

    @Test
    void testInValidOrder(){

        Assertions.assertThrows(IllegalArgumentException.class, () -> service.sortByPrice("snehal"));

    }

}
