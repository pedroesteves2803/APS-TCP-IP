/**
 * Esse código importa as classes necessárias para a criação de um cliente de chat em Java, incluindo as bibliotecas Swing 
 * para a criação da interface gráfica, e as bibliotecas para a comunicação via rede através do protocolo TCP/IP.
 * 
 * As classes importadas são:
 * java.awt.BorderLayout: para gerenciamento de layout dos componentes gráficos na tela.
 * java.awt.event.*: para lidar com eventos do usuário, como cliques de botão ou teclas pressionadas.
 * javax.swing.JFrame: para criação da janela principal do programa.
 * javax.swing.JPanel: para criação de painéis que contêm outros componentes gráficos.
 * javax.swing.border.EmptyBorder: para adicionar margens vazias ao redor dos componentes gráficos.
 * javax.swing.JTextField: para criação do campo de entrada de texto.
 * javax.swing.JScrollPane: para adicionar barras de rolagem à área de texto.
 * javax.swing.JTextArea: para criação da área de texto que exibe as mensagens.
 * javax.swing.JButton: para criação dos botões.
 * javax.swing.ScrollPaneConstants: para definir as constantes utilizadas pelas barras de rolagem.
 * javax.swing.text.DefaultCaret: para controlar o comportamento do cursor na área de texto.
 * 
 * Essas classes serão usadas para criar a interface gráfica do cliente de chat, permitir que o usuário insira mensagens e 
 * visualize as mensagens recebidas, além de controlar a conexão com o servidor de chat.
 */

package chat;

import java.awt.BorderLayout;
import java.awt.event.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.DefaultCaret;

import java.io.*;

import java.net.ConnectException;
import java.net.Socket;

public class Cliente extends JFrame implements Runnable {

    /**
     * Esse código cria duas variáveis: uma para armazenar um objeto Socket e outra para armazenar um objeto 
     * BufferedWriter. Essas variáveis podem ser utilizadas em um programa para estabelecer uma conexão de 
     * rede e enviar dados através dela.
     */
    private Socket socket;
    private BufferedWriter bufferWriter;

    /**
     * Esse código cria três variáveis: duas Strings e um inteiro, que são utilizados para armazenar 
     * informações relacionadas a um cliente em um programa. A primeira String, chamada "user", 
     * pode ser utilizada para armazenar o nome ou identificador do usuário. A segunda String, 
     * chamada "serverIP", armazena o endereço IP do servidor com o qual o cliente irá se comunicar. 
     * Por fim, o inteiro "serverPort" armazena a porta do servidor que será utilizada para estabelecer a conexão. 
     */
    private String user;
    private String serverIP;
    private int serverPort;

    /**
     * 
     * Esse código cria três variáveis: uma para armazenar um valor longo chamado "serialVersionUID", que é utilizado 
     * para controlar a versão de uma classe serializável em Java; uma variável JTextField chamada "inputText", que 
     * é utilizada para capturar a entrada de texto do usuário; e uma variável JTextArea chamada "output", que é 
     * utilizada para exibir texto de saída. Essas variáveis podem ser utilizadas em um programa para criar uma 
     * interface de usuário que permita a entrada e saída de texto.
     */
    private static final long serialVersionUID = 5391582161763137020L;
    private JTextField inputText;
    private JTextArea output;

