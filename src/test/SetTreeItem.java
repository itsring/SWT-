package test;

import java.io.File;

import org.eclipse.swt.widgets.TreeItem;

public class SetTreeItem {
	public void addTree(File[] fileList, TreeItem tree) {
		 for (int loopIndex0 = 0; loopIndex0 < fileList.length; loopIndex0++) {
			 tree = new TreeItem(tree, 0);
			 tree.setText(fileList[loopIndex0].toString());
//		      SearchList InFolder = new SearchList();
//		      search.searchInsideOfFolder2(search.direction(loopIndex0));
		    }
	}
}
