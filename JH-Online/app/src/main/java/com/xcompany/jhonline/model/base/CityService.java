package com.xcompany.jhonline.model.base;

import com.xcompany.jhonline.model.home.City;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CityService extends Model implements Serializable {
    private static final long serialVersionUID = 6270828565223252135L;
    Map<String,City> provinceListMap = new HashMap<>();
    Map<String,List<City>> cityListMap = new HashMap<>();
    Map<String,List<City>> districtListMap = new HashMap<>();

    public Map<String, City> getProvinceListMap() {
        return provinceListMap;
    }

    public void setProvinceListMap(Map<String, City> provinceListMap) {
        this.provinceListMap = provinceListMap;
    }

    public Map<String, List<City>> getCityListMap() {
        return cityListMap;
    }

    public void setCityListMap(Map<String, List<City>> cityListMap) {
        this.cityListMap = cityListMap;
    }

    public Map<String, List<City>> getDistrictListMap() {
        return districtListMap;
    }

    public void setDistrictListMap(Map<String, List<City>> districtListMap) {
        this.districtListMap = districtListMap;
    }
}
