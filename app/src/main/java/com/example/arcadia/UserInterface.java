package com.example.arcadia;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.midi.MidiOutputPort;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.OffsetTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Date;

public class UserInterface extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;
    private TextView logout;
    private Button button, btn;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("User");
    private String mAuth;
    private DatabaseReference mData;
   // private DatabaseReference rootRef;

    List<MessageChatModel> messageChatModelList =  new ArrayList<>();
    RecyclerView recyclerView;
    MessageChatAdapter adapter ;
    int i=1,flag=0;
    String NUM=null;

    public EditText messageET;
    ImageView sendBtn;
    Date date;
    String t;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_interface);
        // logout=findViewById(R.id.logout);
        messageET = findViewById(R.id.messageET);
        sendBtn = (ImageView) findViewById(R.id.sendBtn);

        // mAuth=FirebaseAuth.getInstance().getUid();
        mAuth=FirebaseAuth.getInstance().getCurrentUser().getUid();
        mData= FirebaseDatabase.getInstance().getReference();
        //rootRef= FirebaseDatabase.getInstance().getReference();
        if(flag==0) {
            mData.child("MSG").child(mAuth)
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            if (dataSnapshot.exists()) {
                                i = (int) dataSnapshot.getChildrenCount();
                                i++;
                                flag = 1;

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
        }



        // messageET= findViewById(R.id.messageET);
        button=findViewById(R.id.button222);
        btn=findViewById(R.id.retrive);

        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        LinearLayoutManager manager = new LinearLayoutManager(UserInterface.this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(manager);

        OffsetTime offset = OffsetTime.now();

        t=String.valueOf(offset.getHour() + " : " + offset.getMinute());
        MessageChatModel model1 = new MessageChatModel(
                "Hello. How are you today?",
                t,
                0
        );
        MessageChatModel model2 = new MessageChatModel(
                "Hey! I'm fine. Thanks for asking!",
                t,
                1
        );
        MessageChatModel model3 = new MessageChatModel(
                "Sweet! So, what do you wanna do today?",
                "11:00 PM",
                0
        );
        MessageChatModel model4 = new MessageChatModel(
                "Nah, I dunno. Play soccer.. or learn more coding perhaps?",
                // Calendar.getInstance().getTime(),
                "10:00 PM",
                1
        );


        messageChatModelList.add(model1);
        messageChatModelList.add(model2);
        messageChatModelList.add(model3);
        messageChatModelList.add(model4);

        recyclerView.smoothScrollToPosition(messageChatModelList.size());
        adapter = new MessageChatAdapter(messageChatModelList, UserInterface.this );
        recyclerView.setAdapter(adapter);



        String msg;
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //String msgno=Integer.toString(i);
                String msgno= myRef.push().getKey();

                String msg = messageET.getText().toString();

                OffsetTime offset = OffsetTime.now();
                t=String.valueOf(offset.getHour() + " : " + offset.getMinute());

                Messages m=new Messages(msg);
                String id = FirebaseAuth.getInstance().getCurrentUser().getUid();


                Integer j=new Integer(i);
                j.toString();

                //Messages num=new Messages(i);


                if(!id.isEmpty()) {
                    //myRef.child(id).setValue(u);
                    mData.child("MSG").child(id).child(String.valueOf(j)).setValue(m);
                    mData.child("Number").child(id).child("n").setValue(String.valueOf(j));
                }
                i++;

                MessageChatModel model = new MessageChatModel(
                        msg,
                        // Calendar.getInstance().getTime(),
                        t,
                        0
                );
                messageChatModelList.add(model);
                recyclerView.smoothScrollToPosition(messageChatModelList.size());
                adapter.notifyDataSetChanged();
                messageET.setText("");


            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UserInterface.this, "You have Successfully Saved Data", Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();
                Intent intoMain = new Intent(UserInterface.this,LoginActivity.class);
                startActivity(intoMain);

            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //    Toast.makeText(UserInterface.this, "You have Successfully Saved Data", Toast.LENGTH_SHORT).show();
                //  FirebaseAuth.getInstance().signOut();
                Intent intoR = new Intent(UserInterface.this, OlderMessage.class);
                startActivity(intoR);

            }
        });

    }



}