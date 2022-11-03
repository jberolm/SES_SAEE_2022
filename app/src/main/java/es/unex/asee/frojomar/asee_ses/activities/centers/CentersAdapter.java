package es.unex.asee.frojomar.asee_ses.activities.centers;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import es.unex.asee.frojomar.asee_ses.R;
import es.unex.asee.frojomar.asee_ses.model.Center;
import es.unex.asee.frojomar.asee_ses.utils.ImageUtils;

public class CentersAdapter extends RecyclerView.Adapter<CentersAdapter.ViewHolder> {
    private final List<Center> mItems = new ArrayList<Center>();

    public interface OnItemClickListener {
        void onItemClick(Center item);     //Type of the element to be returned
    }

    private final OnItemClickListener listener;


    // Provide a suitable constructor (depends on the kind of dataset)
    public CentersAdapter(OnItemClickListener listener) {
        this.listener = listener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public CentersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_centers_element, parent, false);

        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(mItems.get(position),listener);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void add(Center item) {

        mItems.add(item);
        notifyDataSetChanged();

    }

    public void clear(){

        mItems.clear();
        notifyDataSetChanged();

    }

    public Object getItem(int pos) {

        return mItems.get(pos);

    }

    public int indexOf(Object item){
        return mItems.indexOf(item);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView address;
        private ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.name_center);
            address = (TextView) itemView.findViewById(R.id.adress_center);
            image = (ImageView) itemView.findViewById(R.id.image_center);

        }

        public void bind(final Center center, final OnItemClickListener listener) {

            name.setText(center.getName());

            address.setText(center.getAddress());

            Bitmap decodedByte= ImageUtils.b64toBitMap(center.getB64Image());
            image.setImageBitmap(decodedByte);


            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    listener.onItemClick(center);
                }
            });
        }
    }

}

