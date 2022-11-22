package dir;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchList implements SearchDirectory {

	public String direction;
	public File[] list;
	public File files = new File("c://");
	
	
	
	public String getDirection() {
		return direction;
	}

	
	public SearchList() {
		super();
	}

	public SearchList(String direction) {
		super();
		this.direction = direction;
	}
	
	@Override
	public File[] searchC() {
		list = files.listFiles();
		return list;
	}

//	@Override
//	public List<File> searchInsideOfFolder2(String direction) {
//		File inFolderList = new File(direction);
//		List<File> filelist = new ArrayList();
//		files = new File(direction);
//		list = files.listFiles();
////		System.out.println(direction);
//		if(list == null) {
//		}else {
//			for(File file : list) {		
////				System.out.println(file);
//				if(list==null) {
//					break;
//				}
//				filelist.add(file);
//			}
//		}
//		list = inFolderList.listFiles(); 
//		Arrays.toString(list);
//		System.out.println(toString());
//		return searchInsideOfFolder2(direction);
//	}
	
	
	@Override
	public File[] searchInsideOfFolder(String direction) {
		try {
			File inFolderList = new File(direction);
			List<File> filelist = new ArrayList();
//			System.out.println(direction);
			list = inFolderList.listFiles(); 
			return list;
		}catch (Exception e) {
			System.out.println("하위 폴더가 없습니다.");
			return null;
		}
		
		
	}
	
	

	@Override
	public String toString() {
		return Arrays.toString(list);
	}
	
	
	
	public String direction(int a) {
		return list[a].toString();
		
	}
	
//	public List showFileDetail(String direction){
//		File[] filelist = searchInsideOfFolder(direction);
//		
//		
//		
//		
////		map.put("name", );
//		
//		return list;
//	}
//	
	
	
	
	
	
}
