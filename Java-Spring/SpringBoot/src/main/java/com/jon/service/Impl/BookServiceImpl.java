package com.jon.service.Impl;

import com.jon.service.BookService;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {
    @Override
    public void save() {
        System.out.println("This is a booking service ...");
    }
}
