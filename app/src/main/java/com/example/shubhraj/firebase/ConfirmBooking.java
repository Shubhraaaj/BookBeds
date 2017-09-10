package com.example.shubhraj.firebase;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Shubhraj on 09-09-2017.
 */

public class ConfirmBooking extends AppCompatActivity
{
    private static final String BED_ID = "bed_id";
    private static final String PRICE_BED = "price";
    private static final String STATUS_BED = "status";

    private static final String AVAILABLE = "Available";
    private static final String UNAVAILABLE = "UnAvailable";
    private static final String BOOKED = "Booked";

    private static final String TAG = "ConfirmBooking";
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private TextView nameOfBed, priceStatusBed, dateOfBooking;
    private Button cancelButton, confirmButton;

    private List<Beds> mBeds;
    private String bedPrice, bedID, bedStatus;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirmation);

        bedID = getIntent().getStringExtra(BED_ID);
        bedPrice = getIntent().getStringExtra(PRICE_BED);
        bedStatus = getIntent().getStringExtra(STATUS_BED);

        int bedNum = (Integer.parseInt(bedID))-1;

        nameOfBed = (TextView) findViewById(R.id.textview_bed_name);
        priceStatusBed = (TextView) findViewById(R.id.textview_price_status);
        dateOfBooking = (TextView) findViewById(R.id.textview_date);

        cancelButton = (Button) findViewById(R.id.cancel_button);
        confirmButton = (Button) findViewById(R.id.confirm_button);

        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference().child("PG-A").child("Beds").child(bedID);

        nameOfBed.setText("BED No: "+bedID);
        priceStatusBed.setText("Price: "+bedPrice+"\nStatus: "+bedStatus+"\n");

        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        dateOfBooking.setText(""+currentDateTimeString);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Call the function to return to BedBooking Activity
                onCancelButtonDialog();
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bedStatus!=BOOKED)
                {
                    onConfirmButtonDialog();
                }
                //TODO:Show confirmation page
                //TODO: Call the function to return to BedBooking Activity
            }
        });

        //TODO: Create a function that moves back to the BedBooking Activity
    }

    private void onCancelButtonDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.cancel_booking);
        builder.setPositiveButton(R.string.cancel_order, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mReference.child("status").setValue(AVAILABLE);
                priceStatusBed.setText("Price: "+bedPrice+"\nStatus: "+AVAILABLE+"\n");
            }
        });
        builder.setNegativeButton(R.string.no_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(dialogInterface!=null)
                    dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void onConfirmButtonDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.confirm_booking);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(dialogInterface != null)
                {
                    mReference.child("status").setValue(BOOKED);
                    priceStatusBed.setText("Price: "+bedPrice+"\nStatus: "+BOOKED+"\n");
                    dialogInterface.dismiss();
                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
