import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonAnyGetter;
import org.codehaus.jackson.annotate.JsonAnySetter;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.opengrid.pojo.*;
import org.opengrid.pojo.modules.*;

public class MyJsonObject {
	 @JsonIgnore
	    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	    @Override
	    public String toString() {
	        return ToStringBuilder.reflectionToString(this);
	    }

	    @JsonAnyGetter
	    public Map<String, Object> getAdditionalProperties() {
	        return this.additionalProperties;
	    }

	    @JsonAnySetter
	    public void setAdditionalProperty(String name, Object value) {
	        this.additionalProperties.put(name, value);
	    }

	    @Override
	    public int hashCode() {
	        return new HashCodeBuilder().append(additionalProperties).toHashCode();
	    }

	    @Override
	    public boolean equals(Object other) {
	        if (other == this) {
	            return true;
	        }
	        if ((other instanceof JsonObject) == false) {
	            return false;
	        }
	        JsonObject rhs = ((JsonObject) other);
	        return true;
//	        return new EqualsBuilder().append(additionalProperties, rhs.getAdditionalProperties().);
	    }

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		            
      // Map it to JSON, store it on disk and then read it back
      try {
         // FileReader fr = new FileReader("json\twitter.json");
    	  FileReader fr = new FileReader(new File("src/main/resources/json/json/twitter.json"));
          MyJsonObject pojo = (MyJsonObject) PojoMapper.fromJson(fr, MyJsonObject.class);
          System.out.println("POJO read from file:\n" + pojo);

      } catch (Exception e) {
          e.printStackTrace();
      }

	}

}
