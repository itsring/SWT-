package main;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
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
import mkshell.AlertShell;
import mkshell.FileCRUDShell;
import mkshell.FolderCRUDShell;
import mkshell.GetZipName;
import mkshell.WhenClickOkDeleteFilesShell;
import org.eclipse.swt.widgets.Label;

public class Main {
	private static Table table;
	private final static Logger LOG = Logger.getGlobal();
	private static FileCRUDShell fileCRUDShell;
	private static FolderCRUDShell folderCRUDShell;
	private static final char[] c = { 'i', 'u', 'd', 'z' };
	private static Shell shell;
	static String newLine = System.getProperty("line.separator");
	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		int x = 20;
		int y = 20;
		int width = 1200;
		int height = 800;
		int treeWidth = 200;
		int treeHeight = 520;
		Display display = new Display();
		shell = new Shell(display);
//		shell.setSize(1200, 800);
		shell.setBounds(x, y, treeWidth+700+9*x, treeHeight+8*y);
		shell.setText("SWT 과제");
		shell.setLayout(null);
		/////////////////////////////////////////////////////////////////////////
		// 트리, 테이블 셋업
		final Tree tree = new Tree(shell,  SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL | SWT.MULTI);
		tree.setBounds(x*2, y*2, treeWidth+2*x, treeHeight+2*y);
//		tree.setBounds(121, 33, 238, 520);

		TableViewer tableViewer = new TableViewer(shell, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI);
		Table table_1 = tableViewer.getTable();
//		table_1.setBounds(453, 33, 588, 520);
//		table_1.setBounds(453, 33, 620, 520);
		table_1.setBounds(treeWidth+6*x, 2*y, 700, treeHeight+2*y);

		TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnFilename = tableViewerColumn.getColumn();
		tblclmnFilename.setWidth(100);
		tblclmnFilename.setText("FileName");

		TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnNewColumn = tableViewerColumn_1.getColumn();
		tblclmnNewColumn.setWidth(200);
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
		setTableColumnLable(tableViewer, table);
		// tree menu
		Menu treeMenu = new Menu(tree);
		tree.setMenu(treeMenu);
		MenuItem treeMenuItem_I = new MenuItem(treeMenu, SWT.NONE);
		treeMenuItem_I.setText("폴더 생성");

		MenuItem treeMenuItem_U = new MenuItem(treeMenu, SWT.NONE);
		treeMenuItem_U.setText("폴더 이름 수정");

		MenuItem treeMenuItem_D = new MenuItem(treeMenu, SWT.NONE);
		treeMenuItem_D.setText("폴더 삭제");		
		
		MenuItem treeMenuItem_A = new MenuItem(treeMenu, SWT.NONE);
		treeMenuItem_A.setText("폴더 압축");	
		

		// table menu
		Menu tableMenu = new Menu(table);
		table.setMenu(tableMenu);
		MenuItem tableMenuItem_I = new MenuItem(tableMenu, SWT.NONE);
		tableMenuItem_I.setText("생성");

		MenuItem tableMenuItem_U = new MenuItem(tableMenu, SWT.NONE);
		tableMenuItem_U.setText("수정");

		MenuItem tableMenuItem_D = new MenuItem(tableMenu, SWT.NONE);
		tableMenuItem_D.setText("삭제");
		
		MenuItem tableMenuItem_A = new MenuItem(tableMenu, SWT.NONE);
		tableMenuItem_A.setText("압축");

		// shell menu
		Menu shellMenu = new Menu(shell);
		shell.setMenu(shellMenu);

		MenuItem shellMenuItem_I = new MenuItem(shellMenu, SWT.NONE);
		shellMenuItem_I.setText("생성");

		MenuItem shellMenuItem_U = new MenuItem(shellMenu, SWT.NONE);
		shellMenuItem_U.setText("수정");

		MenuItem shellMenuItem_D = new MenuItem(shellMenu, SWT.NONE);
		shellMenuItem_D.setText("삭제");
		
		Label lblNewLabel = new Label(shell, SWT.NONE);
		lblNewLabel.setBounds(40, 14, 71, 20);
		lblNewLabel.setText("파일 트리");
		
		Label lblNewLabel_1 = new Label(shell, SWT.NONE);
		lblNewLabel_1.setBounds(320, 14, 121, 20);
		lblNewLabel_1.setText("하위 파일 테이블");
		
		// setup end
		/////////////////////////////////////////////////////////////////////
		
		
        int toolbarSize = 30;
		
		shell.addMouseMoveListener(e -> showSize(e));
        
		
///////////////////////////////////////////////////////////////////
		
		
		
		
		// Event Listener List start
		
