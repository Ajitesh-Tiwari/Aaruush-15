package it.neokree.materialtabs;

public interface MaterialTabListener {
	void onTabSelected(MaterialTab tab);
	
	void onTabReselected(MaterialTab tab);
	
	void onTabUnselected(MaterialTab tab);
}
