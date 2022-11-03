package es.unex.asee.frojomar.asee_ses.activities.appointments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import es.unex.asee.frojomar.asee_ses.R;
import es.unex.asee.frojomar.asee_ses.model.AppointmentSpecialist;

public class AppointmentsSpecialistAdapter extends RecyclerView.Adapter<AppointmentsSpecialistAdapter.ViewHolder> {
private final List<AppointmentSpecialist> mItems = new ArrayList<AppointmentSpecialist>();

public interface OnItemClickListener {
    void onItemClick(AppointmentSpecialist item);     //Type of the element to be returned
}

    private final AppointmentsSpecialistAdapter.OnItemClickListener listener;
    private final AppointmentsSpecialistAdapter.OnItemClickListener aplazarListener;
    protected Context context;


    // Provide a suitable constructor (depends on the kind of dataset)
    public AppointmentsSpecialistAdapter(
                                AppointmentsSpecialistAdapter.OnItemClickListener listener,
                                AppointmentsSpecialistAdapter.OnItemClickListener aplazarListener,
                                Context context) {
        this.listener = listener;
        this.aplazarListener=aplazarListener;
        this.context=context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public AppointmentsSpecialistAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_appointmentsspecialist_element, parent, false);



        return new AppointmentsSpecialistAdapter.ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(AppointmentsSpecialistAdapter.ViewHolder holder, int position) {
        holder.bind(mItems.get(position),listener, context);
        holder.aplazarButtonBind(mItems.get(position),aplazarListener);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void add(AppointmentSpecialist item) {

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

    private TextView date;
    private TextView time;
    private TextView description;
    private TextView solAplazamiento;
    private TextView medicalField;


    public ViewHolder(View itemView) {
        super(itemView);

        date = (TextView) itemView.findViewById(R.id.fecha_appointment);
        time = (TextView) itemView.findViewById(R.id.hora_appointment);
        description = (TextView) itemView.findViewById(R.id.desc_appointment);
        solAplazamiento = (TextView) itemView.findViewById(R.id.solAplazamiento_appointment);
        medicalField = (TextView) itemView.findViewById(R.id.medfield_appointment);

    }

    public void bind(final AppointmentSpecialist appointment, final AppointmentsSpecialistAdapter.OnItemClickListener listener,
                     Context context) {

        date.setText(appointment.getDate());
        time.setText(appointment.getTime());
        description.setText(appointment.getDescription());
        if(appointment.getSolAplazamiento().equals("true")){
            solAplazamiento.setText(context.getResources().getText(R.string.solAplazamiento_appointment_true));
            itemView.findViewById(R.id.button_aplazar).setVisibility(View.GONE);
        }
        else {
            solAplazamiento.setText(context.getResources().getText(R.string.solAplazamiento_appointment_false));
        }
        medicalField.setText(appointment.getMedicalField());


        itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                listener.onItemClick(appointment);
            }
        });
    }

    public void aplazarButtonBind(final AppointmentSpecialist appointment, final AppointmentsSpecialistAdapter.OnItemClickListener listener) {

        itemView.findViewById(R.id.button_aplazar).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                listener.onItemClick(appointment);
            }
        });
    }

    }
}
