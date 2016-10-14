package han.anthony.treedatademo.bean;

import han.anthony.treedatademo.anotations.TreeNodeId;
import han.anthony.treedatademo.anotations.TreeNodeLabel;
import han.anthony.treedatademo.anotations.TreeNodePid;

/**
 * Created by senior on 2016/10/13.
 */

public class FileBean {
    @TreeNodeId(type = Integer.class)
    private int id;
    @TreeNodePid(type = Integer.class)
    private int pid;
    @TreeNodeLabel
    private String label;
    public FileBean(int id,int pid,String label){
        this.id=id;
        this.pid=pid;
        this.label=label;
    }
}