		//압축기능 
		Listener FileToZip = new Listener() {
			@Override
			public void handleEvent(Event e) {
				final int MAX_SIZE = 1024;
				System.out.println("///////////////////btn File To Zip - Start/////////////////////");
				if(table.getSelectionCount()!=0) {
					
					System.out.println("table.getSelectionCount() : "+ table.getSelectionCount());
					String path = tree.getSelection()[0].getData().toString();
					System.out.println("path : "+path);
					String[] FilePath = new String[table.getSelectionCount()];
					List<File> WillBeZipFiles = new ArrayList<>();
					for(int i =0; i< table.getSelectionCount(); i++) {
						FilePath[i] = path+"\\"+table.getSelection()[i].getText();
						File WillBeZipFile = new File(FilePath[i]);				
						WillBeZipFiles.add(WillBeZipFile);
						System.out.println(WillBeZipFiles.get(i));
					}
					
					///////////////////////////////////////////////////
					GetZipName GZN = new GetZipName(path,WillBeZipFiles);
					GZN.open();
					////////////////////////////////////////////////////////////////////////
					
				}else {
					Alert("하나 이상의 파일을 테이블에서 선택해 주세요.");					
				}
				
			}
		};
		
		Listener FileToZipTree = new Listener() {
			@Override
			public void handleEvent(Event e) {
				final int MAX_SIZE = 1024;
				System.out.println("///////////////////btn File To Zip - Start/////////////////////");
				if(tree.getSelectionCount()!=0) {
					
					System.out.println("tree.getSelectionCount() : "+ tree.getSelectionCount());
//					String path = tree.getSelection()[0].getData().toString();
					String path = tree.getSelection()[0].getData().toString().replace(tree.getSelection()[0].getText(), "");
					System.out.println("path : "+path);
					String[] FilePath = new String[tree.getSelectionCount()];
					List<File> WillBeZipFiles = new ArrayList<>();
					for(int i =0; i< tree.getSelectionCount(); i++) {
						FilePath[i] = path+"\\"+tree.getSelection()[i].getText();
						File WillBeZipFile = new File(FilePath[i]);				
						WillBeZipFiles.add(WillBeZipFile);
						System.out.println(WillBeZipFiles.get(i));
					}
					
					///////////////////////////////////////////////////
					GetZipName GZN = new GetZipName(path,WillBeZipFiles);
					GZN.open();
					////////////////////////////////////////////////////////////////////////
					
				}else {
					Alert("하나 이상의 파일을 테이블에서 선택해 주세요.");					
				}
				
			}
		};
		
