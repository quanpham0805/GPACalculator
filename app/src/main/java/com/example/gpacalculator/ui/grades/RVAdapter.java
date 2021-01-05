package com.example.gpacalculator.ui.grades;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gpacalculator.R;

import java.util.ArrayList;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.RVViewHolder> {

    private int mNumberItems;
    private final ListItemClickListener mOnClickListener;
    private ArrayList<String> RVData;

    public RVAdapter(ListItemClickListener listener) {
        this.mOnClickListener = listener;
    }

    public class RVViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView listItemView;

        public RVViewHolder(@NonNull View itemView) {
            super(itemView);
            this.listItemView = (TextView) itemView.findViewById(R.id.tv_list_item);
            itemView.setOnClickListener(this);
        }

        public TextView getTextView() {
            return this.listItemView;
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition, v);
        }
    }

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex, View view);
    }

    @NonNull
    @Override
    public RVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        return new RVViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RVViewHolder holder, int position) {
        holder.getTextView().setText(RVData.get(position));
    }

    @Override
    public int getItemCount() {
        return this.RVData.size();
    }

    public void updateData(ArrayList<String> newData) {
        this.RVData = newData;
        notifyDataSetChanged();
    }

}
