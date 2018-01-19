package info.androidhive.navigationdrawer.other;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import info.androidhive.navigationdrawer.R;
import info.androidhive.navigationdrawer.activity.post_view;

/**
 * Created by hp on 07-01-2018.
 */


public class community_home_adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private OnLoadMoreListener onLoadMoreListener;
    private boolean isLoading;
    private Activity activity;
    private List<community_home_helper> contacts;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;

    public community_home_adapter(RecyclerView recyclerView, List<community_home_helper> contacts, Activity activity) {
        this.contacts = contacts;
        this.activity = activity;

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    if (onLoadMoreListener != null) {
                        onLoadMoreListener.onLoadMore();
                    }
                    isLoading = true;
                }
            }
        });
    }

    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.onLoadMoreListener = mOnLoadMoreListener;
    }

    @Override
    public int getItemViewType(int position) {
        return contacts.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(activity).inflate(R.layout.item_recycler_view_row, parent, false);
            return new UserViewHolder(view);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(activity).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof UserViewHolder) {
            community_home_helper contact = contacts.get(position);
            UserViewHolder userViewHolder = (UserViewHolder) holder;
            userViewHolder.user.setText(contact.getUser());
            userViewHolder.title.setText(contact.getTitle());
            userViewHolder.desc.setText(contact.getDesc());
            if((contact.getimage()!="")||(contact.getimage()!=null))
            userViewHolder.im.setImageBitmap(base64ToBitmap(contact.getimage()));
            userViewHolder.id.setText(contact.getId());
            System.out.println("minpl "+contact.getimage());

            //   userViewHolder.im.setText(contact.getPhone());


        } else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        return contacts == null ? 0 : contacts.size();
    }

    public void setLoaded() {
        isLoading = false;
    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(View view) {
            super(view);
            progressBar = (ProgressBar) view.findViewById(R.id.progressBar1);
        }
    }

    private class UserViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView user,desc,id;
        ImageView im;

        public UserViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            user = (TextView) view.findViewById(R.id.user);
            desc = (TextView) view.findViewById(R.id.desc);
            im = (ImageView) view.findViewById(R.id.image);
            id = (TextView) view.findViewById(R.id.ids);

view.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        int pos = getAdapterPosition();
        // check if item still exists
        if(pos != RecyclerView.NO_POSITION){
            community_home_helper clickedDataItem = contacts.get(pos);
            Toast.makeText(view.getContext(), "You clicked " + clickedDataItem.getId(), Toast.LENGTH_SHORT).show();
            Intent ieventreport = new Intent(activity,post_view.class);
            ieventreport.putExtra("id",clickedDataItem.getId());
            activity.startActivity(ieventreport);
        }
    }
});
        }
    }
    private Bitmap base64ToBitmap(String b64) {
        byte[] imageAsBytes = Base64.decode(b64.getBytes(), Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
    }
}