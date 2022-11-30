import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class RestaurantService {
    private static List<Restaurant> restaurants = new ArrayList<>();

    public Restaurant findRestaurantByName(String restaurantName) throws restaurantNotFoundException {

        Restaurant res = null;
        boolean foundRestaurant = false;

        for (Restaurant resObj : restaurants) {
            if (resObj.getName().equals(restaurantName)) {
                System.out.println("Found res name: " + resObj.getName());
                res = resObj;
                foundRestaurant = true;
                break;
            }
        }
        if(!foundRestaurant)
            throw  new restaurantNotFoundException("restaurantName");
        return res;
    }


    public Restaurant addRestaurant(String name, String location, LocalTime openingTime, LocalTime closingTime) {
        Restaurant newRestaurant = new Restaurant(name, location, openingTime, closingTime);
        restaurants.add(newRestaurant);
        return newRestaurant;
    }

    public Restaurant removeRestaurant(String restaurantName) throws restaurantNotFoundException {
        Restaurant restaurantToBeRemoved = findRestaurantByName(restaurantName);
        restaurants.remove(restaurantToBeRemoved);
        return restaurantToBeRemoved;
    }

    public List<Restaurant> getRestaurants() {
        return restaurants;
    }
}
