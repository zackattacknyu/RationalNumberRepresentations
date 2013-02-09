package appletUI;

import ioMethods.IntegerHelper;
import ioMethods.ProgramInstance;
import ioMethods.StringHelper;

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
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.math.BigInteger;

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
	private ProgramInstance instance;

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
		instance = new ProgramInstance();
		
		JLabel lblWelcomeToRational = new JLabel("Rational Number Representations");
		lblWelcomeToRational.setFont(new Font("Tahoma", Font.PLAIN, 20));
		JLabel lblNumber = new JLabel("Number");
		JLabel lblOutputBase = new JLabel("Output Base");
		
		textArea = new JTextArea();
		
		contentPane = new JPanel();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 870	, 728);
		setContentPane(contentPane);
		
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		contentPane.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(151dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(31dlu;default)"),
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
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),}));
		
		contentPane.add(lblWelcomeToRational, "4, 2, 7, 4, center, top");
		
		JButton btnHelp = new JButton("Help");
		btnHelp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				showHelpMessage();
			}
		});
		contentPane.add(btnHelp, "2, 4");
		contentPane.add(lblNumber, "2, 6, right, default");
		
		txtNumber = new JTextField();
		contentPane.add(txtNumber, "4, 6, fill, default");
		
		txtNumber.setColumns(10);
		
		JLabel lblOr = new JLabel("OR");
		lblOr.setFont(new Font("Tahoma", Font.PLAIN, 15));
		contentPane.add(lblOr, "6, 6, center, default");
		
		JLabel lblVariable = new JLabel("Variable:");
		contentPane.add(lblVariable, "8, 6, right, default");
		
		variableNamesComboBox = new JComboBox();
		variableNamesComboBox.setModel(new DefaultComboBoxModel(new String[] {"<NONE>"}));
		variableNamesComboBox.setToolTipText("Available variables");
		contentPane.add(variableNamesComboBox, "10, 6, fill, default");
		JLabel lblInputBase = new JLabel("Input Base");
		contentPane.add(lblInputBase, "2, 8, right, default");
		txtInputBase = new JTextField();
		contentPane.add(txtInputBase, "4, 8, fill, default");
		txtInputBase.setColumns(10);
		JLabel lblTo = new JLabel("to");
		lblTo.setFont(new Font("Tahoma", Font.BOLD, 20));
		contentPane.add(lblTo, "6, 12, center, default");
		contentPane.add(lblOutputBase, "2, 16, right, default");
		txtOutputBase = new JTextField();
		contentPane.add(txtOutputBase, "4, 16, 7, 1, fill, default");
		txtOutputBase.setColumns(10);
		
		JButton btnCalculate = new JButton("Calculate new Representation");
		contentPane.add(btnCalculate, "4, 18");
		
		btnCalculate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				CalculateResult();
			}
		});
		
		JButton btnSaveToVariable = new JButton("Save Rational Number to Variable");
		btnSaveToVariable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				addVariable();
				
			}
		});
		contentPane.add(btnSaveToVariable, "8, 18, 3, 1");
		JLabel lblLogOfResults = new JLabel("Log of Results");
		contentPane.add(lblLogOfResults, "4, 22");
		contentPane.add(textArea, "4, 24, 7, 1, fill, fill");
		textArea.setEditable(false);
		
	}
	
	public void showHelpMessage(){
		String helpMessage = "The rational number can be entered in the following formats:\n" + 
				"Integer Format: <Integer>\n" +
				"Decimal Format: <IntegerPart>.<InitialPart>_<RepeatingPart>  OR\n" + 
				"                <IntegerPart>.<TerminatingPart>\n" + 
				"Fraction Format: <IntegerPart> <Numerator>/<Denominator>  OR\n" + 
				"                 <Numerator>/<Denominator>\n\n" +
				"The Input Base and Output Base can be between 2 and 36.\n" + 
				"If the base is greater than 10, the numbers 11-36 are represented by A-Z\n\n" + 
				"If a variable is specified, the rational number saved to that variable\n" + 
				"is used as the input and its representation using the output base is computed";
		JOptionPane.showMessageDialog(this, helpMessage);
	}
	
	public void CalculateResult(){
		if(!IntegerHelper.isInteger(txtInputBase.getText())){
			JOptionPane.showMessageDialog(this,"Invalid Input Base. Must be Integer");
			return;
		}
		
		if(!IntegerHelper.isInteger(txtOutputBase.getText())){
			JOptionPane.showMessageDialog(this,"Invalid Output Base. Must be Integer");
			return;
		}
		
		String textToAdd = instance.getLogResult(txtNumber.getText(), new BigInteger(txtInputBase.getText()), 
				variableNamesComboBox.getSelectedItem().toString(), new BigInteger(txtOutputBase.getText())); 
		textToAdd += "\n\n";
		textInLog = textToAdd + textInLog;
		textArea.setText(textInLog);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addVariable(){
		String result = JOptionPane.showInputDialog("Specify Name of Variable");
		int stringIndex = 0;
		if((result != null) && (!result.isEmpty())){
			
			String[] buttonStrings = {"Yes","No"};

			//variable name was already saved
			if(instance.getSavedVariables().containsKey(result)){
				
				int option = JOptionPane.showOptionDialog(this,"Variable name in use already. Overwrite current value?","Variable name used already",
					    JOptionPane.YES_OPTION,JOptionPane.NO_OPTION,null, buttonStrings,null);
				if(option == JOptionPane.YES_OPTION){
					instance.saveLastResult(result);
				}
				
			}else if(StringHelper.validVariableName(result)){
				
				//makes the array to use as the new combo box model
				String[] newComboBoxModel = new String[variableNamesComboBox.getModel().getSize()+1];
				while(stringIndex < variableNamesComboBox.getModel().getSize()){
					newComboBoxModel[stringIndex] = (String) variableNamesComboBox.getModel().getElementAt(stringIndex);
					stringIndex++;
				}
				
				//adds the new variable to the combo box model, i.e. list of values
				newComboBoxModel[stringIndex] = result;
				variableNamesComboBox.setModel(new DefaultComboBoxModel(newComboBoxModel));
				
				//add the variable to the program instance
				instance.saveLastResult(result);
				
			}
			else{
				JOptionPane.showMessageDialog(this,"Invalid Variable Name");
			}
		}
	}

}
