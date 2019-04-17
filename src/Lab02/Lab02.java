package Lab02;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Lab02 {
    public static void run(BufferedImage in) {
        try {
            BufferedImage out = Lab02.skalowanie(in,(float)(0.4));
            out = Lab02.skalowanie(out,(float)(2.5));
            ImageIO.write(out,"jpg",new File("05.jpg"));
            out = Lab02.progowanieSG(in);
            ImageIO.write(out,"jpg",new File("06a.jpg"));
            
            int size[] = {2,5,7,10,12};
            for(int pom_licz = 0; pom_licz < size.length;pom_licz++) {
                out = Lab02.progowanieL(in, size[pom_licz]);
                ImageIO.write(out,"jpg",new File("06b-" + (2*size[pom_licz]+1) + ".jpg"));
            }
            
            int size2[] = {15,25,35};
             for(int pom_licz = 0; pom_licz < size2.length;pom_licz++) {
                 out = Lab02.progowanieLSG(in, 15, size2[pom_licz]);
                 ImageIO.write(out,"jpg",new File("06c-" + size2[pom_licz] + ".jpg"));
             }
             
             int size3[][][] =  {{{ 1, 1, 1},{ 1, 1, 1},{ 1, 1, 1}},
                                 {{ 1, 1, 1},{ 1, 2, 1},{ 1, 1, 1}},
                                 {{ 1, 2, 1},{ 2, 4, 2},{ 1, 2, 1}},
                                 {{ 0,-1, 0},{-1, 5,-1},{ 0,-1, 0}},
                                 {{-1,-1,-1},{-1, 9,-1},{-1,-1,-1}},
                                 {{ 1,-2, 1},{-2, 5,-2},{ 1,-2, 1}}
             };
             for(int pom_licz = 0; pom_licz < size3.length;pom_licz++) {
                 out = Lab02.filtrSplotowy(in, size3[pom_licz]);
                 ImageIO.write(out,"jpg",new File("07-" + pom_licz + ".jpg"));
             }
             
            
        } catch(IOException e) {
            System.out.println("W module Lab02 padło: " + e.toString());
        }
    }
    
    // skalowanie obrazu
    public static BufferedImage skalowanie(BufferedImage in, float skala) {
        BufferedImage out = new BufferedImage(Math.round(skala*in.getWidth()), Math.round(skala*in.getHeight()),in.getType());
        int height,width;
        width = out.getWidth();
        height = out.getHeight();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                out.setRGB(i, j, in.getRGB((int)Math.floor(i/skala), (int)Math.floor(j/skala)));
            }
        }
        return out;
    }
    
    // progowanie po średniej globalnej
    public static BufferedImage progowanieSG(BufferedImage in) {
        BufferedImage out = new BufferedImage(in.getWidth(), in.getHeight(),in.getType());
        BufferedImage szary = Lab01.Lab01.szarosciN(in); //Tu potrzebujesz kodu z pierwszych zajęć
        int height,width;
        width = out.getWidth();
        height = out.getHeight();
        int czarny = 0x00000000;
        int bialy = 0x00ffffff;
        // obliczanie średniej
        int srednia = 0;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                srednia += RGB.getR(szary.getRGB(i, j));
            }
        }
        srednia = srednia / (height * width);
        // właściwe progowanie
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if(RGB.getB(szary.getRGB(i, j)) < srednia) {
                    out.setRGB(i, j, czarny);
                } else {
                    out.setRGB(i, j, bialy);
                }
            }
        }
        return out;
    }
    
    // progowanie lokalne
    public static BufferedImage progowanieL(BufferedImage in,int rozmiar) {
        BufferedImage out = new BufferedImage(in.getWidth(), in.getHeight(),in.getType());
        BufferedImage szary = Lab01.Lab01.szarosciN(in); //Tu potrzebujesz kodu z pierwszych zajęć
        int height, width;
        int srednia_lokalna;
        int i_dol,i_gora,j_dol,j_gora,sumcia;
        int wObie = rozmiar/2;
        width = out.getWidth();
        height = out.getHeight();
        int czarny = 0x00000000;
        int bialy = 0x00ffffff;
        int count;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                sumcia = 0;
                count = 0;
                for(int x = i - wObie; x <= i+wObie; x++) {
                    if(isDimensionValid(width, x)){
                        for (int y = j - wObie; y <= j + wObie; y++){
                            if(isDimensionValid(height, y) ) {
                                sumcia += RGB.getR(szary.getRGB(x, y));
                                count++;
                            }
                        }
                    }
                }
                try {

                    sumcia = sumcia / count;
                } catch (ArithmeticException e) {
                    throw new ArithmeticException("Dziele przez zero, nie policzylem zadnego piksela dla punktu (" + i + ", " + j+")");
                }
                if(RGB.getB(szary.getRGB(i, j)) < sumcia) {
                    out.setRGB(i, j, czarny);
                } else {
                    out.setRGB(i, j, bialy);
                }
            }
        }

        return out;
    }

    private static boolean isDimensionValid(int maxDimension, int y) {
        return y > 0 && y < maxDimension;
    }

    // progowanie lokalne ze średnią globalną
    public static BufferedImage progowanieLSG(BufferedImage in,int rozmiar,int odbieganie) {
        BufferedImage out = new BufferedImage(in.getWidth(), in.getHeight(),in.getType());
        BufferedImage szary = Lab01.Lab01.szarosciN(in); //Tu potrzebujesz kodu z pierwszych zajęć
        int height,width;
        int srednia_lokalna;
        int i_dol,i_gora,j_dol,j_gora,sumcia, count;
        width = out.getWidth();
        height = out.getHeight();
        int czarny = 0x00000000;
        int bialy = 0x00ffffff;
        int wObie = rozmiar/2;
        int srednia = 0;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                srednia += RGB.getR(szary.getRGB(i, j));
            }
        }
        srednia = srednia / (height * width);
        
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                sumcia = 0;
                count = 0;
                for(int x = i - wObie; x <= i+wObie; x++) {
                    if(isDimensionValid(width, x)){
                        for (int y = j - wObie; y <= j + wObie; y++){
                            if(isDimensionValid(height, y) ) {
                                sumcia += RGB.getR(szary.getRGB(x, y));
                                count++;
                            }
                        }
                    }
                }
                try {
                    sumcia = sumcia / count;
                } catch (ArithmeticException e) {
                    throw new ArithmeticException("Dziele przez zero, nie policzylem zadnego piksela dla punktu (" + i + ", " + j+")");
                }
                if(Math.abs(sumcia - srednia) > odbieganie){
                    if (RGB.getB(szary.getRGB(i, j)) < srednia) {
                        out.setRGB(i, j, czarny);
                    } else {
                        out.setRGB(i, j, bialy);
                    }
                } else {
                    if (RGB.getB(szary.getRGB(i, j)) < sumcia) {
                        out.setRGB(i, j, czarny);
                    } else {
                        out.setRGB(i, j, bialy);
                    }
                }
            }
        }

        return out;
    }
    
    // filtry splotowe
    public static BufferedImage filtrSplotowy(BufferedImage in,int maska[][]) {
        BufferedImage out = new BufferedImage(in.getWidth(), in.getHeight(),in.getType());
        int height,width,mheight,mwidth;
        int r,g,b;
        mheight = maska[0].length-1;
        mwidth = maska.length-1;
        int minIndexX = (maska.length/2);
        int minIndexY = (maska[0].length/2);
        width = out.getWidth()-minIndexX;
        height = out.getHeight()-minIndexY;

        int sumaM = 0;
        for(int i=0;i<=mwidth;i++) {
            for(int j=0;j<=mheight;j++) {
                sumaM += maska[i][j];
            }
        }
        
        if (sumaM == 0) {
            sumaM = 1;
        }
        
        for (int i = minIndexX; i < width; i++) {
            for (int j = minIndexY; j < height; j++) {
                r = 0;
                g = 0;
                b = 0;
                for(int x=0 ; x <= mwidth; x++) {
                    for(int y=0; y <= mheight; y++) {
                        r += RGB.getR(in.getRGB(i + x - minIndexX, j+ y - minIndexY)) * maska[x][y];
                        g += RGB.getG(in.getRGB(i + x - minIndexX, j+ y - minIndexY)) * maska[x][y];
                        b += RGB.getB(in.getRGB(i + x - minIndexX, j+ y - minIndexY)) * maska[x][y];
                    }
                }
                r = r / sumaM;
                g = g / sumaM;
                b = b / sumaM;
                if (r > 255) {
                    r = 255;
                }
                if (g > 255) {
                    g = 255;
                }
                if (b > 255) {
                    b = 255;
                }
                if (r < 0) {
                    r = 0;
                }
                if (g < 0) {
                    g = 0;
                }
                if (b < 0) {
                    b = 0;
                }
                out.setRGB(i,j, RGB.toRGB(r, g, b));
            }
        }
        
        return out;
    }
}
