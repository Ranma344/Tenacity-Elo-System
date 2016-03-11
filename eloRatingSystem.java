import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

import jxl.read.biff.BiffException;
import jxl.write.WriteException;

import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class eloRatingSystem extends JApplet /*implements Runnable*/{
	/*****************************************************************************/
	private static final int BUTTON_HEIGHT = 50;
	private static final int BUTTON_WIDTH  = 150;
	/*****************************************************************************/
	private Button newPlayer, editPlayer, editTeams, commitMatch, downloadBook; 
	private JButton editPopUp;
	/*****************************************************************************/
	private JComponent[] inputs; 
	private JComponent[] tables;
	/*****************************************************************************/
	private int testRow;
	private int playerCounter;
	/*****************************************************************************/
	private ArrayList<Player> pList;
	/*****************************************************************************/
	private String[] titles;
	/*****************************************************************************/
	private ListenForAction lForAction;
	/*****************************************************************************/
	private modifyExcel book;
	/*****************************************************************************/
	private JPanel panel;
	/*****************************************************************************/
	private JTable playerTable;
	/*****************************************************************************/
	private TableRowSorter<?> sorter;
	/*****************************************************************************/
	Dimension d;
	/*****************************************************************************/
	
	/*****************************Initialization**********************************/
	public void init() {
		inputs         =   new JComponent[]{
				new JLabel("Enter The Player's Name"),
		};
		
		testRow        = 10000;
		
		playerCounter  = 0;
		
		lForAction     =   new ListenForAction();
		
		book           =   new modifyExcel();
		
		book.openExcel();
		
		
		titles         =   new String[6];
		
		panel          =   new JPanel();
		
		
		pList          =   new ArrayList<Player>();
		d              =   new Dimension(500,140);
		
		titles         =   book.setTitles();
		
		/*DEBUG
		JOptionPane.showMessageDialog(null, titles[0] + titles[1] + titles[2] + titles[3] + titles[4] + titles[5], "Work", JOptionPane.PLAIN_MESSAGE);
		*/
		editPopUp      =   new JButton("Edit Player");
		
		newPlayer	   =	 new Button("Add Player");
		editPlayer	   =	 new Button("Edit Player");
		editTeams	   =	 new Button("Edit Teams");
		commitMatch    =	 new Button("Commit Match");
		downloadBook   =   new Button("Download Excel File");
		
		
		setLayout(null);
		
		//editPopUp.setBounds(0,0,BUTTON_WIDTH,BUTTON_HEIGHT);
		newPlayer.setBounds(75, 180, BUTTON_WIDTH, BUTTON_HEIGHT);
		editPlayer.setBounds(235, 180, BUTTON_WIDTH, BUTTON_HEIGHT);
		editTeams.setBounds(395, 180, BUTTON_WIDTH, BUTTON_HEIGHT);
		commitMatch.setBounds(555, 180, BUTTON_WIDTH, BUTTON_HEIGHT);
		
		panel.add(editPopUp);
		
		newPlayer.addActionListener(lForAction);
		editPlayer.addActionListener(lForAction);
		editPopUp.addActionListener(lForAction);
		
		
		initPlayers(book.getRows());
		//System.out.println(pList[4].getName());
		
		
		
		tables = new JComponent[]{
				new JScrollPane(playerTable = new JTable(new MyTableModel())),
				panel
		};
		
		
		playerTable.setAutoCreateRowSorter(true);
		createSorter();
		
		add(newPlayer);
		add(editPlayer);
		add(editTeams);
		add(commitMatch);
		
		getContentPane().setBackground(Color.GREEN);
		
		
	}
	/*
	public void start() {
		
	}
	public void destroy() {
		
	}
	public void stop() {
		
	}
	public void paint(Graphics g){
		
	}
	public void run() {
		this.setVisible(true);
		
	}*/
	/*****************************Initialize Players*************************************
	 *   This function calls for a read in of all players and data listed
	 * 	 inside of an excel spreadsheet. The players are added to an Array
	 *   List for easy access and dynamic storage capabilities.	 * 
	/************************************************************************************/
	public void initPlayers(int i){
		
		for(int x = 0; x < (i-1); x++){
			
			//System.out.println("Made it Here");
			pList.add(book.setPlayer((x+1)));
			
			//JOptionPane.showMessageDialog(null, pList.get(x).getName());
			
		}
		playerCounter = (book.getRows()-1);
		
	}
	/***************************************************************************************
	 * 	This function defines an Action Listener for defining button activity
	 *	The buttons all refer to this listener to create their functionality 
	 ***************************************************************************************/
	public class ListenForAction implements ActionListener{
		String S;
		int active = 0;
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == newPlayer){
				S = JOptionPane.showInputDialog(null, inputs, "Add New Player", JOptionPane.PLAIN_MESSAGE);
				if (S != null){
					addPlayer(S);
					try {
						book.appendExcel(pList, playerCounter-1);
					} catch (WriteException | BiffException | IOException e1) {
						e1.printStackTrace();
					}
				}
				/*DEBUG
				JOptionPane.showMessageDialog(null, pList[playerCounter - 1].getName());
				pList[0].setElo(2300);
				pList[0].calculateElo(6, "LOSE");
				JOptionPane.showMessageDialog(null, pList[0].getElo());
				*/
			} else if (e.getSource() == editPlayer){			
				JOptionPane.showOptionDialog(null, tables, "Edit Player Info", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, new Object[]{}, null);
			} else if (e.getSource() == editPopUp){
				if (active == 0) {
					editPopUp.setText("Stop Editing");
					testRow = playerTable.getSelectedRow();
					active = 1;
				} else {
					editPopUp.setText("Edit Player");
					testRow = 1000000;
					active = 0;
				}
					
			}
			
		}
	}
	/************************************************************************************
	 * 	Custom Table Model to allow for a list of editable size and data
	 *	The players are stored read into this via the Array List and the
	 *	Data is displayed in a list that can be sorted by column.
	 *************************************************************************************/
	private class MyTableModel extends AbstractTableModel {
		

        MyTableModel() {
             
        }

        public int getColumnCount() {
             return titles.length;
        }

        public int getRowCount() {
             return pList.size();
        }

        public String getColumnName(int col) {
             return titles[col];
        }
        
        public void setValueAt(Object value, int row, int col){
        	
        	switch(col) {
        	case 0:
        		pList.get(row).setName((String)value);
        		break;
        	case 1:
        		pList.get(row).setElo((Integer)value);
        		break;
        	case 2:
        		pList.get(row).setAttendance((Integer)value);
        		break;
        	case 3:
        		pList.get(row).setMVP((Integer)value);
        		break;
        	case 4:
        		pList.get(row).setWins((Integer)value);
        		break;
        	case 5:
        		pList.get(row).setLose((Integer)value);
        		break;
        	}
        	
        	System.out.println(pList.get(row).getName() + pList.get(row).getAttendance());
        }
        
       public boolean isCellEditable(int row, int column)
        {
    	   //System.out.println(Flag);
            if(testRow == playerTable.getSelectedRow()){
                return true;
            } else {
            	return false;
            }
        }

        public Object getValueAt(int row, int col) {

             Player object = pList.get(row);

             switch (col) {
             case 0:
                  return object.getName();
             case 1:
                  return object.getElo();
             case 2:
                  return object.getAttendance();
             case 3:
                  return object.getMVP();
             case 4:
                  return object.getWin();
             case 5:
          	    return object.getLose();
             default:
                  return "unknown";
             }
        }

        public Class getColumnClass(int c) {
             return getValueAt(0, c).getClass();
        }
	}
    
	private void addPlayer(String S){
		pList.add(new Player(S));
		tables = new JComponent[]{
				new JScrollPane(playerTable = new JTable(new MyTableModel())),
				panel
		};
		
		
		playerCounter++;
		createSorter();
	}
		

	
	public void createSorter(){
		
		playerTable.setPreferredScrollableViewportSize(d);
		
		playerTable.setAutoResizeMode(getHeight());
		playerTable.setAutoCreateRowSorter(true);
		sorter = (TableRowSorter<?>)playerTable.getRowSorter();
		sorter.setRowFilter(new RowFilter<TableModel, Integer>(){
			
			@Override
			public boolean include(RowFilter.Entry<? extends TableModel, ? extends Integer> entry){
				boolean included = true;
				Object cellValue = entry.getModel().getValueAt(entry.getIdentifier(), 0);
				if(cellValue == null || cellValue.toString().trim().isEmpty()){
					included = false;
				}
				return included;
			}
		});
	}
}