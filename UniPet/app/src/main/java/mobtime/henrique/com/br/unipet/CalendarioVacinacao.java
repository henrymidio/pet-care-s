package mobtime.henrique.com.br.unipet;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import io.realm.Realm;
import mobtime.henrique.com.br.unipet.pojo.Animal;

public class CalendarioVacinacao extends AppCompatActivity {
    private EditText etV4;
    private EditText etAntiRabica;
    private EditText etV10;
    private EditText etVermifugo;
    private EditText etGiardia;
    private Realm realm;
    private Animal pet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario_vacinacao);
        getWindow().getDecorView().setBackgroundColor(Color.WHITE);

        //Seta Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Calendário de Vacinação");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Recupera dados do pet no Realm
        SharedPreferences sp = getSharedPreferences("LOGIN", Context.MODE_PRIVATE);
        int dono = sp.getInt("id", 0);
        realm = Realm.getInstance(this);
        pet = realm.where(Animal.class).equalTo("dono", dono).findFirst();

        //Seta EditText views
        etAntiRabica = (EditText) findViewById(R.id.etAntiRabica);
        etAntiRabica.setText(""+pet.getAntiRabica());
        etGiardia    = (EditText) findViewById(R.id.etGiardia);
        etGiardia.setText(""+pet.getGiardia());
        etV10 = (EditText) findViewById(R.id.etV10);
        etV10.setText(""+pet.getV10());
        etVermifugo    = (EditText) findViewById(R.id.etVermifugo);
        etVermifugo.setText(""+pet.getVermífugo());
        etV4 = (EditText) findViewById(R.id.etV4);
        etV4.setText(""+pet.getV4());

    }

    @Override
    protected void onPause() {
        super.onPause();
        realm.beginTransaction();
        pet.setAntiRabica(etAntiRabica.getText().toString());
        pet.setGiardia(etGiardia.getText().toString());
        pet.setV10(etV10.getText().toString());
        pet.setV4(etV4.getText().toString());
        pet.setVermífugo(etVermifugo.getText().toString());
        realm.commitTransaction();
        realm.close();
        Toast.makeText(this, "Calendário atualizado", Toast.LENGTH_SHORT).show();
    }
}
