package com.boomui.ddcharactersheet;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		//		WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);
        final ActionBar actionBar = getActionBar();
        
        /*
        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new CharacterIconAdapter(this));

        gridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				openCharacterSheet();
				// TODO Auto-generated method stub	
			}
        });
        */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    public void openCharacterSheet(View v){
    	Intent intent = new Intent(this, CharacterSheetActivity.class);
    	startActivity(intent);
    }
    
    public void openCharacterSheet(){
    	Intent intent = new Intent(this, CharacterSheetActivity.class);
    	startActivity(intent);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
    	switch(item.getItemId()){
    	case R.id.new_character_button:
    		openCharacterSheet();
    		return true;
    	case R.id.new_folder_button:
    		return true;
    	case R.id.dice_roller_nav:
    		return true;
    	case R.id.spell_lookup_nav:
    		return true;
    	default:
    		return false;
    	}
    }
    
    
    
    
    
    private class CharacterIconAdapter extends BaseAdapter {
    	private Context mContext;

        public CharacterIconAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
            return mThumbIds.length;
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        // create a new ImageView for each item referenced by the Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView == null) {  // if it's not recycled, initialize some attributes
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(8, 8, 8, 8);
            } else {
                imageView = (ImageView) convertView;
            }

            imageView.setImageResource(mThumbIds[position]);
            return imageView;
        }

        // references to our images
        private Integer[] mThumbIds = {
        	R.drawable.ic_launcher, R.drawable.ic_launcher
        };
        private String[] nameIds = {
        	"Steve", "Jimbo"	
        };
    }
}
