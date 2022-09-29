package controller;

import domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
public class BookController {

    @PostMapping
    public String save() {
        System.out.println("booking save ...");
        return "{'module':'book save'}";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id) {
        System.out.println("user delete ..." + id);
        return "{'module':'usr delete'}";
    }

    @PutMapping
    public String update(@RequestBody User user) {
        System.out.println("user update ..." + user);
        return "User updated";
    }


    @GetMapping("/{id}")
    public String getById(@PathVariable Integer id) {
        System.out.println("user update ..." + id);
        return "User Id Got";
    }

    @GetMapping
    public String getAll() {
        System.out.println("user getAll ...");
        return "User get All";
    }
}
