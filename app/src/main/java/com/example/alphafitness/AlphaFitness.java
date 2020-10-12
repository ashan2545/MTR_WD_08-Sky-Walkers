package com.example.alphafitness;

        import androidx.annotation.NonNull;
        import androidx.appcompat.app.AppCompatActivity;

        import android.os.Bundle;
        import android.text.TextUtils;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Toast;

        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.Query;
        import com.google.firebase.database.ValueEventListener;

        import java.util.HashMap;
        import java.util.Map;

public class AlphaFitness extends AppCompatActivity implements View.OnClickListener{
    Button btnaccept,btnedite,btndelete,btnView;
    EditText txtSche,txtTrai,txtPay,txtid;
    DatabaseReference dbRef,dbRef2;
    AddWeek adW;
    Map<String,Object> updateMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alpha_fitness);

        txtSche = findViewById(R.id.txtWs);
        txtTrai = findViewById(R.id.txtWt);
        txtPay = findViewById(R.id.txtWp);
        txtid = findViewById(R.id.txtWid);

        adW = new AddWeek();

        btnaccept = findViewById(R.id.btnAccept);
        btnedite = findViewById(R.id.btnEdite);
        btndelete = findViewById(R.id.btnDelete);
        btnView = findViewById(R.id.btnShow);

        btnaccept.setOnClickListener(this);
        btnedite.setOnClickListener(this);
        btnView.setOnClickListener(this);
        btndelete.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnAccept:Save();
                break;
            case R.id.btnEdite:Edit();
                break;
            case R.id.btnDelete:Delete();
                break;
            case R.id.btnShow:View();
                break;
        }
    }

    private void Save() {

        dbRef = FirebaseDatabase.getInstance().getReference().child("weekly package");
        try{
            if(TextUtils.isEmpty(txtSche.getText().toString()))
                Toast.makeText(getApplicationContext(),"Empty Schedule",Toast.LENGTH_SHORT).show();
            else if(TextUtils.isEmpty(txtTrai.getText().toString()))
                Toast.makeText(getApplicationContext(),"Empty Trainer",Toast.LENGTH_SHORT).show();
            else if(TextUtils.isEmpty(txtPay.getText().toString()))
                Toast.makeText(getApplicationContext(),"Empty Payment",Toast.LENGTH_SHORT).show();
            else if(TextUtils.isEmpty(txtid.getText().toString()))
                Toast.makeText(getApplicationContext(),"Empty ID",Toast.LENGTH_SHORT).show();
            else {

                adW.setSID(txtid.getText().toString().trim());
                adW.setSchedule(txtSche.getText().toString().trim());
                adW.setTrainer(txtTrai.getText().toString().trim());
                adW.setPayment(txtPay.getText().toString().trim());

                dbRef.push().setValue(adW);


                Toast.makeText(getApplicationContext(), "Successfully Inserted", Toast.LENGTH_SHORT).show();
                clearControls();
            }

        } catch (Exception nfe) {
            Toast.makeText(getApplicationContext(),"Error!!!",Toast.LENGTH_SHORT).show();
        }
    }

    private void clearControls(){
        txtSche.setText("");
        txtTrai.setText("");
        txtPay.setText("");

    }
    public void Edit(){
        dbRef2 = FirebaseDatabase.getInstance().getReference();
        Query updateQuery = dbRef.child("weekly package").orderByChild("Sid").equalTo(txtid.getText().toString().trim());
        updateMap = new HashMap<String,Object>();
        updateMap.put("SID",txtid.getText().toString().trim());
        updateMap.put("schedule",txtSche.getText().toString().trim());
        updateMap.put("Trainer",txtTrai.getText().toString().trim());
        updateMap.put("Payment",txtPay.getText().toString().trim());
        updateQuery.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot updateSnapshot : dataSnapshot.getChildren()) {


                    updateSnapshot.getRef().updateChildren(updateMap);
                    Toast.makeText(getApplicationContext(), "Successfully Updated", Toast.LENGTH_SHORT).show();
                    clearControls();


                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void Delete() {
        dbRef = FirebaseDatabase.getInstance().getReference();
        Query deleteQuery = dbRef.child("weekly package").orderByChild("SID").equalTo(txtid.getText().toString().trim());
        deleteQuery.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                dataSnapshot.getRef().removeValue();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void View()
    {

        Query readRef = FirebaseDatabase.getInstance().getReference().child("weekly package").orderByChild("SID").equalTo(txtid.getText().toString().trim());
        readRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren())
                {
                    txtPay.setText(dataSnapshot.child("Payment").getValue().toString());
                    txtSche.setText(dataSnapshot.child("schedule").getValue().toString());
                    txtTrai.setText(dataSnapshot.child("Trainer").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}


