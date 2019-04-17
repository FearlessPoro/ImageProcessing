package Lab04;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import javax.imageio.ImageIO;

public class Lab04 {
    public static void run(BufferedImage in) {
        try {
            BufferedImage out,out2;
            int tab[] = {5,10,15};
            for(int i = 0;i<tab.length;i++) {
                out = Lab04.solIpieprz(in,tab[i]);
                ImageIO.write(out,"jpg",new File("11-solpepper" + tab[i] + ".jpg"));
                System.out.println("Szum sól i pieprz z prawdopodobieństwem " + tab[i] + "% gotowy!");
                for(int j=1;j<4;j++) {
                    out2 = Lab04.odszumianieS(out,j);
                    ImageIO.write(out2,"jpg",new File("11-solpepper" + tab[i] + "-odszumianieSrednia-" + (2*j+1) + ".jpg"));
                    System.out.println("Odszumianie przez średnią obrazu sól i pieprz z prawdopodobieństwem " + tab[i] + "% z otoczeniem " + (2*j+1) + " gotowe!");
                    out2 = Lab04.odszumianieM(out,j);
                    ImageIO.write(out2,"jpg",new File("11-solpepper" + tab[i] + "-odszumianieMediana-" + (2*j+1) + ".jpg"));
                    System.out.println("Odszumianie przez medianę obrazu sól i pieprz z prawdopodobieństwem " + tab[i] + "% z otoczeniem " + (2*j+1) + " gotowe!");
                    out2 = Lab04.odszumianieMU(out,j);
                    ImageIO.write(out2,"jpg",new File("11-solpepper" + tab[i] + "-odszumianieUlepszonaMediana-" + (2*j+1) + ".jpg"));
                    System.out.println("Odszumianie przez ulepszoną medianę obrazu sól i pieprz z prawdopodobieństwem " + tab[i] + "% z otoczeniem " + (2*j+1) + " gotowe!");
                }
            }

            for(int i = 0;i<tab.length;i++) {
                out = Lab04.rownomiernyT(in,tab[i]);
                ImageIO.write(out,"jpg",new File("11-szumRownomiernyT" + tab[i] + ".jpg"));
                System.out.println("Szum równomierny zgodny na każdym kanale z prawdopodobieństwem " + tab[i] + "% gotowy!");
            }
            for(int i = 0;i<tab.length;i++) {
                out = Lab04.rownomiernyN(in,tab[i]);
                ImageIO.write(out,"jpg",new File("11-szumRownomiernyN" + tab[i] + ".jpg"));
                System.out.println("Szum równomierny z różnymi wartościami na kanałach z prawdopodobieństwem " + tab[i] + "% gotowy!");
                for(int j=1;j<4;j++) {
                    out2 = Lab04.odszumianieS(out,j);
                    ImageIO.write(out2,"jpg",new File("11-szumRownomiernyN" + tab[i] + "-odszumianieSrednia-" + (2*j+1) + ".jpg"));
                    System.out.println("Odszumianie przez średnią obrazu z szumem równomiernym z różnymi wartościami na kanałach z prawdopodobieństwem " + tab[i] + "% z otoczeniem " + (2*j+1) + " gotowe!");
                    out2 = Lab04.odszumianieM(out,j);
                    ImageIO.write(out2,"jpg",new File("11-szumRownomiernyN" + tab[i] + "-odszumianieMediana-" + (2*j+1) + ".jpg"));
                    System.out.println("Odszumianie przez medianę obrazu z szumem równomiernym z różnymi wartościami na kanałach z prawdopodobieństwem " + tab[i] + "% z otoczeniem " + (2*j+1) + " gotowe!");
                    out2 = Lab04.odszumianieMU(out,j);
                    ImageIO.write(out2,"jpg",new File("11-szumRownomiernyN" + tab[i] + "-odszumianieUlepszonaMediana-" + (2*j+1) + ".jpg"));
                    System.out.println("Odszumianie przez ulepszoną medianę obrazu z szumem równomiernym z różnymi wartościami na kanałach z prawdopodobieństwem " + tab[i] + "% z otoczeniem " + (2*j+1) + " gotowe!");
                }
            }
        } catch(IOException e) {
            System.out.println("W module Lab01 padło: " + e.toString());
        }
    }
    
