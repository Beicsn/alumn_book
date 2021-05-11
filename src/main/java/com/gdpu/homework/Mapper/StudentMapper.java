package com.gdpu.homework.Mapper;

import com.gdpu.homework.Entity.Campus;
import com.gdpu.homework.Entity.College;
import com.gdpu.homework.Entity.Major;
import com.gdpu.homework.Entity.Student;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import javax.websocket.server.PathParam;
import java.util.List;

@Repository
public interface StudentMapper {
    @Select("select distinct campus from student")
    public List<Campus>  getCampus();
    @Select("select distinct campus,college from student")
    public List<College>  getCollege();
    @Select("select distinct campus,college,major from student")
    public List<Major>  getMajor();
    @Select("select * from student")
    public List<Student> getAllStudent();
    @Select("select * from student where campus = #{campus}")
    public List<Student> getStudentsByCampus(String campus);
    @Select("select * from student where campus = #{campus} and college = #{college}")
    public List<Student> getStudentsByCollege(@Param("campus") String campus,@Param("college") String college);
    @Select("select * from student where campus = #{campus} and college = #{college} and major = #{major}")
    public List<Student> getStudentsByMajor(@Param("campus") String campus,@Param("college") String college,@Param("major") String major);
    @Select("select * from student where campus = #{campus} and name  like '%${name}%'")
    public  List<Student> getStudentsByCampusAndName (@Param("campus")String campus,@Param("name") String name);
    @Select("select * from student where campus = #{campus} and college = #{college} and name  like '%${name}%'")
    public  List<Student> getStudentsByCollegeAndName (@Param("campus")String campus,@Param("college") String college,@Param("name") String name);
    @Select("select * from student where campus = #{campus} and college = #{college} and major = #{major} and name  like '%${name}%'")
    public  List<Student> getStudentsByMajorAndName (@Param("campus")String campus,@Param("college") String college,
                                                       @Param("major") String major,@Param("name") String name);
    @Insert("insert into student(id,campus,college,major,grade,name,sex,weChat,phoneNumber)" +
            "values(#{id},#{campus},#{college},#{major},#{grade},#{name},#{sex},#{weChat},#{phoneNumber})")
    public void insertStudent(Student student);
    @Delete("delete from student where id =#{id}")
    public void deleteStudent(@Param("id") String id);
    @Update("update student set campus = #{campus},college = #{college},major = #{major},grade = #{grade},name = #{name},sex = #{sex},weChat = #{weChat},phoneNumber = #{phoneNumber} where id = #{id}")
    public void updateStudent(Student student);
    @Select("select weChat from student where id = #{id}")
    public String getWeChat(@Param("id") String id);
    @Select("select phoneNumber from student where id = #{id}")
    public String getPhoneNumber(@Param("id")String id);
    @Update("update student set phoneNumber = #{phoneNumber} where id = #{id}")
    public void updatePhoneNumber( @Param("id") String id,@Param("phoneNumber") String phoneNumber);
    @Update("update student set weChat = #{weChat} where id = #{id}")
    public void updateWeChat(@Param("id") String id,@Param("weChat") String weChat);
}
