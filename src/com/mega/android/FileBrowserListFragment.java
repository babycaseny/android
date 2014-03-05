package com.mega.android;

import java.util.ArrayList;
import java.util.List;

import com.mega.sdk.MegaApiAndroid;
import com.mega.sdk.MegaNode;
import com.mega.sdk.NodeList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class FileBrowserListFragment extends Fragment implements OnClickListener, OnItemClickListener{

	Context context;
	ActionBar aB;
	ListView listView;
	MegaBrowserListAdapter adapter;
	MegaApiAndroid megaApi;
	
//	public static final String[] names = new String[] { "salamanca01.png", "salamanca02.png", "salamanca03.png", "salamanca04.png", "salamanca05.png", "salamanca06.png", "salamanca07.png", "salamanca08.png", "salamanca09.png", "salamanca10.png"};
//	public static final Integer[] images = { R.drawable.sal01, R.drawable.sal02, R.drawable.sal03, R.drawable.sal04, R.drawable.sal05, R.drawable.sal06, R.drawable.sal07, R.drawable.sal08, R.drawable.sal09, R.drawable.sal10};
//	public static final String[] names = new String[] { "salamanca01.png", "salamanca02.png", "salamanca03.png", "salamanca04.png", "salamanca05.png", "salamanca06.png", "salamanca07.png", "salamanca08.png", "salamanca09.png", "salamanca10.png", "salamanca11.png", "salamanca12.png", "salamanca13.png", "salamanca14.png", "salamanca15.png", "salamanca16.png", "salamanca17.png", "salamanca18.png", "salamanca19.png"};
//	public static final Integer[] images = { R.drawable.sal01, R.drawable.sal10, R.drawable.sal03, R.drawable.sal10, R.drawable.sal05, R.drawable.sal10, R.drawable.sal07, R.drawable.sal10, R.drawable.sal09, R.drawable.sal10, R.drawable.sal09, R.drawable.sal10, R.drawable.sal09, R.drawable.sal10, R.drawable.sal09, R.drawable.sal10, R.drawable.sal09, R.drawable.sal10, R.drawable.sal09};
	
	List<ItemFileBrowser> rowItems;
	
	//Esto hay que quitarlo cuando haga el visor completo
	ArrayList<String> namesArray = new ArrayList<String>();
	ArrayList<Integer> imageIds = new ArrayList<Integer>();
	//HASTA AQUI 
	
	@Override
	public void onCreate (Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		log("onCreate");

		if (megaApi == null){
			megaApi = ((MegaApplication) ((Activity)context).getApplication()).getMegaApi();
		}
		
		rowItems = new ArrayList<ItemFileBrowser>();
		
		NodeList children = megaApi.getChildren(megaApi.getRootNode());
		for(int i=0; i<children.size(); i++){
			MegaNode node = children.get(i);
			long nodeHandle = node.getHandle();	
			log("nodeHandle=" + nodeHandle);
			ItemFileBrowser item = new ItemFileBrowser(nodeHandle);
			rowItems.add(item);
			
			//Esto hay que quitarlo cuando haga el visor completo
			namesArray.add("NombrePrueba");
			imageIds.add(R.drawable.sal01);
			//HASTA AQUI
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if (aB == null){
			aB = ((ActionBarActivity)context).getSupportActionBar();
		}
		aB.setTitle(getString(R.string.section_cloud_drive));
		
		View v = inflater.inflate(R.layout.fragment_filebrowserlist, container, false);
		        
        listView = (ListView) v.findViewById(R.id.file_list_view_browser);
		listView.setOnItemClickListener(this);
		listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		listView.setItemsCanFocus(false);
		adapter = new MegaBrowserListAdapter(context, rowItems);
		adapter.setPositionClicked(-1);
		listView.setAdapter(adapter);
		
		return v;
	}
	
	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity;
        aB = ((ActionBarActivity)activity).getSupportActionBar();
    }
	
	@Override
	public void onClick(View v) {

		switch(v.getId()){

		}
	}
	
	@Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
            long id) {
		
		Intent i = new Intent(context, FullScreenImageViewer.class);
		i.putExtra("position", position);
		i.putExtra("imageIds", imageIds);
		i.putExtra("names", namesArray);
		startActivity(i);
    }
	
	public int onBackPressed(){
		
		if (adapter.getPositionClicked() != -1){
			adapter.setPositionClicked(-1);
			adapter.notifyDataSetChanged();
			return 1;
		}
		else{
			return 0;
		}
	}
	
	private static void log(String log) {
		Util.log("FileBrowserListFragment", log);
	}

}