package com.maoge.jacocopets.flow;

import org.springframework.stereotype.Component;

/**
 * IF控制流
 */
@Component
public class CtrlIfCase1 {

    public int exec(int input){
        int result = 0;
        if(input==0){
            result = 1;
        }
        return result;
    }
}
