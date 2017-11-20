package com.app.gandalf.piquatro.Carrinho;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.app.gandalf.piquatro.R;
import com.app.gandalf.piquatro.models.Cart;
import com.app.gandalf.piquatro.models.Cart_List;

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

        //for(int i = 0; i <= list.size(); i++){
            ImageView image = (ImageView) view.findViewById(R.id.icon_image_view_row);
            TextView nome = (TextView) view.findViewById(R.id.name_text_view_row);
            TextView preco = (TextView) view.findViewById(R.id.price_text_view_row);

            String imagemP = list.get(position).getImage();
            String nomeP = list.get(position).getNome();
            double precoP = list.get(position).getPromocao();

            final byte[] image64 = Base64.decode(imagemP, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(image64, 0, image64.length);

            // Setando valores
            image.setImageBitmap(bitmap);
            nome.setText(nomeP);
            preco.setText(new DecimalFormat("R$ #,##0.00").format(precoP));
        //}

        return view;
    }

}
