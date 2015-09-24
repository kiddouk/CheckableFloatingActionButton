package com.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;
import io.errorlab.widget.CheckableFloatingActionButton;


public class MainActivity extends AppCompatActivity
{
	static final String STATE_FAB = "fabState";
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

		// setSupportActionBar(toolbar);
		toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));


		String[] comments = {
			"Comment 1",
			"Comment 2",
			"Comment 3",
			"Comment 4",
			"Comment 5",
			"Comment 6",
			"Comment 7",
			"Comment 8",
			"Comment 9",
			"Comment 10",
			"Comment 11",
			"Comment 12",
			"Comment 13",
			"Comment 14",
			"Comment 15",
			"Comment 16",
			"Comment 17",
			"Comment 18",
			"Comment 13",
			"Comment 14",
			"Comment 10",
			"Comment 11",
			"Comment 12",
			"Comment 13",
			"Comment 14",
			"Comment 10",
			"Comment 11",
			"Comment 12",
			"Comment 13",
			"Comment 14",
			"Comment 10",
			"Comment 11",
			"Comment 12",
			"Comment 13",
			"Comment 14",
			"Comment 10",
			"Comment 11",
			"Comment 12",
			"Comment 13",
			"Comment 14",

		};
		
		MyAdapter adapter = new MyAdapter(comments);
		RecyclerView commentsView = (RecyclerView) findViewById(R.id.comments);
		Log.e("TOTO", Integer.toString(adapter.getItemCount()));
		commentsView.setAdapter(adapter);
		commentsView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));

		adapter.notifyDataSetChanged();
    }

	static class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
		private String[] mDataset;

		// Provide a reference to the views for each data item
		// Complex data items may need more than one view per item, and
		// you provide access to all the views for a data item in a view holder
		public static class ViewHolder extends RecyclerView.ViewHolder {
			// each data item is just a string in this case
			public TextView mTextView;
			public ViewHolder(TextView v) {
				super(v);
				mTextView = v;
			}
		}

		// Provide a suitable constructor (depends on the kind of dataset)
		public MyAdapter(String[] myDataset) {
			mDataset = myDataset;
		}

		// Create new views (invoked by the layout manager)
		@Override
		public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
													   int viewType) {
			// create a new view
			TextView v = (TextView) LayoutInflater.from(parent.getContext())
				.inflate(R.layout.text_item, parent, false);
			ViewHolder vh = new ViewHolder(v);
			return vh;
		}

		// Replace the contents of a view (invoked by the layout manager)
		@Override
		public void onBindViewHolder(ViewHolder holder, int position) {
			// - get element from your dataset at this position
			// - replace the contents of the view with that element
			holder.mTextView.setText(mDataset[position]);
		}

		// Return the size of your dataset (invoked by the layout manager)
		@Override
		public int getItemCount() {
			return mDataset.length;
		}
	}
}


