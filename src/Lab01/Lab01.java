package Lab01;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Lab01 {
    
    public static void run(BufferedImage in) {
        try {
            BufferedImage out = Lab01.negatyw(in);
            ImageIO.write(out,"jpg",new File("02.jpg"));
            out = Lab01.histnorm(in);
            ImageIO.write(out,"jpg",new File("01b.jpg"));
            out = Lab01.szarosciS(in);
            ImageIO.write(out,"jpg",new File("03.jpg"));
            out = Lab01.histnorm(out);
            ImageIO.write(out,"jpg",new File("03b.jpg"));
            out = Lab01.szarosciN(in);
            ImageIO.write(out,"jpg",new File("04.jpg"));
            out = Lab01.histnorm(out);
            ImageIO.write(out,"jpg",new File("04b.jpg"));
        } catch(IOException e) {
            System.out.println("W module Lab01.Lab01 padło: ");
            e.printStackTrace();
        }
    }
    
    // tworzenie negatywu - spacer po wszystkich pikselach i zamiana ich wartości R, G i B na 255 - wartość
    public static BufferedImage negatyw(BufferedImage in) {
        BufferedImage out = new BufferedImage(in.getWidth(), in.getHeight(),in.getType());
        int height, width;
        width = out.getWidth();
        height = out.getHeight();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                out.setRGB(i, j, RGB.toRGB(255, 255, 255) - in.getRGB(i, j));
            }
        }
        return out;
    }
    // tworzenie odcieni szarości - (R+G+B)/3
    public static BufferedImage szarosciS(BufferedImage in) {
        BufferedImage out = new BufferedImage(in.getWidth(), in.getHeight(),in.getType());
        int width, height;
        int red, green, blue, grey;
        width = out.getWidth();
        height = out.getHeight();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int color =  in.getRGB(i,j);
                grey = (RGB.getR(color)+RGB.getG(color)+RGB.getB(color))/3;
                out.setRGB(i, j, RGB.toRGB(grey, grey, grey));
            }
        }
        return out;
    }
    // tworzenie odcieni szarości - 0.3*R+0.59*G+0.11*B
    public static BufferedImage szarosciN(BufferedImage in) {
        BufferedImage out = new BufferedImage(in.getWidth(), in.getHeight(),in.getType());
        int width, height;
        int red, green, blue, grey;
        width = out.getWidth();
        height = out.getHeight();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int color =  in.getRGB(i,j);
                grey = (int) (0.3 * RGB.getR(color) +
                        0.59 * RGB.getG(color) +
                        0.11 * RGB.getB(color));
                out.setRGB(i, j, RGB.toRGB(grey, grey, grey) );
            }
        }
        return out;
    }
    // normalizacja histogramu
    public static BufferedImage histnorm(BufferedImage in) {
        BufferedImage out = new BufferedImage(in.getWidth(), in.getHeight(),in.getType());
        int width,height,R,G,B;
        int minR=255, minG=255, minB=255;
        int maxR=0, maxG=0, maxB=0;
        int pixel;
        width = out.getWidth();
        height = out.getHeight();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                pixel = in.getRGB(i, j);
                R = RGB.getR(pixel);
                B = RGB.getB(pixel);
                G = RGB.getG(pixel);
                if(minR > R ){ minR = R; }
                if(maxR < R ){ maxR = R; }
                if(minB > B ){ minB = B; }
                if(maxB < B ){ maxB = B; }
                if(minG > G ){ minG = G; }
                if(maxG < G ){ maxG = G; }
            }
        }
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                pixel = in.getRGB(i, j);
                R = 255 * (RGB.getR(pixel) - minR)/(maxR - minR);
                B = 255 * (RGB.getB(pixel) - minB)/(maxB - minB);
                G = 255 * (RGB.getG(pixel) - minG)/(maxG - minG);
                out.setRGB(i, j, RGB.toRGB(R, G, B));
            }
        }
        return out;
    }
}
