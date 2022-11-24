package mkshell;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class AlertShell {
	private Shell shell;
	
	
	public AlertShell(String str) {		
		shell = new Shell(Display.getCurrent());
		shell.setSize(300, 150);
		shell.setText("경고");
		GridLayout gridLayout = new GridLayout(1, true);
		gridLayout.numColumns = 1;
		GridData gridData = new GridData(GridData.FILL_BOTH);
		gridData.horizontalSpan = 1;
		shell.setLayout(gridLayout);
		
		Text text = new Text(shell, SWT.MULTI | SWT.BORDER | SWT.WRAP);
		text.setLayoutData(gridData);
		text.setText(str);	
		
		Button btnOK = new Button(shell, SWT.PUSH);
	    btnOK.setText("확인");
	    btnOK.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_CENTER));
	    btnOK.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {            	
            	close();
            }
		});
	    
	    System.out.println("Alert !!!");		
			
	}
	
	public void open() {
		shell.open();
	}
	
	public void close() {
		shell.setVisible(false);
	}
}
