package com.example.afinal;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;

public class CitiBikeRenderer extends DefaultClusterRenderer <MyItem> {

private final IconGenerator iconGenerator;
private final Context mContext;

public CitiBikeRenderer(Context context, GoogleMap map, ClusterManager<MyItem> clusterManager) {
        super(context, map, clusterManager);
        mContext = context;
        iconGenerator = new IconGenerator(mContext);
        }


protected void onBeforeClusterItemRendered(MyItem item, MarkerOptions markerOptions) {
        // Draw a single person - show their profile photo and set the info window to show their name
        BitmapDescriptor markerDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.bike);
        markerOptions.icon(markerDescriptor).snippet(item.getTitle());
        }



//    protected void onBeforeClusterRendered(Cluster<MyItem> cluster, MarkerOptions markerOptions) {
//        if (cluster.getSize() > 5) {
//            IconGenerator.setBackground(ContextCompat.getDrawable(mContext, R.drawable.amu_bubble_mask));
//        } else {
//            IconGenerator.setBackground(ContextCompat.getDrawable(mContext, R.drawable.common_full_open_on_phone));
//        }
//        IconGenerator.setTextAppearance(R.style.TextAppearance_AppCompat_Body1);
//
//        String clusterTitle = String.valueOf(cluster.getSize());
//        Bitmap icon = IconGenerator.makeIcon(clusterTitle);
//        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon));
//    }


@Override
protected boolean shouldRenderAsCluster(Cluster cluster) {
        // Always render clusters.
        return cluster.getSize() > 5;
        }
        }

