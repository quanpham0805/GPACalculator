package com.example.gpacalculator.ui.grades;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gpacalculator.R;

import java.util.ArrayList;
import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.RVViewHolder> {

    private final ListItemClickListener mOnClickListener;
    private List<String> RVData;

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
        if (RVData != null)
            holder.getTextView().setText(RVData.get(position));
        else
            holder.getTextView().setText("No Data");
    }

    @Override
    public int getItemCount() {
        if (RVData != null)
            return RVData.size();
        else return 0;
    }

    public void updateDataInteger(List<Integer> newData) {
//        if (newData == null) RVData = null;
//        else {
//            if (RVData != null) RVData.clear();
//            else RVData = new ArrayList<>();
//            for (Integer i : newData) {
//                RVData.add(Integer.toString(i));
//            }
//        }

        RVData = new ArrayList<>();
        for (Integer i : newData) {
            RVData.add(Integer.toString(i));
        }
        notifyDataSetChanged();
    }

    public void updateDataString(List<String> newData) {
        RVData = newData;
        notifyDataSetChanged();
    }


}
