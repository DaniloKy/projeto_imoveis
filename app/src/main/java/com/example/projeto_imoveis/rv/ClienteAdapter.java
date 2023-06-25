package com.example.projeto_imoveis.rv;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.projeto_imoveis.ManageClientes;
import com.example.projeto_imoveis.R;
import com.example.projeto_imoveis.bd.DatabaseHelper;
import com.example.projeto_imoveis.classes.Client;

import java.util.List;

public class ClienteAdapter extends RecyclerView.Adapter<ClienteAdapter.ClientViewHolder> {

    List<Client> clientes;

    ClienteAdapter(List<Client> lstCl){
        this.clientes = lstCl;
    }

    @Override
    public ClientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemcliente, parent, false);
        ClientViewHolder avh = new ClientViewHolder(v, parent.getContext());
        return avh;
    }

    @Override
    public void onBindViewHolder(ClientViewHolder holder, int position) {
        holder.idCliente = clientes.get(position).getId();
        holder.nomeCliente.setText(clientes.get(position).nome);
        holder.idadeCliente.setText((clientes.get(position).idade).toString());
        //holder.imagemCliente.setImageResource(clientes.get(position).url);
    }

    @Override
    public int getItemCount() {
        return clientes.size();
    }

    public class ClientViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        DatabaseHelper db;
        int idCliente;
        Button deleteBtn, editBtn;
        //ImageView urlCliente;
        TextView nomeCliente, idadeCliente;
        Context context;

        public ClientViewHolder(View itemView, Context ctx) {
            super(itemView);
            itemView.setOnClickListener(this);

            deleteBtn = (Button) itemView.findViewById(R.id.deleteCliente);
            editBtn = (Button) itemView.findViewById(R.id.editCliente);
            deleteBtn.setOnClickListener(this);
            editBtn.setOnClickListener(this);

            //urlCliente = (ImageView) itemView.findViewById(R.id.imagemCliente);
            nomeCliente = (TextView) itemView.findViewById(R.id.nomeCliente);
            idadeCliente = (TextView) itemView.findViewById(R.id.idadeCliente);
            this.context = ctx;
        }

        @Override
        public void onClick(View view) {
            if(view == itemView) {
                Intent intent = new Intent(this.context, ActCliente.class);
                this.makeIntent(intent);
                this.context.startActivity(intent);
            }else if(view == deleteBtn){
                db = new DatabaseHelper(this.context);
                db.removeClient(this.idCliente);
                db.fecharDB();
                removeAt(getLayoutPosition());
            }else if(view == editBtn){
                Intent intent = new Intent(this.context, ManageClientes.class);
                this.makeIntent(intent);
                this.context.startActivity(intent);
            }
        }

        private Intent makeIntent(Intent intent){
            intent.putExtra("id", this.idCliente);
            intent.putExtra("nome", nomeCliente.getText().toString());
            intent.putExtra("idade", idadeCliente.getText().toString());
            //intent.putExtra("imagem", imagemImovel.getText().toString());
            return intent;
        }

        public void removeAt(int position) {
            clientes.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, clientes.size());
        }
    }
}
