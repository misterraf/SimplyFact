package com.rt.simplyFact;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

public class RecoveryList extends JDialog{
	private Cabinet cab=new Cabinet();
	private List<String> listeNoms=new ArrayList<String>();
	private ImageIcon btnIcon1 = new ImageIcon(getClass().getResource("/undo-icon.png"));
	private ImageIcon btnIcon2 = new ImageIcon(getClass().getResource("/Trash-empty-icon.png"));
	private ImageIcon btnIcon3 = new ImageIcon(getClass().getResource("/close.png"));
	private JList tableNoms=new JList();
	
	public RecoveryList(JFrame parent, String title, boolean modal){
		super(parent,title,modal);
		this.setSize(new Dimension(250,350));
		this.setLocation(150,150);
		this.setResizable(true);
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		this.initComponent();
	}
	private void initComponent(){
		BorderLayout b = new BorderLayout();
		
		this.setLayout(b);
		JPanel btnPanel=new JPanel();
		JButton btnDelete=new JButton(btnIcon2);
		JButton btnRecover=new JButton(btnIcon1);
		JButton btnClose=new JButton(btnIcon3);
		btnDelete.setToolTipText("Supprimer Definitivement Les Patients Selectionnes de la Base de Donnees");
		btnRecover.setToolTipText("Retablir Patients Selectionnes");
		btnPanel.add(btnRecover);
		btnPanel.add(btnDelete);
		btnPanel.add(btnClose);
		this.getContentPane().add(btnPanel,BorderLayout.SOUTH);
		btnClose.addActionListener(new CloseBtnListener());
		btnRecover.addActionListener(new RecoverBtnListener());
		btnDelete.addActionListener(new DeleteBtnListener());
		JScrollPane listScroll=new JScrollPane(tableNoms); 
		this.getContentPane().add(listScroll,BorderLayout.CENTER);
		tableNoms.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		
	}
	public List<String> showRecoveryList(){
		this.setVisible(true);
		return this.listeNoms;
		
	}
	public void setRecoveryInfo(List<String> listeNoms){
		this.listeNoms=listeNoms;
		DefaultListModel listModel=new DefaultListModel();
		tableNoms.setModel(listModel);
		for (int i=0;i<listeNoms.size();i++){
			listModel.addElement(listeNoms.get(i));
//			System.out.println("patient efface :"+listeNoms.get(i));
		}
		
		
		
	}
	class CloseBtnListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			listeNoms.clear();
			listeNoms.add("0");
			setVisible(false);
		}
	}
	class RecoverBtnListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			listeNoms.clear();
			if(tableNoms.getSelectedIndices().length>=0){
				for (int i=0;i<tableNoms.getSelectedIndices().length;i++){
//					System.out.println("i : "+i+" index "+tableNoms.getSelectedIndices()[i]);
					listeNoms.add(tableNoms.getModel().getElementAt(tableNoms.getSelectedIndices()[i]).toString());
					
				}
			}
			listeNoms.add("1");
			setVisible(false);
		}
	}
	class DeleteBtnListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			listeNoms.clear();
			if(tableNoms.getSelectedIndices().length>=0){
				for (int i=0;i<tableNoms.getSelectedIndices().length;i++){
					listeNoms.add(tableNoms.getModel().getElementAt(tableNoms.getSelectedIndices()[i]).toString());
				}
			}
			listeNoms.add("2");
			setVisible(false);
		}
	}
}
