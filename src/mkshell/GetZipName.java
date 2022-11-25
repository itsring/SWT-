package mkshell;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class GetZipName {
	private Shell shell;
	
	public GetZipName(String path, List<File> WillBeZipFiles)	{
		shell = new Shell(Display.getCurrent());
		shell.setSize(300, 150);
		shell.setText("압축파일 이름");
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

            	//////////////////
            	 File zipFile = new File(path, t.getText()+".zip");
            	 
            	 byte[] buf = new byte[4096];
            	 try (ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFile))) {
            		
                     for (File file : WillBeZipFiles) {
//                    	 searchDirectory(file, out, buf);
                    	 zipFile(file, file.getName(), out);
                     }
                 } catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            	////////////////////
				 close();
				 System.out.println("///////////////////btn File To Zip - END/////////////////////");
            }
		});
	    
	    Button btnCancel = new Button(shell, SWT.PUSH);
		btnCancel.setText("취소");
		btnCancel.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING));	    
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
	
//	public void searchDirectory(File file, ZipOutputStream out, byte[] buf) {
//		if (file.isDirectory()) {
//			// 디렉토리일 경우 재탐색(재귀)
//			File[] files = file.listFiles();
//			for (File f : files) {
//				System.out.println("file = > " + f);
//				searchDirectory(f , out, buf);
//			}
//		} else {
//			try {
//				compressZip(file, out, buf);
//			} catch (Exception e) {
//				// TODO: handle exception
//			}
//		}
//	}
//	
//	public void compressZip(File file, ZipOutputStream out, byte[] buf) throws FileNotFoundException, IOException {
//		 try (FileInputStream in = new FileInputStream(file)) {
//             ZipEntry ze = new ZipEntry(file.getName());
//             out.putNextEntry(ze);
//
//             int len;
//             
//			 while ((len = in.read(buf)) > 0) {
//                 out.write(buf, 0, len);
//             }
//			 System.out.println("압축 파일 생성 성공");
//             out.closeEntry();
//         }
//	}
	
	private static void zipFile(File fileToZip, String fileName, ZipOutputStream zipOut) throws IOException {
        if (fileToZip.isHidden()) {
            return;
        }
        if (fileToZip.isDirectory()) {
            if (fileName.endsWith("/")) {
                zipOut.putNextEntry(new ZipEntry(fileName));
                zipOut.closeEntry();
            } else {
                zipOut.putNextEntry(new ZipEntry(fileName + "/"));
                zipOut.closeEntry();
            }
            File[] children = fileToZip.listFiles();
            for (File childFile : children) {
                zipFile(childFile, fileName + "/" + childFile.getName(), zipOut);
            }
            return;
        }
        FileInputStream fis = new FileInputStream(fileToZip);
        ZipEntry zipEntry = new ZipEntry(fileName);
        zipOut.putNextEntry(zipEntry);
        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }
        fis.close();
    }
	
}
