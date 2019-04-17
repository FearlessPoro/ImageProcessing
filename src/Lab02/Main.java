package Lab02;/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class Main {

    public static void main(String[] args) {
        try {
            BufferedImage in = ImageIO.read(new File("C:\\studies\\AiPO\\lab01\\Lab02\\01.jpg"));

            Lab02.run(in);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    

}
