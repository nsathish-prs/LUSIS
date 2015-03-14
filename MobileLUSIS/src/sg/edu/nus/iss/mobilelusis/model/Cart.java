package sg.edu.nus.iss.mobilelusis.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Parcel;
import android.os.Parcelable;

public class Cart implements Parcelable {


	Map<String, StationeryItem> items = null;
	String comment = null;


	public Cart() {
		super();
		items = new HashMap<String, StationeryItem>();
	}
	
	public void addToCart(StationeryItem item) {
		items.put(item.getItemCode(), item);
	}

	@Override
	public int describeContents() {
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {

		List<StationeryItem> list = new ArrayList<StationeryItem>();
		for (String itemCode : items.keySet()) {
			StationeryItem item = items.get(itemCode);
			list.add(item);
		}		
		dest.writeList(list);		
	}

	private Cart(Parcel in) {
		List<StationeryItem> list = new ArrayList<StationeryItem>();
		in.readList(list, getClass().getClassLoader());
		if (items == null) { 
			items = new HashMap<String, StationeryItem>(); 
		}
		items.clear();
		for (StationeryItem item : list) {
			items.put(item.getItemCode(), item);
		}
	}

	public static final Parcelable.Creator<Cart> CREATOR = new Parcelable.Creator<Cart>() {
		public Cart createFromParcel(Parcel in) {
			return new Cart(in);
		}
		public Cart[] newArray(int size) {
			return new Cart[size];
		}
	};


	public int getNumberOfItems() {
		return items.size();
	}

	public List<StationeryItem> getCheckoutItems() {
		List<StationeryItem> checkOutItems = new ArrayList<StationeryItem>();
		for (StationeryItem item : items.values()) {
			checkOutItems.add(item);
		}
		return checkOutItems;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public StationeryItem getItem(String itemCode) {
		if (items != null && items.containsKey(itemCode)) {
			return items.get(itemCode);
		}
		return null;
	}

}
