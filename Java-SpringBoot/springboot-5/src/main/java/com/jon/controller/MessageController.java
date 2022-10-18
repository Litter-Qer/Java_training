package com.jon.controller;

import com.jon.service.MessageService;
import com.jon.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/msg")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @GetMapping()
    public String doMessage() {
        return messageService.doMsg();
    }
}
