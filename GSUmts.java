import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Aldeqeela
 * Jun 27, 2015 - 11:34:42 PM
 * Endang.Ismaya(endang.ismaya@gmail.com)
 * Walnut Creek, CA, USA
 */
public class GSUmts {

	private static final String refValue = "refValue";
	private static final String refPermission = "refPermission";
	
	private String fileName;
	
	public GSUmts(){
	}
	
	public GSUmts(String fileName){
		this.fileName = fileName;
	}
	
	public ArrayList<String> FiletoArrayList(){
		
		ArrayList<String> results = new ArrayList<String>(); 
		String fileName = this.fileName;
		
		
		try {			
			
			FileReader fileInput = null;
			BufferedReader sline = null;
			
			if (fileName == null ){		
				throw new Exception("No file to proceed!");
			}	
		
			// # read fileName
			fileInput = new FileReader(fileName);
			sline = new BufferedReader(fileInput);
		    
			while (true) {
		    	
			String data = sline.readLine();
		    	
				if (data == null){
					break;
				}
				else {
					results.add(data);
				}

			} // close while(true)
		   
			//Close file
			fileInput.close();

		} // Close try
		catch(Exception e) {
				System.out.println("Error: " + e.getMessage());
		} // Close Catch
		
		
		return results;
		
	} // end FiletoArryList
	
	public Map<String,HashMap<String,String>> FiletoMap(String fileName){
		
		ArrayList<String> results = new ArrayList<String>();
		Map<String,HashMap<String,String>> map = new HashMap<String, HashMap<String,String>>();
		HashMap<String,String> hmValue = new HashMap<String,String>();
		HashMap<String,String> hmPermission = new HashMap<String,String>();
		// String fileName = this.fileName;

		try {			
			
			FileReader fileInput = null;
			BufferedReader sline = null;
			
			if (fileName == null ){		
				throw new Exception("No file to proceed!");
			}	
		
			// # read fileName
			fileInput = new FileReader(fileName);
			sline = new BufferedReader(fileInput);
		    
			while (true) {
		    	
			String data = sline.readLine();
		    	
				if (data == null){
					break;
				}
				else if (data.trim().equals("")){
					continue;
				}
				else {
					results.add(data);
					String[] arrays = data.split(";");
					String MO = arrays[1];
					// System.out.println(MO);
					String Param = arrays[2];
					String Value = arrays[3];
					String Permission = arrays[4];
					String MOParam = MO + "." + Param;
					hmValue.put(MOParam, Value);
					hmPermission.put(MOParam, Permission);	
					// System.out.println(MOParam);
				}

			} // close while(true)
		   
			//Close file
			fileInput.close();

		} // Close try
		catch(Exception e) {
				e.printStackTrace();
		} // Close Catch
		
		map.put(refValue, hmValue);
		map.put(refPermission, hmPermission);

		return map;
		
	} // end FiletoArryList

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
}
