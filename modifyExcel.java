import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import jxl.*;
import jxl.read.biff.BiffException;
import jxl.write.*;
import jxl.write.Number;
import jxl.write.biff.RowsExceededException;

public class modifyExcel {
	private Player pHolder;
	private String[] tHolder =  new String[6];
	private Workbook eloBook;
	Sheet sheet;
	WritableSheet sheet2;
	WritableWorkbook eloBook2;	
	public void openExcel(){
		try {
			eloBook = Workbook.getWorkbook(new File("./eloSpreadsheet.xls"));
			
		} catch(Exception e){
			e.printStackTrace();;
		}
		sheet = eloBook.getSheet(0);
		
	}
	
	public void appendExcel(ArrayList<Player> p, int x) throws RowsExceededException, WriteException, IOException, BiffException{
		Label name;
		Number Elo;
		Number Attendance;
		Number MVP;
		Number Wins;
		Number Lose;
		try {
			eloBook2 = Workbook.createWorkbook(new File("eloSpreadsheet.xls"), eloBook);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		sheet2 = eloBook2.getSheet(0);
		
		
			name = new Label(0, x+1, p.get(x).getName());
			Elo = new Number(1, x+1, p.get(x).getElo());
			Attendance = new Number(2, x+1, p.get(x).getAttendance());
			MVP = new Number(3, x+1, p.get(x).getMVP());
			Wins =  new Number(4, x+1, p.get(x).getWin());
			Lose = new Number(5, x+1, p.get(x).getLose());
			
			sheet2.addCell(name);
			sheet2.addCell(Elo);
			sheet2.addCell(Attendance);
			sheet2.addCell(MVP);
			sheet2.addCell(Wins);
			sheet2.addCell(Lose);
			
			eloBook2.write();
			eloBook2.close();
			eloBook = Workbook.getWorkbook(new File("eloSpreadsheet.xls"));
			sheet = eloBook.getSheet(0);
			
			
			
		
		
	}
	
	public String[] setTitles(){
		for(int x = 0; x<=5; x++){
			tHolder[x] = sheet.getCell(x, 0).getContents();
		}
		return(tHolder);
	}
	
	public Player setPlayer(int i){
		//System.out.println("Made it Here " + i);
		pHolder = new Player();
		pHolder.setName(sheet.getCell(0, i).getContents());
		pHolder.setElo(Integer.parseInt(sheet.getCell(1, i).getContents()));
		pHolder.setAttendance(Integer.parseInt(sheet.getCell(2, i).getContents()));
		pHolder.setMVP(Integer.parseInt(sheet.getCell(3, i).getContents()));
		pHolder.setWins(Integer.parseInt(sheet.getCell(4, i).getContents()));
		pHolder.setLose(Integer.parseInt(sheet.getCell(5, i).getContents()));
		
		
		return(pHolder);
	}
	
	public int getRows(){
		return(sheet.getRows());
	}
}