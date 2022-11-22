package main;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.internal.forms.widgets.SelectionData;

import datas.FileTable;
import datas.FileTableList;
import dir.SearchList;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Composite;
import java.awt.Frame;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.events.MenuAdapter;
import org.eclipse.swt.events.MenuEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import java.awt.Panel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.BorderLayout;
import javax.swing.JRootPane;
import org.eclipse.swt.widgets.MenuItem;

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
		/////////////////////////////////////////////////////////////////////////
		//트리, 테이블 셋업
		final Tree tree = new Tree(shell, SWT.CHECK | SWT.BORDER | SWT.V_SCROLL
		        | SWT.H_SCROLL);
		
		TableViewer tableViewer = new TableViewer(shell, SWT.BORDER | SWT.FULL_SELECTION);
		Table table_1 = tableViewer.getTable();
		
		
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
		
		leftTree(shell, tree);
		table = tableViewer.getTable();
		table.setBounds(360, 50, 515, 495);
		
		Menu menu = new Menu(shell);
		shell.setMenu(menu);
		
		MenuItem mntmNewItem = new MenuItem(menu, SWT.NONE);
		mntmNewItem.setText("생성");
		
		MenuItem mntmNewItem2 = new MenuItem(menu, SWT.NONE);
		mntmNewItem2.setText("수정");
		
		MenuItem mntmNewItem_1 = new MenuItem(menu, SWT.NONE);
		mntmNewItem_1.setText("삭제");
		
		
		/////////////////////////////////////////////////////////////////////
		
		// 생성 , 수정, 삭제 기능 구현
		Listener listener = new Listener() {
			
			@Override
			public void handleEvent(Event e) {
				switch(e.type) {
				case SWT.Selection:
					if(e.item == mntmNewItem) {
						//생성						
						String path = tree.getSelection()[0].getData().toString();
						File file = new File(path);
						if(!file.isFile()) {
							System.out.println("button click test");
						}
						//파일 내용 작성
						//저장
						//파일 이름 물어보기
						//확인
					}
					if(e.item == mntmNewItem2) {
						//수정
					}
					if(e.item == mntmNewItem_1) {
						//삭제
					}
				}
			}
		};
		
		final MenuItem copyItem = new MenuItem(menu, SWT.NONE);
		
		copyItem.addSelectionListener(new SelectionAdapter() {
			public void handleAdapter() {
				
			}
		});
		  
		mntmNewItem.addListener(SWT.Selection, listener);
		mntmNewItem2.addListener(SWT.Selection, listener);
		mntmNewItem_1.addListener(SWT.Selection, listener);
		
		menu.addMenuListener(new MenuAdapter() {
			public void menuShown(MenuEvent e) {
				MenuItem[] items = menu.getItems();
	            System.out.println("selected tree : "+ tree.getSelection()[0].getData());
			}
		});
		
		/////////////////////////////////////////////////////////////////////
		
        
        tree.addListener(SWT.Expand, new Listener() {
			@Override
			public void handleEvent(Event e) {
				treeExpandEvent(e);
			}
		});
        
        tree.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event e) {
				treeSelectionEvent(e, tableViewer);
			}
		});
        
//        tree.addListener(SWT.Mouse, null);
        // given
        // 
        // when
        // 
        //then
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
	

	
	static void leftTree(Shell shell, Tree tree) {
		tree.setLocation(139, 50);
	    tree.setSize(215, 495);  
		SearchList search = new SearchList();
		File[] fileList = search.searchC();
		System.out.println(search.toString());
		      
        
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
	}
	static void treeExpandEvent(Event e){
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
	static void treeSelectionEvent(Event e, TableViewer tableViewer) {
		if(e.item!=null) {
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
}
