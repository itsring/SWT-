package layout;



import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

import datas.FileTable;

public class Treelayout extends Composite{
	
	public Treelayout(Composite parent, File[] files) {
        super(parent, SWT.NONE);
        
        FillLayout fillLayout= new FillLayout(SWT.VERTICAL);
        this.setLayout(fillLayout);
       
        for(File file : files) {
        	FileTable FT = new FileTable(file);
        	
        }
        
        
    }

}
