package mobtime.henrique.com.br.unipet;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmObject;
import mobtime.henrique.com.br.unipet.network.NetworkConnection;
import mobtime.henrique.com.br.unipet.pojo.Usuario;

public class Cadastro extends AppCompatActivity {

    private EditText etNome;
    private EditText etEmail;
    private EditText etSenha;
    private EditText etTelefone;
    private EditText etEndereco;
    private Spinner spCidade;
    private Button btnFinalizar;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Cadastro");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        etNome = (EditText) findViewById(R.id.etClienteNome);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etSenha = (EditText) findViewById(R.id.etSenha);
        etTelefone = (EditText) findViewById(R.id.etTelefone);
        etEndereco = (EditText) findViewById(R.id.etEndereco);
        spCidade = (Spinner) findViewById(R.id.spCidade);

        String cidades[] = {"São Paulo"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, cidades);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCidade.setAdapter(adapter);

    }

    public void finalizar(View v) {

        final String nome = etNome.getText().toString();
        final String endereco = etEndereco.getText().toString();
        final String telefone = etTelefone.getText().toString();
        final String email = etEmail.getText().toString();
        final String senha = etSenha.getText().toString();
        final String cidade = spCidade.getSelectedItem().toString();

        // Instantiate the RequestQueue.
        NetworkConnection nc = NetworkConnection.getInstance(getApplicationContext());
        RequestQueue queue = nc.getRequestQueue();

        // Request a string response from the provided URL.
        StringRequest sr = new StringRequest(Request.Method.POST, "http://cardappweb.com/pet_cares/cadastrar.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //Recupera o JSON e converte em objeto
                            Gson gson = new GsonBuilder()
                                    .setExclusionStrategies(new ExclusionStrategy() {
                                        @Override
                                        public boolean shouldSkipField(FieldAttributes f) {
                                            return f.getDeclaringClass().equals(RealmObject.class);
                                        }

                                        @Override
                                        public boolean shouldSkipClass(Class<?> clazz) {
                                            return false;
                                        }
                                    })
                                    .create();

                            Usuario usuario = gson.fromJson(response, Usuario.class);

                            //Grava o objeto no Realm
                            Realm realm = Realm.getInstance(Cadastro.this);
                            realm.beginTransaction();
                            realm.copyToRealmOrUpdate(usuario);
                            realm.commitTransaction();
                            realm.close();


                            //Salva nas preferências para continuar logado
                            SharedPreferences settings = getSharedPreferences("LOGIN", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putBoolean("logged", true);
                            editor.putInt("id", usuario.getId());
                            editor.commit();


                            Intent it = new Intent(Cadastro.this, MainActivity.class);
                            startActivity(it);

                            pd.dismiss();

                            finish();

                        } catch (com.google.gson.JsonSyntaxException ex) {
                            pd.dismiss();
                            Toast.makeText(getApplicationContext(), "Ocorreu um erro.", Toast.LENGTH_LONG).show();
                            Log.i("Error", ex.getMessage());

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

                params.put("nome", nome);
                params.put("endereco", endereco);
                params.put("cidade", cidade);
                params.put("telefone", telefone);
                params.put("email", email);
                params.put("senha", senha);

                return params;
            }
        };

        queue.add(sr);
        pd = ProgressDialog.show(this, "Aguarde...", "", true);

    }


}
