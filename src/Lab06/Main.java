/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Lab06;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Adam
 */
public class Main {

    public static void main(String[] args) {
        try {
            BufferedImage in1 = ImageIO.read(new File("C:\\studies\\AiPO\\lab01\\untitled\\src\\Lab06\\12-b.bmp"));
            BufferedImage in2 = ImageIO.read(new File("C:\\studies\\AiPO\\lab01\\untitled\\src\\Lab06\\12-t2.bmp"));
            BufferedImage in3 = ImageIO.read(new File("C:\\studies\\AiPO\\lab01\\untitled\\src\\Lab06\\12-t3.bmp"));
            BufferedImage in4 = ImageIO.read(new File("C:\\studies\\AiPO\\lab01\\untitled\\src\\Lab06\\12-t4.bmp"));
            
            Lab06.run(in1,in2,in3,in4);
                        
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
