package mobtime.henrique.com.br.unipet.pojo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import io.realm.RealmObject;

/**
 * Created by Henrique on 18/04/2016.
 */
public class Animal extends RealmObject {

    private String nome;
    private String pathFoto;
    private String vermífugo;
    private String v4;
    private String v10;
    private String giardia;
    private String antiRabica;
    private byte[] byteFoto;
    private int dono;

    public Animal(String nome, int dono, byte[] b) {

        this.nome = nome;
        this.vermífugo = "";
        this.v4 = "";
        this.v10 = "";
        this.giardia = "";
        this.antiRabica = "";
        this.dono = dono;
        this.byteFoto = b;
    }

    public Animal(){

    }

    public byte[] getByteFoto() {
        return byteFoto;
    }

    public void setByteFoto(byte[] byteFoto) {
        this.byteFoto = byteFoto;
    }

    public String getPathFoto() {
        return pathFoto;
    }

    public void setPathFoto(String pathFoto) {
        this.pathFoto = pathFoto;
    }

    public String getVermífugo() {
        return vermífugo;
    }

    public void setVermífugo(String vermífugo) {
        this.vermífugo = vermífugo;
    }

    public String getV4() {
        return v4;
    }

    public void setV4(String v4) {
        this.v4 = v4;
    }

    public String getV10() {
        return v10;
    }

    public void setV10(String v10) {
        this.v10 = v10;
    }

    public String getGiardia() {
        return giardia;
    }

    public void setGiardia(String giardia) {
        this.giardia = giardia;
    }

    public String getAntiRabica() {
        return antiRabica;
    }

    public void setAntiRabica(String antiRabica) {
        this.antiRabica = antiRabica;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getDono() {
        return dono;
    }

    public void setDono(int dono) {
        this.dono = dono;
    }

    public Bitmap getFoto(){
        Bitmap bmp = BitmapFactory.decodeByteArray(getByteFoto(), 0, getByteFoto().length);
        return bmp;
    }

}
