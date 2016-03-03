package se.timberline.shopping.model;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import se.timberline.shopping.Application;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

@SpringApplicationConfiguration(classes = Application.class)
public class ShoppingShoppingListRepositoryIntegrationTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private ShoppingListRepository dayRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    public void canStore() {
        ShoppingList day = new ShoppingList(LocalDate.parse("2015-02-01"));
        dayRepository.save(day);
        entityManager.flush();
        entityManager.clear();
        ShoppingList savedDay = dayRepository.findByDate(LocalDate.parse("2015-02-01"));
        assertEquals(day.date(), savedDay.date());
        assertNotNull(savedDay.id());
    }

    @Test
    public void requiresUniqueDate() {
        ShoppingList day = new ShoppingList(LocalDate.parse("2015-02-01"));
        ShoppingList day2 = new ShoppingList(LocalDate.parse("2015-02-01"));
        dayRepository.save(day);
        entityManager.flush();
        try {
            dayRepository.save(day2);
            entityManager.flush();
            fail("Should throw exception!");
        } catch (PersistenceException pe) {

        }
    }

}