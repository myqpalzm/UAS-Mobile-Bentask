package id.ac.umn.uas_mobile_bentask;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateCategoryActivity extends AppCompatActivity {

    EditText category_input;
    Button update_button, delete_button;
    String id,title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_category);

        category_input = findViewById(R.id.category_input2);
        update_button = findViewById(R.id.update_button);

        //First we call this
        getAndSetIntentData();

        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateCategoryActivity.this);
                title = category_input.getText().toString().trim();
                myDB.updateData(id, title);
                Intent intent = new Intent(UpdateCategoryActivity.this, CategoryActivity.class);
                startActivity(intent);
            }
        });
        getAndSetIntentData();
    }

    void getAndSetIntentData(){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("title")){
            id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");

            category_input.setText(title);

        }else{
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }
}