package com.example.shubhraj.firebase;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shubhraj on 09-09-2017.
 */

public class BedBooking extends AppCompatActivity
{
    private static final String TAG = "BedBooking";
    private static final String BED_ID = "bed_id";
    private static final String PRICE_BED = "price";
    private static final String STATUS_BED = "status";
    private Button bed1, bed2, bed3, bed4, bed5, bookButton;
    private TextView bedName, bedPrice, bedStatus;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private List<Beds> mBeds;

    private static final String AVAILABLE = "Available";
    private static final String UNAVAILABLE = "UnAvailable";
    private static final String BOOKED = "Booked";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floors);

        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference().child("PG-A").child("Beds");

        bed1 = (Button) findViewById(R.id.button_bed_1);
        bed2 = (Button) findViewById(R.id.button_bed_2);
        bed3 = (Button) findViewById(R.id.button_bed_3);
        bed4 = (Button) findViewById(R.id.button_bed_4);
        bed5 = (Button) findViewById(R.id.button_bed_5);
        bookButton = (Button) findViewById(R.id.button_to_book);

        bedName = (TextView) findViewById(R.id.bed_name_textView);
        bedPrice = (TextView) findViewById(R.id.price_textView);
        bedStatus = (TextView) findViewById(R.id.availablity_textView);

        mBeds = new ArrayList<Beds>();
        mReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Beds bedInfo = dataSnapshot.getValue(Beds.class);
                mBeds.add(bedInfo);
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Beds bedInfo = dataSnapshot.getValue(Beds.class);
                String bedNum = bedInfo.getId().toString();
                int bedNumber = Integer.parseInt(bedNum.substring(4));
                mBeds.set(bedNumber-1,bedInfo);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG,"Cancelled Connection",databaseError.toException());
            }
        });

        bed1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBedTouched(0);
            }
        });

        bed2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBedTouched(1);
            }
        });

        bed3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBedTouched(2);
            }
        });

        bed4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBedTouched(3);
            }
        });

        bed5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBedTouched(4);
            }
        });

        bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String bed_id = changeStatusToUnavailable();
                Intent intent = new Intent(BedBooking.this,ConfirmBooking.class);
                intent.putExtra(BED_ID,bed_id);
                intent.putExtra(PRICE_BED,bedPrice.getText().toString());
                intent.putExtra(STATUS_BED,bedStatus.getText().toString());
                startActivity(intent);
            }
        });
    }

    private String changeStatusToUnavailable()
    {
        /*Beds bedSelected = new Beds(bedName.getText().toString(),
                Integer.parseInt(bedPrice.getText().toString()), UNAVAILABLE);*/

        String bed_id=bedName.getText().toString();
        bed_id = bed_id.substring(4);
        mReference.child(bed_id).child("status").setValue(UNAVAILABLE);
        return bed_id;
    }

    public void onBedTouched(int bedNum)
    {
        bedName.setText(mBeds.get(bedNum).getId());
        bedPrice.setText(""+mBeds.get(bedNum).getPrice());
        bedStatus.setText(mBeds.get(bedNum).getStatus());
    }


}
