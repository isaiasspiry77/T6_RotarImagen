package com.abrahamsantos.editorphotostouch;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.SeekBar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    public static int CR, CB, CG;
    public static final String tag = "MyTouchEvent";
    private Canvas canvas;
    private Paint paint;
    private float down_x = 0, down_y = 0, move_x = 0, move_y = 0;
    ImageView ima;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.barraStilo);
        ima = findViewById(R.id.imagenEdit);
        SeekBar girar = findViewById(R.id.prueba);
        girar.setMax(360);
        toolbar.setTitle("Editor");
        setSupportActionBar(toolbar);

        SeekBar.OnSeekBarChangeListener listener = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int progreso=girar.getProgress();
                ima.setRotation((float)progreso);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        };
        girar.setOnSeekBarChangeListener(listener);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btnEdit:
                Intent otherActivity = new Intent(this, Main2Activity.class);
                startActivity(otherActivity);
                pintaImg();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void pintaImg() {

        Bitmap original = ((BitmapDrawable) ima.getDrawable()).getBitmap();

        Bitmap mutable = original.copy(Bitmap.Config.ARGB_8888, true);

        canvas = new Canvas(mutable);

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);

        paint.setARGB(255, CR, CG, CB);

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(20);

        ima.setImageBitmap(mutable);

        ima.setOnTouchListener((view, motionEvent) -> {
            paint.setARGB(255, CR, CG, CB);
            Matrix inverse = new Matrix();
            ima.getImageMatrix().invert(inverse);
            float[] pts = {
                    motionEvent.getX(), motionEvent.getY()
            };
            inverse.mapPoints(pts);
            String message = "";

            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    message = String.format(Locale.US, "Touch DOWN on (%.2f, %.2f)", motionEvent.getX(), motionEvent.getY());
                    down_x = (float) Math.floor(pts[0]);
                    down_y = (float) Math.floor(pts[1]);
                    Log.i(tag, message);

                    return true;
                case MotionEvent.ACTION_UP:
                    message = String.format(Locale.US, "Touch UP on (%.2f, %.2f)", motionEvent.getX(), motionEvent.getY());
                    Log.i(tag, message);
                    return true;
                case MotionEvent.ACTION_MOVE:
                    message = String.format(Locale.US, "MOVING on (%.2f, %.2f)", motionEvent.getX(), motionEvent.getY());
                    move_x = (float) Math.floor(pts[0]);
                    move_y = (float) Math.floor(pts[1]);
                    if (move_x != down_x & move_y != down_y) {
                        canvas.drawLine(down_x, down_y, move_x, move_y, paint);
                        down_x = move_x;
                        down_y = move_y;
                        ima.invalidate();
                    }
                    Log.i(tag, message);
                    break;
            }
            return super.onTouchEvent(motionEvent);
        });
        }
}
