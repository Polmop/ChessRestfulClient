package applicationView;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;

import modelClasses.JSONMessage;
import modelClasses.JSONMessage.EnergyOrNot;
import modelClasses.JSONMessage.Topic;
import serverConnectors.GameRegistration;
import serverConnectors.GetMoveFromServer;
import serverConnectors.SendMoveToServer;

public class ChessGUIView {

	private final static Logger LOGGER = Logger.getLogger(ChessGUIView.class.getName()); 
	private JFrame frmChessConnectorClient;
	private JTextField engineName;
	private JTextField doWysy³ki;
	JButton exitGameButton = new JButton("Wyjd\u017A");
	JButton sendButton = new JButton("Wyslij");
	
	private Integer gameNumber=null;
	private Integer playerNumber=null;
	
	JLabel informationLabel = new JLabel("Hello.");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChessGUIView window = new ChessGUIView();
					window.frmChessConnectorClient.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ChessGUIView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmChessConnectorClient = new JFrame();
		frmChessConnectorClient.setTitle("Chess Connector Client");
		frmChessConnectorClient.setBounds(100, 100, 1172, 417);
		frmChessConnectorClient.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmChessConnectorClient.getContentPane().setLayout(null);
		
		JLabel lblSilnik = new JLabel("Silnik");
		lblSilnik.setBounds(240, 19, 46, 14);
		frmChessConnectorClient.getContentPane().add(lblSilnik);
		
		engineName = new JTextField();
		engineName.setBounds(138, 35, 232, 22);
		frmChessConnectorClient.getContentPane().add(engineName);
		engineName.setColumns(10);
		
