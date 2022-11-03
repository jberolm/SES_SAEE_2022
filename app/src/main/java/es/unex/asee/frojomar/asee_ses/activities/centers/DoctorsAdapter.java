package es.unex.asee.frojomar.asee_ses.activities.centers;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import es.unex.asee.frojomar.asee_ses.R;
import es.unex.asee.frojomar.asee_ses.model.Doctor;
import es.unex.asee.frojomar.asee_ses.utils.ImageUtils;

public class DoctorsAdapter extends RecyclerView.Adapter<DoctorsAdapter.ViewHolder> {
    private final List<Doctor> mItems = new ArrayList<Doctor>();

    public interface OnItemClickListener {
        void onItemClick(Doctor item);     //Type of the element to be returned
    }

    private final OnItemClickListener listener;


    // Provide a suitable constructor (depends on the kind of dataset)
    public DoctorsAdapter(OnItemClickListener listener) {
        this.listener = listener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public DoctorsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                        int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.simple_item_list, parent, false);

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

    public void add(Doctor item) {

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

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private CircleImageView image;

        public ViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.simple_item_name);
            image = (CircleImageView) itemView.findViewById(R.id.simple_item_image);

        }

        public void bind(final Doctor doctor, final OnItemClickListener listener) {

            name.setText(doctor.getName());

            Bitmap decodedByte= ImageUtils.b64toBitMap(doctor.getB64Image());
            image.setImageBitmap(decodedByte);


            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    listener.onItemClick(doctor);
                }
            });
        }
    }

}