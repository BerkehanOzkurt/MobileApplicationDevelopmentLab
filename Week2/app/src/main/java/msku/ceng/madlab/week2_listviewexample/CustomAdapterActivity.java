package msku.ceng.madlab.week2_listviewexample;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapterActivity extends AppCompatActivity {
    final List<Animal> animals = new ArrayList<Animal>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_custom_adapter);
        animals.add(new Animal("Dart Monkey", R.drawable.dart));
        animals.add(new Animal("Sniper Monkey", R.drawable.sniper));
        animals.add(new Animal("Glue Gunner Monkey", R.drawable.glue));
        animals.add(new Animal("Benjamin", R.drawable.ben));
        animals.add(new Animal("Corvus", R.drawable.cor));
        animals.add(new Animal("Sauda", R.drawable.sauda));
        animals.add(new Animal("Pat Fusty", R.drawable.pat));

        final ListView listView = (ListView) findViewById(R.id.listview);
        AnimalAdapter animalAdapter = new AnimalAdapter(this, animals);
        listView.setAdapter(animalAdapter);
    }
}