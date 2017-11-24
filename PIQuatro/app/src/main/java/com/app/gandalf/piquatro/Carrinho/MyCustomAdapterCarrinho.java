package com.app.gandalf.piquatro.Carrinho;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.ViewGroup.LayoutParams;

import com.app.gandalf.piquatro.R;
import com.app.gandalf.piquatro.models.Cart_List;
import com.app.gandalf.piquatro.models.SharedPreferencesCart;

import java.text.DecimalFormat;
import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class MyCustomAdapterCarrinho extends BaseAdapter implements ListAdapter {
    private List<Cart_List> list;
    private Context context;
    private ViewHolder holder = new ViewHolder();
    private Dialog MyDialog;

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
    public View getView(final int position, final View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.produto_list_row, null);
        }

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
        holder.btnmenos.setTag(holder.qtd);

        holder.btnmenos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.qtd = (TextView) view.getTag();

                int qtdNew_minus = Integer.parseInt(holder.qtd.getText().toString());
                qtdNew_minus--;
                list.get(position).setQtd(qtdNew_minus);

                if(qtdNew_minus == 0){
                    String image = list.get(position).getImage();

                    MyDialog = new Dialog(context);
                    MyDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    MyDialog.setContentView(R.layout.popup_window_checkout);
                    MyDialog.setTitle("My Custom Dialog");

                    Button closeButton = (Button) MyDialog.findViewById(R.id.btnCancelar);
                    Button btnRemoverItem = (Button) MyDialog.findViewById(R.id.btnRemoverItem);

                    final ImageView imageP = (ImageView) MyDialog.findViewById(R.id.imgCartProduct);
                    final byte[] image64 = Base64.decode(image, Base64.DEFAULT);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(image64, 0, image64.length);
                    imageP.setImageBitmap(bitmap);

                    btnRemoverItem.setEnabled(true);
                    closeButton.setEnabled(true);

                    closeButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            holder.qtd.setText(String.valueOf(1));
                            MyDialog.cancel();
                        }
                    });

                    btnRemoverItem.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SharedPreferencesCart sh = new SharedPreferencesCart();
                            sh.removeIten(context, list.get(position));
                            MyDialog.cancel();
                        }
                    });

                    MyDialog.show();


                } else {
                    holder.qtd.setText(String.valueOf(qtdNew_minus));
                    SharedPreferencesCart sh = new SharedPreferencesCart();
                    sh.saveItens(context, list);
                }
            }
        });

        // Adicionando produto no carrinho
        holder.btnplus = (Button) view.findViewById(R.id.btn_plus);
        holder.btnplus.setTag(holder.qtd);
        holder.btnplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.qtd = (TextView) v.getTag();
                int qtdNew_plus = Integer.parseInt(holder.qtd.getText().toString());
                qtdNew_plus++;
                holder.qtd.setText(String.valueOf(qtdNew_plus));

                SharedPreferencesCart sh = new SharedPreferencesCart();
                list.get(position).setQtd(qtdNew_plus);
                sh.saveItens(context, list);
            }
        });

        // Setando valores
        holder.image.setImageBitmap(bitmap);
        holder.nome.setText(nomeP);
        holder.preco.setText(new DecimalFormat("R$ #,##0.00").format(precoP));
        holder.qtd.setText(String.valueOf(qtdP));

        view.setTag(holder);
        return view;
    }

    public class ViewHolder {
        ImageView image;
        TextView nome;
        TextView preco;
        TextView qtd;
        Button btnmenos;
        Button btnplus;
    }
}