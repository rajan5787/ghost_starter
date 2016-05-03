package com.google.engedu.ghost;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import java.io.IOException;
import java.util.Random;

public class GhostActivity extends ActionBarActivity {
    private static final String COMPUTER_TURN = "Computer's turn";
    private static final String USER_TURN = "Your turn";
    private GhostDictionary dictionary;
    private boolean userTurn = false;
    private Random random = new Random();
    private String wordFragment = "";
    private TextView wordTextView;
    private TextView label;
    private boolean condition = true;
    private String[] alphabate = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s",
            "t","u","v","w","x","y","z"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ghost);
        wordTextView = (TextView)findViewById(R.id.ghostText);
        label = (TextView) findViewById(R.id.gameStatus);
        try {
            dictionary = new FastDictionary(getAssets().open("words.txt"));//project 4
            dictionary = new SimpleDictionary(getAssets().open("words.txt"));//project 3

        } catch (IOException e) {
            e.printStackTrace();
        }
        onStart(null);
    }
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if(keyCode >= 29 && keyCode <= 54){
            if(condition==true) {
                wordFragment = wordFragment.concat(event.getDisplayLabel() + "");
                wordFragment = wordFragment.toLowerCase();
                wordTextView.setText(wordFragment);
                computerTurn();
            }
        }
        return super.onKeyUp(keyCode, event);
    }
    public void challenge(View view){
        if(wordFragment.length() >= 4) {
            if (dictionary.isWord(wordFragment)) {
                label.setText("You win");
                condition = false;
            } else {
                String nextWord = dictionary.getAnyWordStartingWith(wordFragment);
                if(nextWord == null) {
                    label.setText("You win");
                    condition = false;
                }
                else{
                    label.setText(nextWord + ". You loose");
                    condition = false;
                }
            }
        }
        else{
            label.setText("Word Fragment is too small to challenge");

        }
    }
    private void computerTurn() {
        userTurn = false;
        if(dictionary.isWord(wordFragment.toLowerCase())){
            label.setText("Computer Wins");
            condition = false;
        }
        else {
            String nextWord = dictionary.getAnyWordStartingWith(wordFragment.toLowerCase());
            if(nextWord == null){
                if(wordFragment.length() >= 4) {
                    label.setText("Computer challenged you. Computer wins");
                    condition = false;
                }
                else{
                    wordFragment = (wordFragment + alphabate[random.nextInt(26)]);
                    wordTextView.setText(wordFragment);
                }
            }
            else{
                wordFragment = nextWord.substring(0,wordFragment.length()+1);
                wordTextView.setText(wordFragment);
                userTurn = true;
                label.setText(USER_TURN);
            }
        }
    }
    public boolean onStart(View view) {
        userTurn = random.nextBoolean();
        condition = true;
        TextView text = (TextView) findViewById(R.id.ghostText);
        text.setText("");
        wordFragment = "";
        TextView label = (TextView) findViewById(R.id.gameStatus);
        if (userTurn) {
            label.setText(USER_TURN);
        } else {
            label.setText(COMPUTER_TURN);
            computerTurn();
        }
        return true;
    }
}
