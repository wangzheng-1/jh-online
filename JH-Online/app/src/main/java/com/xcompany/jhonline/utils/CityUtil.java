package com.xcompany.jhonline.utils;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.xcompany.jhonline.app.JhApplication;
import com.xcompany.jhonline.model.base.CityService;
import com.xcompany.jhonline.model.home.City;
import com.xcompany.jhonline.network.JHCallback;
import com.xcompany.jhonline.network.JHResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CityUtil {
    public static void getCityList(int level,String pid,String ppid){
        OkGo.<JHResponse<List<City>>>post(ReleaseConfig.baseUrl() + "class/area")
                .params("pid", pid)
                .execute(new JHCallback<JHResponse<List<City>>>() {
                    @Override
                    public void onSuccess(Response<JHResponse<List<City>>> response) {
                        List<City> cityList = response.body().getMsg();
                        if(level == 1){
                            for(int i = 0; i < cityList .size(); i++){
                                City province = cityList.get(i);
                                if(province.getId().equals("1")){  //中国过滤调
                                    continue;
                                }
                                CityService cityService = JhApplication.getInstance().getCityService();
                                Map<String,City> provinceListMap = cityService.getProvinceListMap();
                                provinceListMap.put(province.getId(),province);
                                getCityList(2,province.getId(),null);
                            }
                        }
                        if(level == 2){
                            CityService cityService = JhApplication.getInstance().getCityService();
                            Map<String,List<City>> cityListMap = cityService.getCityListMap();
                            cityListMap.put(pid,cityList);
                            for(int i = 0; i < cityList .size(); i++){
                                City city = cityList.get(i);
                                getCityList(3,city.getId(),city.getPid());
                            }
                        }
                        if(level == 3){
                            CityService cityService = JhApplication.getInstance().getCityService();
                            Map<String,List<City>> districtListMap = cityService.getDistrictListMap();
                            districtListMap.put(ppid + "|" +  pid,cityList);
                        }
                    }
                    @Override
                    public void onError(Response<JHResponse<List<City>>> response) {
                    }
                });
    }

    public static List<City> getProvinceList(){
        CityService cityService = JhApplication.getInstance().getCityService();
        Map<String,City> provinceListMap = cityService.getProvinceListMap();
        List<City> provinceList = new ArrayList<>();
        Set<String> keySet = provinceListMap.keySet();
        for(String key : keySet){
            provinceList.add(provinceListMap.get(key));
        }
        return provinceList;
    }

    public static List<List<City>> getCityList(){
        CityService cityService = JhApplication.getInstance().getCityService();
        Map<String,City> provinceListMap = cityService.getProvinceListMap();
        List<List<City>> cityList = new ArrayList<>();
        Set<String> keySet = provinceListMap.keySet();
        for(String key : keySet){
            City province = provinceListMap.get(key);
            Map<String,List<City>> cityListMap = cityService.getCityListMap();
            List<City> oneProvinceCityList = cityListMap.get(province.getId());
            cityList.add(oneProvinceCityList);
        }
        return cityList;
    }

    /**
     *  获取地区列表  遍历省，市 返回三层结构
     * @return
     */
    public static List<List<List<City>>> getDistrictList(){
        CityService cityService = JhApplication.getInstance().getCityService();
        Map<String,City> provinceListMap = cityService.getProvinceListMap();
        List<List<List<City>>> provinceAndCityAndAddList = new ArrayList<>();
        Set<String> keySet = provinceListMap.keySet();
        for(String key : keySet){
            City province = provinceListMap.get(key);
            List<List<City>> provinceCityList = new ArrayList<>();
            Map<String,List<City>> cityListMap = cityService.getCityListMap();
            List<City> oneProvinceCityList = cityListMap.get(province.getId());
            for(City city : oneProvinceCityList){
                Map<String,List<City>> districtListMap  = cityService.getDistrictListMap();
                List<City> oneCityDistrictList = districtListMap.get(province.getId() + "|" + city.getId());
                provinceCityList.add(oneCityDistrictList);
            }
            provinceAndCityAndAddList.add(provinceCityList);
        }
        return provinceAndCityAndAddList;
    }
}
