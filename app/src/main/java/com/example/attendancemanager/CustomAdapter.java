package com.example.attendancemanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<Word> {


    public CustomAdapter(@NonNull Context context, int resource, @NonNull List<Word> objects) {
        super(context,0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.row, parent, false);
        }

        Word word = getItem(position);

        TextView tSubject = (TextView) listItemView.findViewById(R.id.subject_id);
        tSubject.setText(word.getwSubject()+" :");

        String mtotal = word.getwPresent() +  " / " +word.getwTotal();

        TextView ttotal = (TextView) listItemView.findViewById(R.id.total_id);
        ttotal.setText(mtotal);

        int mPresent = Integer.parseInt(word.getwPresent());
        int wtotal = Integer.parseInt(word.getwTotal());

        float mPercent;
        if(wtotal == 0) {
            mPercent = 0;
        }else {
            mPercent = Math.round((float) mPresent / wtotal *10000)/100;
        }
        TextView tPercent = (TextView) listItemView.findViewById(R.id.percent_id);
        tPercent.setText(mPercent+"%");

        return listItemView;

    }
}
