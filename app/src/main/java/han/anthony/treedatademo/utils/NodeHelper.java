package han.anthony.treedatademo.utils;

import android.util.Log;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import han.anthony.treedatademo.R;
import han.anthony.treedatademo.anotations.TreeNodeId;
import han.anthony.treedatademo.anotations.TreeNodeLabel;
import han.anthony.treedatademo.anotations.TreeNodePid;
import han.anthony.treedatademo.node.Node;

/**
 * Created by senior on 2016/10/13.
 */

public class NodeHelper {
    public static Class TYPE_INTEGER = Integer.class;
    public static Class TYPE_SRING = String.class;
    private static final String TAG="NodeHelper";
    /**
     * 把普通的数据list转换成List<Node>并排序
     */
    public static <T> List<Node> convertToNodes(List<T> datas,int defaultExpandLevel) throws IllegalAccessException {
        List<Node> nodes = getOriginalNodes(datas);
        setParentAndChildren(nodes);
        List<Node> rootNodes = getRootNodes(nodes);
        List<Node> sortedNodes=getSortedNodes(rootNodes,defaultExpandLevel);
        Log.wtf(TAG, "convertToNodes: ");
        return sortedNodes;
    }

    /**
     * 过滤需要显示的Node
     */
    public static List<Node> getVisibleNodes(List<Node> nodes){
        List<Node> visibleNodes=new ArrayList<>();
        for(Node n:nodes){
            if(n.isRoot()||n.isParentExpand()){
                visibleNodes.add(n);
                setIcon(n);
            }else {
                Log.wtf(TAG, "getVisibleNodes: "+(n.getParent()==null));
            }
        }
        return visibleNodes;
    }
//下面是私有方法
    private static List<Node> getSortedNodes(List<Node> rootNodes,int defaultExpandLevel) {
        List<Node> sortedNodes=new ArrayList<>();
        for(Node n:rootNodes){
            addNode(sortedNodes,n,defaultExpandLevel,2);
        }
        return sortedNodes;
    }

    private static void addNode(List<Node> sortedNodes, Node node, int defaultExpandLevel, int level) {
        if(defaultExpandLevel>=level){
            node.setExpand(true);
        }
        sortedNodes.add(node);
        if(node.isLeaf()){
            return;
        }
        for(Node n:node.getChildren()){
            addNode(sortedNodes,n,defaultExpandLevel,level+1);
        }
    }

    /**
     * 获得id,pid,label
     */
    private static <T> List<Node> getOriginalNodes(List<T> datas) throws IllegalAccessException {
        int id = -1;
        int pid = -1;
        String label = null;
        List<Node> nodes = new ArrayList<>();
        Node node;
        /**
         * 设置Node的id,pid,label
         */
        for (T t : datas) {
            Class clazz = t.getClass();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {

                TreeNodeId a;
                if ((a = field.getAnnotation(TreeNodeId.class)) != null) {
                    field.setAccessible(true);
                    if (a.type() == TYPE_INTEGER) {
                        id = field.getInt(t);
                        continue;
                    } else if (a.type() == TYPE_SRING) {
                        id = (int) field.get(t);
                        continue;
                    } else {
                        throw new IllegalAccessException("非法的注解值,type不为Integer.class 也不为String.class");
                    }
                }
                TreeNodePid b;
                if ((b = field.getAnnotation(TreeNodePid.class)) != null) {
                    field.setAccessible(true);
                    if (b.type() == TYPE_INTEGER) {
                        pid = field.getInt(t);
                        continue;
                    } else if (b.type() == TYPE_SRING) {
                        pid = (int) field.get(t);
                        continue;
                    } else {
                        throw new IllegalAccessException("非法的注解值,type不为Integer.class 也不为String.class");
                    }
                }
                if (field.getAnnotation(TreeNodeLabel.class) != null) {
                    field.setAccessible(true);
                    label = (String) field.get(t);
                }
            }
            node = new Node(id, pid, label);
            nodes.add(node);
        }
        return nodes;
    }

    /**
     * 设置父子关系
     */
    private static void setParentAndChildren(List<Node> nodes) {
        for (int i = 0; i < nodes.size(); i++) {
            Node nodeI = nodes.get(i);
            for (int j = i + 1; j < nodes.size(); j++) {
                Node nodeJ = nodes.get(j);
                if (nodeI.getId() == nodeJ.getPid()) {
                    nodeI.addChild(nodeJ);
                    nodeJ.setParent(nodeI);
                } else if (nodeJ.getId() == nodeI.getPid()) {
                    nodeJ.addChild(nodeI);
                    nodeI.setParent(nodeJ);
                }
            }
        }
    }

    private static List<Node> getRootNodes(List<Node> nodes) {
        List<Node> rootNodes = new ArrayList<>();
        for (Node n : nodes) {
            if (n.getParent() == null) {
                rootNodes.add(n);
            }
        }
        return rootNodes;
    }

    private static void setIcon(Node node) {
       if(node.isLeaf()){
           node.setIcon(-1);
       }else if(node.isExpand()){
           node.setIcon(R.mipmap.tree_ex);
       }else {
           node.setIcon(R.mipmap.tree_ec);
       }

    }
}

