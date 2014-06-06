package com.arakitech.worldclock;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.ListFragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Date;
import java.util.TimeZone;

public class TimeListFragment extends ListFragment {

    public TimeListFragment() {
    }

    public static TimeListFragment newInstance() {
        return new TimeListFragment();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        setListAdapter(new TimeZoneAdapter(activity));
    }

    private static class TimeZoneAdapter extends BaseAdapter {

        private static final TimeZone[] TIME_ZONES = {
                TimeZone.getTimeZone("Australia/Sydney"),
                TimeZone.getTimeZone("Asia/Tokyo"),
                TimeZone.getTimeZone("Europe/London"),
                TimeZone.getTimeZone("America/New_York"),
                TimeZone.getTimeZone("America/Los_Angeles"),
        };

        private final Context mContext;
        private final DateFormat mDateFormat;
        private final DateFormat mTimeFormat;

        public TimeZoneAdapter(Context context) {
            mContext = context;
            mDateFormat = android.text.format.DateFormat.getLongDateFormat(mContext);
            mTimeFormat = android.text.format.DateFormat.getTimeFormat(mContext);
        }

        @Override
        public int getCount() {
            return TIME_ZONES.length;
        }

        @Override
        public TimeZone getItem(int position) {
            return TIME_ZONES[position];
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
                view = LayoutInflater.from(mContext).inflate(R.layout.item_time, parent, false);
                holder = new ViewHolder();
                holder.timeZone = (TextView) view.findViewById(R.id.time_zone);
                holder.time = (TextView) view.findViewById(R.id.time);
                view.setTag(holder);
            } else {
                view = convertView;
                holder = (ViewHolder) view.getTag();
            }
            TimeZone timeZone = getItem(position);
            holder.timeZone.setText(timeZone.getDisplayName());
            mDateFormat.setTimeZone(timeZone);
            mTimeFormat.setTimeZone(timeZone);
            Date now = new Date();
            holder.time.setText(Html.fromHtml("<small>" + mDateFormat.format(now) + "</small> "
                    + mTimeFormat.format(now)));
            return view;
        }

        private static class ViewHolder {
            TextView timeZone;
            TextView time;
        }

    }

}
