package pos;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.AbstractTableModel;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class GoodsPanel extends MyPanel implements ActionListener,TableModelListener{
	JTextField t_search;
	JLabel la_name;
	JButton bt_search,bt_reg,bt_edit;
	//엑셀 버튼 바닥에 붙일꺼~!
	JButton bt_excel_download, bt_excel_upload;
	JFileChooser chooser = new JFileChooser("");
	
	Vector<String> columnName;
	Vector<Vector> data;
	Vector<String> vec;
	boolean flag=true;
	DataModel model2;
	
	public GoodsPanel() {
		t_search = new JTextField(20);
		la_name =new JLabel("이름");
		bt_reg = new JButton("재고 추가");
		dataController = new DataController(this);
		dataController.getList("goods");
		
		p_south.setPreferredSize(new Dimension(800, 70));
		setLayout(new BorderLayout());
		//p_north.add(bt_reg);
		//p_north.add(bt_reg);
		
		//버튼에 리스너 연결
		bt_reg.addActionListener(this);
		//테이블 초기 설정
		model =(DataModel)dataController.getDataModel();
		model.addTableModelListener(this);
			
		table.setModel(model);
		
		//엑셀관련 디자인
		bt_excel_download = new JButton("엑셀파일로 받기");
		bt_excel_upload = new JButton("엑셀파일 올리기");
		p_south.add(bt_excel_download);
		p_south.add(bt_excel_upload);
		bt_excel_download.addActionListener(this);
		bt_excel_upload.addActionListener(this);
		
		add(p_north,BorderLayout.NORTH);
		add(scroll);
		add(p_south,BorderLayout.SOUTH);
	}
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj == bt_reg){
			regist();
		}else if(obj == bt_excel_download){
			downLoad();
		}else if(obj == bt_excel_upload){
			upLoad();
		}		
	}
	//테이블의 내용을 엑셀 파일에 받기!
	public void downLoad(){
		//선택한 디렉토리겟
		chooser.setFileFilter(new FileNameExtensionFilter("xls", "xls"));
		int result = chooser.showSaveDialog(this);
		File file = chooser.getSelectedFile();
		
		if(result==JFileChooser.APPROVE_OPTION){
			Workbook wb = new HSSFWorkbook();
		    Sheet sheet = wb.createSheet("goods");
		    
		    int table_row=table.getRowCount();
		    int table_col=table.getColumnCount();
		  
		    
		    for(int a=0; a<table_row+1;a++){
			    Row row = sheet.createRow((short)a);
			    for(int i=0; i<table_col;i++){
			    	String value=null;
			    	Cell cell = row.createCell(i);
			    	if(a==0){
			    		value = table.getColumnName(i);
			    	}else {
			    		value = (String)table.getValueAt(a-1, i);
			    	}
			    	cell.setCellValue(value);
			    }
		    }
		    try {
				FileOutputStream fos = new FileOutputStream(file+".xls");
				wb.write(fos);
				fos.close();
				JOptionPane.showMessageDialog(this, "엑셀 다운로드 완료");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void upLoad(){
		//파일 업로드하기!
		int result = chooser.showOpenDialog(this);
		File file = chooser.getSelectedFile();
		flag=false;
		//컬럼네임용 벡터
		columnName = new Vector<String>();
		//컬럼데이터용 벡터
		data = new Vector<Vector>();
		
		if(result==JFileChooser.APPROVE_OPTION){
			try {
				FileInputStream fis = new FileInputStream(file);
				HSSFWorkbook wb = new HSSFWorkbook(fis);
				//시트가져오기!
				HSSFSheet sheet = wb.getSheetAt(0);
				for(int a=0;a<sheet.getLastRowNum()+1;a++){
					HSSFRow row = sheet.getRow(a);
					
					if(a!=0){
						vec = new Vector<String>();
					}
					for(int i=0; i<row.getLastCellNum();i++){
						//컬럼네임 따로 모으자!
						if(a==0){
							HSSFCell col = row.getCell(i);
							columnName.add(col.getStringCellValue());
						} else {
							HSSFCell col = row.getCell(i);
							if(col.getCellType()==HSSFCell.CELL_TYPE_NUMERIC){
								vec.add(Integer.toString(Double.valueOf(col.getNumericCellValue()).intValue()));

							}else if(col.getCellType()==HSSFCell.CELL_TYPE_STRING){
								vec.add(col.getStringCellValue());
							}
						}
					}
					//이제 다 담자~!
					if(a!=0){
						data.add(vec);
					}
				}
				//테이블 다시 만들기!
				
				model2=  new DataModel(data, columnName);
				dataController.addDB(data);
				model2.addTableModelListener(this);
				table.setModel(model2);
				table.updateUI();
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//나중에 디비 변경하는거 추가해야함~~!
	}
	
	public void regist(){
		//System.out.println(dataController.data.get(1).get(1));
		JOptionPane.showMessageDialog(this, "회원 계정 등록");
	}
	
	public void tableChanged(TableModelEvent e) {
		int row = e.getFirstRow();
		int col = e.getColumn();
		if(flag){
			dataController.editTable(model,e,"goods");
		}else{
			dataController.editTable(model2,e,"goods");
		}
	}
}
