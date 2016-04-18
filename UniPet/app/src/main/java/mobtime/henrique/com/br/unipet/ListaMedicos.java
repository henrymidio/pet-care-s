package mobtime.henrique.com.br.unipet;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import mobtime.henrique.com.br.unipet.adapters.ListaMedicosAdapter;
import mobtime.henrique.com.br.unipet.pojo.Medico;

public class ListaMedicos extends AppCompatActivity {

    private ListView lvListaMedicos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_medicos);
        getWindow().getDecorView().setBackgroundColor(Color.WHITE);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Equipe");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Medico medico = new Medico("Dra. Bruna Tortoro", "CRMV-SP 20.303", R.drawable.bruna);
        Medico medico2 = new Medico("Dra. Tatiane Faria", "CRMV 25588", R.drawable.tatiane);
        List<Medico> lista = new ArrayList<>();
        lista.add(medico);
        lista.add(medico2);

        lvListaMedicos = (ListView) findViewById(R.id.lvListaMedicos);
        lvListaMedicos.setAdapter(new ListaMedicosAdapter(this, lista));
        lvListaMedicos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent it = new Intent(ListaMedicos.this, MedicoProfile.class);
                startActivity(it);
            }
        });
    }
}
