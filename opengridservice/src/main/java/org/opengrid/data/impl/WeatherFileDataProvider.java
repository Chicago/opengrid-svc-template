package org.opengrid.data.impl;

import org.opengrid.data.DataProvider;
import org.opengrid.service.OpenGridException;
import org.opengrid.util.FileUtil;

public class WeatherFileDataProvider implements DataProvider {


	@Override
	public String getData(String filter, int max, String sort) throws OpenGridException {
		//not supporting filtering right now on file-based impl
		return FileUtil.getJsonFileContents("json/weather_data.json");
	}


	@Override
	public String getDescriptor() throws OpenGridException {
		return FileUtil.getJsonFileContents("json/weather_descriptor.json");
	}
	

	@Override
	public String getId() {
		return org.opengrid.constants.DataTypes.WEATHER;
	}
}
