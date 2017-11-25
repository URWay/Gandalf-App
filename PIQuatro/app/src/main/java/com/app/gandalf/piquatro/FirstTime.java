package com.app.gandalf.piquatro;

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

                   Intent intent = new Intent(FirstTime.this, NewIndex.class);
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
        String s = "O uso do serviço “Gandalf Geek-Store” implica na aceitação da existência e do conteúdo destes Termos e Condições de Uso pelo Cliente. Portanto, o Cliente que não estiver de acordo com o presente instrumento não deve  utilizar os serviços oferecidos pelo “Gandalf Geek-Store”.\n" +
                "1.\tGandalf Geek-Store\n" +
                "1.1 O “Gandalf Geek-Store” corresponde a um ecommerce, voltado aos clientes Gandalf, maiores de 18 (dezoito) anos por meio do qual o Cliente terá acesso, através do seu celular, aos seguintes conteúdos temáticos:\n" +
                "•\tProdutos\n" +
                "•\tSegmento por categorias de filmes, séries, quadrinhos, música e cinema\n" +
                "2.\tAcesso ao serviço “Gandalf Geek-Store”\n" +
                "2.1 O serviço estará disponível única e exclusivamente aos clientes Gandalf. O Cliente deverá dispor de todo o equipamento e software necessários para se conectar ao serviço “Gandalf Geek-Store”, incluindo, dentre outras coisas, o telefone celular ou outro aparelho de acesso, certificando-se, ainda, de que as configurações disponíveis em seu aparelho sejam compatíveis com os serviços adquiridos. \n" +
                "2.2 O Cliente fica ciente que a contratação dos serviços e o uso do aplicativo “Gandalf Geek-Store” está condicionada à utilização de aparelhos celulares que possuam algum dos sistemas operacionais iOS ou Android (aplicativo) e acesso a internet (web/mobile).\n" +
                " \n" +
                "2.3 O acesso à Internet e qualquer tráfego de dados para hipóteses de download do conteúdo do “Gandalf Geek-Store” que possam vir a ser disponibilizados, serão tarifados normalmente no plano contratado pelo Cliente junto a Gandalf.\n" +
                " \n" +
                "3.\tResponsabilidades de uso\n" +
                "3.1 Para contratar o serviço, o Cliente deve ter completado sua maioridade civil e estar plenamente capaz, de acordo com o Código Civil brasileiro  .\n" +
                "3.1.1 No caso do acesso por menores de idade, a responsabilidade será exclusivamente do Cliente contratante, responsável legal do menor, junto a Gandalf, inexistindo qualquer responsabilidade imputável a Gandalf sobre a utilização indevida dos conteúdos disponibilizados, uma vez que o Cliente contratante/responsável legal é o único responsável pela guarda e utilização da estação móvel ou equipamento que permita o acesso ao serviço.\n" +
                "3.2 A Gandalf não se responsabiliza por qualquer inaptidão do Cliente em conectar-se à Internet, bem como pelos equipamentos de hardware e software utilizados (aparelhos celulares, tablets, computador ou qualquer outro equipamento compatível) para conexão e acesso ao site/link correspondente ao presente serviço.\n" +
                "3.3 A Gandalf-geek store declara não existir quaisquer tipos de garantias de satisfação das expectativas não declaradas e próprias de cada Cliente sobre os serviços contratados, nem quaisquer tipos de garantias no que se refere à adequação dos conteúdos disponibilizados no serviço “Gandalf Geek-Store”, a qualquer objetivo em particular bem como à política de privacidade de terceiros.\n" +
                "3.4 A Gandalf não será responsável por eventuais interrupções dos serviços que não lhe sejam atribuíveis e/ou que escapem ao seu controle técnico, tais como disfunções da rede IP ou telefônica, não sendo, nestes casos, igualmente responsável pelos tempos de resposta às demandas do Cliente. Assim, a Gandalf  não garante o acesso ininterrupto ou livre de erros aos conteúdos decorrentes de interrupção por vírus, ataques de hackers, erros de script, corrupção de arquivos, pirataria, quebra de segurança, programas incompatíveis ou outros quaisquer que impeçam a visualização correta do conteúdo disponibilizado, não assumindo qualquer responsabilidade por danos diretos ou indiretos causados em virtude do acesso ou por impossibilidade de acessá-los.\n" +
                "3.5 A Gandalf  poderá interromper a prestação dos serviços com o objetivo de realizar trabalhos de reparação, correções do seu sistema, manutenção e/ou melhoras, quando seja oportuno.\n" +
                "3.6 A Gandalf  não se responsabiliza por danos aos Clientes que acessarem sites com endereços similares aos sites e Portais GANDALF, mas que não sejam administrados pela GANDALF e não estejam sob as regras deste Termo de Uso.\n" +
                "3.7 O Cliente expressamente concorda e está ciente de que a GANDALF não terá qualquer responsabilidade, seja contratual ou extracontratual, por quaisquer danos patrimoniais ou morais, incluindo, sem limitação, danos por lucros cessantes, perda de fundo de comércio ou de informações ou outras perdas intangíveis resultantes do:\n" +
                "3.7.1 uso ou incapacidade de usar o serviço;\n" +
                "3.7.2 compartilhamento de dados através de redes sociais atreladas ao serviço “Gandalf Geek-Store”;\n" +
                "3.7.3 acesso não autorizado às transmissões ou informações do usuário, bem como da alteração destes;\n" +
                "3.7.4 orientações ou condutas de terceiros sobre o serviço;\n" +
                "3.7.5 por motivos de força maior ou caso fortuito e atos praticados pelo próprio Cliente.\n" +
                "3.8 A GANDALF não irá se responsabilizar em qualquer hipótese por devolução de valores eventualmente cobrados que resultem:\n" +
                "3.8.1 de incompatibilidade entre elementos de hardware e software empregados pelo Cliente com os serviços prestados pelo “Gandalf Geek-Store”;\n" +
                "3.8.2 do equipamento do cliente não atender aos requisitos mínimos computacionais para exibição dos conteúdos e execução de eventuais aplicativos disponibilizados;\n" +
                "3.8.3 de erro atribuível somente ao Cliente quanto às suas expectativas dos conteúdos e aplicativos;\n" +
                "3.8.4 da não observação, por parte do usuário, dos procedimentos estabelecidos no presente termo e nas demais instruções fornecidas no portal do serviço;\n" +
                "3.8.5 de atos de má-fé praticados pelo cliente;\n" +
                "3.8.6 pela desistência do serviço quando este já se houver concluído.\n" +
                "3.9 A GANDALF não oferece qualquer garantia em relação à propaganda de qualquer produto, serviço e/ou aplicativo que venha a ser disponibilizado no “Gandalf Geek-Store”.\n" +
                "3.10 A GANDALF não se responsabiliza por eventuais mudanças ou cancelamentos de quaisquer conteúdos e/ou aplicativos disponibilizados online pelo seu Parceiro, pela retirada deste ou por quaisquer efeitos no acesso aos conteúdos, aquisição de aplicativos e/ou prestação de serviços disponibilizados on-line causados por tais mudanças, cancelamentos ou retiradas, com os quais desde já concorda o Cliente.\n" +
                " \n" +
                "4.\tTarifas e formas de cobrança\n" +
                "4.1 O Cliente que assinar o “Gandalf Geek-Store” terá acesso ilimitado ao conteúdo do serviço pelo valor semanal de R$3,99 ou mensal de R$9,90, com cobrança recorrente.\n" +
                "4.1.2 Sobre o valor acima estão incluídos os tributos incidentes.\n" +
                "4.2 O valor será debitado automaticamente dos saldos de créditos dos Clientes com celulares pré-pagos, ou cobrado na conta telefônica quando forem Clientes com celulares pós-pagos.\n" +
                "4.3. Cliente poderá cancelar o serviço a qualquer momento (vide item 6).\n" +
                " \n" +
                "5.\tCancelamento do serviço “Gandalf Geek-Store”\n" +
                "6.1 O Cliente poderá, a qualquer momento, cancelar o serviço “Gandalf Geek-Store”, enviando uma mensagem SMS com o texto “SAIR” para o número 4288, partindo do aparelho móvel que utiliza o número telefônico do Cliente cadastrado no serviço. O Cliente também poderá cancelar o serviço através da Central de Relacionamento com o Cliente da GANDALF (**144 para ligações a partir de celulares ou 1056 para ligações a partir de telefones fixos).\n" +
                "6.1.1 Ao cancelar o serviço, o Cliente perderá o acesso ao serviço “Gandalf Geek-Store”.\n" +
                "6.2 O cancelamento do serviço não gera nenhuma multa para o Cliente, devendo apenas este efetuar a quitação dos valores devidos pela disponibilização do serviço, ainda que cobrados em fatura ou mediante débito de créditos posteriormente ao cancelamento.\n" +
                "6.3 Se não houver o cancelamento do serviço pelo Cliente, por meio de uma das formas acima especificadas, o serviço será renovado de forma recorrente, com a disponibilização de conteúdo semanal ou mensal.\n" +
                "6.4 A GANDALF poderá cancelar o serviço automaticamente e sem prévia comunicação, a seu exclusivo critério, na hipótese de ser identificado eventual mau uso do serviço por parte do Cliente, sem que seja devida qualquer indenização, pagamento ou restituição a que título for.\n" +
                " \n" +
                "7.\tPropriedade Intelectual\n" +
                "7.1 Todos os conteúdos disponibilizados no serviço “Gandalf Geek-Store” são protegidos pelas leis de propriedade intelectual aplicáveis e por outras leis, incluindo, sem limitação, Código Civil e Lei de Direitos Autorais. Estes direitos pertencem ao Parceiro da GANDALF, fornecedor direto do serviço, sendo que os direitos de comercialização dos conteúdos foram concedidos à GANDALF por seu titular.\n" +
                "7.2 Ao acessar os serviços e aplicativos da GANDALF geel-store e seus parceiros, o Cliente declara que irá respeitar todos os direitos de propriedade intelectual e industrial, os decorrentes da proteção de marcas registradas da mesma, bem como de todos os direitos autorais referentes a terceiros que porventura estejam, ou estiveram, de alguma forma disponível no portal. Ao Cliente não é conferido qualquer direito de uso dos nomes, títulos, palavras, frases, marcas, patentes, obras literárias, artísticas, dentre outras, que estejam contidos ou disponíveis no portal da GANDALF e/ou no serviço “Gandalf Geek-Store”.\n" +
                "7.3 O cliente não utilizará os materiais ou informações exclusivas, disponibilizados no serviço “Gandalf Geek-Store”, sob nenhuma forma, salvo para a utilização do serviço em conformidade com as condições do presente Termo. É proibida a reprodução de qualquer parte dos conteúdos “Gandalf Geek-Store”, sob qualquer forma ou meio, salvo conforme expressamente permitido sob o presente instrumento, incluindo, mas, não se limitando à apropriação ou sobrecarga da capacidade de rede do Serviço. O Cliente concorda em não modificar, alugar, arrendar, emprestar, vender, distribuir ou criar obras derivadas ou baseadas no serviço prestado pela GANDALF, sob qualquer forma, bem como em não explorar o Serviço sob qualquer forma não autorizada.\n" +
                "7.4 A utilização dos serviços “Gandalf Geek-Store”, salvo para fins de utilização na forma permitida pelo Termo presente, é expressamente proibida e viola os direitos de propriedade intelectual de terceiros, podendo o usuário sujeitar-se a indenizações pecuniárias por violação de direito autoral, além das demais penas cabíveis.\n" +
                "7.5 O Cliente assume toda e qualquer responsabilidade, de caráter civil e/ou criminal, pela utilização indevida das informações, textos, gráficos, marcas, obras, enfim, todo e qualquer direito de propriedade intelectual ou industrial deste serviço.\n" +
                "8.\tDisposições Gerais\n" +
                "8.1 O presente Termo poderá ser alterado a qualquer momento a critério exclusivo da GANDALF, especialmente, mas não se limitando à necessidade de adequação do serviço à legislação aplicável e os valores do serviço poderão ser alterados ou revistos/reajustados, a qualquer tempo, atendidos os procedimentos em vigor para alteração de tarifas para o serviço.\n" +
                "8.2 A omissão ou tolerância da GANDALF em exigir o estrito cumprimento do presente termo, não implicará em novação ou renúncia a direitos, sendo considerada mera liberalidade, não afetando os seus direitos, que poderão ser exercidos a qualquer tempo.\n" +
                "8.3 Na hipótese em que qualquer estipulação ou disposição do presente Termo venham a ser declaradas nulas ou não aplicáveis, tal nulidade ou inexequibilidade não afetará o restante do Termo que permanecerá em pleno vigor e eficácia.\n" +
                "8.4 Quaisquer questões não tratadas no presente Termo serão tratadas pela GANDALF.\n" +
                " \n" +
                "9.\tPolítica de Privacidade\n" +
                "9.1 A GANDALF respeita a privacidade de seus clientes e garante não se utilizar de nenhum dado pessoal dos mesmos para meios diferentes aos do uso dos serviços oferecidos.\n" +
                "9.2 A Política de Privacidade da GANDALF está de acordo com a legislação aplicável aos locais onde o Portal é oferecido e não se aplica a nenhuma outra informação fornecida, recolhida ou obtida pela GANDALF ou por nenhum outro meio, exceto que expressamente assim seja declarado.\n" +
                "9.3 Se o Cliente interagir com aplicativos e redes sociais terceiras, é possível que a GANDALF tenha acesso a certas informações de sua conta, como, por exemplo, sua identificação no site, foto de perfil, conexões e seguidores, além de conteúdos postados ou visualizados através da rede social.\n" +
                "\n";

        txtermo.setMovementMethod(new ScrollingMovementMethod());
        txtermo.setText(s);

    }

    }
