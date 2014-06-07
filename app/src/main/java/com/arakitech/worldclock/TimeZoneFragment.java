package com.arakitech.worldclock;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;
import java.util.TimeZone;

public class TimeZoneFragment extends ListFragment {

    public static TimeZoneFragment newInstance() {
        return new TimeZoneFragment();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        setListAdapter(new TimeZoneAdapter(activity));
    }

    public static class TimeZoneAdapter extends BaseAdapter {

        private static final List<String> TIME_ZONE_IDS = Arrays.asList(TimeZone.getAvailableIDs());

        private final Context mContext;

        public TimeZoneAdapter(Context context) {
            mContext = context;
        }

        @Override
        public int getCount() {
            return TIME_ZONE_IDS.size();
        }

        @Override
        public TimeZone getItem(int position) {
            return TimeZone.getTimeZone(TIME_ZONE_IDS.get(position));
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            ViewHolder holder;
            if (null == convertView) {
                view = LayoutInflater.from(mContext)
                        .inflate(android.R.layout.simple_list_item_2, parent, false);
                holder = new ViewHolder();
                holder.text1 = (TextView) view.findViewById(android.R.id.text1);
                holder.text2 = (TextView) view.findViewById(android.R.id.text2);
                view.setTag(holder);
            } else {
                view = convertView;
                holder = (ViewHolder) view.getTag();
            }
            TimeZone timeZone = getItem(position);
            holder.text1.setText(TimeZoneInterpreter.from(mContext).getFullName(timeZone.getID()));
            holder.text2.setText(String.valueOf(timeZone.getRawOffset()));
            return view;
        }

        public static class ViewHolder {
            TextView text1;
            TextView text2;
        }

    }

}
