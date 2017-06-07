package ygl.com.yglapp.Utlities;

import android.content.Context;
import android.util.Log;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

import ygl.com.yglapp.Model.Quizz;

import static android.content.ContentValues.TAG;

/**
 * Created by ahmed on 06/06/2017.
 */

public class ParsingUtil {


    public static String getStringjsonFromFile(Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("ygl.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }

       public static Quizz[] getConfig(Context context) {
        String json = getStringjsonFromFile(context);
        Quizz[] quizz = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT);
            quizz = objectMapper.readValue(json, Quizz[].class);

        } catch (IOException e) {
            Log.e(TAG, "getConfig: ");
//            Log.e("Une erreur s'est produite lors du parsing du fichier de pré-configuration");
//            e.printStackTrace();
        }

        return quizz;
    }

}
