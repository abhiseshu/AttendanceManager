package com.example.attendancemanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity<lview> extends AppCompatActivity {

    CustomAdapter customAdapter;
    DbHelper dbHelper;
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView);
        dbHelper = new DbHelper(this);

        loadSubject();
        listView.setClickable(true);
        listView.setLongClickable(true);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    Log.i("ERoro,","LoG SKD");
                    int mposition = listView.getPositionForView(view);
                    Word word = customAdapter.getItem(position);
                    final String mSubject = word.getwSubject();
                    AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Do you want to remove the Subject")
                            .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    dbHelper.deleteSubject(mSubject);
                                    loadSubject();
                                }
                            })
                            .setNegativeButton("NO",null)
                            .create();
                    dialog.show();

                }catch (Exception e)
                {
                    Log.i("Error","row"+e);
                }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.info,menu);
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void loadSubject(){
        ArrayList<Word> SubList = dbHelper.getSubjectList();
        if (customAdapter == null){
            customAdapter =  new CustomAdapter(this,R.layout.row,SubList);
            listView.setAdapter(customAdapter);
        }else {
            customAdapter.clear();
            customAdapter.addAll(SubList);
            customAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_subject_id:
               final EditText enterSubject = new EditText(this);
               AlertDialog dialog =  new AlertDialog.Builder(this)
                       .setTitle("Enter the Subject")
                       .setView(enterSubject)
                       .setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {
                                    String mSubject = enterSubject.getText().toString();
                                    dbHelper.insertSubject(mSubject);
                                    // load the task
                                    loadSubject();
                           }
                       })
                       .setNegativeButton("Cancel",null)
                       .create();
               dialog.show();
               return true;
            case R.id.show_info:
                Intent inten;
                inten = new Intent(MainActivity.this,AttendInfo.class);

                startActivity(inten);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void AttendMethod(View view){
        try{
            int position = listView.getPositionForView(view);
            Word word = customAdapter.getItem(position);
            String mSubject = word.getwSubject();
            int Present = Integer.parseInt(word.getwPresent());
            int total = Integer.parseInt(word.getwTotal());
            Present+=1;
            total+=1;
            dbHelper.insertPresent(Present,mSubject);
            dbHelper.insertTotal(total,mSubject);
            loadSubject();
        }catch (Exception e){
            Log.i("ERROR", "Show"+e );
        }
    }
    public void BunkMethod(View view){
        try{
            int position = listView.getPositionForView(view);
            Word word = customAdapter.getItem(position);
            String mSubject = word.getwSubject();
            int total = Integer.parseInt(word.getwTotal());
            total+=1;
            dbHelper.insertTotal(total,mSubject);
            loadSubject();
        }catch (Exception e){
            Log.i("ERROR", "Show"+e );
        }
    }

/*

    public void rowMethod(View view){
        try {
            int position = listView.getPositionForView(view);
            Word word = customAdapter.getItem(position);
            final String mSubject = word.getwSubject();
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("Do you want to remove the Subject")
                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dbHelper.deleteSubject(mSubject);
                            loadSubject();
                        }
                    })
                    .setNegativeButton("NO",null)
                    .create();
            dialog.show();

        }catch (Exception e)
        {
            Log.i("Error","row"+e);
        }
    }

*/

}
