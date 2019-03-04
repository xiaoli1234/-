package com.cjf.androidframework.ListActivity;

import java.util.Comparator;

public class ListModelSort implements Comparator<ListModel> {

	@Override
	public int compare(ListModel arg0, ListModel arg1) {
		if(arg0.IsJ1){
			return 1;
		}
		return -1;
	}

}
