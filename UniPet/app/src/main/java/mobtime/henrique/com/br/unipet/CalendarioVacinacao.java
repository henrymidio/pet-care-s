package mobtime.henrique.com.br.unipet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CalendarioVacinacao extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario_vacinacao);
        getSupportActionBar().hide();
    }
}
