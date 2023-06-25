package com.example.projeto_imoveis.rv;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.projeto_imoveis.ManageImoveis;
import com.example.projeto_imoveis.R;
import com.example.projeto_imoveis.bd.DatabaseHelper;
import com.example.projeto_imoveis.classes.Caracteristica;
import com.example.projeto_imoveis.classes.Imovel;

import java.util.List;

public class ImovelAdapter extends RecyclerView.Adapter<ImovelAdapter.ImovelViewHolder> {

    List<Imovel> imoveis;

    ImovelAdapter(List<Imovel> lstIm){
        this.imoveis = lstIm;
    }

    /*
    * onCreateViewHolder para inflar o layout do item
    * */

    @Override
    public ImovelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemimovel, parent, false);
        ImovelViewHolder avh = new ImovelViewHolder(v, parent.getContext());
        return avh;
    }

    @Override
    public void onBindViewHolder(ImovelViewHolder holder, int position) {
        holder.idImovel = imoveis.get(position).getId();
        holder.descricaoImovel.setText(imoveis.get(position).descricao);
        holder.tipologiaImovel.setText(imoveis.get(position).tipologia);
        holder.localizacaoImovel.setText(imoveis.get(position).localizacao);
        holder.caracteristica = imoveis.get(position).caracteristicas;
        //holder.imagemCliente.setImageResource(imoveis.get(position).url);
    }

    @Override
    public int getItemCount() {
        return imoveis.size();
    }

    public class ImovelViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        DatabaseHelper db;
        Button deleteBtn, editBtn;
        int idImovel;
        //ImageView urlImovel;

        Caracteristica caracteristica;
        TextView descricaoImovel, tipologiaImovel, localizacaoImovel;
        Context context;

        public ImovelViewHolder(View itemView, Context ctx) {
            super(itemView);
            itemView.setOnClickListener(this);

            deleteBtn = (Button) itemView.findViewById(R.id.deleteImovel);
            editBtn = (Button) itemView.findViewById(R.id.editImovel);
            deleteBtn.setOnClickListener(this);
            editBtn.setOnClickListener(this);


            //imagemImovel = (ImageView) itemView.findViewById(R.id.imagemImovel);
            descricaoImovel = (TextView) itemView.findViewById(R.id.descricaoImovel);
            tipologiaImovel = (TextView) itemView.findViewById(R.id.tipologiaImovel);
            localizacaoImovel = (TextView) itemView.findViewById(R.id.localizacaoImovel);

            this.context = ctx;
        }

        @Override
        public void onClick(View view) {
            if(view == itemView) {
                Intent intent = new Intent(this.context, ActImovel.class);
                this.makeIntent(intent);
                //intent.putExtra("imagem", imagemImovel.getText().toString());
                this.context.startActivity(intent);
            }else if(view == deleteBtn){
                db = new DatabaseHelper(this.context);
                db.removeImovel(this.idImovel);
                db.fecharDB();
                removeAt(getLayoutPosition());
            }else if(view == editBtn){
                Intent intent = new Intent(this.context, ManageImoveis.class);
                this.makeIntent(intent);
                this.context.startActivity(intent);
            }
        }

        private Intent makeIntent(Intent intent){
            intent.putExtra("id", this.idImovel);
            intent.putExtra("descricao", descricaoImovel.getText().toString());
            intent.putExtra("tipologia", tipologiaImovel.getText().toString());
            intent.putExtra("localizacao", localizacaoImovel.getText().toString());
            intent.putExtra("hasSauna", this.caracteristica.hasSauna());
            intent.putExtra("hasAreacomum", this.caracteristica.hasAreacomum());
            return intent;
        }

        public void removeAt(int position) {
            imoveis.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, imoveis.size());
        }
    }
}
