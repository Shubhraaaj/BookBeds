package com.example.shubhraj.firebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity
{
    private static final String TAG = "MainActivity";
    private TextView mTextView;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = (TextView) findViewById(R.id.text_view);

        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference().child("PG-A").child("Beds");
        final StringBuilder builder = new StringBuilder();
        onBooked(1);
        mReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Beds bed1 = dataSnapshot.getValue(Beds.class);
                builder.append("ID:"+bed1.getId()+"\nPrice:"+bed1.getPrice()+
                        "\nStatus:"+bed1.getStatus()+"\n\n");
                mTextView.setText(builder.toString());
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

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
    }

    public void onBooked(int bedNo)
    {
        if(bedNo==1)
        {
            Beds beds = new Beds("101",500,"Booked");
            mReference.child("01").setValue(beds);
        }
    }
}
