package com.example.a442projects_the_big_brain_bros;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ListViewAdapter extends ArrayAdapter<String> {

    ArrayList<String> recipeTitles;
    ArrayList<String> recipeIcons;
    Context context;

    public ListViewAdapter(Activity context, ArrayList<String> title, ArrayList<String> icons) {
        super(context, R.layout.recipe_list_image, title);
        this.recipeTitles = title;
        this.recipeIcons = icons;
        this.context = context;
    }

    @Override
    public int getCount() {
        return recipeTitles.size();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        ViewHolder viewHolder = new ViewHolder();
//        if(convertView == null) {
//            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            convertView = inflater.inflate(R.layout.recipe_list_image, parent, false);
//            viewHolder.icon = (ImageView) convertView.findViewById(R.id.imageView3);
//            viewHolder.name = (TextView) convertView.findViewById(R.id.textView2);
//            convertView.setTag(viewHolder);
//        } else {
//            viewHolder = (ViewHolder)convertView.getTag();
//        }
//            viewHolder.icon.setImageDrawable(recipeIcons.get(position));
//            viewHolder.name.setText(recipeTitles.get(position));
//        return super.getView(position, convertView, parent);
        View r = convertView;
        ViewHolder viewHolder = null;
        if (r == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            r = inflater.inflate(R.layout.recipe_list_image, null, true);
            viewHolder = new ViewHolder(r);
            r.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) r.getTag();
        }

        Picasso.with(context).load(recipeIcons.get(position)).into(viewHolder.icon);
        viewHolder.name.setText(recipeTitles.get(position));
        return r;
    }


    static class ViewHolder {
        ImageView icon;
        TextView name;
        ViewHolder(View v) {
            icon = (ImageView) v.findViewById((R.id.imageView3));
            name = (TextView) v.findViewById((R.id.textView2));
        }
    }
}
