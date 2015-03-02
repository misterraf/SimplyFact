package com.rt.simplyFact;
import javax.swing.table.AbstractTableModel;

class ActTableModel extends AbstractTableModel{

	private Object[][] data;
	private String[] title;

	//Constructeur
	public ActTableModel(Object[][] data, String[] title){
		this.data = data;
		this.title = title;
	}

	//Retourne le titre de la colonne à l'indice spécifié
	public String getColumnName(int col) {
		return this.title[col];
	}

	//Retourne le nombre de colonnes
	public int getColumnCount(){
		return this.title.length;
	}

	//Retourne le nombre de lignes
	public int getRowCount(){
		return this.data.length;
	}

	//Retourne la valeur à l'emplacement spécifié
	public Object getValueAt(int row, int col) {
		return this.data[row][col];
	}

	//Définit la valeur à l'emplacement spécifié
	public void setValueAt(Object value, int row, int col) {
		//On interdit la modification sur certaines colonnes !
		if((!this.getColumnName(col).equals("Voir"))&&(!this.getColumnName(col).equals("Cot°"))){
				
			this.data[row][col] = value;
			this.fireTableDataChanged();

		}
	}

	//Retourne la classe de la donnée de la colonne
	public Class getColumnClass(int col){
		//On retourne le type de la cellule à la colonne demandée
		//On se moque de la ligne puisque les données sont les mêmes
		//On choisit donc la première ligne
		return this.data[0][col].getClass();
	}

	//Méthode permettant de retirer une ligne du tableau
	public void removeRow(int position){

		int indice = 0;
		int indice2 = 0;
		int nbRow = this.getRowCount() -1;
		int nbCol = this.getColumnCount();
		Object temp[][] = new Object[nbRow][nbCol];

		for(Object[] value : this.data){
			if(indice != position){
				temp[indice2++] = value;
			}
			//System.out.println("Indice = " + indice);
			indice++;
		}
		this.data = temp;
		temp = null;
		//Cette méthode permet d'avertir le tableau que les données
		//ont été modifiées, ce qui permet une mise à jour complète du tableau
		this.fireTableDataChanged();
	}
		//Permet d'ajouter une ligne dans le tableau
	public void addRow(Object[] data){
		int indice = 0, nbRow = this.getRowCount(), nbCol = this.getColumnCount();

		Object temp[][] = this.data;
		this.data = new Object[nbRow+1][nbCol];

		for(Object[] value : temp)
			this.data[indice++] = value;

		this.data[indice] = data;
		temp = null;
		//Cette méthode permet d'avertir le tableau que les données
		//ont été modifiées, ce qui permet une mise à jour complète du tableau
		this.fireTableDataChanged();
	}

	public boolean isCellEditable(int row, int col){
			return false;
	}
}
