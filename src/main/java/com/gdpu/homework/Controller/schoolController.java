package com.gdpu.homework.Controller;

import com.gdpu.homework.Config.OnlyRoot;
import com.gdpu.homework.Entity.*;
import com.gdpu.homework.Entity.Config.JsonData;
import com.gdpu.homework.ServiceImpl.Service.SchoolService;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/vue/school")
public class schoolController {
    @Autowired
    SchoolService schoolService;
    @GetMapping("/campus")
    @ResponseBody
    public List<Campus> getCampus(){
        List<Campus> campus = schoolService.getCampus();
         return  campus;

    }
    @GetMapping("/campus/college")
    @ResponseBody
    public List<ListCamCollege> getCollege(){
        //获取不重复校区
        List<Campus> campus = schoolService.getCampus();
        //获取所有不重复的学院
        List<College> college = schoolService.getCollege();
        //定义一个数组，此数组包含n个对象，此对象有校区名和其名下的学院
        List<ListCamCollege> ListListCamCol = new ArrayList<>();
         for(int i = 0 ; i < campus.size(); i++){
             //生成一个对象有校区名和其名下的学院
           ListCamCollege camCol = new ListCamCollege();
           //此数组保存相同校区的学院
           List<String> CamCollege = new ArrayList<>();
           //将校区名保存
           camCol.setCampus(campus.get(i).getCampus());
           for(int k = 0; k < college.size();k++){
               //如果校区名相同，将所有的学院保存在CamCollege数组中
               if(college.get(k).getCampus().equals(camCol.getCampus()) ){
                  CamCollege.add(college.get(k).getCollege());

               }
           }

           camCol.setCollege(CamCollege);
           ListListCamCol.add(camCol);
         }



        return  ListListCamCol;

    }
    @GetMapping("/campus/college/major")
    @ResponseBody
    public Object   getMajor(){
        List<Campus> campus = schoolService.getCampus();
        List<Major> major = schoolService.getMajor();
        List<College> college = schoolService.getCollege();
        //保存所有校区的所有学院和专业
        List<ListCamColMajor> listListCamColMajor = new ArrayList<>();
        //先将不重复的校区存起来
         for(int n=0; n< campus.size();n++){
             //保存单个校区内所有学院和专业并且包含校区名
            ListCamColMajor listCamColMajor = new ListCamColMajor();
            //设置校区名
            listCamColMajor.setCampus(campus.get(n).getCampus());
            //保存相同校区的所有学院和专业(不包含校区名)
             List<ListColMajor> listColMajor = new ArrayList<>();
        for(int i = 0; i < college.size(); i++){
            //保存相同学院的专业(不包含学院名）
            List<String> sameColMajor = new ArrayList<>();
            //保存相同学院的专业(包含学院名）
            ListColMajor colMajor =new ListColMajor();
            //设置学院名
            if(college.get(i).getCampus().equals(listCamColMajor.getCampus())){
            colMajor.setCollege(college.get(i).getCollege());
            for(int k = 0; k< major.size(); k++){
                if(major.get(k).getCampus().equals( college.get(i).getCampus())  && major.get(k).getCollege().equals(college.get(i).getCollege()) ){
                   //如果是相同校区的相同学院的,则添加
                    sameColMajor.add(major.get(k).getMajor());
                }
            }

            //将专业列表储存起来
            colMajor.setMajor(sameColMajor);
            //将包含学院名和专业列表的储存起来(其中的校区名相同)
            listColMajor.add(colMajor);
           }
        }
             //将此学院收录
             listCamColMajor.setCollege(listColMajor);
             //将此校区收录
             listListCamColMajor.add(listCamColMajor);
         }
        return listListCamColMajor;

    }
    @OnlyRoot
    @PutMapping("/{campus}")
    @ResponseBody
    public JsonData updateCampus(@PathVariable("campus") String oldCampus,@RequestBody Map map){
        String campus = (String) map.get("campus");
        try{
            oldCampus = URLDecoder.decode(oldCampus, "UTF-8");
          schoolService.updateCampus(oldCampus,campus);
        }catch (Exception e){
            e.printStackTrace();
          return   JsonData.error("校区信息更新失败");
        }
        return JsonData.progress("更新校区成功");
    }
    @OnlyRoot
    @PutMapping("/{campus}/{college}")
    @ResponseBody
    public JsonData updateCollege(@PathVariable("campus") String campus,@PathVariable("college") String oldCollege,@RequestBody Map map){
            String college = (String) map.get("college");
            try{
               campus =  URLDecoder.decode(campus, "UTF-8");
               college = URLDecoder.decode(college, "UTF-8");
                schoolService.updateCollege(campus,oldCollege,college);
        }catch (Exception e){
            e.printStackTrace();
           return JsonData.error("学院信息更新失败");
        }
        return JsonData.progress("更新学院成功");
    }
    @OnlyRoot
    @PutMapping("/{campus}/{college}/{major}")
    @ResponseBody
    public JsonData updateMajor(@PathVariable("campus") String campus,@PathVariable("college") String college,@PathVariable("major") String oldMajor,@RequestBody Map map){
        String major = (String) map.get("major");
        try{
            campus = URLDecoder.decode(campus, "UTF-8");
            college = URLDecoder.decode(college, "UTF-8");
            major = URLDecoder.decode(major, "UTF-8");
            schoolService.updateMajor(campus,college,oldMajor,major);
        }catch (Exception e){
            e.printStackTrace();
            return JsonData.error("专业信息更新失败");
        }
        return JsonData.progress("更新专业成功");
    }
    @OnlyRoot
    @PostMapping("/")
    @ResponseBody
    public JsonData insertMajor(@RequestBody Major major){
        try{
            schoolService.insertMajor(major);
        }catch (Exception e){
            return JsonData.error("添加学校信息失败");
        }
        return  JsonData.progress("添加学校信息成功");
    }
    @OnlyRoot
    @DeleteMapping("/{campus}")
    @ResponseBody
    public JsonData deleteCampus(@PathVariable("campus") String campus){

        try{
            campus = URLDecoder.decode(campus, "UTF-8");
            schoolService.deleteCampus(campus);
        }catch (Exception e){
            e.printStackTrace();
            return   JsonData.error("校区信息更新失败");
        }
        return JsonData.progress("更新校区成功");
    }
    @OnlyRoot
    @DeleteMapping("/{campus}/{college}")
    @ResponseBody
    public JsonData deleteCollege(@PathVariable("campus") String campus,@PathVariable("college") String college){

        try{
            campus =  URLDecoder.decode(campus, "UTF-8");
            college = URLDecoder.decode(college, "UTF-8");
            schoolService.deleteCollege(campus,college);
        }catch (Exception e){
            e.printStackTrace();
            return JsonData.error("学院信息更新失败");
        }
        return JsonData.progress("更新学院成功");
    }
    @OnlyRoot
    @DeleteMapping("/{campus}/{college}/{major}")
    @ResponseBody
    public JsonData deleteMajor(@PathVariable("campus") String campus,@PathVariable("college") String college,@PathVariable("major") String major){

        try{
            campus = URLDecoder.decode(campus, "UTF-8");
            college = URLDecoder.decode(college, "UTF-8");
            major = URLDecoder.decode(major, "UTF-8");
            schoolService.deleteMajor(campus,college,major);
        }catch (Exception e){
            e.printStackTrace();
            return JsonData.error("专业信息更新失败");
        }
        return JsonData.progress("更新专业成功");
    }

}
