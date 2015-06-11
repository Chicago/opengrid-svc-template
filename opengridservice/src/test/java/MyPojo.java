
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.opengrid.pojo.*;

public class MyPojo {
	    private boolean on;
	    private String name;
	    private Map<String,Date> map;

	    public void setOn(boolean on) {
	        this.on = on;
	    }

	    public boolean isOn() {
	        return this.on;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }

	    public String getName() {
	        return this.name;
	    }

	    public void setMap(Map<String,Date> map) {
	        this.map = map;
	    }

	    public Map<String,Date> getMap() {
	        return this.map;
	    }

	    public String toString() {
	        StringBuilder sb = new StringBuilder();
	        sb.append("on=" + on);
	        sb.append(" name=" + name);
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
        MyPojo pojo = new MyPojo();
        // Populate it with some data
        pojo.setName("Lucky");
        pojo.setOn(true);
        Map<String,Date> map = new HashMap<String,Date>();
        map.put("now", new Date());
        pojo.setMap(map);

        // Map it to JSON and then back again
        try {
            String pojoAsString = PojoMapper.toJson(pojo, true);
            System.out.println("POJO as string:\n" + pojoAsString + "\n");
            MyPojo pojo2 = (MyPojo) PojoMapper.fromJson(pojoAsString, MyPojo.class);
            System.out.println("POJO toString():\n" + pojo2 + "\n");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create another POJO
        MyPojo pojo3 = new MyPojo();
        pojo3.setName("Baldwin");
        pojo3.setOn(false);
        Map<String,Date> map2 = new HashMap<String,Date>();
        map2.put("now", new Date());
        pojo3.setMap(map2);

        // Map it to JSON, store it on disk and then read it back
        try {
            FileWriter fw = new FileWriter("pojo.txt");
            PojoMapper.toJson(pojo3, fw, true);

            FileReader fr = new FileReader("pojo.txt");

            MyPojo pojo4 = (MyPojo) PojoMapper.fromJson(fr, MyPojo.class);
            System.out.println("POJO read from file:\n" + pojo4);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	}
