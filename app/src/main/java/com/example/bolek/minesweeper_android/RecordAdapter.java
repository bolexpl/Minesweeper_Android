package com.example.bolek.minesweeper_android;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bolek.minesweeper_android.pojo.Record;

import java.util.ArrayList;
import java.util.List;

public class RecordAdapter extends RecyclerView.Adapter {

    private List<Record> data;
    private Context context;

    private class RecordViewHolder extends RecyclerView.ViewHolder {

        TextView nr;
        TextView time;
        TextView player;
        TextView board;

        RecordViewHolder(View itemView) {
            super(itemView);
            nr = (TextView) itemView.findViewById(R.id.nr);
            time = (TextView) itemView.findViewById(R.id.time);
            player = (TextView) itemView.findViewById(R.id.player);
            board = (TextView) itemView.findViewById(R.id.board);
        }
    }

    public RecordAdapter(ArrayList<Record> data, Context context) {
        this.data = data;
        this.context = context;
    }

    public void update(List<Record> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.record_item, parent, false);
        return new RecordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder h, int position) {
        Record r = data.get(position);
        RecordViewHolder holder = (RecordViewHolder) h;
        holder.nr.setText(context.getResources().getString(R.string.record_number, position + 1));
        holder.time.setText(context.getResources().getString(R.string.record_time, r.getCzas()));
        holder.player.setText(r.getLogin());
        holder.board.setText(r.getBoard());
    }

    private Record getRecord(int i) {
        return data.get(i);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
