package appletUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.RowSpec;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.Font;

public class MainApplet extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextArea textArea;
	private JTextField txtNumber;
	private JTextField txtInputBase;
	private JTextField txtOutputBase;
	private JComboBox variableNamesComboBox;
	private String textInLog = "";
	private String result = "sample";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainApplet frame = new MainApplet();
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
	public MainApplet() {
		JLabel lblWelcomeToRational = new JLabel("Rational Number Representations");
		lblWelcomeToRational.setFont(new Font("Tahoma", Font.PLAIN, 20));
		JLabel lblNumber = new JLabel("Number");
		JLabel lblOutputBase = new JLabel("Output Base");
		
		textArea = new JTextArea();
		
		contentPane = new JPanel();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700	, 500);
		setContentPane(contentPane);
		
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		contentPane.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(119dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(15dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(31dlu;default)"),
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("169px:grow"),},
			new RowSpec[] {
				FormFactory.LINE_GAP_ROWSPEC,
				RowSpec.decode("16px"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),}));
		
		contentPane.add(lblWelcomeToRational, "4, 2, 7, 2, fill, top");
		contentPane.add(lblNumber, "2, 4, right, default");
		
		txtNumber = new JTextField();
		contentPane.add(txtNumber, "4, 4, fill, default");
		
		txtNumber.setColumns(10);
		
		JLabel lblOr = new JLabel("OR");
		lblOr.setFont(new Font("Tahoma", Font.PLAIN, 15));
		contentPane.add(lblOr, "6, 4");
		
		JLabel lblVariable = new JLabel("Variable:");
		contentPane.add(lblVariable, "8, 4, right, default");
		
		variableNamesComboBox = new JComboBox();
		variableNamesComboBox.setToolTipText("Available variables");
		contentPane.add(variableNamesComboBox, "10, 4, fill, default");
		JLabel lblInputBase = new JLabel("Input Base");
		contentPane.add(lblInputBase, "2, 6, right, default");
		txtInputBase = new JTextField();
		contentPane.add(txtInputBase, "4, 6, fill, default");
		txtInputBase.setColumns(10);
		JLabel lblTo = new JLabel("to");
		lblTo.setFont(new Font("Tahoma", Font.BOLD, 20));
		contentPane.add(lblTo, "6, 10");
		contentPane.add(lblOutputBase, "2, 14, right, default");
		txtOutputBase = new JTextField();
		contentPane.add(txtOutputBase, "4, 14, 7, 1, fill, default");
		txtOutputBase.setColumns(10);
		
		JButton btnCalculate = new JButton("Calculate!");
		contentPane.add(btnCalculate, "4, 16");
		
		btnCalculate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				CalculateResult();
			}
		});
		
		JButton btnSaveToVariable = new JButton("Save to Variable");
		btnSaveToVariable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String result = JOptionPane.showInputDialog("Specify Name of Variable");
				int stringIndex = 0;
				if((result != null) && (!result.isEmpty())){
					String[] newComboBoxModel = new String[variableNamesComboBox.getModel().getSize()+1];
					while(stringIndex < variableNamesComboBox.getModel().getSize()){
						newComboBoxModel[stringIndex] = (String) variableNamesComboBox.getModel().getElementAt(stringIndex);
						stringIndex++;
					}
					newComboBoxModel[stringIndex] = result;
					variableNamesComboBox.setModel(new DefaultComboBoxModel(newComboBoxModel));
				}
				
			}
		});
		contentPane.add(btnSaveToVariable, "8, 16, 3, 1");
		JLabel lblLogOfResults = new JLabel("Log of Results");
		contentPane.add(lblLogOfResults, "4, 20");
		contentPane.add(textArea, "4, 22, 7, 1, fill, fill");
		textArea.setEditable(false);
		
	}
	
	public void CalculateResult(){
		String textToAdd = txtNumber.getText() + " in base " + txtInputBase.getText() + " is \n" + result + " in base " + txtOutputBase.getText() + "\n\n";
		textInLog = textToAdd + textInLog;
		textArea.setText(textInLog);
	}

}