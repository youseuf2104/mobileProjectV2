package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //define listview
    ListView listView;
    //array to store song items
    String[] songs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.SongListView);
        runtimePermission();
    }

    //ask for permission
    public void runtimePermission()
    {
        Dexter.withContext(this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    //display songs on the on permission granted
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        showSongs();

                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();

                    }
                }).check();
    }
    //method to find songs from external storage
    public ArrayList<File> searchSong (File file)
    {
        ArrayList<File> arrayList = new ArrayList<>();
        File[] files = file.listFiles();

        //for loop to check if its a mp3 file or a folder
        for (File singlefile: files)
        {
            //check file if it's a directory or not, and also check if it's hidden
            if (singlefile.isDirectory() && !singlefile.isHidden())
            {
                //transfer directory to array list to check for songs inside the folder
                arrayList.addAll(searchSong(singlefile));
            }
            //check if the file is a mp3 file or wav file
            else
            {
                if (singlefile.getName().endsWith(".mp3") || singlefile.getName().endsWith(".wav") )
                {
                    //if thats true add to the array list
                    arrayList.add(singlefile);
                }
            }
        }
        return arrayList;
    }

    //display songs
    void showSongs()
    {
        final ArrayList<File> mySongs = searchSong(Environment.getExternalStorageDirectory());

        songs = new String[mySongs.size()];
        //for loop to store all the songs in an item
        for (int x = 0; x<mySongs.size(); x++)
        {
            //add single items in the array
            songs[x] = mySongs.get(x).getName().toString().replace(".mp3", "").replace(".wav", "");
        }
        //attach the items from the list view
        /*ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, songs);
        listView.setAdapter(myAdapter);*/

        customAdapter customAdapter = new customAdapter();
        listView.setAdapter(customAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int x, long l) {
                String songName = (String) listView.getItemAtPosition(x);
                startActivity(new Intent(getApplicationContext(), MusicPlayerActivity.class)
                        .putExtra("songs", mySongs)
                        .putExtra("songname", songName)
                        .putExtra("position", x));

            }
        });


    }

    class customAdapter extends BaseAdapter
    {

        @Override
        public int getCount() {
            return songs.length;
        }

        @Override
        public Object getItem(int x) {
            return null;
        }

        @Override
        public long getItemId(int x) {
            return 0;
        }

        @Override
        public View getView(int x, View view, ViewGroup viewGroup) {

            View myView = getLayoutInflater().inflate(R.layout.list_song, null);
            TextView textsongs =myView.findViewById(R.id.songnametxt);
            textsongs.setSelected(true);
            textsongs.setText(songs[x]);

            return myView;
        }


    }


}