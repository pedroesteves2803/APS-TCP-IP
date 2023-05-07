/**
 * Esse código define uma classe chamada chat que contém uma janela de aplicativo gráfico. A janela tem dois botões, um rótulo e um campo de texto.
 * 
 * Os botões permitem ao usuário escolher entre iniciar o aplicativo como um servidor ou um cliente. Quando o botão "Servidor" é pressionado, 
 * uma nova janela é aberta para configurar as opções do servidor. Quando o botão "Cliente" é pressionado, uma nova janela é aberta para o usuário inserir suas credenciais de login.
 * 
 * O rótulo é uma mensagem que pergunta ao usuário como deseja inicializar o programa, e o campo de texto é onde o usuário pode inserir o número da porta para o servidor.
 * 
 * Em resumo, esse código define a interface gráfica do usuário (GUI) para a aplicação de conversa, permitindo que o usuário escolha como iniciar 
 * o programa (como servidor ou cliente) e insira informações relevantes.
 */

package chat;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Login extends JFrame {

    /**
     * Esse código define uma classe que representa a interface gráfica do usuário para a configuração do cliente em um sistema de chat. 
     * Ela é uma subclasse de JFrame, que é uma classe que fornece uma janela padrão para um aplicativo de desktop. 
     * A classe define três campos de texto (inputIP, inputPort e inputUser) para o usuário inserir o endereço IP do servidor, a porta do servidor e o nome de usuário, respectivamente.
     * 
     * A classe também contém um botão "Conectar" que o usuário pode pressionar para iniciar uma conexão com o servidor. Q
     * uando o botão é pressionado, a ação correspondente é executada, que extrai as informações inseridas pelo usuário nos campos de texto e inicia uma nova conexão com o servidor. 
     * A classe possui métodos getter para acessar o conteúdo dos campos de texto e um construtor que define a aparência da janela do aplicativo.
     */
    private static final long serialVersionUID = 948747724372712259L;
    private JPanel contentPane;
    private JTextField inputIP;
    private JTextField inputPort;
    private JTextField inputUser;

    /**
     * Este código é responsável por iniciar a aplicação. Ele chama a classe Login e a torna visível, criando uma nova instância dela na thread de eventos. 
     * A classe Login é a interface gráfica que permite ao usuário fazer login no sistema de chat.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Login frame = new Login();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Esse código é responsável por criar uma janela de login para o usuário inserir informações de conexão com um servidor de chat. 
     * A janela contém campos de texto para inserir o endereço IP do servidor, porta de conexão e nome de usuário.
     * 
     * O construtor da classe Login define as configurações da janela, como o tamanho, posição, layout e título. 
     * Também cria os componentes visuais da janela, como botões, rótulos e campos de texto, e define suas posições na janela.
     * 
     * O botão "Login" tem um ouvinte de ação que é acionado quando o botão é pressionado. Quando o botão é pressionado, a janela de login é fechada, 
     * a conexão com o servidor é estabelecida e a aplicação é iniciada.
     */
    public Login() {
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 374, 277);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        setLocationRelativeTo(null);
        setTitle("Aplicação de Conversa (Cliente)");

        JButton btnLogin = new JButton("Login");
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                Login.this.dispose();
                setFocusableWindowState(false);

                connect();
            }
        });

        btnLogin.setFont(new Font("Arial", Font.PLAIN, 15));
        btnLogin.setBounds(5, 198, 355, 39);
        contentPane.add(btnLogin);

        JLabel lblServerIp = new JLabel("IP do servidor:");
        lblServerIp.setFont(new Font("Arial", Font.PLAIN, 15));
        lblServerIp.setBounds(5, 11, 151, 39);
        contentPane.add(lblServerIp);

        inputIP = new JTextField();
        inputIP.setText("127.0.0.1");
        inputIP.setFont(new Font("Arial", Font.PLAIN, 15));
        inputIP.setBounds(138, 15, 222, 30);
        contentPane.add(inputIP);
        inputIP.setColumns(10);

        JLabel lblPortaDoServidor = new JLabel("Porta do servidor:");
        lblPortaDoServidor.setFont(new Font("Arial", Font.PLAIN, 15));
        lblPortaDoServidor.setBounds(5, 72, 151, 39);
        contentPane.add(lblPortaDoServidor);

        inputPort = new JTextField();
        inputPort.setText("45454");
        inputPort.setFont(new Font("Arial", Font.PLAIN, 15));
        inputPort.setColumns(10);
        inputPort.setBounds(138, 76, 222, 30);
        contentPane.add(inputPort);

        JLabel lblUsurio = new JLabel("Nome de usuário:");
        lblUsurio.setFont(new Font("Arial", Font.PLAIN, 15));
        lblUsurio.setBounds(5, 134, 151, 39);
        contentPane.add(lblUsurio);

        inputUser = new JTextField();
        inputUser.setText("unip1");
        inputUser.setFont(new Font("Arial", Font.PLAIN, 15));
        inputUser.setColumns(10);
        inputUser.setBounds(138, 138, 222, 30);
        contentPane.add(inputUser);
    }


    /**
     * Este método connect() cria uma nova thread e passa os parâmetros getUser(), getIP() e getPort() para a classe Cliente. 
     * Essa classe Cliente é iniciada na nova thread e é responsável por estabelecer a conexão com o servidor de chat usando as informações fornecidas pelo usuário. 
     * Se houver alguma exceção durante a execução, ela será registrada em uma pilha de exceções.
     */
    private void connect() {
        try {
            Thread thread = new Thread(new Cliente(new String[] {getUser(), getIP(), getPort()}));
            thread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * O método getIP() retorna o texto (String) contido no objeto inputIP, que é um JTextField. 
     * Isso significa que o método retorna o endereço IP digitado pelo usuário na interface gráfica da aplicação.
     */
    private String getIP() {
        return inputIP.getText();
    }

    /**
     * Esse código é um método chamado getPort() que retorna o valor do campo de texto inputPort, que representa a porta do servidor de chat que o cliente irá se conectar. 
     * Ele basicamente obtém a string de texto inserida pelo usuário no campo de texto inputPort e retorna como resultado.
     */
    private String getPort() {
        return inputPort.getText();
    }

    /**
     * Esse método retorna o nome de usuário inserido pelo usuário na interface gráfica do aplicativo de chat. 
     * Ele obtém o texto digitado no campo de entrada de texto "inputUser" e o retorna como uma string.
     */
    private String getUser() {
        return inputUser.getText();
    }


}
