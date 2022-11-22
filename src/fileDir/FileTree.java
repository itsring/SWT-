package fileDir;

import java.io.File;

public class FileTree {
 
    private final FileNode rootFileNode;
    private boolean initialized = false;
 
    public FileNode getRootFileNode() {
        if(!initialized)
        {
            initialize();
        }
        return rootFileNode;
    }
 
    public FileTree(final String rootDir) {
        if (rootDir == null) {
            throw new IllegalArgumentException();
        }
        this.rootFileNode = new FileNode(new File(rootDir));
    }
 
    public FileTree(final File rootDir) {
        if (rootDir == null) {
            throw new IllegalArgumentException();
        }
        this.rootFileNode = new FileNode(rootDir);
    }
 
    public FileTree(final FileNode rootFileNode) {
        if (rootFileNode == null) {
            throw new IllegalArgumentException();
        }
        this.rootFileNode = rootFileNode;
    }
 
    public synchronized void initialize() {
        if(initialized) {
            return ;
        }
        rootFileNode.removeChilderen();
        addChildrenRecursively(rootFileNode);
        initialized = true;
    }
 
    private void addChildrenRecursively(FileNode parentNode) {
//    	System.out.println(parentNode.getFile());
        File[] childrenFile = parentNode.getFile().listFiles();
		 if(childrenFile != null) {
			 
		    for (File child : childrenFile) {
		        if(child.isFile()) {
		            continue;
		        }
		        addChildrenRecursively(parentNode.addChild(child));
		    }
		 }
 
    }
 
}