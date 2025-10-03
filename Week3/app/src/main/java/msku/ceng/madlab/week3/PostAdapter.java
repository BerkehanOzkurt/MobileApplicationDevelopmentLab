package msku.ceng.madlab.week3;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class PostAdapter extends BaseAdapter {

    List<Post> postList;
    LayoutInflater inflater;

    public PostAdapter(Activity activity, List<Post> postList) {
        inflater = activity.getLayoutInflater();
        this.postList = postList;
    }

    ;   @Override
    public int getCount() {
        return postList.size();
    }

    @Override
    public Object getItem(int i) {
        return postList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View rowView;
        rowView = inflater.inflate(R.layout.row, null);
        EditText txtMessage = (EditText) rowView.findViewById(R.id.txtMessage);
        TextView txtLocation = (TextView) rowView.findViewById(R.id.txtLocation);
        ImageView imgView = (ImageView) rowView.findViewById(R.id.imgView);
        Post post = postList.get(i);
        txtMessage.setText(post.getMessage());
        imgView.setImageBitmap(post.getImage());
        if (post.getLocation() != null)
            txtLocation.setText(String.format("%s %s", post.getLocation().getLatitude(), post.getLocation().getLongitude()));
        return rowView;
    }
}
