package upv.ejercicios.modulo4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by migui on 0007.
 */

public class SocialNetworkItemAdapter extends BaseAdapter {
    private Context context;
    private List<SocialNetworkItem> ssNNItems;

    public SocialNetworkItemAdapter(Context context, List<SocialNetworkItem> ssNNItems) {
        this.context = context;
        this.ssNNItems = ssNNItems;
    }

    @Override
    public int getCount() {
        return ssNNItems.size();
    }

    @Override
    public Object getItem(int position) {
        return ssNNItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /* method that will populate the grid with images */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.social_network_item,parent, false);

        }

        // Set data into the view....
        ImageView socialNetworkImageView = (ImageView) rowView.findViewById(R.id.ivSocialNetworkItem);

        SocialNetworkItem socialNetworkItem = this.ssNNItems.get(position);
        socialNetworkImageView.setImageResource(socialNetworkItem.getImage());

        return rowView;

    }
}
