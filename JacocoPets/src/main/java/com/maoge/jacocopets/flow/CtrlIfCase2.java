package com.maoge.jacocopets.flow;

import org.springframework.stereotype.Component;

@Component
public class CtrlIfCase2 {

    public int exec(int input){
        int result = 0;
        if(input==0 || input==1){
            result = 2;
        }
        return result;
    }
}
