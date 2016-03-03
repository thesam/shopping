package se.timberline.shopping.rest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import se.timberline.shopping.model.ShoppingList;
import se.timberline.shopping.model.ShoppingListRepository;

@RestController
@RequestMapping("/list")
public class ShoppingListController {

    private Logger logger = Logger.getLogger(ShoppingListController.class);

    @Autowired
    private ShoppingListRepository shoppingListRepository;

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<ShoppingList> findAll() {
        return shoppingListRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    public ShoppingList save(@RequestBody ShoppingList shoppingList) {
        return shoppingList;
//        if (day.id() != null) {
//            ShoppingList savedDay = dayRepository.findOne(day.id());
//            savedDay.update(day);
//            dayRepository.save(savedDay);
//            return savedDay;
//        } else {
//            return dayRepository.save(day);
//        }
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handle(HttpMessageNotReadableException e) {
        logger.warn("Returning HTTP 400 Bad Request", e);
    }
}
