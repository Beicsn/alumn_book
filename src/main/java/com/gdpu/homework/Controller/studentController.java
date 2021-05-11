package com.gdpu.homework.Controller;

import com.gdpu.homework.Config.OnlyRoot;
import com.gdpu.homework.Config.SexValidator2;
import com.gdpu.homework.Entity.*;
import com.gdpu.homework.Entity.Config.JsonData;
import com.gdpu.homework.ServiceImpl.Service.StudentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/vue/student")
public class studentController {
    @Autowired
    StudentService studentService;

    @GetMapping("/")
    @ResponseBody
    public JsonData getStudents() {
        List<Student> students = studentService.getAllStudent();
        return JsonData.success(students);
    }

    @GetMapping("/campus")
    @ResponseBody
    public List<Campus> getCampus() {
        List<Campus> campus = studentService.getCampus();
        return campus;

    }

    @GetMapping("/campus/college")
    @ResponseBody
    public List<ListCamCollege> getCollege() {
        //获取不重复校区
        List<Campus> campus = studentService.getCampus();
        //获取所有不重复的学院
        List<College> college = studentService.getCollege();
        //定义一个数组，此数组包含n个对象，此对象有校区名和其名下的学院
        List<ListCamCollege> ListListCamCol = new ArrayList<>();
        for (int i = 0; i < campus.size(); i++) {
            //生成一个对象有校区名和其名下的学院
            ListCamCollege camCol = new ListCamCollege();
            //此数组保存相同校区的学院
            List<String> CamCollege = new ArrayList<>();
            //将校区名保存
            camCol.setCampus(campus.get(i).getCampus());
            for (int k = 0; k < college.size(); k++) {
                //如果校区名相同，将所有的学院保存在CamCollege数组中
                if (college.get(k).getCampus().equals(camCol.getCampus())) {
                    CamCollege.add(college.get(k).getCollege());

                }
            }

            camCol.setCollege(CamCollege);
            ListListCamCol.add(camCol);
        }


        return ListListCamCol;

    }

