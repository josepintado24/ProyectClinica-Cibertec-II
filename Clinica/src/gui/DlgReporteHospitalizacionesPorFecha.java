package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import arreglos.ArregloCamas;
import arreglos.ArregloEmpleado;
import arreglos.ArregloHospitalizacion;
import arreglos.ArregloPaciente;
import clases.Empleado;
import clases.Internamiento;
import clases.Paciente;
import libreria.Fecha;
import libreria.Libreria;
import javax.swing.JPanel;
import java.awt.Cursor;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseListener;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

public class DlgReporteHospitalizacionesPorFecha extends JDialog implements ActionListener, MouseListener {

	private static final long serialVersionUID = 1L;
	private JScrollPane scrollPane;
	private JTable jtblHospitalizaciones;
	private JLabel lblFechaLlegada;
	private JComboBox<String> cboDia;
	private JComboBox<String> cboMes;
	private JComboBox<String> cboA�o;
	private DefaultTableModel dtm;
	private int x;
	private int y;

	// DECLARACI�N GLOBAL DE ARREGLOS
	ArregloPaciente ap = new ArregloPaciente("pacientes.txt");
	ArregloCamas ac = new ArregloCamas("camas.txt");
	ArregloHospitalizacion ah = new ArregloHospitalizacion("hospitalizaciones.txt");
	ArregloEmpleado ae = new ArregloEmpleado("empleados.txt");
	private JPanel panel;
	private JLabel label_1;
	private JLabel lblR;
	private JButton btnBuscar;
	private JLabel label;
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		try {
			DlgReporteHospitalizacionesPorFecha dialog = new DlgReporteHospitalizacionesPorFecha();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DlgReporteHospitalizacionesPorFecha() {
		setUndecorated(true);
		setResizable(false);
		setModal(true);
		getContentPane().setBackground(Color.WHITE);
		setTitle("BUSCAR HOSPITALIZACIONES");
		setBounds(100, 100, 1069, 515);
		getContentPane().setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 116, 1044, 388);
		getContentPane().add(scrollPane);

		dtm = new DefaultTableModel(null, getColumnas());
		jtblHospitalizaciones = new JTable(dtm) {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int rowIndex, int vColIndex) {
				return false;
			}
		};
		jtblHospitalizaciones.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jtblHospitalizaciones.setFont(new Font("Arial", Font.BOLD, 14));
		scrollPane.setViewportView(jtblHospitalizaciones);
		
		lblFechaLlegada = new JLabel("FECHA LLEGADA :");
		lblFechaLlegada.setHorizontalAlignment(SwingConstants.LEFT);
		lblFechaLlegada.setForeground(Color.BLACK);
		lblFechaLlegada.setFont(new Font("Source Sans Pro Semibold", Font.PLAIN, 14));
		lblFechaLlegada.setBounds(10, 50, 149, 36);
		getContentPane().add(lblFechaLlegada);
		
		cboDia = new JComboBox<String>();
		cboDia.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cboDia.setForeground(Color.BLACK);
		cboDia.setFont(new Font("Source Sans Pro Semibold", Font.PLAIN, 14));
		cboDia.setBounds(199, 50, 59, 38);
		Fecha.colocarItems(cboDia, 1, 31);
		Fecha.colocarDiaActual(cboDia);
		getContentPane().add(cboDia);
		
		cboMes = new JComboBox<String>();
		cboMes.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cboMes.setForeground(Color.BLACK);
		cboMes.setFont(new Font("Source Sans Pro Semibold", Font.PLAIN, 14));
		cboMes.setBounds(259, 50, 116, 38);
		Fecha.colocarMeses(cboMes);
		Fecha.colocarMesActual(cboMes);
		getContentPane().add(cboMes);
		
		cboA�o = new JComboBox<String>();
		cboA�o.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cboA�o.setForeground(Color.BLACK);
		cboA�o.setFont(new Font("Source Sans Pro Semibold", Font.PLAIN, 14));
		cboA�o.setBounds(376, 50, 83, 38);
		Fecha.colocarItems(cboA�o, 2000, Fecha.a�oActual());
		cboA�o.setSelectedIndex(16);
		getContentPane().add(cboA�o);
		
