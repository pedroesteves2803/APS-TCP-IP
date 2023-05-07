/**
 * Esse código inicia a definição de um pacote Java chamado conexao, que contém duas importações de pacotes Java: java.io e java.net. 
 * Ele também define uma classe Java chamada Cliente.
 * 
 * A classe Cliente é responsável por gerenciar a conexão do cliente com o servidor de chat. 
 * Para isso, a classe cria uma instância de um socket de rede TCP, que será usada para enviar e receber dados do servidor. Além disso, a classe 
 * também cria duas instâncias de objetos BufferedReader e PrintWriter, que serão usados para ler e escrever dados para o servidor.
 * 
 * A classe também define uma série de métodos que serão usados para configurar e gerenciar a conexão com o servidor. 
 * Entre esses métodos, estão conectar(), que é usado para conectar o cliente ao servidor, desconectar(), que é usado para desconectar 
 * o cliente do servidor, e enviarMensagem(), que é usado para enviar mensagens para o servidor.
 * 
 * Além disso, a classe também define um método run(), que será executado em uma thread separada para gerenciar a conexão do cliente com o servidor em tempo real. 
 * Esse método é responsável por receber mensagens do servidor e imprimi-las na tela do cliente.
 */
package conexao;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;


/**
 * Este código define uma classe Java chamada "Servidor", que estende a classe "Thread" e representa um servidor que ouve e manipula conexões de clientes.
 * 
 * A classe tem os seguintes atributos:
 * 
 * "clients": um ArrayList de objetos BufferedWriter, representando os fluxos de saída para enviar mensagens aos clientes conectados.
 * "users": um ArrayList de objetos String, contendo os nomes de usuário dos clientes conectados.
 * "connection": um objeto Socket representando o socket usado para a conexão com o servidor.
 * "bufferReader": um objeto BufferedReader representando o fluxo de entrada para ler mensagens do cliente.
 * "currentUser": uma String representando o nome de usuário do cliente atual.
 * 
 * A classe "Servidor" substitui o método "run" da classe "Thread", que é o método que é executado quando uma nova thread é iniciada.
 * 
 * O método "run" executa as seguintes etapas:
 * 
 * Cria um objeto BufferedWriter para enviar mensagens ao cliente.
 * Adiciona o objeto BufferedWriter ao ArrayList "clients".
 * Lê o nome de usuário enviado pelo cliente e adiciona-o ao ArrayList "users".
 * Envia uma mensagem para todos os clientes conectados informando que um novo cliente se conectou.
 * Entra em um loop para ler as mensagens enviadas pelo cliente até que o cliente desconecte ou envie uma mensagem "Disconnect".
 * Envia a mensagem recebida para todos os clientes conectados.
 * Remove o usuário atual dos ArrayLists "clients" e "users".
 * Envia uma mensagem para todos os clientes conectados informando que o usuário atual se desconectou.
 * 
 * A classe "Servidor" também tem os seguintes métodos privados:
 * 
 * "broadCast": envia uma mensagem para todos os clientes conectados, usando o fluxo de saída de cada cliente no ArrayList "clients".
 * "removeUser": remove um usuário dos ArrayLists "clients" e "users".
 */
public class Servidor extends Thread {
    private static ArrayList<BufferedWriter> clients = new ArrayList<BufferedWriter>();
    private static ArrayList<String> users = new ArrayList<String>();
    private Socket connection;
    private BufferedReader bufferReader;
    private String currentUser = "";

    public Servidor(Socket connection) {
        this.connection = connection;

        try {
            // Reader
            bufferReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {

            BufferedWriter bufferWriter = new BufferedWriter(new OutputStreamWriter(this.connection.getOutputStream()));

            clients.add(bufferWriter);
            currentUser = this.bufferReader.readLine();
            users.add(currentUser);

            String msg = "Text&" + currentUser + " Conectado";

            while (!("Text&Disconnect " + currentUser).equalsIgnoreCase(msg) && msg != null) {
                broadCast(msg);
                System.out.println(currentUser + " [Server(run)] " + msg);
                msg = this.bufferReader.readLine();
            }

            removeUser(currentUser);

            broadCast("Text&Usuário " + currentUser + " Desconectado");
        } catch (Exception e) {
            e.printStackTrace();
            removeUser(currentUser);
        }
    }

    private void broadCast(String msg) {
        for (BufferedWriter bufferClient : clients) {
            try {
                bufferClient.write(msg + "\r\n");
                System.out.println("[Broadcast] " + msg);
                bufferClient.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void removeUser(String user) {
        int index = users.indexOf(user);
        clients.remove(index);
        users.remove(index);
    }
}
