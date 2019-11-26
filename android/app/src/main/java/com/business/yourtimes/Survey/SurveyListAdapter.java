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

    public SurveyListAdapter(Context context, ArrayList<CategoryItem>Dataset) {
        this.context = context;
        this.mDataset = Dataset;
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

                        if (num == 3 && value == false) {
                            Toast.makeText(context, context.getResources().getString(R.string.survey_notice_toast), Toast.LENGTH_SHORT).show();
                        }
                        else if (num < 3 || (num == 3 && value == true)){
                            // 클릭
                            if (value == false) {
                                ((GlobalClass) context.getApplicationContext()).setSelected(pos, true);
                                Log.d("SurveyListAdapter", "set " + pos + " as true");
                            }
                            // 클릭 해제
                            else {
                                ((GlobalClass) context.getApplicationContext()).setSelected(pos, false);
                                Log.d("SurveyListAdapter", "set " + pos + " as false");
                            }
                            notifyItemChanged(pos);
                        }
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
            holder.mButton.setBackgroundColor(context.getResources().getColor(R.color.colorGray));
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
