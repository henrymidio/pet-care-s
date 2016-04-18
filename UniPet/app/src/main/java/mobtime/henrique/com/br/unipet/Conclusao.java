package mobtime.henrique.com.br.unipet;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Conclusao extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conclusao);
        getWindow().getDecorView().setBackgroundColor(Color.WHITE);
        getSupportActionBar().hide();

        double total = getIntent().getDoubleExtra("total", 150);

        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/ProximaNova-Semibold.ttf");
        TextView tv15 = (TextView) findViewById(R.id.textView15);
        tv15.setTypeface(tf);
        TextView tvMarca = (TextView) findViewById(R.id.tvMarca);
        tvMarca.setTypeface(tf);

        TextView tv14 = (TextView) findViewById(R.id.textView14);
        tv14.setText("TOTAL: R$ "+total);
    }
}