		// 생성 , 수정, 삭제 기능 구현
		// 빈곳 메뉴 파일 생성
		Listener createShellButtonListener = new Listener() {
			@Override
			public void handleEvent(Event e) {
				if(tree.getSelection().length ==0) {
					Alert("create before select folder");
				}else {
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
			}
		};
		// 테이블에 파일 생성
		Listener createFileInTableButtonListener = new Listener() {
			@Override
			public void handleEvent(Event e) {
				if(tree.getSelection().length ==0) {
					Alert("create before select folder");
				}else {
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
			}
		};
		// 폴더생성
		Listener createFolderButtonListener = new Listener() {
			@Override
			public void handleEvent(Event e) {
				if(tree.getSelection().length ==0) {
					Alert("create before select folder");
				}else {
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
			}
		};
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// 수정
		Listener updateButtonListener = new Listener() {
			@Override
			public void handleEvent(Event e) {
				if(tree.getSelection().length ==0) {
					Alert("update before select folder");
				}else {
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
			}
		};
		// 테이블 파일 수정
		Listener updateFileInTableButtonListener = new Listener() {
			@Override
			public void handleEvent(Event e) {
				// 생성
//				String path = tree.getSelection()[0].getData().toString();
				
				if(table.getSelection().length ==0) {
					Alert("update before select folder");
				}else {
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
			}
		};
		// 트리 폴더 이름 수정
		Listener updateFolderButtonListener = new Listener() {
			@Override
			public void handleEvent(Event e) {
				if(tree.getSelection().length ==0) {
					Alert("update before select file");
				}else {
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
			}
		};
		
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// 삭제
		Listener deleteButtonListener = new Listener() {

			@Override
			public void handleEvent(Event e) {

				if(tree.getSelection().length ==0) {
					Alert("delete before select folder");
				}else {
				// 생성
					String path = tree.getSelection()[0].getData().toString();
					File file = new File(path);
					if (!file.isFile()) {
						System.out.println("button click test");
					}
					// 파일 내용 작성
					openAndSetFileCRUDShell(path, c[2]);
					System.out.println("Another Shell open");
				}
			}
		};
		
		// 폴더 삭제
		Listener deleteFolderButtonListener = new Listener() {

			@Override
			public void handleEvent(Event e) {
				if(tree.getSelection().length ==0) {
					Alert("delete before select folder");
				}else {
					String[] paths = new String[tree.getSelectionCount()];
					for(int i =0; i< tree.getSelectionCount(); i++) {
						String path = tree.getSelection()[i].getData().toString();						
						String filename = tree.getSelection()[i].getText().toString();
						System.out.println("path : "+ path + " , filename : "+filename);
						String filePath = path+"\\"+filename;
//						paths[i] = filePath;
						paths[i] = path;
						System.out.println("paths["+i+"] : "+paths[i]);
		//						File file = new File(path);
						File file = new File(filePath);
						
						if (file.isFile()) {
							System.out.println("button click test table , it is a file");
						}else {
							System.out.println("button click test, it is a directory");
						}
						
						// 파일 내용 작성
//						openAndSetFileCRUDShell(filePath, c[2]);
		//						openAndSetFileCRUDShell(path, c[2]);
						System.out.println("Another Shell open");
					}
					WhenClickOkDeleteFilesShell testShell = new WhenClickOkDeleteFilesShell(paths);
					testShell.open();
					
				
					
//				 생성
//					String path = tree.getSelection()[0].getData().toString();
//					
//					File file = new File(path);
//					if (!file.isFile()) {
//						System.out.println("button click test, it is a directory");
//					}
//					// 파일 내용 작성
//					openAndSetFolderCRUDShell(path, c[2]);
//					System.out.println("Another Shell open");
				}
			}
				
		};
		
		// 파일 삭제
		Listener deleteFileInTableButtonListener = new Listener() {
			@Override
			public void handleEvent(Event e) {
				if(table.getSelection().length ==0) {
					Alert("delete before select file in this table");
				}else {
					// 생성
					String[] paths = new String[table.getSelectionCount()];
					for(int i =0; i< table.getSelectionCount(); i++) {
						String path = tree.getSelection()[0].getData().toString();						
						String filename = table.getSelection()[i].getText().toString();
						System.out.println("path : "+ path + " , filename : "+filename);
						String filePath = path+"\\"+filename;
						paths[i] = filePath; 
		//						File file = new File(path);
						File file = new File(filePath);
						
						if (file.isFile()) {
							System.out.println("button click test table , it is a file");
						}
						// 파일 내용 작성
//						openAndSetFileCRUDShell(filePath, c[2]);
		//						openAndSetFileCRUDShell(path, c[2]);
						System.out.println("Another Shell open");
					}
					WhenClickOkDeleteFilesShell testShell = new WhenClickOkDeleteFilesShell(paths);
					testShell.open();
				}
			}
		};
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// 생성버튼
		shellMenuItem_I.addListener(SWT.Selection, createShellButtonListener);
		treeMenuItem_I.addListener(SWT.Selection, createFolderButtonListener);
		tableMenuItem_I.addListener(SWT.Selection, createFileInTableButtonListener);		
		// 수정버튼
		shellMenuItem_U.addListener(SWT.Selection, updateFolderButtonListener);
//		shellMenuItem_U.addListener(SWT.Selection, updateButtonListener);
		treeMenuItem_U.addListener(SWT.Selection, updateFolderButtonListener);
		tableMenuItem_U.addListener(SWT.Selection, updateFileInTableButtonListener);		
		// 삭제버튼
		shellMenuItem_D.addListener(SWT.Selection, deleteFolderButtonListener);
		treeMenuItem_D.addListener(SWT.Selection, deleteFolderButtonListener);
		tableMenuItem_D.addListener(SWT.Selection, deleteFileInTableButtonListener);
		
		// 파일 및 폴더 압축
		tableMenuItem_A.addListener(SWT.Selection, FileToZip);
		treeMenuItem_A.addListener(SWT.Selection, FileToZipTree);
		
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
			setTableColumnLable(tableViewer, table);
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
	static void Alert(String str ) {
		AlertShell alertShell = new AlertShell(str);
		alertShell.open();
	}
	public static void setTableColumnLable(TableViewer tableViewer, Table table) {
		
		
		TableItem item = new TableItem(table, SWT.NONE);
		item.setText(new String[] { "FileName", "Update Date", "Extension", "Size",
				"Category" });
		
	}
	public static void showSize(MouseEvent e) {

       
       
	}
}
