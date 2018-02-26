package com.nexer.nexerbluetooth.main.presentation.log;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import comunicacao.bluetooth.R;

/**
 * Created by guilhermecoelho on 2/26/18.
 */

public class LogAdapter extends RecyclerView.Adapter<LogAdapter.PlanetViewHolder> {

    ArrayList<String> planetList;

    public LogAdapter(ArrayList<String> planetList, Context context) {
        this.planetList = planetList;
    }

    @Override
    public LogAdapter.PlanetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.log_row,
                parent,
                        false);

        PlanetViewHolder viewHolder = new PlanetViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(LogAdapter.PlanetViewHolder holder, int position) {
        holder.text.setText(planetList.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return planetList.size();
    }

    public static class PlanetViewHolder extends RecyclerView.ViewHolder{

        protected TextView text;

        public PlanetViewHolder(View itemView) {
            super(itemView);
            text= (TextView) itemView.findViewById(R.id.text_id);
        }
    }
}