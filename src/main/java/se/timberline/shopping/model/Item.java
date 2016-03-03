package se.timberline.shopping.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Item {
    @Column
    public String text;
    @Column
    public boolean done;

    public Item(String text) {
        this.text = text;
    }

    protected Item() {
        // JPA
    }
}
