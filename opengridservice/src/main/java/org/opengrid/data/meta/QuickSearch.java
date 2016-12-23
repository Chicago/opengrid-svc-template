package org.opengrid.data.meta;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class QuickSearch {
	private boolean enable;
	private String triggerWord;
	private String triggerAlias;
	private Integer defaultMax;
	private String defaultSort;
	private String baseClientFilter;
	private String hintCaption;
	private String hintExample;
	
	public boolean isEnable() {
		return enable;
	}
	
	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public String getTriggerWord() {
		return triggerWord;
	}

	public void setTriggerWord(String triggerWord) {
		this.triggerWord = triggerWord;
	}

	public String getTriggerAlias() {
		return triggerAlias;
	}

	public void setTriggerAlias(String triggerAlias) {
		this.triggerAlias = triggerAlias;
	}

	public Integer getDefaultMax() {
		return defaultMax;
	}

	public void setDefaultMax(Integer defaultMax) {
		this.defaultMax = defaultMax;
	}

	public String getDefaultSort() {
		return defaultSort;
	}

	public void setDefaultSort(String defaultSort) {
		this.defaultSort = defaultSort;
	}

	public String getBaseClientFilter() {
		return baseClientFilter;
	}

	public void setBaseClientFilter(String baseClientFilter) {
		this.baseClientFilter = baseClientFilter;
	}

	public String getHintCaption() {
		return hintCaption;
	}

	public void setHintCaption(String hintCaption) {
		this.hintCaption = hintCaption;
	}

	public String getHintExample() {
		return hintExample;
	}

	public void setHintExample(String hintExample) {
		this.hintExample = hintExample;
	}
		
}
