package com.example.pijus.bortaiapp;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.Arrays;

import static com.example.pijus.bortaiapp.Main.galimasBortuSk;
import static com.example.pijus.bortaiapp.Main.rastiCentroKord;
import static com.example.pijus.bortaiapp.Main.rastiSpinduli;

public class MainActivity extends AppCompatActivity {

    public static final String title = "Salelės";
    private Intent i;
    public double plotis;
    public double aukstis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        ViewDialog alert = new ViewDialog();
        alert.showDialog(MainActivity.this, getText(R.string.pagalba_string));

    }

    public void apdoroti(View view) {
        EditText editText = (EditText) findViewById(R.id.editText);
        EditText editText2 = (EditText) findViewById(R.id.editText2);
        if (editText.getText().toString().isEmpty() || editText2.getText().toString().isEmpty() ) {
            ViewDialog alert = new ViewDialog();
            alert.showDialog(MainActivity.this, getText(R.string.pagalba_string));
        }
        else {
            i = new Intent(this, ApdorotiActivity.class);
            plotis = Double.parseDouble(editText.getText().toString());
            aukstis = Double.parseDouble(editText2.getText().toString());

            Bortas kampinis = Bortas.nesipjaus();
            Bortas virsutinis = Bortas.pjausis();
            virsutinis.setIlgisV(plotis * 0.2);
            PointF vk = new PointF((float)(plotis / 2 - virsutinis.getIlgisV() / 2),(float) (aukstis));
            PointF vd = new PointF((float)(plotis / 2 + virsutinis.getIlgisV() / 2), (float)(aukstis));
            virsutinis.setKoordVK(vk);
            virsutinis.setKoordVD(vd);

            PointF centroKoord = rastiCentroKord(virsutinis.getKoordVK().x, virsutinis.getKoordVK().y ,
                    virsutinis.getKoordVK().x, virsutinis.getKoordVK().y  * -1,
                    kampinis.getKoordVD().x, kampinis.getKoordVD().y);

            double spindulys = rastiSpinduli(kampinis.getKoordVD().x, kampinis.getKoordVD().y, centroKoord.x, centroKoord.y);
            int[] variantai = galimasBortuSk(spindulys, aukstis);
            Arrays.sort(variantai);
            String[] a = new String[variantai.length];
            int i = 0;
            while (i < variantai.length) {
                a[i] = String.valueOf(variantai[i++]);
            }
            if (a.length == 0) {
                ViewDialog alert = new ViewDialog();
                alert.showDialog(MainActivity.this, getText(R.string.error_message));
            }
            else onCreateDialog(a).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(this, OptionsItemSelect.class);
        switch (item.getItemId()) {
            case R.id.action_settings1:
                // Pagalbos iškvietimas
                CharSequence message = getText(R.string.pagalba_string);
                String newTitle = getString(R.string.action_settings1);
                intent.putExtra("Text", message);
                intent.putExtra(title, newTitle);
                this.startActivity(intent);
                break;

            case R.id.action_settings2:
                // Apie iškvietimas
                message = getText(R.string.apie_string);
                newTitle = getString(R.string.action_settings2);
                intent.putExtra("Text", message);
                intent.putExtra(title, newTitle);
                this.startActivity(intent);
                break;

            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    public Dialog onCreateDialog(String[] variantai) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, variantai);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.bortu_kiekis)
                .setCancelable(false)
                .setAdapter(adapter, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        ListView lw = ((AlertDialog)dialog).getListView();
                        String s = lw.getItemAtPosition(item).toString();
                        int b = Integer.parseInt(s);
                        i.putExtra("bortai", b);
                        i.putExtra("plotis", plotis);
                        i.putExtra("aukstis", aukstis);
                        startActivity(i);
                    }
                });
        return builder.create();
    }

}
