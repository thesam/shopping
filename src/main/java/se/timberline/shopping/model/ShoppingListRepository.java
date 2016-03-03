package se.timberline.shopping.model;

import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Component;

@Component
public interface ShoppingListRepository extends Repository<ShoppingList, Long> {
    ShoppingList save(ShoppingList day);

    ShoppingList findOne(Long id);

    Iterable<ShoppingList> findAll();
}
