/**
 * Este código simplesmente importa as classes EventQueue, JFrame, JPanel e EmptyBorder do pacote javax.swing. 
 * Essas classes são usadas para criar e gerenciar interfaces gráficas de usuário (GUIs) em Java. 
 * O JFrame é uma classe que representa uma janela de aplicativo, o JPanel é um contêiner de componentes e 
 * o EmptyBorder é usado para adicionar espaçamento ao redor dos componentes dentro de um contêiner. 
 * O EventQueue é usado para despachar eventos em threads de eventos, o que é importante para garantir a sincronização e o desempenho adequados das GUIs Java.
 */
package chat;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


/**
 * Este código importa várias classes e interfaces Java utilizadas na implementação de uma aplicação de chat. Aqui está uma breve explicação de cada declaração de importação:
 * 
 * conexao.Servidor: Este import é para uma classe ou pacote personalizado chamado "conexao" que contém uma classe ou pacote "Servidor". 
 * Esta classe ou pacote não faz parte da biblioteca padrão do Java e sua funcionalidade é desconhecida sem examinar sua implementação.
 * 
 * javax.swing.JLabel: Este import é para a classe JLabel no pacote javax.swing. É um componente de GUI que exibe uma única linha de texto.
 * java.awt.Font: Este import é para a classe Font no pacote java.awt. É usado para definir o estilo, tamanho e tipo de letra para o texto exibido em uma interface gráfica do usuário.
 * javax.swing.JTextField: Este import é para a classe JTextField no pacote javax.swing. É um componente de GUI que permite ao usuário inserir uma única linha de texto.
 * javax.swing.JButton: Este import é para a classe JButton no pacote javax.swing. É um componente de GUI que representa um botão clicável.
 * java.awt.event.ActionListener: Este import é para a interface ActionListener no pacote java.awt.event. É usada para lidar com cliques em botões e outros eventos acionados pelo usuário em uma interface gráfica do usuário.
 * java.awt.event.ActionEvent: Este import é para a classe ActionEvent no pacote java.awt.event. É usada para representar um evento que ocorre como resultado de uma ação do usuário, como clicar em um botão.
 * java.awt.CardLayout: Este import é para a classe CardLayout no pacote java.awt. É um gerenciador de layout usado para alternar entre diferentes visualizações ou "cartões" em uma GUI.
 * java.net.InetAddress: Este import é para a classe InetAddress no pacote java.net. É usada para representar um endereço de Protocolo da Internet (IP).
 * java.net.ServerSocket: Este import é para a classe ServerSocket no pacote java.net. É usada para ouvir conexões de rede recebidas em um servidor.
 * java.net.Socket: Este import é para a classe Socket no pacote java.net. É usada para estabelecer uma conexão com um host remoto em uma rede.
 */
