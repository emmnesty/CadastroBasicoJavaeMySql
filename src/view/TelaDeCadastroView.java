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
import javax.swing.border.EtchedBorder;
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
	private JButton btnDeletar;

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
		setBounds(100, 100, 458, 522);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblId = new JLabel("ID");
		lblId.setForeground(Color.DARK_GRAY);
		lblId.setFont(new Font("Arial", Font.BOLD, 14));
		lblId.setBounds(72, 77, 36, 14);
		contentPane.add(lblId);

		JLabel lblUsuario = new JLabel("Usu\u00E1rio");
		lblUsuario.setForeground(Color.DARK_GRAY);
		lblUsuario.setFont(new Font("Arial", Font.BOLD, 14));
		lblUsuario.setBounds(62, 120, 67, 14);
		contentPane.add(lblUsuario);

		JLabel lblNewLabel_2 = new JLabel("Senha");
		lblNewLabel_2.setForeground(Color.DARK_GRAY);
		lblNewLabel_2.setFont(new Font("Arial", Font.BOLD, 14));
		lblNewLabel_2.setBounds(62, 158, 46, 14);
		contentPane.add(lblNewLabel_2);

		tfId = new JTextField();
		tfId.setEditable(false);
		tfId.setBounds(118, 75, 86, 20);
		contentPane.add(tfId);
		tfId.setColumns(10);

		tfUsuario = new JTextField();
		tfUsuario.setBounds(118, 118, 190, 20);
		contentPane.add(tfUsuario);
		tfUsuario.setColumns(10);

		pfSenha = new JPasswordField();
		pfSenha.setBounds(118, 156, 190, 20);
		contentPane.add(pfSenha);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"Cadastro de Usu\u00E1rio", TitledBorder.RIGHT, TitledBorder.TOP, null, new Color(64, 64, 64)));
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setBounds(41, 319, 348, 85);
		contentPane.add(panel);
		panel.setLayout(null);

		// INSERT

		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {

				if (tfUsuario.getText().equals("") || pfSenha.getText().equals(""))
					JOptionPane.showMessageDialog(null, "Usuário/Senha em branco!");
				if (tfUsuario.getText().equals(tfId))
					JOptionPane.showMessageDialog(null, "Usuário/Senha Existente!");

				else {
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
						tfId.setText("");
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
		btnSalvar.setBounds(34, 23, 89, 23);
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
		btnListarDados.setBounds(187, 23, 126, 23);
		panel.add(btnListarDados);

		// Atualizar

		JButton btnAtualizar = new JButton("Atualizar Dados");
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (tfId.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Informe o ID");
				} else {
					try {
						Connection conn = Conexao.con();
						String sql = "update dados_senhas set usuario=?, senha=? where id =?";

						PreparedStatement stmt = conn.prepareStatement(sql);
						stmt.setString(1, tfUsuario.getText());
						stmt.setString(2, pfSenha.getText());
						stmt.setString(3, tfId.getText());

						stmt.execute();
						stmt.close();
						conn.close();

						JOptionPane.showMessageDialog(null, "Dados Atualizados!");

					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}
			}
		});

		btnAtualizar.setForeground(Color.DARK_GRAY);
		btnAtualizar.setBounds(187, 51, 126, 23);
		panel.add(btnAtualizar);

		btnDeletar = new JButton("Deletar");
		btnDeletar.setForeground(Color.DARK_GRAY);
		btnDeletar.setBounds(34, 51, 89, 23);
		panel.add(btnDeletar);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"Pesquisar Usu\u00E1rio", TitledBorder.RIGHT, TitledBorder.TOP, null, new Color(64, 64, 64)));
		panel_1.setBackground(Color.LIGHT_GRAY);
		panel_1.setBounds(41, 415, 348, 51);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		tfBusca = new JTextField();
		tfBusca.setBounds(164, 20, 158, 20);
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

						stmt.setString(1, "" + tfBusca.getText());
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
		btnPesquisar.setBounds(27, 19, 103, 23);
		panel_1.add(btnPesquisar);

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"Informa\u00E7\u00F5es de Login", TitledBorder.TRAILING, TitledBorder.TOP, null,
				new Color(64, 64, 64)));
		panel_2.setBounds(40, 55, 349, 142);
		contentPane.add(panel_2);

		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"Informa\u00E7\u00F5es de Pesquisa", TitledBorder.RIGHT, TitledBorder.TOP, null, Color.DARK_GRAY));
		panel_3.setBounds(41, 197, 349, 123);
		contentPane.add(panel_3);
		panel_3.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(21, 22, 318, 89);
		panel_3.add(scrollPane);

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
