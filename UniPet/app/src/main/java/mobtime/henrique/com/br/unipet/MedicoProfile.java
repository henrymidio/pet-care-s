package mobtime.henrique.com.br.unipet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MedicoProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medico_profile);
        getSupportActionBar().hide();
    }
}
