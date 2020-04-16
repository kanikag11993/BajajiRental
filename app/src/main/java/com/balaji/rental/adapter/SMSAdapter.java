package com.balaji.rental.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.balaji.rental.R;
import com.balaji.rental.main.notifications.NotificationsFragment;
import com.balaji.rental.model.SMSModel;

import java.util.concurrent.CopyOnWriteArrayList;

public class SMSAdapter extends RecyclerView.Adapter<SMSAdapter.SMSViewHolder> {

    private Context mContext;
    private CopyOnWriteArrayList<SMSModel> mNavigatorList;
    private NotificationsFragment.NavigatorListener mListener;


    /**
     * Constructor for our adapter class
     */
    public SMSAdapter(Context context, CopyOnWriteArrayList<SMSModel> navigatorList, NotificationsFragment.NavigatorListener mListener) {
        this.mContext = context;
        this.mNavigatorList = navigatorList;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public SMSViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.sms_detail, parent, false);
        return new SMSViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SMSViewHolder holder, int position) {
        SMSModel navigator = mNavigatorList.get(position);
        holder.mNavigatorNameTextView.setText(navigator.getNavigatorName());
        //holder.sendViaWhatsapp.setChecked(navigator.getToSend());
    }

    @Override
    public int getItemCount() {
        return mNavigatorList.size();
    }

    public CopyOnWriteArrayList<SMSModel> getSMSList() {
        return mNavigatorList;
    }

    public class SMSViewHolder extends RecyclerView.ViewHolder {
        private EditText mNavigatorNameTextView;
        private Button sendViaWhatsapp;
        private Button markAsSent;

        public SMSViewHolder(final @NonNull View itemView) {
            super(itemView);

            mNavigatorNameTextView = itemView.findViewById(R.id.smsDetail);
            sendViaWhatsapp = itemView.findViewById(R.id.send_via_whatsapp);
            markAsSent=itemView.findViewById(R.id.mark_as_sent);
            sendViaWhatsapp.setOnClickListener(new View.OnClickListener() {
                                                   @Override
                                                   public void onClick(View v) {
                                                       PackageManager pm = mContext.getPackageManager();
                                                       try {

                                                           Intent waIntent = new Intent(Intent.ACTION_SEND);
                                                           waIntent.setType("text/plain");
                                                           String text = mNavigatorNameTextView.getText().toString();

                                                           PackageInfo info = pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
                                                           //Check if package exists or not. If not then code
                                                           //in catch block will be called
                                                           waIntent.setPackage("com.whatsapp");

                                                           waIntent.putExtra(Intent.EXTRA_TEXT, text);
                                                           waIntent.putExtra("jid", "919910291926" + "@s.whatsapp.net"); //phone number without "+" prefix

                                                           mContext.startActivity(Intent.createChooser(waIntent, "Share with"));

                                                       } catch (PackageManager.NameNotFoundException e) {
                                                           Toast.makeText(mContext, "WhatsApp not Installed", Toast.LENGTH_SHORT)
                                                                   .show();
                                                       }
                                                   }
                                               }
            );
            mNavigatorNameTextView.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    //mNavigatorList.get(getAdapterPosition()).setTextToSend(mNavigatorNameTextView.getText().toString());

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    mNavigatorList.get(getAdapterPosition()).setTextToSend(mNavigatorNameTextView.getText().toString());
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onNavigatorSelected(mNavigatorList.get(getAdapterPosition()), itemView);
                }
            });
        }
    }

    /**
     * The interface that receives onClick listener.
     */
    public interface NavigatorAdapterListener {
        void onNavigatorSelected(SMSModel navigator, View view);
    }
}
