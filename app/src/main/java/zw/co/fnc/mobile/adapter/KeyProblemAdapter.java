package zw.co.fnc.mobile.adapter;

import android.app.Activity;
import android.content.Context;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import zw.co.fnc.mobile.R;
import zw.co.fnc.mobile.business.domain.*;

import java.util.ArrayList;

/**
 * Created by User on 3/10/2017.
 */
public class KeyProblemAdapter extends ArrayAdapter<KeyProblem> {

    private Context context;
    private ArrayList<KeyProblem> list;
    TextView driverOfStunting;
    TextView indicator;
    TextView intervention;
    TextView action;
    TextView resources;
    TextView challenges;
    TextView strategies;

    public KeyProblemAdapter(Context context, ArrayList<KeyProblem> list){
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
        for(Indicator i : driver.indicators){
            String name = i.name + "@";
            indicators += name.replace("@", System.getProperty("line.separator")) + System.getProperty("line.separator");
        }
        indicator.setText(indicators);
        String interventions = "";
        for(InterventionCategory cat : driver.interventions){
            String name = cat.name + "@";
            interventions += name.replace("@", System.getProperty("line.separator")) + System.getProperty("line.separator");
        }
        intervention.setText(interventions);
        String actionCategory = "";
        String resourcesNeeded = "";
        String potentialChallenges = "";
        String strategiesToOvercome = "";
        for(InterventionCategory inter : driver.interventions){
            int i = 0;
            for(ActionRequired action : ActionRequired.findByInterventionCategory(inter)){
                String name = action.actionCategory.name + "@";
                actionCategory += name.replace("@", System.getProperty("line.separator")) + System.getProperty("line.separator");
                for(ResourcesNeededCategory resource : ResourcesNeededCategory.findByActionRequired(action)){
                    String resourceName = resource.name + "@";
                    resourcesNeeded += resourceName.replace("@", System.getProperty("line.separator")) + System.getProperty("line.separator");
                }
                resources.setText(resourcesNeeded);
                for(PotentialChallengesCategory challenge : PotentialChallengesCategory.findByActionRequired(action)){
                    String challengeName = challenge.name + "@";
                    potentialChallenges += challengeName.replace("@", System.getProperty("line.separator")) + System.getProperty("line.separator");
                }
                challenges.setText(potentialChallenges);
                for(StrategyCategory strategy : StrategyCategory.findByActionRequired(action)){
                    String strategyName = strategy.name + "@";
                    strategiesToOvercome += strategyName.replace("@", System.getProperty("line.separator")) + System.getProperty("line.separator");
                }
                strategies.setText(strategiesToOvercome);
            }
            action.setText(actionCategory);
            Log.d("Size", ActionRequired.findByInterventionCategory(inter).size() + "");
        }
        return newView;
    }

    public void onDataSetChanged(ArrayList<KeyProblem> list){
        list.clear();
        list.addAll(list);
        notifyDataSetChanged();
    }
}
