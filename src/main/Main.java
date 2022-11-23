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
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import datas.FileTable;
import datas.FileTableList;
import dir.SearchList;
import mkshell.FileCRUDShell;
import mkshell.FolderCRUDShell;

public class Main {
	private static Table table;
	private final static Logger LOG = Logger.getGlobal();
	private static FileCRUDShell fileCRUDShell;
	private static FolderCRUDShell folderCRUDShell;
	private static final char[] c = { 'i', 'u', 'd' };

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setSize(900, 600);
		shell.setText("SWT 과제");
		shell.setLayout(null);
		/////////////////////////////////////////////////////////////////////////
		// 트리, 테이블 셋업
		final Tree tree = new Tree(shell, SWT.CHECK | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);

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

		// tree menu
		Menu treeMenu = new Menu(tree);
		tree.setMenu(treeMenu);
		MenuItem treeMenuItem_I = new MenuItem(treeMenu, SWT.NONE);
		treeMenuItem_I.setText("폴더 생성");

		MenuItem treeMenuItem_U = new MenuItem(treeMenu, SWT.NONE);
		treeMenuItem_U.setText("폴더 이름 수정");

		MenuItem treeMenuItem_D = new MenuItem(treeMenu, SWT.NONE);
		treeMenuItem_D.setText("폴더 삭제");

		// table menu
		Menu tableMenu = new Menu(table);
		table.setMenu(tableMenu);
		MenuItem tableMenuItem_I = new MenuItem(tableMenu, SWT.NONE);
		tableMenuItem_I.setText("생성");

		MenuItem tableMenuItem_U = new MenuItem(tableMenu, SWT.NONE);
		tableMenuItem_U.setText("수정");

		MenuItem tableMenuItem_D = new MenuItem(tableMenu, SWT.NONE);
		tableMenuItem_D.setText("삭제");

		// shell menu
		Menu shellMenu = new Menu(shell);
		shell.setMenu(shellMenu);

		MenuItem shellMenuItem_I = new MenuItem(shellMenu, SWT.NONE);
		shellMenuItem_I.setText("생성");

		MenuItem shellMenuItem_U = new MenuItem(shellMenu, SWT.NONE);
		shellMenuItem_U.setText("수정");

		MenuItem shellMenuItem_D = new MenuItem(shellMenu, SWT.NONE);
		shellMenuItem_D.setText("삭제");
		
		// setup end
		/////////////////////////////////////////////////////////////////////

