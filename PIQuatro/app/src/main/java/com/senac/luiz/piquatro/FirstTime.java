package com.senac.luiz.piquatro;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class FirstTime extends AppCompatActivity {
private TextView txtermo;
private CheckBox checktermo;
private Button btnok;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_time);


        checktermo = (CheckBox) findViewById(R.id.checktermo);
        btnok = (Button) findViewById(R.id.btnok);
        txtermo = (TextView) findViewById(R.id.txtermo);
        loadText();


        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checktermo.isChecked()){

                    Intent intent = new Intent(FirstTime.this, Home.class);
                    startActivity(intent);
                    finish();

                }else{
                    AlertDialog.Builder alerta = new AlertDialog.Builder(FirstTime.this);
                    alerta.setTitle("Erro!");
                    alerta.setMessage("Leia o termo antes de prosseguir!");
                    alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });
                    AlertDialog dialog = alerta.create();
                    dialog.show();
                }
            }
        });

    }
    public void loadText(){
        String s = "Lorem ipsum dolor sit amet, est at inani cetero intellegebat. Tempor efficiantur eos an. Ea omnium inciderint his, duis explicari ex pri. Petentium definitiones ei eos. Tacimates accusata facilisis ne eum.\n" +
                "\n" +
                "An vim esse persius, nec delenit dignissim et, tota paulo offendit sit ei. Cum erat civibus molestiae ea, per nibh fugit voluptaria eu. His possim officiis delicatissimi at, per ea animal docendi appellantur. Consul doctus imperdiet et vim.\n" +
                "\n" +
                "Ne officiis facilisis elaboraret vix, assentior vulputate efficiendi eu vix. Pri viderer forensibus an, ne eum nihil scaevola. At dolorum ocurreret adipiscing sea, mei in brute epicuri intellegat, ei vidit tacimates intellegam ius. Dolore fabellas vim ne, stet putant vis et, ut duo explicari molestiae intellegat. Eu eam porro labitur accusata, an vis clita noluisse, doming dolorum constituam mea ex. No choro habemus volumus sit, id qui wisi vitae quodsi.\n" +
                "\n" +
                "Ad duo platonem reprimique, quo ut falli recteque, ea laoreet nusquam vel. Ipsum timeam vel ut. Eu quo melius aliquip aliquid. At vel ipsum appareat intellegat, eam ne liber oporteat, his ne viris malorum maiestatis. Nam erat utinam deseruisse ei, debet officiis ei has. Ei consul causae pri, brute solet nec ne.\n" +
                "\n" +
                "Ut impedit volumus quo. Semper iracundia per in, eu rebum mollis cum, debet tritani eleifend ex eam. Ludus graeco qui eu. Audire sadipscing an usu, corpora persequeris id vim. At his congue primis dissentiet, usu vidisse conceptam mnesarchum ad. Ut qui ponderum suavitate.";


        txtermo.setMovementMethod(new ScrollingMovementMethod());
        txtermo.setText(s);

    }

    }
