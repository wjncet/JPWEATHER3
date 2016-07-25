package app.oukanan.gtune.jpweather3.lvitem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import app.oukanan.gtune.jpweather3.R;

/**
 * Created by 王佳楠 on 2016/07/26.
 */
public class AreaAdapter extends ArrayAdapter<Area> {

    private int resourceId;

    public AreaAdapter(Context context, int textViewResourceId, List<Area> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Area area = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.areaName = (TextView) view.findViewById(R.id.area_name);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.areaName.setText(area.getName());
        return view;
    }

    class ViewHolder {
        TextView areaName;
    }
}
