package com.abrahamsantos.editorphotostouch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    SeekBar CRojo;
    SeekBar CVerde;
    SeekBar CAzul;
    TextView texto;
    int IR=0,IG=0,IB=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        CRojo = findViewById(R.id.rojo);
        CVerde = findViewById(R.id.Green);
        CAzul = findViewById(R.id.Blue);

        CRojo.setMax(255);
        CVerde.setMax(255);
        CAzul.setMax(255);

        texto = findViewById(R.id.txt);

        SeekBar.OnSeekBarChangeListener listener = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                switch (seekBar.getId()) {
                    case R.id.rojo:
                        IR = CRojo.getProgress();
                        MainActivity.CR=IR;
                        break;
                    case R.id.Green:
                        IG = CVerde.getProgress();
                        MainActivity.CG=IG;
                        break;
                    case R.id.Blue:
                        IB = CAzul.getProgress();
                        MainActivity.CB=IB;
                        break;

                }
                texto.setText("Rojo: "+IR+"\nVerde: "+IG+"\nAzul: "+IB);
                if(IR<=20 && IG<=20 && IB<=20){
                    texto.setTextColor(android.graphics.Color.rgb(255,255,255));
                }else{
                    texto.setTextColor(android.graphics.Color.rgb(0,0,0));
                }
                texto.setBackgroundColor(android.graphics.Color.rgb(IR,IG,IB));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        };
        CRojo.setOnSeekBarChangeListener(listener);
        CVerde.setOnSeekBarChangeListener(listener);
        CAzul.setOnSeekBarChangeListener(listener);


        }

}