    // zaszumianie sól i pieprz
    public static BufferedImage solIpieprz(BufferedImage in,int procent) {
        double pr = procent/100.;
        int height,width;
        BufferedImage out = getBufferedImageCopy(in);
        width = out.getWidth();
        height = out.getHeight();
        int czarny = 0x00000000;
        int bialy = 0x00ffffff;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (Math.random() <= pr){
                    if (Math.random() > 0.5) {
                        out.setRGB(i, j, czarny);
                    } else
                    {
                        out.setRGB(i, j, bialy);
                    }
                }
            }
        }
        return out;
    }

    private static BufferedImage getBufferedImageCopy(BufferedImage in) {
        BufferedImage out = new BufferedImage(in.getWidth(), in.getHeight(), in.getType());
        Graphics g = out.getGraphics();
        g.drawImage(in, 0, 0, null);
        g.dispose();
        return out;
    }

    // zaszumianie równomierne, każdy kanał ten sam
    public static BufferedImage rownomiernyT(BufferedImage in,int procent) {
        double pr = procent/100.;
        int height,width;
        BufferedImage out = getBufferedImageCopy(in);
        width = out.getWidth();
        height = out.getHeight();
        
        int war, R1, G1, B1;
        
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (Math.random() >= pr ) {
                    int przesuniecie = (int) (Math.random() * (-9) - 21);
                    if (Math.random() > 0.5) {
                        przesuniecie = - przesuniecie;
                    }
                    R1 = RGB.getR(in.getRGB(i, j)) + przesuniecie;
                    R1 = checkEdgeCase(R1);
                    G1 = RGB.getG(in.getRGB(i, j)) + przesuniecie;
                    G1 = checkEdgeCase(G1);
                    B1 = RGB.getB(in.getRGB(i, j)) + przesuniecie;
                    B1 = checkEdgeCase(B1);
                    out.setRGB(i, j, RGB.toRGB(R1, G1, B1));
                }
            }
        }
        return out;
    }

    private static int checkEdgeCase(int r1) {
        if (r1 > 255) {
            r1 = 255;
        } else if (r1 < 0 ) {
            r1 = 0;
        }
        return r1;
    }

    // zaszumianie równomierne, każdy kanał inny
    public static BufferedImage rownomiernyN(BufferedImage in,int procent) {
        double pr = procent/100.;
        int height,width;
        BufferedImage out = getBufferedImageCopy(in);
        width = out.getWidth();
        height = out.getHeight();
        
        int war, R1, G1, B1;
        
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (Math.random() >= pr ) {
                    int przesuniecie1 = (int) (Math.random() * (-9) - 30);
                    int przesuniecie2 = (int) (Math.random() * (-9) - 30);
                    int przesuniecie3 = (int) (Math.random() * (-9) - 30);
                    if (Math.random() > 0.5) {
                        przesuniecie1 = - przesuniecie1;
                    }
                    if (Math.random() > 0.5) {
                        przesuniecie2 = - przesuniecie2;
                    }
                    if (Math.random() > 0.5) {
                        przesuniecie3 = - przesuniecie3;
                    }
                    R1 = RGB.getR(in.getRGB(i, j)) + przesuniecie1;
                    R1 = checkEdgeCase(R1);
                    G1 = RGB.getG(in.getRGB(i, j)) + przesuniecie2;
                    G1 = checkEdgeCase(G1);
                    B1 = RGB.getB(in.getRGB(i, j)) + przesuniecie3;
                    B1 = checkEdgeCase(B1);
                    out.setRGB(i, j, RGB.toRGB(R1, G1, B1));
                }
            }
        }
        return out;
    }
    
    // odszumianie przez średnią
    public static BufferedImage odszumianieS(BufferedImage in,int rozmiar) {
        BufferedImage out = getBufferedImageCopy(in);
        long start = System.currentTimeMillis();
        int height,width;
        int SLR,SLG,SLB;
        int sumcia;
        int wObie = rozmiar;
        width = out.getWidth();
        height = out.getHeight();
        for (int i = 2; i < width-2; i++) {
            for (int j = 2; j < height-2; j++) {
                SLR = 0 ;
                SLB = 0;
                SLG = 0;
                sumcia = 0;
                for(int x = i - wObie; x <= i+wObie; x++) {
                    if (isDimensionValid(width, x)) {
                        for (int y = j - wObie; y <= j + wObie; y++) {
                            if (isDimensionValid(height, y)) {
                                SLR += RGB.getR(in.getRGB(x, y));
                                SLB += RGB.getB(in.getRGB(x, y));
                                SLG += RGB.getG(in.getRGB(x, y));
                                sumcia++;
                            }
                        }
                    }
                }
                SLR /= sumcia;
                SLB /= sumcia;
                SLG /= sumcia;
                out.setRGB(i, j, RGB.toRGB(SLR, SLG, SLB));
            }
        }
        long end = System.currentTimeMillis();
        Long difference = end - start;
        System.out.println("Czas potrzebny na odszumianie srednia: " + difference.toString());
        return out;
    }

    public static boolean isDimensionValid(int dimension, int i) {
        return i >= 0 && i < dimension;
    }
    // odszumianie przez medianę
    public static BufferedImage odszumianieM(BufferedImage in,int rozmiar) {
        BufferedImage out = getBufferedImageCopy(in);
        long start = System.currentTimeMillis();
        int height,width;
        int RGBtab[] = new int[3];
        int wObie = rozmiar;
        width = out.getWidth();
        height = out.getHeight();
        int tab[][] = new int[3][(2*rozmiar+1)*(2*rozmiar+1)];
        int srodek;
        
        for (int i = rozmiar; i < width -rozmiar; i++) {
            for (int j = rozmiar; j < height-rozmiar; j++) {
                for (int k = 0; k < 3; k++) {
                    Arrays.fill(tab[k], 300);
                }
                srodek = 0;
                for(int x = i - wObie; x <= i+wObie; x++) {
                    if (isDimensionValid(width, x)) {
                        for (int y = j - wObie; y <= j + wObie; y++) {
                            if (isDimensionValid(height, y)) {
                                tab[0][srodek] = RGB.getR(in.getRGB(x, y));
                                tab[1][srodek] = RGB.getG(in.getRGB(x, y));
                                tab[2][srodek] = RGB.getB(in.getRGB(x, y));
                                srodek++;
                            }
                        }
                    }
                }
                for (int k = 0; k < 3; k++) {
                    quicksort(tab[k], 0, srodek-1);
                    if (srodek % 2 == 0) {
                        RGBtab[k] = (tab[k][srodek/2] + tab[k][srodek/2 -1]) / 2;
                    } else {
                        RGBtab[k] = tab[k][srodek/2];
                    }
                }

                out.setRGB(i, j, RGB.toRGB(RGBtab[0], RGBtab[1], RGBtab[2]));
            }
        }
        long end = System.currentTimeMillis();
        Long difference = end - start;
        System.out.println("Czas potrzebny na odszumianie mediana: " + difference.toString());
        return out;
    }
    
    // odszumianie przez ulepszoną medianę
    public static BufferedImage odszumianieMU(BufferedImage in,int rozmiar) {
        BufferedImage out = getBufferedImageCopy(in);
        long start = System.currentTimeMillis();
        int height,width;
        int RGBtab[] = new int[3];
        int wObie = rozmiar;
        width = out.getWidth();
        height = out.getHeight();
        int tab[][] = new int[3][(2*rozmiar+1)*(2*rozmiar+1)];
        int srodek;
        int procent = ((2*rozmiar)+1) / 5;
        if (procent <2) {
            procent = 2;
        }
        int RGBOld[] = new int[3];

        for (int i = rozmiar; i < width-rozmiar; i++) {
            for (int j = rozmiar; j < height-rozmiar; j++) {
                for (int k = 0; k < 3; k++) {
                    Arrays.fill(tab[k], 300);

                }
                RGBOld[0] = RGB.getR(in.getRGB(i, j));
                RGBOld[1] = RGB.getG(in.getRGB(i, j));
                RGBOld[2] = RGB.getB(in.getRGB(i, j));

                srodek = 0;
                for(int x = i - wObie; x <= i+wObie; x++) {
                    if (isDimensionValid(width, x)) {
                        for (int y = j - wObie; y <= j + wObie; y++) {
                            if (isDimensionValid(height, y)) {
                                tab[0][srodek] = RGB.getR(in.getRGB(x, y));
                                tab[1][srodek] = RGB.getG(in.getRGB(x, y));
                                tab[2][srodek] = RGB.getB(in.getRGB(x, y));
                                srodek++;
                            }
                        }
                    }
                }
                for (int k = 0; k < 3; k++) {
                    quicksort(tab[k], 0, srodek-1);
                    if (srodek % 2 == 0) {
                        for (int l = 0; l < procent; l++) {
                            if (tab[k][l] ==  RGBOld[k]) {
                                continue;
                            }
                            if (tab[k][srodek - l -1] ==  RGBOld[k]) {
                                continue;
                            }
                        }
                        RGBtab[k] = (tab[k][srodek/2] + tab[k][srodek/2 -1]) / 2;
                    } else {
                        for (int l = 0; l < procent; l++) {
                            if (tab[k][l] ==  RGBOld[k]) {
                                continue;
                            }
                            if (tab[k][srodek - l -1] ==  RGBOld[k]) {
                                continue;
                            }
                        }
                        RGBtab[k] = tab[k][srodek/2];
                    }
                }

                out.setRGB(i, j, RGB.toRGB(RGBtab[0], RGBtab[1], RGBtab[2]));
            }
        }
        long end = System.currentTimeMillis();
        Long difference = end - start;
        System.out.println("Czas potrzebny na odszumianie ulepszona mediana: " + difference.toString());
        return out;
    }
    //optymalne sortowanie - możesz wykorzystać je przy filtrach medianowych
    private static void quicksort(int tab[],int dol,int gora) {

        int i = dol, j = gora;
        int polowa = tab[dol + (gora-dol)/2];

        while (i <= j) {
            while (tab[i] < polowa) {
                    i++;
            }
            while (tab[j] > polowa) {
                    j--;
            }
            if (i <= j) {
                int tmp = tab[i];
                tab[i] = tab[j];
                tab[j] = tmp;
                i++;
                j--;
            }
        }
        if (dol < j)
                quicksort(tab,dol, j);
        if (i < gora)
                quicksort(tab,i, gora);
    }
    
}
