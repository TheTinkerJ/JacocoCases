package com.maoge.jacocopets.flow;

import org.springframework.stereotype.Component;

@Component
public class CtrlIfCase3 {
    public int exec(int input){
        int result = 0;
        if(input==0 || input==1 || input==2){
            result = 3;
        }
        return result;
    }
}
