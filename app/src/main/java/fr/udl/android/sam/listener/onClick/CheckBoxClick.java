package fr.udl.android.sam.listener.onClick;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckedTextView;
import android.widget.Toast;

import fr.udl.android.sam.activities.MapActivity;

public class CheckBoxClick implements AdapterView.OnItemClickListener{
    private Context context;
    //private  List<String> checkedList;

    public CheckBoxClick(Context context/*, List<String> checkedList*/){
        this.context = context;
        //this.checkedList = checkedList;
    }
 
    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

        CheckedTextView ctv = (CheckedTextView) arg1;

        if(ctv.isChecked()){
            MapActivity.checkedList.add(ctv.getText().toString());
            Toast.makeText(context, ctv.getText().toString() + " added", Toast.LENGTH_SHORT).show();
        }else{
            MapActivity.checkedList.remove(ctv.getText().toString());
            Toast.makeText(context, ctv.getText().toString() + " removed", Toast.LENGTH_SHORT).show();
        }   
    }   
}