package controller;


import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import model.Modifier;
import model.PrimitiveType;
import model.UmlMethod;
import model.UmlParams;
import model.UmlRefType;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import exception.ExceptionMethode;
import exception.ExceptionModifier;

import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JCheckBox;

public class MethodAddController extends JDialog {
	
	private Set<UmlParams> umlParams;
	
	public MethodAddController(UmlRefType umlRef) {
		
		this.umlParams = new HashSet<>();
		
		JDialog myself = this;
		
		this.setTitle("Add a method");
		
		getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,}));
		
		JLabel lblName = new JLabel("Name : ");
		getContentPane().add(lblName, "4, 6, right, default");
		
		JTextField nameMethodtextField = new JTextField();
		getContentPane().add(nameMethodtextField, "8, 6, 3, 1, fill, default");
		nameMethodtextField.setColumns(10);

		JLabel lblParameters = new JLabel("Parameters : ");
		getContentPane().add(lblParameters, "4, 10");
		
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Name");
        model.addColumn("Type");
        JTable table = new JTable(model);
                
		JScrollPane scrollPane = new JScrollPane(table);
        getContentPane().add(scrollPane, "8, 10, 3, 5, default, default");

		JButton btnAddParams = new JButton("Add params");
		getContentPane().add(btnAddParams, "8, 16");
		
		JButton btnRemoveParams = new JButton("Remove params");
		btnRemoveParams.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (table.getSelectedRow() != -1) {
					removeParameter((String)table.getValueAt(table.getSelectedRow(), 0));
					model.removeRow(table.getSelectedRow());
				}
			}
			
		});
		
		getContentPane().add(btnRemoveParams, "10, 16");
		
		JLabel lblModifiers = new JLabel("Modifiers :");
		getContentPane().add(lblModifiers, "4, 18");
		
		JCheckBox chckbxFinal = new JCheckBox("final");
		getContentPane().add(chckbxFinal, "8, 18");
		
		JCheckBox chckbxStatic = new JCheckBox("static");
		getContentPane().add(chckbxStatic, "10, 18");
		
		JCheckBox chckbxAbstract = new JCheckBox("abstract");
		getContentPane().add(chckbxAbstract, "8, 20");
		
		JLabel lblReturnType = new JLabel("Return type :");
		getContentPane().add(lblReturnType, "4, 22");
		
		JComboBox<String> choice = new JComboBox<>();
		getContentPane().add(choice, "8, 22, 3, 1");
		
		JButton btnCancel = new JButton("Cancel");
		getContentPane().add(btnCancel, "4, 26");
		
		btnCancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
			
		});
		
		JButton btnAddThisMethod = new JButton("Add this method");
		getContentPane().add(btnAddThisMethod, "10, 26");
		
		btnAddThisMethod.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				UmlMethod newMethod = new UmlMethod(nameMethodtextField.getText());
				newMethod.addParams(umlParams);
				
				try {
					
					addModifier(chckbxAbstract, newMethod, Modifier.ABSTRACT);
					addModifier(chckbxStatic, newMethod, Modifier.STATIC);
					addModifier(chckbxFinal, newMethod, Modifier.FINAL);

				} catch (ExceptionModifier e1) {
						JOptionPane.showMessageDialog(myself, "Error: "+e1.getMessage(), "Can't add this modifier", JOptionPane.ERROR_MESSAGE);
				}
				
				newMethod.setReturnType(PrimitiveType.valueOf((String)choice.getSelectedItem()));
				try {
					umlRef.addMethod(newMethod);
					dispose();
				} catch (ExceptionMethode e1) {
					JOptionPane.showMessageDialog(myself, "Error: "+e1.getMessage(), "Can't add method", JOptionPane.ERROR_MESSAGE);
				}
				
			}
			
		});
		
		
		btnAddParams.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				JDialog dialog = new JDialog(myself);

				
				dialog.getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
						FormSpecs.RELATED_GAP_COLSPEC,
						FormSpecs.DEFAULT_COLSPEC,
						FormSpecs.RELATED_GAP_COLSPEC,
						FormSpecs.DEFAULT_COLSPEC,
						FormSpecs.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("default:grow"),
						FormSpecs.RELATED_GAP_COLSPEC,
						FormSpecs.DEFAULT_COLSPEC,
						FormSpecs.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("default:grow"),},
					new RowSpec[] {
						FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC,}));
				
				JLabel lblName = new JLabel("Name");
				dialog.getContentPane().add(lblName, "2, 4, right, default");
				
				JTextField nameTextField = new JTextField();
				dialog.getContentPane().add(nameTextField, "6, 4, 5, 1, fill, default");
				nameTextField.setColumns(10);
				
				JLabel lblType = new JLabel("Type");
				dialog.getContentPane().add(lblType, "2, 6, right, default");
				
				JComboBox<String> typeComboBox = new JComboBox<>();
				initializeType(typeComboBox);
				dialog.getContentPane().add(typeComboBox, "6, 6, 5, 1, fill, default");

				
				JButton btnCancel = new JButton("Cancel");
				dialog.getContentPane().add(btnCancel, "6, 8");
				btnCancel.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
					
				});
				
				JButton btnAddParam = new JButton("Add this param");
				dialog.getContentPane().add(btnAddParam, "10, 8");
				btnAddParam.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						UmlParams umlParam = new UmlParams(PrimitiveType.valueOf((String)typeComboBox.getSelectedItem()), nameTextField.getText());
						umlParams.add(umlParam);
						model.addRow(new Object[]{umlParam.getName(), umlParam.getType().getTypeName()});				
						dialog.dispose();
					}
					
				});
				
				dialog.setLocationRelativeTo(null);
				dialog.pack();
				dialog.setVisible(true);
				
			}
			
		});
		
		initializeType(choice);
		
		
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);


	}
	
	
	private void initializeType(JComboBox<String> combo) {
		for (PrimitiveType type : PrimitiveType.values()) {
			combo.addItem(type.name());
		}
	}
	
	private void addModifier(JCheckBox checkBox, UmlMethod umlMethod, Modifier modifier) throws ExceptionModifier {
		if (checkBox.isSelected()) {
			umlMethod.addModifier(modifier);
		}
	}
	
	
	private void removeParameter(String methodName) {
		
		UmlParams found = null;
				
		UmlParams[] tab = umlParams.toArray(new UmlParams[umlParams.size()]);
		
		for (int i = 0; i < tab.length && found == null; i++) {

			if (tab[i].getName().equals(methodName)) {
				found = tab[i];
			}
		}
		
		if (found != null) {
			umlParams.remove(found);
		}
		
		
	}

	
}
