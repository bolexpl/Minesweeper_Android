package com.example.bolek.minesweeper_android;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.bolek.minesweeper_android.model.ApiUtils;
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
        ImageView avatar;

        RecordViewHolder(View itemView) {
            super(itemView);
            nr = (TextView) itemView.findViewById(R.id.nr);
            time = (TextView) itemView.findViewById(R.id.time);
            player = (TextView) itemView.findViewById(R.id.player);
            board = (TextView) itemView.findViewById(R.id.board);
            avatar = (ImageView) itemView.findViewById(R.id.avatar);
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

    public List<Record> getData() {
        return data;
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

        if (r.getAvatar().equals("no_avatar.png")) {
            holder.avatar.setImageDrawable(context.getResources().getDrawable(R.drawable.no_avatar));
        } else {
            Glide.with(context).load(ApiUtils.getAvatarUrl(r.getAvatar())).into(holder.avatar);
            Log.d("Glide",ApiUtils.getAvatarUrl(r.getAvatar()));
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
