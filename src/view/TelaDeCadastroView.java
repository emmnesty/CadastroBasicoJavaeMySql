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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import dao.Conexao;

@SuppressWarnings("serial")
public class TelaDeCadastroView extends JFrame {

	private JPanel contentPane;
	private JTextField tfId;
	private JTextField tfUsuario;
	private JPasswordField pfSenha;
	private JTextField tfBusca;
	private JTable tbDados;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaDeCadastroView frame = new TelaDeCadastroView();
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
	public TelaDeCadastroView() {
		setResizable(false);
		setTitle("Tela de Cadastro");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 342, 552);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblId = new JLabel("ID");
		lblId.setForeground(Color.DARK_GRAY);
		lblId.setFont(new Font("Arial", Font.BOLD, 14));
		lblId.setBounds(41, 40, 36, 14);
		contentPane.add(lblId);

		JLabel lblUsuario = new JLabel("Usu\u00E1rio");
		lblUsuario.setForeground(Color.DARK_GRAY);
		lblUsuario.setFont(new Font("Arial", Font.BOLD, 14));
		lblUsuario.setBounds(26, 91, 75, 14);
		contentPane.add(lblUsuario);

		JLabel lblNewLabel_2 = new JLabel("Senha");
		lblNewLabel_2.setForeground(Color.DARK_GRAY);
		lblNewLabel_2.setFont(new Font("Arial", Font.BOLD, 14));
		lblNewLabel_2.setBounds(26, 140, 46, 14);
		contentPane.add(lblNewLabel_2);

		tfId = new JTextField();
		tfId.setEditable(false);
		tfId.setBounds(82, 38, 86, 20);
		contentPane.add(tfId);
		tfId.setColumns(10);

		tfUsuario = new JTextField();
		tfUsuario.setBounds(82, 89, 190, 20);
		contentPane.add(tfUsuario);
		tfUsuario.setColumns(10);

		pfSenha = new JPasswordField();
		pfSenha.setBounds(82, 138, 190, 20);
		contentPane.add(pfSenha);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Cadastro", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBackground(Color.GRAY);
		panel.setBounds(26, 251, 267, 55);
		contentPane.add(panel);
		panel.setLayout(null);

		// INSERT

		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {

				if (tfUsuario.getText().equals("") || pfSenha.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Usuário/Senha em branco!");

				} else {
					try {
						Connection conn = Conexao.con();

						String sql = "insert into dados_senhas(usuario, senha) value (?, ?)";
						PreparedStatement stmt = conn.prepareStatement(sql);
						stmt.setString(1, tfUsuario.getText());
						stmt.setString(2, new String(pfSenha.getPassword()));

						stmt.execute();
						stmt.close();
						conn.close();

						JOptionPane.showMessageDialog(null, "Usuário Cadastrado!");
						tfUsuario.setText("");
						pfSenha.setText("");

					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		btnSalvar.setForeground(Color.DARK_GRAY);
		btnSalvar.setFont(new Font("Arial", Font.PLAIN, 12));
		btnSalvar.setBounds(10, 23, 89, 23);
		panel.add(btnSalvar);

		// LISTAR

		JButton btnListarDados = new JButton("Listar Dados");
		btnListarDados.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Connection conn = Conexao.con();

					String sql = "select *from dados_senhas";

					PreparedStatement stmt = conn.prepareStatement(sql);
					ResultSet rs = stmt.executeQuery();

					DefaultTableModel modelo = (DefaultTableModel) tbDados.getModel();
					modelo.setNumRows(0);

					while (rs.next()) {
						modelo.addRow(
								new Object[] { rs.getString("id"), rs.getString("usuario"), rs.getString("senha") });

					}
					rs.close();
					conn.close();

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		btnListarDados.setForeground(Color.DARK_GRAY);
		btnListarDados.setBounds(136, 23, 121, 23);
		panel.add(btnListarDados);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Pesquisar", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBackground(Color.GRAY);
		panel_1.setBounds(26, 317, 267, 51);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		tfBusca = new JTextField();
		tfBusca.setBounds(109, 20, 128, 20);
		panel_1.add(tfBusca);
		tfBusca.setColumns(10);

		// QUERY

		JButton btnPesquisar = new JButton("Pesquisar");
		btnPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tfBusca.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Informe um ID");
				} else {

					try {
						Connection conn = Conexao.con();

						String sql = "select *from dados_senhas where id like ?";

						PreparedStatement stmt = conn.prepareStatement(sql);

						stmt.setString(1, "%" + tfBusca.getText());
						ResultSet rs = stmt.executeQuery();

						while (rs.next()) {
							tfId.setText(rs.getString("id"));
							tfUsuario.setText(rs.getString("usuario"));
							pfSenha.setText(rs.getString("senha"));

						}

						rs.close();
						conn.close();

					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		btnPesquisar.setForeground(Color.DARK_GRAY);
		btnPesquisar.setBounds(10, 19, 89, 23);
		panel_1.add(btnPesquisar);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(26, 165, 267, 78);
		contentPane.add(scrollPane);

		tbDados = new JTable();
		tbDados.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "ID", "Usuario", "Senha" }) {
			boolean[] columnEditables = new boolean[] { false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		scrollPane.setViewportView(tbDados);
	}
}
