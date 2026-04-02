package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import controller.mainMVC;
import model.LIVRE;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class View_rest {

	private JFrame frame;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public View_rest() throws ClassNotFoundException, SQLException {
		mainMVC.getM().getAll();
		initialize();
		frame.setVisible(true);

	}


	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JTextField textField_ISBN = new JTextField();
		
		JLabel lblNewLabel = new JLabel("ISBN");
		lblNewLabel.setBounds(114, 56, 31, 14);
		frame.getContentPane().add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(166, 53, 96, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btn = new JButton("OK");
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String ISBN = textField.getText();
				
				LIVRE l = mainMVC.getM().findLivre(ISBN);
		
				if (l==null)
				{
					System.out.println("pas trouvé");
				}
				else
				{
					if(l.getEmprunteur()==null)
					{
						System.out.println("Livre pas emprunté");
					}
					else
					{
						mainMVC.getM().updateEmprunteur(ISBN);
						System.out.println("livre rendu");
					}
					
				}
			}
		});
		btn.setBounds(137, 124, 88, 22);
		frame.getContentPane().add(btn);
	}
}
