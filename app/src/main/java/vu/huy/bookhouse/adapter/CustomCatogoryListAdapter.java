package vu.huy.bookhouse.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import vu.huy.bookhouse.R;
import vu.huy.bookhouse.model.Category;

public class CustomCatogoryListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<Category> listTitle;
    private HashMap<Category, List<Category>> listItem;

    public CustomCatogoryListAdapter(Context context, List<Category> listTitle, HashMap<Category, List<Category>> listItem) {
        this.context = context;
        this.listTitle = listTitle;
        this.listItem = listItem;
    }

    @Override
    public int getGroupCount() {
        return listTitle.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return listItem.get(listTitle.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listTitle.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listItem.get(listTitle.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        Category title = (Category) getGroup(groupPosition);
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_group, null);
        }
        TextView txtTitle = convertView.findViewById(R.id.list_Cate_Title);
        txtTitle.setTypeface(null, Typeface.BOLD);
        txtTitle.setText(title.getCatName());

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        Category item = (Category) getChild(groupPosition, childPosition);
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, null);
        }
        TextView txtItem = convertView.findViewById(R.id.list_cate_item);
        txtItem.setTypeface(null, Typeface.BOLD);
        txtItem.setText(item.getCatName());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
