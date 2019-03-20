import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeSet;


/**
 * @author Aldeqeela
 * Jun 29, 2015 - 10:25:27 AM
 * Endang.Ismaya(endang.ismaya@gmail.com)
 * Walnut Creek, CA, USA
 * SRT UMTS - GS Check v.1.0.1
 * -> Update with NodeName
 */
public class Mainv101 {

	/**
	 * @param args
	 */
	
	private static final String sc = ";";
	static ArrayList<String> lined = new ArrayList<String>(); // detect "==========="
	static ArrayList<String> moparam = new ArrayList<String>(); // combination of MOClass + Parameters
	private static final String fileName = "/home/ei400b/00_Tools/srtumts/UTRAN_RBS_UMTS_BASELINE";
	
	public static void main(String[] args) throws IOException {

		String filename = "";
		FontColor fc = new FontColor("0-> Initializing...");
		System.out.println(fc.bblue());

		if (args.length >= 1) {
			filename = args[0];
			moparam = getMOParam(filename);
			
			ExtractMOv101 emo;
			try {
				emo = new ExtractMOv101(moparam,fileName);
				
				emo.GSChecking();
				System.out.println("File save as: " + ExtractMOv101.getFILERESULT());
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			System.out.println("Unable to find a logfile to process!!");
		}

	}

	private static ArrayList<String> getMOParam(String filename){
		
		ArrayList<String> arr = new ArrayList<String>();
		String mo = null;
		String moId = null;
		String nodeName = "NodeId";
		
		try {
			
			FileReader fileInput = null;
			BufferedReader sline = null;
			
			TreeSet<String> elementId = new TreeSet<String>();
			
		    // System.out.println(arg);
			fileInput = new FileReader(filename);
		    sline = new BufferedReader(fileInput);

		    while (true) {
		    	
		    	String data = sline.readLine();
		    	
				if (data == null){
					break;
				}
				// # Get NodeName
				else if(data.matches(".*[A-Za-z0-9]> .*")){
					// System.out.println(data);
					String[] arrays = data.split(">");
					nodeName = arrays[0];
					// System.out.println(nodeName);
				}
				else {				
					//System.out.println(data);
					// # Detect how many lines were occured
					// # will reset to 0 when it reach more than 2 lines
					// # if lined.Size = 1 then the next line must be a MO (ManagedObject)
					// # if lined.Size = 2 then the next line must be Parameters with values
					if (data.matches(".*======================================================.*")){
						//System.out.println(lined.size());
						if (lined.size() > 2){
							lined.clear();
							lined.add(data);
						}else{
							lined.add(data);
						}
						//System.out.println(lined.size());
					}				
					// # Get Managed Object 
					if (lined.size() == 1 && !data.matches(".*======================================================.*")){
						String[] arrays = data.split("\\s+");
						mo = arrays[arrays.length - 1].trim();
						// mo = mo.replace("RncFunction=1,UtranCell=", "");
						mo = mo.replace("RncFunction=1,", "");
					}
					// # Get Parameters with its values
					else if (lined.size() == 2 && !data.matches(".*======================================================.*")){						
						if (data.matches("\\w*Id.*")) {
							// System.out.println(data);
							String[] arrays = data.split("\\s+");
							String element = arrays[0];
							
							if (element.matches("[A-Z].*Id$")){
								elementId.add(element);
								moId = element.replace("Id", "");
							}
						}
						// System.out.printf("%-20s %s%n", mo , data);
						arr.add(nodeName + sc + mo + sc + moId + sc + data);
					}
				}			
		    }
		    
		    //Close file
		    fileInput.close();
			}
			catch(Exception e) {
				
				System.out.println("Error: " + e.getMessage());
				e.printStackTrace();
				
			}
		    
		return arr;

	}
		
}
