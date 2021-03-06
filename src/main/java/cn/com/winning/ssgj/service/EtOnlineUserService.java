package cn.com.winning.ssgj.service;

import java.util.List;

import cn.com.winning.ssgj.domain.EtOnlineUser;
import cn.com.winning.ssgj.domain.EtUserInfo;

/**
 *
 *
 * @author SSGJ
 * @date 2018-01-18 10:11:48
 */
public interface EtOnlineUserService {

    Integer createEtOnlineUser(EtOnlineUser t);

    int modifyEtOnlineUser(EtOnlineUser t);

    int removeEtOnlineUser(EtOnlineUser t);

    EtOnlineUser getEtOnlineUser(EtOnlineUser t);

    List<EtOnlineUser> getEtOnlineUserList(EtOnlineUser t);

    Integer getEtOnlineUserCount(EtOnlineUser t);

    List<EtOnlineUser> getEtOnlineUserPaginatedList(EtOnlineUser t);
    

    void generateEtOnlineUser(EtOnlineUser etOnlineUser,String path);


    void createEtOnlineUserList(List<List<Object>> userList,EtOnlineUser etOnlineUser,Long serialNo);
}