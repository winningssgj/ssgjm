package cn.com.winning.ssgj.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;


import cn.com.winning.ssgj.base.helper.SSGJHelper;
import cn.com.winning.ssgj.base.util.StringUtil;
import cn.com.winning.ssgj.domain.expand.NodeTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.winning.ssgj.dao.SysModPopedomDao;
import cn.com.winning.ssgj.domain.SysModPopedom;
import cn.com.winning.ssgj.service.SysModPopedomService;

/**
 * @author SSGJ
 * @date 2018-01-18 10:11:48
 */
@Service
public class SysModPopedomServiceImpl implements SysModPopedomService {

    @Resource
    private SysModPopedomDao sysModPopedomDao;
    @Autowired
    private SSGJHelper ssgjHelper;



    public Integer createSysModPopedom(SysModPopedom t) {
        return this.sysModPopedomDao.insertEntity(t);
    }


    public SysModPopedom getSysModPopedom(SysModPopedom t) {
        return this.sysModPopedomDao.selectEntity(t);
    }


    public Integer getSysModPopedomCount(SysModPopedom t) {
        return (Integer) this.sysModPopedomDao.selectEntityCount(t);
    }


    public List<SysModPopedom> getSysModPopedomList(SysModPopedom t) {
        return this.sysModPopedomDao.selectEntityList(t);
    }


    public int modifySysModPopedom(SysModPopedom t) {
        return this.sysModPopedomDao.updateEntity(t);
    }


    public int removeSysModPopedom(SysModPopedom t) {
        return this.sysModPopedomDao.deleteEntity(t);
    }


    public List<SysModPopedom> getSysModPopedomPaginatedList(SysModPopedom t) {
        return this.sysModPopedomDao.selectEntityPaginatedList(t);
    }

    @Override

    public List<Long> getModuleIdList(SysModPopedom modPopedom) {
        return this.sysModPopedomDao.selectModuleIdList(modPopedom);
    }

    @Override

    public void createSysModPopedomForIdList(String idList) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("ids", StringUtil.generateDeleteSqlString(idList, "ROLE_ID"));
        System.out.println(StringUtil.generateDeleteSqlString(idList, "ROLE_ID"));
        this.sysModPopedomDao.deleteSysModPopedomForIds(param);
        List<String> addModPopedom = StringUtil.generateStringList(idList);
        for (String s : addModPopedom) {
            SysModPopedom modPopedom = new SysModPopedom();
            modPopedom.setId(ssgjHelper.createSysRoleModId());
            modPopedom.setRoleId(Long.valueOf(s.split(",")[0]));
            modPopedom.setModId(Long.valueOf(s.split(",")[1]));
            this.sysModPopedomDao.insertEntity(modPopedom);
        }

    }

    @Override

    public void modifyModPopedomMapping(String idList) {
        if(idList.contains(":")){
            String[] modPopeInfoList = idList.split(";");
            for (String s : modPopeInfoList) {
                String[] modPope = s.split(":");
                SysModPopedom mod = new SysModPopedom();
                mod.setId(Long.parseLong(modPope[0]));
                mod.setModId(Long.parseLong(modPope[1]));
                mod.setRoleId(Long.parseLong(modPope[2]));
                mod.setPopedomCode(modPope[3]);
                this.sysModPopedomDao.updateEntity(mod);
            }
        }else{
            SysModPopedom mod = new SysModPopedom();
            mod.setRoleId(Long.parseLong(idList));
            mod.setPopedomCode(null);
            this.sysModPopedomDao.updateSysModPopedomAllPopedomCode(mod);
        }

    }

    @Override

    public List<SysModPopedom> getSysModPopedomHasPopedomList(SysModPopedom modPopedom) {
        return this.sysModPopedomDao.selectSysModPopedomHasPopedomList(modPopedom);
    }

    @Override
    public void createSysModPopedomByList(List<SysModPopedom> modPopedomList) {
        long roleId  = modPopedomList.get(0).getRoleId();
        SysModPopedom rolePopedom = new SysModPopedom();
        rolePopedom.setRoleId(roleId);
        this.sysModPopedomDao.deleteEntity(rolePopedom);
        for (SysModPopedom popedom : modPopedomList) {
            popedom.setId(ssgjHelper.createSysRoleModId());
            this.sysModPopedomDao.insertEntity(popedom);
        }
    }

    @Override
    public Set<String> getButtonFlagForPageByModUrlAndRoles(SysModPopedom modPopedom) {
        return this.sysModPopedomDao.selectButtonFlagForPageByModUrlAndRoles(modPopedom);
    }

}
