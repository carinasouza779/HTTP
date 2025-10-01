import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.*;
import java.net.InetSocketAddress;

public class Servidor {


    public static void main(String[] args) throws Exception {
        //Escuta em todas as interfaces de rede
        HttpServer servidor = HttpServer.create(new InetSocketAddress("0.0.0.0", 8080), 0);

        //rotas fixas
        servidor.createContext("/jogos",troca->enviarResposta(troca, "<h1> Jogos </h1> <p> jogo para jogar no pc</p> <h3> <ul>Undertale</ul> <ul>Final Fantasy VII Remake</ul> <ul>Red Dead Redemption 2</ul>  <ul>God of War</ul>  <ul>Hollow Knight</ul> </h3> "));
        servidor.createContext("/series",troca->enviarResposta(troca, "<h1> Series </h1> <p>top series sul coreana para assitir na netflix<p>  <h3> <ul>Pretendente Surpresa</ul> <ul>Love to Hate You</ul> <ul>Se a Vida Te Der Tangerinas</ul> </h3> "));
        servidor.createContext("/musicas",troca->enviarResposta(troca, "<h1> Musicas </h1> <p> Musicas R&B para ouvir no tempo livre  </p> <h3> <ul>Tim Maia:Gostava Tanto de Você</ul> <ul>Chico Buarque:O Que Será (À Flor da Pele)</ul> <ul>Tom Jobim:Eu Sei Que Vou Te Amar</ul>  <ul>Djavan: Flor de Lis</ul> </h3> "));
        servidor.createContext("/index",troca-> {
                    String html = """
                            <!DOCTYPE html>
                            <html lang="en">
                            <head>
                                <meta charset="UTF-8">
                                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                                <title>Document</title>
                            
                                <style>
                                  h1{
                                    color: rgb(81, 17, 133);
                                 background-color: rgb(249, 185, 255);
                                }
                                body{
                                    background-color: rgb(226, 170, 243);
                                    text-align: center;
                                }
                                .botao{
                                   margin: 15px;
                                   padding: 20px;
                                   background-color: rgb(202, 153, 250);
                                   text-decoration: none;
                                   border-radius: 30px;
                                   box-shadow: 0 4px 10px rgb(194, 138, 240);
                                }
                                .botao:hover{
                                    background-color: rgb(119, 12, 182);
                                    box-shadow: 0 4px 10px rgb(104, 12, 146);
                                }
                                </style>
                            
                            </head>
                            <body>
                                <h1>Bem-vindo ao portal da Carina</h1>
                            
                            <a href="/jogos" class="botao">Jogos</a>
                            <a href="/musicas"class="botao">Música</a>
                            <a href="/series"class="botao">Séries</a>
                            
                            </body>
                            </html>
                            """;
                    enviarResposta(troca, html);
                });


        //rotas dinâmicas                 /boasvindas?nome=Carina
        servidor.createContext("/boasvindas",troca->{
            String consulta = troca.getRequestURI().getQuery();
            String nome = consulta.replace("nome=","");
            String resposta = "Seja bem-vindo" + nome;
           enviarResposta(troca,resposta);
        });

        //Iniciar o servidor
        servidor.start();

        //Manter o servidor ativo
        while (true) {
            Thread.sleep(1000);
        }

    }

    public static void enviarResposta(HttpExchange troca, String resposta) throws IOException {
          byte[] bytes;
          bytes = resposta.getBytes(StandardCharsets.UTF_8);

          //Confirmação de que deu certo e o tamanho da mensagem
          troca.getResponseHeaders().set("Content-Type", "text/html;charset= UTF-8");
          troca.sendResponseHeaders(200,bytes.length);

          //Enviar para o cliente do resultado
         try(OutputStream os = troca.getResponseBody()){
             //Escreve a mensagem para cliente
             os.write(bytes);




         }
    }
}