		// 생성 , 수정, 삭제 기능 구현
		// 파일 생성
		Listener createShellButtonListener = new Listener() {
			@Override
			public void handleEvent(Event e) {

				// 생성
				String path = tree.getSelection()[0].getData().toString();
				File file = new File(path);
				if (!file.isFile()) {
					System.out.println("button click test");
				}
				// 파일 내용 작성
				openAndSetFileCRUDShell(path, c[0]);
				// save
				System.out.println("Another Shell open");
			}
		};
		// 테이블에 파일 생성
		Listener createFileInTableButtonListener = new Listener() {
			@Override
			public void handleEvent(Event e) {
				// 생성
				String path = tree.getSelection()[0].getData().toString();						
				File file = new File(path);
				if (!file.isFile()) {
					System.out.println("button click test");
				}
				// 파일 내용 작성
				openAndSetFileCRUDShell(path, c[0]);
				System.out.println("Another Shell open : "+e.item);						
			}
		};
		// 폴더생성
		Listener createFolderButtonListener = new Listener() {
			@Override
			public void handleEvent(Event e) {
				// 생성
				String path = tree.getSelection()[0].getData().toString();
				File file = new File(path);
				file.mkdirs();
				if (!file.isFile()) {
					System.out.println("create folder test");
				}						
				// 파일 내용 작성
				openAndSetFolderCRUDShell(path, c[0]);
				System.out.println("Another Shell open");
			}
		};
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// 수정
		Listener updateButtonListener = new Listener() {
			@Override
			public void handleEvent(Event e) {
				// 생성
				String path = tree.getSelection()[0].getData().toString();
				File file = new File(path);
				if (!file.isFile()) {
					System.out.println("button click test");
				}
				// 파일 내용 작성
				openAndSetFileCRUDShell(path, c[1]);
				System.out.println("Another Shell open");
			}
		};
		// 테이블 파일 수정
		Listener updateFileInTableButtonListener = new Listener() {
			@Override
			public void handleEvent(Event e) {
				// 생성
//				String path = tree.getSelection()[0].getData().toString();
				String filename = table.getSelection()[0].getText().toString();
				String path = tree.getSelection()[0].getData().toString();
				String filePath = path+"\\"+filename;
				File file = new File(filePath);
//				File file = new File(path);
				if (!file.isFile()) {
					System.out.println("button click test");
				}
				// 파일 내용 작성
				openAndSetFileCRUDShell(filePath, c[1]);
				System.out.println("Another Shell open");
			}
		};
		// 트리 폴더 이름 수정
		Listener updateFolderButtonListener = new Listener() {
			@Override
			public void handleEvent(Event e) {
				// 생성
				String path = tree.getSelection()[0].getData().toString();
				File file = new File(path);
				if (!file.isFile()) {
					System.out.println("button click test");
				}
				// 파일 내용 작성
				openAndSetFolderCRUDShell(path, c[1]);
				System.out.println("Another Shell open");
			}
		};
		
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// 삭제
		Listener deleteButtonListener = new Listener() {

			@Override
			public void handleEvent(Event e) {

				// 생성
				String path = tree.getSelection()[0].getData().toString();
				File file = new File(path);
				if (!file.isFile()) {
					System.out.println("button click test");
				}
				// 파일 내용 작성
				openAndSetFileCRUDShell(path, c[2]);
				System.out.println("Another Shell open");

				// 저장
				// 파일 이름 물어보기
				// 확인
			}
		};
		
		// 폴더 삭제
		Listener deleteFolderButtonListener = new Listener() {

			@Override
			public void handleEvent(Event e) {

				// 생성
				String path = tree.getSelection()[0].getData().toString();
				
				File file = new File(path);
				if (!file.isFile()) {
					System.out.println("button click test, it is a directory");
				}
				// 파일 내용 작성
				openAndSetFolderCRUDShell(path, c[2]);
				System.out.println("Another Shell open");
			}
		};
		
