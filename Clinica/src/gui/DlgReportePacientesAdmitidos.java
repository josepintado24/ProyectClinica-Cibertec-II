package gui;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import arreglos.ArregloCamas;
import arreglos.ArregloHospitalizacion;
import arreglos.ArregloPaciente;
import clases.Camas;
import clases.Internamiento;
import clases.Paciente;
import libreria.Libreria;
import java.awt.Color;
import java.awt.Font;
import java.awt.MouseInfo;
import java.awt.Point;

import javax.swing.JPanel;
import java.awt.Cursor;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class DlgReportePacientesAdmitidos extends JDialog {

	private static final long serialVersionUID = 1L;
	private JScrollPane scrollPane;
	private JTable jtblPacientes;
	private DefaultTableModel dtm;
	private int x;
	private int y;

	// DECLARACI�N GLOBAL DE ARREGLOS HOSPITALIZACI�N, CAMA Y PACIENTES
	ArregloPaciente ap = new ArregloPaciente("pacientes.txt");
	ArregloCamas ac = new ArregloCamas("camas.txt");
	ArregloHospitalizacion ah = new ArregloHospitalizacion("hospitalizaciones.txt");

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		try {
			DlgReportePacientesAdmitidos dialog = new DlgReportePacientesAdmitidos();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DlgReportePacientesAdmitidos() {
		setUndecorated(true);
		getContentPane().setBackground(Color.WHITE);
		setResizable(false);
		setModal(true);
		setBounds(100, 100, 826, 599);
		setTitle("PACIENTES ADMITIDOS");
		getContentPane().setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 50, 806, 538);
		getContentPane().add(scrollPane);

		dtm = new DefaultTableModel(null, getColumnas());
		jtblPacientes = new JTable(dtm) {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int rowIndex, int vColIndex) {
				return false;
			}

		};
		jtblPacientes.setFont(new Font("Arial", Font.BOLD, 14));
		jtblPacientes.setForeground(Color.BLACK);
		jtblPacientes.setRowHeight(25);
		jtblPacientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(jtblPacientes);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				Point ubicacion = MouseInfo.getPointerInfo().getLocation();
			    setLocation(ubicacion.x - x, ubicacion.y - y);
			}
		});
		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				x = e.getX();
			    y = e.getY();
			}
		});
		panel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		panel.setBounds(0, 0, 826, 39);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel label = new JLabel("");
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Icon m = new ImageIcon(getClass().getResource("/image/ADVERTENCIA.png"));
		  		int dialog = JOptionPane.YES_NO_OPTION;
		 		int result1 =JOptionPane.showConfirmDialog(null, "�Desea Volver a la Ventana Principal?","Abvertencia",dialog,dialog,m);
		 	
		  		
		  		if(result1 ==0){
		  			dispose();
		 			Principal frame = new Principal();
		 			frame.setVisible(true);
		  		}
			}
		});
		label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		label.setIcon(new ImageIcon(DlgReportePacientesAdmitidos.class.getResource("/image/icons8_Return_32px.png")));
		label.setBounds(786, 0, 40, 39);
		panel.add(label);
		
		JLabel lblReportePacientes = new JLabel("Reporte Pacientes ");
		lblReportePacientes.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblReportePacientes.setFont(new Font("Decker", Font.PLAIN, 16));
		lblReportePacientes.setBounds(38, 0, 146, 39);
		panel.add(lblReportePacientes);
		
		JLabel label_1 = new JLabel("");
		label_1.setIcon(new ImageIcon(DlgReportePacientesAdmitidos.class.getResource("/image/sale-report.png")));
		label_1.setBounds(10, 0, 25, 39);
		panel.add(label_1);

		formatoTabla();
		listadoPacientes();

		setLocationRelativeTo(this);
	}

	// M�TODOS VOID SIN PAR�METROS
	void formatoTabla() {
		TableColumnModel tc = jtblPacientes.getColumnModel();
		tc.getColumn(0).setPreferredWidth(50);
		tc.getColumn(1).setPreferredWidth(150);
		tc.getColumn(2).setPreferredWidth(150);
		tc.getColumn(3).setPreferredWidth(50);
		tc.getColumn(4).setPreferredWidth(100);
		tc.getColumn(5).setPreferredWidth(100);

		DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
		tcr.setHorizontalAlignment(SwingConstants.CENTER);
		tc.getColumn(0).setCellRenderer(tcr);
		tc.getColumn(3).setCellRenderer(tcr);
		tc.getColumn(4).setCellRenderer(tcr);

		tcr = new DefaultTableCellRenderer();
		tcr.setHorizontalAlignment(SwingConstants.RIGHT);
		tc.getColumn(5).setCellRenderer(tcr);
	}

	void listadoPacientes() {
		Internamiento h;
		for (int i = 0; i < ah.tama�o(); i++) {

			h = ah.obtener(i);
			if (h.getEstado() == 1) {
				Paciente p;
				for (int j = 0; j < ap.tama�o(); j++) {

					p = ap.obtener(j);
					if (p.getCodpaciente() == h.getCodPaci()) {

						Camas c;
						for (int j2 = 0; j2 < ac.tama�o(); j2++) {
							c = ac.obtener(j2);
							if (c.getNroCama() == h.getNumCama()) {
								dtm.addRow(new Object[] { p.getCodpaciente(), p.getNombres().toUpperCase(),
										p.getApellidos().toUpperCase(), h.getNumCama(), getCategoria(c.getCategoria()),
										Libreria.formato2Decimales(c.getPrecioxDia()) });
							}
						}

					}

				}
			}
		}
	}

	// M�TODOS CON RETORNO SIN PAR�METROS
	String[] getColumnas() {
		String columnas[] = new String[] { "C�DIGO", "NOMBRES", "APELLIDOS", "NRO CAMA", "CATEGOR�A",
				"PRECIO POR D�A" };
		return columnas;
	}

	// M�TODOS CON RETORNO CON PAR�METROS
	String getCategoria(int est) {
		if (est == 0) {
			return "ECON�MICO";
		}
		else {
			return "EJECUTIVO";
		}
	}
}
