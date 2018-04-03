package com.example.rehan.tourista;

/**
 * Created by Aimann on 3/22/2018.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rehan.tourista.Activities.trip;
import com.example.rehan.tourista.RecentRides;

import java.util.ArrayList;

/**
 * Created by hassan on 11-Nov-17.
 */

public class RecentToursAdapter extends BaseAdapter {

    Context context;
    private LayoutInflater inflater;

    int i;

    private ArrayList<trip> menuData=new ArrayList<>();
    //private ArrayList<Booking> nameData=new ArrayList<>();

    public RecentToursAdapter(Context context, ArrayList<trip>ImageULRS)
    {
        this.context = context;
        this.menuData=ImageULRS;
        inflater = LayoutInflater.from(context);
        i=0;
    }
    @Override
    public int getCount() {
        return menuData.size();
    }

    @Override
    public Object getItem(int i) {
        return menuData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolderShowAllBooking holder;
        if (convertView == null) {

            holder = new ViewHolderShowAllBooking();
            convertView = inflater.inflate(R.layout.list_row_layout_recent_rides, parent, false);
            holder.trip_no = (TextView) convertView.findViewById(R.id.trip_no_list_row_layout_xml);
            holder.pick_up = (TextView) convertView.findViewById(R.id.pickup_list_row_layout_xml);
            holder.dropoff = (TextView) convertView.findViewById(R.id.dropoff_list_row_layout_xml);

            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolderShowAllBooking) convertView.getTag();
        }

        holder.dropoff.setText(menuData.get(position).getDrop_off());      /** THIS ARRAY IS ONLY FOR NAME*/


        /** THIS ARRAY IS FOR REST OF THE DETAILS*/
        holder.pick_up.setText(menuData.get(position).getPick_up());
        holder.trip_no.setText(menuData.get(position).getTrip_no());
        //holder.bookingPrice.setText(menuData.get(position).getBookingTotal());

        // downloadImage(position,holder);
        return convertView;
    }


//    private void downloadImage(int position,ViewHolderShowAllBooking holder)
//    {
//        Picasso.with(context)
//                .load("http://www.efefoundation.net/inklink/uploads/artist/"+menuData.get(position).getArtistId()+".jpg")
//                .fit() // will explain later
//                .into(holder.profile);
//    }

    static class ViewHolderShowAllBooking
    {
        TextView trip_no;
        TextView pick_up;
        TextView dropoff;
    }
}
