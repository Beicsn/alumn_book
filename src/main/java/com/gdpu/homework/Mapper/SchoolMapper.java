package com.gdpu.homework.Mapper;

import com.gdpu.homework.Entity.Campus;
import com.gdpu.homework.Entity.College;
import com.gdpu.homework.Entity.Major;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Repository
public interface SchoolMapper {
    @Select("select distinct campus from school")
    public List<Campus>  getCampus();
    @Select("select distinct campus,College from school")
    public List<College>  getCollege();
    @Select("select * from school")
    public List<Major>  getMajor();
    @Update("update school set campus = #{campus} where campus = #{oldCampus}")
    public void updateCampus(@Param("oldCampus") String oldCampus,@Param("campus") String campus);
    @Update("update school set college = #{college} where campus = #{campus} and college = #{oldCollege}")
    public void updateCollege(@Param("campus") String campus,@Param("oldCollege") String oldCollege,@Param("college") String college);
    @Update("update school set major = #{major} where campus = #{campus} and college = #{college} and major = #{oldMajor}")
    public void updateMajor(@Param("campus") String campus,@Param("college") String college,@Param("oldMajor") String oldMajor,@Param("major") String major);
    @Insert("insert into school(campus,college,major) values(#{campus},#{college},#{Major})")
    public void insertMajor(Major major);
    @Delete("delete from school where campus = #{campus}")
    public void deleteCampus(@Param("campus") String campus);
    @Delete("delete from school where campus = #{campus} and college = #{college}")
    public void deleteCollege(@Param("campus") String campus,@Param("college") String college);
    @Delete("delete from school where campus = #{campus} and college = #{college} and major = #{major}")
    public void deleteMajor(@Param("campus") String campus,@Param("college") String college,@Param("major") String major);
}
