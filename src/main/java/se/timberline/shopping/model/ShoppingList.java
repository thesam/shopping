package se.timberline.shopping.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ShoppingList {

    @Id
    @GeneratedValue
    @JsonProperty
    private Long id;

    @JsonProperty
    @ElementCollection
    private List<Item> items = new ArrayList<>();

    public ShoppingList() {
    }

    public Long id() {
        return id;
    }

    public void add(Item item) {
        this.items.add(item);
    }
}
