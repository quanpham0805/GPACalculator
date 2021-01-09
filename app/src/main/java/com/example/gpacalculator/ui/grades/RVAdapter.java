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
    private List<String> RVTitleData;
    private List<String> RVGradesData;

    public RVAdapter(ListItemClickListener listener) {
        this.mOnClickListener = listener;
    }

    public class RVViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView titleItemView, gradesItemView;

        public RVViewHolder(@NonNull View itemView) {
            super(itemView);
            this.titleItemView = (TextView) itemView.findViewById(R.id.tv_name);
            this.gradesItemView = (TextView) itemView.findViewById(R.id.tv_gpa);
            itemView.setOnClickListener(this);
        }

        public TextView getTitleTextView() {
            return this.titleItemView;
        }

        public TextView getGradesTextView() {
            return this.gradesItemView;
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
        if (RVTitleData != null) {
            holder.getTitleTextView().setText(RVTitleData.get(position));
            holder.getGradesTextView().setText("GPA: " + RVGradesData.get(position));
        } else {
            holder.getTitleTextView().setText("No Data");
            holder.getGradesTextView().setText("GPA: No Data");
        }
    }

    @Override
    public int getItemCount() {
        if (RVTitleData != null)
            return RVTitleData.size();
        else return 0;
    }

    void updateGradesData(List<Double> newGradesData) {
        RVGradesData = new ArrayList<>();
        // TODO: use real data
//        for (Double i : newGradesData) {
//            RVGradesData.add(String.format("%.2f", i));
//        }
        if (RVTitleData != null) {
            for (int i = 0 ; i < RVTitleData.size() ; i ++) {
                RVGradesData.add("90.00%");
            }
        }
    }

    public void updateDataInteger(List<Integer> newTitleData, List<Double> newGradesData) {

        RVTitleData = new ArrayList<>();
        for (Integer i : newTitleData) {
            RVTitleData.add(Integer.toString(i));
        }
        updateGradesData(newGradesData);

        notifyDataSetChanged();
    }

    public void updateDataString(List<String> newTitleData, List<Double> newGradesData) {
        RVTitleData = newTitleData;
        updateGradesData(newGradesData);
        notifyDataSetChanged();
    }


}
