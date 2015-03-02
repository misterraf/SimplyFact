package com.rt.simplyFact;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

//CTRL + SHIFT + O pour générer les imports
public class ButtonRenderer extends JButton implements TableCellRenderer{

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean isFocus, int row, int col) {
		//On écrit dans le bouton avec la valeur de la cellule
		//ImageIcon icon=(value==null)?new ImageIcon(""):new ImageIcon(value.toString().substring(6,value.toString().length()));
		this.setIcon((ImageIcon)value);
		//setText( (value ==null) ? "" : value.toString() );
		//On retourne notre bouton
		//this.setBackground(Color.WHITE);
		this.setPreferredSize(new Dimension(24,24));
		return this;
	}
}
