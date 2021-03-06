package cn.com.winning.ssgj.dao;

import cn.com.winning.ssgj.domain.SysHospitalDept;
import cn.com.winning.ssgj.dao.EntityDao;

import java.util.List;

/**
 * Coder AutoGenerator generate.
 *
 * @author Coder AutoGenerator
 * @date 2018-03-23 15:16:59
 */
public interface SysHospitalDeptDao extends EntityDao<SysHospitalDept> {


    public List<SysHospitalDept> selectSysHospitalDeptPageListByFuzzy(SysHospitalDept dept);
    public int selectSysHospitalDeptCountByFuzzy(SysHospitalDept dept);

    int selectSysHospitalDeptName(SysHospitalDept dept);
}
