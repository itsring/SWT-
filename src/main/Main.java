package main;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import datas.FileTable;
import datas.FileTableList;
import dir.SearchList;
import fileDir.FileNode;
import fileDir.FileTree;
import layout.Treelayout;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.jface.viewers.TableViewerColumn;

public class Main {
	private static Table table;
	private final static Logger LOG = Logger.getGlobal();
	
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		
		
		
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setSize(900, 600);
		shell.setText("SWT 과제");
		shell.setLayout(null);

		TableViewer tableViewer = new TableViewer(shell, SWT.BORDER | SWT.FULL_SELECTION);
		table = tableViewer.getTable();
		table.setBounds(360, 50, 515, 495);
		
		TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnFilename = tableViewerColumn.getColumn();
		tblclmnFilename.setWidth(100);		
		tblclmnFilename.setText("FileName");
		
		TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnNewColumn = tableViewerColumn_1.getColumn();
		tblclmnNewColumn.setWidth(100);
		tblclmnNewColumn.setText("Update Date");
		
		TableViewerColumn tableViewerColumn_2 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnNewColumn_1 = tableViewerColumn_2.getColumn();
		tblclmnNewColumn_1.setWidth(100);
		tblclmnNewColumn_1.setText("Extension");

		TableViewerColumn tableViewerColumn_3 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnNewColumn_2 = tableViewerColumn_3.getColumn();
		tblclmnNewColumn_2.setWidth(100);
		tblclmnNewColumn_2.setText("Size");
		
		TableViewerColumn tableViewerColumn_4 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnNewColumn_3 = tableViewerColumn_4.getColumn();
		tblclmnNewColumn_3.setWidth(100);
		tblclmnNewColumn_3.setText("Category");
		

		
//		Treelayout treelayout = new Treelayout(shell, fileList);
		SearchList search = new SearchList();
		File[] fileList = search.searchC();
		System.out.println(search.toString());
		final Tree tree = new Tree(shell, SWT.CHECK | SWT.BORDER | SWT.V_SCROLL
		        | SWT.H_SCROLL);
		tree.setLocation(139, 50);
	    tree.setSize(215, 495);        
        
        for(File file : fileList) {
        	TreeItem treeItem0 = new TreeItem(tree,0);
	    	treeItem0.setText(file.getName());	    	
	    	treeItem0.setData(file.getPath());
			    	
	    	System.out.println(file.getPath());
	    	File[] fileList2 = search.searchInsideOfFolder(file.getPath());
	    	if(fileList2!=null) {
		    	for(File file2 : fileList2) {
		    		TreeItem treeItem1 = new TreeItem(treeItem0,0);
			    	treeItem1.setText(file2.getName());
		    	}
	    	}
		
        }
        
        tree.addListener(SWT.Expand, new Listener() {
			@Override
			public void handleEvent(Event e) {
				TreeItem treeitem = (TreeItem)e.item;
				if(e.item!=null) {
					String name = e.item.toString();
					String path;
					if(e.item.getData()!=null) {
						path = e.item.getData().toString();											
					}else {
						path = null;
					}
					SearchList search = new SearchList();
					File[] fileList = search.searchInsideOfFolder(path);
					LOG.info(""+fileList);
			        System.out.println(e.item.toString()+", "+ e.item.getData());
			        treeitem.clearAll(true);
			        treeitem.removeAll();;
			        if(fileList!=null) {			        	
				        for(File file : fileList) {
				        	TreeItem treeItem3 = new TreeItem(treeitem,0);
					    	treeItem3.setText(file.getName());	
					    	if(file.isFile()) {
					    		treeItem3.setData("하위 폴더 없음");	
					    		continue;
					    	}else {
							    treeItem3.setData(file.getPath());	
							    File[] fileList2 = search.searchInsideOfFolder(file.getPath());
							    if(fileList2==null) {
						    		continue;
						    	}
						    	for(File file2 : fileList2) {
						    		TreeItem treeItem1 = new TreeItem(treeItem3,0);
							    	treeItem1.setText(file2.getName());
						    		if(file2.isFile()) {
								    	treeItem1.setData("하위 폴더가 없습니다.");
						    		}else {
								    	treeItem1.setData(file2.getPath());
						    		}
						    	}
					    	}
				        }
			        }else {		        	
			        	System.out.println("폴더가 아님.");
			        }
		        }
			}
		});
        
        tree.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event e) {
				TreeItem treeitem = (TreeItem)e.item;
				if(e.item!=null) {
					String name = e.item.toString();
					String path;
					if(e.item.getData()!=null) {
						path = e.item.getData().toString();
											
					}else {
						path = null;
					}
					SearchList search = new SearchList();
					FileTableList FTL;
					List<FileTable> fT = new ArrayList<>();
					File[] fileList = search.searchInsideOfFolder(path);
					LOG.info(""+fileList);			        
			        table.removeAll();
			        if(fileList!=null) {			        	
				        for(File file : fileList) {
						    FileTable ft = new FileTable(new File(file.getPath()));
						    fT.add(ft);
						    setTT(ft, tableViewer);
				        }
				        FTL = new FileTableList(fT, e.item.toString());
				        System.out.println(FTL.FT);
			        }else {		        	
			        	System.out.println("폴더가 아님.");
			        }
				}		       
			}
		});
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		
		
	}
	static void setTT(FileTable ft, TableViewer tableViewer) {
		TableItem item = new TableItem(table, SWT.NONE);
	    item.setText(
	    		new String[]{
	    				ft.getFileName(), 
	    				ft.getUpdateDate(), 
	    				ft.getFileExtension(), 
	    				ft.getFileSize(), 
	    				ft.getFileCg()
	    				}
	    		);
	}
}
