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
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
		JLabel lblNumber = new JLabel("Number");
		JLabel lblInputBase = new JLabel("Input Base");
		JLabel lblTo = new JLabel("to");
		JLabel lblOutputBase = new JLabel("Output Base");
		JLabel lblLogOfResults = new JLabel("Log of Results");
		
		JButton btnCalculate = new JButton("Calculate!");
		
		txtNumber = new JTextField();
		txtInputBase = new JTextField();
		txtOutputBase = new JTextField();
		
		textArea = new JTextArea();
		
		contentPane = new JPanel();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700	, 500);
		setContentPane(contentPane);
		
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		contentPane.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("422px:grow"),},
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
				RowSpec.decode("default:grow"),}));
		
		contentPane.add(lblWelcomeToRational, "4, 2, fill, top");
		contentPane.add(lblNumber, "2, 4, right, default");
		contentPane.add(txtNumber, "4, 4, fill, default");
		contentPane.add(lblInputBase, "2, 6, right, default");
		contentPane.add(txtInputBase, "4, 6, fill, default");
		contentPane.add(lblTo, "4, 8");
		contentPane.add(lblOutputBase, "2, 10, right, default");
		contentPane.add(txtOutputBase, "4, 10, fill, default");
		contentPane.add(lblLogOfResults, "4, 16");
		contentPane.add(textArea, "4, 18, fill, fill");
		contentPane.add(btnCalculate, "4, 12");
		
		txtNumber.setColumns(10);
		txtInputBase.setColumns(10);
		txtOutputBase.setColumns(10);
		textArea.setEditable(false);
		
		btnCalculate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				CalculateResult();
			}
		});
		
	}
	
	public void CalculateResult(){
		String textToAdd = txtNumber.getText() + " in base " + txtInputBase.getText() + " is \n" + result + " in base " + txtOutputBase.getText() + "\n\n";
		textInLog = textToAdd + textInLog;
		textArea.setText(textInLog);
	}

}
