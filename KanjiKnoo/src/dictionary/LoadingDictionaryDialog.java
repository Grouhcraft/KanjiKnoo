package dictionary;

import java.awt.BorderLayout;
import javax.swing.JDialog;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JProgressBar;

public class LoadingDictionaryDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6790645705181865864L;
	private final JPanel contentPanel = new JPanel();
	private final JProgressBar progressBar = new JProgressBar();

	/**
	 * Create the dialog.
	 */
	public LoadingDictionaryDialog(int maxValue) {
		setAlwaysOnTop(true);
		setResizable(false);
		setUndecorated(true);
		setBounds(100, 100, 450, 138);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(47, 79, 79));
		contentPanel.setBorder(new LineBorder(new Color(0, 0, 0), 4, true));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		JLabel lblNewLabel = new JLabel("Loading dictionary..");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel.setForeground(new Color(192, 192, 192));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		progressBar.setMaximum(maxValue);
		progressBar.setFont(new Font("Tahoma", Font.ITALIC, 17));
		progressBar.setStringPainted(true);
		progressBar.setForeground(new Color(112, 128, 144));
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
						.addComponent(progressBar, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 422, Short.MAX_VALUE)
						.addComponent(lblNewLabel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 422, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(progressBar, GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
					.addContainerGap())
		);
		contentPanel.setLayout(gl_contentPanel);
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	public void setValue(int p) {
		progressBar.setValue(progressBar.getValue() + p);
	}
	
	public void incrementValue() {
		progressBar.setValue(progressBar.getValue() + 1);
	}
}
