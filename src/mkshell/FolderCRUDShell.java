package mkshell;

import java.beans.JavaBean;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
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
        System.out.println("확인버튼 클릭");
        setFileName(t);
        File file = new File(getPath()+"\\"+getFileName());
        
        System.out.println(file.getName());
        if (!file.exists()){
        	file.mkdirs();
        	System.out.println("make directory successful!!!");
        }else {
        	System.out.println("already exist");
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
        
//        
//        System.out.println(file.getName());
//        if (!file.exists()){
//        	file.mkdirs();
//        	System.out.println("make directory successful!!!");
//        }else {
//        	System.out.println("already exist");
//        }
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
	    
	    btnOK.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {            	
               addFileText(t.getText());
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
            	FolderRename(t.getText());
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
	
	
	
	public void deleteFolder(Shell shell) {
		GridLayout gridLayout = new GridLayout(1, true);
		gridLayout.numColumns = 2;
		shell.setLayout(gridLayout);
		Button btnOK = new Button(shell, SWT.PUSH);
	    btnOK.setText("확인");
	    btnOK.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
	    
	    btnOK.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {            	
            	File file = new File(getPath());            	
                System.out.println(file.getName());
            	if( file.exists() ){
            		if(file.delete()){
            			System.out.println("dir delete success");
            		}else{
            			System.out.println("dir delete fail");
            		}
            	}else{
            		System.out.println("folder not exist");
            	}
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
	

}
