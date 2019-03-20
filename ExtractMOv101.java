import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Aldeqeela
 * Jun 23, 2015 - 1:07:18 PM
 * Endang.Ismaya(endang.ismaya@gmail.com)
 * Walnut Creek, CA, USA
 */
public class ExtractMOv101 extends GSUmts {

	private static final String refValue = "refValue";
	private static final String refPermission = "refPermission";
	private static final String sc = ";";
	private static final String NA = "Not Auditable";
	private static final String penaltyTime = "0";
	private static final String FEMTOpenaltyTime = "20";
	private static final String hcsSib11Config_penaltyTime = "UtranRelation.hcsSib11Config.penaltyTime";
	private static final String UtranCell_userLabel = "UtranCell.userLabel";
	private static final String EutranFreqRelation_cellReselectionPriority = "EutranFreqRelation.cellReselectionPriority";
	private static final String cellReselectionPriority6 = "6";
	private static final String cellReselectionPriority7 = "7";
	private static String FILERESULT;
	private String nodeName;
	
	private enum status {TRUE, FALSE, NotAuditable, NoBaseline};
	
	ArrayList<String> MOs = new ArrayList<String>();
	private String fileName;

	public ExtractMOv101(){
	}
	
	public ExtractMOv101(ArrayList<String> MOs){
		this.MOs = MOs;
	}
	
	public ExtractMOv101(ArrayList<String> MOs, String fileName){
		super(fileName);
		this.MOs = MOs;
		this.fileName = fileName;
	}
	
	public ExtractMOv101(ArrayList<String> MOs, String fileName, String nodeName){
		super(fileName);
		this.MOs = MOs;
		this.fileName = fileName;
		this.nodeName = nodeName;
	}
	
	/**
	 * @return the mOs
	 */
	public ArrayList<String> getMOs() {
		return MOs;
	}

	/**
	 * @param mOs the mOs to set
	 */
	public void setMOs(ArrayList<String> mOs) {
		MOs = mOs;
	}

