package quizz;


import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JList;


import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

import tools.Logger;
import users.User;
import users.UserManager;

public class SelectUser extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4074456463041573433L;
	private JPanel contentPane;
	private JTextField newUserName;
	public JList userList = null; 
	private String userName = null; 
	
	/**
	 * Create the frame.
	 */
	public SelectUser(JFrame parent) {
		super(parent, "Create / Select a user", true);
		
		setBounds(100, 100, 469, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JPanel selectUserPanel = new JPanel();
		
		JPanel createUserPanel = new JPanel();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(createUserPanel, GroupLayout.DEFAULT_SIZE, 423, Short.MAX_VALUE)
						.addComponent(selectUserPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(selectUserPanel, GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
					.addGap(18)
					.addComponent(createUserPanel, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE))
		);
		
		JLabel lblSelectAUser = new JLabel("Select a user:");
		
		JScrollPane scrollPane = new JScrollPane();
		
		final JButton btnSelect = new JButton("Select");
		btnSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				userName = (String)userList.getSelectedValue();
				dispose();
			}
		});
		btnSelect.setEnabled(false);
		GroupLayout gl_selectUserPanel = new GroupLayout(selectUserPanel);
		gl_selectUserPanel.setHorizontalGroup(
			gl_selectUserPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_selectUserPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_selectUserPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 423, Short.MAX_VALUE)
						.addComponent(lblSelectAUser)
						.addComponent(btnSelect, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		gl_selectUserPanel.setVerticalGroup(
			gl_selectUserPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_selectUserPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblSelectAUser)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnSelect))
		);
		
		userList = new JList(UserManager.getUsersList().toArray());
		userList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
					btnSelect.setEnabled(!((JList) arg0.getSource()).isSelectionEmpty());
			}
		});
		userList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(userList);
		selectUserPanel.setLayout(gl_selectUserPanel);
		
		JLabel lblCreateANew = new JLabel("Create a new user");
		
		JLabel lblName = new JLabel("Name");
		final JButton btnCreate = new JButton("Create !");
		
		newUserName = new JTextField();
		newUserName.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed (KeyEvent arg0) {}
			@Override
			public void keyReleased(KeyEvent e) {}

			@Override
			public void keyTyped(KeyEvent e) {
				btnCreate.setEnabled(!((JTextField) e.getSource()).getText().isEmpty());
			}
		});
		newUserName.setColumns(10);
		
		
		btnCreate.setEnabled(false);
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Logger.log("click! (" + newUserName.getText() + ")");
				UserManager.createUser(newUserName.getText());
				userList = new JList(UserManager.getUsersList().toArray());
			}
		});
		GroupLayout gl_createUserPanel = new GroupLayout(createUserPanel);
		gl_createUserPanel.setHorizontalGroup(
			gl_createUserPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_createUserPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_createUserPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_createUserPanel.createSequentialGroup()
							.addGap(62)
							.addComponent(lblName)
							.addGap(18)
							.addComponent(newUserName, GroupLayout.DEFAULT_SIZE, 316, Short.MAX_VALUE))
						.addComponent(btnCreate, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblCreateANew))
					.addContainerGap())
		);
		gl_createUserPanel.setVerticalGroup(
			gl_createUserPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_createUserPanel.createSequentialGroup()
					.addContainerGap(38, Short.MAX_VALUE)
					.addComponent(lblCreateANew)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_createUserPanel.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblName)
						.addComponent(newUserName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnCreate)
					.addContainerGap())
		);
		createUserPanel.setLayout(gl_createUserPanel);
		contentPane.setLayout(gl_contentPane);
		setVisible(true);
	}

	public User getSelectedUser() {
		if(userName != null) {
			try {
				return UserManager.loadUser(userName);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();		
			}
		}
		return null;
	}
}
