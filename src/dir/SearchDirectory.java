package dir;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface SearchDirectory {
	public File[] searchC();
	
	public File[] searchInsideOfFolder(String direction);
	
//	public List<File> searchInsideOfFolder2(String direction);
}
