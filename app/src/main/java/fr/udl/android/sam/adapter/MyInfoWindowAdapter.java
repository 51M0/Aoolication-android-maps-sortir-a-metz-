package fr.udl.android.sam.adapter;

import android.content.Context;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import fr.udl.android.sam.R;
import fr.udl.android.sam.activities.MapActivity;
import fr.udl.android.sam.persistence.Entities.Site;
import fr.udl.android.sam.persistence.dao.DaoSite;

public class MyInfoWindowAdapter implements GoogleMap.InfoWindowAdapter{

    private final View myContentsView;
    private MapActivity mActivity;
  
  public MyInfoWindowAdapter(Context context, MapActivity mActivity){
     // mActivity.getContext
      LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
      myContentsView = inflater.inflate(R.layout.info_view, null);
      this.mActivity = mActivity;
  }
  
  @Override
  public View getInfoContents(Marker marker) {
   
      TextView tvTitle = ((TextView)myContentsView.findViewById(R.id.infoTitleText));
        tvTitle.setText(marker.getTitle());

      TextView tvSnippet = ((TextView)myContentsView.findViewById(R.id.resumeSite));
      String resume = retrieveSiteResume(marker.getTitle());
      tvSnippet.setText(resume);

      Location markerLoc = new Location("");
      markerLoc.setLatitude(marker.getPosition().latitude);
      markerLoc.setLongitude(marker.getPosition().longitude);
      float distanceF = mActivity.getCurrentLoc().distanceTo(markerLoc);
      String distanceS = "Distance to goal: " + distanceF + " m";

      TextView tvDistance = ((TextView)myContentsView.findViewById(R.id.distance));
        tvDistance.setText(distanceS);

      Site site = new DaoSite(mActivity).getSiteByName(marker.getTitle());

      Button markerButton = (Button) myContentsView.findViewById(R.id.markerButton);
      if(site != null){
          markerButton.setText("Details");
      }else{
          markerButton.setText("Add");
      }
      return myContentsView;
  }

    private String retrieveSiteResume(String title){
        Site site = new DaoSite(mActivity).getSiteByName(title);

        if(site != null){
            return site.getRésume();
        }else{
            return "Pas de résumé";
        }

    }

  @Override
  public View getInfoWindow(Marker marker) {

   return null;
  }
  
 }