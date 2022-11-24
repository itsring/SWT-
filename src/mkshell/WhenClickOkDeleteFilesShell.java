package mkshell;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class WhenClickOkDeleteFilesShell {
	private Shell shell;
	public String[] path;
	
	public WhenClickOkDeleteFilesShell(String[] path) {
		
		this.path = path;
		
		
		shell = new Shell(Display.getCurrent());
		shell.setSize(400, 150);
		shell.setText("파일 삭제");
		GridLayout gridLayout = new GridLayout(1, true);
		gridLayout.numColumns = 2;
		shell.setLayout(gridLayout);
		Button btnOK = new Button(shell, SWT.PUSH);
	    btnOK.setText("확인");
	    btnOK.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
	    
	    btnOK.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
            	for(int i=0; i< getPath().length; i++) {
	            	File file = new File(getPath()[i]);
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
	
	public void open() {
		shell.open();
	}
	
	public void close() {
		shell.setVisible(false);
	}

	public String[] getPath() {
		return path;
	}


	
	
}
