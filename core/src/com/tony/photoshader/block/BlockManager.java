package com.tony.photoshader.block;

public class BlockManager {

    public static BaseBlockActor getBaseBlockActor(){
        int value = (int) (1+(Math.random() * 17));
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
        }else if (value == 7){
            return new BlockSeven();
        }else if (value == 8){
            return new BlockEight();
        }else if (value == 9){
            return new BlockNine();
        }else if (value == 10){
            return new BlockTen();
        }else if (value == 11){
            return new BlockEleven();
        }else if (value == 12){
            return new BlockTwelve();
        }else if (value == 13){
            return new BlockThirteen();
        }else if (value == 14){
            return new BlockFourteen();
        }else if (value == 15){
            return new BlockFifteen();
        }else if (value == 16){
            return new BlockSixTeen();
        }else if (value == 17){
            return new BlockSevenTeen();
        }else if (value == 18){
            return new BlockEightTeen();
        }
        return new BlockOne();
    }
}
