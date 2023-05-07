/**
 * Esse código é a declaração dos pacotes e classes necessárias para criar uma janela de diálogo para o chat. 
 * Ele inclui as classes JButton, JDialog, JPanel, EmptyBorder, JLabel e Font do pacote javax.swing, além das classes ActionListener, 
 * WindowAdapter e WindowListener dos pacotes java.awt.event.
 * 
 * Essas classes são usadas para criar e configurar os elementos da janela de diálogo, como botões, painéis e rótulos, 
 * bem como para definir os eventos que ocorrem durante a interação do usuário com a janela. A classe ActionListener é usada para definir o 
 * comportamento dos botões quando eles são clicados, enquanto as classes WindowAdapter e WindowListener são usadas para gerenciar eventos 
 * relacionados à abertura e fechamento da janela.
 */

package chat;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.ActionEvent;

public class Iniciar extends JDialog {

    /**
     * Este código cria uma variável estática "serialVersionUID" que é usada para controlar a versão da classe serializada. 
     * Em seguida, cria um objeto JPanel chamado "contentPanel" que é usado para conter outros componentes gráficos. 
     * O modificador "final" indica que o conteúdo do painel não pode ser alterado após a inicialização.
     */
    private static final long serialVersionUID = 5602644222765201727L;
    private final JPanel contentPanel = new JPanel();

    /**
     * Este código é o método principal (main) do programa. 
     * Ele inicia a execução do aplicativo. 
     * Dentro dele, um novo objeto da classe Iniciar é criado e sua visibilidade é definida como verdadeira (setVisible(true)), 
     * o que faz com que a janela de início do aplicativo seja exibida na tela. 
     * Se ocorrer alguma exceção durante a execução, ela será impressa na saída padrão de erro (e.printStackTrace()).
     */
    public static void main(String[] args) {
        try {
            Iniciar dialog = new Iniciar();
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Esse código cria uma janela de diálogo que permite ao usuário escolher entre iniciar o programa como servidor ou como cliente. 
     * A janela tem dois botões, um para configurar o servidor e outro para fazer o login como cliente. Quando o botão "Servidor" é clicado, 
     * a janela atual é fechada e a janela de configuração do servidor é aberta. Quando o botão "Cliente" é clicado, a janela atual é fechada e a 
     * janela de login do cliente é aberta. A janela de diálogo também inclui um rótulo que pergunta ao usuário como deseja inicializar o programa.
     */
    public Iniciar() {
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        getContentPane().setLayout(null);
        contentPanel.setBounds(0, 0, 434, 228);
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel);
        contentPanel.setLayout(null);
        setLocationRelativeTo(null);
        setTitle("Aplicação de Conversa");
        {
            JButton btnServidor = new JButton("Servidor");
            btnServidor.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
                    ConfiguracaoDoServidor server = new ConfiguracaoDoServidor();
                    server.setVisible(true);
                    Iniciar.this.dispose();
                }
            });
            btnServidor.setBounds(10, 126, 182, 61);
            contentPanel.add(btnServidor);
        }
        {
            JButton btnCliente = new JButton("Cliente");
            btnCliente.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    Login login = new Login();
                    login.setVisible(true);
                    WindowListener exitListener = new WindowAdapter() {

                        @Override
                        public void windowClosed(WindowEvent e) {
                            Iniciar.this.dispose();
                        }
                    };
                    login.addWindowListener(exitListener);
                }
            });
            btnCliente.setBounds(242, 126, 182, 61);
            contentPanel.add(btnCliente);
        }
        {
            JLabel lblInicializar = new JLabel("Como deseja inicializar o programa?");
            lblInicializar.setBounds(97, 40, 242, 34);
            contentPanel.add(lblInicializar);
            lblInicializar.setFont(new Font("Arial", Font.PLAIN, 15));
        }
        {
            JPanel buttonPane = new JPanel();
            buttonPane.setBounds(0, 228, 434, 33);
            getContentPane().add(buttonPane);
            buttonPane.setLayout(null);
        }
    }

}
