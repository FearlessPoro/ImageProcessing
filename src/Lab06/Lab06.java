package Lab06;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Lab06 {
    public static void run(BufferedImage in1,BufferedImage in2,BufferedImage in3,BufferedImage in4) {
        try {
            BufferedImage out = Lab06.cien_maska(in1);
            ImageIO.write(out,"bmp",new File("12-t1-M.bmp"));
            System.out.println("Maska pierwszego gotowa!");
            out = Lab06.cien_maska(in2);
            ImageIO.write(out,"bmp",new File("12-t2-M.bmp"));
            System.out.println("Maska drugiego gotowa!");
            out = Lab06.cien_maska(in3);
            ImageIO.write(out,"bmp",new File("12-t3-M.bmp"));
            System.out.println("Maska trzeciego gotowa!");
            out = Lab06.cien_maska(in4);
            ImageIO.write(out,"bmp",new File("12-t4-M.bmp"));
            System.out.println("Maska czwartego gotowa!");
            out = Lab06.cien_KMM(in1);
            ImageIO.write(out,"bmp",new File("12-t1-KMM.bmp"));
            System.out.println("KMM pierwszego gotowe!");
            out = Lab06.cien_KMM(in2);
            ImageIO.write(out,"bmp",new File("12-t2-KMM.bmp"));
            System.out.println("KMM drugiego gotowe!");
            out = Lab06.cien_KMM(in3);
            ImageIO.write(out,"bmp",new File("12-t3-KMM.bmp"));
            System.out.println("KMM trzeciego gotowe!");
            out = Lab06.cien_KMM(in4);
            ImageIO.write(out,"bmp",new File("12-t4-KMM.bmp"));
            System.out.println("KMM czwartego gotowe!");
        } catch(IOException e) {
            System.out.println("W module Lab06 padło: " + e.toString());
        }
    }
    
    //poszczególne wykorzystywane kolory, czerwonym oznaczane są elementy maski, które są nieistotne
    private static final int czarny = Color.BLACK.getRGB();
    private static final int bialy = Color.WHITE.getRGB();
    private static final int czerwony = Color.RED.getRGB();
    
    private static int [][][] maski = { {{-1, 1, -1},
                                         {-1,-1, -1},
                                         { 0, 0,  0}},

                                        {{-1, -1, 0},
                                         { 1, -1, 0},
                                         {-1, -1, 0}},

                                        {{0,  0,  0},
                                         {-1,-1, -1},
                                         {-1, 1, -1}},

                                        {{0, -1, -1},
                                         {0, -1,  1},
                                         {0, -1, -1}},
    };
    
    // funkcja porównująca otoczenie piksela z maską
    private static int porownaj(BufferedImage biezacy,int i,int j) {
        int zlicz;
        for(int m=0; m<4 ;m++) {
            zlicz = 0;
            for (int k = -1; k <= 1; k++) {
                for (int l = -1; l <= 1; l++) {
                    if (maski[m][k+1][l+1] != -1) {
                        if (maski[m][k+1][l+1] * 255 == RGB.getB(biezacy.getRGB(k+i, l+j)) ) {
                            zlicz++;
                        }
                    }
                }
            }
            if(zlicz == 4) return bialy;
        }
        return czarny;
    }
    
    // Ścienianie przez maskę
    public static BufferedImage cien_maska(BufferedImage in){
        
        int szerokosc = in.getWidth()+2;
        int wysokosc = in.getHeight()+2;
        
        BufferedImage out = new BufferedImage(in.getWidth(), in.getHeight(),in.getType());
        
        BufferedImage out1 = RGB.powiekszBialymi(in, 1);
        BufferedImage out2 = RGB.powiekszBialymi(in, 1);

        //***************************************
        boolean zmiany = true;

        while(zmiany) {
            zmiany = false;
            for(int i=1;i<szerokosc-1;i++){
                for(int j=1;j<wysokosc-1;j++) {
                    if(RGB.getB(out2.getRGB(i, j)) != 255) {
                        out2.setRGB(i, j, porownaj(out1, i, j));
                    }
                }
            }
            for(int i=1;i<szerokosc-1;i++){
                for(int j=1;j<wysokosc-1;j++) {
                    if (RGB.getR(out1.getRGB(i, j)) != RGB.getR(out2.getRGB(i, j))){
                        zmiany = true;
                    }
                }
            }
            for(int i=1;i<szerokosc-1;i++){
                for(int j=1;j<wysokosc-1;j++) {
                    out1.setRGB(i, j, out2.getRGB(i, j));
                }
            }
        }        
        //****************************************/
        for(int i=1;i<szerokosc-1;i++) {
            for(int j=1;j<wysokosc-1;j++) {
                out.setRGB(i-1, j-1, out2.getRGB(i,j));
            }
        }
        return out;
    }
    
    // wartości masek do KMM
    private final static int [] wyciecia = {3, 5, 7, 12, 13, 14, 15, 20,
                        21, 22, 23, 28, 29, 30, 31, 48,
                        52, 53, 54, 55, 56, 60, 61, 62,
                        63, 65, 67, 69, 71, 77, 79, 80,
                        81, 83, 84, 85, 86, 87, 88, 89,
                        91, 92, 93, 94, 95, 97, 99, 101,
                        103, 109, 111, 112, 113, 115, 116, 117,
                        118, 119, 120, 121, 123, 124, 125, 126,
                        127, 131, 133, 135, 141, 143, 149, 151,
                        157, 159, 181, 183, 189, 191, 192, 193,
                        195, 197, 199, 205, 207, 208, 209, 211,
                        212, 213, 214, 215, 216, 217, 219, 220,
                        221, 222, 223, 224, 225, 227, 229, 231,
                        237, 239, 240, 241, 243, 244, 245, 246,
                        247, 248, 249, 251, 252, 253, 254, 255};

    private final static int [] czworki = {3, 6, 7, 12, 14, 15, 24, 28,
                        30, 48, 56, 60, 96, 112, 120, 129,
                        131, 135, 192, 193, 195, 224, 225, 240};

    private static int [][] maska = {
                                     {128, 64, 32},
                                     {  1,  0, 16},
                                     {  2,  4,  8}
    };
    // Ścienianie przez KMM
    public static BufferedImage cien_KMM(BufferedImage in){

        int [][] biezacy = new int[in.getWidth()+2][in.getHeight()+2];
        int [][] biezacyOld = new int[in.getWidth()+2][in.getHeight()+2];

        BufferedImage out = new BufferedImage(in.getWidth(), in.getHeight(),in.getType());
        int szerokosc = in.getWidth()+2;
        int wysokosc = in.getHeight()+2;
        int waga;


        for(int i = 1; i < szerokosc-1;i++) {
            for(int j = 1;j < wysokosc-1;j++) {
                if ( in.getRGB(i-1, j-1) == czarny) {
                    biezacy[i][j] = 1;
                } else {
                    biezacy[i][j] = 0;
                }
            }
        }

        boolean zmiany;
        do
        {
            zmiany = false;
            for(int i = 1; i < szerokosc-1;i++) {
                for(int j = 1;j < wysokosc-1;j++) {
                    if ( biezacy[i][j] == 1) {
                        if ( biezacy[i-1][j] == 0 || biezacy[i][j-1] == 0
                                || biezacy[i+1][j] == 0 || biezacy[i][j+1] ==0 ) {
                            biezacy[i][j] = 2;
                        }
                    }
                }
            }
            for(int i = 1; i < szerokosc-1;i++) {
                for(int j = 1;j < wysokosc-1;j++) {
                    if ( biezacy[i][j] == 1) {
                        if ( biezacy[i-1][j-1] == 0 || biezacy[i-1][j+1] == 0
                                || biezacy[i+1][j-1] == 0 || biezacy[i+1][j+1] == 0) {
                            biezacy[i][j] = 3;
                        }
                    }
                }
            }
            for(int i = 1; i < szerokosc-1;i++) {
                for(int j = 1;j < wysokosc-1;j++) {
                    if ( biezacy[i][j] == 2) {
                        waga = getWaga(biezacy, i, j);
                        if ( contains(waga, czworki)) {
                            biezacy[i][j] = 4;
                        }
                    }
                }
            }
            for(int i = 1; i < szerokosc-1;i++) {
                for (int j = 1; j < wysokosc - 1; j++) {
                    if (biezacy[i][j] == 4) {
                        convertToBinary(biezacy, i, j);
                    }

                }
            }
            for(int i = 1; i < szerokosc-1;i++) {
                for (int j = 1; j < wysokosc - 1; j++) {
                    if (biezacy[i][j] == 2 ) {
                        convertToBinary(biezacy, i, j);
                    }

                }
            }
            for(int i = 1; i < szerokosc-1;i++) {
                for (int j = 1; j < wysokosc - 1; j++) {
                    if (biezacy[i][j] == 3 ) {
                        convertToBinary(biezacy, i, j);
                    }

                }
            }

            for(int i = 1; i < szerokosc-1;i++) {
                for (int j = 1; j < wysokosc - 1; j++) {
                    if (biezacy[i][j] != biezacyOld[i][j]) {
                        zmiany = true;
                    }
                    biezacyOld[i][j] = biezacy[i][j];
                }
            }

        }
        while(zmiany);

        for(int i = 1; i < szerokosc-1;i++) {
            for(int j = 1;j < wysokosc-1;j++) {
                if (biezacy[i][j] == 0) {
                    out.setRGB(i-1, j-1, bialy);
                } else {
                    out.setRGB(i-1, j-1, czarny);
                }
            }
        }

        return out;
    }

    private static void convertToBinary(int[][] biezacy, int i, int j) {
        int waga = getWaga(biezacy, i, j);
        if(contains(waga, wyciecia)) {
            biezacy[i][j] = 0;
        }
        else biezacy[i][j] = 1;
    }

    private static boolean contains(int waga, int [] list) {
        for (int i : list) {
            if (i == waga) {
                return true;
            }
        }
        return false;
    }

    private static int getWaga(int[][] biezacy, int i, int j) {
        int waga = 0;
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                if (biezacy[i+x][j+y] != 0) {
                    waga += maska[x+1][y+1];
                }
            }
        }
        return waga;
    }

}
