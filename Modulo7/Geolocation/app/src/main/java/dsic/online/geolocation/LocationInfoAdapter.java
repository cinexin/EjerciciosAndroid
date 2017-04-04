package dsic.online.geolocation;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import org.w3c.dom.Text;

/**
 * Created by migui on 0004.
 */

public class LocationInfoAdapter implements GoogleMap.InfoWindowAdapter {

    LayoutInflater inflater;

    public LocationInfoAdapter() {
    }

    public LocationInfoAdapter(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View positionInfoView = this.getInflater().inflate(R.layout.activity_position_info, null);
        TextView title =  (TextView) positionInfoView.findViewById(R.id.position_info_title);
        TextView positionSpinet = (TextView) positionInfoView.findViewById(R.id.position_info_spinet);

        title.setText("Marker from adapter");
        positionSpinet.setText(marker.getPosition().longitude + " : " + marker.getPosition().latitude);
        return positionInfoView;

    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    public LayoutInflater getInflater() {
        return inflater;
    }

    public void setInflater(LayoutInflater inflater) {
        this.inflater = inflater;
    }
}
