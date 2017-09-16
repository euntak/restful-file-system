package io.euntak.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FileController {

    @RequestMapping ("/")
    public String index() {

        return "index";
    }
}
