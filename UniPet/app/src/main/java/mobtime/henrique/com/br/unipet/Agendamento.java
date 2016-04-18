package mobtime.henrique.com.br.unipet;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import java.util.HashMap;
import java.util.Map;
import mobtime.henrique.com.br.unipet.network.NetworkConnection;

public class Agendamento extends AppCompatActivity {

    private Spinner spinnerEspecie;
    private Spinner spinnerPorte;
    private Spinner spinnerHora;
    private Spinner spinnerPagamento;
    private ProgressDialog pd;
    private EditText etSintoma1;
    private EditText etSintoma2;
    private EditText etSintoma3;
    private DatePicker datePicker;
    private CheckBox cb1;
    private CheckBox cb2;
    private CheckBox cb3;
    private CheckBox cb4;
    private CheckBox cb5;
    private CheckBox cb6;

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

        datePicker = (DatePicker) findViewById(R.id.datePicker);
        etSintoma1 = (EditText) findViewById(R.id.etSintoma1);
        etSintoma2 = (EditText) findViewById(R.id.etSintoma2);
        etSintoma3 = (EditText) findViewById(R.id.etSintoma3);
        spinnerEspecie = (Spinner) findViewById(R.id.spinnerEspecie);
        spinnerPorte = (Spinner) findViewById(R.id.spinnerPorte);
        spinnerHora = (Spinner) findViewById(R.id.spinnerHora);
        spinnerPagamento = (Spinner) findViewById(R.id.spinnerPagamento);

        cb1 = (CheckBox) findViewById(R.id.cb1);
        cb2 = (CheckBox) findViewById(R.id.cb2);
        cb3 = (CheckBox) findViewById(R.id.cb3);
        cb4 = (CheckBox) findViewById(R.id.cb4);
        cb5 = (CheckBox) findViewById(R.id.cb5);
        cb6 = (CheckBox) findViewById(R.id.cb6);

        if(getIntent().getStringExtra("agendamento").startsWith("Va")){
            LinearLayout ll = (LinearLayout) findViewById(R.id.vacinas);
            ll.setVisibility(LinearLayout.VISIBLE);

            LinearLayout ls = (LinearLayout) findViewById(R.id.sintomas);
            ls.setVisibility(LinearLayout.GONE);
        }

        //Seta font
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/ProximaNova-Semibold.ttf");
        TextView tv9 = (TextView) findViewById(R.id.textView9);
        tv9.setTypeface(tf);
        TextView tvCaracteristicas = (TextView) findViewById(R.id.tvCaracteristicas);
        tvCaracteristicas.setTypeface(tf);
        TextView tv10 = (TextView) findViewById(R.id.textView10);
        tv10.setTypeface(tf);
        TextView tv11 = (TextView) findViewById(R.id.textView11);
        tv11.setTypeface(tf);
        TextView tv12 = (TextView) findViewById(R.id.textView12);
        tv12.setTypeface(tf);
        TextView tv13 = (TextView) findViewById(R.id.textView13);
        tv13.setTypeface(tf);

        String especies[] = {"Cachorro", "Gato"};
        String porte[] = {"Pequeno", "Médio", "Grande"};
        String hora[] = {"Qualquer horário de Manhã", "Qualquer horário à Tarde", "08h", "09h", "10h", "11h", "12h", "13h", "14h", "15h",
        "16h", "17h", "18h", "19h", "20h"};
        String pagamento[] = {"PagSeguro", "Bitcoin"};

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

    public void agendarConsulta(View v){
        new AlertDialog.Builder(this)
                .setTitle("Você confirma o agendamento da consulta?")
                .setMessage("")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {

                        //Recupera id do usuário
                        SharedPreferences settings = getSharedPreferences("LOGIN", Context.MODE_PRIVATE);
                        final int cliente_id = settings.getInt("id", 0);

                        //Recupera dados dos inputs
                        final String animal = spinnerEspecie.getSelectedItem().toString();
                        final String porte = spinnerPorte.getSelectedItem().toString();
                        final String sintoma1 = etSintoma1.getText().toString();
                        final String sintoma2 = etSintoma2.getText().toString();
                        final String sintoma3 = etSintoma3.getText().toString();
                        final String sintomas = sintoma1 + ", " + sintoma2 + ", " + sintoma3;
                        int dataMes = datePicker.getMonth() + 1;
                        int dataDia = datePicker.getDayOfMonth();
                        int dataAno = datePicker.getYear();
                        final String data = dataDia + "/" + dataMes + "/" + dataAno;
                        final String horario = spinnerHora.getSelectedItem().toString();
                        final String pagamento = spinnerPagamento.getSelectedItem().toString();

                        //Seta os valores de checkbox
                        final String v1 = (cb1.isChecked()) ? "Anti-rábica" : "";
                        final String v2 = (cb2.isChecked()) ? "Bronche" : "";
                        final String v3 = (cb3.isChecked()) ? "V10" : "";
                        final String v4 = (cb4.isChecked()) ? "Giárgia" : "";
                        final String v5 = (cb5.isChecked()) ? "Vermífugo" : "";
                        final String v6 = (cb6.isChecked()) ? "V4" : "";
                        final String vacinas = v1 + ", " + v2 + ", " + v3 + ", " + v4 + ", " + v5 + ", " + v6;

                        //Calcula o valor
                        double valor = (cb1.isChecked()) ? 64.9 : 0;
                        valor = (cb2.isChecked()) ? valor + 84.9 : valor + 0;
                        valor = (cb3.isChecked()) ? valor + 89.9 : valor + 0;
                        valor = (cb4.isChecked()) ? valor + 84.9 : valor + 0;
                        valor = (cb5.isChecked()) ? valor + 0 : valor + 0;
                        valor = (cb6.isChecked()) ? valor + 89.9 : valor + 0;

                        final double total = (valor == 0) ? valor + 150.0 : valor;


                        // Instantiate the RequestQueue.
                        NetworkConnection nc = NetworkConnection.getInstance(getApplicationContext());
                        RequestQueue queue = nc.getRequestQueue();

                        // Request a string response from the provided URL.
                        StringRequest sr = new StringRequest(Request.Method.POST, "http://cardappweb.com/pet_cares/agendamento.php",
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        pd.dismiss();
                                        if (response.startsWith("ok")) {
                                            Intent in = new Intent(Agendamento.this, Conclusao.class);
                                            in.putExtra("total", total);
                                            startActivity(in);
                                            finish();
                                        } else {
                                            Toast.makeText(Agendamento.this, "Servidor não encontrado", Toast.LENGTH_LONG).show();
                                        }

                                    }
                                }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                pd.dismiss();
                                Toast.makeText(getApplicationContext(), "Servidor não encontrado", Toast.LENGTH_LONG).show();
                            }


                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<String, String>();

                                params.put("cliente_id", "" + cliente_id);
                                params.put("animal", animal);
                                params.put("porte", porte);
                                params.put("data", data);
                                params.put("horario", horario);
                                params.put("pagamento", pagamento);
                                params.put("sintomas", sintomas);
                                params.put("vacinas", vacinas);


                                return params;
                            }
                        };

                        queue.add(sr);
                        pd = ProgressDialog.show(Agendamento.this, "Aguarde...", "", true);

                    }
                })
                .setNegativeButton(android.R.string.no, null).show();
    }
}
