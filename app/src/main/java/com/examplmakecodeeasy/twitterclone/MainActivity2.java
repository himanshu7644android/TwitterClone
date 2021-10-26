package com.examplmakecodeeasy.twitterclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Matrix3f;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Toast;

import com.examplmakecodeeasy.twitterclone.databinding.ActivityMain2Binding;
import com.parse.FindCallback;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ActivityMain2Binding binding;
    private ArrayList<String> tUsers;
    private ArrayAdapter adapter;
    private String followedUser = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        tUsers= new ArrayList<>();
        adapter=new ArrayAdapter(this, android.R.layout.simple_list_item_checked,tUsers);
        binding.listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        binding.listView.setOnItemClickListener(this);

        try {

            ParseQuery<ParseUser> query = ParseUser.getQuery();
            query.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());
            query.findInBackground(new FindCallback<ParseUser>() {
                @Override
                public void done(List<ParseUser> objects, ParseException e) {
                    if (objects.size() > 0 && e == null) {

                        for (ParseUser twitterUser : objects) {

                            tUsers.add(twitterUser.getUsername());

                        }
                       binding.listView.setAdapter(adapter);

                        for (String twitterUser: tUsers){
                            if (ParseUser.getCurrentUser().getList("fanOf") != null) {
                                if (ParseUser.getCurrentUser().getList("fanOf").contains(twitterUser)) {
                                    followedUser = followedUser + twitterUser + "\n" ;



                                    binding.listView.setItemChecked(tUsers.indexOf(twitterUser), true);
                                    FancyToast.makeText(MainActivity2.this, ParseUser.getCurrentUser().getUsername()+ "is following " + followedUser, FancyToast.LENGTH_LONG, FancyToast.INFO, true).show();
                                }
                            }
                        }

                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }



        //ParseInstallation.getCurrentInstallation().saveInBackground();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.my_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

       switch (item.getItemId()) {
           case R.id.logout:
               ParseUser.getCurrentUser().logOutInBackground(new LogOutCallback() {
                   @Override
                   public void done(ParseException e) {
                       Intent intent = new Intent(MainActivity2.this, MainActivity.class);
                       startActivity(intent);
                       finish();
                   }
               });
               break;


           case R.id.tweet:
               Intent intent = new Intent(MainActivity2.this, TweetActivity3.class);
               startActivity(intent);
               break;

       }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        CheckedTextView checkedTextView = (CheckedTextView) view;

        if (checkedTextView.isChecked()){


            FancyToast.makeText(MainActivity2.this, tUsers.get(position) + "is followed", FancyToast.LENGTH_LONG, FancyToast.INFO, true).show();
            ParseUser.getCurrentUser().add("fanOf",tUsers.get(position));
        }else {
            FancyToast.makeText(MainActivity2.this, tUsers.get(position) + "is unfollowed", FancyToast.LENGTH_LONG, FancyToast.INFO, true).show();

            ParseUser.getCurrentUser().getList("fanOf").remove(tUsers.get(position));
            List currentUserFanOfList = ParseUser.getCurrentUser().getList("fanOf");
            ParseUser.getCurrentUser().remove("fanOf");
            ParseUser.getCurrentUser().put("fanOf",currentUserFanOfList);
        }

        ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e == null){
                    FancyToast.makeText(MainActivity2.this,  "change saved", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();


                }
            }
        });


    }
}