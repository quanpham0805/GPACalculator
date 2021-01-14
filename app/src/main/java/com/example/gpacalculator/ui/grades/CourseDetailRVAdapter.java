package com.example.gpacalculator.ui.grades;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gpacalculator.R;
import com.example.gpacalculator.database.CourseDetailEntity;

import java.util.List;

public class CourseDetailRVAdapter extends RecyclerView.Adapter<CourseDetailRVAdapter.CourseDetailViewHolder> {

    private List<CourseDetailEntity> mData;

    @NonNull
    @Override
    public CourseDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.course_detail_item, parent, false);

        return new CourseDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseDetailViewHolder holder, int position) {
        if (mData != null) {
            holder.getTVWeight().setText("Weight: " + mData.get(position).getCourseWeight());
            holder.getTVGpa().setText("GPA: " + mData.get(position).getCourseMark());

            switch ((int) mData.get(position).getCourseScale()) {
                case 4:
                    holder.getTVScale().setText("Scale: 4.0 scale");
                    break;
                case 100:
                    holder.getTVScale().setText("Scale: 100% scale");
                    break;
            }

            holder.getTVTitle().setText(mData.get(position).getCourseMarkName());

        } else {
            holder.getTVWeight().setText("Weight: NO DATA");
            holder.getTVGpa().setText("GPA: NO DATA");
            holder.getTVScale().setText("Scake: NO DATA");
            holder.getTVTitle().setText("NO DATA");
        }
    }

    @Override
    public int getItemCount() {
        if (mData != null) return mData.size();
        else return 0;
    }

    public void updateData(List<CourseDetailEntity> newData) {
        mData = newData;
        notifyDataSetChanged();
    }

    public class CourseDetailViewHolder extends RecyclerView.ViewHolder {
        private final TextView TVTitle;
        private final TextView TVGpa;
        private final TextView TVWeight;
        private final TextView TVScale;

        public CourseDetailViewHolder(@NonNull View itemView) {
            super(itemView);
            this.TVTitle = itemView.findViewById(R.id.tv_title);
            this.TVGpa = itemView.findViewById(R.id.tv_gpa);
            this.TVWeight = itemView.findViewById(R.id.tv_weight);
            this.TVScale = itemView.findViewById(R.id.tv_scale);
        }

        public TextView getTVTitle() {
            return TVTitle;
        }

        public TextView getTVGpa() {
            return TVGpa;
        }

        public TextView getTVWeight() {
            return TVWeight;
        }

        public TextView getTVScale() {
            return TVScale;
        }
    }


}
