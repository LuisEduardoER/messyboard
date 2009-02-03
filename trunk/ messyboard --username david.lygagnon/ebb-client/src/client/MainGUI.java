package client;

import javax.swing.JFrame;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;


import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JButton;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import javax.swing.text.Document;

import common.Message;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class MainGUI extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel panel_main = null;
	private JScrollPane scrollPane_post = null;
	private JTextPane textPane_post = null;
	private JScrollPane scrollPane_table = null;
	private JTable table_posts = null;
	private JButton button_post = null;
	private DefaultTableModel model;
	Border border;
	
	private EbbClient ebbclient;	// client layer
	private JTextField textField_title = null;
	private JLabel label_title = null;
	/**
	 * This method initializes
	 * 
	 */
	public MainGUI(EbbClient client) {
		super("Electronic Bulletin Board");
		this.ebbclient = client;
		border = new LineBorder(Color.BLACK, 1);
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(800, 672));
		this.setContentPane(getJPanel());

	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (panel_main == null) {
			label_title = new JLabel();
			label_title.setBounds(new Rectangle(15, 400, 76, 25));
			label_title.setHorizontalTextPosition(SwingConstants.LEFT);
			label_title.setHorizontalAlignment(SwingConstants.LEFT);
			label_title.setText("Title");
			panel_main = new JPanel();
			panel_main.setLayout(null);
			panel_main.add(getJScrollPane1(), null);
			panel_main.add(getJScrollPane(), null);
			panel_main.add(getJButton(), null);
			panel_main.add(getJTextPane1(), null);
			panel_main.add(label_title, null);
		}
		return panel_main;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (scrollPane_post == null) {
			scrollPane_post = new JScrollPane();
			scrollPane_post.setBounds(new Rectangle(15, 453, 767, 134));
			scrollPane_post.setViewportView(getJTextPane());
		}
		return scrollPane_post;
	}

	/**
	 * This method initializes jTextPane Messages 
	 * 
	 * @return javax.swing.JTextPane
	 */
	private JTextPane getJTextPane() {
		if (textPane_post == null) {
			textPane_post = new JTextPane();
		}
		return textPane_post;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (scrollPane_table == null) {
			scrollPane_table = new JScrollPane();
			scrollPane_table.setBounds(new Rectangle(16, 61, 764, 300));
			scrollPane_table.setViewportView(getJTable1());
		}
		return scrollPane_table;
	}

	/**
	 * This method initializes jTable1
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getJTable1() {
		List<Message> message_list;
		Object[][] data;
		int num_post;	// number of posts initially
		
		message_list = new ArrayList<Message>();
		num_post = 0;
		
		message_list = ebbclient.viewMessages();
		if(message_list != null)
			num_post = message_list.size();
		
		data = new Object[num_post][3];
		
		for(int i=0; i < message_list.size(); i++) {
			data[i][0] = message_list.get(i).getMessage();
			data[i][1] = message_list.get(i).getOwner();
			data[i][2] = message_list.get(i).getDate();
		}
		
		String[] columnNames = { "Discussion Title", "Owner", "Date Created" };
/*
		Object[][] data = {
				{ "Mary", "Campione", "Snowboarding", new Integer(5),
						new Boolean(false) },
				{ "Alison", "Huml", "Rowing", new Integer(3), new Boolean(true) },
				{ "Kathy", "Walrath", "Knitting", new Integer(2),
						new Boolean(false) },
				{ "Sharon", "Zakhour", "Speed reading", new Integer(20),
						new Boolean(true) },
				{ "Philip", "Milne", "Pool", new Integer(10),
						new Boolean(false) } };
	*/	
		model = new DefaultTableModel(data,columnNames);
		
		if (table_posts == null) {
			table_posts = new JTable(model){
				private static final long serialVersionUID = 1L;

				public boolean isCellEditable(int row, int column){
					return false;
				}
			};
			table_posts.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		}
		return table_posts;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (button_post == null) {
			button_post = new JButton("Post");
			button_post.setBounds(new Rectangle(14, 598, 76, 32));
			button_post.addActionListener(this);
		}
		return button_post;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		// TODO Auto-generated method stub
		if (event.getSource() == button_post){
			System.out.println("[gui]: " + textField_title.getText() +textPane_post.getText());
			ebbclient.postMessage(textField_title.getText(), textPane_post.getText());
			
			model.insertRow(table_posts.getRowCount(),new Object[]{textField_title.getText(),"jack", "date"});
			
			textPane_post.setText("");
		}
		
	}

	/**
	 * This method initializes jTextPane1	
	 * 	
	 * @return javax.swing.JTextPane	
	 */
	private JTextField getJTextPane1() {
		
		if (textField_title == null) {
			textField_title = new JTextField();
			textField_title.setBounds(new Rectangle(15, 425, 420, 20));
			textField_title.setToolTipText("Title");
			textField_title.setText("");
			textField_title.setBorder(border);
			Document doc = textField_title.getDocument();
			AbstractDocument absDoc = (AbstractDocument )doc;
			absDoc.setDocumentFilter(new DocumentSizeFilter (100));
		}
		return textField_title;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
