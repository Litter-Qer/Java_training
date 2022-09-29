package controller;

import domain.Book;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@ResponseBody
@RequestMapping("/books")
public class BookController {

    @PostMapping
    public String save(@RequestBody Book book) {
        System.out.println("book ==> " + book);
        return "Book success";
    }

    @GetMapping
    public List<Book> getAll() {
        Book book1 = new Book();
        book1.setType("PC");
        book1.setName("Spring REST");
        book1.setDescription("Goodieeee");

        Book book2 = new Book();
        book2.setType("LP");
        book2.setName("Spring REST Advance");
        book2.setDescription("Saddd");

        List<Book> books = new ArrayList<>();
        books.add(book1);
        books.add(book2);
        return books;
    }


}
