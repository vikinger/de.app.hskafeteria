package de.app.hskafeteria;

import java.util.Arrays;

import com.facebook.widget.LoginButton;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SplashFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, 
	        ViewGroup container, Bundle savedInstanceState) {
	    View view = inflater.inflate(R.layout.splash, 
	            container, false);
//	    LoginButton authButton = (LoginButton) view.findViewById(R.id.login_button);
//	    authButton.setFragment(this);
//	    authButton.setReadPermissions(Arrays.asList("email"));
	    return view;
	}
}
