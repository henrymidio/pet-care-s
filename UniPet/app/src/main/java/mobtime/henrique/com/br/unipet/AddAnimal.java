package mobtime.henrique.com.br.unipet;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.pkmmte.view.CircularImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import io.realm.Realm;
import mobtime.henrique.com.br.unipet.pojo.Animal;

public class AddAnimal extends AppCompatActivity {
    public final int IMAGE_GALLERY_REQUEST = 20;
    private CircularImageView ivAnimalFoto;
    private Bitmap bitmap;
    private Button btnCadastrarPet;
    private EditText etAnimalNome;
    private String pathFoto;
    private String absolutePath;
    private String pictureDirectoryPath;
    private byte[] byteArray;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_animal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Adicionar Pet");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ivAnimalFoto = (CircularImageView) findViewById(R.id.ivAnimalFoto);
        etAnimalNome = (EditText) findViewById(R.id.etAnimalNome);

        btnCadastrarPet = (Button) findViewById(R.id.btnCadastrarAnimal);
    }

    public void cadastrarAnimal(View view){

        if(byteArray == null){
            Toast.makeText(this, "Selecione uma imagem", Toast.LENGTH_SHORT).show();
        } else {

            SharedPreferences sp = getSharedPreferences("LOGIN", Context.MODE_PRIVATE);
            int dono = sp.getInt("id", 0);
            String nome = etAnimalNome.getText().toString();

            Animal animal = new Animal(nome, dono, byteArray);

            //Grava o objeto no Realm
            Realm realm = Realm.getInstance(this);
            realm.beginTransaction();
            realm.copyToRealm(animal);
            realm.commitTransaction();
            realm.close();

            Toast.makeText(this, "Pet cadastrado", Toast.LENGTH_SHORT).show();
            Intent it = new Intent(AddAnimal.this, MainActivity.class);
            startActivity(it);

            finish();
        }
    }

    public void converteBitmap(Bitmap b){
        Bitmap bmp = b;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byteArray = stream.toByteArray();
        
    }

    public void onImageGalleryClicked(View view) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);

        File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        pictureDirectoryPath = pictureDirectory.getPath();

        Uri data = Uri.parse(pictureDirectoryPath);

        photoPickerIntent.setDataAndType(data, "image/*");

        startActivityForResult(photoPickerIntent, IMAGE_GALLERY_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            if(requestCode == IMAGE_GALLERY_REQUEST){
                Uri imageUri = data.getData();

                InputStream inputStream;
                try {
                    inputStream = getContentResolver().openInputStream(imageUri);
                    bitmap = BitmapFactory.decodeStream(inputStream);
                    converteBitmap(bitmap);
                    ivAnimalFoto.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
