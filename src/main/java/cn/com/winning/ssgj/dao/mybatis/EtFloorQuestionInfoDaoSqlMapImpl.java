package cn.com.winning.ssgj.dao.mybatis;

import org.springframework.stereotype.Service;

import cn.com.winning.ssgj.dao.EtFloorQuestionInfoDao;
import cn.com.winning.ssgj.domain.EtFloorQuestionInfo;
import cn.com.winning.ssgj.dao.mybatis.EntityDaoSqlMapImpl;

import java.util.List;

/**
 *
 *
 * @author SSGJ
 * @date 2018-01-18 10:11:47
 */
@Service
public class EtFloorQuestionInfoDaoSqlMapImpl extends EntityDaoSqlMapImpl<EtFloorQuestionInfo> implements EtFloorQuestionInfoDao {

    @Override
    public List<EtFloorQuestionInfo> selectEtFloorQuestionInfoWithHospitalList(EtFloorQuestionInfo t) {
        return this.getSqlSession().selectList("selectEtFloorQuestionInfoWithHospitalList",t);
    }

    @Override
    public List<EtFloorQuestionInfo> selectEtFloorQuestionInfoSummaryList(EtFloorQuestionInfo t) {
        return this.getSqlSession().selectList("selectEtFloorQuestionInfoSummaryList",t);
    }
}
