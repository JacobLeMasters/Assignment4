package lwtech.itad230.location;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


/**
 * Created by Jacob on 2/21/2016.
 */
public class MainActivity extends Activity{

    private String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startMainService(View v){
        EditText editText = (EditText) findViewById(R.id.nameOfFile);
        fileName = editText.getText().toString() + ".txt";
        Intent intent = new Intent(this, MainService.class);
        intent.putExtra("fileName", fileName);
        startService(intent);
    }

    public void stopMainService(View v){
        Intent intent = new Intent(this, MainService.class);
        stopService(intent);
    }

    public void openTextFile(View v) throws FileNotFoundException {
        int i;
        TextView textView = (TextView) findViewById(R.id.fileContents);
        String message = textView.getText().toString();
        File file = new File(getFilesDir(), fileName);
    try {
        FileInputStream fileInputStream = new FileInputStream(file);
        while ((i = fileInputStream.read()) != -1) {
            message += (char) i;
        }
        fileInputStream.close();
    }catch (Exception e){
        e.printStackTrace();
    }

        textView.setText(message);
    }
}
