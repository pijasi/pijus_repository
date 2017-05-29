package com.example.pijus.bortaiapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.widget.ImageView;

import static com.example.pijus.bortaiapp.Main.rastiCentroKord;
import static com.example.pijus.bortaiapp.Main.rastiKoordinatesApaciai;
import static com.example.pijus.bortaiapp.Main.rastiKoordinatesVirsui;
import static com.example.pijus.bortaiapp.Main.rastiSpinduli;
import static com.example.pijus.bortaiapp.Main.rastiVirsutinioBortoAK;

public class ApdorotiActivity extends AppCompatActivity {

    Intent intent;
    double plotis;
    double aukstis;
    int bortai1;

    public Bortas[] bortai () {

        Bortas kampinis = Bortas.nesipjaus();
        kampinis.setKoordAD(new PointF(15,0));
        Bortas virsutinis = Bortas.pjausis();
        virsutinis.setIlgisV(40);
        PointF vk = new PointF((float)(plotis / 2 - virsutinis.getIlgisV() / 2),(float) aukstis);
        PointF vd = new PointF((float)(plotis / 2 + virsutinis.getIlgisV() / 2), (float)aukstis);
        virsutinis.setKoordVK(vk);
        virsutinis.setKoordVD(vd);



        PointF centroKoord = rastiCentroKord(virsutinis.getKoordVK().x, virsutinis.getKoordVK().y ,
                virsutinis.getKoordVK().x, virsutinis.getKoordVK().y  * -1,
                kampinis.getKoordVD().x, kampinis.getKoordVD().y);

        double spindulys = rastiSpinduli(kampinis.getKoordVD().x, kampinis.getKoordVD().y, centroKoord.x, centroKoord.y);

        rastiVirsutinioBortoAK(spindulys - 15, centroKoord, aukstis - 15, virsutinis);




        Bortas[] bortai = new Bortas[bortai1];
        for (int i = 0; i < bortai1; i++) {
            bortai[i] = Bortas.pjausis();
        }


        rastiKoordinatesVirsui(virsutinis,kampinis,bortai,plotis,aukstis,bortai1, spindulys, centroKoord.x);
        rastiKoordinatesApaciai(virsutinis, kampinis, bortai, plotis - 30, aukstis - 15, bortai1, spindulys, centroKoord.x);
        bortai[0].setKoordAK(new PointF(15,0));


        for (int i = 1; i < bortai.length; i++) {
            bortai[i].skaiciuotiPjovimoKampus();
        }
        virsutinis.skaiciuotiPjovimoKampus();

        Bortas[] bortaiVisi = new Bortas[bortai.length + 2];
        int counter = 0;
        bortaiVisi[counter++] = kampinis;
        for (Bortas bor : bortai) {
            bortaiVisi[counter++] = bor;
        }
        bortaiVisi[counter] = virsutinis;
        return bortaiVisi;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apdoroti);
        //ConstraintLayout mConstraintLayout = new ConstraintLayout(this);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        intent = getIntent();
        plotis = intent.getDoubleExtra("plotis", 0);
        aukstis = intent.getDoubleExtra("aukstis", 0);
        bortai1 = intent.getIntExtra("bortai", 0);


        ImageView image = (ImageView) findViewById(R.id.imageView);


        Display mdisp = getWindowManager().getDefaultDisplay();
        Point mdispSize = new Point();
        mdisp.getSize(mdispSize);
        float maxX = mdispSize.x;
        float maxY = mdispSize.y - myToolbar.getHeight();

        Bitmap bitmap = Bitmap.createBitmap((int)maxX,(int) maxY, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLUE);
        paint.setTextSize(25);
        paint.setStrokeWidth(5);
        Bortas[] bortai = bortai();


        float konstX = maxX / bortai[bortai.length - 1].getKoordVD().x;
        float konstY = maxY / (float) (aukstis);

        for (int i = 1; i < bortai.length - 1; i++) {
            bortai[i].piestiBorta(canvas, paint, konstX, konstY);
        }

        bortai[bortai.length - 1].piestiVirsutini(canvas, paint, konstX, konstY);

        for (int i = 1; i < bortai.length; i++) {
            bortai[i].piestiPjovimoKampus(canvas, paint, konstX, konstY);
        }

        paint.setColor(Color.BLUE);
        bortai[2].piestiLegenda(bortai[2].getIlgisV(), canvas, paint, maxX, maxY, 100);
        paint.setColor(Color.GREEN);
        bortai[2].piestiLegenda(bortai[2].getIlgisA(), canvas, paint, maxX, maxY, 130);
        paint.setColor(Color.YELLOW);
        bortai[bortai.length - 1].piestiLegenda(bortai[bortai.length - 1].getIlgisV(), canvas, paint, maxX, maxY, 160);
        paint.setColor(Color.GRAY);
        bortai[bortai.length - 1].piestiLegenda(bortai[bortai.length - 1].getIlgisA(), canvas, paint, maxX, maxY, 190);
        paint.setColor(Color.RED);
        canvas.drawText("Pjovimo kampai ", maxX - 300, 220, paint);



        image.setImageBitmap(bitmap);

    }


}
