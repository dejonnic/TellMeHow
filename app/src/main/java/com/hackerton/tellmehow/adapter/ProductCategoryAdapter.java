package com.hackerton.tellmehow.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hackerton.tellmehow.R;
import com.hackerton.tellmehow.model.Material;

import java.util.ArrayList;
import java.util.List;

public class ProductCategoryAdapter extends BaseAdapter {
    private final List<Item> mItems = new ArrayList<Item>();
    private final LayoutInflater mInflater;
    public static final int ID_TAG = 111;

    public ProductCategoryAdapter(Context context) {
        mInflater = LayoutInflater.from(context);

        mItems.add(new Item(1, "Bottles", R.drawable.prod_ic_milkbottle));
        mItems.add(new Item(2, "Cans", R.drawable.prod_ic_can));
        mItems.add(new Item(3, "Packaging", R.drawable.prod_ic_package));
        mItems.add(new Item(4, "Household", R.drawable.prod_ic_household));
        mItems.add(new Item(5, "Wearables", R.drawable.prod_ic_wearables));
        mItems.add(new Item(6, "Office Goods", R.drawable.prod_ic_office_goods));
        mItems.add(new Item(7, "Food Waste", R.drawable.prod_ic_food_waste));
        mItems.add(new Item(8, "Electronics", R.drawable.prod_ic_electronics));
    }

    public ProductCategoryAdapter(Context context, List<Material> materials, int categoryId) {
        mInflater = LayoutInflater.from(context);

        for(Material material : materials) {
            mItems.add(new Item(material.id, material.name, CategoryIconManager.getIcon(material.id, categoryId)));
        }
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Item getItem(int i) {
        return mItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return mItems.get(i).id;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Item item = getItem(i);

        View v = view;
        ImageView icon;
        TextView name;

        if (v == null) {
            v = mInflater.inflate(R.layout.item_product, viewGroup, false);
            v.setTag(R.id.icon, v.findViewById(R.id.icon));
            v.setTag(R.id.text, v.findViewById(R.id.text));
        }

        icon = (ImageView) v.getTag(R.id.icon);
        name = (TextView) v.getTag(R.id.text);

        icon.setImageResource(item.icon);
        name.setText(item.name);

        return v;
    }

    public class Item {
        int id;
        String name;
        int icon;

        Item(int id, String name, int icon) {
            this.id = id;
            this.name = name;
            this.icon = icon;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getIcon() {
            return icon;
        }

        public void setIcon(int icon) {
            this.icon = icon;
        }
    }
}