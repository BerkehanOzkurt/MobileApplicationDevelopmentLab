package msku.ceng.madlab.week8;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ActionMenuView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {


    EditText txturl;
    Button btnDownload;
    ImageView imgView;

    private static final  int REQUEST_EXTERNAL_STORAGE = 1;
    private static final  String [] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txturl = findViewById(R.id.txtURL);
        btnDownload = findViewById(R.id.btnDownload);
        imgView = findViewById(R.id.imgView);

        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int permission = ActivityCompat.checkSelfPermission(MainActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (permission != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(MainActivity.this, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
                }

                String fileName = "temp_image.jpg";
                String filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "/" + fileName;

                downloadFile(txturl.getText().toString(), filePath);
                preview(filePath);
            }
        });

    }

    private void preview(String filePath) {
        Bitmap image = BitmapFactory.decodeFile(filePath);
        float width = image.getWidth();
        float height = image.getHeight();
        int W = 400;
        int H = (int) ((height * W) / (width));
        Bitmap.createScaledBitmap(image, W, H,false);
        imgView.setImageBitmap(image);
    }

    private void downloadFile(String fileUrl, String filePath) {
        try {
            URL strUrl = new URL(fileUrl);
            URLConnection urlConnection = strUrl.openConnection();
            urlConnection.connect();
            InputStream inputStream = new BufferedInputStream(strUrl.openStream(),8192);
            OutputStream outputStream = new FileOutputStream(filePath);

            byte data [] = new byte [1024];
            int count;
            while ((count = inputStream.read(data)) != -1 ){
                outputStream.write(data, 0 , count);
            }

            outputStream.close();
            inputStream.close();
        }
        catch (Exception exception){
            exception.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_EXTERNAL_STORAGE){
            if (grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){

                String fileName = "temp_image.jpg";
                String filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "/" + fileName;

                downloadFile(txturl.getText().toString(), filePath);
                preview(filePath);
            }
        }
        else {
            Toast.makeText(this,"External Storage Permission not granted",Toast.LENGTH_SHORT).show();
        }
    }
}