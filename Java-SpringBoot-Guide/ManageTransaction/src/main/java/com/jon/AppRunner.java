//package com.jon;
//
//import com.jon.service.BookService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//import org.springframework.util.Assert;
//
//@Slf4j
//@Component
//public class AppRunner implements CommandLineRunner {
//    private final BookService bookService;
//
//    public AppRunner(BookService bookService) {
//        this.bookService = bookService;
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//        bookService.book("Alice", "Bob", "Carol");
//        Assert.isTrue(bookService.findAllBookings().size() == 3, "First booking completed successfully");
//
//        try {
//            bookService.book("Chris", "Samuel");
//        } catch (RuntimeException e) {
//            log.info("v--- The following exception is expect because 'Samuel' is too big for the DB ---v");
//            log.error(e.getMessage());
//        }
//
//        bookService.findAllBookings().forEach(p -> log.info("So far, " + p + " is booked."));
//        log.info("You shouldn't see Chris or Samuel. Samuel violated DB constraints, and Chris was rolled back in the same TX.");
//        Assert.isTrue(bookService.findAllBookings().size() == 3, "'Samuel' should have triggered a rollback.");
//
//        try {
//            bookService.book("Buddy", null);
//        } catch (RuntimeException e) {
//            log.info("v--- The following exception is expect because null is not valid for the DB ---v");
//            log.error(e.getMessage());
//        }
//
//        bookService.findAllBookings().forEach(p -> log.info("So far, " + p + " is booked."));
//        log.info("You shouldn't see Buddy or null. null violated DB constraints, and Buddy was rolled back in the same TX.");
//        Assert.isTrue(bookService.findAllBookings().size() == 3, "'Samuel' should have triggered a rollback.");
//    }
//}