    @GetMapping("/campus/college/major")
    @ResponseBody
    public Object getMajor() {
        List<Campus> campus = studentService.getCampus();
        List<Major> major = studentService.getMajor();
        List<College> college = studentService.getCollege();
        //保存所有校区的所有学院和专业
        List<ListCamColMajor> listListCamColMajor = new ArrayList<>();
        //先将不重复的校区存起来
        for (int n = 0; n < campus.size(); n++) {
            //保存单个校区内所有学院和专业并且包含校区名
            ListCamColMajor listCamColMajor = new ListCamColMajor();
            //设置校区名
            listCamColMajor.setCampus(campus.get(n).getCampus());
            //保存相同校区的所有学院和专业(不包含校区名)
            List<ListColMajor> listColMajor = new ArrayList<>();
            for (int i = 0; i < college.size(); i++) {
                //保存相同学院的专业(不包含学院名）
                List<String> sameColMajor = new ArrayList<>();
                //保存相同学院的专业(包含学院名）
                ListColMajor colMajor = new ListColMajor();
                //设置学院名
                if (college.get(i).getCampus().equals(listCamColMajor.getCampus())) {
                    colMajor.setCollege(college.get(i).getCollege());
                    for (int k = 0; k < major.size(); k++) {
                        if (major.get(k).getCampus().equals(college.get(i).getCampus()) && major.get(k).getCollege().equals(college.get(i).getCollege())) {
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

    @GetMapping("/{campus}/{currentPage}/{pageSize}")
    @ResponseBody
    public JsonData getTotalNum(@PathVariable(name = "campus") String getCampus, @PathVariable("currentPage") int currentPage, @PathVariable("pageSize") int pageSize) {
        String campus = null;

        try {
            campus = URLDecoder.decode(getCampus, "UTF-8");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        PageHelper.startPage(currentPage, pageSize);
        List<Student> students = studentService.getStudentsByCampus(campus);
        PageInfo pageInfo = new PageInfo(students);
        HashMap map = new HashMap();
        map.put("students", pageInfo.getList());
        map.put("totalNum", pageInfo.getTotal());

        return JsonData.success(map);
    }

    @GetMapping("/{campus}/{college}/{currentPage}/{pageSize}")
    @ResponseBody
    public JsonData getStudentsByCollege(@PathVariable(name = "campus") String getCampus, @PathVariable("college") String getCollege,
                                         @PathVariable("currentPage") int currentPage, @PathVariable("pageSize") int pageSize) {
        String campus = null;
        String college = null;
        try {
            campus = URLDecoder.decode(getCampus, "UTF-8");
            college = URLDecoder.decode(getCollege, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        PageHelper.startPage(currentPage, pageSize);
        List<Student> students = studentService.getStudentsByCollege(campus, college);
        PageInfo pageInfo = new PageInfo(students);
        HashMap map = new HashMap();
        map.put("students", pageInfo.getList());
        map.put("totalNum", pageInfo.getTotal());

        return JsonData.success(map);


    }

    @GetMapping("/{campus}/{college}/{major}/{currentPage}/{pageSize}")
    @ResponseBody
    public JsonData getStudentsByCollege(@PathVariable(name = "campus") String getCampus, @PathVariable("college") String getCollege,
                                         @PathVariable("major") String getMajor, @PathVariable("currentPage") int currentPage, @PathVariable("pageSize") int pageSize) {
        String campus = null;
        String college = null;
        String major = null;
        try {
            campus = URLDecoder.decode(getCampus, "UTF-8");
            college = URLDecoder.decode(getCollege, "UTF-8");
            major = URLDecoder.decode(getMajor, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        PageHelper.startPage(currentPage, pageSize);
        List<Student> students = studentService.getStudentsByMajor(campus, college, major);
        PageInfo pageInfo = new PageInfo(students);
        HashMap map = new HashMap();
        map.put("students", pageInfo.getList());
        map.put("totalNum", pageInfo.getTotal());

        return JsonData.success(map);


    }

    @GetMapping("/{campus}/{currentPage}/{pageSize}/search/{name}")
    @ResponseBody
    public JsonData getSomeOneStudent(@PathVariable("campus") String getCampus, @PathVariable("currentPage") int currentPage,
                                      @PathVariable("pageSize") int pageSize, @PathVariable("name") String getName) {
        String campus = null;
        String name = null;
        try {
            campus = URLDecoder.decode(getCampus, "UTF-8");
            name = URLDecoder.decode(getName, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }


        PageHelper.startPage(currentPage , pageSize);
        List<Student> students = studentService.getStudentsByCampusAndName(campus, name);

        PageInfo pageInfo = new PageInfo(students);
        HashMap map = new HashMap();
        map.put("students", pageInfo.getList());
        map.put("totalNum", pageInfo.getTotal());

        return JsonData.success(map);
    }

    @GetMapping("/{campus}/{college}/{currentPage}/{pageSize}/search/{name}")
    @ResponseBody
    public JsonData getSomeOneStudent(@PathVariable("campus") String getCampus, @PathVariable("college") String getCollege,
                                      @PathVariable("currentPage") int currentPage,
                                      @PathVariable("pageSize") int pageSize, @PathVariable("name") String getName) {
        String campus = null;
        String college = null;
        String name = null;
        try {
            campus = URLDecoder.decode(getCampus, "UTF-8");
            college = URLDecoder.decode(getCollege, "UTF-8");
            name = URLDecoder.decode(getName, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }

        PageHelper.startPage(currentPage, pageSize);
        List<Student> students = studentService.getStudentsByCollegeAndName(campus, college, name);
        PageInfo pageInfo = new PageInfo(students);
        HashMap map = new HashMap();
        map.put("students", pageInfo.getList());
        map.put("totalNum", pageInfo.getTotal());

        return JsonData.success(map);
    }

    @GetMapping("/{campus}/{college}/{major}/{currentPage}/{pageSize}/search/{name}")
    @ResponseBody
    public JsonData getSomeOneStudent(@PathVariable("campus") String getCampus, @PathVariable("college") String getCollege,
                                      @PathVariable("major") String getMajor, @PathVariable("currentPage") int currentPage,
                                      @PathVariable("pageSize") int pageSize, @PathVariable("name") String getName) {
        String campus = null;
        String college = null;
        String major = null;
        String name = null;
        try {
            campus = URLDecoder.decode(getCampus, "UTF-8");
            college = URLDecoder.decode(getCollege, "UTF-8");
            major = URLDecoder.decode(getMajor, "UTF-8");
            name = URLDecoder.decode(getName, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }

        PageHelper.startPage(currentPage , pageSize);
        List<Student> students = studentService.getStudentsByMajorAndName(campus, college, major, name);
        PageInfo pageInfo = new PageInfo(students);
        HashMap map = new HashMap();
        map.put("students", pageInfo.getList());
        map.put("totalNum", pageInfo.getTotal());

        return JsonData.success(map);
    }
    //后面需要指明绑定的方法 原因:Spring不会验证您的其他方法，但WebDataBinder不允许您设置一个不接受绑定的bean的验证器。
    @InitBinder("insertStudent")
    public void initBinder(WebDataBinder dataBinder){
        dataBinder.setValidator(new SexValidator2(){

        });
    }
    @OnlyRoot
    @PostMapping("/")
    @ResponseBody
    //BindingResult bindingResult自定义注解用此收集错误
    public JsonData insertStudent(@RequestBody @Valid Student student, Errors errors) {
//        if(bindingResult.hasErrors()){
//            for(FieldError fieldError : bindingResult.getFieldErrors())
//               return JsonData.error(fieldError.getDefaultMessage());
//        }
          if(errors.hasErrors()){
              System.out.println("获取到错误");
              for(FieldError fieldError : errors.getFieldErrors())
                  return JsonData.error(fieldError.getDefaultMessage());
          }
        System.out.println(student);

        try {
            studentService.insertStudent(student);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonData.error("已存在该同学的通讯录");
        }

        return JsonData.progress("该同学通讯录保存成功");
    }
    @OnlyRoot
    @PutMapping("/")
    @ResponseBody
    public JsonData updateStudent(@RequestBody Student student) {

        System.out.println(student);

        try {
            studentService.updateStudent(student);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonData.error("更新失败");
        }

        return JsonData.progress("更新通讯录成功");
    }
    @OnlyRoot
    @DeleteMapping("/")
    @ResponseBody
    public JsonData deleteStudent(@RequestBody Map map) {
        String id = (String) map.get("id");
        try {
            studentService.deleteStudent(id);
        } catch (Exception e) {

            return JsonData.error("该学生的通讯录不存在,无法删除");
        }

        return JsonData.progress("该学生的通讯录删除成功");
    }

    @GetMapping("/weChat/{id}")
    @ResponseBody
    public JsonData getMyWeChat(@PathVariable("id") String id) {
        String weChat = null;
      try {
           weChat = studentService.getWeChat(id);
      } catch (Exception e){
          return  JsonData.error("获取微信失败");
      }
      return  JsonData.success(weChat);
    }
    @GetMapping("/phoneNumber/{id}")
    @ResponseBody
    public JsonData getPhoneNumber(@PathVariable("id") String id) {
        String phoneNumber = null;
        try {
            phoneNumber = studentService.getPhoneNumber(id);
        }catch (Exception e){
            return  JsonData.error("获取手机号失败");
        }
        return  JsonData.success(phoneNumber);
    }
    @PutMapping("/weChat/{id}")
    @ResponseBody
    public JsonData updateWeChat(@PathVariable("id") String id,@RequestBody Map map) {

        try {
            studentService.updateWeChat(id,(String) map.get("weChat"));
        } catch (Exception e){
            return  JsonData.error("更新微信失败");
        }
        return  JsonData.progress("更新微信成功");
    }
    @PutMapping("/phoneNumber/{id}")
    @ResponseBody
    public JsonData updatePhoneNumber(@PathVariable("id") String id,@RequestBody Map map) {

        try {
             studentService.updatePhoneNumber(id, (String) map.get("phoneNumber"));
        }catch (Exception e){
            e.printStackTrace();
            return  JsonData.error("更新手机号失败");
        }
        return  JsonData.progress("更新手机号成功");
    }
}