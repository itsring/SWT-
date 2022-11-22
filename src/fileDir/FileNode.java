package fileDir;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileNode {
	
	private final File file;
    private final List<FileNode> children;
 
    public FileNode(final File file) {
        if (file == null) {
            throw new IllegalArgumentException();
        }
        this.file = file;
        this.children = new ArrayList<>();
    }
    public File getFile() {
		return file;
	}

	public List<FileNode> getChildren() {
		return children;
	}
	

	public FileNode addChild(File child) {
        FileNode childNode = new FileNode(child);
        addChild(childNode);
        return childNode;
    }
 	public boolean addChild(FileNode child) {
        if (child == null) {
            throw new IllegalArgumentException();
        }
        return this.children.add(child);
    }
 	 public void removeChilderen()
     {
         this.children.removeAll(this.children);
     }
}
