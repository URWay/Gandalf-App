package com.app.gandalf.piquatro;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.gandalf.piquatro.models.Cart_List;
import com.app.gandalf.piquatro.models.SharedPreferencesCart;

import java.util.List;

public class CartAdapter extends ArrayAdapter<Cart_List> {

    private Context context;
    List<Cart_List> list;
    SharedPreferencesCart sharedPreference;

    public CartAdapter(Context context, List<Cart_List> list) {
        super(context, R.layout.produto_list_row, list);
        this.context = context;
        this.list = list;
        sharedPreference = new SharedPreferencesCart();
    }

    private class ViewHolder {
        TextView productNameTxt;
        TextView productPriceTxt;
        ImageView Img;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Cart_List getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.produto_list_row, null);

            // Build
            holder = new ViewHolder();
            holder.productNameTxt = (TextView) convertView.findViewById(R.id.name_text_view_row);
            holder.productPriceTxt = (TextView) convertView.findViewById(R.id.price_text_view_row);
            holder.Img = (ImageView) convertView.findViewById(R.id.icon_image_view_row);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Cart_List list = (Cart_List) getItem(position);
        holder.productNameTxt.setText(list.getNome());
        holder.productPriceTxt.setText(list.getPromocao() + "");

        String imagemP = list.getImage();
        final byte[] image64 = Base64.decode(imagemP, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(image64, 0, image64.length);
        holder.Img.setImageBitmap(bitmap);

        return convertView;
    }

    @Override
    public void add(Cart_List product) {
        super.add(product);
        list.add(product);
    }

}
