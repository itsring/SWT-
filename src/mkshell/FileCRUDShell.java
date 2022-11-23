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
public class FileCRUDShell {
	
	private Shell shell;
	public String fileName;
	public String path;
	private GetFileName getFileName;
	private CRUDShell crudShell;
	public FileCRUDShell(char c) {
		switch (c) {
		case 'i': {
			shell = new Shell(Display.getCurrent());
			shell.setSize(800, 600);
			shell.setText("파일 생성");
			createFile(shell, this);
			break;
		}
		case 'u':{
			shell = new Shell(Display.getCurrent());
			shell.setSize(800, 600);
			shell.setText("파일 수정");	
			updateFile(shell, this);
			break;
		}
		case 'd':{
			shell = new Shell(Display.getCurrent());
			shell.setSize(400, 300);
			shell.setText("파일 삭제");	
			deleteFile(shell, this);
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
	
	public void addFileText(String t, FileCRUDShell fileCRUDShell, char c) {			
        System.out.println("ok btn click");
        getFileName = new GetFileName(t,  fileCRUDShell, c);
        getFileName.open();
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public void createFile(Shell shell, FileCRUDShell fileCRUDShell) {				
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
               addFileText(t.getText(), fileCRUDShell, 'i');
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
	public void updateFile(Shell shell, FileCRUDShell fileCRUDShell) {		
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
            	addFileText(t.getText(), fileCRUDShell, 'u');
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
	
	
	
	public void deleteFile(Shell shell, FileCRUDShell fileCRUDShell) {
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
            			close();
            		}else{
            			System.out.println("dir delete fail");            			
            		}
            	}else{
            		System.out.println("folder not exist");
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
}
