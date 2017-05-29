package com.example.pijus.bortaiapp;

import android.graphics.PointF;


public class Main {


    public static void rastiVirsutinioBortoAK(double spindulys, PointF centras, double aukstis, Bortas virsutinis){

        double b = 2 * centras.x;
        double c = (centras.x * centras.x + aukstis * aukstis - spindulys * spindulys);

        double d = b * b - 4 * c;
        double x = (b - Math.sqrt(d))/2;
        PointF p = new PointF((float)x, (float)aukstis);
        virsutinis.setKoordAK(p);

        rastiVirsutinioBortoAD(virsutinis);
    }

    public static void rastiVirsutinioBortoAD(Bortas virsutinis){
        double dx = virsutinis.getKoordAK().x - virsutinis.getKoordVK().x;
        dx = virsutinis.getIlgisV() - 2* dx;
        virsutinis.setIlgisA(dx);
        dx = virsutinis.getKoordAK().x + dx;
        PointF p = new PointF((float)dx, virsutinis.getKoordAK().y);
        virsutinis.setKoordAD(p);
    }



    public static void rastiKoordinatesApaciai(Bortas virsutinis, Bortas kampinis, Bortas[] bortai, double plotis, double ilgis,
                                               int kiekis, double spindulys, double c_x) {



        spindulys = spindulys - 15;
        double krastine = c_x - virsutinis.getKoordAK().x;
        double dalinimoKampas = rastiKampaBortamsApatLank(krastine, ilgis) / kiekis;
        priskirtiBortamsAD(kiekis, dalinimoKampas, spindulys, plotis, bortai, c_x);
        priskirtiBortamsAK(bortai);

    }

    public static void priskirtiBortamsAK(Bortas[] bortai){
        for (int i = 0; i < bortai.length -1; i++) {
            bortai[i + 1].setKoordAK(bortai[i].getKoordAD());
        }
    }

    public static void priskirtiBortamsAD(int kiekis, double dalinimoKampas, double r, double plotis, Bortas[] bortai, double c_x){
        double kampas = 0;
        for (int i = 0; i < kiekis; i++) {
            kampas += dalinimoKampas;
            bortai[i].setKoordAD(rastiBortuKoord(r, kampas, plotis, c_x));
        }
    }

    public static Bortas[] rastiKoordinatesVirsui(Bortas virsutinis, Bortas kampinis, Bortas[] bortai, double plotis, double ilgis,
                                                  int kiekis, double spindulys, double c_x){

        double dalinimoKampas = rastiKampaBortamsVirsLank(spindulys, ilgis) / kiekis;

        bortai = priskirtiBortamsVD(kiekis, dalinimoKampas, spindulys, plotis, bortai, c_x);
        priskirtiBortamsVK(bortai);
        return bortai;
    }

    public static void priskirtiBortamsVK(Bortas[] bortai){
        for (int i = 0; i < bortai.length -1; i++) {
            bortai[i + 1].setKoordVK(bortai[i].getKoordVD());
        }
    }

    public static Bortas[] priskirtiBortamsVD(int kiekis, double dalinimoKampas, double r, double plotis, Bortas[] bortai, double c_x){
        double kampas = 0;
        for (int i = 0; i < kiekis; i++) {
            kampas += dalinimoKampas;
            bortai[i].setKoordVD(rastiBortuKoord(r, kampas, plotis, c_x));
        }

        return bortai;
    }

    public static int[] galimasBortuSk(double r, double h){
        double kampas = rastiKampaBortamsVirsLank(r, h);

        double kampasIlgiausiamBortui = (360 * 100) / (2 * Math.PI * r);
        double kampasTrumpiausiamBortui = (360 * 30) / (2 * Math.PI * r);

        int kiekisIlgiausiais = (int) (kampas / kampasIlgiausiamBortui) + 1;
        int kiekisTrumpiausiais = (int) (kampas / kampasTrumpiausiamBortui) + 1;

        int[] variantai = new int[kiekisTrumpiausiais - kiekisIlgiausiais];
        int sk = kiekisIlgiausiais;
        for (int i = 0; i <  variantai.length; i++) {
            variantai[i] = sk++;
        }
        return variantai;
    }



    public static double rastiKampaBortamsVirsLank(double r, double h){

        double kampas = Math.acos(h/r);
        kampas = Math.toDegrees(kampas);
        return 90 - kampas;
    }

    public static double rastiKampaBortamsApatLank(double a, double h){
        double kampas = Math.atan(a/h);
        kampas = Math.toDegrees(kampas);
        return 90 - kampas;
    }

    public static double rastiSpinduli(double x1, double y1, double x2, double y2){

        double r = Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));

        return r;
    }

    public static PointF rastiCentroKord(double x1, double y1, double x2, double y2, double x3, double y3){
        double c_x;
        double c_y;
        double D;

        D = 2 * (x1 * (y2 - y3) + x2 * (y3 - y1) + x3 * (y1 - y3));

        c_x = ( (Math.pow(x1, 2) + Math.pow(y1, 2)) * (y2 - y3) + (Math.pow(x2, 2)
                + Math.pow(y2, 2)) * (y3 - y1) + (Math.pow(x3, 2) + Math.pow(y3, 2)) * (y1 - y2)) / D;

        c_y = ( (Math.pow(x1, 2) + Math.pow(y1, 2)) * (x3 - x2) + (Math.pow(x2, 2)
                + Math.pow(y2, 2)) * (x1 - x3) + (Math.pow(x3, 2) + Math.pow(y3, 2)) * (x2 - x1)) / D;

        return new PointF((float)(c_x),(float) c_y);
    }

    public static PointF rastiBortuKoord(double r, double dalinimoKampas, double plotis, double c_x){
        double ilgisX = Math.cos(Math.toRadians(dalinimoKampas)) * r;
        double x = c_x - ilgisX;
        double ilgisY = Math.sin(Math.toRadians(dalinimoKampas)) * r;
        double y =  ilgisY;
        return new PointF((float)x,(float)y);
    }

}
