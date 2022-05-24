package com.maoge.jacocopets.service;

import org.springframework.stereotype.Service;

@Service
public class PetService {

    public String tryBySound(String sound) throws Exception {
        if(sound.contains("miao")){
            return "Cat";
        }
        if(sound.contains("wang")){
            return "Dog";
        }
        if(sound.contains("ji")){
            return "bird";
        }
        throw new Exception("notFound");
    }

    public String tryBySoundV2(String sound) throws Exception {
        switch (sound){
            case "miao":
                return "Cat";
            case "wang":
                return "Dog";
            case "ji":
                return "bird";
        }
        throw new Exception("notFound");
    }
}
