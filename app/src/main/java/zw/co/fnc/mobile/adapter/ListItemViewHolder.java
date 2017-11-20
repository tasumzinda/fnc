package zw.co.fnc.mobile.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import zw.co.fnc.mobile.R;

/**
 * Created by User on 3/10/2017.
 */
public class ListItemViewHolder extends RecyclerView.ViewHolder {

    TextView driverOfStunting;
    TextView indicator;
    TextView intervention;
    TextView action;

    public ListItemViewHolder(View newView){
        super(newView);
        driverOfStunting = (TextView) newView.findViewById(R.id.driverOfStunting);
        indicator = (TextView) newView.findViewById(R.id.indicator);
        intervention = (TextView) newView.findViewById(R.id.intervention);
        action = (TextView) newView.findViewById(R.id.action);
    }
}
