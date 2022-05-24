package com.maoge.jacocopets.controller;

import com.maoge.jacocopets.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class PetController {

    @Autowired
    PetService petService;

    @GetMapping("/pet/search/TryBySound")
    public Optional<String> trySearchBySound(@RequestParam String sound){
        try{
            String result = petService.tryBySound(sound);
            return Optional.of(result);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @GetMapping("/pet/search/TryBySoundV2")
    public Optional<String> trySearchBySoundV2(@RequestParam String sound){
        try{
            String result = petService.tryBySoundV2(sound);
            return Optional.of(result);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
