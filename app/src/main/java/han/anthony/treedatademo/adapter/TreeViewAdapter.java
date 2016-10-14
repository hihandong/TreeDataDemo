package han.anthony.treedatademo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import han.anthony.treedatademo.R;
import han.anthony.treedatademo.node.Node;
import han.anthony.treedatademo.utils.NodeHelper;

/**
 * Created by senior on 2016/10/14.
 */

public class TreeViewAdapter <T>extends RecyclerView.Adapter <TreeViewAdapter.ViewHolder>{
    private List<Node> mAllNodes;
    private List<Node> mVisibleNodes;
    private LayoutInflater mInflater;
    private OnItemClickListener mListener;
    public TreeViewAdapter(Context context,List<T> datas,int defaultExpandLevel) throws IllegalAccessException {
        mInflater=LayoutInflater.from(context);
        mAllNodes= NodeHelper.convertToNodes(datas,defaultExpandLevel);
        mVisibleNodes=NodeHelper.getVisibleNodes(mAllNodes);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.item_tree,parent,false));
    }
    @Override
    public int getItemCount() {
        return mVisibleNodes.size();
    }


    @Override
    public void onBindViewHolder(TreeViewAdapter.ViewHolder holder, final int position) {
        final Node node=mVisibleNodes.get(position);
        int imgId=node.getIcon();
        if(imgId==-1){
            holder.iv.setVisibility(View.INVISIBLE);
        }else {
            holder.iv.setVisibility(View.VISIBLE);
            holder.iv.setImageResource(imgId);
        }
        holder.tv.setText(mVisibleNodes.get(position).getLabel());
        holder.itemView.setPadding(node.getLevel()*40,3,3,3);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeExpandState(position);
                if(mListener!=null){
                    mListener.onItemClick(v,position);
                }
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                if(mListener!=null){
                    mListener.onItemLongClick(v,position);
                }
                return true;
            }
        });
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iv;
        TextView tv;
        public ViewHolder(View itemView) {
            super(itemView);
            iv= (ImageView) itemView.findViewById(R.id.tree_icon);
            tv= (TextView) itemView.findViewById(R.id.tree_text);
        }
    }

    /**
     * 改变Expand
     * @param position
     */
    private void changeExpandState(int position) {
        Node node=mVisibleNodes.get(position);
        node.setExpand(!node.isExpand());
        //Log.wtf(TAG, "changeExpandState: "+node.isExpand());
        mVisibleNodes=NodeHelper.getVisibleNodes(mAllNodes);
        notifyDataSetChanged();
    }


    /**
     * 长按可以增加节点
     */
    public void addExtraNode(int position, String s) {
        if(TextUtils.isEmpty(s)){
            return;
        }
        Node clickedNode=mVisibleNodes.get(position);
        int index=mAllNodes.indexOf(clickedNode);
        Node node=new Node(-1,clickedNode.getPid(),s);
        node.setIcon(-1).setParent(clickedNode.getParent());

        mAllNodes.add(index,node);
        mVisibleNodes=NodeHelper.getVisibleNodes(mAllNodes);
        notifyDataSetChanged();
    }



    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener=listener;
    }

    public interface OnItemClickListener{
        void onItemClick(View v, int position);
        void onItemLongClick(View v, int position);
    }
}
