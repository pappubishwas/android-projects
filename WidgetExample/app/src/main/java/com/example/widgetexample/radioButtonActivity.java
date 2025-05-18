package com.example.widgetexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class radioButtonActivity extends AppCompatActivity {

    String str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio_button);
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        if (view.getId() == R.id.rdbt1 && checked) {
            str="Lenevo";
            Toast.makeText(this, "Lenovo chosen!", Toast.LENGTH_SHORT).show();
        } else if (view.getId() == R.id.rdbt2 && checked) {
            str="Asus";
            Toast.makeText(this, "Asus chosen!", Toast.LENGTH_SHORT).show();
        } else if (view.getId() == R.id.rdbt3 && checked) {
            str="HP";
            Toast.makeText(this, "HP chosen!", Toast.LENGTH_SHORT).show();
        } else {
            str="Not Selected";
            Toast.makeText(this, "Not chosen", Toast.LENGTH_SHORT).show();
        }
    }

    public void view(View view) {
        Intent intent=new Intent(this, RadioInputDisplayActivity.class);
        EditText editText=findViewById(R.id.edit);
        intent.putExtra("radioChosen",str);
        intent.putExtra("editValue",editText.getText().toString());
        startActivity(intent);
    }
}






//
//public class radioButtonActivity extends AppCompatActivity {
//
//    private static final int RDBT1_ID = R.id.rdbt1;
//    private static final int RDBT2_ID = R.id.rdbt2;
//    private static final int RDBT3_ID = R.id.rdbt3;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_radio_button);
//    }
//
//    public void onRadioButtonClicked(View view) {
//        boolean checked = ((RadioButton) view).isChecked();
//        int viewId = view.getId();
//
//        if (viewId == RDBT1_ID && checked) {
//            Toast.makeText(this, "Lenovo chosen!", Toast.LENGTH_SHORT).show();
//        } else if (viewId == RDBT2_ID && checked) {
//            Toast.makeText(this, "Asus chosen!", Toast.LENGTH_SHORT).show();
//        } else if (viewId == RDBT3_ID && checked) {
//            Toast.makeText(this, "HP chosen!", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(this, "Not chosen", Toast.LENGTH_SHORT).show();
//        }
//    }
//}





// I don't know why switch case not working.....................



//public class radioButtonActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_radio_button);
//    }
//
//    public void onRadioButtonClicked(View view) {
//        boolean checked = ((RadioButton) view).isChecked();
//
//        // Check which radio button was clicked
//        switch (view.getId()) {
//            case R.id.rdbt1:
//                if (checked) {
//                    Toast.makeText(this, "Lenovo chosen!", Toast.LENGTH_SHORT).show();
//                }
//                break;
//            case R.id.rdbt2:
//                if (checked) {
//                    Toast.makeText(this, "Asus chosen!", Toast.LENGTH_SHORT).show();
//                }
//                break;
//            case R.id.rdbt3:
//                if (checked) {
//                    Toast.makeText(this, "HP chosen!", Toast.LENGTH_SHORT).show();
//                }
//                break;
//            default:
//                Toast.makeText(this, "Not chosen", Toast.LENGTH_SHORT).show();
//                break;
//        }
//    }
//}
