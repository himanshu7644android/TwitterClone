package com.examplmakecodeeasy.twitterclone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.examplmakecodeeasy.twitterclone.databinding.ActivityTweet3Binding;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TweetActivity3 extends AppCompatActivity implements View.OnClickListener {
    ActivityTweet3Binding binding;
    private ArrayList<String> AllUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTweet3Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.tweetListVew.setOnClickListener(this);
        binding.tweetListVew.setOnItemClickListener((AdapterView.OnItemClickListener) this);


        binding.btnTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseObject parseObject = new ParseObject("MyTweet");
                parseObject.put("tweet",binding.edtTweet.getText().toString());
                parseObject.put("usr",ParseUser.getCurrentUser().getUsername());

                final ProgressDialog progressDialog = new ProgressDialog(TweetActivity3.this);
                progressDialog.setMessage("Loading..");
                progressDialog.show();
                parseObject.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Toast.makeText(TweetActivity3.this, ParseUser.getCurrentUser().getUsername() + binding.edtTweet.getText().toString() + "Tweeted", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(TweetActivity3.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                });
            }
        });

        binding.btnShowTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ArrayList<HashMap<String,String>> tweetList = new ArrayList<>();
                final SimpleAdapter adapter = new SimpleAdapter(TweetActivity3.this,tweetList, android.R.layout.simple_list_item_2,new String[]{"tweetUserName","tweetValue"},new int[]{android.R.id.text1,android.R.id.text2});

                try {
                    ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("MyTweet");
                    parseQuery.whereContainedIn("user",ParseUser.getCurrentUser().getList("fanOf"));
                    parseQuery.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> objects, ParseException e) {
                            if (objects.size() > 0 && e == null){
                                for (ParseObject tweetObjects : objects){
                                    HashMap<String,String > userTweet = new HashMap<>();
                                    userTweet.put("tweetUserName",tweetObjects.getString("usr"));
                                    userTweet.put("tweetValue",tweetObjects.getString("tweet"));
                                    tweetList.add(userTweet);


                                }

                                binding.tweetListVew.setAdapter(adapter);


                            }
                        }
                    });



                }catch (Exception e){
                    e.printStackTrace();
                }


            }
        });


    }

    @Override
    public void onClick(View v) {

    }
}