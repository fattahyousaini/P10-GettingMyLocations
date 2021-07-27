package com.myapplicationdev.android.p10_gettingmylocations;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class RecordActivity extends AppCompatActivity {
    Button btnRefresh, btnFav;
    TextView tv;
    ListView lv;
    String folderLocation;
    ArrayList al;
    ArrayAdapter aa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        btnRefresh = findViewById(R.id.btnRefresh);
        btnFav = findViewById(R.id.btnFav);
        tv = findViewById(R.id.tv);
        lv = findViewById(R.id.lv);
        al = new ArrayList();
        folderLocation = getFilesDir().getAbsolutePath() + "/ProblemStatementP10";
        File folder = new File(folderLocation);
        if (folder.exists() == false){
            boolean result = folder.mkdir();
            if (result == true){
                Log.d("File Read/Write", "Folder created");
            }
        }
        readFile();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selected = al.get(i).toString();
                Toast.makeText(RecordActivity.this, al.get(i).toString(), Toast.LENGTH_SHORT).show();
                AlertDialog.Builder myBuilder = new AlertDialog.Builder(RecordActivity.this);
                myBuilder.setTitle("Add to favorite?");
                myBuilder.setMessage("Do You want to set location to favorite?");
                myBuilder.setNegativeButton("No", null);
                myBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try{
                            String folderLocation_II = getFilesDir().getAbsolutePath() + "/ProblemStatementP10";;
                            File targetFile_I = new File(folderLocation_II, "favorite.txt");
                            FileWriter write_I = new FileWriter(targetFile_I, true);
                            write_I.write(selected);
                            write_I.flush();
                            write_I.close();
                        }
                        catch (Exception e){
                            Toast.makeText(RecordActivity.this, "Failed to write!", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                });
                myBuilder.show();
            }
        });
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aa.notifyDataSetChanged();
                readFile();
            }
        });
        btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aa.clear();
                readFavFile();
                aa.notifyDataSetChanged();
            }
        });
    }
    private void readFile() {
        File targetFile = new File(folderLocation, "data.txt");
        if (targetFile.exists() == true) {
            try {
                FileReader reader = new FileReader(targetFile);
                BufferedReader br = new BufferedReader(reader);
                //While it returns something (content is present),
                // continue to read line by line until end of file
                al.clear();
                String line = br.readLine();
                while (line != null) {
                    al.add(line + "\n");
                    line = br.readLine();
                }
                tv.setText("Number of records: " + al.size());
                br.close();
                reader.close();
            } catch (Exception e) {
                Toast.makeText(RecordActivity.this, "Failed to read!", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
            aa = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, al);
            lv.setAdapter(aa);
        }
    }
    private void readFavFile() {
        File targetFile2 = new File(folderLocation, "favorite.txt");
        if (targetFile2.exists() == true) {
            try {
                FileReader reader = new FileReader(targetFile2);
                BufferedReader br = new BufferedReader(reader);
                //While it returns something (content is present),
                // continue to read line by line until end of file
                al.clear();
                String line = br.readLine();
                while (line != null) {
                    al.add(line + "\n");
                    line = br.readLine();
                }
                tv.setText("Number of records: " + al.size());
                br.close();
                reader.close();
            } catch (Exception e) {
                Toast.makeText(RecordActivity.this, "Failed to read!", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
            aa = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, al);
            lv.setAdapter(aa);
        }

    }
}