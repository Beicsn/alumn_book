package com.gdpu.homework.ServiceImpl.Service;

import com.gdpu.homework.Entity.Campus;
import com.gdpu.homework.Entity.College;
import com.gdpu.homework.Entity.Major;
import com.gdpu.homework.Entity.Student;

import java.util.List;

public interface StudentService {
    public List<Student> getAllStudent();
    public List<Campus> getCampus();
    public List<College> getCollege();
    public List<Major> getMajor();
    public List<Student> getStudentsByCampus(String campus);
    public List<Student> getStudentsByCollege(String campus,String college);
    public List<Student> getStudentsByMajor(String campus,String college,String major);
    public List<Student> getStudentsByCampusAndName(String campus,String name);
    public List<Student> getStudentsByCollegeAndName(String campus,String college,String name);
    public List<Student> getStudentsByMajorAndName(String campus,String college,String major,String name);
    public void insertStudent(Student student);
    public void deleteStudent(String id);
    public void updateStudent(Student student);
    public String getWeChat(String id);
    public String getPhoneNumber(String id);
    public void updatePhoneNumber(String id,String phoneNumber);
    public void updateWeChat(String id,String weChat);
}
