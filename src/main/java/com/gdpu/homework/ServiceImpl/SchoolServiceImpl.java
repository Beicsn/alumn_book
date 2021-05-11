package com.gdpu.homework.ServiceImpl;

import com.gdpu.homework.Entity.Campus;
import com.gdpu.homework.Entity.College;
import com.gdpu.homework.Entity.Major;
import com.gdpu.homework.Mapper.SchoolMapper;
import com.gdpu.homework.ServiceImpl.Service.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchoolServiceImpl implements SchoolService {
    @Autowired
    SchoolMapper schoolMapper;
    @Override
    public List<Campus> getCampus() {
        return schoolMapper.getCampus();
    }

    @Override
    public List<College> getCollege() {
        return schoolMapper.getCollege();
    }

    @Override
    public List<Major> getMajor() {
        return schoolMapper.getMajor();
    }
    @Override
    public void updateCampus(String oldCampus,String campus){
        schoolMapper.updateCampus(oldCampus,campus);
    }
    @Override
    public void updateCollege(String campus,String oldCollege,String college){
        schoolMapper.updateCollege(campus,oldCollege,college);
    }
    @Override
    public void updateMajor(String campus,String college,String oldMajor,String major){
        schoolMapper.updateMajor(campus,college,oldMajor,major);
    }
    public void insertMajor(Major major){
        schoolMapper.insertMajor(major);
    }

    @Override
    public void deleteCampus(String campus) {
        schoolMapper.deleteCampus(campus);
    }

    @Override
    public void deleteCollege(String campus, String college) {
        schoolMapper.deleteCollege(campus,college);
    }

    @Override
    public void deleteMajor(String campus, String college, String major) {
        schoolMapper.deleteMajor(campus,college,major);
    }
}
