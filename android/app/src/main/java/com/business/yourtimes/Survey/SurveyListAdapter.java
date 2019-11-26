package com.business.yourtimes.Survey;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.business.yourtimes.GlobalClass;
import com.business.yourtimes.R;
import com.business.yourtimes.SurveyActivity;
import com.business.yourtimes.item.CategoryItem;

import java.util.ArrayList;

public class SurveyListAdapter extends RecyclerView.Adapter<SurveyListAdapter.ViewHolder> {
    Context context;
    private ArrayList<CategoryItem> mDataset;
    private ArrayList<CategoryItem> mSelected;

    public SurveyListAdapter(Context context, ArrayList<CategoryItem>Dataset) {
        this.context = context;
        this.mDataset = Dataset;
        mSelected = new ArrayList<>();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Button mButton;

        ViewHolder(View itemView) {
            super(itemView);

            mButton = itemView.findViewById(R.id.survey_btn);
            mButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    boolean value = ((GlobalClass) context.getApplicationContext()).getSelected(pos);

                    if (pos != RecyclerView.NO_POSITION) {
                        int num = ((GlobalClass) context.getApplicationContext()).numSelected();

                        /* 버튼 클릭 해제 시 */
                        if (value) {
                                ((GlobalClass) context.getApplicationContext()).setSelected(pos, false);
                                Log.d("SurveyListAdapter", "unset " + mDataset.get(pos).getCategory());
                                mSelected.remove(mDataset.get(pos));
                                Log.d("SurveyListAdapter", "mSelected: " + mSelected.toString());
                        }
                        /* 버튼 클릭 시 */
                        else {
                            if (num == 3) {
                                Toast.makeText(context, context.getResources().getString(R.string.survey_notice_toast), Toast.LENGTH_SHORT).show();
                            }
                            else {
                                ((GlobalClass) context.getApplicationContext()).setSelected(pos, true);
                                Log.d("SurveyListAdapter", "set " + mDataset.get(pos).getCategory());
                                mSelected.add(mDataset.get(pos));
                                Log.d("SurveyListAdapter", "mSelected: " + mSelected.toString());
                            }

                        }

                        notifyDataSetChanged();
                    }
                }
            });

        }
    }

    @Override
    public SurveyListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int ViewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.recyclerview_survey, parent, false);

        return new SurveyListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SurveyListAdapter.ViewHolder holder, int position) {
        holder.mButton.setText(mDataset.get(position).getCategory());

        boolean value = ((GlobalClass) context.getApplicationContext()).getSelected(position);

        // 버튼이 선택된 상태면
        if (value) {
            if (mDataset.get(position).getCategory().equals(mSelected.get(0).getCategory())) {
                holder.mButton.setBackgroundColor(context.getResources().getColor(R.color.category_first));
            }
            else if (mDataset.get(position).getCategory().equals(mSelected.get(1).getCategory())) {
                holder.mButton.setBackgroundColor(context.getResources().getColor(R.color.category_second));
            }
            else if (mDataset.get(position).getCategory().equals(mSelected.get(2).getCategory())) {
                holder.mButton.setBackgroundColor(context.getResources().getColor(R.color.category_third));
            }
        }

        // 버튼이 선택되지 않은 상태라면
        else {
            holder.mButton.setBackgroundColor(context.getResources().getColor(R.color.colorWhite));
        }
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
