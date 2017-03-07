package flash.hippo.com.autoqrscanner;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.*;
import com.google.zxing.common.HybridBinarizer;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;



public class MainActivity extends AppCompatActivity {

    int RESULT_LOAD_IMG=88;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      /* Bitmap bitmap = BitmapFactory.decodeStream(is);
        String decoded=scanQRImage(bitmap);
        Toast.makeText(this,"Decoded string is"+decoded,Toast.LENGTH_SHORT);

        Log.d(decoded,"NTOHING");*/

       /*// Log.i("QrTest", "Decoded string="+decoded);
      Bitmap  image= BitmapFactory.decodeResource(getResources(), R.drawable.image);
        Toast.makeText(getBaseContext(),""+scanQRImage(image),Toast.LENGTH_SHORT);*/
      /*  Log.d(scanQRImage(image),"NTOHING");
*/






    }


    public void loadimage(View v) {

        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
// Start the Intent
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(getBaseContext(),"soooooooooooooooooo",Toast.LENGTH_SHORT);
        Log.d("ffffffffffffffffff","FFFFFFF");
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data) {
                /*// Get the Image from data
                Log.d("data returned",""+data.getDataString());
                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
              String  imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
                Toast.makeText(getBaseContext(),imgDecodableString,Toast.LENGTH_SHORT);
                Log.d(imgDecodableString,"FFFFFFF");
*/
                String fileName="";

                Uri uri = data.getData();
                if (uri != null) {
                    if (uri.toString().startsWith("file:")) {
                        fileName = uri.getPath();
                    } else { // uri.startsWith("content:")

                        Cursor c = getContentResolver().query(uri, null, null, null, null);

                        if (c != null && c.moveToFirst()) {

                            int id = c.getColumnIndex(MediaStore.Images.Media.DATA);
                            if (id != -1) {
                                fileName = c.getString(id);
                            }
                        }
                    }
                }


                try {
                    File f=new File(fileName);
                    Log.d(fileName,"end string");
                    Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));

                    //Log.d("Readinngggggggg",scanQRImage(b));
                    TextView textView=(TextView)findViewById(R.id.qrimage);
                    textView.setText(scanQRImage(b));
                }
                catch (FileNotFoundException e)
                {
                    e.printStackTrace();
                    Log.d("Readinngggggggg","ffailed");
                }




                /*ImageView imgView = (ImageView) findViewById(R.id.imgView);
                // Set the Image in ImageView after decoding the String
                imgView.setImageBitmap(BitmapFactory
                        .decodeFile(imgDecodableString));*/

            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Log.e("SomethingSomething\t", "Something", e);
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

    }




    public static String scanQRImage(Bitmap bMap) {
        String contents = null;

        int[] intArray = new int[bMap.getWidth()*bMap.getHeight()];
        //copy pixel data from the Bitmap into the 'intArray' array
        bMap.getPixels(intArray, 0, bMap.getWidth(), 0, 0, bMap.getWidth(), bMap.getHeight());

        LuminanceSource source = new RGBLuminanceSource(bMap.getWidth(), bMap.getHeight(), intArray);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

        Reader reader = new MultiFormatReader();
        try {
            Result result = reader.decode(bitmap);
            contents = result.getText();
        }
        catch (Exception e) {
            Log.e("QrTest", "Error decoding barcode", e);
        }
        return contents;
    }


}