		panel = new JPanel();
		panel.setBorder(new MatteBorder(0, 0, 1, 0, (Color) Color.LIGHT_GRAY));
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
		panel.setBounds(0, 0, 1069, 39);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		label_1 = new JLabel("");
		label_1.addMouseListener(new MouseAdapter() {
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
		label_1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		label_1.setIcon(new ImageIcon(DlgReporteHospitalizacionesPorFecha.class.getResource("/image/icons8_Return_32px.png")));
		label_1.setBounds(1030, 0, 39, 39);
		panel.add(label_1);
		
		lblR = new JLabel("Reporte Hospital");
		lblR.setBorder(new MatteBorder(0, 0, 0, 0, (Color) new Color(192, 192, 192)));
		lblR.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblR.setFont(new Font("Decker", Font.PLAIN, 16));
		lblR.setBounds(36, 0, 134, 39);
		panel.add(lblR);
		
		label = new JLabel("");
		label.setIcon(new ImageIcon(DlgReporteHospitalizacionesPorFecha.class.getResource("/image/analytics2.png")));
		label.setBounds(8, 0, 25, 39);
		panel.add(label);
		
		btnBuscar = new JButton("Buscar");
		btnBuscar.setRolloverIcon(new ImageIcon(DlgReporteHospitalizacionesPorFecha.class.getResource("/iconBotones/buscarBlnco.png")));
		btnBuscar.setIcon(new ImageIcon(DlgReporteHospitalizacionesPorFecha.class.getResource("/iconBotones/buscar.png")));
		btnBuscar.setContentAreaFilled(false);
		btnBuscar.addMouseListener(this);
		btnBuscar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnBuscar.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		btnBuscar.addActionListener(this);
		btnBuscar.setBounds(490, 50, 155, 36);
		getContentPane().add(btnBuscar);
		
		modeloTabla();
		listar(0);
		
		setLocationRelativeTo(this);
	}
	
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == btnBuscar) {
			actionPerformedBtnBuscar(arg0);
		}
	}

	// M�TODOS VOID CON PAR�METROS
	void listar(int fecha) {
		Libreria.limpiarJTable(jtblHospitalizaciones, dtm);
		Internamiento h;
		for (int i = 0; i < ah.tama�o(); i++) {
			h = ah.obtener(i);

			Paciente p;
			for (int j = 0; j < ap.tama�o(); j++) {
				p = ap.obtener(j);
				if (h.getCodPaci() == p.getCodpaciente()) {

					Empleado e;
					for (int j2 = 0; j2 < ae.tama�o(); j2++) {
						e = ae.obtener(j2);
						if (e.getCodEmpleado() == h.getCodEmpl()) {
							if (fecha != 0) {
								if (h.getFecLleg() == fecha ) {
									dtm.addRow(new Object[] { h.getCodHosp(), p.getNombres().toUpperCase(),
											p.getApellidos().toUpperCase(), e.getNombres().toUpperCase(),
											e.getApellidos().toUpperCase(), h.getNumCama(), 
											Fecha.dd_mm_aaaa(h.getFecLleg()), Fecha.hh_mm_ss(h.getHorLleg())});
								}
							}else {
								dtm.addRow(new Object[] { h.getCodHosp(), p.getNombres().toUpperCase(),
										p.getApellidos().toUpperCase(), e.getNombres().toUpperCase(),
										e.getApellidos().toUpperCase(), h.getNumCama(), 
										Fecha.dd_mm_aaaa(h.getFecLleg()), Fecha.hh_mm_ss(h.getHorLleg())});
							}
						}
					}
				}
			}
		}
	}
	
	// M�TODOS VOID SIN PAR�METROS
	void modeloTabla() {
		TableColumnModel tc = jtblHospitalizaciones.getColumnModel();
		tc.getColumn(0).setPreferredWidth(60);
		tc.getColumn(1).setPreferredWidth(150);
		tc.getColumn(2).setPreferredWidth(150);
		tc.getColumn(3).setPreferredWidth(150);
		tc.getColumn(4).setPreferredWidth(150);
		tc.getColumn(5).setPreferredWidth(60);
		tc.getColumn(6).setPreferredWidth(100);
		tc.getColumn(7).setPreferredWidth(100);
		
		DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
		tcr.setHorizontalAlignment(SwingConstants.CENTER);
		tc.getColumn(0).setCellRenderer(tcr);
		tc.getColumn(5).setCellRenderer(tcr);
		tc.getColumn(6).setCellRenderer(tcr);
		tc.getColumn(7).setCellRenderer(tcr);
	}

	// M�TODOS CON RETORNO SIN PAR�METROS
	String[] getColumnas() {
		String columnas[] = new String[] { "C�DIGO", "NOMBRES PACIENTE", "APELLIDOS PACIENTE", "NOMBRE EMPLEADO",
				"APELLIDO EMPLEADO", "NRO CAMA", "FECHA LLEGADA", "HORA LLEGADA" };
		return columnas;
	}
	public void mouseClicked(MouseEvent arg0) {
	}
	public void mouseEntered(MouseEvent e) {
		if (e.getSource() == btnBuscar) {
			mouseEnteredBtnBuscar(e);
		}
	}
	public void mouseExited(MouseEvent e) {
		if (e.getSource() == btnBuscar) {
			mouseExitedBtnBuscar(e);
		}
		btnBuscar.setOpaque(false);
		btnBuscar.setForeground(new Color(0, 0, 1));
		
		
	}
	public void mousePressed(MouseEvent e) {
	}
	public void mouseReleased(MouseEvent e) {
	}
	protected void actionPerformedBtnBuscar(ActionEvent arg0) {
		listar(Fecha.getFecha(cboDia, cboMes, cboA�o));
	}
	protected void mouseEnteredBtnBuscar(MouseEvent e) {
		btnBuscar.setOpaque(true);
		btnBuscar.setBackground(new Color(0, 139, 173));
		btnBuscar.setForeground(new Color(255, 255, 255));
		
		
	}
	protected void mouseExitedBtnBuscar(MouseEvent e) {
		btnBuscar.setOpaque(false);
		btnBuscar.setForeground(new Color(0, 0, 1));
		
		btnBuscar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 1)));
	}
}