import conexao.Servidor;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.CardLayout;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class ConfiguracaoDoServidor extends JFrame {
	/**
	 * Variáveis ​​do servidor
	 * 
	 * Esse código declara uma variável estática do tipo ServerSocket chamada "server", que é uma classe da biblioteca padrão do 
	 * Java usada para criar um servidor que escuta conexões de rede. A variável é definida como privada, o que significa que só pode 
	 * ser acessada dentro da classe em que foi declarada. A palavra-chave "static" indica que a variável é uma variável de classe, 
	 * e não uma variável de instância, o que significa que é compartilhada por todas as instâncias da classe e pode ser acessada sem a 
	 * necessidade de criar uma instância da classe.
	 */
	private static ServerSocket server;

	/**
	 *  variáveis ​​de formulário
	 * 
	 * Esse trecho de código define variáveis ​​de formulário para uma interface gráfica de usuário. 
	 * O serialVersionUID é uma variável estática usada para garantir a compatibilidade da classe serializada com diferentes versões da JVM. 
	 * O contentPane é um painel que é usado como o contêiner principal para os elementos da interface gráfica. 
	 * O inputPort é um JTextField onde o usuário pode inserir um número de porta para iniciar uma conexão de servidor. 
	 * Os botões btnOk e btnBack são botões clicáveis ​​que executam funções específicas. 
	 * O panelConfig e o panelStatus são painéis que exibem informações e opções de configuração. 
	 * Os JLabels lblIp, lblPort, lblValueIP e lblValuePort exibem informações relacionadas ao endereço IP e ao número da porta. 
	 * O btnStopConnection é um botão usado para interromper uma conexão existente. 
	 * O InetAddress e o hostaddress são usados para armazenar informações relacionadas ao endereço IP e ao nome do host.
	 */
	private static final long serialVersionUID = 4998717362394143017L;
	private JPanel contentPane;
	private JTextField inputPort;
	private JButton btnOk;
	private JButton btnBack;
	private JPanel panelConfig;
	private JPanel panelStatus;
	private JLabel lblIp;
	private JLabel lblPort;
	private JLabel lblValueIP;
	private JLabel lblValuePort;
	private JButton btnStopConnection;
	private InetAddress inetAddress;
	private String hostaddress;

	/**
	 * Esse trecho de código é responsável por iniciar a aplicação. 
	 * Ele define o método main() que é o ponto de entrada da aplicação. 
	 * Ao ser executado, esse método cria uma instância da classe ConfiguracaoDoServidor, torna a janela visível e a exibe na tela. 
	 * A classe ConfiguracaoDoServidor é a classe principal da aplicação e contém a lógica para configurar o servidor de chat. 
	 * O método main() também captura e imprime qualquer exceção que possa ocorrer durante a execução do programa.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConfiguracaoDoServidor frame = new ConfiguracaoDoServidor();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Esse código cria uma interface gráfica para configuração e execução de um servidor de bate-papo. 
	 * A classe ConfiguracaoDoServidor estende JFrame, que é uma classe do Swing para criar janelas de aplicativo. A janela tem dois painéis: panelConfig e panelStatus.
	 * 
	 * O painel panelConfig contém elementos de interface de usuário (UI) para configurar o servidor, incluindo um rótulo JLabel para indicar ao usuário que a entrada inputPort 
	 * é para o número da porta, uma caixa de texto JTextField para inserir o número da porta, um botão JButton para iniciar o servidor e um botão JButton para voltar à 
	 * janela anterior.
	 * 
	 * O painel panelStatus contém elementos de interface de usuário que exibem o status do servidor, incluindo dois rótulos JLabel para exibir o endereço IP e 
	 * número da porta do servidor, e um botão JButton para encerrar a conexão do servidor.
	 * 
	 * O método construtor ConfiguracaoDoServidor() é responsável por construir a janela, adicionar os elementos de interface do usuário aos painéis, definir seus layouts 
	 * e especificar suas ações. A janela é exibida quando o método main é executado, criando uma nova instância de ConfiguracaoDoServidor e definindo-a como visível.
	 */
	public ConfiguracaoDoServidor() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 366, 158);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new CardLayout(0, 0));
		this.setLocationRelativeTo(null);
		this.setTitle("Aplicação de Conversa (Servidor)");
		
		panelConfig = new JPanel();
		contentPane.add(panelConfig, "panelConfig");
		panelConfig.setLayout(null);
		
		JLabel lblNumeroDaPorta = new JLabel("Número da porta:");
		lblNumeroDaPorta.setBounds(10, 35, 113, 18);
		panelConfig.add(lblNumeroDaPorta);
		lblNumeroDaPorta.setFont(new Font("Arial", Font.PLAIN, 15));
		
		inputPort = new JTextField();
		inputPort.setBounds(135, 35, 86, 20);
		panelConfig.add(inputPort);
		inputPort.setText("45454");
		inputPort.setColumns(10);
		
		btnOk = new JButton("Ok");
		btnOk.setBounds(90, 86, 73, 23);
		panelConfig.add(btnOk);
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				      		
				startServer();
			}
		});
		
		btnBack = new JButton("Voltar");
		btnBack.setBounds(177, 86, 73, 23);
		panelConfig.add(btnBack);
		
		panelStatus = new JPanel();
		contentPane.add(panelStatus, "panelStatus");
		panelStatus.setLayout(null);
		
		lblIp = new JLabel("IP:");
		lblIp.setFont(new Font("Arial", Font.PLAIN, 15));
		lblIp.setBounds(38, 11, 46, 14);
		panelStatus.add(lblIp);
		
		lblPort = new JLabel("Porta:");
		lblPort.setFont(new Font("Arial", Font.PLAIN, 15));
		lblPort.setBounds(38, 36, 46, 14);
		panelStatus.add(lblPort);
		
		lblValueIP = new JLabel(); //TODO: JLabel(getIP())
		lblValueIP.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblValueIP.setBounds(67, 11, 171, 14);
		panelStatus.add(lblValueIP);
		
		lblValuePort = new JLabel(); //TODO: JLabel(getPort())
		lblValuePort.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblValuePort.setBounds(80, 36, 154, 14);
		panelStatus.add(lblValuePort);
		
		btnStopConnection = new JButton("Encerrar Conexão");
		btnStopConnection.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
				System.exit(0);
			}
		});
		btnStopConnection.setBounds(94, 79, 144, 30);
		panelStatus.add(btnStopConnection);
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ConfiguracaoDoServidor.this.dispose();
				Iniciar start = new Iniciar();
				start.setVisible(true);
			}
		});
	}

	/**
	 * Este código é responsável por iniciar um servidor de chat em uma aplicação. 
	 * Ele cria uma interface gráfica que permite ao usuário configurar a porta do servidor. 
	 * Quando o usuário pressiona o botão "Ok", o método startServer() é chamado. 
	 * Este método cria um novo objeto ServerSocket na porta especificada pelo usuário e começa a ouvir novas conexões em um loop infinito. 
	 * Quando uma nova conexão é estabelecida, é criada uma nova thread para lidar com essa conexão usando a classe Servidor. 
	 * A interface gráfica é atualizada para mostrar o endereço IP do servidor e a porta em uso. Além disso, há um botão para encerrar a conexão com o servidor.
	 */
	private void startServer() {
		try {
			server = new ServerSocket(Integer.parseInt(inputPort.getText()));
			new Thread(new Runnable() {
				@Override
				public void run() {		
					CardLayout c = (CardLayout)(contentPane.getLayout());
					c.show(contentPane, "panelStatus");
					try {
						inetAddress = InetAddress.getLocalHost();
						hostaddress = inetAddress.getHostAddress();
						lblValueIP.setText(hostaddress);
					} catch (UnknownHostException e1) {
						e1.printStackTrace();
					}					
					lblValuePort.setText(inputPort.getText());
					while (true) {
		                System.out.println("Waiting connection...");
		                try {
		                Socket connection = server.accept();
		                Thread serverThread = new Servidor(connection);
		                serverThread.start();
		                } catch (Exception e) {
		                	e.printStackTrace();
		                }		                
		            }
				}				
			}).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}