	public void ExtractMOParamValue(){
		
		ArrayList<String> MOs = new ArrayList<String>();
		
		MOs = this.MOs;
		boolean struct = false;
		boolean bArr = false;
		String paramParent = null;
		
		for (int i = 0; i < MOs.size(); i++){
			
			String mo = MOs.get(i);
			
//			System.out.println(mo);
			
			String[] arrays = mo.split("\\s+");
 			
			if (mo.matches(".*Struct\\{.*\\}")){
				// System.out.println(mo);
				String[] subarrays = arrays[0].split(sc);
				paramParent = subarrays[subarrays.length - 1]; // Parent Parameters
				struct = true;
			} 
			else if (struct && !bArr){
				
				try {
					if (mo.contains(">>>")){
						
						String[] subarrays = mo.split(sc);
						String nodeName = subarrays[0]; // NodeId ; SNTDCAUJRNC003
						String ManagedObject = subarrays[1]; // UtranCell=CNU3345V -> MO
						// System.out.println(subarray0);
						String MOClass = subarrays[2];
						// System.out.println(subarray1); // UtranCell --> MO Class
						String SubParameter = subarrays[3];
						// System.out.println(subarray2); // >>> 1.cellReselectionPriority = 3 --> SubParameter
						String[] aSubParameter = SubParameter.split("\\.");
						String bSubParameter = "." + aSubParameter[1];
						// System.out.println(asubarray1); // .cellReselectionPriority = 3
						
						if (bSubParameter.contains("(")){
							// admBlockRedirection.gsmRrc = 0 (OFF)
							String[] a = bSubParameter.split("=");
							String subParam = a[0].trim(); // admBlockRedirection.gsmRrc
							String values = a[1].trim(); // 0 (OFF)
							String[] b = values.split("\\s+"); 
							String value = b[0].trim(); // 0
							System.out.println(nodeName + sc
									+ ManagedObject + sc + MOClass + sc + paramParent + subParam + sc + value); // output
						} 
						else {
							// cellReselectionPriority = 3	
							// locRegAcb = 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
							String[] a = bSubParameter.split("=");
							String subParam = a[0].trim(); // cellReselectionPriority
							String values = a[1].trim(); // 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
							String[] b = values.split("\\s");
							String value = null;

							if (b.length > 1){
								 value = values.replace(" ", ":");
							}else{
								value = values;
							}
							 
							System.out.println(nodeName + sc
									+ ManagedObject + sc + MOClass + sc + paramParent + subParam + sc + value); // output
						}
						
					}else{
						
						struct = false;
						i -= 1;
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} // end if Struct
			else if (arrays.length == 2 && !struct && !bArr){
				System.out.println(arrays[0] + sc + arrays[1]); // output
			}else{
					
				try {
					
					if (mo.contains("i[")){
						// System.out.println("test");
						// dSystem.out.println(mo);
						String[] subarrays = mo.split(sc);
						String nodeName = subarrays[0]; // NodeId ; SNTDCAUJRNC003
						String ManagedObject = subarrays[1]; // UtranCell=CNU3345V -> MO
//						System.out.println(ManagedObject);
						String MOClass = subarrays[2];
						// System.out.println(subarray1); // UtranCell --> MO Class
						String SubParameter = subarrays[3];
						// System.out.println(subarray2); // >>> 1.cellReselectionPriority = 3 --> SubParameter
						String[] aSubParameter = SubParameter.split("=");
						String[] params = aSubParameter[0].split("\\s+");
						String Param = params[0].trim();
						String values = aSubParameter[1].trim();
						
						if (values.contains("(")){
// 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 (FALSE FALSE FALSE FALSE FALSE FALSE FALSE FALSE FALSE FALSE FALSE FALSE FALSE FALSE FALSE FALSE)
							String[] a = values.split("\\(");
							String subValue = a[0].trim(); // admBlockRedirection.gsmRrc
							String[] b = subValue.split("\\s+");
							String value = null;
							if (b.length > 1){
								 value = subValue.replace(" ", ":");
							}else{
								value = subValue;
							}
							System.out.println(nodeName + sc
									+ ManagedObject + sc + MOClass + sc + Param + sc + value); // output
						}
						else if (mo.contains("i[0]")){
							String value = "[0]";
							System.out.println(nodeName + sc
									+ ManagedObject + sc + MOClass + sc + Param + sc + value); // output
						} 
						else {
							String value = values.replace(" ", ":");
							System.out.println(nodeName + sc
									+ ManagedObject + sc + MOClass + sc + Param + sc + value); // output
						}
					}
					else if (mo.matches(".*\\[.*\\].*")){
						// System.out.println(mo);
						String[] subarrays = arrays[0].split(sc);
						paramParent = subarrays[subarrays.length - 1]; // Parent Parameters
//						System.out.println(paramParent);
						bArr = true;
						if (mo.contains("[0]")){
							String[] allMO = mo.split(sc);
							String nodeName = allMO[0]; // NodeId ; SNTDCAUJRNC003
							String MO = allMO[1]; // MO : UtranCell=CNU3345V
							String MOClass = allMO[2]; // MO Class : UtranCell
							String value = "[0]";
							System.out.println(nodeName + sc
									+ MO + sc + MOClass + sc + paramParent + sc + value); // output
						}
					}
					else if (bArr && !struct){
						if (mo.contains(">>>")){	
							// >>> reservedBy = RncFunction=1,UtranCell=CNU3345T,UtranRelation=CNU3345V
							String[] allMO = mo.split(sc);
							String nodeName = allMO[0]; // NodeId ; SNTDCAUJRNC003
							String MO = allMO[1]; // MO : UtranCell=CNU3345V
							String MOClass = allMO[2]; // MO Class : UtranCell
							String SubParameter = allMO[3]; // >>> reservedBy = RncFunction=1,UtranCell=CNU3084F,UtranRelation=CNU3345V
							String[] ArrValues = SubParameter.split("\\s+");
							String value = ArrValues[ArrValues.length - 1].trim();
							System.out.println(nodeName + sc
									+ MO + sc + MOClass + sc + paramParent + sc + paramParent + "." +value); // output
						}
						else {
							bArr = false;
							i -= 1;
						}
					}
					else{
						
						if (mo.contains("(")){
							// UtranCell=CNU3345V;UtranCell;agpsEnabled                          1 (TRUE)
							String[] allMO = mo.split(sc);
							String nodeName = allMO[0]; // NodeId ; SNTDCAUJRNC003
							String MO = allMO[1]; // MO : UtranCell=CNU3345V
							String MOClass = allMO[2]; // MO Class : UtranCell
							String SubParameter = allMO[3]; //agpsEnabled                          1 (TRUE)
							String[] ArrValues = SubParameter.split("\\s+");
							String sParams = ArrValues[0];
							String value = ArrValues[1];
							
							System.out.println(nodeName + sc 
									+ MO + sc + MOClass + sc + sParams + sc + value); // output
						}
						else{
							// UtranCell=CNU0191Z,UtranRelation=CNU3088F;UtranRelation;creationTime                         2015-02-22 14:29:59
							String[] allMO = mo.split(sc);
							String nodeName = allMO[0]; // NodeId ; SNTDCAUJRNC003
							String MO = allMO[1]; // MO : UtranCell=CNU3345V
							String MOClass = allMO[2]; // MO Class : UtranCell
							String SubParameter = allMO[3]; // creationTime                         2015-02-22 14:29:59
							String[] ArrValues = SubParameter.split("\\s+");
							String sParams = ArrValues[0];
							// System.out.println(sParams);
							 String value = "";
//							System.out.println(ArrValues.length);
							
							if (ArrValues.length > 1) {
								for (int p = 0;p < ArrValues.length; p++){
									
									if (p != 0){
										if (p == 1){
											value = ArrValues[p];
										}else{
											value = value + "_" + ArrValues[p];
										}
									}
								}
								System.out.println(nodeName + sc + 
										MO + sc + MOClass + sc + sParams + sc + value); // output
							}else{
								System.out.println(nodeName + sc + 
										MO + sc + MOClass + sc + sParams + sc + value); // output
							}
						}
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}		
	}
	
	public ArrayList<String> ExtractMOParamValueArrayList(){
		
		ArrayList<String> MOs = new ArrayList<String>();
		ArrayList<String> results = new ArrayList<String>();
		
		MOs = this.MOs;
		boolean struct = false;
		boolean bArr = false;
		String paramParent = null;
		
		for (int i = 0; i < MOs.size(); i++) {
			
			String mo = MOs.get(i);
			
			String[] arrays = mo.split("\\s+");
 			
			if (mo.matches(".*Struct\\{.*\\}")){
				// System.out.println(mo);
				try {
					String[] subarrays = arrays[0].split(sc);
					paramParent = subarrays[subarrays.length - 1]; // Parent Parameters
					struct = true;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} 
			else if (struct && !bArr){
				
				try {
					if (mo.contains(">>>")){
						
						String[] subarrays = mo.split(";");
						String nodeName = subarrays[0]; // NodeId ; SNTDCAUJRNC003
						String ManagedObject = subarrays[1]; // UtranCell=CNU3345V -> MO
						// System.out.println(subarray0);
						String MOClass = subarrays[2];
						// System.out.println(subarray1); // UtranCell --> MO Class
						String SubParameter = subarrays[3];
						// System.out.println(subarray2); // >>> 1.cellReselectionPriority = 3 --> SubParameter
						String[] aSubParameter = SubParameter.split("\\.");
						String bSubParameter = "." + aSubParameter[1];
						// System.out.println(asubarray1); // .cellReselectionPriority = 3
						
						if (bSubParameter.contains("(")){
							// admBlockRedirection.gsmRrc = 0 (OFF)
							String[] a = bSubParameter.split("=");
							String subParam = a[0].trim(); // admBlockRedirection.gsmRrc
							String values = a[1].trim(); // 0 (OFF)
							String[] b = values.split("\\s+"); 
							String value = b[0].trim(); // 0
							//System.out.println(ManagedObject + ";" + MOClass + ";" + paramParent + subParam + ";" + value); // output
							String aInput = nodeName + sc
									+ ManagedObject + sc + MOClass + sc + paramParent + subParam + sc + value; // output
							results.add(aInput);
						} 
						else {
							// cellReselectionPriority = 3	
							// locRegAcb = 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
							String[] a = bSubParameter.split("=");
							String subParam = a[0].trim(); // cellReselectionPriority
							String values = a[1].trim(); // 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
							String[] b = values.split("\\s");
							String value = null;

							if (b.length > 1){
								 value = values.replace(" ", ":");
							}else{
								value = values;
							}
							 
							// System.out.println(ManagedObject + ";" + MOClass + ";" + paramParent + subParam + ";" + value); // output
							String aInput = nodeName + sc
									+ ManagedObject + sc + MOClass + sc + paramParent + subParam + sc + value; // output
							results.add(aInput);
						}
						
					}else{
						
						struct = false;
						i -= 1;
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} // end if Struct
			else if (arrays.length == 2 && !struct && !bArr){
				// System.out.println(arrays[0] + ";" + arrays[1]); // output
				try {
					String aInput = arrays[0] + sc + arrays[1]; // output
					results.add(aInput);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
					
				try {
					
					if (mo.contains("i[")){
						// System.out.println("test");
						// dSystem.out.println(mo);
						String[] subarrays = mo.split(sc);
						String nodeName = subarrays[0]; // NodeId ; SNTDCAUJRNC003
						String ManagedObject = subarrays[1]; // UtranCell=CNU3345V -> MO
//						System.out.println(ManagedObject);
						String MOClass = subarrays[2];
						// System.out.println(subarray1); // UtranCell --> MO Class
						String SubParameter = subarrays[3];
						// System.out.println(subarray2); // >>> 1.cellReselectionPriority = 3 --> SubParameter
						String[] aSubParameter = SubParameter.split("=");
						String[] params = aSubParameter[0].split("\\s+");
						String Param = params[0].trim();
						String values = aSubParameter[1].trim();
						
						if (values.contains("(")){
// 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 (FALSE FALSE FALSE FALSE FALSE FALSE FALSE FALSE FALSE FALSE FALSE FALSE FALSE FALSE FALSE FALSE)
							String[] a = values.split("\\(");
							String subValue = a[0].trim(); // admBlockRedirection.gsmRrc
							String[] b = subValue.split("\\s+");
							String value = null;
							if (b.length > 1) {
								value = subValue.replace(" ", ":");
							}else{
								value = subValue;
							}
							// System.out.println(ManagedObject + ";" + MOClass + ";" + Param + ";" + value); // output
							String aInput = nodeName + sc
									+ ManagedObject + sc + MOClass + sc + Param + sc + value; // output
							results.add(aInput);
						}
						else if (mo.contains("i[0]")){
							String value = "[0]";
							// System.out.println(ManagedObject + ";" + MOClass + ";" + Param + ";" + value); // output
							String aInput = nodeName + sc
									+ ManagedObject + sc + MOClass + sc + Param + sc + value; // output
							results.add(aInput);
						} 
						else {
							String value = values.replace(" ", ":");
							// System.out.println(ManagedObject + ";" + MOClass + ";" + Param + ";" + value); // output
							String aInput = nodeName + sc
									+ ManagedObject + sc + MOClass + sc + Param + sc + value; // output
							results.add(aInput);
						}
					}
					else if (mo.matches(".*\\[.*\\].*")){
						// System.out.println(mo);
						String[] subarrays = arrays[0].split(sc);
						paramParent = subarrays[subarrays.length - 1]; // Parent Parameters
//						System.out.println(paramParent);
						bArr = true;
						if (mo.contains("[0]")){
							String[] allMO = mo.split(sc);
							String nodeName = allMO[0]; // NodeId ; SNTDCAUJRNC003
							String MO = allMO[1]; // MO : UtranCell=CNU3345V
							String MOClass = allMO[2]; // MO Class : UtranCell
							String value = "[0]";
							// System.out.println(MO + ";" + MOClass + ";" + paramParent + ";" + value); // output
							String aInput = nodeName + sc
									+ MO + sc + MOClass + sc + paramParent + sc + value; // output
							results.add(aInput);
						}
					}
					else if (bArr && !struct){
						if (mo.contains(">>>")){	
							// >>> reservedBy = RncFunction=1,UtranCell=CNU3345T,UtranRelation=CNU3345V
							String[] allMO = mo.split(sc);
							String nodeName = allMO[0]; // NodeId ; SNTDCAUJRNC003
							String MO = allMO[1]; // MO : UtranCell=CNU3345V
							String MOClass = allMO[2]; // MO Class : UtranCell
							String SubParameter = allMO[3]; // >>> reservedBy = RncFunction=1,UtranCell=CNU3084F,UtranRelation=CNU3345V
							String[] ArrValues = SubParameter.split("\\s+");
							String value = ArrValues[ArrValues.length - 1].trim();
							// System.out.println(MO + ";" + MOClass + ";" + paramParent + ";" + paramParent + "." +value); // output
							String aInput = nodeName + sc
									+ MO + sc + MOClass + sc + paramParent + sc + paramParent + "." +value; // output
							results.add(aInput);
						}
						else {
							bArr = false;
							i -= 1;
						}
					}
					else{
						
						if (mo.contains("(")){
							// UtranCell=CNU3345V;UtranCell;agpsEnabled                          1 (TRUE)
							String[] allMO = mo.split(sc);
							String nodeName = allMO[0]; // NodeId ; SNTDCAUJRNC003
							String MO = allMO[1]; // MO : UtranCell=CNU3345V
							String MOClass = allMO[2]; // MO Class : UtranCell
							String SubParameter = allMO[3]; //agpsEnabled                          1 (TRUE)
							String[] ArrValues = SubParameter.split("\\s+");
							String sParams = ArrValues[0];
							String value = ArrValues[1];
							
							// System.out.println(MO + ";" + MOClass + ";" + sParams + ";" + value); // output
							String aInput = nodeName + sc
									+ MO + sc + MOClass + sc + sParams + sc + value; // output
							results.add(aInput);
						}
						else{
							// UtranCell=CNU0191Z,UtranRelation=CNU3088F;UtranRelation;creationTime                         2015-02-22 14:29:59
							String[] allMO = mo.split(sc);
							String nodeName = allMO[0]; // NodeId ; SNTDCAUJRNC003
							String MO = allMO[1]; // MO : UtranCell=CNU3345V
							String MOClass = allMO[2]; // MO Class : UtranCell
							String SubParameter = allMO[3]; // creationTime                         2015-02-22 14:29:59
							String[] ArrValues = SubParameter.split("\\s+");
							String sParams = ArrValues[0];
							// System.out.println(sParams);
							 String value = "";
//							System.out.println(ArrValues.length);
							
							if (ArrValues.length > 1) {
								for (int p = 0;p < ArrValues.length; p++){
									
									if (p != 0){
										if (p == 1){
											value = ArrValues[p];
										}else{
											value = value + "_" + ArrValues[p];
										}
									}
								}
								// System.out.println(MO + ";" + MOClass + ";" + sParams + ";" + value); // output
								String aInput = nodeName + sc + MO + sc + MOClass + sc + sParams + sc + value; // output
								results.add(aInput);
							}else{
								// System.out.println(MO + ";" + MOClass + ";" + sParams + ";" + value); // output
								String aInput = nodeName + sc + MO + sc + MOClass + sc + sParams + sc + value; // output
								results.add(aInput);
							}
						}
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return results;
	}
	
	private void PrinttoFile(String FileName, String element){
		
		try {
			
			PrintWriter outFile = new PrintWriter(new FileWriter(FileName,true));
			
			outFile.write(element);
			outFile.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void GSChecking() throws IOException{
		
		ArrayList<String> SourceFile = new ArrayList<String>();
		Map<String,HashMap<String,String>> map = new HashMap<String, HashMap<String,String>>();
		HashMap<String,String> hmValue = new HashMap<String,String>();
		HashMap<String,String> hmPermission = new HashMap<String,String>();
		FontColor fc = new FontColor();
		String element = null;
		String FileResult = null;
		
		// System.out.println("phase1");
		
		try {
			SourceFile = ExtractMOParamValueArrayList();
			// System.out.println("phase1");
			map = FiletoMap(fileName);
			hmValue = map.get(refValue);
			hmPermission = map.get(refPermission);
			java.util.Date today = new java.util.Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_hhmmss");
			FileResult = "GS_RESULT_" + this.nodeName + "_" + dateFormat.format(today) + ".log";
			FILERESULT = "03_srtumts_logfile/" + FileResult;
			
			element = "NodeId" + sc 
						+ "ManagedObject" + sc + "MO.Class" + sc + "Parameter" + sc + "Current.Value" + sc 
							+ "Baseline.Value" + sc + "TRUE-FALSE" + "\n";
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		
		try {
			PrintWriter outFile = new PrintWriter(new FileWriter(FILERESULT));
			outFile.write(element);
			outFile.close();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		// ProgressBar bar = new ProgressBar();
		fc.setString("0-> GS Checking...");
		System.out.println(fc.bblue());
		// bar.update(0, SourceFile.size());
		
		try {
			for (int p = 0 ; p < SourceFile.size() ; p++){
				
				String index = SourceFile.get(p);
				String[] arrays = index.split(sc);
				String Node = arrays[0];
				String MO = arrays[1];
				String MOClass = arrays[2];
				String Param = arrays[3];
				String MOCParam = MOClass + "." + Param; 
				String Value = "";
				String GSValue = "undefined";
				String GSPermission = "";
				// int total = SourceFile.size();
				
				// # progress bar update
			    // bar.update(p, total);
			    
				try{
					Value = arrays[4];
					if (Value == null){
						Value = "";
					}
				}
				catch (ArrayIndexOutOfBoundsException e){
					// System.out.println(e);
					Value = "";
				}
				
				
				// #Suffixes
				if (MOCParam.equalsIgnoreCase(hcsSib11Config_penaltyTime)){
					if (MO.contains("FEMTO")){
						GSValue = FEMTOpenaltyTime;
					}else {
						GSValue = penaltyTime;
					}
				} 
				else if (MOCParam.equalsIgnoreCase(EutranFreqRelation_cellReselectionPriority)){
					if (Value.equals(cellReselectionPriority6)){
						GSValue = cellReselectionPriority6;
					} else if (Value.equals(cellReselectionPriority7)){
						GSValue = cellReselectionPriority7;
					} else {
						GSValue = cellReselectionPriority7;
					}
				} 
				else if(MOCParam.equalsIgnoreCase(UtranCell_userLabel)){
					GSValue = MO.replace("UtranCell=", "");
				}
				else {
					GSValue = hmValue.get(MOCParam);
				}
				
				// # compare to GS Value
				GSPermission = hmPermission.get(MOCParam);
				if (GSPermission == null){
					// GSPermission = "No Baseline Value in GS File";
//				System.out.printf("%-30s %-10s %-50s %-100s %-60s %s%n",
//						MO, MOClass, Param, Value, "-", GSPermission);
					element = Node + sc
							+ MO + sc + MOClass + sc + Param + sc + Value + sc + "-" + sc + status.NoBaseline + "\n";
					PrinttoFile(FILERESULT,element);
				}
				else if (GSPermission.equals(NA)){
//				System.out.printf("%-30s %-10s %-50s %-100s %-60s %s%n",
//									MO, MOClass, Param, Value, "-", GSPermission);
					element = Node + sc
							+ MO + sc + MOClass + sc + Param + sc + Value + sc + "-" + sc + status.NotAuditable + "\n";
					PrinttoFile(FILERESULT,element);
				}
				else {
//				System.out.printf("%-30s %-10s %-50s %-100s %-60s %s%n",
//						MO, MOClass, Param, Value, GSValue, (Value.equals(GSValue) ? "TRUE" : "FALSE"));
					element = Node + sc
							+ MO + sc + MOClass + sc + Param + sc + Value + sc + GSValue + sc + (Value.equalsIgnoreCase(GSValue) ? status.TRUE : status.FALSE) + "\n";
					PrinttoFile(FILERESULT,element);
				}
				
			} // end for
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		fc.setString("0-> Completed.");
		System.out.println(fc.bblue());
	}// end GSChecking

	/**
	 * @return the fILERESULT
	 */
	public static String getFILERESULT() {
		return FILERESULT;
	}
	
	
}













