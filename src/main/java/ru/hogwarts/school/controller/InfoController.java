package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.school.model.Student;

import java.util.Collection;

@RestController
@RequestMapping("/info")
public class InfoController {
    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/port")
    public String getServerPort() {
        return "Actual server port: " + serverPort;
    }
}