    /**
     * Esse código define um método chamado "run()", que é utilizado para iniciar a execução de uma aplicação. 
     * Dentro do método, três ações são realizadas: a primeira é definir o título da aplicação como o nome do usuário; 
     * a segunda é estabelecer uma conexão com algum recurso externo (como um servidor, por exemplo); 
     * e a terceira é escutar a conexão estabelecida em busca de mensagens recebidas. Se alguma exceção ocorrer 
     * durante a execução dessas ações, o método irá imprimir o rastreamento da pilha de exceção para fins de depuração.
     */
    public void run() {
        try {
            setTitle(user);
            establishConnection();
            listenConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        
        

    /**
     * Esse código cria uma janela de interface gráfica do usuário (GUI) para um cliente de chat. 
     * Ele define a aparência e o comportamento da janela, adicionando elementos como uma área de texto para exibição de mensagens, 
     * uma caixa de texto para entrada de texto, e botões para enviar mensagens e desconectar do chat. O código também define ações 
     * para responder a eventos de botões pressionados e eventos de fechamento de janela. Além disso, o código estabelece a conexão 
     * com o servidor de chat e define as informações do cliente, como nome do usuário, endereço IP e porta do servidor. 
     */
    public Cliente(String[] args) {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
                                   @Override
                                   public void windowClosing(WindowEvent e) {
                                       if(socket == null)
                                           dispose();
                                       else if(!socket.isClosed()) {
                                           try {
                                               disconnect();
                                           } catch (Exception ex) {
                                               ex.printStackTrace();
                                           }
                                       }

                                       Iniciar start = new Iniciar();
                                       start.setVisible(true);
                                       dispose();
                                   }
                               }
        );

        setBounds(100, 100, 600, 500);
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));
        setLocationRelativeTo(null);
        setTitle("AplicaçãSo de Conversa (Cliente)");

        output = new JTextArea();
        output.setEditable(false);
        output.setLineWrap(true);

        JScrollPane messages = new JScrollPane(output);
        messages.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        DefaultCaret caretOutput = (DefaultCaret) output.getCaret();
        caretOutput.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        contentPane.add(messages, BorderLayout.CENTER);

        JPanel interactive = new JPanel();
        contentPane.add(interactive, BorderLayout.SOUTH);
        interactive.setLayout(new BorderLayout(0, 0));

        inputText = new JTextField();
        interactive.add(inputText, BorderLayout.CENTER);
        inputText.setColumns(10);

        JPanel buttons = new JPanel();
        interactive.add(buttons, BorderLayout.EAST);
        buttons.setLayout(new BorderLayout(0, 0));

        JButton btnSend = new JButton("Enviar");
        btnSend.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String text = inputText.getText();
                if(!text.equals("")) {
                    sendMessage(concatMsg(text));
                    inputText.setText("");
                }
            }
        });
        buttons.add(btnSend, BorderLayout.NORTH);

        inputText.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void keyReleased(KeyEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String text = inputText.getText();
                    if(!text.equals("")) {
                        sendMessage(concatMsg(text));
                        inputText.setText("");
                    }
                }
            }
        });

        JButton btnExit = new JButton("Desconectar");
        btnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    disconnect();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        buttons.add(btnExit, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
        setClientInfo(args[0], args[1], args[2]);
    }

    /**
     * Este código define as informações do cliente, incluindo o nome do usuário, endereço IP do servidor e a porta em que o servidor está ouvindo. 
     * Ele recebe essas informações como argumentos da função e atribui os valores às variáveis de instância da classe, que são 
     * usadas em outras partes do código para estabelecer a conexão com o servidor e enviar/receber mensagens. Além disso, a porta do servidor 
     * é convertida de uma string para um número inteiro usando o método Integer.parseInt(), que analisa a string e retorna o valor numérico correspondente.
     * Essa função pode ser chamada em algum momento antes de iniciar a execução do cliente de chat para definir as informações de conexão.
     */
    private void setClientInfo(String user, String serverIP, String serverPort) {
        this.user = user;
        this.serverIP = serverIP;
        this.serverPort = Integer.parseInt(serverPort);
    }

    /**
     * 
     * Este código é responsável por enviar uma mensagem para o servidor através do socket. 
     * Ele recebe a mensagem a ser enviada como um parâmetro msg, e em seguida, tenta escrever a mensagem no bufferWriter que 
     * é um objeto da classe BufferedWriter. Em seguida, ele invoca o método flush(), que força o conteúdo do buffer a ser escrito no socket. 
     * Se ocorrer algum erro durante o processo de escrita, a função writeOutput() é chamada com uma mensagem informando que o usuário foi desconectado. 
     * Essa função é chamada sempre que o usuário envia uma mensagem no cliente de chat. 
    */
    private void sendMessage(String msg) {
        try {
            bufferWriter.write(msg);
            bufferWriter.flush();
        } catch (Exception e) {
            writeOutput("Desconectado");
        }
    }

    /**
     * Este código é responsável por concatenar a mensagem que o usuário digitou com o nome do usuário, formatando-a em um formato específico para o envio ao servidor.
     * A função concatMsg recebe uma mensagem msg como entrada e retorna a mensagem formatada. A mensagem formatada é composta pelo seguinte formato: "Text&" + "[" + user + "]" + " ~> " + msg + "\r\n".
     * "Text&" é um marcador que informa ao servidor que a mensagem enviada é uma mensagem de texto.
     * "[" + user + "]" é o nome do usuário, que é colocado entre colchetes e seguido de um sinal de maior que.
     * "msg" é a mensagem que o usuário digitou.
     * "\r\n" é um caractere especial que indica uma nova linha e é adicionado ao final da mensagem para que o servidor possa processar a mensagem corretamente.
     * Portanto, a função concatMsg é chamada sempre que o usuário digita uma mensagem para enviar ao servidor e retorna a mensagem formatada para ser enviada através do socket.
     */
    private String concatMsg(String msg) {
        return "Text&" + "[" + user + "]" + " ~> " + msg + "\r\n";
    }


    /**
     * O método writeOutput recebe uma string como parâmetro e a adiciona na caixa de texto output. 
     * A função append adiciona a string ao final da caixa de texto, acrescentando uma quebra de linha \r\n 
     * para que o próximo texto seja adicionado em uma nova linha. O objetivo desse método é exibir mensagens 
     * de texto na interface do usuário para que o usuário possa ver o que está acontecendo na aplicação.
     */
    private void writeOutput(String phrase) {
        output.append(phrase + "\r\n");
    }

    /**
     * O método establishConnection() é responsável por estabelecer uma conexão com um servidor de chat. Ele cria uma instância de Socket para estabelecer a conexão 
     * com o servidor de chat usando o endereço IP do servidor e o número da porta.
     * 
     * Em seguida, cria uma instância de BufferedWriter para escrever no fluxo de saída do soquete e envia o nome do usuário para o servidor de chat como uma mensagem. 
     * Por fim, o método flush() é chamado para garantir que todos os dados sejam escritos no fluxo de saída.
     * Se ocorrer uma exceção do tipo ConnectException, é impresso uma mensagem informando que não foi possível criar a conexão, indicando que o servidor de chat está 
     * indisponível na porta e no endereço IP especificados. Caso contrário, qualquer outra exceção é impressa no console e a conexão é desconectada usando o método disconnect().
     */
    private void establishConnection() {
        try {
            socket = new Socket(this.serverIP, this.serverPort);
            bufferWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bufferWriter.write(user + "\r\n");
            bufferWriter.flush();
        } catch (ConnectException e) {
            System.out.println("Não foi possivel criar a conexão, servidor indisponível na porta e IP indicados.");
        } catch (Exception e) {
            e.printStackTrace();
            disconnect();
        }
    }

    /**
     * Esse método é responsável por escutar a conexão com o servidor e ler as mensagens recebidas. 
     * Ele cria um objeto BufferedReader que lê o fluxo de entrada do Socket e, em seguida, entra em um loop do-while que é executado enquanto 
     * a mensagem recebida não contém o comando "Disconnect" seguido do nome do usuário conectado. Dentro do loop, o método verifica se o BufferedReader 
     * está pronto para ler a mensagem e, se estiver, lê a mensagem e a divide em duas partes usando o caractere "&". A primeira parte contém o comando 
     * da mensagem e a segunda parte contém o texto da mensagem. Se o comando da mensagem for "Text", o método chama o método writeOutput() para imprimir o 
     * texto da mensagem na área de saída do cliente. Se o comando for diferente de "Text", o método imprime uma mensagem de erro na área de saída do cliente. 
     * Se ocorrer uma exceção durante a leitura da mensagem, o método imprime uma mensagem de erro na saída do sistema
     */
    private void listenConnection() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String msg = "";
            String command;
            String textMsg;

            do {
                if (bufferedReader.ready()) {
                    msg = bufferedReader.readLine();
                    command = msg.split("&", 2)[0];
                    textMsg = msg.split("&", 2)[1];

                    if(command.equals("Text")) {
                        writeOutput(textMsg);
                    } else {
                        writeOutput("Algo está errado na mensagem recebida do servidor");
                    }
                }
            } while (!("Disconnect " + user).equalsIgnoreCase(msg));

        } catch (Exception e) {
            System.out.println("Impossivel escutar servidor. O mesmo possivelmente está indisponível.");
        }
    }

    /**
     * Esse código representa um método que fecha a conexão do cliente com o servidor. 
     * Ele começa chamando o método writeOutput() para mostrar no console do cliente que a conexão foi desconectada. 
     * Em seguida, ele chama o método sendMessage() para enviar uma mensagem "Disconnect" para o servidor. 
     * O método sendMessage() envia a mensagem "Disconnect" ao servidor com o nome do usuário conectado. 
     * Em seguida, o método tenta fechar o bufferWriter e o socket usados para se comunicar com o servidor. 
     * Se houver uma exceção ao tentar fechar os recursos, uma mensagem de erro é exibida no console do cliente. 
     * Em resumo, esse método é responsável por finalizar a conexão entre o cliente e o servidor.
     */
    private void disconnect() {
        writeOutput("Desconectado");
        sendMessage("Text&" + "Disconnect " + this.user);
        try {
            bufferWriter.close();
            socket.close();
        } catch (Exception e) {
            System.out.println("Nao é possível fechar conexão.");
        }
    }
}
