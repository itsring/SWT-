package layout;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

public class HoLayout extends Composite{
	public HoLayout(Composite parent) {
        super(parent, SWT.NONE);
        
        FillLayout fillLayout= new FillLayout(SWT.HORIZONTAL);
        this.setLayout(fillLayout);

		final Tree tree = new Tree(parent, SWT.CHECK | SWT.BORDER | SWT.V_SCROLL
		        | SWT.H_SCROLL);
		tree.setLocation(139, 50);
	    tree.setSize(215, 495);
        
        for(int i =0 ; i <2 ; i++) {
        	
        }
    }
}
