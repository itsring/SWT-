package mkshell;

public class CRUDShell {
	public FileCRUDShell fileCRUDShell;
	public FolderCRUDShell folderCRUDShell;
	
	public CRUDShell(FileCRUDShell fileCRUDShell, FolderCRUDShell folderCRUDShell) {
		super();
		this.fileCRUDShell = fileCRUDShell;
		this.folderCRUDShell = folderCRUDShell;
	}

	public FileCRUDShell getFileCRUDShell() {
		return fileCRUDShell;
	}

	public void setFileCRUDShell(FileCRUDShell fileCRUDShell) {
		this.fileCRUDShell = fileCRUDShell;
	}

	public FolderCRUDShell getFolderCRUDShell() {
		return folderCRUDShell;
	}

	public void setFolderCRUDShell(FolderCRUDShell folderCRUDShell) {
		this.folderCRUDShell = folderCRUDShell;
	}
	
	

}
