package com.tony.photoshader.block;

public class BlockManager {

    public static BaseBlockActor getBaseBlockActor(){
        int value = (int) (1+(Math.random() * 5));
        System.out.println(value);
        if (value == 1){
            return new BlockOne();
        }else if (value == 2){
            return new BlockTwo();
        }else if (value == 3){
            return new BlockThree();
        }else if (value == 4){
            return new BlockFour();
        }else if (value == 5){
            return new BlockFive();
        }else if (value == 6){
            return new BlockSix();
        }
        return new BlockOne();
    }
}