		JButton engineConnect = new JButton("Po\u0142\u0105cz");
		engineConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		engineConnect.setBounds(105, 59, 288, 32);
		frmChessConnectorClient.getContentPane().add(engineConnect);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)), "Typ Gry", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		panel.setBounds(105, 96, 290, 74);
		frmChessConnectorClient.getContentPane().add(panel);
		panel.setLayout(null);
		
		JRadioButton zagrajGreKlastrowa = new JRadioButton("Do\u0142\u0105cz do klastra silnik\u00F3w");
		zagrajGreKlastrowa.setBounds(60, 43, 199, 23);
		panel.add(zagrajGreKlastrowa);
		
		JRadioButton zagrajGreDuo = new JRadioButton("Do\u0142\u0105cz jako pojedy\u0144czy silnik");
		zagrajGreDuo.setBounds(60, 18, 199, 23);
		panel.add(zagrajGreDuo);
		zagrajGreDuo.setSelected(true);
		

		zagrajGreKlastrowa.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if(zagrajGreKlastrowa.isSelected())
				{
					zagrajGreDuo.setSelected(false);
					LOGGER.info("Game vs engine's  cluster is choosen");
				}
				else
				{
					zagrajGreDuo.setSelected(true);
					LOGGER.info("Game vs another engine is choosen");
				}
			}
		});
		zagrajGreDuo.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if(zagrajGreDuo.isSelected())
				{
					zagrajGreKlastrowa.setSelected(false);
					LOGGER.info("Game vs another engine is choosen");
				}
				else
				{
					zagrajGreKlastrowa.setSelected(true);
					LOGGER.info("Game vs engine's  cluster is choosen");
				}
			}
		});
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)), "Rodzaj klastra", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_1.setBounds(105, 170, 290, 74);
		frmChessConnectorClient.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JRadioButton zagrajGreKlasyczna = new JRadioButton("Gra Klasyczna");
		zagrajGreKlasyczna.setBounds(58, 19, 109, 23);
		panel_1.add(zagrajGreKlasyczna);
		
		JRadioButton zagrajGraEnergooszczdna = new JRadioButton("Gra Energooszcz\u0119dna");
		zagrajGraEnergooszczdna.setBounds(58, 42, 147, 23);
		panel_1.add(zagrajGraEnergooszczdna);
		

		zagrajGraEnergooszczdna.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(zagrajGraEnergooszczdna.isSelected())
				{
					zagrajGreKlasyczna.setSelected(false);
					LOGGER.info("Look on energy during game was choosen");
				}
				else
				{
					zagrajGreKlasyczna.setSelected(true);
					LOGGER.info("Do not look on energy during game was choosen");
				}
			}
		});
		

		zagrajGreKlasyczna.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(zagrajGreKlasyczna.isSelected()){
					zagrajGraEnergooszczdna.setSelected(false);
					LOGGER.info("Do not look on energy during game was choosen");
				}
				else{
					zagrajGraEnergooszczdna.setSelected(true);
					LOGGER.info("Look on energy during game was choosen");
				}
			}
		});
		zagrajGreKlasyczna.setSelected(true);
		
		JButton btnNewButton_1 = new JButton("DO\u0141\u0104CZ !!!");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				zagrajGraEnergooszczdna.setEnabled(false);
				zagrajGreDuo.setEnabled(false);
				zagrajGreKlastrowa.setEnabled(false);
				zagrajGreKlasyczna.setEnabled(false);
				engineName.setEnabled(false);
				btnNewButton_1.setEnabled(false);
				exitGameButton.setEnabled(true);
				engineConnect.setEnabled(false);
				
				if(zagrajGreDuo.isSelected())
				{
					try {
						JSONMessage returnMessage = new JSONMessage();
						if(zagrajGreKlasyczna.isSelected())
							returnMessage = GameRegistration.registerToDuoGame(EnergyOrNot.DONT_LOOK_ON_ENERGY);
						else if(zagrajGraEnergooszczdna.isSelected())
							returnMessage = GameRegistration.registerToDuoGame(EnergyOrNot.ENERGYSAFE);
						gameNumber = returnMessage.getGameNumber();
						playerNumber = returnMessage.getPlayerNumber();
						if(playerNumber == 1){
							informationLabel.setText("Engine register to Game " + gameNumber + " as player with white chess");
						}
						else if(playerNumber == 2){
							informationLabel.setText("Engine register to Game " + gameNumber + " as player with black chess");
							sendButton.setEnabled(false); 
						} 
						GetMoveFromServer gmfs = new GetMoveFromServer(gameNumber, playerNumber, informationLabel, sendButton);
						Thread t1 = new Thread(gmfs);
						t1.start();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						informationLabel.setText("Server is not working");
						e1.printStackTrace();
					}
				}
			}

			
		});
		btnNewButton_1.setBounds(105, 257, 139, 29);
		frmChessConnectorClient.getContentPane().add(btnNewButton_1);
		
		JLabel lblStatus = new JLabel("Status:");
		lblStatus.setBounds(10, 356, 55, 16);
		frmChessConnectorClient.getContentPane().add(lblStatus);
		
		informationLabel.setBounds(66, 356, 1080, 16);
		frmChessConnectorClient.getContentPane().add(informationLabel);
		
		doWysy³ki = new JTextField();
		doWysy³ki.setBounds(146, 298, 122, 28);
		frmChessConnectorClient.getContentPane().add(doWysy³ki);
		doWysy³ki.setColumns(10);
		
		sendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				
				sendButton.setEnabled(false); 
				informationLabel.setText(SendMoveToServer.sendMessage(gameNumber, playerNumber, Topic.SEND_MESSAGE, doWysy³ki.getText().toString()));
				LOGGER.info("To server we sent message with move");
			}
		});
		sendButton.setBounds(280, 298, 90, 28);
		frmChessConnectorClient.getContentPane().add(sendButton);
		
		exitGameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				zagrajGraEnergooszczdna.setEnabled(true);
				zagrajGreDuo.setEnabled(true);
				zagrajGreKlastrowa.setEnabled(true);
				zagrajGreKlasyczna.setEnabled(true);
				engineName.setEnabled(true);
				engineConnect.setEnabled(true);
				btnNewButton_1.setEnabled(true);
				exitGameButton.setEnabled(false);
			}
		});
		exitGameButton.setBounds(256, 256, 132, 28);
		frmChessConnectorClient.getContentPane().add(exitGameButton);
		
	}
}
