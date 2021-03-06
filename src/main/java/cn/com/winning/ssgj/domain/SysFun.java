package cn.com.winning.ssgj.domain;

import java.io.Serializable;

import cn.com.winning.ssgj.domain.expand.NodeTree;
import org.apache.ibatis.type.Alias;
import cn.com.winning.ssgj.domain.BaseDomain;

/**
 *
 *
 * @author SSGJ
 * @date 2018-01-18 10:11:46
 */

@Alias("sysFun")
public class SysFun extends BaseDomain implements Serializable {

    private static final long serialVersionUID = -1L;

    private Long id;

    private String funCode;

    private String funName;

    private String iconPath;

    private Integer orderValue;

    private Integer isDel;

    private NodeTree nodeTree = new NodeTree();

    public SysFun() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFunCode() {
        return funCode;
    }

    public void setFunCode(String funCode) {
        this.funCode = funCode;
    }

    public String getFunName() {
        return funName;
    }

    public void setFunName(String funName) {
        this.funName = funName;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public Integer getOrderValue() {
        return orderValue;
    }

    public void setOrderValue(Integer orderValue) {
        this.orderValue = orderValue;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public NodeTree getNodeTree(){
        nodeTree.setId(this.id);
        nodeTree.setNodeId(this.id);
        nodeTree.setText(this.funName + '['+funCode+']');
        return nodeTree;
    }
}