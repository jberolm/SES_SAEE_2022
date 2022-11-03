package es.unex.asee.frojomar.asee_ses.activities.appointments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import es.unex.asee.frojomar.asee_ses.R;
import es.unex.asee.frojomar.asee_ses.model.Appointment;

public class AppointmentsAdapter extends RecyclerView.Adapter<AppointmentsAdapter.ViewHolder> {
    private final List<Appointment> mItems = new ArrayList<Appointment>();

    public interface OnItemClickListener {
        void onItemClick(Appointment item);     //Type of the element to be returned
    }

    private final AppointmentsAdapter.OnItemClickListener listener;
    private final AppointmentsAdapter.OnItemClickListener editlistener;
    private final AppointmentsAdapter.OnItemClickListener deletelistener;


    // Provide a suitable constructor (depends on the kind of dataset)
    public AppointmentsAdapter(AppointmentsAdapter.OnItemClickListener listener,
                               AppointmentsAdapter.OnItemClickListener editlistener,
                               AppointmentsAdapter.OnItemClickListener deletelistener) {
        this.listener = listener;
        this.editlistener=editlistener;
        this.deletelistener=deletelistener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                                        int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_appointments_element, parent, false);



        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(mItems.get(position),listener);
        holder.editButtonBind(mItems.get(position),editlistener);
        holder.deleteButtonBind(mItems.get(position),deletelistener);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void add(Appointment item) {

        mItems.add(item);
        notifyDataSetChanged();
    }

    public void replace(Appointment item, Integer position){

        mItems.set(position,item);
        notifyDataSetChanged();
    }

    public void clear(){

        mItems.clear();
        notifyDataSetChanged();

    }

    public Integer getIndexof(Appointment item){
        return mItems.indexOf(item);
    }

    public Object getItem(int pos) {

        return mItems.get(pos);

    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView date;
        private TextView time;
        private TextView telephone;
        private TextView description;


        public ViewHolder(View itemView) {
            super(itemView);

            date = (TextView) itemView.findViewById(R.id.fecha_appointment);
            time = (TextView) itemView.findViewById(R.id.hora_appointment);
            telephone = (TextView) itemView.findViewById(R.id.tlfno_appointment);
            description = (TextView) itemView.findViewById(R.id.desc_appointment);

        }

        public void bind(final Appointment appointment, final AppointmentsAdapter.OnItemClickListener listener) {

            date.setText(appointment.getDate());
            time.setText(appointment.getTime());
            telephone.setText(appointment.getTelephone());
            description.setText(appointment.getDescription());


            itemView.findViewById(R.id.appointment_element).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    listener.onItemClick(appointment);
                }
            });
        }

        public void editButtonBind(final Appointment appointment, final AppointmentsAdapter.OnItemClickListener listener) {

            itemView.findViewById(R.id.button_edit).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    listener.onItemClick(appointment);
                }
            });
        }

        public void deleteButtonBind(final Appointment appointment, final AppointmentsAdapter.OnItemClickListener listener) {

            itemView.findViewById(R.id.button_delete).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    listener.onItemClick(appointment);
                }
            });
        }
    }
}
