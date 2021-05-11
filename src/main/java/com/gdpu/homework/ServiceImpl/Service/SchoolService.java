package com.gdpu.homework.ServiceImpl.Service;

import com.gdpu.homework.Entity.Campus;
import com.gdpu.homework.Entity.College;
import com.gdpu.homework.Entity.Major;
import com.gdpu.homework.Entity.User;

import java.util.List;

public interface SchoolService {
    public List<Campus> getCampus();
    public List<College> getCollege();
    public List<Major> getMajor();
    public void updateCampus(String oldCampus,String campus);
    public void updateCollege(String campus,String oldCollege,String college);
    public void updateMajor(String campus,String college,String oldMajor,String major);
    public void insertMajor(Major major);
    public void deleteCampus(String campus);
    public void deleteCollege(String campus,String college);
    public void deleteMajor(String campus,String college,String major);


}
