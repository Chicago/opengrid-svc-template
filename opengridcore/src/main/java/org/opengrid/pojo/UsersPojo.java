package org.opengrid.pojo;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class UsersPojo implements Serializable {		
		  /**
		 * 
		 */
		private static final long serialVersionUID = 920474444711665462L;
		private String firstName;
		private String lastName;
		private String userID;
		private String userPass;
		private String email;
		private String department;
		private String deptNumber;
		private Map<String,Date> map;
		  
		  
		public String getDepartment() {
			return department;
		}
		public void setDepartment(String department) {
			this.department = department;
		}
		public String getFirstName() {
			return firstName;
		}
		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}
		public String getLastName() {
			return lastName;
		}
		public void setLastName(String lastName) {
			this.lastName = lastName;
		}
		public String getUserID() {
			return userID;
		}
		public String getUserPass() {
			return userPass;
		}
		public void setUserID(String userID) {
			this.userID = userID;
		}
		public void setUserPass(String userPass) {
			this.userPass = userPass;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getDeptNumber() {
			return deptNumber;
		}
		public void setDeptNumber(String deptNumber) {
			this.deptNumber = deptNumber;
		}
		
		public Map<String, Date> getMap() {
			return map;
		}
		public void setMap(Map<String, Date> map) {
			this.map = map;
		}
		public static long getSerialversionuid() {
			return serialVersionUID;
		}
		
	    public String toString() {
	        StringBuilder sb = new StringBuilder();
	        sb.append("department=" + department);
	        sb.append(" deptNumber=" + deptNumber);
	        sb.append(" firstName=" + firstName);
	        sb.append(" lastName=" + lastName);
	        sb.append(" email=" + email);
	        sb.append(" userID=" + userID);
	        sb.append(" userPass=" +"504bd6582c176712c214d370");
	        sb.append(" map={");
	        for (Map.Entry<String,Date> entry : this.map.entrySet()) {
	            sb.append("" + entry.getKey() + "=" + entry.getValue());
	        }
	        sb.append("}");
	        return sb.toString();
	    }
		
		public static void main(String[] args) {
			// TODO Auto-generated method stub
			  // Create a POJO
	        UsersPojo pojo = new UsersPojo();
	        // Populate it with some data
	        pojo.setDepartment("eki");
	        pojo.setDeptNumber("myNuber");
	        pojo.setLastName("Fan");
	        pojo.setFirstName("Wayne");
	        pojo.setUserID("wfan");
	        pojo.setUserPass("504bd6582c176712c214d370");
	        pojo.setEmail("wfan@eki-consulting.com");
	       
	        Map<String,Date> map = new HashMap<String,Date>();
	        map.put("now", new Date());
	        pojo.setMap(map);

	        // Map it to JSON and then back again
	        try {
	            String pojoAsString = PojoMapper.toJson(pojo, true);
	            System.out.println("POJO as string:\n" + pojoAsString + "\n");
	            UsersPojo pojo2 = (UsersPojo) PojoMapper.fromJson(pojoAsString, UsersPojo.class);
	            System.out.println("POJO toString():\n" + pojo2 + "\n");
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        // Create another POJO
	        UsersPojo pojo3 = new UsersPojo();
	        pojo3.setDeptNumber("eki");
	        pojo3.setDeptNumber("ourNumber");
	        pojo3.setLastName("Ladines <rladines@eki-consulting.com>");
	        pojo3.setFirstName("Roderick");
	        pojo3.setUserID("380657");
	        pojo.setUserPass("504bd6582c176712c214d370");
	        pojo3.setEmail("rladines@eki-consulting.com");
	        Map<String,Date> map2 = new HashMap<String,Date>();
	        map2.put("now", new Date());
	        pojo3.setMap(map2);

	        // Map it to JSON, store it on disk and then read it back
	        try {
	            FileWriter fw = new FileWriter("pojo.txt");
	            PojoMapper.toJson(pojo3, fw, true);

	            FileReader fr = new FileReader("pojo.txt");

	            UsersPojo pojo4 = (UsersPojo) PojoMapper.fromJson(fr, UsersPojo.class);
	            System.out.println("POJO read from file:\n" + pojo4);

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	}

