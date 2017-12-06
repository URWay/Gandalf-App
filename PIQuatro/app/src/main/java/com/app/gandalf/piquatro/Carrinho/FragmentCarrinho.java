package com.app.gandalf.piquatro.Carrinho;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.gandalf.piquatro.Checkout.Checkout;
import com.app.gandalf.piquatro.FragmentLogin;
import com.app.gandalf.piquatro.Functions;
import com.app.gandalf.piquatro.NewIndex;
import com.app.gandalf.piquatro.R;
import com.app.gandalf.piquatro.models.Cart_List;
import com.app.gandalf.piquatro.models.SharedPreferencesCart;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.DecimalFormat;
import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class FragmentCarrinho extends Fragment {

    private ListView listaCart;
    private List<Cart_List> list;
    public static final String PREFS_NAME = "PRODUCT_APP";
    public static final String PRODUCTS = "Product";
    private Functions f = new Functions();
    private View view;
    private MyCustomAdapterCarrinhoA thadapter = null;
    private static final String TAG = "Carrinho";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_carrinho,container,false);

        SharedPreferences prefs = getActivity().getSharedPreferences("PRODUCT_APP", getContext().MODE_PRIVATE);
        String product = prefs.getString("Product", null);
        if(product == null || product.equals("") || product.equals("[]")) {
            view = inflater.inflate(R.layout.fragment_carrinho_empty, container, false);

            Button btnContinuar = (Button) view.findViewById(R.id.btnContinuar);

            btnContinuar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getActivity(), NewIndex.class));
                }
            });

            return view;
        }

        listaCart = (ListView) view.findViewById(R.id.listaCart);

        // Alimenta a lista do carrinho
        SharedPreferencesCart sh = new SharedPreferencesCart();
        list = sh.getItens(getContext());

        TextView txtTotalCarrrinho = (TextView) view.findViewById(R.id.txtTotalCarrrinho);

        // Total do carrinho
        double totalCArrinho = sh.getTotal(getContext());
        txtTotalCarrrinho.setText(new DecimalFormat("R$ #,##0.00").format(totalCArrinho));

        thadapter = new MyCustomAdapterCarrinhoA(list, getContext());

        listaCart.setAdapter(thadapter);

        Button btncheckout = (Button) view.findViewById(R.id.btncheckout);

        btncheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs = getActivity().getSharedPreferences(PREFS_NAME, getContext().MODE_PRIVATE);
                String json = prefs.getString(PRODUCTS, null);

                try {
                    JSONArray array = new JSONArray(json);
                    int id = f.getId(getActivity());

                    if(id > 0){
                        if(array.length() > 0){
                            if (json != null || !json.equals("[]")) {
                                Intent intent = new Intent(getActivity(), Checkout.class);
                                startActivity(intent);
                            }
                        }
                    } else {
                        Fragment fragment = null;
                        Class fragmentClass = null;

                        fragmentClass = FragmentLogin.class;
                        try {
                            fragment = (Fragment) fragmentClass.newInstance();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.corpo, fragment).commit();
                        Toast.makeText(getContext(), "Para finalizar o pedido, é ncessário fazer o login", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        return view;
    }

    public class MyCustomAdapterCarrinhoA extends BaseAdapter implements ListAdapter {
        private List<Cart_List> list;
        private Context context;
        private Dialog MyDialog;

        public MyCustomAdapterCarrinhoA(List<Cart_List> list, Context context) {
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
            ViewHolder holder = new ViewHolder();

            holder.image = (ImageView) view.findViewById(R.id.icon_image_view_row);
            holder.nome = (TextView) view.findViewById(R.id.name_text_view_row);
            holder.preco = (TextView) view.findViewById(R.id.price_text_view_row);
            holder.qtd = (TextView) view.findViewById(R.id.qtd_text_view_row);

            String imagemP = list.get(position).getImage();
            String nomeP = list.get(position).getNome();
            double precoPromocao = list.get(position).getPromocao();
            double precoP = list.get(position).getPromocao();
            double total = 0;
            boolean promocao = true;

            if (precoP <= precoPromocao) {
                promocao = false;
                total = precoP;
            } else {
                total = precoPromocao;
            }

            int qtdP = list.get(position).getQtd();

            final byte[] image64 = Base64.decode(imagemP, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(image64, 0, image64.length);

            // Retirando produtos do carrinho
            holder.btnmenos = (Button) view.findViewById(R.id.btn_minus);
            holder.btnmenos.setTag(holder.qtd);

            holder.btnmenos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ViewHolder holder = new ViewHolder();
                    holder.qtd = (TextView) v.getTag();

                    int qtdNew_minus = Integer.parseInt(holder.qtd.getText().toString());
                    qtdNew_minus--;
                    list.get(position).setQtd(qtdNew_minus);

                    if (qtdNew_minus == 0) {
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

                        // Botão de fechar
                        closeButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ViewHolder holder = new ViewHolder();
                                holder.qtd.setText(String.valueOf(1));
                                MyDialog.cancel();
                            }
                        });

                        // Botão de remover do carrinho
                        btnRemoverItem.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                SharedPreferencesCart sh = new SharedPreferencesCart();
                                sh.removeIten(context, list.get(position));
                                list.remove(position);
                                thadapter.notifyDataSetChanged();

                                // Verificar o refresh
                                MyDialog.cancel();

                                Toast.makeText(context, "Item removido", Toast.LENGTH_SHORT).show();

                                Fragment fragment = null;
                                Class fragmentClass = null;

                                fragmentClass = FragmentCarrinho.class;

                                try {
                                    fragment = (Fragment) fragmentClass.newInstance();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                fragmentManager.beginTransaction().replace(R.id.corpo, fragment).commit();
                            }
                        });

                        MyDialog.show();


                    } else {
                        holder.qtd.setText(String.valueOf(qtdNew_minus));
                        SharedPreferencesCart sh = new SharedPreferencesCart();
                        sh.saveItens(context, null, list);

                        Fragment fragment = null;
                        Class fragmentClass = null;

                        fragmentClass = FragmentCarrinho.class;

                        try {
                            fragment = (Fragment) fragmentClass.newInstance();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.corpo, fragment).commit();

                    }
                }
            });

            // Adicionando produto no carrinho
            holder.btnplus = (Button) view.findViewById(R.id.btn_plus);
            holder.btnplus.setTag(holder.qtd);
            holder.btnplus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ViewHolder holder = new ViewHolder();
                    holder.qtd = (TextView) v.getTag();
                    int qtdNew_plus = Integer.parseInt(holder.qtd.getText().toString());
                    qtdNew_plus++;
                    holder.qtd.setText(String.valueOf(qtdNew_plus));

                    SharedPreferencesCart sh = new SharedPreferencesCart();
                    list.get(position).setQtd(qtdNew_plus);
                    sh.saveItens(context, null, list);

                    Fragment fragment = null;
                    Class fragmentClass = null;

                    fragmentClass = FragmentCarrinho.class;

                    try {
                        fragment = (Fragment) fragmentClass.newInstance();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.corpo, fragment).commit();

                }
            });

            // Setando valores
            holder.image.setImageBitmap(bitmap);
            holder.nome.setText(nomeP);

            holder.preco.setText(new DecimalFormat("R$ #,##0.00").format(total));
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

}
