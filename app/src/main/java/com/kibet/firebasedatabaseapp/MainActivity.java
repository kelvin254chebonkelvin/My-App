package com.kibet.firebasedatabaseapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    EditText name,email,number;
    Button save,view;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = findViewById(R.id.editname);
        email = findViewById(R.id.editemail);
        number =findViewById(R.id.editphone);
        save =findViewById(R.id.btnsave);
        view =findViewById(R.id.btnview);

        //Yaani mse awezi cancel hadi imalize kusave io infomation

        dialog = new ProgressDialog(this);
        dialog.setMessage("Saving...");

        //To disable someone from cancelling the saving prosess even when he touches the back button. So we use the following line of codes

        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String jina = name.getText().toString();
                String arafa = email.getText().toString();
                String simu = number.getText().toString();
                if (jina.isEmpty()||arafa.isEmpty()||simu.isEmpty()){
                    Toast.makeText(MainActivity.this, "Fill All Inputs", Toast.LENGTH_SHORT).show();
                }else {
                    long time = System.currentTimeMillis();

                    //Creating a table in the Database

                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Watu/"+time);
                    Item x = new Item(jina,arafa,simu);
                    dialog.show();
                    ref.setValue(x).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            dialog.dismiss();
                            if (task.isSuccessful()){
                                Toast.makeText(MainActivity.this, "Save Successful", Toast.LENGTH_SHORT).show();
                                name.setText("");
                                email.setText("");
                                number.setText("");
                            }else {
                                Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,UsersActivity.class);
                startActivity(intent);
            }
        });

    }
}
