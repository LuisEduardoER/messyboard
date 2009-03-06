package client;

import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import java.awt.Rectangle;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import common.Message;
import javax.swing.JTextField;

public class Post_Window extends JDialog implements ActionListener{

	private static final long serialVersionUID = 1L;
	
	private EbbClient ebbclient;	// client layer
	
	private Message message;

	private JPanel post_window = null;

	private JScrollPane window_scroller = null;

	private JTextPane posting_txt = null;

	private JButton update_btn = null;

	private JButton delete_btn = null;

	private JButton cancel_btn = null;

	private JTextField title_txt = null;

	public Post_Window(JFrame parent, EbbClient client, Message message) {
		super(parent, true);
		this.ebbclient = client;
		this.message = message;
		initialize();
	}
	
	
	private void initialize() {
		this.setSize(new Dimension(800, 282));
		this.setTitle("Edit Post");
		this.setContentPane(getPostWindow());
	}

	private JPanel getPostWindow() {
		if (post_window == null) {
			post_window = new JPanel();
			post_window.setLayout(null);
			post_window.add(getJScrollPane(), null);
			post_window.add(getUpdateButton(), null);
			post_window.add(getDeleteButton(), null);
			post_window.add(getCancelButton(), null);
			post_window.add(getTextField_title(), null);
		}
		return post_window;
	}

	private JScrollPane getJScrollPane() {
		if (window_scroller == null) {
			window_scroller = new JScrollPane();
			window_scroller.setBounds(new Rectangle(9, 60, 774, 136));
			window_scroller.setViewportView(getJTextPane());
		}
		return window_scroller;
	}

	private JTextPane getJTextPane() {
		if (posting_txt == null) {
			posting_txt = new JTextPane();
			posting_txt.setSize(new Dimension(6, 21));
			posting_txt.setText(this.message.getMessage());
		}
		return posting_txt;
	}

	private JButton getUpdateButton() {
		if (update_btn == null) {
			update_btn = new JButton();
			update_btn.setText("update");
			update_btn.setSize(new Dimension(100, 30));
			update_btn.setLocation(new Point(15, 210));
			update_btn.addActionListener(this);
		}
		return update_btn;
	}

	private JButton getDeleteButton() {
		if (delete_btn == null) {
			delete_btn = new JButton();
			delete_btn.setText("delete");
			delete_btn.setSize(new Dimension(100, 30));
			delete_btn.setLocation(new Point(144, 209));
			delete_btn.addActionListener(this);
		}
		return delete_btn;
	}

	private JButton getCancelButton() {
		if (cancel_btn == null) {
			cancel_btn = new JButton();
			cancel_btn.setText("cancel");
			cancel_btn.setSize(new Dimension(100, 30));
			cancel_btn.setLocation(new Point(269, 210));
			cancel_btn.addActionListener(this);
		}
		return cancel_btn;
	}

	@Override
	/**
	 * Button listener: Handles actions performed on message: UPDATE, DELETE, CANCEL
	 */
	public void actionPerformed(ActionEvent event) {
		System.out.println("message id : " + this.message.getId() + "message title : " + this.message.getTitle());
		
		//Button UPDATE is clicked
		if (event.getSource() == update_btn){
			if (!this.message.getTitle().equals(title_txt.getText()) && !this.message.getMessage().equals(posting_txt.getText())) {
				this.message.setTitle(title_txt.getText());
				this.message.setMessage(posting_txt.getText());
				ebbclient.updateMessage(this.message.getId(), title_txt.getText(), posting_txt.getText());
			}
			else if(!this.message.getTitle().equals(title_txt.getText()))
			{
				this.message.setTitle(title_txt.getText());
				ebbclient.updateMessage(this.message.getId(), title_txt.getText(), posting_txt.getText());
			}
			else if(!this.message.getMessage().equals(posting_txt.getText()))
			{
				this.message.setMessage(posting_txt.getText());
				ebbclient.updateMessage(this.message.getId(), title_txt.getText(), posting_txt.getText());
			}
			
			ebbclient.updateMessage(this.message.getId(), title_txt.getText(), posting_txt.getText());
			//TODO CLOSE AND UPDATE PARENT...
		}
		else if (event.getSource() == delete_btn){
			System.out.println("Delete");
		}
		else if (event.getSource() == cancel_btn){
			System.out.println("Cancel");
		}
		else {
			
		}
	}

	private JTextField getTextField_title() {
		if (title_txt == null) {
			title_txt = new JTextField();
			title_txt.setText(this.message.getTitle());
			title_txt.setLocation(new Point(10, 26));
			title_txt.setSize(new Dimension(420, 20));
			title_txt.setToolTipText("Title");
		}
		return title_txt;
	}
}  //  @jve:decl-index=0:visual-constraint="10,10"
