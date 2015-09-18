package info.tutsmodel.requestjson;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by locnt_000 on 9/18/2015.
 */
public class UserAdapter extends ArrayAdapter<User> {
    Activity activity;
    List<User> list;
    public UserAdapter(Activity activity, List<User> list) {
        super(activity, R.layout.list_item,list);
        this.activity = activity;
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        User user = list.get(position);

        LayoutInflater inflater = activity.getLayoutInflater();
        View row = inflater.inflate(R.layout.list_item, null);

        TextView txtName = (TextView) row.findViewById(R.id.textView);
        TextView txtAge = (TextView) row.findViewById(R.id.textView2);

        txtName.setText("Name: "+user.name);
        txtAge.setText("Age: "+user.age);


        return row;
    }
}
