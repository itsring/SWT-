package datas;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;

public class FileTable {
	
	public String fileName;
	public String updateDate;
	public String fileSize;
	public String fileCg;
	public String fileExtension;
	
	public String getFileExtension() {
		return fileExtension;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public String getUpdateDate() {
		return updateDate;
	}
	
	public String getFileSize() {
		return fileSize;
	}
	
	public String getFileCg() {
		return fileCg;
	}
	
	public FileTable(File files) {
		super();
		try {
			
			Path file = Paths.get(files.getPath());			
			BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);
			
			this.fileName = files.getName();
			this.updateDate = ""+attr.lastModifiedTime();
			this.fileSize = ""+attr.size();
			if(files.isFile()) {
				this.fileCg = "file";			
			}else {
				this.fileCg = "folder";
			}
			int index = this.fileName.lastIndexOf('.');
			if(index > 0) {
				this.fileExtension = this.fileName.substring(index+1);				
			}else {
				this.fileExtension = "folder";
			}
		}catch(IOException e){
			System.out.println(e);
		}
//		System.out.println(toString());
	}

	@Override
	public String toString() {
		return "FileTable [fileName=" + fileName + ", updateDate=" + updateDate + ", fileSize=" + fileSize + ", fileCg="
				+ fileCg + ", fileExtention=" + fileExtension + "]";
	}
	
	
	
	

}
