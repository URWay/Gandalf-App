package com.example.lincolnesfrederico.imagedownloader;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private Button button;
    private ImageView image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Binding dos componentes visuais

        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        button = (Button)findViewById(R.id.button);
        image = (ImageView)findViewById(R.id.image);

        //Configuração inicial da barra de progresso
        progressBar.setMax(100);
        progressBar.setProgress(0);

        //Tratamento de evento de clique  no botão
        button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){

                //Cria uma nova instância da thread
                ImageDownloadNetworkCall downloadTask = new
                        ImageDownloadNetworkCall();

                //try/catch é uma exigêmcia do comando de criação da URL
                try{
                    //Reinicializa a barra de progresso (caso já tenha completado)
                    progressBar.setProgress(0);
                    //Execute a tarefa (thread)
                    downloadTask.execute(new URL("http://www.sp.senac.br/moldura/images/senac_logo.png"));
                }catch (MalformedURLException e){
                    e.printStackTrace();
                }
            }
        });
    }

    //Chamado pela AsyncTask
    private void setProgressPercent(int val){
        //Configura o percentual na barra
        progressBar.setProgress(val);
    }
    //Método facilitador para exibição de uma mensagem em diálogo
    private void showDialog(String message, String title){

        //Cria uma fábrica de diálogos utilizando a atividade corrente
        AlertDialog.Builder builder = new
                AlertDialog.Builder(MainActivity.this);

        //Configura a mensagem do diálogo como o parâmetro fornecido
        builder.setMessage(message);
        //configura o título da mensagem
        builder.setTitle(title);
        //Impede o fechamento do diálogo sem o clique em um de seus botões
        builder.setCancelable(false);
        //Configura um botão de ok no diálogo, junto ao seu listener de clique
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener(){
           public void onClick(DialogInterface dialog, int id){

           }
        });

        //solicita a criação de uma instância de diálogo à fábrica
        AlertDialog dialog =  builder.create();
        //Exibe o diálogo
        dialog.show();

    }

    //Task para execução de tarefa em segundo plano
    private class ImageDownloadNetworkCall extends AsyncTask<URL, Integer, Long>{
        //Cria um atributo que representa a imagem baixada. Será utilizado
        //para configuração no componente imageViewer ao dim do download
        Bitmap bmp = null;

        //Método principal da AsyncTask, executa tarefas em background
        protected Long doInBackground(URL... urls) {
            //Código executado em backgrpund aqui

            //Inicializa o total de bytes baixados com 0
            long totalSize = 0;

            //declara um InputStream para receber os bits da imagem
            InputStream in = null;

            //try/catch é uma exigência do método openStream da URL
            try{
                //Obtém um elemento de conexão com a URL da imagem
                //(sempre o primeiro parãmetro de URL, temos apenas 1 imagem)
                URLConnection connection = urls[0].openConnection();

                //Especifica um timeout caso não consiga baixar o conteúdo
                connection.setConnectTimeout(4000);

                //EFETUA O DOWNLOAD DA IMAGEM
                in = connection.getInputStream();

                //Transforma os bytes em imagem
                bmp = BitmapFactory.decodeStream(in);

                //Obtém o tamanho (em bytes) da imagem
                totalSize += bmp.getByteCount();

                //Atualiza o progresso para 100%
                publishProgress(100);

            }catch (Exception e){
                //Exibe mensagem de erro no console caso algum erro ocorra
                e.printStackTrace();
            }

            return totalSize;
        }

        //Método de callback da task. Executado para atualização de progresso
        protected void onProgressUpdate(Integer... progress){
            //Código de atualização do progresso aqui
            //Chama o método da atividade para atualizar o progresso
            setProgressPercent(progress[0]);

        }

        //Método de callback para quando a tarefa é concluída ou encerrada
        protected void onPostExecute(Long result){
            //código para quando a tarefa acabar aqui

            //Verifica se a imagem foi baixada
            if (bmp !=null){

                //Configura a imagem para visualização no componente image viewer
                image.setImageBitmap(bmp);

                //Mostra um dialogo como o tamanho total do download
                showDialog(result +" bytes doram baixados", "tarefa concluída");
            }else{
                //Mostra um diálogo de erro caso a imagem não tenha sido baixada
                showDialog("Não foi possível baixar a imagem","Erro");
            }
        }
    }

}
