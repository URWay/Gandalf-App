package com.app.gandalf.piquatro;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MyCustomAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<String> list = new ArrayList<String>();
    private List<Integer> positions = new ArrayList<>();
    private Context context;
    private int id;
    private int visible;
    private Activity activity;

    public MyCustomAdapter(ArrayList<String> list, List<Integer> positions, Context context, Activity activity) {
        this.list = list;
        this.positions = positions;
        this.context = context;
        this.activity = activity;
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
            view = inflater.inflate(R.layout.list_item, null);
        }

        TextView listItemText = (TextView) view.findViewById(R.id.list_item_string);
        ImageView deleteBtn = (ImageView) view.findViewById(R.id.delete_btn);
        listItemText.setText(list.get(position));

        if(positions.get(position) == 0){
            //view.findViewById(R.id.delete_btn).setVisibility(View.INVISIBLE);
        }

        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whick) {
                        switch (whick) {
                            case DialogInterface.BUTTON_POSITIVE:
                                int param = positions.get(position);
                                String url = "http://gandalf-ws.azurewebsites.net/pi4/wb/endereco/deletar/" + param;

                                Functions fun = new Functions();
                                int retorno = fun.sendDelete(url, "DELETE");
                                if(retorno == 200){
                                    list.remove(positions.get(position));
                                    Toast toast = Toast.makeText(activity.getApplicationContext(), "Endereço deletado com sucesso!", Toast.LENGTH_SHORT);
                                    toast.show();
                                }else {
                                    Toast toast = Toast.makeText(activity.getApplicationContext(), "Erro ao deletar o endereço.", Toast.LENGTH_SHORT);
                                    toast.show();
                                }
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setMessage("Corfirma a exclusão do endereço ?").setTitle("Deletar endereço").setPositiveButton("Sim", dialogClickListener).setNegativeButton("Não", dialogClickListener).show();
            }
        });

        return view;
    }

}