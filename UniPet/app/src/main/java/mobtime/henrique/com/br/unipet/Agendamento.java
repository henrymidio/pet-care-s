package mobtime.henrique.com.br.unipet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class Agendamento extends AppCompatActivity {

    private Spinner spinnerEspecie;
    private Spinner spinnerPorte;
    private Spinner spinnerHora;
    private Spinner spinnerPagamento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agendamento);

        spinnerEspecie = (Spinner) findViewById(R.id.spinnerEspecie);
        spinnerPorte = (Spinner) findViewById(R.id.spinnerPorte);
        spinnerHora = (Spinner) findViewById(R.id.spinnerHora);
        spinnerPagamento = (Spinner) findViewById(R.id.spinnerPagamento);

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
