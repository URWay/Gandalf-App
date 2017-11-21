package com.app.gandalf.piquatro.Carrinho;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.app.gandalf.piquatro.R;
import com.app.gandalf.piquatro.models.Cart;
import com.app.gandalf.piquatro.models.Cart_List;
import com.app.gandalf.piquatro.models.SharedPreferencesCart;

import java.text.DecimalFormat;
import java.util.List;

public class MyCustomAdapterCarrinho extends BaseAdapter implements ListAdapter {
    private List<Cart_List> list;
    private Context context;
    private Cart cart;
    private Cart_List cartList;
    private int id;

    public MyCustomAdapterCarrinho(List<Cart_List> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 0; //list.get(pos).getId();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.produto_list_row, null);
        }

            ViewHolder holder = new ViewHolder();

        //for(int i = 0; i <= list.size(); i++){
            holder.image = (ImageView) view.findViewById(R.id.icon_image_view_row);
            holder.nome = (TextView) view.findViewById(R.id.name_text_view_row);
            holder.preco = (TextView) view.findViewById(R.id.price_text_view_row);
            holder.qtd = (TextView) view.findViewById(R.id.qtd_text_view_row);

            String imagemP = list.get(position).getImage();
            String nomeP = list.get(position).getNome();
            double precoP = list.get(position).getPromocao();
            int qtdP = list.get(position).getQtd();

            final byte[] image64 = Base64.decode(imagemP, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(image64, 0, image64.length);

            // Retirando produtos do carrinho
            holder.btnmenos = (Button) view.findViewById(R.id.btn_minus);
            holder.btnmenos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Ver depois como pegar o valor da quantidade
                    ViewHolder holder = new ViewHolder();
                    holder.qtd = (TextView) view.findViewById(R.id.qtd_text_view_row);
                    int qtdNew_minus = Integer.parseInt(holder.qtd.getText().toString());
                    qtdNew_minus--;
                    holder.qtd.setText(String.valueOf(qtdNew_minus));
                    SharedPreferencesCart sh = new SharedPreferencesCart();
                    list.get(position).setQtd(qtdNew_minus);
                    sh.saveItens(context, list);
                }
            });

            // Setando valores
            holder.image.setImageBitmap(bitmap);
            holder.nome.setText(nomeP);
            holder.preco.setText(new DecimalFormat("R$ #,##0.00").format(precoP));
            holder.qtd.setText(String.valueOf(qtdP));
        //}

        view.setTag(holder);
        return view;
    }

    public class ViewHolder {
        ImageView image;
        TextView nome;
        TextView preco;
        TextView qtd;
        Button btnmenos;
    }

}
