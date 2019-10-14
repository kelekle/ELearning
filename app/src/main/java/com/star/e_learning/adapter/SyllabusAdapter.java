package com.star.e_learning.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.star.e_learning.R;

import java.util.List;

public class SyllabusAdapter extends BaseExpandableListAdapter {


    List<String> mGroupList;//一级List
    List<List<String>> mChildList;//二级List 注意!这里是List里面套了一个List<String>,实际项目你可以写一个pojo类来管理2层数据


    public SyllabusAdapter(List<String> groupList, List<List<String>> childList){
        mGroupList = groupList;
        mChildList = childList;

    }

    @Override
    public int getGroupCount() {//返回第一级List长度
        return mGroupList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {//返回指定groupPosition的第二级List长度
        return mChildList.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {//返回一级List里的内容
        return mGroupList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {//返回二级List的内容
        return mChildList.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {//返回一级View的id 保证id唯一
        return groupPosition;
    }
    @Override
    public long getChildId(int groupPosition, int childPosition) {//返回二级View的id 保证id唯一
         return groupPosition + childPosition;
    }
    /** * 指示在对基础数据进行更改时子ID和组ID是否稳定 * @return */
    @Override
    public boolean hasStableIds() {
        return true;
    }
    /** * 返回一级父View */
    @Override public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_title_item, parent,false);
        ((TextView)convertView).setText((String)getGroup(groupPosition)); return convertView;
    }
    /** * 返回二级子View */
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.second_title_item, parent,false);
        ((TextView)convertView).setText((String)getChild(groupPosition,childPosition));
        return convertView; } /** * 指定位置的子项是否可选 */
        @Override public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
}
