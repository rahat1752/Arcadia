package com.example.arcadia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OlderMessage extends AppCompatActivity {

    private DatabaseReference rootRef, mmData;
    FirebaseAuth mFirebaseAuth;
    String name1,name2,age;
    static String NUM2;
    Button[] txt=new Button[4];
    int i2=10;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_older_message);

        mFirebaseAuth=FirebaseAuth.getInstance();
        txt[0]=findViewById(R.id.sentmsg);
        txt[1]=findViewById(R.id.sentmsg2);
        txt[2]=findViewById(R.id.sentmsg3);
        txt[3]=findViewById(R.id.sentmsg4);


        rootRef= FirebaseDatabase.getInstance().getReference();
        String id= FirebaseAuth.getInstance().getCurrentUser().getUid();
        mmData = FirebaseDatabase.getInstance().getReference();
        mmData.child("MSG").child(id)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.exists() ) {
                            //NUM2 = dataSnapshot.child("n").getValue().toString();
                            //txt[3].setText(NUM2);
                            //i2=Integer.parseInt(NUM2);
                            i2= (int) dataSnapshot.getChildrenCount();
                            txt[3].setText(String.valueOf(i2));
                            RetriveUserInfo(i2);


                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

        if(NUM2!=null)
        {
            //
            //txt[3].setText(NUM2);
        }

    }

    private void RetriveUserInfo(int num) {
        //final int[] i2 = {0};
        //final DatabaseReference[] mmData = new DatabaseReference[1];
        //String ID=mFirebaseAuth.getCurrentUser().getUid();
        String ID = FirebaseAuth.getInstance().getCurrentUser().getUid();





        int y = num - 3;
        int x = 0;
        for (int j = num; j > y; j--) {
            int finalI1 = x;
            Integer j2 = new Integer(j);
            j2.toString();
            //txt[finalI1].setText(String.valueOf(j2));

            rootRef.child("MSG").child(ID).child(String.valueOf(j2))
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            if (dataSnapshot.exists() && (dataSnapshot.hasChild("Msend"))) {
                                name1 = dataSnapshot.child("Msend").getValue().toString();
                                txt[finalI1].setText(name1);

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
            x++;
        }
    }
}

