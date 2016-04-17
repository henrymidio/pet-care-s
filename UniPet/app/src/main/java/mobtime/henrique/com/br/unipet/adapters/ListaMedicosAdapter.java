package mobtime.henrique.com.br.unipet.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.pkmmte.view.CircularImageView;

import java.util.List;

import mobtime.henrique.com.br.unipet.R;
import mobtime.henrique.com.br.unipet.pojo.Medico;

/**
 * Created by Henrique on 16/04/2016.
 */
public class ListaMedicosAdapter extends BaseAdapter {

    private Context ctx;
    private List<Medico> lista;
    private static LayoutInflater inflater=null;

    public ListaMedicosAdapter(Context ctx, List<Medico> lista){
        this.ctx = ctx;
        this.lista = lista;
        inflater = ( LayoutInflater )ctx.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CustomViewHolder holder;
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.layout_listviw_medicos, parent, false);
            holder = new CustomViewHolder();
            convertView.setTag(holder);

            holder.ivFoto = (CircularImageView) convertView.findViewById(R.id.ivMedico);
            holder.tvNome = (TextView) convertView.findViewById(R.id.tvNomeMedico);
            holder.tvRegistro = (TextView) convertView.findViewById(R.id.tvRegistro);
        } else {
            holder = (CustomViewHolder) convertView.getTag();
        }

        Medico medico = lista.get(position);
        holder.ivFoto.setImageResource(medico.getFoto());
        holder.tvNome.setText(medico.getNome());
        holder.tvRegistro.setText(medico.getRegistro());

        return convertView;
    }

    public static class CustomViewHolder{
        TextView tvNome;
        TextView tvRegistro;
        CircularImageView ivFoto;
    }
}
