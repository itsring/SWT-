package mkshell;

import java.beans.JavaBean;
import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

@JavaBean
public class FolderCRUDShell {
	
	private Shell shell;
	public String fileName;
	public String path;
	public FolderCRUDShell(char c) {		
			switch (c) {
			case 'i': {
				shell = new Shell(Display.getCurrent());
				shell.setSize(400, 300);
				shell.setText("폴더 생성");
				createFolder(shell);
				break;
			}
			case 'u':{
				shell = new Shell(Display.getCurrent());
				shell.setSize(400, 300);
				shell.setText("폴더 이름 수정");	
				updateFolder(shell);
				break;
			}
			case 'd':{
				shell = new Shell(Display.getCurrent());
				shell.setSize(300, 100);
				shell.setText("폴더 삭제");	
				deleteFolder(shell);
				break;
			}
			default:
				throw new IllegalArgumentException("Unexpected value: " + c);
			}		
	}
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void open() {
		shell.open();
	}
	
	public void close() {
		shell.setVisible(false);
	}
	
	public void addFileText(String t) {			
        System.out.println("btn Ok");
        setFileName(t);
        File file = new File(getPath()+"\\"+getFileName());
        if (!file.exists()){
        	file.mkdirs();
        	System.out.println("make directory successful!!!");
        }else {
        	System.out.println("already exist filename : "+file.getName());
        }
        close();
	}
	public void FolderRename(String t) {			
        System.out.println("확인버튼 클릭");
        setFileName(t);
        System.out.println("path : "+ getPath()+", file name : "+ getFileName());
//        String[] filename = getPath().split("\\");
//        String filename = getPath().substring(getPath().lastIndexOf("\\")+1);
        String renamePlusPath = getPath().substring(0, getPath().lastIndexOf("\\")+1)+getFileName();
        System.out.println("renamePlusPath : "+renamePlusPath);
        
//        File file = new File(filename[filename.length-1]);
        File file = new File(getPath());
//        File rename = new File(getFileName());
        File rename = new File(renamePlusPath);
        
        if (file.renameTo(rename)) {
            System.out.println("Folder renamed successfully");
        } else {
            System.out.println("Failed to rename file");
        }
        close();
	}
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public void createFolder(Shell shell) {
				
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
	    String beforePath = t.getText();
	    btnOK.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
            	String nowPath = t.getText();
            	System.out.println(nowPath==beforePath);
            	if(nowPath == beforePath) {
            		Alert("folder name is required");
            	}else {
            		addFileText(t.getText());
            	}
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
	}
	public void updateFolder(Shell shell) {
		
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
            		Alert("folder name is required");
            	}else {
            		FolderRename(t.getText());
            	}
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
	}
	
	public static void deleteFolderInFolder(String path) {
		File file = new File(path);
		try {
			if(file.exists()){
                File[] folder_list = file.listFiles(); //파일리스트 얻어오기
					
				for (int i = 0; i < folder_list.length; i++) {
				    if(folder_list[i].isFile()) {
						folder_list[i].delete();
						System.out.println("file delete success");
				    }else {
				    	deleteFolderInFolder(folder_list[i].getPath()); //재귀함수호출
						System.out.println("dir delete success");
				    }
				    folder_list[i].delete();
				 }
				file.delete(); //폴더 삭제
				System.out.println("delete complete");
		       }
		   } catch (Exception error) {
			error.getStackTrace();
		   }	
	}
	
	public void deleteFolder(Shell shell) {
		GridLayout gridLayout = new GridLayout(1, true);
		gridLayout.numColumns = 2;
		shell.setLayout(gridLayout);
		Button btnOK = new Button(shell, SWT.PUSH);
	    btnOK.setText("확인");
	    btnOK.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
	    btnOK.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
            	deleteFolderInFolder(getPath()); // 폴더안의 폴더 , 파일 삭제 재귀함수
            	close();
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
	}
	static void Alert(String str) {
		AlertShell alertShell = new AlertShell(str);
		alertShell.open();
	}
}
