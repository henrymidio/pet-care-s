package mobtime.henrique.com.br.unipet;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class Launcher extends AppCompatActivity implements View.OnClickListener{

    private Button btnCadastrar;
    private Button btnLogar;
    private EditText etLogin;
    private EditText etSenha;
    private Resources resources;
    private ProgressDialog pd;
    private String user;
    private String pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        //getSupportActionBar().hide();

        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/ProximaNova-Semibold.ttf");
        TextView tv = (TextView) findViewById(R.id.textView62);
        tv.setTypeface(tf);

        etLogin = (EditText) findViewById(R.id.etLogin);
        etSenha = (EditText) findViewById(R.id.etSenha);

        //etLogin.getBackground().mutate().setColorFilter(getResources().getColor(R.color.), PorterDuff.Mode.SRC_ATOP);

        initViews();

        btnCadastrar = (Button) findViewById(R.id.btnCadastrar);
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Launcher.this, Cadastro.class);
                startActivity(it);
                finish();
            }
        });
    }

    /**
     * Recupera as views e configura os listeners para os campos editáveis e para o botão de entrar
     */
    private void initViews() {
        resources = getResources();

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                callClearErrors(s);
            }
        };


        etLogin.addTextChangedListener(textWatcher);

        etSenha.addTextChangedListener(textWatcher);

        btnLogar = (Button) findViewById(R.id.btnLogar);
        btnLogar.setOnClickListener(this);
    }

    /**
     * Chama o método para limpar erros
     *
     * @param s Editable
     */
    private void callClearErrors(Editable s) {
        if (!s.toString().isEmpty()) {
            clearErrorFields(etLogin);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnLogar) {
            if (validateFields()) {
                // Instantiate the RequestQueue.
                NetworkConnection nc = NetworkConnection.getInstance(getApplicationContext());
                RequestQueue queue = nc.getRequestQueue();

                // Request a string response from the provided URL.
                StringRequest sr = new StringRequest(Request.Method.POST, "http://cardappweb.com/pet_cares/autenticacao.php",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.i("resposta", "R: "+response);
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


                                    Realm realm = Realm.getInstance(Launcher.this);
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


                                    Intent it = new Intent(Launcher.this, MainActivity.class);
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

                        params.put("email", user);
                        params.put("senha", pass);
                        Log.i("login", ""+user);
                        Log.i("senha", ""+pass);
                        return params;
                    }
                };

                queue.add(sr);
                pd = ProgressDialog.show(this, "Aguarde...", "", true);
            }
        }
    }

    /**
     * Efetua a validação dos campos.Nesse caso, valida se os campos não estão vazios e se tem
     * tamanho permitido.
     * Nesse método você poderia colocar outros tipos de validações de acordo com a sua necessidade.
     *
     * @return boolean que indica se os campos foram validados com sucesso ou não
     */
    private boolean validateFields() {
        user = etLogin.getText().toString().trim();
        pass = etSenha.getText().toString().trim();
        return (!isEmptyFields(user, pass));
    }

    private boolean isEmptyFields(String user, String pass) {
        if (TextUtils.isEmpty(user)) {
            etLogin.requestFocus(); //seta o foco para o campo user
            etLogin.setError("Preencha o campo de Login");
            return true;
        } else if (TextUtils.isEmpty(pass)) {
            etSenha.requestFocus(); //seta o foco para o campo password
            etSenha.setError("Preencha o campo Senha");
            return true;
        }
        return false;
    }

    /*
    private boolean hasSizeValid(String user, String pass) {

        if (!(user.length() > 3)) {
            edtUser.requestFocus();
            edtUser.setError(resources.getString(R.string.login_user_size_invalid));
            return false;
        } else if (!(pass.length() > 5)) {
            edtPassword.requestFocus();
            edtPassword.setError(resources.getString(R.string.login_pass_size_invalid));
            return false;
        }
        return true;
    }
    */

    /**
     * Limpa os ícones e as mensagens de erro dos campos desejados
     *
     * @param editTexts lista de campos do tipo EditText
     */
    private void clearErrorFields(EditText... editTexts) {
        for (EditText editText : editTexts) {
            editText.setError(null);
        }
    }
}

