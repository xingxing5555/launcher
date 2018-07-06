package com.example.admin.launcherdemo.adpter;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.launcherdemo.R;
import com.example.admin.launcherdemo.utils.AppInfo;

import java.util.List;

/**
 * @author Xinxin Shi
 */

public class AppAdapter extends RecyclerView.Adapter<AppAdapter.ViewHolder> {

    private Context context;
    private List<AppInfo> appInfos;

    public AppAdapter(Context context, List<AppInfo> appInfos) {
        this.context = context;
        this.appInfos = appInfos;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_app, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final AppInfo appInfo = appInfos.get(position);
        holder.tvName.setText(appInfo.getAppName());
        holder.ivIcon.setImageDrawable(appInfo.getAppIcon());

        holder.ivIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppInfo appInfo = appInfos.get(position);
                String packageName = appInfo.getPackageName();
                String className = appInfo.getClassName();
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                ComponentName cn = new ComponentName(packageName, className);
                intent.setComponent(cn);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return appInfos == null ? 0 : appInfos.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivIcon;
        TextView tvName;

        public ViewHolder(View itemView) {
            super(itemView);
            ivIcon = (ImageView) itemView.findViewById(R.id.iv_icon);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
        }
    }
}
