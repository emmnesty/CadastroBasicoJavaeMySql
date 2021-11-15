package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import dao.Conexao;



public class TelaDeLoginView extends JFrame {

	private JPanel contentPane;
	private JTextField tfUsuario;
	private JPasswordField pfSenha;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaDeLoginView frame = new TelaDeLoginView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TelaDeLoginView() {
		setResizable(false);
		setTitle("Tela de Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 381, 319);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblUsuario = new JLabel("Usu\u00E1rio");
		lblUsuario.setForeground(Color.DARK_GRAY);
		lblUsuario.setFont(new Font("Arial", Font.BOLD, 14));
		lblUsuario.setBounds(32, 57, 132, 42);
		contentPane.add(lblUsuario);

		JLabel lblSenha = new JLabel("Senha");
		lblSenha.setForeground(Color.DARK_GRAY);
		lblSenha.setFont(new Font("Arial", Font.BOLD, 14));
		lblSenha.setBounds(32, 116, 132, 42);
		contentPane.add(lblSenha);

		tfUsuario = new JTextField();
		tfUsuario.setFont(new Font("Arial", Font.PLAIN, 12));
		tfUsuario.setBounds(99, 69, 198, 20);
		contentPane.add(tfUsuario);
		tfUsuario.setColumns(10);

		pfSenha = new JPasswordField();
		pfSenha.setFont(new Font("Arial", Font.PLAIN, 12));
		pfSenha.setBounds(99, 128, 198, 20);
		contentPane.add(pfSenha);

		// Orientado ao evento de clicar no botão. Criar o método para o comando Select
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Connection conn = Conexao.con();
					String sql = "select *from dados_Senhas where usuario=? and senha=?";
					PreparedStatement stmt = conn.prepareStatement(sql);
					stmt.setString(1, tfUsuario.getText());
					stmt.setString(2, new String(pfSenha.getPassword()));
					
					ResultSet rs = stmt.executeQuery();
					
					if (rs.next()) {
						TelaDeCadastroView exibir = new TelaDeCadastroView();
						exibir.setVisible(true);
						setVisible (false);
					}
					else {
						JOptionPane.showMessageDialog(null, "Usuário/Senha Inválido!");
					}
					stmt.close();
					conn.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnLogin.setBackground(Color.DARK_GRAY);
		btnLogin.setFont(new Font("Arial", Font.PLAIN, 12));
		btnLogin.setBounds(144, 182, 89, 23);
		contentPane.add(btnLogin);
	}
}
