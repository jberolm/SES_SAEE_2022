package es.unex.asee.frojomar.asee_ses.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

public class ImageUtils {

    public static Bitmap b64toBitMap(String b64image){
        Log.i("IU--START", b64image);
        b64image.replaceAll("\n","");
        Log.i("IU-MIDDLE", b64image);
        String base64Image = b64image.split(",")[1];
        Log.i("IU----END", base64Image);
        byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        return decodedByte;
    }
}
