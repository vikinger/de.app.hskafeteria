package de.app.hskafeteria;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

public class aktionenTab extends Fragment {
    
    private ExpandableListView mExpandableList;

	 
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View inflatedView = inflater.inflate(R.layout.aktionen, container, false);

        mExpandableList = (ExpandableListView) inflatedView.findViewById(R.id.expandable_list_aktionen);


        
        return inflatedView;
    }
	
}