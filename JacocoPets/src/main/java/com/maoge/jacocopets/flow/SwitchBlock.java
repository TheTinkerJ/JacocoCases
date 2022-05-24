package com.maoge.jacocopets.flow;

public class SwitchBlock {

    public int exec(int value){
        int result = -1;
        switch (value) {
            case 1 :
                result = 2;
                break;
            case 2:
                result = 3;
                break;
            case 3:
                result = 4;
                break;
        }
        return result;
    }
}
