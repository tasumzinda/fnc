package zw.co.fnc.mobile.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import zw.co.fnc.mobile.business.domain.Indicator;
import zw.co.fnc.mobile.business.domain.InterventionCategory;
import zw.co.fnc.mobile.business.domain.KeyProblem;

import java.util.ArrayList;

/**
 * Created by User on 3/10/2017.
 */
public class KeyProblemRecyclerAdapter extends RecyclerView.Adapter<ListItemViewHolder> {

    private ArrayList<KeyProblem> list;
    private int layout;

    public KeyProblemRecyclerAdapter(ArrayList<KeyProblem> list, int layout){
        this.layout = layout;
        this.list = list;
    }
    @Override
    public ListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new ListItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ListItemViewHolder holder, int position) {
        KeyProblem driver = list.get(position);
        holder.driverOfStunting.setText(driver.keyProblemCategory.name);
        String indicators = "";
        for(Indicator i : driver.indicators){
            String name = i.name + "@";
            indicators += name.replace("@", System.getProperty("line.separator")) + System.getProperty("line.separator");
        }
        holder.indicator.setText(indicators);
        for(InterventionCategory cat : driver.interventions){
            holder.intervention.setText(cat.name);
        }
        holder.action.setText("Add Action Required");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
