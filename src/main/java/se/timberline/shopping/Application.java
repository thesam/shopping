package se.timberline.shopping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import se.timberline.shopping.model.Item;
import se.timberline.shopping.model.ShoppingList;
import se.timberline.shopping.model.ShoppingListRepository;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);
        ShoppingListRepository shoppingListRepository = ctx.getBean(ShoppingListRepository.class);
        ShoppingList shoppingList = new ShoppingList();
        shoppingList.add(new Item("Kaffe"));
        shoppingListRepository.save(shoppingList);
    }

}