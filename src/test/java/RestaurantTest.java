import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RestaurantTest {
    Restaurant restaurant;

    @BeforeEach
    public void initTestCase() {
        System.out.println("Initializing restaurant object");
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }

    @AfterEach
    public void resetForTestCase() {
        System.out.println("Resetting restaurant object");
        restaurant = null;
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){

        LocalTime dummyOpeningTime = LocalTime.now().minusHours(2);
        LocalTime dummyClosingTime = LocalTime.now().plusHours(2);

        Restaurant mockRest = Mockito.spy(new Restaurant("Indian Haveli","Bangalor",
                dummyOpeningTime,dummyClosingTime));

        boolean isRestaurantOpen = mockRest.isRestaurantOpen();
        assertThat(isRestaurantOpen,equalTo(true));
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){

        LocalTime dummyOpeningTime = LocalTime.now().minusHours(4);
        LocalTime dummyClosingTime = LocalTime.now().minusHours(2);

        Restaurant mockRest = Mockito.spy(new Restaurant("Indian Haveli","Bangalor",
                dummyOpeningTime,dummyClosingTime));
        // checking when time is after closing time
        boolean isRestaurantOpen = mockRest.isRestaurantOpen();
        assertThat(isRestaurantOpen,equalTo(false));

        mockRest.closingTime = LocalTime.now().plusHours(4);
        mockRest.openingTime = LocalTime.now().plusHours(2);

        // checking when time is before opening
        isRestaurantOpen = mockRest.isRestaurantOpen();
        assertThat(isRestaurantOpen,equalTo(false));

    }
    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {

        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //>>>>>>>>>>>>>>>>>>>>>>>ITEM COST<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void when_no_item_selected_total_cost_should_be_zero() {
        List<Item> menuItems = new ArrayList<Item>();
        int totalCost = restaurant.getTotalCost(menuItems);
        assertEquals(0, totalCost);
    }

    @Test
    public void when_one_or_more_item_selected_accumulated_cost_as_total_cost() {

        List<Item> menuItems = new ArrayList<Item>();
        menuItems.add(new Item("Sweet corn soup",100));
        menuItems.add(new Item("Vegetable lasagne", 200));
        menuItems.add(new Item("Pizza", 300));

        int totalCost = restaurant.getTotalCost(menuItems);
        assertEquals(600, totalCost);
    }
    //<<<<<<<<<<<<<<<<<<<<<<<ITEM COST>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
}