package com.url.encurtador.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/refresh")
public class RefreshController {

    //Endpoint Utilizado somente para fazer refresh com o Esp32
    @GetMapping()
    public String recarregar(){
        String print = "Recarregando APi";
        System.out.println("Recarregando APi");
        return print;
    }

}
