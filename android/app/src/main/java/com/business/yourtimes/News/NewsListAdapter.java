package com.business.yourtimes.News;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.business.yourtimes.R;

import java.util.ArrayList;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.ViewHolder> {
    Context context;
    private ArrayList<NewsCard> mDataset;

    public NewsListAdapter(Context context, ArrayList<NewsCard> mDataset) {
        this.context = context;
        this.mDataset = mDataset;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView mCardView;
        TextView mCategory;
        TextView mHeadline;
        TextView mAuthor;
        TextView mDate;
        TextView mDesc;

        ViewHolder(View itemView) {
            super(itemView);

            mCardView = (CardView) itemView.findViewById(R.id.news_cv);
            mCategory = (TextView) itemView.findViewById(R.id.news_cv_category);
            mHeadline = (TextView) itemView.findViewById(R.id.news_cv_headline);
            mAuthor = (TextView) itemView.findViewById(R.id.news_cv_author);
            mDate = (TextView) itemView.findViewById(R.id.news_cv_date);
            mDesc = (TextView) itemView.findViewById(R.id.news_cv_desc);
        }
    }

    @Override
    public NewsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int ViewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.recyclerview_news, parent, false);

        return new NewsListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mCategory.setText("[" + mDataset.get(position).getCategory() + "]");
        holder.mHeadline.setText(mDataset.get(position).getHeadline());
        holder.mAuthor.setText(mDataset.get(position).getAuthors());
        holder.mDate.setText(mDataset.get(position).getDate());
        holder.mDesc.setText(mDataset.get(position).getDesc());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
