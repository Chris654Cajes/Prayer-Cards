package Adapters;

import android.content.Context;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prayercards.PrayerContent;
import com.example.prayercards.R;

import java.time.LocalDate;
import java.time.ZoneId;

import java.util.ArrayList;
import java.util.Date;

import Models.Prayer;

/*
    This class displays all the prayers, 31 days of the month, and add each prayer to button in their respective dates
* */

public class PrayerAdapter extends RecyclerView.Adapter<PrayerAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<Prayer> prayers;

    // Use this constructor to create an instance of PrayerAdapter
    public PrayerAdapter(Context context, ArrayList<Prayer> prayers) {
        this.context = context;
        this.prayers = prayers;
    }

    // Create a row of button
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.prayer_item, parent, false);

        return new MyViewHolder(view);
    }

    // Put every prayer to every row
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        int index = position;

        // This gets the day / date from the datetime
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int day = localDate.getDayOfMonth();

        // The current day changes the color so that it will be easier to which day should be prayed
        if (prayers.get(index).getDay() == day) {
            holder.btn_prayer.setBackgroundColor(context.getResources().getColor(R.color.yellow));
            holder.btn_prayer.setTextColor(context.getResources().getColor(R.color.black));
        } else {
            holder.btn_prayer.setBackgroundColor(context.getResources().getColor(R.color.blue));
            holder.btn_prayer.setTextColor(context.getResources().getColor(R.color.white));
        }

        holder.btn_prayer.setText("Day " + prayers.get(index).getDay());
        holder.btn_prayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PrayerContent.class);

                intent.putExtra("day", prayers.get(index).getDay());
                intent.putExtra("prayer", prayers.get(index).getPrayer());
                intent.putExtra("takenFrom", prayers.get(index).getTakenFrom());

                context.startActivity(intent);
            }
        });
    }

    // Get the number of rows in the prayers array
    @Override
    public int getItemCount() { return prayers.size(); }

    // Blueprints of the row structure
    public class MyViewHolder extends RecyclerView.ViewHolder {
        Button btn_prayer;
        LinearLayout prayer_item_row;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            btn_prayer = itemView.findViewById(R.id.btn_prayer);
            prayer_item_row = itemView.findViewById(R.id.prayer_item_row);
        }
    }
}
