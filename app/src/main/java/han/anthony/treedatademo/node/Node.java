package han.anthony.treedatademo.node;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by senior on 2016/10/13.
 */

public class Node {
    private int id;
    private int pid=0;
    private String label;
    private Node parent;
    private List<Node> children=new ArrayList<>();
    private int icon;
    private boolean isExpand=false;

    public Node(int id,int pid,String label){
        this.id=id;
        this.pid=pid;
        this.label=label;
    }

    public int getLevel(){
        return parent==null?0:parent.getLevel()+1;
    }
    /**
     * 如果自己的状态改变为收缩,遍历子节点设置为收缩
     */
    public Node setExpand(boolean expand) {
        isExpand = expand;
        if(!isExpand()){
            for(Node node:children){
                node.setExpand(false);
            }
        }
        return this;
    }
    public boolean isLeaf(){
        return children.size()==0;
    }

    /**
     *
     */
    public boolean isRoot(){
        return pid==0;
    }

    public List<Node> getChildren() {
        return children;
    }

    public Node addChild(Node child) {
        children.add(child);
        return this;
    }
    public boolean isParentExpand(){
        if(parent==null){
            return false;
        }
        return parent.isExpand();
    }

    public int getIcon() {
        return icon;
    }

    public Node setIcon(int icon) {
        this.icon = icon;
        return this;
    }

    public int getId() {
        return id;
    }

    public Node setId(int id) {
        this.id = id;
        return this;
    }

    public boolean isExpand() {
        return isExpand;
    }



    public String getLabel() {
        return label;
    }

    public Node setLabel(String label) {
        this.label = label;
        return this;
    }

    public Node getParent() {
        return parent;
    }

    public Node setParent(Node parent) {
        this.parent = parent;
        return this;
    }

    public int getPid() {
        return pid;
    }

    public Node setPid(int pid) {
        this.pid = pid;
        return this;
    }
}
