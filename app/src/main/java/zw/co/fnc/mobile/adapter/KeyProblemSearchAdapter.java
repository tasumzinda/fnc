package zw.co.fnc.mobile.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import zw.co.fnc.mobile.R;
import zw.co.fnc.mobile.business.domain.*;

import java.util.ArrayList;

/**
 * Created by User on 3/12/2017.
 */
public class KeyProblemSearchAdapter extends ArrayAdapter<KeyProblem> {


    private Context context;
    private ArrayList<KeyProblem> list;
    TextView driverOfStunting;
    TextView indicator;
    TextView intervention;
    TextView action;
    TextView resources;
    TextView challenges;
    TextView strategies;

    public KeyProblemSearchAdapter(Context context, ArrayList<KeyProblem> list){
        super(context, R.layout.list_view_item, list);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View newView = convertView;
        if(newView == null){
            LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
            newView = layoutInflater.inflate(R.layout.list_view_item, null);
            driverOfStunting = (TextView) newView.findViewById(R.id.driverOfStunting);
            indicator = (TextView) newView.findViewById(R.id.indicator);
            intervention = (TextView) newView.findViewById(R.id.intervention);
            action = (TextView) newView.findViewById(R.id.action);
            resources = (TextView) newView.findViewById(R.id.resources);
            challenges = (TextView) newView.findViewById(R.id.challenges);
            strategies = (TextView) newView.findViewById(R.id.strategies);
        }
        KeyProblem driver = list.get(position);
        driverOfStunting.setText(driver.keyProblemCategory.name);
        String indicators = "";
        for(Indicator i : Indicator.findByKeyProblem(driver)){
            String name = i.name + "@";
            indicators += name.replace("@", System.getProperty("line.separator")) + System.getProperty("line.separator");
        }
        indicator.setText(indicators);
        String interventions = "";
        for(InterventionCategory cat : InterventionCategory.findByKeyProblem(driver)){
            String name = cat.name + "@";
            interventions += name.replace("@", System.getProperty("line.separator")) + System.getProperty("line.separator");
        }
        intervention.setText(interventions);
        String actionCategory = "";
        for(ActionRequired action: ActionRequired.findByKeyProblem(driver)){
            String name = action.actionCategory.name + "@";
            actionCategory += name.replace("@", System.getProperty("line.separator")) + System.getProperty("line.separator");
        }
        action.setText(actionCategory);
        String resourcesNeeded = "";
        for(ActionRequired action : ActionRequired.findByKeyProblem(driver)){
            for(ResourcesNeededCategory resource : ResourcesNeededCategory.findByActionRequired(action)){
                String name = resource.name + "@";
                resourcesNeeded += name.replace("@", System.getProperty("line.separator")) + System.getProperty("line.separator");
            }
            resources.setText(resourcesNeeded);
            String potentialChallenges = "";
            for(PotentialChallengesCategory challenge : PotentialChallengesCategory.findByActionRequired(action)){
                String name = challenge.name + "@";
                potentialChallenges += name.replace("@", System.getProperty("line.separator")) + System.getProperty("line.separator");
            }
            challenges.setText(potentialChallenges);
            String strategiesToOvercome = "";
            for(StrategyCategory strategy : StrategyCategory.findByActionRequired(action)){
                String name = strategy.name + "@";
                strategiesToOvercome += name.replace("@", System.getProperty("line.separator")) + System.getProperty("line.separator");
            }
            strategies.setText(strategiesToOvercome);
        }
        return newView;
    }

    public void onDataSetChanged(ArrayList<KeyProblem> list){
        list.clear();
        list.addAll(list);
        notifyDataSetChanged();
    }
}
