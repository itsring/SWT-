package mkshell;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class GetFileName {
	private Shell shell;
	public String fileName;
	public String fileType = ".txt";
	private static final String[] ITEMS = {".txt",".jpg",".png",".pdf",".word",".hwp",".mp3",".mp4"};
	
	public GetFileName(String fileText, FileCRUDShell fileCRUDShell, char c) {	
		shell = new Shell(Display.getCurrent());
		shell.setSize(300, 150);
		switch (c) {
		case 'i': {
			shell.setText("파일 이름");
			GridLayout gridLayout = new GridLayout(1, true);
			gridLayout.numColumns = 3;
			shell.setLayout(gridLayout);
			GridData gridData = new GridData(GridData.FILL_BOTH);
			gridData.horizontalSpan = 2;
			Text t = new Text(shell, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
			t.setLayoutData(gridData);
			// 메뉴 바
			Menu menu = new Menu(shell, SWT.BAR);
			shell.setMenuBar(menu);
			

		    // Create a dropdown Combo
		    Combo combo = new Combo(shell, SWT.DROP_DOWN);
		    combo.setItems(ITEMS);
		    combo.select(0);
		    combo.addListener(SWT.Selection, new Listener() {	
				@Override
				public void handleEvent(Event e) {					
					fileType=ITEMS[combo.getSelectionIndex()];
					System.out.println(fileType);
				}
			} );
		    
		    		
			Button btnOK = new Button(shell, SWT.PUSH);
		    btnOK.setText("확인");
		    btnOK.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
		    btnOK.addSelectionListener(new SelectionAdapter() {
	            public void widgetSelected(SelectionEvent e) {            	
	            	addFileNameText(t);
	            	String saveFilePath = fileCRUDShell.getPath()+"\\" + getFileName();                	
	            	System.out.println("saveFilePath : "+saveFilePath);
	            	System.out.println("fileText : "+fileText);
	            	System.out.println("saveFilePath + fileType : "+saveFilePath+fileType);
	            	File file = new File(saveFilePath+fileType);
	            	fileType=ITEMS[combo.getSelectionIndex()];
	            	System.out.println(fileType);
	            	if(t.getText()==null) {
	            		Alert("file name is required");
	            	}else {
		            	try {
//		            		boolean result = false;
//		            		result = 
		            		file.createNewFile();
		            		if(file.exists()) {
		            			FileWriter FW = new FileWriter(file, false);
		            			FW.write(fileText);
		            			FW.flush();
		            			FW.close();
		            			System.out.println("File Exist, File Name : "+file.getName());
		            			System.out.println("File Exist, File text : "+fileText);
		            			AlertShell alert = new AlertShell("create success filename : "+file.getName());
		            			alert.open();
		            		}else {
		            			AlertShell alert = new AlertShell("create fail filename : "+file.getName());
		            			alert.open();
	//	            			if(result) {
	//		            			FileWriter FW = new FileWriter(file);
	//		            			FW.write(fileText.toString());
	//		            			FW.flush();
	//		            			FW.close();
	//		            			System.out.println("File NOT Exist, File Name : "+file.getName());
	//		            		}else {
	//		            			System.out.println("File Create Fail!!!!! ");
	//		            		}
		            		}
		            		
		            	}catch (IOException error) {
		            		error.printStackTrace();
						}
	            	}
	            	fileCRUDShell.close();
	            }
			});
		    
		    Button btnCancel = new Button(shell, 1);
			btnCancel.setText("취소");
			btnCancel.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_END));	    
			btnCancel.addSelectionListener(new SelectionAdapter() {
	            public void widgetSelected(SelectionEvent e) {
	            	close();
	            }
			});
			break;
		}
		
		
		case 'u': {
			shell.setText("파일 이름 수정");
			GridLayout gridLayout = new GridLayout(1, true);
			gridLayout.numColumns = 2;
			shell.setLayout(gridLayout);
			GridData gridData = new GridData(GridData.FILL_BOTH);
			gridData.horizontalSpan = 2;
			Text t = new Text(shell, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
			t.setLayoutData(gridData);
			
			Button btnOK = new Button(shell, SWT.PUSH);
		    btnOK.setText("확인");
		    btnOK.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
		    btnOK.addSelectionListener(new SelectionAdapter() {
	            public void widgetSelected(SelectionEvent e) { 
	            	if(t.getText()==null) {
	            		Alert("file name is required");
	            	}
	            	addFileNameText(t);
	            	String saveFilePath = fileCRUDShell.getPath();                	
	            	System.out.println("saveFilePath : "+saveFilePath);
	            	System.out.println("fileText : "+fileText);
	            	System.out.println("fileName : "+getFileName());
	            	String renamePlusPath = saveFilePath.substring(0, saveFilePath.lastIndexOf("\\")+1)+getFileName()+fileType;
	            	System.out.println("renamePlusPath : "+renamePlusPath);
	            	File originFile = new File(saveFilePath);
	            	File forRenameFile = new File(renamePlusPath);
//	            	File file = new File(saveFilePath+fileType);
//	            	
	            	if (originFile.renameTo(forRenameFile)) {
	            	    System.out.println("File renamed successfully");
	            	    System.out.println(" originFile.getName() : " +originFile.getName());
	            	} else {
	            	    System.out.println("Failed to rename file");
	            	}
	            	
	            	
	            	try {	            		
	            		if(forRenameFile.exists()) {
//	            			FileWriter FW = new FileWriter(forRenameFile, false);
	            			FileWriter FW = new FileWriter(forRenameFile, true);
	            			FW.write(fileText);
	            			FW.flush();
	            			FW.close();
	            			System.out.println("File Exist, File Name : "+forRenameFile.getName());	            			
	            		}
	            	}catch (IOException error) {
	            		error.printStackTrace();
					}
	            	fileCRUDShell.close();
	            }
			});
		    
		    Button btnCancel = new Button(shell, 1);
			btnCancel.setText("취소");
			btnCancel.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_END));	    
			btnCancel.addSelectionListener(new SelectionAdapter() {
	            public void widgetSelected(SelectionEvent e) {
	            	close();
	            	fileCRUDShell.close();
	            }
			});
			break;
		}
		case 'd': {
			shell.setText("파일 삭제");
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + c);
		}
		
		
		
	}
	
	public void open() {
		shell.open();
	}
	
	public void close() {
		shell.setVisible(false);
	}
	public void addBtnOk(Text t) {
		Button btnOK = new Button(shell, 0);
	    btnOK.setText("확인");
	    
	    
	   
	}
	
	public void addBtnCancel() {
		Button btnCancel = new Button(shell, 1);
		btnCancel.setText("취소");
		btnCancel.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
            	close();
            }
		});
	}
	
	public void addFileNameText(Text t) {
		String getText = t.getText();
        System.out.println("확인버튼 클릭");
        System.out.println("getText : "+getText);
        setFileName(getText);
        close();        
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public boolean confirm() {		
		boolean result = false;
		return result;
	}
	static void Alert(String str) {
		AlertShell alertShell = new AlertShell(str);
		alertShell.open();
	}
	
	
}
