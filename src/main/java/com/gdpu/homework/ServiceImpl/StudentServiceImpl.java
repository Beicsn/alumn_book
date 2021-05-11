package com.gdpu.homework.ServiceImpl;

import com.gdpu.homework.Entity.Campus;
import com.gdpu.homework.Entity.College;
import com.gdpu.homework.Entity.Major;
import com.gdpu.homework.Entity.Student;
import com.gdpu.homework.Mapper.StudentMapper;
import com.gdpu.homework.ServiceImpl.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    StudentMapper studentMapper;

    @Override
    public List<Student> getAllStudent() {
      return   studentMapper.getAllStudent();
    }

    @Override
    public List<Campus> getCampus() {
        return  studentMapper.getCampus();
    }

    @Override
    public List<College> getCollege() {
        return studentMapper.getCollege();
    }

    @Override
    public List<Major> getMajor() {
        return studentMapper.getMajor();
    }

    @Override
    public List<Student> getStudentsByCampus(String campus) {

        return studentMapper.getStudentsByCampus(campus);
    }
    @Override
    public List<Student> getStudentsByCollege(String campus,String college){

        return  studentMapper.getStudentsByCollege(campus,college);
    }

    @Override
    public List<Student> getStudentsByMajor(String campus, String college, String major) {
        return  studentMapper.getStudentsByMajor(campus,college,major);
    }

    @Override
    public List<Student> getStudentsByCampusAndName(String campus, String name) {
        return  studentMapper.getStudentsByCampusAndName(campus,name);
    }

    @Override
    public List<Student> getStudentsByCollegeAndName(String campus, String college, String name) {
        return  studentMapper.getStudentsByCollegeAndName(campus,college,name);
    }
    @Override
    public List<Student> getStudentsByMajorAndName(String campus,String college,String major,String name){
        return studentMapper.getStudentsByMajorAndName(campus,college,major,name);
    }
    @Override
    public void insertStudent(Student student){
         studentMapper.insertStudent(student);
    }
    @Override
    public void deleteStudent(String id){
        studentMapper.deleteStudent(id);
    }
    public void updateStudent(Student student){
        studentMapper.updateStudent(student);
    }

    @Override
    public String getWeChat(String id) {
        return studentMapper.getWeChat(id);
    }

    @Override
    public String getPhoneNumber(String id) {
        return studentMapper. getPhoneNumber(id);
    }
    @Override
    public void updatePhoneNumber(String id,String phoneNumber){
        studentMapper.updatePhoneNumber(id,phoneNumber);
    }
    @Override
    public void updateWeChat(String id,String weChat){
        studentMapper.updateWeChat(id,weChat);
    }
}
