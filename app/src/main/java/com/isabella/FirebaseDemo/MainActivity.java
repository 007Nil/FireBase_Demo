package com.isabella.FirebaseDemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.isabella.FirebaseDemo.Model.Message;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button logout;
    private Button sendMessage;
    private EditText message;
    private ListView listView;

    private Message message_info = new Message();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logout = findViewById(R.id.logout);
        message = findViewById(R.id.message);
        sendMessage = findViewById(R.id.send);
        listView = findViewById(R.id.listView);


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(MainActivity.this, "Logout Successful !!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
        });

        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_message = message.getText().toString();

                if (TextUtils.isEmpty(txt_message)){
                    Toast.makeText(MainActivity.this, "Message Feild is Emply", Toast.LENGTH_SHORT).show();
                }else{
                    FirebaseDatabase.getInstance().getReference().child("Demo_Database").push().child("Message").setValue(txt_message);
                }
            }
        });

//        FirebaseDatabase.getInstance().getReference().child("Demo_Database").child("Message").setValue("Test Message");
        ArrayList<String> messageList = new ArrayList<>();
        ArrayAdapter adopter = new ArrayAdapter<String>(this, R.layout.list_item, messageList);
        listView.setAdapter(adopter);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Demo_Database");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messageList.clear();
                for (DataSnapshot each: snapshot.getChildren()){
                    messageList.add(each.getValue().toString().replace("{", "").replace("Message=", "")
                    .replace("}",""));
//                    message_info = each.getValue(Message.class);
//                    String txt_message_info = message_info.getMessage();
//                    System.out.println(txt_message_info);
//                    messageList.add(txt_message_info);

                }
                adopter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}