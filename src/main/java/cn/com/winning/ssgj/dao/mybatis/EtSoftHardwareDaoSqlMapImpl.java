package cn.com.winning.ssgj.dao.mybatis;

import org.springframework.stereotype.Service;

import cn.com.winning.ssgj.dao.EtSoftHardwareDao;
import cn.com.winning.ssgj.domain.EtSoftHardware;
import cn.com.winning.ssgj.dao.mybatis.EntityDaoSqlMapImpl;

import java.util.List;

/**
 *
 *
 * @author SSGJ
 * @date 2018-01-18 10:11:47
 */
@Service
public class EtSoftHardwareDaoSqlMapImpl extends EntityDaoSqlMapImpl<EtSoftHardware> implements EtSoftHardwareDao {

    @Override
    public void insertEtSoftHardwareByList(List<EtSoftHardware> etSoftHardwares) {
        this.getSqlSession().insert("insertEtSoftHardwareByList",etSoftHardwares);
    }

    @Override
    public List<EtSoftHardware> selectEtSoftHardwareByProductInfo(EtSoftHardware etSoftHardware) {
        return this.getSqlSession().selectList("selectEtSoftHardwareByProductInfo",etSoftHardware);
    }
}