		// 파일 삭제
		Listener deleteFileInTableButtonListener = new Listener() {
			@Override
			public void handleEvent(Event e) {

				// 생성
				String path = tree.getSelection()[0].getData().toString();
				String filename = table.getSelection()[0].getText().toString();
				System.out.println("path : "+ path + " , filename : "+filename);
				String filePath = path+"\\"+filename;
//						File file = new File(path);
				File file = new File(filePath);
				
				if (file.isFile()) {
					System.out.println("button click test table , it is a file");
				}
				// 파일 내용 작성
				openAndSetFileCRUDShell(filePath, c[2]);
//						openAndSetFileCRUDShell(path, c[2]);
				System.out.println("Another Shell open");
			}
		};
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// 생성버튼
		shellMenuItem_I.addListener(SWT.Selection, createShellButtonListener);
		treeMenuItem_I.addListener(SWT.Selection, createFolderButtonListener);
		tableMenuItem_I.addListener(SWT.Selection, createFileInTableButtonListener);		
		// 수정버튼
		shellMenuItem_U.addListener(SWT.Selection, updateButtonListener);
		treeMenuItem_U.addListener(SWT.Selection, updateFolderButtonListener);
		tableMenuItem_U.addListener(SWT.Selection, updateFileInTableButtonListener);		
		// 삭제버튼
//		shellMenuItem_D.addListener(SWT.Selection, deleteButtonListener);
		treeMenuItem_D.addListener(SWT.Selection, deleteFolderButtonListener);
		tableMenuItem_D.addListener(SWT.Selection, deleteFileInTableButtonListener);
/////////////////////////////////////////////////////////////////////
// 트리 확장 시 children 출력
		tree.addListener(SWT.Expand, new Listener() {
			@Override
			public void handleEvent(Event e) {
				treeExpandEvent(e);
			}
		});
// 트리 선택시 테이블에 children 정보 요약된 list 출력
		tree.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event e) {
				treeSelectionEvent(e, tableViewer);
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
// table 세팅
	static void setTT(FileTable ft) {
		TableItem item = new TableItem(table, SWT.NONE);
		item.setText(new String[] { ft.getFileName(), ft.getUpdateDate(), ft.getFileExtension(), ft.getFileSize(),
				ft.getFileCg() });
	}
// 왼쪽 트리 세팅 
// 파일은 출력하지 않음 
	static void leftTree(Shell shell, Tree tree) {
		tree.setLocation(139, 50);
		tree.setSize(215, 495);
		SearchList search = new SearchList();
		File[] fileList = search.searchC();
		System.out.println(search.toString());

		for (File file : fileList) {
			if (!file.isFile()) {
				TreeItem treeItem0 = new TreeItem(tree, 0);
				treeItem0.setText(file.getName());
				treeItem0.setData(file.getPath());

				System.out.println(file.getPath());
				File[] fileList2 = search.searchInsideOfFolder(file.getPath());
				if (fileList2 != null) {
					for (File file2 : fileList2) {
						TreeItem treeItem1 = new TreeItem(treeItem0, 0);
						treeItem1.setText(file2.getName());
					}
				}
			} else {
				continue;
			}
		}
	}
// 트리 확장 이벤트 
	static void treeExpandEvent(Event e) {
		TreeItem treeitem = (TreeItem) e.item;
		if (e.item != null) {
			String name = e.item.toString();
			String path;
			if (e.item.getData() != null) {
				path = e.item.getData().toString();
			} else {
				path = null;
			}
			SearchList search = new SearchList();
			File[] fileList = search.searchInsideOfFolder(path);
			LOG.info("" + fileList);
			System.out.println(e.item.toString() + ", " + e.item.getData());
			treeitem.clearAll(true);
			treeitem.removeAll();
			if (fileList != null) {
				for (File file : fileList) {
					if (!file.isFile()) {
						TreeItem treeItem3 = new TreeItem(treeitem, 0);
						treeItem3.setText(file.getName());
						treeItem3.setData(file.getPath());
						File[] fileList2 = search.searchInsideOfFolder(file.getPath());
						if (fileList2 == null) {
							continue;
						} else {
							for (File file2 : fileList2) {
								TreeItem treeItem1 = new TreeItem(treeItem3, 0);
								treeItem1.setText(file2.getName());
								if (file2.isFile()) {
									treeItem1.setData("하위 폴더가 없습니다.");
								} else {
									treeItem1.setData(file2.getPath());
								}
							}
						}
					}

				}
			} else {
				System.out.println("폴더가 아님.");
			}
		}
	}

	static void treeSelectionEvent(Event e, TableViewer tableViewer) {
		if (e.item != null) {
			String path;
			if (e.item.getData() != null) {
				path = e.item.getData().toString();
			} else {
				path = null;
			}
			SearchList search = new SearchList();
			FileTableList FTL;
			List<FileTable> fT = new ArrayList<>();
			File[] fileList = search.searchInsideOfFolder(path);
			LOG.info("" + fileList);
			table.removeAll();
			if (fileList != null) {
				for (File file : fileList) {
					FileTable ft = new FileTable(new File(file.getPath()));
					fT.add(ft);
					setTT(ft);
				}
				FTL = new FileTableList(fT, e.item.toString());
				System.out.println(FTL.FT);
			} else {
				System.out.println("폴더가 아님.");
			}
		}
	}
// 중복 방지	
	static void openAndSetFileCRUDShell(String path, char c) {
		fileCRUDShell = new FileCRUDShell(c);
		fileCRUDShell.open();
		fileCRUDShell.setPath(path);
	}
// 중복방지	
	static void openAndSetFolderCRUDShell(String path, char c) {
		folderCRUDShell = new FolderCRUDShell(c);
		folderCRUDShell.open();
		folderCRUDShell.setPath(path);
	}
}
