package mobtime.henrique.com.br.unipet;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class Agendamento extends AppCompatActivity {

    private Spinner spinnerEspecie;
    private Spinner spinnerPorte;
    private Spinner spinnerHora;
    private Spinner spinnerPagamento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agendamento);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Agendamento");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        spinnerEspecie = (Spinner) findViewById(R.id.spinnerEspecie);
        spinnerPorte = (Spinner) findViewById(R.id.spinnerPorte);
        spinnerHora = (Spinner) findViewById(R.id.spinnerHora);
        spinnerPagamento = (Spinner) findViewById(R.id.spinnerPagamento);

        //Seta font
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/ProximaNova-Semibold.ttf");
        TextView tv9 = (TextView) findViewById(R.id.textView9);
        tv9.setTypeface(tf);
        TextView tv10 = (TextView) findViewById(R.id.textView10);
        tv10.setTypeface(tf);
        TextView tv11 = (TextView) findViewById(R.id.textView11);
        tv11.setTypeface(tf);
        TextView tv12 = (TextView) findViewById(R.id.textView12);
        tv12.setTypeface(tf);

        String especies[] = {"Cachorro", "Gato"};
        String porte[] = {"Pequeno", "Médio", "Grande"};
        String hora[] = {"Qualquer horário de Manhã", "Qualquer horário à Tarde", "08h", "09h", "10h", "11h", "12h", "13h", "14h", "15h",
        "16h", "17h", "18h", "19h", "20h"};
        String pagamento[] = {"PagSeguro", "Bitcoin", "Dinheiro"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, especies);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, porte);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, hora);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, pagamento);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEspecie.setAdapter(adapter);
        spinnerPorte.setAdapter(adapter2);
        spinnerHora.setAdapter(adapter3);
        spinnerPagamento.setAdapter(adapter4);
    }
}
