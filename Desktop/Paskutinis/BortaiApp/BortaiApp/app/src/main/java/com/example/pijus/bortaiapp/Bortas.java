package com.example.pijus.bortaiapp;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;

/**
 * Created by Pijus on 2017-05-23.
 */
public class Bortas {
    private double plotis = 15;
    private double ilgisV;
    private double ilgisA;
    private PointF koordVK;
    private PointF koordVD;
    private PointF koordAK;
    private PointF koordAD;
    private double desinysKampas;
    private double kairysKampas;
    private boolean arPjausis;

    private Bortas(double ilgisV, double ilgisA, PointF koordVK, PointF koordVD, PointF koordAK, PointF koordAD, boolean arPjausis){
        this.ilgisV = ilgisV;
        this.ilgisA = ilgisA;
        this.koordVK = koordVK;
        this.koordVD = koordVD;
        this.koordAK = koordAK;
        this.koordAD = koordAD;
        this.arPjausis = arPjausis;
    }

    public void skaiciuotiPjovimoKampus(){
        virsutinisIlgis();
        apatinisIlgis();
        kampasDesinys();
        kampasKairys();
    }

    private void kampasKairys(){
        double ilgis = Math.sqrt((Math.pow(this.koordVK.x - this.koordAK.x, 2 ) + Math.pow(this.koordVK.y - this.koordAK.y, 2)));
        double kampas = Math.sqrt(ilgis * ilgis - plotis * plotis);
        this.kairysKampas = kampas;
    }

    private void kampasDesinys(){
        double ilgis = Math.sqrt((Math.pow(this.koordVD.x - this.koordAD.x, 2 ) + Math.pow(this.koordVD.y - this.koordAD.y, 2)));
        double kampas = Math.sqrt(ilgis * ilgis - plotis * plotis);
        this.desinysKampas = kampas;
    }

    public void apatinisIlgis(){
        double ilgis = Math.sqrt((Math.pow(this.koordAD.x - this.koordAK.x, 2 ) + Math.pow(this.koordAD.y - this.koordAK.y, 2)));
        this.ilgisA = ilgis;
    }

    public void virsutinisIlgis(){
        double ilgis = Math.sqrt((Math.pow(this.koordVD.x - this.koordVK.x, 2 ) + Math.pow(this.koordVD.y - this.koordVK.y, 2)));
        this.ilgisV = ilgis;
    }

    public static Bortas nesipjaus(){
        return new Bortas(100, 100, new PointF(0,-100), new PointF(0,0),
                new PointF(15,-100), new PointF(15,0), false);
    }

    public static Bortas pjausis(){
        return  new Bortas(0,0, new PointF(), new PointF(), new PointF(), new PointF(),
                true);
    }

    public void piestiIlgius(Canvas canvas, Paint paint, float kofx, float kofy){
        String ilgis = String.valueOf(Math.floor(ilgisV * 100) / 100);
        canvas.drawText(ilgis, koordVK.x * kofx, (koordVK.y + (float) ilgisV/2) * kofy, paint);
        ilgis = String.valueOf(Math.floor(ilgisA * 100) / 100);
        canvas.drawText(ilgis, koordAK.x * kofx, (koordAK.y + (float) ilgisA/2) * kofy, paint);
    }

    public void piestiLegendaVirsui(Canvas canvas, Paint paint, float kofx, float kofy){
        paint.setColor(Color.BLUE);
        String ilgis = String.valueOf(Math.floor(ilgisV * 100) /100);
        canvas.drawText(ilgis, (koordVK.x + (float) ilgisV/2) * kofx , koordVK.y * kofy -3, paint);
        ilgis = String.valueOf(Math.floor(ilgisA * 100) /100);
        canvas.drawText(ilgis, (koordAK.x + (float) ilgisA/2) * kofx , koordAK.y * kofy -3, paint);
        paint.setColor(Color.BLACK);
    }

    public void piestiPjovimoKampus(Canvas canvas, Paint paint, float kofx, float kofy){
        paint.setColor(Color.RED);
        String ilgis = String.valueOf(Math.floor(kairysKampas * 100) / 100);
        canvas.drawText(ilgis, (koordAK.x + 1) * kofx, (koordAK.y - 2) * kofy, paint);

    }


    public void piestiBorta(Canvas canvas, Paint paint, float kofx, float kofy){
        paint.setColor(Color.BLUE);
        canvas.drawLine(koordVK.x * kofx, koordVK.y * kofy, koordVD.x * kofx, koordVD.y * kofy, paint);
        paint.setColor(Color.BLACK);
        canvas.drawLine(koordVD.x * kofx, koordVD.y * kofy, koordAD.x * kofx, koordAD.y * kofy, paint);
        paint.setColor(Color.GREEN);
        canvas.drawLine(koordAD.x * kofx, koordAD.y * kofy, koordAK.x * kofx, koordAK.y * kofy, paint);
        paint.setColor(Color.BLACK);
        canvas.drawLine(koordAK.x * kofx, koordAK.y * kofy, koordVK.x * kofx, koordVK.y * kofy, paint);
    }

    public void piestiVirsutini(Canvas canvas, Paint paint, float kofx, float kofy){
        paint.setColor(Color.YELLOW);
        canvas.drawLine(koordVK.x * kofx, koordVK.y * kofy, koordVD.x * kofx, koordVD.y * kofy, paint);
        paint.setColor(Color.BLACK);
        canvas.drawLine(koordVD.x * kofx, koordVD.y * kofy, koordAD.x * kofx, koordAD.y * kofy, paint);
        paint.setColor(Color.GRAY);
        canvas.drawLine(koordAD.x * kofx, koordAD.y * kofy, koordAK.x * kofx, koordAK.y * kofy, paint);
        paint.setColor(Color.BLACK);
        canvas.drawLine(koordAK.x * kofx, koordAK.y * kofy, koordVK.x * kofx, koordVK.y * kofy, paint);
    }

    public void piestiLegenda(double ilgis, Canvas canvas, Paint paint, float maxX, float maxY, int n){
        String ilgis1 =  String.valueOf(Math.floor(ilgis * 100) / 100);
        canvas.drawText("Borto ilgis " + ilgis1, maxX - 300, n, paint);
    }


    public double getIlgisV() {
        return ilgisV;
    }

    public void setIlgisV(double ilgisV) {
        this.ilgisV = ilgisV;
    }

    public double getIlgisA() {
        return ilgisA;
    }

    public void setIlgisA(double ilgisA) {
        this.ilgisA = ilgisA;
    }

    public PointF getKoordVK() {
        return koordVK;
    }

    public void setKoordVK(PointF koordVK) {
        this.koordVK = koordVK;
    }

    public PointF getKoordVD() {
        return koordVD;
    }

    public void setKoordVD(PointF koordVD) {
        this.koordVD = koordVD;
    }

    public PointF getKoordAK() {
        return koordAK;
    }

    public void setKoordAK(PointF koordAK) {
        this.koordAK = koordAK;
    }

    public PointF getKoordAD() {
        return koordAD;
    }

    public void setKoordAD(PointF koordAD) {
        this.koordAD = koordAD;
    }
}
